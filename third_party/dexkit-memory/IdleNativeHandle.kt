package org.luckypray.dexkit

import java.io.Closeable
import java.lang.ref.WeakReference
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

internal fun interface IdleCancellation { fun cancel() }

internal interface IdleTaskScheduler {
    fun schedule(delayMillis: Long, action: () -> Unit): IdleCancellation
}

/** 只持有轻量入口；查询期间独占句柄，真正空闲后释放，后续查询再打开。 */
internal class IdleNativeHandle(
    private val idleTimeoutMillis: Long,
    private val open: () -> Long,
    private val release: (Long) -> Unit,
    private val nanoTime: () -> Long = System::nanoTime,
    private val scheduler: IdleTaskScheduler = SharedIdleScheduler,
    private val onIdleRelease: () -> Unit = {},
    private val keepResident: () -> Boolean = { false },
    private val consumeForceRelease: () -> Boolean = { false }
) : Closeable {
    private val lock = ReentrantLock()
    private var handle = 0L
    private var closed = false
    private var depth = 0
    private var lastUseNanos = 0L
    private var ticket = 0L
    private var pending: IdleCancellation? = null
    private var releaseAfterUse = false

    init { require(idleTimeoutMillis > 0) { "空闲回收期限必须为正数" } }

    val isValid: Boolean get() = lock.withLock { !closed }
    val isLoaded: Boolean get() = lock.withLock { handle != 0L }

    fun <T> use(block: (Long) -> T): T = lock.withLock {
        check(!closed) { "DexKitBridge 已永久关闭" }
        if (handle == 0L) {
            val created = open()
            check(created != 0L) { "DexKit native 引擎创建失败" }
            handle = created
        }
        depth++
        try {
            block(handle)
        } finally {
            depth--
            lastUseNanos = nanoTime()
            if (depth == 0 && (releaseAfterUse || consumeForceRelease())) {
                releaseAfterUse = false
                releaseHandleLocked()
            } else if (depth == 0 && !keepResident() && pending == null) {
                scheduleCheck(idleTimeoutMillis)
            }
        }
    }

    /** 当前句柄来自扫不到代码的 APK。空闲时立刻释放，查询进行中则等本次查询结束后释放。 */
    internal fun requestReopen() = lock.withLock {
        if (closed || keepResident()) return@withLock
        if (depth != 0) {
            releaseAfterUse = true
            return@withLock
        }
        releaseHandleLocked()
    }

    private fun scheduleCheck(delayMillis: Long) {
        val currentTicket = ++ticket
        val owner = WeakReference(this)
        pending = scheduler.schedule(delayMillis) { owner.get()?.checkIdle(currentTicket) }
    }

    private fun releaseHandleLocked() {
        pending?.cancel()
        pending = null
        ticket++
        val oldHandle = handle
        handle = 0L
        if (oldHandle != 0L) release(oldHandle)
    }

    private fun checkIdle(expectedTicket: Long) = lock.withLock {
        if (closed || ticket != expectedTicket) return@withLock
        pending = null
        if (keepResident() || handle == 0L) return@withLock
        val idleMillis = TimeUnit.NANOSECONDS.toMillis(nanoTime() - lastUseNanos)
        if (depth != 0 || idleMillis < idleTimeoutMillis) {
            scheduleCheck(maxOf(1L, idleTimeoutMillis - idleMillis))
            return@withLock
        }
        releaseHandleLocked()
        // 日志回调失败不能影响已完成的资源释放或下一次查询。
        runCatching(onIdleRelease)
    }

    override fun close() = lock.withLock {
        if (closed) return@withLock
        check(depth == 0) { "不能在正在执行的 native 查询内部关闭引擎" }
        closed = true
        ticket++
        pending?.cancel()
        pending = null
        val oldHandle = handle
        handle = 0L
        if (oldHandle != 0L) release(oldHandle)
    }
}

/** 所有实例共用一个回收线程；任务只弱引用入口，取消的任务立即出队。 */
private object SharedIdleScheduler : IdleTaskScheduler {
    private val executor = ScheduledThreadPoolExecutor(1) { task ->
        Thread(task, "Hchat-DexIdle").apply { isDaemon = true }
    }.apply {
        removeOnCancelPolicy = true
        setKeepAliveTime(30, TimeUnit.SECONDS)
        allowCoreThreadTimeOut(true)
    }

    override fun schedule(delayMillis: Long, action: () -> Unit): IdleCancellation {
        val future = executor.schedule({ action() }, delayMillis, TimeUnit.MILLISECONDS)
        return IdleCancellation { future.cancel(false) }
    }
}
