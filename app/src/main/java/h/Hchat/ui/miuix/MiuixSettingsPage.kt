package h.Hchat.ui.miuix

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ClipData
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Color as AndroidColor
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.media.ThumbnailUtils
import android.os.Build
import android.os.Handler
import android.os.Bundle
import android.os.Looper
import android.os.Parcelable
import android.os.SystemClock
import android.provider.OpenableColumns
import android.provider.MediaStore
import android.provider.Settings
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebView
import android.widget.DatePicker
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TimePicker
import android.widget.Toast
import android.widget.VideoView
import h.Hchat.BuildConfig
import h.Hchat.R
import h.Hchat.crash.CrashReportRuntime
import h.Hchat.crash.CrashReportSettings
import h.Hchat.crash.CrashReportSettingsProvider
import h.Hchat.hooks.api.ui.HchatAgentIconDrawable
import h.Hchat.hooks.items.floatingshortcut.FloatingShortcutFeature
import h.Hchat.hooks.items.script.market.PluginMarketClient
import h.Hchat.hooks.items.script.market.PluginMarketSettings
import h.Hchat.hooks.items.floatingshortcut.FloatingShortcutGlyph
import h.Hchat.hooks.items.floatingshortcut.FloatingShortcutGlyphDrawable
import h.Hchat.hooks.items.floatingshortcut.FloatingShortcutGlyphs
import h.Hchat.hooks.items.floatingshortcut.FloatingShortcutIconPickResult
import h.Hchat.hooks.items.floatingshortcut.FloatingShortcutIconPicker
import h.Hchat.hooks.items.floatingshortcut.FloatingShortcutIconStore
import h.Hchat.hooks.items.floatingshortcut.FloatingShortcutItem
import h.Hchat.hooks.items.floatingshortcut.FloatingShortcutRuntime
import h.Hchat.hooks.items.floatingshortcut.FloatingShortcutSettings
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.SideEffect
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigationevent.NavigationEventDispatcher
import androidx.navigationevent.NavigationEventDispatcherOwner
import androidx.navigationevent.compose.LocalNavigationEventDispatcherOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import h.Hchat.hooks.api.core.WeChatApis
import h.Hchat.hooks.api.media.WeChatFavoriteItem
import h.Hchat.hooks.api.message.WeChatMessageObserveApi
import h.Hchat.hooks.api.model.ContactLabelBean
import h.Hchat.hooks.api.model.WeChatContact
import h.Hchat.hooks.items.audiotransform.AudioTransformFeature
import h.Hchat.hooks.items.audiotransform.AudioTransformCancelledException
import h.Hchat.hooks.items.audiotransform.AudioTransformSegmenter
import h.Hchat.hooks.items.audiotransform.AudioTransformSettings
import h.Hchat.hooks.items.antirecall.AntiRecallFeature
import h.Hchat.hooks.items.antirecall.AntiRecallSettings
import h.Hchat.hooks.items.autoreply.AutoReplyFeature
import h.Hchat.hooks.items.autoreply.AutoReplyRule
import h.Hchat.hooks.items.autoreply.AutoReplyRuntime
import h.Hchat.hooks.items.autoreply.AutoReplySettings
import h.Hchat.hooks.items.autoreply.AutoReplyStep
import h.Hchat.hooks.items.autoreply.XiaozhiMcpStatus
import h.Hchat.hooks.items.autoreply.AutoReplyXiaozhiConfig
import h.Hchat.hooks.items.autoreply.AutoReplyZhiliaConfig
import h.Hchat.hooks.items.autoreply.XiaozhiAgentOption
import h.Hchat.hooks.items.autoreply.XiaozhiConsoleApi
import h.Hchat.hooks.items.autoreply.XiaozhiModelOption
import h.Hchat.hooks.items.autoreply.XiaozhiVoiceOption
import h.Hchat.hooks.items.autooriginal.AutoOriginalImageFeature
import h.Hchat.hooks.items.autooriginal.AutoOriginalImageSettings
import h.Hchat.hooks.items.autovieworiginal.AutoViewOriginalFeature
import h.Hchat.hooks.items.autovieworiginal.AutoViewOriginalSettings
import h.Hchat.hooks.items.atallnotify.AtAllNotificationBlockFeature
import h.Hchat.hooks.items.atallnotify.AtAllNotificationBlockSettings
import h.Hchat.hooks.items.automessageforward.AutoMessageForwardFeature
import h.Hchat.hooks.items.automessageforward.AutoMessageForwardRule
import h.Hchat.hooks.items.automessageforward.AutoMessageForwardSettings
import h.Hchat.hooks.items.callmedialimit.CallMediaLimitFeature
import h.Hchat.hooks.items.callmedialimit.CallMediaLimitSettings
import h.Hchat.hooks.items.callmedialimit.CallRingtoneBlockFeature
import h.Hchat.hooks.items.chattime.ChatTimeStyleFeature
import h.Hchat.hooks.items.chattime.ChatTimeStyleSettings
import h.Hchat.hooks.items.conversationgroup.ConversationGroup
import h.Hchat.hooks.items.conversationgroup.ConversationGroupFeature
import h.Hchat.hooks.items.conversationgroup.ConversationGroupPickerSupport
import h.Hchat.hooks.items.conversationgroup.ConversationGroupRuntime
import h.Hchat.hooks.items.conversationgroup.ConversationGroupStore
import h.Hchat.hooks.items.conversationtabs.ConversationTabsFeature
import h.Hchat.hooks.items.conversationtabs.ConversationTabsStore
import h.Hchat.hooks.items.custombottombar.CustomBottomBarFeature
import h.Hchat.hooks.items.custombottombar.FloatingBottomBarSettings
import h.Hchat.hooks.items.customnotify.CustomNotificationFeature
import h.Hchat.hooks.items.customnotify.CustomNotificationRule
import h.Hchat.hooks.items.customnotify.CustomNotificationRuntime
import h.Hchat.hooks.items.customfriendavatar.CustomFriendAvatarFeature
import h.Hchat.hooks.items.customfriendavatar.CustomFriendAvatarPicker
import h.Hchat.hooks.items.customfriendavatar.CustomFriendAvatarSettings
import h.Hchat.hooks.items.customfriendavatar.CustomFriendAvatarStore
import h.Hchat.hooks.items.customnotify.CustomNotificationSettings
import h.Hchat.hooks.items.editmsg.EditMessageFeature
import h.Hchat.hooks.items.editmsg.EditMessageSettings
import h.Hchat.hooks.items.emojisave.EmojiSaveFeature
import h.Hchat.hooks.items.emojisave.EmojiSaveSettings
import h.Hchat.hooks.items.fakelocation.FakeLocationFeature
import h.Hchat.hooks.items.fakelocation.FakeLocationSettings
import h.Hchat.hooks.items.fakescancamera.FakeScanCameraFeature
import h.Hchat.hooks.items.fakescancamera.FakeScanCameraSettings
import h.Hchat.hooks.items.fakevoiceduration.FakeVoiceDurationFeature
import h.Hchat.hooks.items.fakevoiceduration.FakeVoiceDurationSettings
import h.Hchat.hooks.items.forwardlimit.RemoveForwardLimitFeature
import h.Hchat.hooks.items.forwardlimit.RemoveForwardLimitSettings
import h.Hchat.hooks.items.gameemoji.GameEmojiFeature
import h.Hchat.hooks.items.gameemoji.GameEmojiSettings
import h.Hchat.hooks.items.groupleave.GroupLeaveMonitorFeature
import h.Hchat.hooks.items.groupleave.GroupLeaveReplyTemplate
import h.Hchat.hooks.items.groupleave.GroupLeaveReplyTemplateBinding
import h.Hchat.hooks.items.groupleave.GroupLeaveMonitorSettings
import h.Hchat.hooks.items.grouplabel.GroupChatLabel
import h.Hchat.hooks.items.grouplabel.GroupChatLabelFeature
import h.Hchat.hooks.items.grouplabel.GroupChatLabelStore
import h.Hchat.hooks.items.grouplabel.QuickGroupChatLabelFeature
import h.Hchat.hooks.items.grouplabel.QuickGroupChatLabelSettings
import h.Hchat.hooks.items.groupnicknamecolor.GroupNicknameColorSettings
import h.Hchat.hooks.items.groupnicknamecolor.GroupNicknameColorStore
import h.Hchat.hooks.items.grouprename.GroupRenameMonitorFeature
import h.Hchat.hooks.items.grouprename.GroupRenameReplyTemplate
import h.Hchat.hooks.items.grouprename.GroupRenameMonitorSettings
import h.Hchat.hooks.items.grouprename.GroupRenameTemplateBinding
import h.Hchat.hooks.items.hideavatar.HideChatAvatarFeature
import h.Hchat.hooks.items.hideavatar.HideChatAvatarSettings
import h.Hchat.hooks.items.hidemenu.HideChatMenuFeature
import h.Hchat.hooks.items.hidemenu.HideChatMenuSettings
import h.Hchat.hooks.items.hotupdate.DisableHotUpdateFeature
import h.Hchat.hooks.items.hotupdate.DisableHotUpdateSettings
import h.Hchat.hooks.items.hometextcolor.HomeTextColorFeature
import h.Hchat.hooks.items.hometextcolor.HomeTextColorSettings
import h.Hchat.hooks.items.inputhint.InputHintFeature
import h.Hchat.hooks.items.inputhint.InputHintSettings
import h.Hchat.hooks.items.inputhint.InputHintStats
import h.Hchat.hooks.items.inputhint.OutgoingMessageStatsRepository
import h.Hchat.hooks.items.keepalive.WeChatKeepAliveFeature
import h.Hchat.hooks.items.keepalive.WeChatKeepAliveRuntime
import h.Hchat.hooks.items.keepalive.WeChatKeepAliveSettings
import h.Hchat.hooks.items.keywordnotify.KeywordNotificationFeature
import h.Hchat.hooks.items.keywordnotify.KeywordNotificationRuntime
import h.Hchat.hooks.items.keywordnotify.KeywordNotificationSettings
import h.Hchat.hooks.items.keywordnotify.KeywordRule
import h.Hchat.hooks.items.zombiecheck.ZombieCheckController
import h.Hchat.hooks.items.zombiecheck.ZombieCheckActionResult
import h.Hchat.hooks.items.zombiecheck.ZombieCheckFeature
import h.Hchat.hooks.items.zombiecheck.ZombieCheckResultType
import h.Hchat.hooks.items.zombiecheck.ZombieCheckSettings
import h.Hchat.hooks.items.membertitle.MemberTitleFeature
import h.Hchat.hooks.items.membertitle.MemberTitleSettings
import h.Hchat.hooks.items.membertitle.MemberTitleStore
import h.Hchat.hooks.items.messageaffix.MessageAffixFeature
import h.Hchat.hooks.items.messageaffix.MessageAffixSettings
import h.Hchat.hooks.items.messagebubble.MessageBubbleFeature
import h.Hchat.hooks.items.messagebubble.MessageBubblePickResult
import h.Hchat.hooks.items.messagebubble.MessageBubblePicker
import h.Hchat.hooks.items.messagebubble.MessageBubbleSettings
import h.Hchat.hooks.items.messagebubble.MessageBubbleSlot
import h.Hchat.hooks.items.messagebubble.MessageBubbleStore
import h.Hchat.hooks.items.messagebubble.MessageBubbleKind
import h.Hchat.hooks.items.messagetextcolor.MessageTextColorFeature
import h.Hchat.hooks.items.messagetextcolor.MessageTextColorSettings
import h.Hchat.hooks.items.messageblock.MessageBlockBinding
import h.Hchat.hooks.items.messageblock.MessageBlockDefaultRule
import h.Hchat.hooks.items.messageblock.MessageBlockFeature
import h.Hchat.hooks.items.messageblock.MessageBlockSettings
import h.Hchat.hooks.items.messageblock.MessageBlockTemplate
import h.Hchat.hooks.items.messageforward.MessageForwardFeature
import h.Hchat.hooks.items.messageforward.MessageForwardSettings
import h.Hchat.hooks.items.miniprogrambaselib.FakeMiniProgramBaseLibFeature
import h.Hchat.hooks.items.miniprogrambaselib.FakeMiniProgramBaseLibSettings
import h.Hchat.hooks.items.miniprogramsplashad.SkipGlobalMiniProgramSplashAdsFeature
import h.Hchat.hooks.items.miniprogramsplashad.SkipGlobalMiniProgramSplashAdsSettings
import h.Hchat.hooks.items.miniprogramvideoad.SkipMiniProgramVideoAdsFeature
import h.Hchat.hooks.items.miniprogramvideoad.SkipMiniProgramVideoAdsSettings
import h.Hchat.hooks.items.musicorder.QQMusicOrderFeature
import h.Hchat.hooks.items.musicorder.QQMusicOrderSettings
import h.Hchat.hooks.items.moments.OriginalMomentsUploadFeature
import h.Hchat.hooks.items.moments.OriginalMomentsUploadSettings
import h.Hchat.hooks.items.moments.MomentsAutoCommentFeature
import h.Hchat.hooks.items.moments.MomentsAutoCommentSettings
import h.Hchat.hooks.items.moments.MomentsAutoLikeFeature
import h.Hchat.hooks.items.moments.MomentsAutoLikeSettings
import h.Hchat.hooks.items.moments.MomentsAutoForwardFeature
import h.Hchat.hooks.items.moments.MomentsAutoForwardSettings
import h.Hchat.hooks.items.moments.MomentsAutoRefreshFeature
import h.Hchat.hooks.items.moments.MomentsAutoRefreshSettings
import h.Hchat.hooks.items.moments.MomentsBottomDetailFeature
import h.Hchat.hooks.items.moments.MomentsBottomDetailSettings
import h.Hchat.hooks.items.moments.MomentsContactFilterFeature
import h.Hchat.hooks.items.moments.MomentsContactFilterSettings
import h.Hchat.hooks.items.moments.MomentsKeywordBlockFeature
import h.Hchat.hooks.items.moments.MomentsKeywordBlockSettings
import h.Hchat.hooks.items.moments.MomentsPostNotificationFeature
import h.Hchat.hooks.items.moments.MomentsPostNotificationSettings
import h.Hchat.hooks.items.moments.MomentsUploadTailFeature
import h.Hchat.hooks.items.moments.MomentsUploadTailPresets
import h.Hchat.hooks.items.moments.MomentsUploadTailSettings
import h.Hchat.hooks.items.moments.RemoveMomentsAdsFeature
import h.Hchat.hooks.items.moments.RemoveMomentsAdsSettings
import h.Hchat.hooks.items.moments.SnsAntiRecallFeature
import h.Hchat.hooks.items.moments.SnsAntiRecallSettings
import h.Hchat.hooks.items.momentsfake.MomentsFakeCommentSettingsProvider
import h.Hchat.hooks.items.momentsfake.MomentsFakeForwardRuntimeRegistry
import h.Hchat.hooks.items.momentsfake.MomentsFakeForwardSettingsProvider
import h.Hchat.hooks.items.momentsfake.MomentsFakeLikeSettingsProvider
import h.Hchat.hooks.items.momentsfake.MomentsFakeLikeCandidateRepository
import h.Hchat.hooks.items.momentsfake.MomentsFakeInteractionRuntimeRegistry
import h.Hchat.hooks.items.momentsfake.MomentsFakeInteractionSettings
import h.Hchat.hooks.items.multirecall.MultiRecallFeature
import h.Hchat.hooks.items.multirecall.MultiRecallSettings
import h.Hchat.hooks.items.payment.core.AutoRedPacketFeature
import h.Hchat.hooks.items.payment.core.PaymentTemplateTimeFormatter
import h.Hchat.hooks.items.payment.core.RedPacketReplyStep
import h.Hchat.hooks.items.payment.core.RedPacketRuleBinding
import h.Hchat.hooks.items.payment.core.RedPacketRuleConfig
import h.Hchat.hooks.items.payment.core.RedPacketRuleTemplate
import h.Hchat.hooks.items.payment.core.RedPacketSettings
import h.Hchat.hooks.items.payment.fakebalance.FakeWalletBalanceFeature
import h.Hchat.hooks.items.payment.fakebalance.FakeWalletBalanceSettings
import h.Hchat.hooks.items.payment.gift.AutoGiftFeature
import h.Hchat.hooks.items.payment.gift.GiftSettings
import h.Hchat.hooks.items.payment.transfer.AutoTransferFeature
import h.Hchat.hooks.items.payment.transfer.AutoTransferSettings
import h.Hchat.hooks.items.payment.transfer.TransferReceiveAccountStore
import h.Hchat.hooks.items.payment.transfer.TransferRuleBinding
import h.Hchat.hooks.items.payment.transfer.TransferRuleConfig
import h.Hchat.hooks.items.payment.transfer.TransferRuleTemplate
import h.Hchat.hooks.items.patblock.PatBlockFeature
import h.Hchat.hooks.items.patblock.PatBlockSettings
import h.Hchat.hooks.items.profileid.ProfileIdFeature
import h.Hchat.hooks.items.profileid.ProfileIdSettings
import h.Hchat.hooks.items.protobuf.ProtobufPacketFeature
import h.Hchat.hooks.items.protobuf.ProtobufPacketRuntime
import h.Hchat.hooks.items.protobuf.ProtobufPacketSettings
import h.Hchat.hooks.items.quoteclear.QuoteDeleteClearFeature
import h.Hchat.hooks.items.quoteclear.QuoteDeleteClearSettings
import h.Hchat.hooks.items.quickcontactedit.QuickContactEditFeature
import h.Hchat.hooks.items.quickcontactedit.QuickContactEditSettings
import h.Hchat.hooks.items.quickmoments.QuickMomentsFeature
import h.Hchat.hooks.items.quickmoments.QuickMomentsSettings
import h.Hchat.hooks.items.quickread.QuickMarkReadFeature
import h.Hchat.hooks.items.quickread.QuickMarkReadSettings
import h.Hchat.hooks.items.quickterminate.QuickTerminateFeature
import h.Hchat.hooks.items.quickterminate.QuickTerminateSettings
import h.Hchat.hooks.items.realtail.RealNameTailFeature
import h.Hchat.hooks.items.realtail.RealNameTailSettings
import h.Hchat.hooks.items.realtail.RealNameTailStore
import h.Hchat.hooks.items.roundavatar.RoundAvatarFeature
import h.Hchat.hooks.items.roundavatar.RoundAvatarSettings
import h.Hchat.hooks.items.script.ScriptPluginFeature
import h.Hchat.hooks.items.script.ScriptPluginManager
import h.Hchat.hooks.items.script.ScriptPluginRuntime
import h.Hchat.hooks.items.script.ScriptPluginSettings
import h.Hchat.hooks.items.script.market.PluginMarketHistoryVersion
import h.Hchat.hooks.items.script.market.PluginMarketInstaller
import h.Hchat.hooks.items.script.market.PluginMarketException
import h.Hchat.hooks.items.script.market.PluginMarketFile
import h.Hchat.hooks.items.script.market.PluginMarketComment
import h.Hchat.hooks.items.script.market.PluginMarketNotification
import h.Hchat.hooks.items.script.market.PluginMarketPlugin
import h.Hchat.hooks.items.script.market.PluginMarketRepository
import h.Hchat.hooks.items.script.market.PluginMarketReviewStatus
import h.Hchat.hooks.items.script.market.PluginMarketUserIdentity
import h.Hchat.hooks.items.script.agent.CodexSettings
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentClient
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentAttachment
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentChatMessage
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentConfig
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentCancellation
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentContext
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentDiff
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentDraft
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentEventIds
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentLocalFiles
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentMcpServer
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentReasoning
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentRequest
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentResumeState
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentSession
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentSessionStore
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentSettings
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentProfile
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentQuotedMessage
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentSpeech
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentTextMerge
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentToolEvent
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentToolResultStore
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentValidator
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentWorkspaceChange
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentWorkspaceToolConfirmation
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentWorkspaceWriteDecision
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentWorkspaceTools
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentWriter
import h.Hchat.hooks.items.scheduledtask.ScheduledTaskContentItem
import h.Hchat.hooks.items.scheduledtask.ScheduledTaskFeature
import h.Hchat.hooks.items.scheduledtask.ScheduledTaskItem
import h.Hchat.hooks.items.scheduledtask.ScheduledTaskRuntimeCoordinator
import h.Hchat.hooks.items.scheduledtask.ScheduledTaskSettings
import h.Hchat.hooks.items.selectedmessages.SelectedMessageSnapshot
import h.Hchat.hooks.items.selectedmessages.SelectedMessagesFeature
import h.Hchat.hooks.items.selectedmessages.SelectedMessagesRuntimeCoordinator
import h.Hchat.hooks.items.selectedmessages.SelectedMessagesSettings
import h.Hchat.hooks.items.settings.PluginAgentEntryProvider
import h.Hchat.hooks.items.settings.SettingsEntrySettings
import h.Hchat.hooks.items.settings.SettingsFeature
import h.Hchat.hooks.items.shortvideo.FinderMediaDownloadFeature
import h.Hchat.hooks.items.shortvideo.FinderMediaDownloadSettings
import h.Hchat.hooks.items.statuslimit.StatusTextLimitFeature
import h.Hchat.hooks.items.statuslimit.StatusTextLimitSettings
import h.Hchat.hooks.items.swipequote.SwipeQuoteFeature
import h.Hchat.hooks.items.swipequote.SwipeQuoteSettings
import h.Hchat.hooks.items.tablet.WeChatTabletFeature
import h.Hchat.hooks.items.tablet.WeChatTabletSettings
import h.Hchat.hooks.items.textspeech.TextSpeechFeature
import h.Hchat.hooks.items.textspeech.TextSpeechEngineCatalog
import h.Hchat.hooks.items.textspeech.TextSpeechSettings
import h.Hchat.hooks.items.textspeech.TextSpeechVoiceCatalog
import h.Hchat.hooks.items.textvoice.TextVoiceFeature
import h.Hchat.hooks.items.textvoice.TextVoiceSettings
import h.Hchat.hooks.items.typingreport.TypingReportBlockFeature
import h.Hchat.hooks.items.typingreport.TypingReportBlockSettings
import h.Hchat.hooks.items.voiceforward.VoiceForwardFeature
import h.Hchat.hooks.items.voiceforward.VoiceForwardSettings
import h.Hchat.hooks.items.hchatextra.GroupMemberHistorySettingsProvider
import h.Hchat.hooks.items.hchatextra.MessageDetailsSettingsProvider
import h.Hchat.hooks.items.hchatextra.RedPacketDetailsSettingsProvider
import h.Hchat.hooks.items.hchatextra.SkipWebRiskSettingsProvider
import h.Hchat.hooks.items.hchatextra.HchatExtraSettings
import h.Hchat.hooks.items.voicepreview.VoicePreviewFeature
import h.Hchat.hooks.items.voicepreview.VoicePreviewSettings
import h.Hchat.media.AudioTransformBridge
import h.Hchat.preferences.HchatStorage
import h.Hchat.preferences.TermsGate
import h.Hchat.ui.FeatureSettingsProvider
import h.Hchat.ui.UIRegistry
import h.Hchat.utils.KavaReflector
import h.Hchat.utils.KeywordReplacementRule
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import org.json.JSONArray
import org.json.JSONObject
import top.yukonga.miuix.kmp.basic.BreadcrumbBar
import top.yukonga.miuix.kmp.basic.BreadcrumbItem
import top.yukonga.miuix.kmp.basic.ButtonDefaults
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.Checkbox
import top.yukonga.miuix.kmp.basic.MiuixScrollBehavior
import top.yukonga.miuix.kmp.basic.PullToRefresh
import top.yukonga.miuix.kmp.basic.ScrollBehavior
import top.yukonga.miuix.kmp.basic.Slider
import top.yukonga.miuix.kmp.basic.TabRow
import top.yukonga.miuix.kmp.basic.DropdownEntry
import top.yukonga.miuix.kmp.basic.DropdownItem
import top.yukonga.miuix.kmp.menu.WindowDropdownMenu
import top.yukonga.miuix.kmp.basic.CircularProgressIndicator
import top.yukonga.miuix.kmp.basic.SmallTitle
import top.yukonga.miuix.kmp.basic.Switch
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.basic.TextButton
import top.yukonga.miuix.kmp.nav.core.NavDisplay
import top.yukonga.miuix.kmp.nav.core.NavKey
import top.yukonga.miuix.kmp.nav.core.navBackStackOf
import top.yukonga.miuix.kmp.nav.transition.NavTransitions
import top.yukonga.miuix.kmp.utils.springAnimateToPage
import top.yukonga.miuix.kmp.basic.TopAppBar
import top.yukonga.miuix.kmp.blur.Backdrop
import top.yukonga.miuix.kmp.blur.layerBackdrop
import top.yukonga.miuix.kmp.blur.rememberLayerBackdrop
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.theme.darkColorScheme
import top.yukonga.miuix.kmp.theme.lightColorScheme
import top.yukonga.miuix.kmp.window.WindowDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import java.io.File
import java.lang.ref.WeakReference
import java.net.URL
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Calendar
import java.util.LinkedHashSet
import java.util.Locale
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.atomic.AtomicReference
import kotlin.math.abs
import kotlin.random.Random

// 顶层页面函数拆在同包的 MiuixSettingsPagePart*.kt，避免 MiuixSettingsPageKt 超过 JVM 类文件上限。

internal const val UI_PREFS_NAME = "Hchat_miuix_ui"

internal const val KEY_FLOATING_NAV = "floating_nav"

internal const val KEY_GLASS_NAV = "glass_nav"

internal const val KEY_SEARCH_HISTORY = "search_history"

internal const val KEY_AGENT_HISTORY_GESTURE_HINT = "agent_history_gesture_hint"

internal const val SEARCH_HISTORY_LIMIT = 16

internal const val PRESS_RELEASE_DELAY_MS = 110L

internal const val RINGTONE_SYSTEM_REQUEST_CODE = 0x48435254

internal const val RINGTONE_FILE_REQUEST_CODE = 0x48435255

internal const val REDPACKET_REPLY_FILE_REQUEST_CODE = 0x48435256

internal const val AUTO_REPLY_FILE_REQUEST_CODE = 0x48435257

internal const val CONFIG_EXPORT_REQUEST_CODE = 0x48435258

internal const val CONFIG_IMPORT_REQUEST_CODE = 0x48435259

internal const val SCHEDULED_TASK_FILE_REQUEST_CODE = 0x4843525A

internal const val AUDIO_TRANSFORM_INPUT_REQUEST_CODE = 0x4843525B

internal const val AUDIO_TRANSFORM_OUTPUT_REQUEST_CODE = 0x4843525C

internal const val FAKE_LOCATION_WECHAT_PICKER_REQUEST_CODE = 0x4843525D

internal const val SCRIPT_AGENT_ATTACHMENT_REQUEST_CODE = 0x4843525E

internal const val PLUGIN_MARKET_EXTRA_FILE_REQUEST_CODE = 0x4843525F

internal const val SCRIPT_PLUGIN_EXPORT_REQUEST_CODE = 0x48435260

internal const val SCRIPT_PLUGIN_IMPORT_REQUEST_CODE = 0x48435261

internal const val MARKDOWN_LINK_TAG = "md_link"

internal val MARKDOWN_LINK_REGEX = Regex("""\[([^\]]+)]\(([^)\s]+)\)""")

internal val NAVIGATION_BUTTON_MIN_INSET = 24.dp

internal val NAVIGATION_BUTTON_EXTRA_GAP = 8.dp

internal const val REDPACKET_AT_SENDER_VARIABLE = "{@发红包的人}"

internal val REDPACKET_AT_SENDER_VARIABLES = listOf(
    REDPACKET_AT_SENDER_VARIABLE,
    "{@sender}",
    "{@成员}"
)

internal val redPacketTemplateVariables = listOf(
    TemplateVariable("{amount}", "金额"),
    TemplateVariable("{talker}", "会话"),
    TemplateVariable("{sender}", "成员"),
    TemplateVariable("{time}", "时间")
)

internal val redPacketReplyTemplateVariables = redPacketTemplateVariables + TemplateVariable(
    REDPACKET_AT_SENDER_VARIABLE,
    "@发红包人"
)

internal val transferTemplateVariables = listOf(
    TemplateVariable("{amount}", "金额"),
    TemplateVariable("{talker}", "会话"),
    TemplateVariable("{sender}", "转账人"),
    TemplateVariable("{@转账的人}", "@转账人"),
    TemplateVariable("{time}", "时间")
)

internal val antiRecallTemplateVariables = listOf(
    TemplateVariable(AntiRecallSettings.VAR_RECALLER_NAME, "撤回者"),
    TemplateVariable(AntiRecallSettings.VAR_RECALL_TEXT, "文字内容"),
    TemplateVariable(AntiRecallSettings.VAR_SEND_TIME, "发送时间"),
    TemplateVariable(AntiRecallSettings.VAR_RECALL_TIME, "撤回时间")
)

internal val messageAffixTemplateVariables = listOf(
    TemplateVariable(MessageAffixSettings.VAR_SEND_TEXT, "原消息"),
    TemplateVariable(MessageAffixSettings.VAR_LINE, "换行"),
    TemplateVariable(MessageAffixSettings.VAR_SEND_TIME, "发送时间"),
    TemplateVariable(MessageAffixSettings.VAR_SEND_DURATION, "发送耗时"),
    TemplateVariable(MessageAffixSettings.VAR_TOTAL_MESSAGES, "发送总数"),
    TemplateVariable(MessageAffixSettings.VAR_TEXT_MESSAGES, "文字消息数"),
    TemplateVariable(MessageAffixSettings.VAR_TEXT_CHARACTERS, "文字字数"),
    TemplateVariable(MessageAffixSettings.VAR_EMOJI_MESSAGES, "表情消息数"),
    TemplateVariable(MessageAffixSettings.VAR_TRANSFER_MESSAGES, "转账消息数"),
    TemplateVariable(MessageAffixSettings.VAR_RED_PACKET_MESSAGES, "红包消息数"),
    TemplateVariable(MessageAffixSettings.VAR_FILE_MESSAGES, "文件消息数")
)

internal val inputHintTemplateVariables = listOf(
    TemplateVariable(InputHintSettings.VAR_TOTAL_MESSAGES, "发送总数"),
    TemplateVariable(InputHintSettings.VAR_TEXT_MESSAGES, "文字消息数"),
    TemplateVariable(InputHintSettings.VAR_TEXT_CHARACTERS, "文字字数"),
    TemplateVariable(InputHintSettings.VAR_EMOJI_MESSAGES, "表情消息数"),
    TemplateVariable(InputHintSettings.VAR_TRANSFER_MESSAGES, "转账消息数"),
    TemplateVariable(InputHintSettings.VAR_RED_PACKET_MESSAGES, "红包消息数"),
    TemplateVariable(InputHintSettings.VAR_FILE_MESSAGES, "文件消息数")
)

internal val keywordNotificationTemplateVariables = listOf(
    TemplateVariable("%keyword%", "关键词"),
    TemplateVariable("%sender%", "发送者"),
    TemplateVariable("%wxid%", "发送者ID"),
    TemplateVariable("%content%", "内容"),
    TemplateVariable("%type%", "消息类型")
)

internal val momentsPostNotificationTemplateVariables = listOf(
    TemplateVariable("%sender%", "发布者"),
    TemplateVariable("%wxid%", "发布者ID"),
    TemplateVariable("%type%", "类型"),
    TemplateVariable("%content%", "内容"),
    TemplateVariable("%snsid%", "朋友圈ID")
)

internal val momentsAutoCommentTemplateVariables = listOf(
    TemplateVariable(MomentsAutoCommentSettings.VAR_TIME, "时间")
)

internal val textSpeechTemplateVariables = listOf(
    TemplateVariable(TextSpeechSettings.VAR_SENDER_NICKNAME, "发送者昵称"),
    TemplateVariable(TextSpeechSettings.VAR_WECHAT_ID, "发送者微信号"),
    TemplateVariable(TextSpeechSettings.VAR_REMARK_NAME, "备注"),
    TemplateVariable(TextSpeechSettings.VAR_GROUP_NICKNAME, "群内昵称"),
    TemplateVariable(TextSpeechSettings.VAR_GROUP_NAME, "群聊名称"),
    TemplateVariable(TextSpeechSettings.VAR_CONVERSATION_NAME, "会话名称"),
    TemplateVariable(TextSpeechSettings.VAR_ANNOUNCEMENT_SOURCE, "播报来源"),
    TemplateVariable(TextSpeechSettings.VAR_MESSAGE_CONTENT, "消息正文"),
    TemplateVariable(TextSpeechSettings.VAR_MESSAGE_TYPE, "消息类型"),
    TemplateVariable(TextSpeechSettings.VAR_VOICE_DURATION, "语音时长"),
    TemplateVariable(TextSpeechSettings.VAR_MESSAGE_TIME, "消息时间"),
    TemplateVariable(TextSpeechSettings.VAR_SENDER_ID, "发送者ID"),
    TemplateVariable(TextSpeechSettings.VAR_CONVERSATION_ID, "会话ID")
)

internal val momentsBottomDetailTemplateVariables = listOf(
    TemplateVariable(MomentsBottomDetailSettings.VAR_ORIGINAL_TEXT, "微信原时间"),
    TemplateVariable(MomentsBottomDetailSettings.VAR_TIME, "自定义时间"),
    TemplateVariable(MomentsBottomDetailSettings.VAR_TYPE, "朋友圈类型"),
    TemplateVariable(MomentsBottomDetailSettings.VAR_SNS_ID, "朋友圈ID"),
    TemplateVariable(MomentsBottomDetailSettings.VAR_USER_NAME, "发布者ID")
)

internal val groupMemberTemplateVariables = listOf(
    TemplateVariable("%userName%", "微信昵称"),
    TemplateVariable("%groupNickname%", "群内昵称"),
    TemplateVariable("%userWxid%", "Wxid"),
    TemplateVariable("%realNameTail%", "实名尾字"),
    TemplateVariable("%gender%", "性别"),
    TemplateVariable("%region%", "地区"),
    TemplateVariable("%groupName%", "群名"),
    TemplateVariable("%time%", "时间"),
    TemplateVariable("[AtWx=%userWxid%]", "@成员"),
    TemplateVariable("[AtWx=]", "@其他人")
)

internal val groupRenameTemplateVariables = listOf(
    TemplateVariable("%userName%", "微信昵称"),
    TemplateVariable("%oldGroupNickname%", "旧群内昵称"),
    TemplateVariable("%newGroupNickname%", "新群内昵称"),
    TemplateVariable("%userWxid%", "Wxid"),
    TemplateVariable("%realNameTail%", "实名尾字"),
    TemplateVariable("%gender%", "性别"),
    TemplateVariable("%region%", "地区"),
    TemplateVariable("%groupName%", "群名"),
    TemplateVariable("%time%", "时间"),
    TemplateVariable("[AtWx=%userWxid%]", "@成员"),
    TemplateVariable("[AtWx=]", "@其他人")
)

internal val groupRenameNoticeVariables = listOf(
    TemplateVariable("%oldGroupNickname%", "旧群内昵称"),
    TemplateVariable("%newGroupNickname%", "新群内昵称"),
    TemplateVariable("%userName%", "微信昵称"),
    TemplateVariable("%userWxid%", "可点击Wxid"),
    TemplateVariable("%realNameTail%", "真实姓名尾巴"),
    TemplateVariable("%gender%", "性别"),
    TemplateVariable("%region%", "地区"),
    TemplateVariable("%groupName%", "群名"),
    TemplateVariable("%time%", "时间")
)

internal val groupLeaveNoticeVariables = listOf(
    TemplateVariable("%displayName%", "完整显示名"),
    TemplateVariable("%groupNickname%", "群内昵称"),
    TemplateVariable("%userName%", "微信昵称"),
    TemplateVariable("%remarkName%", "备注"),
    TemplateVariable("%userWxid%", "可点击Wxid"),
    TemplateVariable("%groupName%", "群名"),
    TemplateVariable("%time%", "时间")
)

internal val groupInviteNoticeVariables = listOf(
    TemplateVariable("%inviterName%", "邀请者名称"),
    TemplateVariable("%inviterGroupNickname%", "邀请者群昵称"),
    TemplateVariable("%inviterWxid%", "邀请者可点击Wxid"),
    TemplateVariable("%inviteeName%", "被邀请者名称"),
    TemplateVariable("%inviteeGroupNickname%", "被邀请者群昵称"),
    TemplateVariable("%inviteeWxid%", "被邀请者可点击Wxid"),
    TemplateVariable("%inviteCount%", "累计邀请次数"),
    TemplateVariable("%groupName%", "群名"),
    TemplateVariable("%time%", "时间")
)

internal val autoReplyTemplateVariables = listOf(
    TemplateVariable("%friendName%", "好友/成员名"),
    TemplateVariable("%senderName%", "发送者"),
    TemplateVariable("%senderWxid%", "发送者ID"),
    TemplateVariable("%talker%", "会话ID"),
    TemplateVariable("%groupName%", "群名"),
    TemplateVariable("%content%", "原消息"),
    TemplateVariable("%atSender%", "@发送者"),
    TemplateVariable("%atAll%", "@所有人")
)

internal val scheduledTaskTemplateVariables = listOf(
    TemplateVariable("%friendName%", "好友/成员名")
)

internal val groupRandomJoinTexts = listOf(
    "[AtWx=%userWxid%] 欢迎 %userName% 加入 %groupName%",
    "欢迎新朋友 %userName% 加入，大家请多关照",
    "欢迎 %userName%，记得看群公告",
    "新成员 %userName% 已加入群聊",
    "欢迎 %userName%，愿在 %groupName% 玩得开心"
)

internal val groupRandomLeftTexts = listOf(
    "有缘再会，祝 %userName% 一切顺利",
    "%userName% 已离开群聊，愿一切安好",
    "青山不改，绿水长流，后会有期",
    "我们会想念你的，%userName%",
    "%userName% 已退群，感谢曾经同行"
)

internal val groupRandomJoinCardTitles = listOf(
    "欢迎：%userName%",
    "群聊因你而精彩",
    "新成员到来：%userName%"
)

internal val groupRandomJoinCardDescs = listOf(
    "常来聊天",
    "群名称：%groupName%\n名片：%groupNickname%\n进群时间：%time%",
    "快来和大家一起玩\nID：%userWxid%"
)

internal val groupRandomLeftCardTitles = listOf(
    "成员离群通知",
    "%userName% 已离开",
    "祝你一切顺利"
)

internal val groupRandomLeftCardDescs = listOf(
    "我们有缘再见",
    "群名称：%groupName%\n名片：%groupNickname%\n离群时间：%time%",
    "相逢是缘，祝君安好"
)

internal object NavIcons {
    val Back: ImageVector = navIcon(
        name = "Rounded.ArrowBack",
        path = "M20,11H7.83l5.59,-5.59L12,4l-8,8 8,8 1.41,-1.41L7.83,13H20v-2z"
    )
    val Attach: ImageVector = navIcon(
        name = "Rounded.AttachFile",
        path = "M16.5,6.5v11c0,2.21 -1.79,4 -4,4s-4,-1.79 -4,-4V5c0,-1.38 1.12,-2.5 2.5,-2.5s2.5,1.12 2.5,2.5v10.5c0,0.55 -0.45,1 -1,1s-1,-0.45 -1,-1V6.5H10v9c0,1.38 1.12,2.5 2.5,2.5s2.5,-1.12 2.5,-2.5V5c0,-2.21 -1.79,-4 -4,-4S7,2.79 7,5v12.5c0,3.04 2.46,5.5 5.5,5.5s5.5,-2.46 5.5,-5.5v-11h-1.5z"
    )
    val Add: ImageVector = navIcon(
        name = "Rounded.Add",
        path = "M19,13h-6v6h-2v-6H5v-2h6V5h2v6h6v2z"
    )
    val Compact: ImageVector = navIcon(
        name = "Rounded.Summarize",
        path = "M14,2H6c-1.1,0 -2,0.9 -2,2v16c0,1.1 0.9,2 2,2h12c1.1,0 2,-0.9 2,-2V8l-6,-6z M13,9V3.5L18.5,9H13z M8,13h8v2H8v-2z M8,17h8v2H8v-2z M8,9h3v2H8V9z"
    )
    val Close: ImageVector = navIcon(
        name = "Rounded.Close",
        path = "M18.3,5.71L12,12l6.3,6.29 -1.41,1.42L10.59,13.41 4.29,19.71 2.88,18.29 9.17,12 2.88,5.71 4.29,4.29 10.59,10.59 16.89,4.29z"
    )
    val History: ImageVector = navIcon(
        name = "Rounded.History",
        path = "M13,3c-4.97,0 -9,4.03 -9,9s4.03,9 9,9c4.63,0 8.44,-3.5 8.94,-8h-2.02c-0.49,3.39 -3.4,6 -6.92,6 -3.87,0 -7,-3.13 -7,-7s3.13,-7 7,-7c1.93,0 3.68,0.79 4.95,2.05L15,10h6V4l-2.63,2.63C16.73,4.42 14.95,3 13,3z M12,7v6l5,3 1,-1.64 -4,-2.36V7h-2z"
    )
    val Bell: ImageVector = navIcon(
        name = "Rounded.Notifications",
        path = "M12,22c1.1,0 2,-0.9 2,-2h-4c0,1.1 0.9,2 2,2z M18,16v-5c0,-3.07 -1.63,-5.64 -4.5,-6.32V4c0,-0.83 -0.67,-1.5 -1.5,-1.5S10.5,3.17 10.5,4v0.68C7.64,5.36 6,7.92 6,11v5l-2,2v1h16v-1l-2,-2z"
    )
    val Modules: ImageVector = navIcon(
        name = "Rounded.Extension",
        path = "M20.5,11H19V7c0,-1.1 -0.9,-2 -2,-2h-4V3.5C13,2.12 11.88,1 10.5,1S8,2.12 8,3.5V5H4c-1.1,0 -1.99,0.9 -1.99,2v3.8H3.5c1.49,0 2.7,1.21 2.7,2.7s-1.21,2.7 -2.7,2.7H2V20c0,1.1 0.9,2 2,2h3.8v-1.5c0,-1.49 1.21,-2.7 2.7,-2.7s2.7,1.21 2.7,2.7V22H17c1.1,0 2,-0.9 2,-2v-4h1.5c1.38,0 2.5,-1.12 2.5,-2.5S21.88,11 20.5,11z"
    )
    val Settings: ImageVector = navIcon(
        name = "Rounded.Settings",
        path = "M19.5,12c0,-0.23 -0.01,-0.45 -0.03,-0.68l1.86,-1.41c0.4,-0.3 0.51,-0.86 0.26,-1.3l-1.87,-3.23c-0.25,-0.44 -0.79,-0.62 -1.25,-0.42l-2.15,0.91c-0.37,-0.26 -0.76,-0.49 -1.17,-0.68l-0.29,-2.31C14.8,2.38 14.37,2 13.87,2h-3.73C9.63,2 9.2,2.38 9.14,2.88L8.85,5.19c-0.41,0.19 -0.8,0.42 -1.17,0.68L5.53,4.96c-0.46,-0.2 -1,-0.02 -1.25,0.42L2.41,8.62c-0.25,0.44 -0.14,0.99 0.26,1.3l1.86,1.41C4.51,11.55 4.5,11.77 4.5,12s0.01,0.45 0.03,0.68l-1.86,1.41c-0.4,0.3 -0.51,0.86 -0.26,1.3l1.87,3.23c0.25,0.44 0.79,0.62 1.25,0.42l2.15,-0.91c0.37,0.26 0.76,0.49 1.17,0.68l0.29,2.31c0.06,0.5 0.49,0.88 0.99,0.88h3.73c0.5,0 0.93,-0.38 0.99,-0.88l0.29,-2.31c0.41,-0.19 0.8,-0.42 1.17,-0.68l2.15,0.91c0.46,0.2 1,0.02 1.25,-0.42l1.87,-3.23c0.25,-0.44 0.14,-0.99 -0.26,-1.3l-1.86,-1.41C19.49,12.45 19.5,12.23 19.5,12z M12.04,15.5c-1.93,0 -3.5,-1.57 -3.5,-3.5s1.57,-3.5 3.5,-3.5s3.5,1.57 3.5,3.5S13.97,15.5 12.04,15.5z"
    )
    val More: ImageVector = navIcon(
        name = "Rounded.MoreVert",
        path = "M12,8c1.1,0 2,-0.9 2,-2s-0.9,-2 -2,-2 -2,0.9 -2,2 0.9,2 2,2z M12,10c-1.1,0 -2,0.9 -2,2s0.9,2 2,2 2,-0.9 2,-2 -0.9,-2 -2,-2z M12,16c-1.1,0 -2,0.9 -2,2s0.9,2 2,2 2,-0.9 2,-2 -0.9,-2 -2,-2z"
    )
    val Import: ImageVector = navIcon(
        name = "Rounded.FileDownload",
        path = "M19,9h-4V3H9v6H5l7,7 7,-7z M5,18v2h14v-2H5z"
    )
    val Export: ImageVector = navIcon(
        name = "Rounded.FileUpload",
        path = "M9,16h6v-6h4l-7,-7 -7,7h4v6z M5,18v2h14v-2H5z"
    )
    val Practical: ImageVector = navIcon(
        name = "Rounded.GridView",
        path = "M5,3h4c1.1,0 2,0.9 2,2v4c0,1.1 -0.9,2 -2,2H5c-1.1,0 -2,-0.9 -2,-2V5c0,-1.1 0.9,-2 2,-2z M15,3h4c1.1,0 2,0.9 2,2v4c0,1.1 -0.9,2 -2,2h-4c-1.1,0 -2,-0.9 -2,-2V5c0,-1.1 0.9,-2 2,-2z M5,13h4c1.1,0 2,0.9 2,2v4c0,1.1 -0.9,2 -2,2H5c-1.1,0 -2,-0.9 -2,-2v-4c0,-1.1 0.9,-2 2,-2z M15,13h4c1.1,0 2,0.9 2,2v4c0,1.1 -0.9,2 -2,2h-4c-1.1,0 -2,-0.9 -2,-2v-4c0,-1.1 0.9,-2 2,-2z"
    )
    val Entertainment: ImageVector = navIcon(
        name = "Rounded.PlayCircle",
        path = "M12,2C6.48,2 2,6.48 2,12s4.48,10 10,10s10,-4.48 10,-10S17.52,2 12,2z M10,15.5v-7c0,-0.4 0.45,-0.64 0.78,-0.42l5.25,3.5c0.3,0.2 0.3,0.64 0,0.84l-5.25,3.5C10.45,16.14 10,15.9 10,15.5z"
    )
    val Play: ImageVector = navIcon(
        name = "Rounded.PlayArrow",
        path = "M8,5v14l11,-7z"
    )
    val Pause: ImageVector = navIcon(
        name = "Rounded.Pause",
        path = "M6,19h4V5H6v14z M14,5v14h4V5h-4z"
    )
    val Search: ImageVector = navIcon(
        name = "Rounded.Search",
        path = "M9.5,3C5.91,3 3,5.91 3,9.5S5.91,16 9.5,16c1.61,0 3.09,-0.59 4.23,-1.57l4.42,4.42c0.29,0.29 0.77,0.29 1.06,0s0.29,-0.77 0,-1.06l-4.42,-4.42C15.91,12.09 16,10.82 16,9.5C16,5.91 13.09,3 9.5,3z M9.5,4.5c2.76,0 5,2.24 5,5s-2.24,5 -5,5s-5,-2.24 -5,-5s2.24,-5 5,-5z"
    )
    val Send: ImageVector = navIcon(
        name = "Rounded.Send",
        path = "M2.01,21L23,12 2.01,3 2,10l15,2 -15,2z"
    )
    val Stop: ImageVector = navIcon(
        name = "Rounded.Stop",
        path = "M6,6h12v12H6z"
    )
    val Refresh: ImageVector = navIcon(
        name = "Rounded.Refresh",
        path = "M17.65,6.35C16.2,4.9 14.21,4 12,4c-4.42,0 -7.99,3.58 -7.99,8s3.57,8 7.99,8c3.73,0 6.84,-2.55 7.73,-6h-2.08c-0.82,2.33 -3.04,4 -5.65,4 -3.31,0 -6,-2.69 -6,-6s2.69,-6 6,-6c1.66,0 3.14,0.69 4.22,1.78L13,11h7V4l-2.35,2.35z"
    )
    val Copy: ImageVector = navIcon(
        name = "Rounded.Copy",
        path = "M16,1H4c-1.1,0 -2,0.9 -2,2v14h2V4h12V1z M19,5H8c-1.1,0 -2,0.9 -2,2v14c0,1.1 0.9,2 2,2h11c1.1,0 2,-0.9 2,-2V7c0,-1.1 -0.9,-2 -2,-2z"
    )
    val Quote: ImageVector = navIcon(
        name = "Rounded.Reply",
        path = "M10,9V5l-7,7 7,7v-4.1c5,0 8.5,1.6 11,5.1 -1,-5 -4,-10 -11,-11z"
    )
    val Volume: ImageVector = navIcon(
        name = "Rounded.VolumeUp",
        path = "M3,9v6h4l5,5V4L7,9H3z M16.5,12c0,-1.77 -1.02,-3.29 -2.5,-4.03v8.05c1.48,-0.73 2.5,-2.25 2.5,-4.02z M14,3.23v2.06c2.89,0.86 5,3.54 5,6.71s-2.11,5.85 -5,6.71v2.06c4.01,-0.91 7,-4.49 7,-8.77s-2.99,-7.86 -7,-8.77z"
    )
    val Edit: ImageVector = navIcon(
        name = "Rounded.Edit",
        path = "M3,17.25V21h3.75L17.81,9.94l-3.75,-3.75L3,17.25z M20.71,7.04c0.39,-0.39 0.39,-1.02 0,-1.41l-2.34,-2.34c-0.39,-0.39 -1.02,-0.39 -1.41,0l-1.83,1.83 3.75,3.75 1.83,-1.83z"
    )
    val Delete: ImageVector = navIcon(
        name = "Rounded.Delete",
        path = "M6,19c0,1.1 0.9,2 2,2h8c1.1,0 2,-0.9 2,-2V7H6v12z M8,9h8v10H8V9z M15.5,4l-1,-1h-5l-1,1H5v2h14V4z"
    )
    val MoveUp: ImageVector = navIcon(
        name = "Rounded.KeyboardArrowUp",
        path = "M7.41,15.41L12,10.83l4.59,4.58L18,14l-6,-6 -6,6z"
    )
    val MoveDown: ImageVector = navIcon(
        name = "Rounded.KeyboardArrowDown",
        path = "M7.41,8.59L12,13.17l4.59,-4.58L18,10l-6,6 -6,-6z"
    )
    val Expand: ImageVector = navIcon(
        name = "Rounded.ChevronRight",
        path = "M9.29,6.71c-0.39,0.39 -0.39,1.02 0,1.41L13.17,12l-3.88,3.88c-0.39,0.39 -0.39,1.02 0,1.41 0.39,0.39 1.02,0.39 1.41,0l4.59,-4.59c0.39,-0.39 0.39,-1.02 0,-1.41L10.7,6.7c-0.38,-0.38 -1.02,-0.38 -1.41,0.01z"
    )
    val Pin: ImageVector = navIcon(
        name = "Rounded.PushPin",
        path = "M16,9V4l1,-1V2H7v1l1,1v5c0,1.66 -1.34,3 -3,3v2h6v7l1,1 1,-1v-7h6v-2c-1.66,0 -3,-1.34 -3,-3z"
    )
    val Lock: ImageVector = navIcon(
        name = "Rounded.Lock",
        path = "M18,8h-1V6c0,-2.76 -2.24,-5 -5,-5S7,3.24 7,6v2H6c-1.1,0 -2,0.9 -2,2v10c0,1.1 0.9,2 2,2h12c1.1,0 2,-0.9 2,-2V10c0,-1.1 -0.9,-2 -2,-2z M9,6c0,-1.66 1.34,-3 3,-3s3,1.34 3,3v2H9V6z M12,17c-1.1,0 -2,-0.9 -2,-2s0.9,-2 2,-2 2,0.9 2,2 -0.9,2 -2,2z"
    )
    val Unlock: ImageVector = navIcon(
        name = "Rounded.LockOpen",
        path = "M12,17c-1.1,0 -2,-0.9 -2,-2s0.9,-2 2,-2 2,0.9 2,2 -0.9,2 -2,2z M18,8h-8V6c0,-1.1 0.9,-2 2,-2 0.95,0 1.74,0.66 1.95,1.54l1.93,-0.52C15.43,3.28 13.86,2 12,2 9.79,2 8,3.79 8,6v2H6c-1.1,0 -2,0.9 -2,2v10c0,1.1 0.9,2 2,2h12c1.1,0 2,-0.9 2,-2V10c0,-1.1 -0.9,-2 -2,-2z"
    )
    val Drag: ImageVector = navIcon(
        name = "Rounded.DragHandle",
        path = "M4,10.5h16v-2H4v2z M4,15.5h16v-2H4v2z"
    )
    val Swipe: ImageVector = navIcon(
        name = "Rounded.SwapHoriz",
        path = "M6.99,11L3,15l3.99,4v-3H14v-2H6.99v-3z M21,9l-3.99,-4v3H10v2h7.01v3L21,9z"
    )
    val Branch: ImageVector = navIcon(
        name = "Rounded.AccountTree",
        path = "M17,11h3c0.55,0 1,-0.45 1,-1V4c0,-0.55 -0.45,-1 -1,-1h-6c-0.55,0 -1,0.45 -1,1v2H9.83C9.42,4.84 8.31,4 7,4 5.34,4 4,5.34 4,7s1.34,3 3,3c1.31,0 2.42,-0.84 2.83,-2H13v2c0,0.55 0.45,1 1,1h1v2H9.83C9.42,11.84 8.31,11 7,11c-1.66,0 -3,1.34 -3,3s1.34,3 3,3c1.31,0 2.42,-0.84 2.83,-2H15v5c0,0.55 0.45,1 1,1h4c0.55,0 1,-0.45 1,-1v-4c0,-0.55 -0.45,-1 -1,-1h-3v-4z"
    )
    val Info: ImageVector = navIcon(
        name = "Rounded.Info",
        path = "M11,17h2v-6h-2v6z M12,2C6.48,2 2,6.48 2,12s4.48,10 10,10 10,-4.48 10,-10S17.52,2 12,2z M12,20c-4.41,0 -8,-3.59 -8,-8s3.59,-8 8,-8 8,3.59 8,8 -3.59,8 -8,8z M11,9h2V7h-2v2z"
    )
    val Terminal: ImageVector = navIcon(
        name = "Rounded.Terminal",
        path = "M20,4H4C2.9,4 2,4.9 2,6v12c0,1.1 0.9,2 2,2h16c1.1,0 2,-0.9 2,-2V6C22,4.9 21.1,4 20,4z M20,18H4V6h16V18z M18,15h-6v-1.5h6V15z M7.5,15l-1.06,-1.06L8.38,12L6.44,10.06L7.5,9l3,3L7.5,15z"
    )

    private fun navIcon(name: String, path: String): ImageVector {
        return ImageVector.Builder(
            name = name,
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).addPath(
            pathData = addPathNodes(path),
            fill = SolidColor(Color.Black)
        ).build()
    }
}

internal class SettingsBackHandlerRegistry {
    private val handlers = LinkedHashMap<Any, () -> Unit>()

    @Synchronized
    fun register(token: Any, handler: () -> Unit) {
        handlers.remove(token)
        handlers[token] = handler
    }

    @Synchronized
    fun unregister(token: Any) {
        handlers.remove(token)
    }

    fun handle(): Boolean {
        val handler = synchronized(this) { handlers.values.lastOrNull() } ?: return false
        handler()
        return true
    }

    @Synchronized
    fun clear() {
        handlers.clear()
    }
}

internal val LocalSettingsBackHandlerRegistry = staticCompositionLocalOf<SettingsBackHandlerRegistry?> { null }

object MiuixSettingsPage {
    private const val PAGE_TAG = "Hchat:MiuixSettingsPage"

    private class SettingsPageHostTag(val floatingAgent: Boolean) {
        var close: (() -> Unit)? = null
        var collapseAgent: (() -> Unit)? = null
    }

    @OptIn(ExperimentalAnimationApi::class)
    @JvmStatic
    fun show(context: Context) {
        show(context, null, floatingAgent = false)
    }

    @OptIn(ExperimentalAnimationApi::class)
    @JvmStatic
    fun showScriptPluginAgent(context: Context) {
        show(context, DetailPage.ScriptPluginAgent(), floatingAgent = false)
    }

    @JvmStatic
    fun showFeature(context: Context, featureId: String): Boolean {
        val provider = UIRegistry.get().getAllProviders()
            .firstOrNull { it.featureId() == featureId }
            ?: return false
        show(context, DetailPage.Feature(provider), floatingAgent = false)
        return true
    }

    @JvmStatic
    fun toggleFloatingScriptPluginAgent(context: Context): Boolean {
        val activity = context as? Activity ?: return false
        val decor = activity.window?.decorView as? ViewGroup ?: return false
        val existing = findExistingPage(decor)
        val tag = existing?.getTag(R.id.hchat_settings_page_host) as? SettingsPageHostTag
        if (tag?.floatingAgent == true) {
            (tag.collapseAgent ?: tag.close)?.invoke()
            return true
        }
        show(activity, DetailPage.ScriptPluginAgent(), floatingAgent = true)
        return true
    }

    @JvmStatic
    fun collapseFloatingScriptPluginAgent(context: Context): Boolean {
        val activity = context as? Activity ?: return false
        val decor = activity.window?.decorView as? ViewGroup ?: return false
        val page = findExistingPage(decor) ?: return false
        val tag = page.getTag(R.id.hchat_settings_page_host) as? SettingsPageHostTag ?: return false
        if (!tag.floatingAgent) return false
        (tag.collapseAgent ?: tag.close)?.invoke()
        return true
    }

    @OptIn(ExperimentalAnimationApi::class)
    private fun show(context: Context, initialPage: DetailPage?, floatingAgent: Boolean) {
        val activity = context as? Activity ?: return
        val decor = activity.window?.decorView as? ViewGroup ?: return
        removeExistingPage(decor)

        val hostTag = SettingsPageHostTag(floatingAgent)
        val owner = EmbeddedComposeOwner()
        val detailBackStack = navBackStackOf(SettingsNavKey(initialPage))
        fun currentDetail(): DetailPage? = (detailBackStack.lastOrNull() as? SettingsNavKey)?.page
        fun openDetail(page: DetailPage?) {
            detailBackStack.add(SettingsNavKey(page))
        }
        fun popDetail() {
            if (detailBackStack.size > 1) detailBackStack.removeLastOrNull()
        }
        val readmeDialogPlugin = mutableStateOf<ScriptPluginRuntime.ScriptPlugin?>(null)
        val configVersion = mutableStateOf(0)
        val settingsBackHandlers = SettingsBackHandlerRegistry()
        lateinit var page: FrameLayout
        val activityBackHooks = ArrayList<XC_MethodHook.Unhook>()
        var consumingBack = false
        var closed = false
        var agentExitHandler: (() -> Unit)? = null
        fun unhookActivityBack() {
            activityBackHooks.forEach {
                try {
                    it.unhook()
                } catch (_: Throwable) {
                }
            }
            activityBackHooks.clear()
        }
        fun closePage() {
            if (closed) return
            closed = true
            hostTag.close = null
            hostTag.collapseAgent = null
            agentExitHandler = null
            settingsBackHandlers.clear()
            unhookActivityBack()
            runCatching {
                page.findViewById<ComposeView>(android.R.id.content)?.disposeComposition()
            }.onFailure {
                h.Hchat.utils.HLog.e("[Hchat:MiuixSettings] 销毁页面 Composition 失败: ${it.message}", it)
            }
            runCatching {
                if (page.parent === decor) decor.removeView(page)
            }.onFailure {
                h.Hchat.utils.HLog.e("[Hchat:MiuixSettings] 移除页面失败: ${it.message}", it)
            }
            runCatching { owner.destroy() }.onFailure {
                h.Hchat.utils.HLog.e("[Hchat:MiuixSettings] 销毁页面 Owner 失败: ${it.message}", it)
            }
        }
        fun handleBack() {
            if (consumingBack) return
            consumingBack = true
            val selected = currentDetail()
            if (selected is DetailPage.ScriptPluginAgent && agentExitHandler != null) {
                agentExitHandler?.invoke()
            } else if (settingsBackHandlers.handle()) {
                // The current nested settings route owns this back event.
            } else if (selected == null) {
                closePage()
            } else {
                popDetail()
            }
            page.post { consumingBack = false }
        }
        hostTag.close = { closePage() }
        if (floatingAgent) hostTag.collapseAgent = { closePage() }
        fun installActivityBackHooks() {
            if (activityBackHooks.isNotEmpty()) return
            val keyHook = object : XC_MethodHook() {
                override fun beforeHookedMethod(param: MethodHookParam) {
                    val event = param.args.firstOrNull { it is KeyEvent } as? KeyEvent ?: return
                    if (event.keyCode != KeyEvent.KEYCODE_BACK || page.parent == null) return
                    if (event.action == KeyEvent.ACTION_UP) {
                        handleBack()
                    }
                    param.result = true
                }
            }
            val pressedHook = object : XC_MethodHook() {
                override fun beforeHookedMethod(param: MethodHookParam) {
                    if (page.parent == null) return
                    handleBack()
                    param.result = null
                }
            }
            listOf(Activity::class.java, activity.javaClass).forEach { clazz ->
                try {
                    activityBackHooks.addAll(XposedBridge.hookAllMethods(clazz, "dispatchKeyEvent", keyHook))
                } catch (_: Throwable) {
                }
                try {
                    activityBackHooks.addAll(XposedBridge.hookAllMethods(clazz, "onKeyDown", keyHook))
                } catch (_: Throwable) {
                }
                try {
                    activityBackHooks.addAll(XposedBridge.hookAllMethods(clazz, "onKeyUp", keyHook))
                } catch (_: Throwable) {
                }
                try {
                    activityBackHooks.addAll(XposedBridge.hookAllMethods(clazz, "onBackPressed", pressedHook))
                } catch (_: Throwable) {
                }
            }
        }
        page = object : FrameLayout(activity) {
            override fun dispatchKeyEvent(event: KeyEvent): Boolean {
                if (event.keyCode == KeyEvent.KEYCODE_BACK) {
                    if (event.action == KeyEvent.ACTION_UP) {
                        handleBack()
                    }
                    return true
                }
                return super.dispatchKeyEvent(event)
            }
        }.apply {
            tag = PAGE_TAG
            setTag(R.id.hchat_settings_page_host, hostTag)
            addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                override fun onViewAttachedToWindow(view: View) = Unit
                override fun onViewDetachedFromWindow(view: View) {
                    // 宿主销毁或外部移除页面也必须释放全局返回 Hook 和私有 Owner。
                    closePage()
                }
            })
            setBackgroundColor(if (isDarkMode(activity)) AndroidColor.BLACK else AndroidColor.WHITE)
            isClickable = true
            isFocusable = true
            isFocusableInTouchMode = true
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }

        owner.install(page)
        owner.attach()
        val selectedTab = mutableStateOf(MainTab.PRACTICAL)
        val termsAccepted = mutableStateOf(TermsGate.isAccepted(activity))
        val composeView = try {
            ComposeView(activity).apply {
                id = android.R.id.content
                owner.install(this)
                owner.installComposition(this)
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindow)
                setContent {
                val mainListState = rememberLazyListState()
                val featureGroupListStates = remember { mutableMapOf<FeatureGroupEntry, LazyListState>() }
                CompositionLocalProvider(
                    LocalNavigationEventDispatcherOwner provides owner,
                    LocalSettingsBackHandlerRegistry provides settingsBackHandlers
                ) {
                    HchatMiuixTheme(activity) {
                        readmeDialogPlugin.value?.let { plugin ->
                            ScriptPluginSettingsMiuixContent.ScriptPluginReadmeDialog(
                                context = activity,
                                plugin = plugin,
                                onClose = { readmeDialogPlugin.value = null }
                            )
                        }
                        if (!termsAccepted.value) {
                            ScriptPluginSettingsMiuixContent.FirstUseAgreementDialog(
                                context = activity,
                                onCancel = { closePage() },
                                onAccepted = {
                                    termsAccepted.value = true
                                    Toast.makeText(activity, "已同意协议，重启微信后完整启用模块功能", Toast.LENGTH_LONG).show()
                                }
                            )
                        }
                        NavDisplay(
                            backStack = detailBackStack,
                            modifier = Modifier.fillMaxSize().background(MiuixTheme.colorScheme.background),
                            transition = NavTransitions.MiuixDefault,
                            onBack = {
                                if (detailBackStack.size > 1) popDetail() else closePage()
                            }
                        ) {
                            entry<SettingsNavKey> { route ->
                                val targetPage = route.page
                            if (targetPage == null) {
                                MainSettingsPage(
                                    context = activity,
                                    providers = UIRegistry.get().getAllProviders(),
                                    listState = mainListState,
                                    selectedTab = selectedTab.value,
                                    configVersion = configVersion.value,
                                    onSelectedTab = { selectedTab.value = it },
                                    onOpenSearch = { openDetail(DetailPage.Search) },
                                    onOpenProvider = { openDetail(DetailPage.Feature(it)) },
                                    onOpenGroup = { openDetail(DetailPage.FeatureGroup(it)) },
                                    onOpenScriptPluginReadme = { readmeDialogPlugin.value = it },
                                    onOpenScriptPluginAgent = { openDetail(DetailPage.ScriptPluginAgent()) },
                                    onOpenScriptPluginMarket = { openDetail(DetailPage.ScriptPluginMarket()) },
                                    onOpenScriptPluginManager = { openDetail(DetailPage.ScriptPluginManager()) },
                                    onConfigImported = { configVersion.value += 1 }
                                )
                            } else if (targetPage is DetailPage.Search) {
                                SettingsSearchPage(
                                    context = activity,
                                    providers = UIRegistry.get().getAllProviders(),
                                    onBack = { popDetail() },
                                    onOpenProvider = { provider, group ->
                                        openDetail(DetailPage.Feature(
                                            provider = provider,
                                            sourceGroup = group,
                                            returnToSearch = true
                                        ))
                                    },
                                    onOpenScriptPluginReadme = { readmeDialogPlugin.value = it }
                                )
                            } else if (targetPage is DetailPage.FeatureGroup) {
                                FeatureGroupSettingsPage(
                                    group = targetPage.group,
                                    listState = featureGroupListStates.getOrPut(targetPage.group) { LazyListState() },
                                    onBack = { popDetail() },
                                    onOpenProvider = {
                                        openDetail(DetailPage.Feature(it, targetPage.group))
                                    }
                                )
                            } else if (targetPage is DetailPage.Feature) {
                                FeatureSettingsPage(
                                    context = activity,
                                    provider = targetPage.provider,
                                    onBack = {
                                        popDetail()
                                    },
                                    onOpenScriptPluginAgent = {
                                        openDetail(DetailPage.ScriptPluginAgent(targetPage))
                                    },
                                    onOpenScriptPluginMarket = {
                                        openDetail(DetailPage.ScriptPluginMarket(targetPage))
                                    },
                                    onOpenScriptPluginManager = {
                                        openDetail(DetailPage.ScriptPluginManager(targetPage))
                                    }
                                )
                            } else if (targetPage is DetailPage.ScriptPluginAgent) {
                                ScriptPluginAgentUi.ScriptPluginAgentWorkspacePage(
                                    context = activity,
                                    onBack = {
                                        if (floatingAgent) closePage()
                                        else popDetail()
                                    },
                                    onExitHandlerChanged = { handler ->
                                        agentExitHandler = handler
                                        if (floatingAgent) {
                                            hostTag.collapseAgent = handler ?: { closePage() }
                                        }
                                    }
                                )
                            } else if (targetPage is DetailPage.ScriptPluginMarket) {
                                PluginMarketUi.ScriptPluginMarketPage(
                                    context = activity,
                                    onBack = { popDetail() }
                                )
                            } else if (targetPage is DetailPage.ScriptPluginManager) {
                                ScriptPluginSettingsMiuixContent.ScriptPluginManagerPage(
                                    context = activity,
                                    onBack = { popDetail() }
                                )
                            }
                            }
                        }
                    }
                }
                }
            }
        } catch (throwable: Throwable) {
            closePage()
            h.Hchat.utils.HLog.e(
                "[Hchat:MiuixSettings] 创建页面 ComposeView 失败: ${throwable.message}",
                throwable
            )
            return
        }

        try {
            page.addView(
                composeView,
                FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
            decor.addView(page)
            composeView.createComposition()
        } catch (throwable: Throwable) {
            closePage()
            h.Hchat.utils.HLog.e(
                "[Hchat:MiuixSettings] 创建页面 Composition 失败: ${throwable.message}",
                throwable
            )
            return
        }
        installActivityBackHooks()
        page.requestFocus()
    }

    private fun findExistingPage(decor: ViewGroup): View? {
        for (index in decor.childCount - 1 downTo 0) {
            val child = decor.getChildAt(index)
            if (child.tag == PAGE_TAG) return child
        }
        return null
    }

    private fun removeExistingPage(decor: ViewGroup) {
        val pages = (decor.childCount - 1 downTo 0)
            .map { decor.getChildAt(it) }
            .filter { it.tag == PAGE_TAG }
        pages.forEach { child ->
            val close = (child.getTag(R.id.hchat_settings_page_host) as? SettingsPageHostTag)?.close
            if (close != null) close() else decor.removeView(child)
        }
    }
}

internal object AvatarMemoryCache {
    private const val MAX_ENTRIES = 256
    private const val MAX_BYTES = 8L * 1024 * 1024
    private data class Entry(val bitmap: ImageBitmap, val bytes: Long)
    private val bitmaps = LinkedHashMap<String, Entry>(16, 0.75f, true)
    private var totalBytes = 0L

    @Synchronized
    fun cached(value: String): Pair<Boolean, ImageBitmap?> {
        val entry = bitmaps[value] ?: return false to null
        return true to entry.bitmap
    }

    @Synchronized
    fun put(value: String, bitmap: ImageBitmap?) {
        if (bitmap == null) return
        val bytes = bitmap.asAndroidBitmap().allocationByteCount.toLong()
        bitmaps.remove(value)?.let { totalBytes -= it.bytes }
        if (bytes <= 0L || bytes > MAX_BYTES) return
        bitmaps[value] = Entry(bitmap, bytes)
        totalBytes += bytes
        val oldest = bitmaps.entries.iterator()
        while ((bitmaps.size > MAX_ENTRIES || totalBytes > MAX_BYTES) && oldest.hasNext()) {
            totalBytes -= oldest.next().value.bytes
            oldest.remove()
        }
        // 缓存淘汰只释放引用；Compose 中仍显示的头像不能主动 recycle。
    }
}

internal object WeChatAvatarPathCache {
    @Volatile
    private var avatarRoot: String? = null

    fun resolveAvatarRoot(): String? {
        avatarRoot?.let { return it }
        val api = WeChatApis.database() ?: return null
        val rows = try {
            api.query("PRAGMA database_list", null)
        } catch (_: Throwable) {
            emptyList()
        }
        for (row in rows) {
            val filePath = row["file"]?.toString().orEmpty()
            if (filePath.isBlank()) continue
            val dbFile = File(filePath)
            val parent = dbFile.parentFile ?: continue
            val avatarDir = File(parent, "avatar")
            if (avatarDir.isDirectory || parent.isDirectory) {
                val path = avatarDir.absolutePath
                avatarRoot = path
                return path
            }
        }
        return null
    }
}

internal object NativeFakeLocationPickerBridge {
    private val hookedClasses = HashSet<Class<*>>()
    private var pending: PendingResult? = null

    @Synchronized
    fun launch(activity: Activity, onResult: (Double, Double) -> Unit) {
        pending = PendingResult(activity, onResult)
        hookActivityResult(activity.javaClass)
        hookActivityResult(Activity::class.java)
        val intent = Intent().apply {
            setClassName(activity.packageName, "${activity.packageName}.plugin.location.ui.RedirectUI")
            putExtra("map_view_type", 8)
        }
        runCatching {
            activity.startActivityForResult(intent, FAKE_LOCATION_WECHAT_PICKER_REQUEST_CODE)
        }.onFailure {
            clearPending()
            Toast.makeText(activity, "启动微信地图失败: ${it.message.orEmpty()}", Toast.LENGTH_SHORT).show()
        }
    }

    @Synchronized
    private fun hookActivityResult(clazz: Class<*>) {
        if (hookedClasses.contains(clazz)) return
        runCatching {
            XposedBridge.hookAllMethods(clazz, "onActivityResult", object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    val requestCode = param.args.getOrNull(0) as? Int ?: return
                    if (requestCode != FAKE_LOCATION_WECHAT_PICKER_REQUEST_CODE) return
                    val result = takePending() ?: return
                    val resultCode = param.args.getOrNull(1) as? Int ?: return
                    val data = param.args.getOrNull(2) as? Intent
                    if (resultCode != Activity.RESULT_OK || data == null) return
                    val location = extractLocation(data)
                    if (location == null) {
                        Toast.makeText(result.activity, "解析微信地图选点失败", Toast.LENGTH_SHORT).show()
                    } else {
                        result.callback(location.first, location.second)
                    }
                }
            })
        }.onSuccess { unhooks ->
            if (unhooks.isNotEmpty()) hookedClasses.add(clazz)
        }.onFailure {
            h.Hchat.utils.HLog.e("[Hchat:FakeLocation] 地图选点结果 Hook 安装失败: ${clazz.name}", it)
        }
    }

    @Suppress("DEPRECATION")
    private fun extractLocation(data: Intent): Pair<Double, Double>? {
        val latitude = data.getDoubleExtra("kwebmap_slat", Double.NaN)
        val longitude = data.getDoubleExtra("kwebmap_lng", Double.NaN)
        validFakeLocation(latitude, longitude)?.let { return it }

        val locationIntent = runCatching {
            data.getParcelableExtra<Parcelable>("KLocationIntent")
        }.getOrNull() ?: return null
        val fieldLatitude = (KavaReflector.readField(locationIntent, "d") as? Number)?.toDouble()
        val fieldLongitude = (KavaReflector.readField(locationIntent, "e") as? Number)?.toDouble()
        if (fieldLatitude != null && fieldLongitude != null) {
            validFakeLocation(fieldLatitude, fieldLongitude)?.let { return it }
        }
        val locationData = KavaReflector.declaredMethods(locationIntent.javaClass)
            .asSequence()
            .filter { it.parameterTypes.isEmpty() && it.returnType == String::class.java }
            .mapNotNull { method -> KavaReflector.invoke(method, locationIntent) as? String }
            .firstOrNull { it.contains("lat", ignoreCase = true) && it.contains("lng", ignoreCase = true) }
            ?: locationIntent.toString()
        return parseNativeFakeLocation(locationData)
    }

    @Synchronized
    private fun takePending(): PendingResult? {
        val result = pending
        pending = null
        return result
    }

    @Synchronized
    private fun clearPending() {
        pending = null
    }

    private data class PendingResult(
        val activity: Activity,
        val callback: (Double, Double) -> Unit
    )
}

internal object RingtonePickerBridge {
    private val hookedClasses = HashSet<Class<*>>()
    private var onPicked: ((String) -> Unit)? = null
    private var currentActivity: Activity? = null

    @Synchronized
    fun launchSystem(activity: Activity, currentUri: String, onResult: (String) -> Unit) {
        onPicked = onResult
        currentActivity = activity
        hookActivityResult(activity.javaClass)
        hookActivityResult(Activity::class.java)
        val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER).apply {
            putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION)
            putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "选择通知铃声")
            putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true)
            putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, true)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            if (currentUri.isNotBlank()) {
                putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, Uri.parse(currentUri))
            }
        }
        activity.startActivityForResult(intent, RINGTONE_SYSTEM_REQUEST_CODE)
    }

    @Synchronized
    fun launchFile(activity: Activity, onResult: (String) -> Unit) {
        onPicked = onResult
        currentActivity = activity
        hookActivityResult(activity.javaClass)
        hookActivityResult(Activity::class.java)
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        }.preferSystemDocumentsUi(activity)
        try {
            activity.startActivityForResult(intent, RINGTONE_FILE_REQUEST_CODE)
        } catch (_: Throwable) {
            val fallback = Intent(Intent.ACTION_GET_CONTENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "*/*"
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            activity.startActivityForResult(Intent.createChooser(fallback, "选择铃声文件"), RINGTONE_FILE_REQUEST_CODE)
        }
    }

    @Synchronized
    private fun hookActivityResult(clazz: Class<*>) {
        if (hookedClasses.contains(clazz)) return
        try {
            XposedBridge.hookAllMethods(clazz, "onActivityResult", object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    val requestCode = param.args.getOrNull(0) as? Int ?: return
                    if (requestCode != RINGTONE_SYSTEM_REQUEST_CODE && requestCode != RINGTONE_FILE_REQUEST_CODE) return
                    val resultCode = param.args.getOrNull(1) as? Int ?: return
                    if (resultCode != Activity.RESULT_OK) return
                    val data = param.args.getOrNull(2) as? Intent ?: return
                    val picked = extractPickedUri(data, requestCode)
                    takeReadPermission(data, picked)
                    onPicked?.invoke(picked?.toString().orEmpty())
                }
            })
            hookedClasses.add(clazz)
        } catch (_: Throwable) {
        }
    }

    private fun extractPickedUri(data: Intent, requestCode: Int): Uri? {
        if (requestCode == RINGTONE_SYSTEM_REQUEST_CODE) {
            @Suppress("DEPRECATION")
            (data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI) as? Uri)?.let { return it }
        }
        data.data?.let { return it }
        @Suppress("DEPRECATION")
        (data.getParcelableExtra(Intent.EXTRA_STREAM) as? Uri)?.let { return it }
        firstUriInClipData(data.clipData)?.let { return it }
        return firstUriInBundle(data.extras)
    }

    private fun firstUriInClipData(clipData: ClipData?): Uri? {
        if (clipData == null || clipData.itemCount <= 0) return null
        return try {
            clipData.getItemAt(0)?.uri
        } catch (_: Throwable) {
            null
        }
    }

    private fun firstUriInBundle(bundle: Bundle?): Uri? {
        if (bundle == null) return null
        return try {
            bundle.keySet().firstNotNullOfOrNull { key ->
                when (val value = bundle.get(key)) {
                    is Uri -> value
                    is Intent -> value.data ?: firstUriInBundle(value.extras)
                    is Bundle -> firstUriInBundle(value)
                    else -> null
                }
            }
        } catch (_: Throwable) {
            null
        }
    }

    private fun takeReadPermission(data: Intent, uri: Uri?) {
        if (uri == null || uri.scheme != "content") return
        try {
            val flags = data.flags and
                (Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
            if ((flags and Intent.FLAG_GRANT_READ_URI_PERMISSION) == 0) return
            currentActivity?.contentResolver
                ?.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        } catch (_: Throwable) {
        }
    }
}

internal object ScriptPluginAgentAttachmentPickerBridge {
    private val hookedClasses = HashSet<Class<*>>()
    private var pending: PendingResult? = null

    @Synchronized
    fun launch(
        activity: Activity,
        directory: File,
        onResult: (List<ScriptPluginAgentAttachment>) -> Unit
    ) {
        pending = PendingResult(activity, directory.apply { mkdirs() }, onResult)
        hookActivityResult(activity.javaClass)
        hookActivityResult(Activity::class.java)
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        }.preferSystemDocumentsUi(activity)
        try {
            activity.startActivityForResult(intent, SCRIPT_AGENT_ATTACHMENT_REQUEST_CODE)
        } catch (_: Throwable) {
            val fallback = Intent(Intent.ACTION_GET_CONTENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "*/*"
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            activity.startActivityForResult(
                Intent.createChooser(fallback, "选择文件或图片"),
                SCRIPT_AGENT_ATTACHMENT_REQUEST_CODE
            )
        }
    }

    @Synchronized
    private fun hookActivityResult(clazz: Class<*>) {
        if (hookedClasses.contains(clazz)) return
        try {
            XposedBridge.hookAllMethods(clazz, "onActivityResult", object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    val requestCode = param.args.getOrNull(0) as? Int ?: return
                    if (requestCode != SCRIPT_AGENT_ATTACHMENT_REQUEST_CODE) return
                    val pending = takePending() ?: return
                    val resultCode = param.args.getOrNull(1) as? Int ?: return
                    if (resultCode != Activity.RESULT_OK) return
                    val data = param.args.getOrNull(2) as? Intent ?: return
                    val activity = pending.activity
                    val directory = pending.directory
                    val uris = pickedUris(data)
                    val persistentUris = uris.filter { uri ->
                        takeReadPermission(activity, data, uri)
                    }.mapTo(HashSet()) { it.toString() }
                    Thread({
                        val attachments = uris.mapNotNull { uri ->
                            copyAttachment(
                                activity,
                                directory,
                                uri,
                                uri.toString() in persistentUris
                            )
                        }.distinctBy { it.sourceUri.ifBlank { it.path } }
                        if (attachments.isNotEmpty()) {
                            Handler(Looper.getMainLooper()).post { pending.callback(attachments) }
                        }
                    }, "Hchat-Agent-Attachment-Copy").start()
                }
            })
            hookedClasses.add(clazz)
        } catch (_: Throwable) {
        }
    }

    private fun pickedUris(data: Intent): List<Uri> {
        val result = ArrayList<Uri>()
        data.data?.let(result::add)
        val clip = data.clipData
        if (clip != null) {
            for (index in 0 until clip.itemCount) {
                runCatching { clip.getItemAt(index)?.uri }.getOrNull()?.let(result::add)
            }
        }
        return result.distinct()
    }

    private fun takeReadPermission(activity: Activity, data: Intent, uri: Uri): Boolean {
        if (uri.scheme != "content") return false
        val resolver = activity.contentResolver
        if (runCatching {
                resolver.persistedUriPermissions.any { it.uri == uri && it.isReadPermission }
            }.getOrDefault(false)
        ) {
            return true
        }
        val flags = data.flags and
            (Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        if ((flags and Intent.FLAG_GRANT_READ_URI_PERMISSION) == 0 ||
            (flags and Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION) == 0
        ) {
            return false
        }
        return runCatching {
            resolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            resolver.persistedUriPermissions.any { it.uri == uri && it.isReadPermission }
        }.getOrDefault(false)
    }

    private fun copyAttachment(
        context: Context,
        directory: File,
        uri: Uri,
        persistentAccess: Boolean
    ): ScriptPluginAgentAttachment? {
        var target: File? = null
        return runCatching {
            val displayName = queryDisplayName(context, uri)
                ?: uri.lastPathSegment
                ?: "attachment"
            val safeName = displayName.replace(Regex("""[\\/:*?"<>|]"""), "_").ifBlank { "attachment" }
            val copyId = java.util.UUID.randomUUID().toString().substring(0, 8)
            val outputFile = File(directory, "${System.currentTimeMillis()}_${copyId}_$safeName")
            target = outputFile
            context.contentResolver.openInputStream(uri)?.use { input ->
                outputFile.outputStream().use { output ->
                    val buffer = ByteArray(32 * 1024)
                    while (true) {
                        val count = input.read(buffer)
                        if (count < 0) break
                        output.write(buffer, 0, count)
                    }
                }
            } ?: return null
            val attachment = ScriptPluginAgentAttachment(
                name = displayName,
                path = outputFile.absolutePath,
                mimeType = context.contentResolver.getType(uri).orEmpty().ifBlank { "application/octet-stream" },
                size = outputFile.length(),
                sourceUri = uri.toString().takeIf { persistentAccess }.orEmpty()
            )
            attachment.takeIf { outputFile.isFile && outputFile.length() > 0L }
                ?: run {
                    outputFile.delete()
                    null
                }
        }.getOrElse {
            target?.delete()
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, it.message ?: "读取附件失败", Toast.LENGTH_SHORT).show()
            }
            null
        }
    }

    @Synchronized
    private fun takePending(): PendingResult? {
        val result = pending
        pending = null
        return result
    }

    private data class PendingResult(
        val activity: Activity,
        val directory: File,
        val callback: (List<ScriptPluginAgentAttachment>) -> Unit
    )

}

internal object PluginMarketExtraFilePickerBridge {
    private val hookedClasses = HashSet<Class<*>>()
    private var pending: PendingResult? = null

    @Synchronized
    fun launch(activity: Activity, onResult: (List<PluginMarketFile>) -> Unit) {
        pending = PendingResult(activity, onResult)
        hookActivityResult(activity.javaClass)
        hookActivityResult(Activity::class.java)
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        }.preferSystemDocumentsUi(activity)
        try {
            activity.startActivityForResult(intent, PLUGIN_MARKET_EXTRA_FILE_REQUEST_CODE)
        } catch (_: Throwable) {
            val fallback = Intent(Intent.ACTION_GET_CONTENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "*/*"
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            activity.startActivityForResult(
                Intent.createChooser(fallback, "选择插件附加文件"),
                PLUGIN_MARKET_EXTRA_FILE_REQUEST_CODE
            )
        }
    }

    @Synchronized
    private fun hookActivityResult(clazz: Class<*>) {
        if (!hookedClasses.add(clazz)) return
        runCatching {
            XposedBridge.hookAllMethods(clazz, "onActivityResult", object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    val requestCode = param.args.getOrNull(0) as? Int ?: return
                    if (requestCode != PLUGIN_MARKET_EXTRA_FILE_REQUEST_CODE) return
                    val result = takePending() ?: return
                    val resultCode = param.args.getOrNull(1) as? Int ?: return
                    if (resultCode != Activity.RESULT_OK) return
                    val data = param.args.getOrNull(2) as? Intent ?: return
                    val uris = pickedUris(data)
                    if (uris.isEmpty()) return
                    Thread({
                        val files = uris.mapNotNull { uri -> readFile(result.activity, uri) }
                            .distinctBy { it.name.lowercase(Locale.ROOT) }
                        if (files.isNotEmpty()) {
                            Handler(Looper.getMainLooper()).post { result.callback(files) }
                        }
                    }, "Hchat-Plugin-Market-Files").start()
                }
            })
        }.onFailure {
            hookedClasses.remove(clazz)
        }
    }

    private fun pickedUris(data: Intent): List<Uri> {
        val result = ArrayList<Uri>()
        data.data?.let(result::add)
        data.clipData?.let { clip ->
            for (index in 0 until clip.itemCount) {
                runCatching { clip.getItemAt(index)?.uri }.getOrNull()?.let(result::add)
            }
        }
        return result.distinct()
    }

    private fun readFile(activity: Activity, uri: Uri): PluginMarketFile? {
        return runCatching {
            val displayName = queryDisplayName(activity, uri)
                ?.substringAfterLast('/')
                ?.substringAfterLast('\\')
                ?.ifBlank { null }
                ?: uri.lastPathSegment?.substringAfterLast('/').orEmpty()
            require(displayName.isNotBlank()) { "无法读取所选文件名" }
            val bytes = java.io.ByteArrayOutputStream().use { output ->
                activity.contentResolver.openInputStream(uri)?.use { input ->
                    val buffer = ByteArray(32 * 1024)
                    var total = 0L
                    while (true) {
                        val count = input.read(buffer)
                        if (count < 0) break
                        total += count
                        require(total <= PluginMarketInstaller.MAX_EXTRA_FILE_BYTES) {
                            "$displayName 超过 ${PluginMarketInstaller.MAX_EXTRA_FILE_BYTES / 1024} KiB"
                        }
                        output.write(buffer, 0, count)
                    }
                } ?: error("无法打开所选文件")
                output.toByteArray()
            }
            PluginMarketInstaller.createExternalFile(displayName, bytes)
        }.getOrElse {
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(activity, it.message ?: "读取附加文件失败", Toast.LENGTH_SHORT).show()
            }
            null
        }
    }

    @Synchronized
    private fun takePending(): PendingResult? {
        val result = pending
        pending = null
        return result
    }

    private data class PendingResult(
        val activity: Activity,
        val callback: (List<PluginMarketFile>) -> Unit
    )
}

internal object RedPacketReplyFilePickerBridge {
    private val hookedClasses = HashSet<Class<*>>()
    private var onPicked: ((List<String>) -> Unit)? = null
    private var currentActivity: Activity? = null

    @Synchronized
    fun launch(activity: Activity, replyMode: Int, onResult: (List<String>) -> Unit) {
        onPicked = onResult
        currentActivity = activity
        hookActivityResult(activity.javaClass)
        hookActivityResult(Activity::class.java)
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        }.preferSystemDocumentsUi(activity)
        try {
            activity.startActivityForResult(intent, REDPACKET_REPLY_FILE_REQUEST_CODE)
        } catch (_: Throwable) {
            val fallback = Intent(Intent.ACTION_GET_CONTENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "*/*"
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            activity.startActivityForResult(
                Intent.createChooser(fallback, redPacketReplyPickerTitle(replyMode)),
                REDPACKET_REPLY_FILE_REQUEST_CODE
            )
        }
    }

    @Synchronized
    private fun hookActivityResult(clazz: Class<*>) {
        if (hookedClasses.contains(clazz)) return
        try {
            XposedBridge.hookAllMethods(clazz, "onActivityResult", object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    val requestCode = param.args.getOrNull(0) as? Int ?: return
                    if (requestCode != REDPACKET_REPLY_FILE_REQUEST_CODE) return
                    val resultCode = param.args.getOrNull(1) as? Int ?: return
                    if (resultCode != Activity.RESULT_OK) return
                    val data = param.args.getOrNull(2) as? Intent ?: return
                    val activity = currentActivity ?: return
                    val paths = pickedUris(data)
                        .mapNotNull { uri ->
                            takeReadPermission(activity, data, uri)
                            copyReplyFileToCache(activity, uri)
                        }
                        .distinct()
                    if (paths.isNotEmpty()) onPicked?.invoke(paths)
                }
            })
            hookedClasses.add(clazz)
        } catch (_: Throwable) {
        }
    }

    private fun pickedUris(data: Intent): List<Uri> {
        val result = ArrayList<Uri>()
        data.data?.let { result.add(it) }
        val clip = data.clipData
        if (clip != null) {
            for (i in 0 until clip.itemCount) {
                try {
                    clip.getItemAt(i)?.uri?.let { result.add(it) }
                } catch (_: Throwable) {
                }
            }
        }
        @Suppress("DEPRECATION")
        (data.getParcelableExtra(Intent.EXTRA_STREAM) as? Uri)?.let { result.add(it) }
        return result.distinct()
    }

    private fun takeReadPermission(activity: Activity, data: Intent, uri: Uri) {
        if (uri.scheme != "content") return
        try {
            val flags = data.flags and
                (Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
            if ((flags and Intent.FLAG_GRANT_READ_URI_PERMISSION) == 0) return
            activity.contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        } catch (_: Throwable) {
        }
    }

    private fun copyReplyFileToCache(context: Context, uri: Uri): String? {
        if ("file".equals(uri.scheme, ignoreCase = true)) {
            val path = uri.path.orEmpty()
            if (path.isNotBlank() && File(path).isFile) return path
        }
        return try {
            val name = safeReplyFileName(queryDisplayName(context, uri) ?: uri.lastPathSegment ?: "reply_file")
            val dir = File(context.filesDir, "Hchat/redpacket_reply").apply { mkdirs() }
            val target = File(dir, "${System.currentTimeMillis()}_$name")
            context.contentResolver.openInputStream(uri)?.use { input ->
                target.outputStream().use { output -> input.copyTo(output) }
            } ?: return null
            target.absolutePath.takeIf { target.isFile && target.length() > 0L }
        } catch (_: Throwable) {
            null
        }
    }

    private fun safeReplyFileName(value: String): String {
        return value.replace(Regex("""[\\/:*?"<>|]"""), "_")
            .trim()
            .ifBlank { "reply_file" }
    }
}

internal object AutoReplyFilePickerBridge {
    private val hookedClasses = HashSet<Class<*>>()
    private var onPicked: ((List<String>) -> Unit)? = null
    private var currentActivity: Activity? = null

    @Synchronized
    fun launch(activity: Activity, replyMode: Int, onResult: (List<String>) -> Unit) {
        onPicked = onResult
        currentActivity = activity
        hookActivityResult(activity.javaClass)
        hookActivityResult(Activity::class.java)
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        }.preferSystemDocumentsUi(activity)
        try {
            activity.startActivityForResult(intent, AUTO_REPLY_FILE_REQUEST_CODE)
        } catch (_: Throwable) {
            val fallback = Intent(Intent.ACTION_GET_CONTENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "*/*"
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            activity.startActivityForResult(
                Intent.createChooser(fallback, autoReplyPickerTitle(replyMode)),
                AUTO_REPLY_FILE_REQUEST_CODE
            )
        }
    }

    @Synchronized
    private fun hookActivityResult(clazz: Class<*>) {
        if (hookedClasses.contains(clazz)) return
        try {
            XposedBridge.hookAllMethods(clazz, "onActivityResult", object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    val requestCode = param.args.getOrNull(0) as? Int ?: return
                    if (requestCode != AUTO_REPLY_FILE_REQUEST_CODE) return
                    val resultCode = param.args.getOrNull(1) as? Int ?: return
                    if (resultCode != Activity.RESULT_OK) return
                    val data = param.args.getOrNull(2) as? Intent ?: return
                    val activity = currentActivity ?: return
                    val paths = pickedUris(data)
                        .mapNotNull { uri ->
                            takeReadPermission(activity, data, uri)
                            copyReplyFileToCache(activity, uri)
                        }
                        .distinct()
                    if (paths.isNotEmpty()) onPicked?.invoke(paths)
                }
            })
            hookedClasses.add(clazz)
        } catch (_: Throwable) {
        }
    }

    private fun pickedUris(data: Intent): List<Uri> {
        val result = ArrayList<Uri>()
        data.data?.let { result.add(it) }
        val clip = data.clipData
        if (clip != null) {
            for (i in 0 until clip.itemCount) {
                try {
                    clip.getItemAt(i)?.uri?.let { result.add(it) }
                } catch (_: Throwable) {
                }
            }
        }
        @Suppress("DEPRECATION")
        (data.getParcelableExtra(Intent.EXTRA_STREAM) as? Uri)?.let { result.add(it) }
        return result.distinct()
    }

    private fun takeReadPermission(activity: Activity, data: Intent, uri: Uri) {
        if (uri.scheme != "content") return
        try {
            val flags = data.flags and
                (Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
            if ((flags and Intent.FLAG_GRANT_READ_URI_PERMISSION) == 0) return
            activity.contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        } catch (_: Throwable) {
        }
    }

    private fun copyReplyFileToCache(context: Context, uri: Uri): String? {
        if ("file".equals(uri.scheme, ignoreCase = true)) {
            val path = uri.path.orEmpty()
            if (path.isNotBlank() && File(path).isFile) return path
        }
        return try {
            val name = safeReplyFileName(queryDisplayName(context, uri) ?: uri.lastPathSegment ?: "reply_file")
            val dir = File(context.filesDir, "Hchat/auto_reply").apply { mkdirs() }
            val target = File(dir, "${System.currentTimeMillis()}_$name")
            context.contentResolver.openInputStream(uri)?.use { input ->
                target.outputStream().use { output -> input.copyTo(output) }
            } ?: return null
            target.absolutePath.takeIf { target.isFile && target.length() > 0L }
        } catch (_: Throwable) {
            null
        }
    }

    private fun safeReplyFileName(value: String): String {
        return value.replace(Regex("""[\\/:*?"<>|]"""), "_")
            .trim()
            .ifBlank { "reply_file" }
    }
}

internal object ScheduledTaskFilePickerBridge {
    private val hookedClasses = HashSet<Class<*>>()
    private var onPicked: ((List<String>) -> Unit)? = null
    private var currentActivity: Activity? = null

    @Synchronized
    fun launch(activity: Activity, taskType: Int, onResult: (List<String>) -> Unit) {
        onPicked = onResult
        currentActivity = activity
        hookActivityResult(activity.javaClass)
        hookActivityResult(Activity::class.java)
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        }.preferSystemDocumentsUi(activity)
        try {
            activity.startActivityForResult(intent, SCHEDULED_TASK_FILE_REQUEST_CODE)
        } catch (_: Throwable) {
            val fallback = Intent(Intent.ACTION_GET_CONTENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "*/*"
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            activity.startActivityForResult(
                Intent.createChooser(fallback, scheduledTaskPickerTitle(taskType)),
                SCHEDULED_TASK_FILE_REQUEST_CODE
            )
        }
    }

    @Synchronized
    private fun hookActivityResult(clazz: Class<*>) {
        if (hookedClasses.contains(clazz)) return
        try {
            XposedBridge.hookAllMethods(clazz, "onActivityResult", object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    val requestCode = param.args.getOrNull(0) as? Int ?: return
                    if (requestCode != SCHEDULED_TASK_FILE_REQUEST_CODE) return
                    val resultCode = param.args.getOrNull(1) as? Int ?: return
                    if (resultCode != Activity.RESULT_OK) return
                    val data = param.args.getOrNull(2) as? Intent ?: return
                    val activity = currentActivity ?: return
                    val paths = scheduledTaskPickedUris(data)
                        .mapNotNull { uri ->
                            scheduledTaskTakeReadPermission(activity, data, uri)
                            copyScheduledTaskFileToCache(activity, uri)
                        }
                        .distinct()
                    if (paths.isNotEmpty()) onPicked?.invoke(paths)
                }
            })
            hookedClasses.add(clazz)
        } catch (_: Throwable) {
        }
    }

    private fun scheduledTaskPickedUris(data: Intent): List<Uri> {
        val result = ArrayList<Uri>()
        data.data?.let { result.add(it) }
        data.clipData?.let { clip ->
            for (index in 0 until clip.itemCount) {
                try {
                    clip.getItemAt(index)?.uri?.let { result.add(it) }
                } catch (_: Throwable) {
                }
            }
        }
        @Suppress("DEPRECATION")
        (data.getParcelableExtra(Intent.EXTRA_STREAM) as? Uri)?.let { result.add(it) }
        return result.distinct()
    }

    private fun scheduledTaskTakeReadPermission(activity: Activity, data: Intent, uri: Uri) {
        if (uri.scheme != "content") return
        try {
            val flags = data.flags and
                (Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
            if ((flags and Intent.FLAG_GRANT_READ_URI_PERMISSION) == 0) return
            activity.contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        } catch (_: Throwable) {
        }
    }

    private fun copyScheduledTaskFileToCache(context: Context, uri: Uri): String? {
        if ("file".equals(uri.scheme, ignoreCase = true)) {
            val path = uri.path.orEmpty()
            if (path.isNotBlank() && File(path).isFile) return path
        }
        return try {
            val name = safeScheduledTaskFileName(queryDisplayName(context, uri) ?: uri.lastPathSegment ?: "scheduled_task_file")
            val dir = File(context.filesDir, "Hchat/scheduled_task").apply { mkdirs() }
            val target = File(dir, "${System.currentTimeMillis()}_$name")
            context.contentResolver.openInputStream(uri)?.use { input ->
                target.outputStream().use { output -> input.copyTo(output) }
            } ?: return null
            target.absolutePath.takeIf { target.isFile && target.length() > 0L }
        } catch (_: Throwable) {
            null
        }
    }

    private fun safeScheduledTaskFileName(value: String): String {
        return value.replace(Regex("""[\\/:*?"<>|]"""), "_")
            .trim()
            .ifBlank { "scheduled_task_file" }
    }
}

internal object AudioTransformDocumentBridge {
    private val hookedClasses = HashSet<Class<*>>()
    private var onInputPicked: ((String, String) -> Unit)? = null
    private var onOutputPicked: ((Uri, String) -> Unit)? = null
    private var currentActivity: Activity? = null

    @Synchronized
    fun launchInput(activity: Activity, onResult: (String, String) -> Unit) {
        onInputPicked = onResult
        currentActivity = activity
        hookActivityResult(activity.javaClass)
        hookActivityResult(Activity::class.java)
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        }.preferSystemDocumentsUi(activity)
        try {
            activity.startActivityForResult(intent, AUDIO_TRANSFORM_INPUT_REQUEST_CODE)
        } catch (_: Throwable) {
            val fallback = Intent(Intent.ACTION_GET_CONTENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "*/*"
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            activity.startActivityForResult(
                Intent.createChooser(fallback, "选择音频文件"),
                AUDIO_TRANSFORM_INPUT_REQUEST_CODE
            )
        }
    }

    @Synchronized
    fun launchOutput(
        activity: Activity,
        suggestedFileName: String,
        mimeType: String,
        onResult: (Uri, String) -> Unit
    ) {
        onOutputPicked = onResult
        currentActivity = activity
        hookActivityResult(activity.javaClass)
        hookActivityResult(Activity::class.java)
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = mimeType
            putExtra(Intent.EXTRA_TITLE, suggestedFileName)
            addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }.preferSystemDocumentsUi(activity)
        try {
            activity.startActivityForResult(intent, AUDIO_TRANSFORM_OUTPUT_REQUEST_CODE)
        } catch (_: Throwable) {
            Toast.makeText(activity, "当前系统不支持创建输出文件", Toast.LENGTH_SHORT).show()
        }
    }

    @Synchronized
    private fun hookActivityResult(clazz: Class<*>) {
        if (hookedClasses.contains(clazz)) return
        try {
            XposedBridge.hookAllMethods(clazz, "onActivityResult", object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    val requestCode = param.args.getOrNull(0) as? Int ?: return
                    if (requestCode != AUDIO_TRANSFORM_INPUT_REQUEST_CODE &&
                        requestCode != AUDIO_TRANSFORM_OUTPUT_REQUEST_CODE
                    ) return
                    val resultCode = param.args.getOrNull(1) as? Int ?: return
                    if (resultCode != Activity.RESULT_OK) return
                    val data = param.args.getOrNull(2) as? Intent ?: return
                    val activity = currentActivity ?: return
                    val uri = data.data ?: return
                    if (requestCode == AUDIO_TRANSFORM_INPUT_REQUEST_CODE) {
                        takeReadPermission(activity, data, uri)
                        val copied = copyInputFileToCache(activity, uri) ?: return
                        onInputPicked?.invoke(copied.first, copied.second)
                    } else {
                        val displayName = queryDisplayName(activity, uri)
                            ?: uri.lastPathSegment
                            ?.substringAfterLast('/')
                            ?.substringAfterLast(':')
                            .orEmpty()
                        onOutputPicked?.invoke(uri, displayName.ifBlank { "output" })
                    }
                }
            })
            hookedClasses.add(clazz)
        } catch (_: Throwable) {
        }
    }

    private fun takeReadPermission(activity: Activity, data: Intent, uri: Uri) {
        if (uri.scheme != "content") return
        try {
            val flags = data.flags and
                (Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
            if ((flags and Intent.FLAG_GRANT_READ_URI_PERMISSION) == 0) return
            activity.contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        } catch (_: Throwable) {
        }
    }

    private fun copyInputFileToCache(context: Context, uri: Uri): Pair<String, String>? {
        if ("file".equals(uri.scheme, ignoreCase = true)) {
            val path = uri.path.orEmpty()
            if (path.isNotBlank() && File(path).isFile) {
                val name = File(path).name.ifBlank { "audio_input" }
                return path to name
            }
        }
        return try {
            val name = safeAudioTransformFileName(queryDisplayName(context, uri) ?: uri.lastPathSegment ?: "audio_input")
            val dir = File(context.filesDir, "Hchat/audio_transform/input").apply { mkdirs() }
            val target = File(dir, "${System.currentTimeMillis()}_$name")
            context.contentResolver.openInputStream(uri)?.use { input ->
                target.outputStream().use { output -> input.copyTo(output) }
            } ?: return null
            if (!target.isFile || target.length() <= 0L) return null
            target.absolutePath to name
        } catch (_: Throwable) {
            null
        }
    }

    private fun safeAudioTransformFileName(value: String): String {
        return value.replace(Regex("""[\\/:*?"<>|]"""), "_")
            .trim()
            .ifBlank { "audio_input" }
    }
}

internal object ScriptPluginDocumentBridge {
    private val hookedClasses = HashSet<Class<*>>()
    private var exportCallback: ((Uri) -> Unit)? = null
    private var importCallback: ((Uri) -> Unit)? = null
    private var exportOwner: WeakReference<Activity>? = null
    private var importOwner: WeakReference<Activity>? = null

    @Synchronized
    fun launchExport(activity: Activity, fileName: String, onPicked: (Uri) -> Unit) {
        if (exportCallback != null) {
            Toast.makeText(activity, "已有导出文件选择正在进行", Toast.LENGTH_SHORT).show()
            return
        }
        exportCallback = onPicked
        exportOwner = WeakReference(activity)
        hookActivityResult(activity.javaClass)
        hookActivityResult(Activity::class.java)
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/zip"
            putExtra(Intent.EXTRA_TITLE, fileName)
            addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }.preferSystemDocumentsUi(activity)
        try {
            activity.startActivityForResult(intent, SCRIPT_PLUGIN_EXPORT_REQUEST_CODE)
        } catch (_: Throwable) {
            exportCallback = null
            exportOwner = null
            Toast.makeText(activity, "当前系统不支持创建 ZIP 文件", Toast.LENGTH_SHORT).show()
        }
    }

    @Synchronized
    fun launchImport(activity: Activity, onPicked: (Uri) -> Unit) {
        if (importCallback != null) {
            Toast.makeText(activity, "已有导入文件选择正在进行", Toast.LENGTH_SHORT).show()
            return
        }
        importCallback = onPicked
        importOwner = WeakReference(activity)
        hookActivityResult(activity.javaClass)
        hookActivityResult(Activity::class.java)
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
            putExtra(
                Intent.EXTRA_MIME_TYPES,
                arrayOf("application/zip", "application/x-zip-compressed", "application/octet-stream")
            )
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        }.preferSystemDocumentsUi(activity)
        try {
            activity.startActivityForResult(intent, SCRIPT_PLUGIN_IMPORT_REQUEST_CODE)
        } catch (_: Throwable) {
            val fallback = Intent(Intent.ACTION_GET_CONTENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "*/*"
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            try {
                activity.startActivityForResult(
                    Intent.createChooser(fallback, "选择插件 ZIP 文件"),
                    SCRIPT_PLUGIN_IMPORT_REQUEST_CODE
                )
            } catch (_: Throwable) {
                importCallback = null
                importOwner = null
                Toast.makeText(activity, "当前系统不支持选择 ZIP 文件", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @Synchronized
    private fun hookActivityResult(clazz: Class<*>) {
        if (hookedClasses.contains(clazz)) return
        try {
            XposedBridge.hookAllMethods(clazz, "onActivityResult", object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    val requestCode = param.args.getOrNull(0) as? Int ?: return
                    if (requestCode != SCRIPT_PLUGIN_EXPORT_REQUEST_CODE &&
                        requestCode != SCRIPT_PLUGIN_IMPORT_REQUEST_CODE
                    ) {
                        return
                    }
                    val owner = param.thisObject as? Activity ?: return
                    val callback = synchronized(this@ScriptPluginDocumentBridge) {
                        if (requestCode == SCRIPT_PLUGIN_EXPORT_REQUEST_CODE) {
                            val matches = exportOwner?.get() === owner
                            exportCallback.takeIf { matches }.also {
                                exportCallback = null
                                exportOwner = null
                            }
                        } else {
                            val matches = importOwner?.get() === owner
                            importCallback.takeIf { matches }.also {
                                importCallback = null
                                importOwner = null
                            }
                        }
                    } ?: return
                    val resultCode = param.args.getOrNull(1) as? Int ?: return
                    if (resultCode != Activity.RESULT_OK) return
                    val data = param.args.getOrNull(2) as? Intent ?: return
                    val uri = data.data ?: return
                    if (requestCode == SCRIPT_PLUGIN_IMPORT_REQUEST_CODE && uri.scheme == "content") {
                        runCatching {
                            val flags = data.flags and
                                (Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
                            if ((flags and Intent.FLAG_GRANT_READ_URI_PERMISSION) != 0) {
                                owner.contentResolver.takePersistableUriPermission(
                                    uri,
                                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                                )
                            }
                        }
                    }
                    callback(uri)
                }
            })
            hookedClasses.add(clazz)
        } catch (_: Throwable) {
        }
    }
}

internal object ConfigImportExportBridge {
    private val hookedClasses = HashSet<Class<*>>()
    private var onImported: (() -> Unit)? = null
    private var currentActivity: Activity? = null

    @Synchronized
    fun launchExport(activity: Activity) {
        currentActivity = activity
        hookActivityResult(activity.javaClass)
        hookActivityResult(Activity::class.java)
        val fileName = "Hchat_config_" + SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date()) + ".json"
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/json"
            putExtra(Intent.EXTRA_TITLE, fileName)
            addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }.preferSystemDocumentsUi(activity)
        try {
            activity.startActivityForResult(intent, CONFIG_EXPORT_REQUEST_CODE)
        } catch (_: Throwable) {
            Toast.makeText(activity, "当前系统不支持创建配置文件", Toast.LENGTH_SHORT).show()
        }
    }

    @Synchronized
    fun launchImport(activity: Activity, onDone: () -> Unit) {
        onImported = onDone
        currentActivity = activity
        hookActivityResult(activity.javaClass)
        hookActivityResult(Activity::class.java)
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }.preferSystemDocumentsUi(activity)
        try {
            activity.startActivityForResult(intent, CONFIG_IMPORT_REQUEST_CODE)
        } catch (_: Throwable) {
            val fallback = Intent(Intent.ACTION_GET_CONTENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "*/*"
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            activity.startActivityForResult(Intent.createChooser(fallback, "选择 Hchat 配置文件"), CONFIG_IMPORT_REQUEST_CODE)
        }
    }

    @Synchronized
    private fun hookActivityResult(clazz: Class<*>) {
        if (hookedClasses.contains(clazz)) return
        try {
            XposedBridge.hookAllMethods(clazz, "onActivityResult", object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    val requestCode = param.args.getOrNull(0) as? Int ?: return
                    if (requestCode != CONFIG_EXPORT_REQUEST_CODE && requestCode != CONFIG_IMPORT_REQUEST_CODE) return
                    val resultCode = param.args.getOrNull(1) as? Int ?: return
                    if (resultCode != Activity.RESULT_OK) return
                    val data = param.args.getOrNull(2) as? Intent ?: return
                    val uri = data.data ?: return
                    val activity = currentActivity ?: return
                    if (requestCode == CONFIG_EXPORT_REQUEST_CODE) {
                        val count = HchatConfigBackup.exportToUri(activity, uri)
                        if (count > 0) {
                            Toast.makeText(activity, "已导出 $count 项配置", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(activity, "导出失败", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        val count = HchatConfigBackup.importFromUri(activity, uri)
                        if (count > 0) {
                            onImported?.invoke()
                            Toast.makeText(activity, "已导入 $count 项配置，当前设置页已刷新", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(activity, "导入失败或文件无配置", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
            hookedClasses.add(clazz)
        } catch (_: Throwable) {
        }
    }
}

internal object HchatConfigBackup {
    private const val VERSION = 1
    private const val TYPE_BOOLEAN = "boolean"
    private const val TYPE_INT = "int"
    private const val TYPE_LONG = "long"
    private const val TYPE_FLOAT = "float"
    private const val TYPE_STRING = "string"
    private const val TYPE_STRING_SET = "string_set"
    private const val KEY_TYPE = "type"
    private const val KEY_VALUE = "value"

    private val prefsNames = listOf(
        UI_PREFS_NAME,
        CallMediaLimitSettings.PREFS_NAME,
        FakeLocationSettings.PREFS_NAME,
        FinderMediaDownloadSettings.PREFS_NAME,
        HchatExtraSettings.PREFS_NAME,
        StatusTextLimitSettings.PREFS_NAME,
        EditMessageSettings.PREFS_NAME,
        DisableHotUpdateSettings.PREFS_NAME,
        RemoveMomentsAdsSettings.PREFS_NAME,
        MomentsAutoCommentSettings.PREFS_NAME,
        MomentsAutoLikeSettings.PREFS_NAME,
        MomentsAutoForwardSettings.PREFS_NAME,
        MomentsAutoRefreshSettings.PREFS_NAME,
        MomentsContactFilterSettings.PREFS_NAME,
        MomentsBottomDetailSettings.PREFS_NAME,
        MomentsPostNotificationSettings.PREFS_NAME,
        MomentsFakeInteractionSettings.PREFS_NAME,
        ProfileIdSettings.PREFS_NAME,
        SettingsEntrySettings.PREFS_NAME,
        FloatingShortcutSettings.PREFS_NAME,
        QuickMarkReadSettings.PREFS_NAME,
        CustomNotificationSettings.PREFS_NAME,
        ConversationGroupStore.PREFS_NAME,
        ConversationTabsStore.PREFS_NAME,
        AutoReplySettings.PREFS_NAME,
        MessageAffixSettings.PREFS_NAME,
        InputHintSettings.PREFS_NAME,
        AutoOriginalImageSettings.PREFS_NAME,
        AutoViewOriginalSettings.PREFS_NAME,
        AtAllNotificationBlockSettings.PREFS_NAME,
        KeywordNotificationSettings.PREFS_NAME,
        TextSpeechSettings.PREFS_NAME,
        TextVoiceSettings.PREFS_NAME,
        ZombieCheckSettings.PREFS_NAME,
        WeChatKeepAliveSettings.PREFS_NAME,
        QuoteDeleteClearSettings.PREFS_NAME,
        ChatTimeStyleSettings.PREFS_NAME,
        EmojiSaveSettings.PREFS_NAME,
        SwipeQuoteSettings.PREFS_NAME,
        AudioTransformSettings.PREFS_NAME,
        FakeVoiceDurationSettings.PREFS_NAME,
        MessageBubbleSettings.PREFS_NAME,
        MessageTextColorSettings.PREFS_NAME,
        HideChatAvatarSettings.PREFS_NAME,
        HideChatMenuSettings.PREFS_NAME,
        QuickContactEditSettings.PREFS_NAME,
        RoundAvatarSettings.PREFS_NAME,
        CustomFriendAvatarSettings.PREFS_NAME,
        MessageBlockSettings.PREFS_NAME,
        GroupLeaveMonitorSettings.PREFS_NAME,
        GroupRenameMonitorSettings.PREFS_NAME,
        MultiRecallSettings.PREFS_NAME,
        VoiceForwardSettings.PREFS_NAME,
        FakeScanCameraSettings.PREFS_NAME,
        RedPacketSettings.PREFS_NAME,
        AntiRecallSettings.PREFS_NAME,
        ProtobufPacketSettings.PREFS_NAME,
        ScriptPluginSettings.PREFS_NAME,
        WeChatTabletSettings.PREFS_NAME,
        AutoTransferSettings.PREFS_NAME,
        FakeWalletBalanceSettings.PREFS_NAME,
        MemberTitleSettings.PREFS_NAME,
        RealNameTailSettings.PREFS_NAME,
        GroupNicknameColorSettings.PREFS_NAME
    ).distinct()

    fun exportToUri(context: Context, uri: Uri): Int {
        return runCatching {
            val prefsJson = JSONObject()
            var itemCount = 0
            prefsNames.forEach { name ->
                val sp = HchatStorage.preferences(context, name)
                val entries = JSONObject()
                sp.all.forEach { (key, value) ->
                    encodePreferenceValue(value)?.let { encoded ->
                        entries.put(key, encoded)
                        itemCount++
                    }
                }
                if (entries.length() > 0) {
                    prefsJson.put(name, entries)
                }
            }
            val root = JSONObject().apply {
                put("format", "HchatConfigBackup")
                put("version", VERSION)
                put("appVersion", BuildConfig.VERSION_NAME)
                put("exportedAt", System.currentTimeMillis())
                put("prefs", prefsJson)
            }
            context.contentResolver.openOutputStream(uri, "wt")?.use { output ->
                output.write(root.toString(2).toByteArray(Charsets.UTF_8))
            } ?: return 0
            itemCount
        }.getOrDefault(0)
    }

    fun importFromUri(context: Context, uri: Uri): Int {
        return runCatching {
            val text = context.contentResolver.openInputStream(uri)?.use { input ->
                input.reader(Charsets.UTF_8).readText()
            }.orEmpty()
            if (text.isBlank()) return 0
            val root = JSONObject(text)
            if (root.optString("format") != "HchatConfigBackup") return 0
            val prefsJson = root.optJSONObject("prefs") ?: return 0
            var itemCount = 0
            prefsNames.forEach { name ->
                val entries = prefsJson.optJSONObject(name) ?: return@forEach
                val editor = HchatStorage.preferences(context, name).edit().clear()
                val keys = entries.keys()
                while (keys.hasNext()) {
                    val key = keys.next()
                    val encoded = entries.optJSONObject(key) ?: continue
                    if (decodePreferenceValue(editor, key, encoded)) {
                        itemCount++
                    }
                }
                editor.commit()
            }
            itemCount
        }.getOrDefault(0)
    }

    private fun encodePreferenceValue(value: Any?): JSONObject? {
        val obj = JSONObject()
        when (value) {
            is Boolean -> {
                obj.put(KEY_TYPE, TYPE_BOOLEAN)
                obj.put(KEY_VALUE, value)
            }
            is Int -> {
                obj.put(KEY_TYPE, TYPE_INT)
                obj.put(KEY_VALUE, value)
            }
            is Long -> {
                obj.put(KEY_TYPE, TYPE_LONG)
                obj.put(KEY_VALUE, value)
            }
            is Float -> {
                obj.put(KEY_TYPE, TYPE_FLOAT)
                obj.put(KEY_VALUE, value.toDouble())
            }
            is String -> {
                obj.put(KEY_TYPE, TYPE_STRING)
                obj.put(KEY_VALUE, value)
            }
            is Set<*> -> {
                obj.put(KEY_TYPE, TYPE_STRING_SET)
                obj.put(KEY_VALUE, JSONArray().apply {
                    value.filterIsInstance<String>().forEach { put(it) }
                })
            }
            else -> return null
        }
        return obj
    }

    private fun decodePreferenceValue(
        editor: SharedPreferences.Editor,
        key: String,
        encoded: JSONObject
    ): Boolean {
        return try {
            when (encoded.optString(KEY_TYPE)) {
                TYPE_BOOLEAN -> editor.putBoolean(key, encoded.optBoolean(KEY_VALUE))
                TYPE_INT -> editor.putInt(key, encoded.optInt(KEY_VALUE))
                TYPE_LONG -> editor.putLong(key, encoded.optLong(KEY_VALUE))
                TYPE_FLOAT -> editor.putFloat(key, encoded.optDouble(KEY_VALUE).toFloat())
                TYPE_STRING -> editor.putString(key, encoded.optString(KEY_VALUE, ""))
                TYPE_STRING_SET -> editor.putStringSet(key, decodeStringSet(encoded.optJSONArray(KEY_VALUE)))
                else -> return false
            }
            true
        } catch (_: Throwable) {
            false
        }
    }

    private fun decodeStringSet(array: JSONArray?): Set<String> {
        if (array == null) return emptySet()
        val result = LinkedHashSet<String>()
        for (i in 0 until array.length()) {
            result.add(array.optString(i, ""))
        }
        return result
    }
}

internal enum class MainTab {
    PRACTICAL,
    ENTERTAINMENT,
    PLUGIN,
    SETTINGS
}

internal data class MainNavItem(
    val tab: MainTab,
    val label: String,
    val icon: ImageVector
)

internal data class FeatureGroupEntry(
    val title: String,
    val providers: List<FeatureSettingsProvider>
)

internal data class FeatureSearchResult(
    val provider: FeatureSettingsProvider,
    val group: FeatureGroupEntry?,
    val summary: String
)

internal data class TemplateVariable(
    val token: String,
    val label: String
)

internal sealed class DetailPage {
    object Search : DetailPage()
    data class FeatureGroup(val group: FeatureGroupEntry) : DetailPage()
    data class Feature(
        val provider: FeatureSettingsProvider,
        val sourceGroup: FeatureGroupEntry? = null,
        val returnToSearch: Boolean = false
    ) : DetailPage()
    data class ScriptPluginAgent(val parentFeature: Feature? = null) : DetailPage()
    data class ScriptPluginMarket(val parentFeature: Feature? = null) : DetailPage()
    data class ScriptPluginManager(val parentFeature: Feature? = null) : DetailPage()
}

internal data class SettingsNavKey(val page: DetailPage?) : NavKey

internal sealed class FeatureSubRoute {
    object Main : FeatureSubRoute()
    data class ContactPicker(val request: ContactPickerRequest) : FeatureSubRoute()
    data class OptionPicker(val request: OptionPickerRequest) : FeatureSubRoute()
}

internal sealed class MomentsAutoLikeRoute {
    object Main : MomentsAutoLikeRoute()
    data class ContactPicker(val listMode: Int, val request: ContactPickerRequest) : MomentsAutoLikeRoute()
}

internal sealed class AtAllNotificationBlockRoute {
    object Main : AtAllNotificationBlockRoute()
    data class GroupPicker(val request: ContactPickerRequest) : AtAllNotificationBlockRoute()
}

internal sealed class MomentsAutoCommentRoute {
    object Main : MomentsAutoCommentRoute()
    data class ContactPicker(val listMode: Int, val request: ContactPickerRequest) : MomentsAutoCommentRoute()
}

internal sealed class MomentsAutoForwardRoute {
    object Main : MomentsAutoForwardRoute()
    object ReplacementRules : MomentsAutoForwardRoute()
    data class ContactPicker(val request: ContactPickerRequest) : MomentsAutoForwardRoute()
}

internal sealed class MomentsContactFilterRoute {
    object Main : MomentsContactFilterRoute()
    data class ContactPicker(val request: ContactPickerRequest) : MomentsContactFilterRoute()
}

internal sealed class MomentsPostNotificationRoute {
    object Main : MomentsPostNotificationRoute()
    data class ContactPicker(val request: ContactPickerRequest) : MomentsPostNotificationRoute()
}

internal sealed class RedPacketRoute {
    object Main : RedPacketRoute()
    object TemplateManager : RedPacketRoute()
    object ListManager : RedPacketRoute()
    object ReplySteps : RedPacketRoute()
    data class TemplateEditor(val request: RedPacketTemplateEditorRequest) : RedPacketRoute()
    data class BindingEditor(val request: RedPacketBindingEditorRequest) : RedPacketRoute()
    data class ContactPicker(val request: ContactPickerRequest) : RedPacketRoute()
    data class RuleContactPicker(val request: MessageBlockContactPickerRequest) : RedPacketRoute()
    data class OptionPicker(val request: OptionPickerRequest) : RedPacketRoute()
}

internal sealed class AutoTransferRoute {
    object Main : AutoTransferRoute()
    object TemplateManager : AutoTransferRoute()
    object BindingManager : AutoTransferRoute()
    object BatchApply : AutoTransferRoute()
    data class GlobalReplySteps(val target: TransferReplyTarget) : AutoTransferRoute()
    data class TemplateEditor(val request: TransferTemplateEditorRequest) : AutoTransferRoute()
    data class BindingEditor(val request: TransferBindingEditorRequest) : AutoTransferRoute()
    data class ContactPicker(val request: ContactPickerRequest) : AutoTransferRoute()
}

internal enum class TransferReplyTarget {
    PRIVATE,
    GROUP
}

internal sealed class MessageBlockRoute {
    object Main : MessageBlockRoute()
    object TemplateManager : MessageBlockRoute()
    object ListManager : MessageBlockRoute()
    data class DefaultRuleEditor(val kind: MessageBlockDefaultRuleKind) : MessageBlockRoute()
    data class TemplateEditor(val request: MessageBlockTemplateEditorRequest) : MessageBlockRoute()
    data class BindingEditor(val request: MessageBlockBindingEditorRequest) : MessageBlockRoute()
    data class BatchBindingEditor(val request: MessageBlockBatchBindingEditorRequest) : MessageBlockRoute()
    data class ContactPicker(val request: ContactPickerRequest) : MessageBlockRoute()
    data class GroupMemberPicker(val request: GroupMemberPickerRequest) : MessageBlockRoute()
}

internal sealed class GroupLeaveRoute {
    object Main : GroupLeaveRoute()
    object GroupManager : GroupLeaveRoute()
    object TemplateManager : GroupLeaveRoute()
    object BatchTemplateBinding : GroupLeaveRoute()
    data class TemplateEditor(val request: GroupLeaveTemplateEditorRequest) : GroupLeaveRoute()
    data class GroupEditor(val groupId: String, val label: String) : GroupLeaveRoute()
    data class ContactPicker(val request: ContactPickerRequest) : GroupLeaveRoute()
    data class FavoritePicker(val request: FavoritePickerRequest) : GroupLeaveRoute()
}

internal sealed class GroupChatLabelRoute {
    object Main : GroupChatLabelRoute()
    data class Editor(val label: GroupChatLabel, val existing: Boolean) : GroupChatLabelRoute()
    data class GroupPicker(val label: GroupChatLabel, val existing: Boolean) : GroupChatLabelRoute()
}

internal sealed class ConversationGroupRoute {
    object Main : ConversationGroupRoute()
    data class Editor(val group: ConversationGroup, val existing: Boolean) : ConversationGroupRoute()
    data class ConversationPicker(val group: ConversationGroup, val existing: Boolean) : ConversationGroupRoute()
    data class ParentPicker(val group: ConversationGroup, val existing: Boolean) : ConversationGroupRoute()
    data class AutomaticGrouping(val group: ConversationGroup) : ConversationGroupRoute()
    data class AutomaticPicker(
        val group: ConversationGroup,
        val kind: ConversationGroupAutomaticPickerKind
    ) : ConversationGroupRoute()
}

internal enum class ConversationGroupAutomaticPickerKind(
    val title: String,
    val emptyText: String
) {
    GROUPS("选择自动归拢群聊", "没有可选群聊"),
    OFFICIAL_INCLUDE("选择要包含的公众号", "没有可选公众号"),
    OFFICIAL_EXCLUDE("选择要排除的公众号", "没有可选公众号")
}

internal sealed class GroupRenameRoute {
    object Main : GroupRenameRoute()
    object GroupManager : GroupRenameRoute()
    object TemplateManager : GroupRenameRoute()
    object BatchTemplateBinding : GroupRenameRoute()
    data class GroupEditor(val groupId: String, val label: String) : GroupRenameRoute()
    data class TemplateEditor(val index: Int, val template: GroupRenameReplyTemplate, val canDelete: Boolean) : GroupRenameRoute()
    data class ContactPicker(val request: ContactPickerRequest, val fromMain: Boolean = false) : GroupRenameRoute()
}

internal sealed class ScheduledTaskRoute {
    object Main : ScheduledTaskRoute()
    object Editor : ScheduledTaskRoute()
    object ContactPicker : ScheduledTaskRoute()
    data class FavoritePicker(val index: Int, val request: FavoritePickerRequest) : ScheduledTaskRoute()
}

internal sealed class SelectedMessagesRoute {
    object Main : SelectedMessagesRoute()
    object Editor : SelectedMessagesRoute()
    object ContactPicker : SelectedMessagesRoute()
    data class FavoritePicker(val request: FavoritePickerRequest) : SelectedMessagesRoute()
}

internal sealed class AudioTransformRoute {
    object Main : AudioTransformRoute()
    object ContactPicker : AudioTransformRoute()
}

internal sealed class CustomNotificationRoute {
    object Main : CustomNotificationRoute()
    object RuleList : CustomNotificationRoute()
    object BatchEditor : CustomNotificationRoute()
    data class DefaultEditor(val kind: CustomNotificationDefaultKind) : CustomNotificationRoute()
    data class RuleEditor(val talker: String) : CustomNotificationRoute()
    data class ContactPicker(val request: ContactPickerRequest) : CustomNotificationRoute()
    data class GroupMemberPicker(val talker: String, val request: GroupMemberPickerRequest) : CustomNotificationRoute()
}

internal sealed class KeywordNotificationRoute {
    object Main : KeywordNotificationRoute()
    object KeywordList : KeywordNotificationRoute()
    object TemplateEditor : KeywordNotificationRoute()
    data class KeywordEditor(val oldKeyword: String?) : KeywordNotificationRoute()
    data class ContactPicker(val request: ContactPickerRequest, val includeMode: Boolean) : KeywordNotificationRoute()
}

internal sealed class TextSpeechRoute {
    object Main : TextSpeechRoute()
    data class ContactPicker(val request: ContactPickerRequest) : TextSpeechRoute()
}

internal enum class MessageBlockDefaultRuleKind {
    PRIVATE,
    GROUP,
    OFFICIAL
}

internal enum class CustomNotificationDefaultKind {
    PRIVATE,
    GROUP,
    OFFICIAL
}

internal data class AutoReplyFriendAutomationKeys(
    val labelNewFriendEnable: String,
    val labelDateEnable: String,
    val labelDateFormat: String,
    val labelExistingEnable: String,
    val labelSelectedNames: String,
    val remarkNewFriendEnable: String,
    val remarkNicknameSuffixEnable: String,
    val remarkDateEnable: String,
    val remarkDateFormat: String,
    val remarkCustomEnable: String,
    val remarkCustomText: String
)

internal sealed class AutoReplyEditorRoute {
    object Main : AutoReplyEditorRoute()
    data class OptionPicker(val request: OptionPickerRequest) : AutoReplyEditorRoute()
    data class ContactPicker(val request: ContactPickerRequest) : AutoReplyEditorRoute()
    data class GroupMemberPicker(val request: GroupMemberPickerRequest) : AutoReplyEditorRoute()
}

internal sealed class AutoReplyStepsRoute {
    object Main : AutoReplyStepsRoute()
    data class OptionPicker(val request: OptionPickerRequest) : AutoReplyStepsRoute()
    data class ContactPicker(val request: ContactPickerRequest) : AutoReplyStepsRoute()
    data class FavoritePicker(val request: FavoritePickerRequest) : AutoReplyStepsRoute()
}

internal sealed class AutoReplyAiRoute {
    object Main : AutoReplyAiRoute()
    object Xiaozhi : AutoReplyAiRoute()
    object Zhilia : AutoReplyAiRoute()
    object ZhiliaConfigs : AutoReplyAiRoute()
    object ZhiliaModels : AutoReplyAiRoute()
    data class OptionPicker(val request: OptionPickerRequest) : AutoReplyAiRoute()
}

internal sealed class RedPacketReplyStepsRoute {
    object Main : RedPacketReplyStepsRoute()
    data class StepEditor(val index: Int) : RedPacketReplyStepsRoute()
    data class FavoritePicker(val request: FavoritePickerRequest) : RedPacketReplyStepsRoute()
}

internal sealed class MessageBlockContactPickerRoute {
    object Labels : MessageBlockContactPickerRoute()
    data class Contacts(
        val filter: MessageBlockContactFilter,
        val label: MessageBlockLabelOption?
    ) : MessageBlockContactPickerRoute()
}

internal sealed class GroupMemberPickerRoute {
    object Groups : GroupMemberPickerRoute()
    data class Members(val group: ContactOption) : GroupMemberPickerRoute()
}

internal object MomentsAutoCommentPage {
    @Composable
    operator fun invoke(
        context: Context,
        provider: FeatureSettingsProvider,
        onBack: () -> Unit
    ) {
    val sp = remember { HchatStorage.preferences(context, MomentsAutoCommentSettings.PREFS_NAME) }
    var route by remember { mutableStateOf<MomentsAutoCommentRoute>(MomentsAutoCommentRoute.Main) }
    var whitelist by remember { mutableStateOf(parseIds(sp.getString(MomentsAutoCommentSettings.KEY_WHITELIST, "").orEmpty())) }
    var blacklist by remember { mutableStateOf(parseIds(sp.getString(MomentsAutoCommentSettings.KEY_BLACKLIST, "").orEmpty())) }

    SettingsRouteTransition(
        targetState = route,
        label = "MomentsAutoCommentRoute",
        depthOf = { if (it is MomentsAutoCommentRoute.Main) 0 else 1 }
    ) { current ->
        when (current) {
            MomentsAutoCommentRoute.Main -> MainPage(
                provider = provider,
                sp = sp,
                whitelist = whitelist,
                blacklist = blacklist,
                onBack = onBack,
                onPickTargets = { listMode ->
                    val selected = if (listMode == MomentsAutoCommentSettings.LIST_BLACKLIST) blacklist else whitelist
                    route = MomentsAutoCommentRoute.ContactPicker(
                        listMode,
                        ContactPickerRequest(
                            title = if (listMode == MomentsAutoCommentSettings.LIST_BLACKLIST) "选择评论黑名单" else "选择评论白名单",
                            mode = ContactPickerMode.FRIENDS,
                            multiSelect = true,
                            existingValue = formatIds(selected),
                            onValue = {},
                            enableLabels = true
                        )
                    )
                }
            )
            is MomentsAutoCommentRoute.ContactPicker -> ContactPickerPage(
                context = context,
                request = current.request,
                onBack = { route = MomentsAutoCommentRoute.Main },
                onConfirm = { picked ->
                    val ids = picked.map { it.id }.filter { it.isNotBlank() }.toSet()
                    if (current.listMode == MomentsAutoCommentSettings.LIST_BLACKLIST) {
                        blacklist = ids
                        sp.edit().putString(MomentsAutoCommentSettings.KEY_BLACKLIST, formatIds(ids)).apply()
                    } else {
                        whitelist = ids
                        sp.edit().putString(MomentsAutoCommentSettings.KEY_WHITELIST, formatIds(ids)).apply()
                    }
                    route = MomentsAutoCommentRoute.Main
                }
            )
        }
    }
    }

    @Composable
    private fun MainPage(
        provider: FeatureSettingsProvider,
        sp: SharedPreferences,
        whitelist: Set<String>,
        blacklist: Set<String>,
        onBack: () -> Unit,
        onPickTargets: (Int) -> Unit
    ) {
    var enabled by remember { mutableStateOf(sp.getBoolean(MomentsAutoCommentSettings.KEY_ENABLE, false)) }
    var commentContent by remember {
        mutableStateOf(
            sp.getString(
                MomentsAutoCommentSettings.KEY_COMMENT_CONTENT,
                MomentsAutoCommentSettings.DEFAULT_COMMENT_CONTENT
            ).orEmpty()
        )
    }
    var timeFormat by remember {
        mutableStateOf(
            sp.getString(
                MomentsAutoCommentSettings.KEY_TIME_FORMAT,
                MomentsAutoCommentSettings.DEFAULT_TIME_FORMAT
            ).orEmpty()
        )
    }
    var commentSelf by remember {
        mutableStateOf(
            sp.getBoolean(
                MomentsAutoCommentSettings.KEY_COMMENT_SELF,
                MomentsAutoCommentSettings.DEFAULT_COMMENT_SELF
            )
        )
    }
    var listMode by remember {
        mutableStateOf(sp.getInt(MomentsAutoCommentSettings.KEY_LIST_MODE, MomentsAutoCommentSettings.DEFAULT_LIST_MODE))
    }
    var dailyCommentLimit by remember {
        mutableStateOf(
            sp.getInt(
                MomentsAutoCommentSettings.KEY_DAILY_COMMENT_LIMIT,
                MomentsAutoCommentSettings.DEFAULT_DAILY_COMMENT_LIMIT
            ).toString()
        )
    }
    var delayMode by remember {
        mutableStateOf(sp.getInt(MomentsAutoCommentSettings.KEY_DELAY_MODE, MomentsAutoCommentSettings.DEFAULT_DELAY_MODE))
    }
    var fixedDelay by remember {
        mutableStateOf(sp.getInt(MomentsAutoCommentSettings.KEY_FIXED_DELAY_SECONDS, MomentsAutoCommentSettings.DEFAULT_FIXED_DELAY_SECONDS).toString())
    }
    var randomMin by remember {
        mutableStateOf(sp.getInt(MomentsAutoCommentSettings.KEY_RANDOM_MIN_SECONDS, MomentsAutoCommentSettings.DEFAULT_RANDOM_MIN_SECONDS).toString())
    }
    var randomMax by remember {
        mutableStateOf(sp.getInt(MomentsAutoCommentSettings.KEY_RANDOM_MAX_SECONDS, MomentsAutoCommentSettings.DEFAULT_RANDOM_MAX_SECONDS).toString())
    }
    var timeWindow by remember {
        mutableStateOf(sp.getBoolean(MomentsAutoCommentSettings.KEY_TIME_WINDOW_ENABLE, MomentsAutoCommentSettings.DEFAULT_TIME_WINDOW_ENABLE))
    }
    var startTime by remember {
        mutableStateOf(sp.getString(MomentsAutoCommentSettings.KEY_START_TIME, MomentsAutoCommentSettings.DEFAULT_START_TIME).orEmpty())
    }
    var endTime by remember {
        mutableStateOf(sp.getString(MomentsAutoCommentSettings.KEY_END_TIME, MomentsAutoCommentSettings.DEFAULT_END_TIME).orEmpty())
    }
    var maxAge by remember {
        mutableStateOf(sp.getInt(MomentsAutoCommentSettings.KEY_MAX_AGE_HOURS, MomentsAutoCommentSettings.DEFAULT_MAX_AGE_HOURS).toString())
    }
    var allowText by remember { mutableStateOf(sp.getBoolean(MomentsAutoCommentSettings.KEY_ALLOW_TEXT, true)) }
    var allowImage by remember { mutableStateOf(sp.getBoolean(MomentsAutoCommentSettings.KEY_ALLOW_IMAGE, true)) }
    var allowVideo by remember { mutableStateOf(sp.getBoolean(MomentsAutoCommentSettings.KEY_ALLOW_VIDEO, true)) }
    var allowLink by remember { mutableStateOf(sp.getBoolean(MomentsAutoCommentSettings.KEY_ALLOW_LINK, false)) }
    var allowMusic by remember { mutableStateOf(sp.getBoolean(MomentsAutoCommentSettings.KEY_ALLOW_MUSIC, false)) }
    var allowOther by remember { mutableStateOf(sp.getBoolean(MomentsAutoCommentSettings.KEY_ALLOW_OTHER, false)) }
    var allowUnknown by remember { mutableStateOf(sp.getBoolean(MomentsAutoCommentSettings.KEY_ALLOW_UNKNOWN, false)) }
    var keywordsText by remember { mutableStateOf(sp.getString(MomentsAutoCommentSettings.KEY_KEYWORDS_TEXT, "").orEmpty()) }
    var keywordsImageText by remember { mutableStateOf(sp.getString(MomentsAutoCommentSettings.KEY_KEYWORDS_IMAGE_TEXT, "").orEmpty()) }
    var keywordsVideoText by remember { mutableStateOf(sp.getString(MomentsAutoCommentSettings.KEY_KEYWORDS_VIDEO_TEXT, "").orEmpty()) }
    var keywordsCardText by remember { mutableStateOf(sp.getString(MomentsAutoCommentSettings.KEY_KEYWORDS_CARD_TEXT, "").orEmpty()) }
    var keywordText by remember { mutableStateOf(sp.getBoolean(MomentsAutoCommentSettings.KEY_KEYWORD_TEXT, true)) }
    var keywordImage by remember { mutableStateOf(sp.getBoolean(MomentsAutoCommentSettings.KEY_KEYWORD_IMAGE, true)) }
    var keywordVideo by remember { mutableStateOf(sp.getBoolean(MomentsAutoCommentSettings.KEY_KEYWORD_VIDEO, true)) }
    var keywordCard by remember { mutableStateOf(sp.getBoolean(MomentsAutoCommentSettings.KEY_KEYWORD_CARD, true)) }
    var logEnabled by remember { mutableStateOf(sp.getBoolean(MomentsAutoCommentSettings.KEY_LOG_ENABLE, false)) }
    var logs by remember { mutableStateOf(sp.getString(MomentsAutoCommentSettings.KEY_LOGS, "").orEmpty()) }
    val list = if (listMode == MomentsAutoCommentSettings.LIST_BLACKLIST) blacklist else whitelist
    val listSummary = when {
        listMode == MomentsAutoCommentSettings.LIST_BLACKLIST && list.isEmpty() -> "未排除好友，将匹配全部好友"
        listMode == MomentsAutoCommentSettings.LIST_WHITELIST && list.isEmpty() -> "未选择好友，自动评论不会执行"
        else -> "已选择 ${list.size} 位好友"
    }
    val listState = rememberLazyListState()
    val scrollBehavior = MiuixScrollBehavior()

    PageScaffold(
        title = provider.title(),
        largeTitle = provider.title(),
        scrollBehavior = scrollBehavior,
        bottomBar = { BottomActionBar("返回", onBack) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            state = listState,
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 8.dp,
                bottom = padding.calculateBottomPadding() + 84.dp
            )
        ) {
            item { SmallTitle(text = "自动评论") }
            item {
                SettingsCard {
                    SwitchRow(enabled, "朋友圈自动评论", "按下方规则处理新获取的朋友圈") {
                        enabled = it
                        sp.edit()
                            .putBoolean(MomentsAutoCommentSettings.KEY_ENABLE, it)
                            .putLong(
                                MomentsAutoCommentSettings.KEY_ENABLED_AT_SECONDS,
                                if (it) System.currentTimeMillis() / 1000L else 0L
                            )
                            .apply()
                    }
                    if (enabled) {
                        InsetDivider()
                        VariableInputRow(
                            title = "评论内容",
                            summary = if (commentContent.isBlank()) "需要填写" else "点击下方中文变量插入到光标位置",
                            value = commentContent,
                            variables = momentsAutoCommentTemplateVariables,
                            minLines = 3
                        ) {
                            commentContent = it
                            sp.edit().putString(MomentsAutoCommentSettings.KEY_COMMENT_CONTENT, it).apply()
                        }
                        if (commentContent.contains(MomentsAutoCommentSettings.VAR_TIME)) {
                            InsetDivider()
                            InputRow(
                                title = "时间变量格式",
                                summary = if (MomentsAutoCommentSettings.isValidTimeFormat(timeFormat)) {
                                    "例如 HH:mm:ss 或 yyyy-MM-dd HH:mm:ss"
                                } else {
                                    "格式无效，当前输入不会保存"
                                },
                                value = timeFormat
                            ) {
                                timeFormat = it
                                if (MomentsAutoCommentSettings.isValidTimeFormat(it)) {
                                    sp.edit().putString(
                                        MomentsAutoCommentSettings.KEY_TIME_FORMAT,
                                        MomentsAutoCommentSettings.normalizeTimeFormat(it)
                                    ).apply()
                                }
                            }
                        }
                        InsetDivider()
                        SwitchRow(commentSelf, "评论自己的朋友圈", "自己的朋友圈不受好友名单限制") {
                            commentSelf = it
                            sp.edit().putBoolean(MomentsAutoCommentSettings.KEY_COMMENT_SELF, it).apply()
                        }
                    }
                }
            }
            if (enabled) {
                item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "好友范围") }
                item {
                    SettingsCard {
                        PopupOptionRow(
                            title = "名单模式",
                            summary = if (listMode == MomentsAutoCommentSettings.LIST_BLACKLIST) "除黑名单外均可评论" else "只评论白名单好友",
                            options = optionItems(
                                "白名单" to MomentsAutoCommentSettings.LIST_WHITELIST,
                                "黑名单" to MomentsAutoCommentSettings.LIST_BLACKLIST
                            ),
                            currentValue = listMode,
                            onValueChanged = {
                                listMode = it
                                sp.edit().putInt(MomentsAutoCommentSettings.KEY_LIST_MODE, it).apply()
                            }
                        )
                        InsetDivider()
                        ActionRow(if (listMode == MomentsAutoCommentSettings.LIST_BLACKLIST) "黑名单" else "白名单", listSummary) {
                            onPickTargets(listMode)
                        }
                        InsetDivider()
                        NumberInputRow("同一人每天评论数量", "每天最多评论同一人的朋友圈条数，0 表示不限制", dailyCommentLimit) {
                            dailyCommentLimit = it
                            it.toIntOrNull()?.let { value ->
                                sp.edit()
                                    .putInt(MomentsAutoCommentSettings.KEY_DAILY_COMMENT_LIMIT, value.coerceAtLeast(0))
                                    .apply()
                            }
                        }
                    }
                }
                item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "执行时间") }
                item {
                    SettingsCard {
                        PopupOptionRow(
                            title = "评论延迟",
                            summary = if (delayMode == MomentsAutoCommentSettings.DELAY_RANDOM) "在范围内随机等待" else "每条等待固定时间",
                            options = optionItems(
                                "固定延迟" to MomentsAutoCommentSettings.DELAY_FIXED,
                                "随机延迟" to MomentsAutoCommentSettings.DELAY_RANDOM
                            ),
                            currentValue = delayMode,
                            onValueChanged = {
                                delayMode = it
                                sp.edit().putInt(MomentsAutoCommentSettings.KEY_DELAY_MODE, it).apply()
                            }
                        )
                        if (delayMode == MomentsAutoCommentSettings.DELAY_FIXED) {
                            InsetDivider()
                            NumberInputRow("等待时间", "单位秒，最少 0 秒", fixedDelay) {
                                fixedDelay = it
                                it.toIntOrNull()?.let { value ->
                                    sp.edit().putInt(MomentsAutoCommentSettings.KEY_FIXED_DELAY_SECONDS, value.coerceAtLeast(0)).apply()
                                }
                            }
                        } else {
                            InsetDivider()
                            NumberInputRow("最短等待", "单位秒，最少 0 秒", randomMin) {
                                randomMin = it
                                it.toIntOrNull()?.let { value ->
                                    sp.edit().putInt(MomentsAutoCommentSettings.KEY_RANDOM_MIN_SECONDS, value.coerceAtLeast(0)).apply()
                                }
                            }
                            InsetDivider()
                            NumberInputRow("最长等待", "单位秒，不能小于最短等待", randomMax) {
                                randomMax = it
                                it.toIntOrNull()?.let { value ->
                                    sp.edit().putInt(MomentsAutoCommentSettings.KEY_RANDOM_MAX_SECONDS, value.coerceAtLeast(0)).apply()
                                }
                            }
                        }
                        InsetDivider()
                        SwitchRow(timeWindow, "限制运行时段", "支持跨零点时段") {
                            timeWindow = it
                            sp.edit().putBoolean(MomentsAutoCommentSettings.KEY_TIME_WINDOW_ENABLE, it).apply()
                        }
                        if (timeWindow) {
                            InsetDivider()
                            TimeOfDayPickerRow("开始时间", startTime) {
                                startTime = it
                                sp.edit().putString(MomentsAutoCommentSettings.KEY_START_TIME, it).apply()
                            }
                            InsetDivider()
                            TimeOfDayPickerRow("结束时间", endTime) {
                                endTime = it
                                sp.edit().putString(MomentsAutoCommentSettings.KEY_END_TIME, it).apply()
                            }
                        }
                        InsetDivider()
                        NumberInputRow("发布时间限制", "仅评论发布后指定小时内的朋友圈，最少 1 小时", maxAge) {
                            maxAge = it
                            it.toIntOrNull()?.let { value ->
                                sp.edit().putInt(MomentsAutoCommentSettings.KEY_MAX_AGE_HOURS, value.coerceAtLeast(1)).apply()
                            }
                        }
                    }
                }
                item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "内容过滤") }
                item {
                    SettingsCard {
                        SwitchRow(allowText, "文字朋友圈", "允许自动评论") {
                            allowText = it; sp.edit().putBoolean(MomentsAutoCommentSettings.KEY_ALLOW_TEXT, it).apply()
                        }
                        InsetDivider()
                        SwitchRow(allowImage, "图片/图文朋友圈", "允许自动评论") {
                            allowImage = it; sp.edit().putBoolean(MomentsAutoCommentSettings.KEY_ALLOW_IMAGE, it).apply()
                        }
                        InsetDivider()
                        SwitchRow(allowVideo, "视频/视文朋友圈", "允许自动评论") {
                            allowVideo = it; sp.edit().putBoolean(MomentsAutoCommentSettings.KEY_ALLOW_VIDEO, it).apply()
                        }
                        InsetDivider()
                        SwitchRow(allowLink, "网页/链接朋友圈", "允许自动评论") {
                            allowLink = it; sp.edit().putBoolean(MomentsAutoCommentSettings.KEY_ALLOW_LINK, it).apply()
                        }
                        InsetDivider()
                        SwitchRow(allowMusic, "音乐朋友圈", "允许自动评论") {
                            allowMusic = it; sp.edit().putBoolean(MomentsAutoCommentSettings.KEY_ALLOW_MUSIC, it).apply()
                        }
                        InsetDivider()
                        SwitchRow(allowOther, "其他卡片朋友圈", "允许自动评论") {
                            allowOther = it; sp.edit().putBoolean(MomentsAutoCommentSettings.KEY_ALLOW_OTHER, it).apply()
                        }
                        InsetDivider()
                        SwitchRow(allowUnknown, "未知类型朋友圈", "允许自动评论，默认关闭") {
                            allowUnknown = it; sp.edit().putBoolean(MomentsAutoCommentSettings.KEY_ALLOW_UNKNOWN, it).apply()
                        }
                        InsetDivider()
                        SwitchRow(keywordText, "文字朋友圈关键词", "开启后按关键词排除") {
                            keywordText = it; sp.edit().putBoolean(MomentsAutoCommentSettings.KEY_KEYWORD_TEXT, it).apply()
                        }
                        if (keywordText) {
                            InsetDivider()
                            InputRow("文字排除关键词", "多个关键词用逗号或换行分隔", keywordsText, minLines = 2) {
                                keywordsText = it
                                sp.edit().putString(MomentsAutoCommentSettings.KEY_KEYWORDS_TEXT, it).apply()
                            }
                        }
                        InsetDivider()
                        SwitchRow(keywordImage, "图文朋友圈关键词", "开启后按关键词排除") {
                            keywordImage = it; sp.edit().putBoolean(MomentsAutoCommentSettings.KEY_KEYWORD_IMAGE, it).apply()
                        }
                        if (keywordImage) {
                            InsetDivider()
                            InputRow("图文排除关键词", "多个关键词用逗号或换行分隔", keywordsImageText, minLines = 2) {
                                keywordsImageText = it
                                sp.edit().putString(MomentsAutoCommentSettings.KEY_KEYWORDS_IMAGE_TEXT, it).apply()
                            }
                        }
                        InsetDivider()
                        SwitchRow(keywordVideo, "视文朋友圈关键词", "开启后按关键词排除") {
                            keywordVideo = it; sp.edit().putBoolean(MomentsAutoCommentSettings.KEY_KEYWORD_VIDEO, it).apply()
                        }
                        if (keywordVideo) {
                            InsetDivider()
                            InputRow("视文排除关键词", "多个关键词用逗号或换行分隔", keywordsVideoText, minLines = 2) {
                                keywordsVideoText = it
                                sp.edit().putString(MomentsAutoCommentSettings.KEY_KEYWORDS_VIDEO_TEXT, it).apply()
                            }
                        }
                        InsetDivider()
                        SwitchRow(keywordCard, "链接/卡片朋友圈关键词", "开启后按关键词排除") {
                            keywordCard = it; sp.edit().putBoolean(MomentsAutoCommentSettings.KEY_KEYWORD_CARD, it).apply()
                        }
                        if (keywordCard) {
                            InsetDivider()
                            InputRow("链接/卡片排除关键词", "多个关键词用逗号或换行分隔", keywordsCardText, minLines = 2) {
                                keywordsCardText = it
                                sp.edit().putString(MomentsAutoCommentSettings.KEY_KEYWORDS_CARD_TEXT, it).apply()
                            }
                        }
                    }
                }
                item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "运行记录") }
                item {
                    SettingsCard {
                        SwitchRow(logEnabled, "记录运行日志", "只保留最近 200 条") {
                            logEnabled = it
                            sp.edit().putBoolean(MomentsAutoCommentSettings.KEY_LOG_ENABLE, it).apply()
                        }
                        if (logEnabled) {
                            InsetDivider()
                            ActionRow("刷新日志", if (logs.isBlank()) "暂无记录" else "${logs.lineSequence().count()} 条记录") {
                                logs = sp.getString(MomentsAutoCommentSettings.KEY_LOGS, "").orEmpty()
                            }
                            if (logs.isNotBlank()) {
                                InsetDivider()
                                Text(
                                    text = logs.take(5000),
                                    color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily.Monospace,
                                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                                )
                                InsetDivider()
                                ActionRow("清空日志", "删除当前自动评论运行记录") {
                                    sp.edit().remove(MomentsAutoCommentSettings.KEY_LOGS).apply()
                                    logs = ""
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    }
}

internal data class FloatingShortcutEditorRequest(
    val original: FloatingShortcutItem?,
    val draft: FloatingShortcutItem
)

internal enum class FloatingShortcutRoute {
    MAIN,
    APPEARANCE
}

internal object AutoMessageForwardSettingsUi {
@Composable
fun Page(
    context: Context,
    provider: FeatureSettingsProvider,
    onBack: () -> Unit
) {
    val settings = remember { AutoMessageForwardSettings }
    var enabled by remember { mutableStateOf(settings.isEnabled(context)) }
    var rules by remember { mutableStateOf(settings.rules(context)) }
    var route by remember { mutableStateOf(AutoMessageForwardRoute.MAIN) }
    var draft by remember { mutableStateOf<AutoMessageForwardRule?>(null) }
    var pickerField by remember { mutableStateOf<AutoMessageForwardContactField?>(null) }
    var ruleQuery by remember { mutableStateOf("") }
    val ruleListState = rememberLazyListState()
    val editorListState = rememberLazyListState()

    fun saveRules(next: List<AutoMessageForwardRule>) {
        rules = next
        settings.saveRules(context, next)
    }

    SettingsRouteTransition(
        targetState = route,
        label = "AutoMessageForwardRoute",
        depthOf = {
            when (it) {
                AutoMessageForwardRoute.MAIN -> 0
                AutoMessageForwardRoute.RULES -> 1
                AutoMessageForwardRoute.EDITOR -> 2
                AutoMessageForwardRoute.CONTACTS,
                AutoMessageForwardRoute.MEMBERS,
                AutoMessageForwardRoute.TYPES,
                AutoMessageForwardRoute.REPLACEMENTS -> 3
            }
        }
    ) { currentRoute ->
        when (currentRoute) {
            AutoMessageForwardRoute.MAIN -> AutoMessageForwardMainPage(
                provider = provider,
                enabled = enabled,
                rules = rules,
                onBack = onBack,
                onEnabledChange = {
                    enabled = it
                    settings.setEnabled(context, it)
                },
                onOpenRules = { route = AutoMessageForwardRoute.RULES }
            )
            AutoMessageForwardRoute.RULES -> AutoMessageForwardRuleListPage(
                rules = rules,
                listState = ruleListState,
                query = ruleQuery,
                onQueryChange = { ruleQuery = it },
                onBack = { route = AutoMessageForwardRoute.MAIN },
                onAdd = {
                    draft = settings.newRule(rules.size + 1)
                    route = AutoMessageForwardRoute.EDITOR
                },
                onEdit = {
                    draft = it
                    route = AutoMessageForwardRoute.EDITOR
                },
                onToggle = { rule, checked ->
                    saveRules(rules.map { if (it.id == rule.id) rule.copy(enabled = checked) else it })
                },
                onDelete = { rule ->
                    saveRules(rules.filterNot { it.id == rule.id })
                    Toast.makeText(context, "规则已删除", Toast.LENGTH_SHORT).show()
                },
                onBatchDelete = { selected ->
                    val ids = selected.mapTo(HashSet()) { it.id }
                    saveRules(rules.filterNot { it.id in ids })
                    Toast.makeText(context, "已删除 ${selected.size} 条规则", Toast.LENGTH_SHORT).show()
                }
            )
            AutoMessageForwardRoute.EDITOR -> {
                val current = draft
                if (current == null) {
                    route = AutoMessageForwardRoute.RULES
                } else {
                    AutoMessageForwardRuleEditorPage(
                        rule = current,
                        listState = editorListState,
                        onBack = { route = AutoMessageForwardRoute.RULES },
                        onDraftChange = { draft = it },
                        onPickContacts = { next, field ->
                            draft = next
                            pickerField = field
                            route = AutoMessageForwardRoute.CONTACTS
                        },
                        onPickMembers = { next ->
                            draft = next
                            route = AutoMessageForwardRoute.MEMBERS
                        },
                        onPickTypes = { next ->
                            draft = next
                            route = AutoMessageForwardRoute.TYPES
                        },
                        onPickReplacementRules = { next ->
                            draft = next
                            route = AutoMessageForwardRoute.REPLACEMENTS
                        },
                        onSave = { saved ->
                            val clean = saved.copy(
                                name = saved.name.trim(),
                                sourceMemberIds = retainAutoMessageForwardSourceMembers(
                                    saved.sourceMemberIds,
                                    saved.sourceIds
                                )
                            )
                            when {
                                clean.name.isBlank() -> Toast.makeText(context, "请输入规则名称", Toast.LENGTH_SHORT).show()
                                clean.sourceIds.isEmpty() -> Toast.makeText(context, "请选择监听会话", Toast.LENGTH_SHORT).show()
                                clean.targetIds.isEmpty() -> Toast.makeText(context, "请选择转发会话", Toast.LENGTH_SHORT).show()
                                clean.messageKinds.isEmpty() -> Toast.makeText(context, "至少选择一种消息类型", Toast.LENGTH_SHORT).show()
                                else -> {
                                    saveRules(
                                        if (rules.any { it.id == clean.id }) {
                                            rules.map { if (it.id == clean.id) clean else it }
                                        } else {
                                            rules + clean
                                        }
                                    )
                                    draft = null
                                    Toast.makeText(context, "规则已保存", Toast.LENGTH_SHORT).show()
                                    route = AutoMessageForwardRoute.RULES
                                }
                            }
                        }
                    )
                }
            }
            AutoMessageForwardRoute.CONTACTS -> {
                val current = draft
                val field = pickerField
                if (current == null || field == null) {
                    route = AutoMessageForwardRoute.EDITOR
                } else {
                    val sources = field == AutoMessageForwardContactField.SOURCES
                    val contactRequest = remember(current.id, field, current.sourceIds, current.targetIds) {
                        ContactPickerRequest(
                            title = if (sources) "选择监听会话" else "选择转发会话",
                            mode = if (sources) ContactPickerMode.ALL_CHATS else ContactPickerMode.BOTH,
                            multiSelect = true,
                            existingValue = formatIds(if (sources) current.sourceIds else current.targetIds),
                            onValue = { value ->
                                val selected = parseIds(value)
                                draft = if (sources) current.copy(
                                    sourceIds = selected,
                                    sourceMemberIds = retainAutoMessageForwardSourceMembers(current.sourceMemberIds, selected)
                                )
                                else current.copy(targetIds = selected)
                                pickerField = null
                                route = AutoMessageForwardRoute.EDITOR
                            },
                            enableLabels = true
                        )
                    }
                    ContactPickerPage(
                        context = context,
                        request = contactRequest,
                        onBack = {
                            pickerField = null
                            route = AutoMessageForwardRoute.EDITOR
                        },
                        onConfirm = { selected ->
                            val value = formatIds(selected.map { it.id })
                            val ids = parseIds(value)
                            draft = if (sources) current.copy(
                                sourceIds = ids,
                                sourceMemberIds = retainAutoMessageForwardSourceMembers(current.sourceMemberIds, ids)
                            )
                            else current.copy(targetIds = ids)
                            pickerField = null
                            route = AutoMessageForwardRoute.EDITOR
                        }
                    )
                }
            }
            AutoMessageForwardRoute.MEMBERS -> {
                val current = draft
                val sourceGroups = current?.sourceIds.orEmpty().filterTo(linkedSetOf(), ::isAutoMessageForwardGroupId)
                if (current == null || sourceGroups.isEmpty()) {
                    route = AutoMessageForwardRoute.EDITOR
                } else {
                    val request = remember(current.id, current.sourceMemberIds, sourceGroups) {
                        GroupMemberPickerRequest(
                            title = "选择监听群成员",
                            existingValue = formatGroupMemberEntries(current.sourceMemberIds),
                            onValue = {},
                            allowedGroupIds = sourceGroups
                        )
                    }
                    GroupMemberPickerPage(
                        context = context,
                        request = request,
                        onBack = { route = AutoMessageForwardRoute.EDITOR },
                        onConfirm = { entries ->
                            draft = current.copy(sourceMemberIds = entries.toSet())
                            Toast.makeText(context, "已选择 ${entries.size} 个群成员", Toast.LENGTH_SHORT).show()
                            route = AutoMessageForwardRoute.EDITOR
                        }
                    )
                }
            }
            AutoMessageForwardRoute.TYPES -> {
                val current = draft
                if (current == null) {
                    route = AutoMessageForwardRoute.EDITOR
                } else {
                    AutoMessageForwardTypePickerPage(
                        selectedKinds = current.messageKinds,
                        onBack = { route = AutoMessageForwardRoute.EDITOR },
                        onConfirm = {
                            draft = current.copy(messageKinds = it)
                            route = AutoMessageForwardRoute.EDITOR
                        }
                    )
                }
            }
            AutoMessageForwardRoute.REPLACEMENTS -> {
                val current = draft
                if (current == null) {
                    route = AutoMessageForwardRoute.EDITOR
                } else {
                    KeywordReplacementRulesPage(
                        context = context,
                        initialRules = current.keywordReplacements,
                        onBack = { route = AutoMessageForwardRoute.EDITOR },
                        onSave = { updated ->
                            draft = current.copy(keywordReplacements = updated)
                            Toast.makeText(context, "替换规则已保存", Toast.LENGTH_SHORT).show()
                            route = AutoMessageForwardRoute.EDITOR
                        }
                    )
                }
            }
        }
    }
}

@Composable
internal fun AutoMessageForwardMainPage(
    provider: FeatureSettingsProvider,
    enabled: Boolean,
    rules: List<AutoMessageForwardRule>,
    onBack: () -> Unit,
    onEnabledChange: (Boolean) -> Unit,
    onOpenRules: () -> Unit
) {
    val listState = rememberLazyListState()
    val scrollBehavior = MiuixScrollBehavior()
    PageScaffold(
        title = provider.title(),
        largeTitle = provider.title(),
        scrollBehavior = scrollBehavior,
        bottomBar = { BottomActionBar("返回", onBack) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            state = listState,
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 8.dp,
                bottom = padding.calculateBottomPadding() + 84.dp
            )
        ) {
            item { SmallTitle(text = "消息自动转发") }
            item {
                SettingsCard {
                    SwitchRow(
                        checked = enabled,
                        title = "启用消息自动转发",
                        summary = "默认不转发自己发送的消息，可在每条规则中单独开启",
                        onCheckedChange = onEnabledChange
                    )
                    InsetDivider()
                    ActionRow(
                        title = "转发规则",
                        summary = if (rules.isEmpty()) "暂无规则" else "${rules.size} 条规则，${rules.count { it.enabled }} 条启用",
                        onClick = onOpenRules
                    )
                }
            }
        }
    }
}

@Composable
internal fun AutoMessageForwardRuleListPage(
    rules: List<AutoMessageForwardRule>,
    listState: LazyListState,
    query: String,
    onQueryChange: (String) -> Unit,
    onBack: () -> Unit,
    onAdd: () -> Unit,
    onEdit: (AutoMessageForwardRule) -> Unit,
    onToggle: (AutoMessageForwardRule, Boolean) -> Unit,
    onDelete: (AutoMessageForwardRule) -> Unit,
    onBatchDelete: (List<AutoMessageForwardRule>) -> Unit
) {
    val context = LocalContext.current
    var batchDeleteMode by remember { mutableStateOf(false) }
    var selectedIds by remember { mutableStateOf<Set<String>>(emptySet()) }
    var showDeleteConfirm by remember { mutableStateOf(false) }
    val visible = rules.filter { rule ->
        val lower = query.trim().lowercase(Locale.US)
        lower.isBlank() ||
            rule.name.lowercase(Locale.US).contains(lower) ||
            rule.sourceIds.any { it.lowercase(Locale.US).contains(lower) } ||
            rule.sourceMemberIds.any { it.lowercase(Locale.US).contains(lower) } ||
            rule.targetIds.any { it.lowercase(Locale.US).contains(lower) } ||
            autoMessageForwardRuleSummary(rule).lowercase(Locale.US).contains(lower)
    }
    val visibleIds = visible.mapTo(LinkedHashSet()) { it.id }
    val selected = rules.filter { it.id in selectedIds }
    val allVisibleSelected = visibleIds.isNotEmpty() && visibleIds.all { it in selectedIds }
    val scrollBehavior = MiuixScrollBehavior()
    PageScaffold(
        title = "转发规则",
        largeTitle = "转发规则",
        scrollBehavior = scrollBehavior,
        bottomBar = {
            if (batchDeleteMode) {
                BottomActionBar(
                    primaryText = "删除所选（${selected.size}）",
                    onPrimaryClick = {
                        if (selected.isEmpty()) Toast.makeText(context, "请先选择规则", Toast.LENGTH_SHORT).show()
                        else showDeleteConfirm = true
                    },
                    secondaryText = "取消",
                    onSecondaryClick = {
                        batchDeleteMode = false
                        selectedIds = emptySet()
                    },
                    middleText = if (visibleIds.isEmpty()) null else if (allVisibleSelected) "取消全选" else "全选",
                    onMiddleClick = if (visibleIds.isEmpty()) null else {
                        {
                            selectedIds = if (allVisibleSelected) selectedIds - visibleIds else selectedIds + visibleIds
                        }
                    }
                )
            } else {
                BottomActionBar(
                    primaryText = "新增规则",
                    onPrimaryClick = onAdd,
                    secondaryText = "返回",
                    onSecondaryClick = onBack,
                    middleText = if (rules.isEmpty()) null else "批量删除",
                    onMiddleClick = if (rules.isEmpty()) null else {
                        {
                            batchDeleteMode = true
                            selectedIds = emptySet()
                        }
                    }
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            state = listState,
            contentPadding = PaddingValues(top = padding.calculateTopPadding() + 8.dp, bottom = padding.calculateBottomPadding() + 84.dp)
        ) {
            item {
                SettingsCard {
                    InputRow("搜索", "按规则名、会话、群成员或消息类型筛选", query, onValueChange = onQueryChange)
                }
            }
            if (visible.isEmpty()) {
                item { SettingsCard { EmptyText("暂无规则") } }
            } else {
                visible.forEachIndexed { index, rule ->
                    item {
                        SmallTitle(
                            modifier = Modifier.padding(top = if (index == 0) 10.dp else 18.dp),
                            text = "规则 ${index + 1}"
                        )
                    }
                    item {
                        SettingsCard {
                            if (batchDeleteMode) {
                                OptionChoiceRow(
                                    item = OptionItem(rule.name.ifBlank { "未命名规则" }, index, autoMessageForwardRuleSummary(rule)),
                                    selected = rule.id in selectedIds,
                                    onClick = {
                                        selectedIds = if (rule.id in selectedIds) selectedIds - rule.id else selectedIds + rule.id
                                    }
                                )
                            } else {
                                SwitchRow(
                                    checked = rule.enabled,
                                    title = rule.name.ifBlank { "未命名规则" },
                                    summary = autoMessageForwardRuleSummary(rule),
                                    onCheckedChange = { onToggle(rule, it) }
                                )
                                InsetDivider()
                                ActionRow("编辑", "修改监听会话、转发会话和消息类型") { onEdit(rule) }
                                InsetDivider()
                                ActionRow("删除", "移除此规则") { onDelete(rule) }
                            }
                        }
                    }
                }
            }
        }
    }
    BatchDeleteConfirmDialog(
        show = showDeleteConfirm,
        message = "将删除已选的 ${selected.size} 条转发规则，此操作不可撤销。",
        labels = selected.map { it.name.ifBlank { "未命名规则" } },
        onDismiss = { showDeleteConfirm = false },
        onConfirm = {
            val targets = selected
            showDeleteConfirm = false
            batchDeleteMode = false
            selectedIds = emptySet()
            onBatchDelete(targets)
        }
    )
}

@Composable
internal fun AutoMessageForwardRuleEditorPage(
    rule: AutoMessageForwardRule,
    listState: LazyListState,
    onBack: () -> Unit,
    onDraftChange: (AutoMessageForwardRule) -> Unit,
    onPickContacts: (AutoMessageForwardRule, AutoMessageForwardContactField) -> Unit,
    onPickMembers: (AutoMessageForwardRule) -> Unit,
    onPickTypes: (AutoMessageForwardRule) -> Unit,
    onPickReplacementRules: (AutoMessageForwardRule) -> Unit,
    onSave: (AutoMessageForwardRule) -> Unit
) {
    val context = LocalContext.current
    val current = rule
    val scrollBehavior = MiuixScrollBehavior()
    PageScaffold(
        title = "编辑转发规则",
        largeTitle = "编辑转发规则",
        scrollBehavior = scrollBehavior,
        bottomBar = {
            BottomActionBar(
                primaryText = "保存",
                onPrimaryClick = { onSave(current) },
                secondaryText = "返回",
                onSecondaryClick = onBack
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            state = listState,
            contentPadding = PaddingValues(top = padding.calculateTopPadding() + 8.dp, bottom = padding.calculateBottomPadding() + 84.dp)
        ) {
            item { SmallTitle(text = "基础") }
            item {
                SettingsCard {
                    SwitchRow(current.enabled, "启用规则", "关闭后保留配置但不触发") { onDraftChange(current.copy(enabled = it)) }
                    InsetDivider()
                    SwitchRow(current.forwardOwnMessages, "转发自己发送的消息", "默认关闭；模块自动转发的消息不会再次触发") {
                        onDraftChange(current.copy(forwardOwnMessages = it))
                    }
                    InsetDivider()
                    SwitchRow(current.followSourceRecall, "跟随原消息撤回", "原消息撤回后，撤回该规则自动转发的消息") {
                        onDraftChange(current.copy(followSourceRecall = it))
                    }
                    InsetDivider()
                    InputRow("规则名称", "用于列表里识别规则", current.name) { onDraftChange(current.copy(name = it)) }
                    InsetDivider()
                    SwitchRow(current.delayEnabled, "延迟发送", "开启后按设置时间等待再转发") {
                        onDraftChange(current.copy(delayEnabled = it))
                    }
                    if (current.delayEnabled) {
                        InsetDivider()
                        NumberInputRow("延迟时间", "单位秒，0 表示立即发送", current.delaySeconds.toString()) {
                            onDraftChange(current.copy(delaySeconds = it.toLongOrNull()?.coerceAtLeast(0L) ?: 0L))
                        }
                    }
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "会话") }
            item {
                SettingsCard {
                    ActionRow("监听会话", autoMessageForwardIdSummary(current.sourceIds, "未选择")) {
                        onPickContacts(current, AutoMessageForwardContactField.SOURCES)
                    }
                    InsetDivider()
                    ActionRow(
                        "群成员范围",
                        if (current.sourceMemberIds.isEmpty()) "不限群成员" else "已选择 ${current.sourceMemberIds.size} 个群成员"
                    ) {
                        if (current.sourceIds.none(::isAutoMessageForwardGroupId)) {
                            Toast.makeText(context, "请先选择监听群聊", Toast.LENGTH_SHORT).show()
                        } else {
                            onPickMembers(current)
                        }
                    }
                    InsetDivider()
                    ActionRow("转发会话", autoMessageForwardIdSummary(current.targetIds, "未选择")) {
                        onPickContacts(current, AutoMessageForwardContactField.TARGETS)
                    }
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "消息类型") }
            item {
                SettingsCard {
                    ActionRow("选择消息类型", autoMessageForwardTypeSummary(current.messageKinds)) {
                        onPickTypes(current)
                    }
                    InsetDivider()
                    SwitchRow(current.includeKeywordsEnabled, "包含关键词", "开启后仅转发命中关键词的消息") {
                        onDraftChange(current.copy(includeKeywordsEnabled = it))
                    }
                    if (current.includeKeywordsEnabled) {
                        InsetDivider()
                        InputRow("包含关键词内容", "多个关键词用 |、逗号或换行分隔", current.includeKeywords, minLines = 2) {
                            onDraftChange(current.copy(includeKeywords = it))
                        }
                    }
                    InsetDivider()
                    SwitchRow(current.excludeKeywordsEnabled, "排除关键词", "开启后不转发命中关键词的消息") {
                        onDraftChange(current.copy(excludeKeywordsEnabled = it))
                    }
                    if (current.excludeKeywordsEnabled) {
                        InsetDivider()
                        InputRow("排除关键词内容", "多个关键词用 |、逗号或换行分隔", current.excludeKeywords, minLines = 2) {
                            onDraftChange(current.copy(excludeKeywords = it))
                        }
                    }
                    InsetDivider()
                    SwitchRow(current.replaceKeywordsEnabled, "替换关键词", "转发普通文字前按多条规则替换正文") {
                        onDraftChange(current.copy(replaceKeywordsEnabled = it))
                    }
                    if (current.replaceKeywordsEnabled) {
                        InsetDivider()
                        ActionRow(
                            "替换规则",
                            if (current.keywordReplacements.isEmpty()) {
                                "暂无规则"
                            } else {
                                "已设置 ${current.keywordReplacements.size} 条规则"
                            }
                        ) {
                            onPickReplacementRules(current)
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun AutoMessageForwardTypePickerPage(
    selectedKinds: Set<String>,
    onBack: () -> Unit,
    onConfirm: (Set<String>) -> Unit
) {
    var selected by remember(selectedKinds) { mutableStateOf(selectedKinds) }
    val options = autoMessageForwardTypeOptions()
    val listState = rememberLazyListState()
    val scrollBehavior = MiuixScrollBehavior()
    PageScaffold(
        title = "选择消息类型",
        largeTitle = "选择消息类型",
        scrollBehavior = scrollBehavior,
        bottomBar = {
            BottomActionBar(
                primaryText = "保存",
                onPrimaryClick = { onConfirm(selected) },
                secondaryText = "返回",
                onSecondaryClick = onBack,
                middleText = if (selected.size == options.size) "取消全选" else "全选",
                onMiddleClick = {
                    selected = if (selected.size == options.size) emptySet() else options.mapTo(LinkedHashSet()) { it.first }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            state = listState,
            contentPadding = PaddingValues(top = padding.calculateTopPadding() + 8.dp, bottom = padding.calculateBottomPadding() + 84.dp)
        ) {
            item {
                SettingsCard {
                    options.forEachIndexed { index, option ->
                        if (index > 0) InsetDivider()
                        SwitchRow(
                            checked = option.first in selected,
                            title = option.second,
                            summary = option.third
                        ) {
                            selected = if (it) selected + option.first else selected - option.first
                        }
                    }
                }
            }
        }
    }
}

internal fun autoMessageForwardRuleSummary(rule: AutoMessageForwardRule): String {
    return buildString {
        append("监听 ${rule.sourceIds.size} 个会话 → 转发到 ${rule.targetIds.size} 个会话 · ${autoMessageForwardTypeSummary(rule.messageKinds)}")
        if (rule.sourceMemberIds.isNotEmpty()) append(" · 指定 ${rule.sourceMemberIds.size} 个群成员")
        if (rule.forwardOwnMessages) append(" · 含自己发送")
        if (rule.followSourceRecall) append(" · 跟随撤回")
        if (rule.delayEnabled && rule.delaySeconds > 0L) append(" · 延迟 ${rule.delaySeconds} 秒")
        if ((rule.includeKeywordsEnabled && rule.includeKeywords.isNotBlank()) ||
            (rule.excludeKeywordsEnabled && rule.excludeKeywords.isNotBlank()) ||
            (rule.replaceKeywordsEnabled && rule.keywordReplacements.isNotEmpty())
        ) {
            append(" · 已设置关键词")
        }
    }
}

internal fun autoMessageForwardIdSummary(ids: Set<String>, empty: String): String {
    return if (ids.isEmpty()) empty else "已选择 ${ids.size} 个会话"
}

internal fun retainAutoMessageForwardSourceMembers(
    memberIds: Set<String>,
    sourceIds: Set<String>
): Set<String> = memberIds.filterTo(linkedSetOf()) { it.substringBefore('/') in sourceIds }

internal fun isAutoMessageForwardGroupId(value: String): Boolean =
    value.endsWith("@chatroom") || value.endsWith("@im.chatroom")

internal fun autoMessageForwardTypeSummary(kinds: Set<String>): String {
    val options = autoMessageForwardTypeOptions().associate { it.first to it.second }
    val labels = kinds.mapNotNull { options[it] }
    return when {
        labels.isEmpty() -> "未选择"
        labels.size == options.size -> "全部支持类型"
        labels.size <= 3 -> labels.joinToString("、")
        else -> "${labels.take(3).joinToString("、")}等 ${labels.size} 类"
    }
}

internal fun autoMessageForwardTypeOptions(): List<Triple<String, String, String>> {
    return listOf(
        Triple(WeChatMessageObserveApi.Kind.TEXT, "文字", "普通文字消息"),
        Triple(WeChatMessageObserveApi.Kind.IMAGE, "图片", "图片消息"),
        Triple(WeChatMessageObserveApi.Kind.VOICE, "语音", "语音消息"),
        Triple(WeChatMessageObserveApi.Kind.VIDEO, "视频", "视频消息"),
        Triple(WeChatMessageObserveApi.Kind.EMOJI, "表情", "动画表情消息"),
        Triple(WeChatMessageObserveApi.Kind.QUOTE, "引用", "引用消息"),
        Triple(WeChatMessageObserveApi.Kind.FILE, "文件", "已下载到本地的文件"),
        Triple(WeChatMessageObserveApi.Kind.LINK, "链接", "网页链接卡片"),
        Triple(WeChatMessageObserveApi.Kind.MUSIC, "音乐", "音乐卡片"),
        Triple(WeChatMessageObserveApi.Kind.APP, "小程序及卡片", "其它可转发的应用消息"),
        Triple(WeChatMessageObserveApi.Kind.LOCATION, "位置", "位置消息"),
        Triple(WeChatMessageObserveApi.Kind.SHARE_CARD, "名片", "联系人名片"),
        Triple(WeChatMessageObserveApi.Kind.NOTE, "聊天记录", "聊天记录等笔记消息"),
        Triple(WeChatMessageObserveApi.Kind.VIDEO_NUMBER_VIDEO, "视频号", "视频号消息")
    )
}
}

internal data class CustomNotificationBatchUpdate(
    val applyEnabled: Boolean,
    val enabled: Boolean,
    val applyMode: Boolean,
    val dnd: Boolean,
    val applyVibrate: Boolean,
    val vibrate: Boolean,
    val applySound: Boolean,
    val sound: Boolean,
    val applyMarkRead: Boolean,
    val markRead: Boolean,
    val applyQuickReply: Boolean,
    val quickReply: Boolean,
    val applyQuoteQuickReply: Boolean,
    val quoteQuickReply: Boolean,
    val applyMergeByTalker: Boolean,
    val mergeByTalker: Boolean,
    val applyShowDetail: Boolean,
    val showDetail: Boolean,
    val applyIgnoreWechatDnd: Boolean,
    val ignoreWechatDnd: Boolean,
    val applyMute: Boolean,
    val muteEnable: Boolean,
    val muteStart: String,
    val muteEnd: String,
    val applyAtRules: Boolean,
    val blockAtAll: Boolean,
    val blockAtMe: Boolean
) {
    fun hasChanges(): Boolean {
        return applyEnabled || applyMode || applyVibrate || applySound || applyMarkRead || applyQuickReply || applyQuoteQuickReply || applyMergeByTalker ||
            applyShowDetail || applyIgnoreWechatDnd || applyMute || applyAtRules
    }

    fun apply(rule: CustomNotificationRule): CustomNotificationRule {
        var next = rule
        if (applyEnabled) next = next.copy(enabled = enabled)
        if (applyMode) next = next.copy(mode = if (dnd) CustomNotificationSettings.MODE_DND else CustomNotificationSettings.MODE_NOTIFY)
        if (applyVibrate) next = next.copy(vibrate = vibrate)
        if (applySound) next = next.copy(sound = sound)
        if (applyMarkRead) next = next.copy(markRead = markRead)
        if (applyQuickReply) next = next.copy(quickReply = quickReply)
        if (applyQuoteQuickReply) next = next.copy(quoteQuickReply = quoteQuickReply)
        if (applyMergeByTalker) next = next.copy(mergeByTalker = mergeByTalker)
        if (applyShowDetail) next = next.copy(showDetail = showDetail)
        if (applyIgnoreWechatDnd) next = next.copy(ignoreWechatDnd = ignoreWechatDnd)
        if (applyMute) {
            next = next.copy(
                muteEnable = muteEnable,
                muteStart = CustomNotificationSettings.normalizeTime(muteStart, CustomNotificationSettings.DEFAULT_MUTE_START),
                muteEnd = CustomNotificationSettings.normalizeTime(muteEnd, CustomNotificationSettings.DEFAULT_MUTE_END)
            )
        }
        if (applyAtRules && next.group) {
            next = next.copy(blockAtAll = blockAtAll, blockAtMe = blockAtMe)
        }
        return next
    }
}

internal enum class ZombieCheckSelection {
    TARGETS,
    EXCLUDED,
    DELETE
}

internal data class ZombieCheckDeleteTarget(val wxid: String, val name: String)

internal sealed class ZombieCheckRoute {
    object Main : ZombieCheckRoute()
    data class ContactPicker(
        val selection: ZombieCheckSelection,
        val request: ContactPickerRequest
    ) : ZombieCheckRoute()
}

internal sealed class QQMusicOrderRoute {
    object Main : QQMusicOrderRoute()
    data class ContactPicker(val request: ContactPickerRequest) : QQMusicOrderRoute()
}

internal enum class CustomFriendAvatarRoute {
    MAIN,
    CONTACT_PICKER
}

internal object ConversationGroupSettingsUi {
@Composable
fun Page(
    context: Context,
    provider: FeatureSettingsProvider,
    onBack: () -> Unit
) {
    var enabled by remember { mutableStateOf(ConversationGroupStore.isEnabled(context)) }
    var groups by remember { mutableStateOf(ConversationGroupStore.load(context)) }
    var route by remember { mutableStateOf<ConversationGroupRoute>(ConversationGroupRoute.Main) }

    fun reload() {
        groups = ConversationGroupStore.load(context)
        ConversationGroupRuntime.syncAsync(context)
    }

    fun saveGroup(group: ConversationGroup, existing: Boolean): Boolean {
        val duplicate = groups.any {
            it.id != group.id && it.parentId == group.parentId &&
                it.name.equals(group.name.trim(), ignoreCase = true)
        }
        if (duplicate) {
            Toast.makeText(context, "同一层级已存在同名分组", Toast.LENGTH_SHORT).show()
            return false
        }
        val success = if (existing) {
            ConversationGroupStore.updateGroup(context, group)
        } else {
            ConversationGroupStore.addGroup(context, group)
        }
        Toast.makeText(context, if (success) "聊天分组已保存" else "聊天分组保存失败", Toast.LENGTH_SHORT).show()
        if (success) reload()
        return success
    }

    SettingsRouteTransition(
        targetState = route,
        label = "ConversationGroupRoute",
        depthOf = {
            when (it) {
                ConversationGroupRoute.Main -> 0
                is ConversationGroupRoute.Editor -> 1
                is ConversationGroupRoute.AutomaticGrouping -> 1
                is ConversationGroupRoute.ConversationPicker,
                is ConversationGroupRoute.ParentPicker,
                is ConversationGroupRoute.AutomaticPicker -> 2
            }
        }
    ) { currentRoute ->
        when (currentRoute) {
            ConversationGroupRoute.Main -> ConversationGroupListPage(
                title = provider.title(),
                enabled = enabled,
                groups = groups,
                onEnabledChange = {
                    enabled = it
                    ConversationGroupStore.setEnabled(context, it)
                    ConversationGroupRuntime.syncAsync(context)
                },
                onBack = onBack,
                onAdd = {
                    route = ConversationGroupRoute.Editor(
                        ConversationGroupStore.newGroup(parentId = null),
                        existing = false
                    )
                },
                onOpenAutomatic = {
                    val target = groups.firstOrNull { it.automaticGroupingEnabled } ?: groups.firstOrNull()
                    if (target == null) {
                        Toast.makeText(context, "请先新增一个聊天分组", Toast.LENGTH_SHORT).show()
                    } else {
                        route = ConversationGroupRoute.AutomaticGrouping(target)
                    }
                },
                onOpen = { route = ConversationGroupRoute.Editor(it, existing = true) }
            )
            is ConversationGroupRoute.Editor -> ConversationGroupEditorPage(
                context = context,
                group = currentRoute.group,
                groups = groups,
                existing = currentRoute.existing,
                onBack = { route = ConversationGroupRoute.Main },
                onPickParent = {
                    route = ConversationGroupRoute.ParentPicker(it, currentRoute.existing)
                },
                onPickConversations = {
                    route = ConversationGroupRoute.ConversationPicker(it, currentRoute.existing)
                },
                onSelectPathGroup = { draft, groupId ->
                    route = ConversationGroupRoute.Editor(
                        draft.copy(parentId = groupId),
                        currentRoute.existing
                    )
                },
                onSave = { draft ->
                    if (saveGroup(draft, currentRoute.existing)) route = ConversationGroupRoute.Main
                },
                onReorder = { action, draft ->
                    val success = ConversationGroupStore.reorderGroup(
                        context,
                        currentRoute.group.id,
                        action
                    )
                    Toast.makeText(
                        context,
                        if (success) "分组顺序已更新" else "分组排序失败",
                        Toast.LENGTH_SHORT
                    ).show()
                    if (success) {
                        reload()
                        ConversationGroupStore.load(context)
                            .firstOrNull { it.id == currentRoute.group.id }
                            ?.let { latest ->
                                route = ConversationGroupRoute.Editor(
                                    draft.copy(order = latest.order),
                                    existing = true
                                )
                            }
                    }
                },
                onDelete = {
                    val success = ConversationGroupStore.deleteGroup(context, currentRoute.group.id)
                    Toast.makeText(context, if (success) "分组已删除" else "分组删除失败", Toast.LENGTH_SHORT).show()
                    if (success) {
                        reload()
                        route = ConversationGroupRoute.Main
                    }
                }
            )
            is ConversationGroupRoute.ConversationPicker -> ConversationGroupConversationPickerPage(
                context = context,
                group = currentRoute.group,
                groups = groups,
                onBack = {
                    route = ConversationGroupRoute.Editor(currentRoute.group, currentRoute.existing)
                },
                onConfirm = { selected ->
                    route = ConversationGroupRoute.Editor(
                        currentRoute.group.copy(conversationIds = selected),
                        currentRoute.existing
                    )
                }
            )
            is ConversationGroupRoute.ParentPicker -> ConversationGroupParentPickerPage(
                group = currentRoute.group,
                groups = groups,
                onBack = {
                    route = ConversationGroupRoute.Editor(currentRoute.group, currentRoute.existing)
                },
                onSelected = { parentId ->
                    route = ConversationGroupRoute.Editor(
                        currentRoute.group.copy(parentId = parentId),
                        currentRoute.existing
                    )
                }
            )
            is ConversationGroupRoute.AutomaticGrouping -> ConversationGroupAutomaticGroupingPage(
                context = context,
                group = currentRoute.group,
                groups = groups,
                onBack = { route = ConversationGroupRoute.Main },
                onTargetChanged = { group ->
                    route = ConversationGroupRoute.AutomaticGrouping(group)
                },
                onPick = { group, kind ->
                    route = ConversationGroupRoute.AutomaticPicker(group, kind)
                },
                onSave = { group ->
                    if (saveGroup(group, existing = true)) {
                        route = ConversationGroupRoute.Main
                    }
                }
            )
            is ConversationGroupRoute.AutomaticPicker -> ConversationGroupAutomaticPickerPage(
                context = context,
                group = currentRoute.group,
                kind = currentRoute.kind,
                onBack = {
                    route = ConversationGroupRoute.AutomaticGrouping(currentRoute.group)
                },
                onConfirm = { selected ->
                    val updated = when (currentRoute.kind) {
                        ConversationGroupAutomaticPickerKind.GROUPS -> {
                            currentRoute.group.copy(automaticGroupIds = selected)
                        }
                        ConversationGroupAutomaticPickerKind.OFFICIAL_INCLUDE -> {
                            currentRoute.group.copy(automaticOfficialIncludeIds = selected)
                        }
                        ConversationGroupAutomaticPickerKind.OFFICIAL_EXCLUDE -> {
                            currentRoute.group.copy(automaticOfficialExcludeIds = selected)
                        }
                    }
                    route = ConversationGroupRoute.AutomaticGrouping(updated)
                }
            )
        }
    }
}

@Composable
internal fun ConversationGroupListPage(
    title: String,
    enabled: Boolean,
    groups: List<ConversationGroup>,
    onEnabledChange: (Boolean) -> Unit,
    onBack: () -> Unit,
    onAdd: () -> Unit,
    onOpenAutomatic: () -> Unit,
    onOpen: (ConversationGroup) -> Unit
) {
    var query by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val scrollBehavior = MiuixScrollBehavior()
    val flattened = remember(groups) { flattenConversationGroups(groups) }
    val lower = query.trim().lowercase(Locale.US)
    val visible = flattened.filter { row ->
        lower.isEmpty() || row.group.name.lowercase(Locale.US).contains(lower) ||
            row.path.lowercase(Locale.US).contains(lower)
    }
    PageScaffold(
        title = title,
        largeTitle = title,
        scrollBehavior = scrollBehavior,
        bottomBar = {
            BottomActionBar(
                primaryText = "新增分组",
                onPrimaryClick = onAdd,
                secondaryText = "返回",
                onSecondaryClick = onBack
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            state = listState,
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 8.dp,
                bottom = padding.calculateBottomPadding() + 84.dp
            )
        ) {
            item { SmallTitle(text = "功能") }
            item {
                SettingsCard {
                    SwitchRow(
                        checked = enabled,
                        title = "聊天分组",
                        summary = if (enabled) "已归拢会话从微信首页隐藏" else "分组配置保留，会话恢复在微信首页显示",
                        onCheckedChange = onEnabledChange
                    )
                    InsetDivider()
                    ActionRow(
                        title = "群聊归拢",
                        summary = "按规则自动归拢群聊和公众号到指定分组"
                    ) { onOpenAutomatic() }
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "分组") }
            item {
                SettingsCard {
                    InputRow("搜索", "分组名称 / 所属路径", query) { query = it }
                }
            }
            item {
                SettingsCard {
                    when {
                        groups.isEmpty() -> EmptyText("暂无聊天分组")
                        visible.isEmpty() -> EmptyText("没有匹配的聊天分组")
                        else -> visible.forEachIndexed { index, row ->
                            SelectRow(
                                title = "${"  ".repeat(row.depth)}${row.group.name}",
                                summary = buildString {
                                    append(row.group.conversationIds.size).append(" 个直属会话")
                                    val childCount = groups.count { it.parentId == row.group.id }
                                    if (childCount > 0) append(" · ").append(childCount).append(" 个子分组")
                                    if (row.depth > 0) append("\n").append(row.path)
                                },
                                onClick = { onOpen(row.group) }
                            )
                            if (index < visible.lastIndex) InsetDivider()
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun ConversationGroupAutomaticGroupingPage(
    context: Context,
    group: ConversationGroup,
    groups: List<ConversationGroup>,
    onBack: () -> Unit,
    onTargetChanged: (ConversationGroup) -> Unit,
    onPick: (ConversationGroup, ConversationGroupAutomaticPickerKind) -> Unit,
    onSave: (ConversationGroup) -> Unit
) {
    var draft by remember(group) { mutableStateOf(group) }
    val labels = remember(context) { GroupChatLabelStore.load(context) }
    val targetChoices = remember(groups) {
        groups.map { item ->
            PopupChoice(item.name, item.id)
        }
    }
    val listState = rememberLazyListState()
    val scrollBehavior = MiuixScrollBehavior()
    PageScaffold(
        title = "群聊归拢",
        largeTitle = "群聊归拢",
        scrollBehavior = scrollBehavior,
        bottomBar = {
            BottomActionBar(
                primaryText = "保存设置",
                onPrimaryClick = { onSave(draft) },
                secondaryText = "返回",
                onSecondaryClick = onBack
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            state = listState,
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 8.dp,
                bottom = padding.calculateBottomPadding() + 84.dp
            )
        ) {
            item { SmallTitle(text = "归拢目标") }
            item {
                SettingsCard {
                    PopupChoiceRow(
                        title = "目标聊天分组",
                        summary = "匹配到的会话会自动放入此分组",
                        options = targetChoices,
                        currentValue = group.id,
                        onValueChanged = { id ->
                            groups.firstOrNull { it.id == id }?.let(onTargetChanged)
                        }
                    )
                    InsetDivider()
                    SwitchRow(
                        checked = draft.automaticGroupingEnabled,
                        title = "启用群聊归拢",
                        summary = "关闭后自动归拢的会话立即恢复原位置",
                        onCheckedChange = { draft = draft.copy(automaticGroupingEnabled = it) }
                    )
                }
            }
            if (draft.automaticGroupingEnabled) {
                item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "群聊规则") }
                item {
                    SettingsCard {
                        SwitchRow(
                            checked = draft.automaticAllGroups,
                            title = "自动归拢所有群聊",
                            summary = "包含普通群和企业微信群"
                        ) { draft = draft.copy(automaticAllGroups = it) }
                        InsetDivider()
                        SwitchRow(
                            checked = draft.automaticNewGroups,
                            title = "自动归拢新群聊",
                            summary = "首次发现的新群聊自动加入目标分组"
                        ) { draft = draft.copy(automaticNewGroups = it) }
                        InsetDivider()
                        ActionRow(
                            title = "选择群聊",
                            summary = if (draft.automaticGroupIds.isEmpty()) {
                                "未选择群聊"
                            } else {
                                "已选择 ${draft.automaticGroupIds.size} 个群聊"
                            }
                        ) { onPick(draft, ConversationGroupAutomaticPickerKind.GROUPS) }
                        InsetDivider()
                        SwitchRow(
                            checked = draft.automaticMutedGroups,
                            title = "自动归拢免打扰群聊",
                            summary = "仅归拢已开启微信消息免打扰的群聊"
                        ) { draft = draft.copy(automaticMutedGroups = it) }
                        InsetDivider()
                        SwitchRow(
                            checked = draft.automaticOwnedGroups,
                            title = "自动归拢自己创建的群聊",
                            summary = "按群资料中的群主账号识别"
                        ) { draft = draft.copy(automaticOwnedGroups = it) }
                        InsetDivider()
                        SwitchRow(
                            checked = draft.automaticEnterpriseGroups,
                            title = "自动归拢企业微信群聊",
                            summary = "仅归拢企业微信互通群"
                        ) { draft = draft.copy(automaticEnterpriseGroups = it) }
                    }
                }
                item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "群聊标签") }
                item {
                    SettingsCard {
                        if (labels.isEmpty()) {
                            EmptyText("暂无群聊标签")
                        } else {
                            labels.forEachIndexed { index, label ->
                                SwitchRow(
                                    checked = label.id in draft.automaticGroupLabelIds,
                                    title = label.name,
                                    summary = "归拢该标签中的 ${label.groupIds.size} 个群聊"
                                ) { checked ->
                                    draft = draft.copy(
                                        automaticGroupLabelIds = if (checked) {
                                            (draft.automaticGroupLabelIds + label.id).distinct()
                                        } else {
                                            draft.automaticGroupLabelIds.filterNot { it == label.id }
                                        }
                                    )
                                }
                                if (index < labels.lastIndex) InsetDivider()
                            }
                        }
                    }
                }
                item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "公众号") }
                item {
                    SettingsCard {
                        SwitchRow(
                            checked = draft.automaticOfficialAccounts,
                            title = "自动归拢公众号",
                            summary = "归拢当前账号可识别的公众号会话"
                        ) { draft = draft.copy(automaticOfficialAccounts = it) }
                        InsetDivider()
                        ActionRow(
                            title = "包含公众号",
                            summary = if (draft.automaticOfficialIncludeIds.isEmpty()) {
                                "未额外包含公众号"
                            } else {
                                "已包含 ${draft.automaticOfficialIncludeIds.size} 个公众号"
                            }
                        ) { onPick(draft, ConversationGroupAutomaticPickerKind.OFFICIAL_INCLUDE) }
                        InsetDivider()
                        ActionRow(
                            title = "排除公众号",
                            summary = if (draft.automaticOfficialExcludeIds.isEmpty()) {
                                "未排除公众号"
                            } else {
                                "已排除 ${draft.automaticOfficialExcludeIds.size} 个公众号"
                            }
                        ) { onPick(draft, ConversationGroupAutomaticPickerKind.OFFICIAL_EXCLUDE) }
                    }
                }
            }
        }
    }
}

@Composable
internal fun ConversationGroupAutomaticPickerPage(
    context: Context,
    group: ConversationGroup,
    kind: ConversationGroupAutomaticPickerKind,
    onBack: () -> Unit,
    onConfirm: (List<String>) -> Unit
) {
    val initialSelected = remember(group, kind) {
        when (kind) {
            ConversationGroupAutomaticPickerKind.GROUPS -> group.automaticGroupIds.toSet()
            ConversationGroupAutomaticPickerKind.OFFICIAL_INCLUDE -> {
                group.automaticOfficialIncludeIds.toSet()
            }
            ConversationGroupAutomaticPickerKind.OFFICIAL_EXCLUDE -> {
                group.automaticOfficialExcludeIds.toSet()
            }
        }
    }
    var selectedIds by remember(group, kind) { mutableStateOf(initialSelected) }
    var options by remember(kind) { mutableStateOf(emptyList<ContactOption>()) }
    var loading by remember(kind) { mutableStateOf(true) }
    var error by remember(kind) { mutableStateOf("") }
    var query by remember(kind) { mutableStateOf("") }
    val listState = rememberLazyListState()
    val scrollBehavior = MiuixScrollBehavior()
    LaunchedEffect(kind) {
        loading = true
        error = ""
        val result = withContext(Dispatchers.IO) {
            runCatching { loadConversationGroupAutomaticOptions(kind) }
        }
        loading = false
        result.onSuccess { options = it }
            .onFailure { error = it.message ?: "读取联系人失败" }
    }
    val visible = options.filter { it.matchesSearch(query.trim().lowercase(Locale.US)) }
        .selectedFirst { it.id in initialSelected }
    val visibleIds = visible.mapTo(linkedSetOf()) { it.id }
    val visibleAllSelected = visibleIds.isNotEmpty() && visibleIds.all(selectedIds::contains)
    PageScaffold(
        title = kind.title,
        largeTitle = kind.title,
        scrollBehavior = scrollBehavior,
        bottomBar = {
            BottomActionBar(
                primaryText = "保存选择",
                onPrimaryClick = { onConfirm(selectedIds.toList()) },
                secondaryText = "返回",
                onSecondaryClick = onBack,
                middleText = if (visibleIds.isEmpty()) null else if (visibleAllSelected) "取消全选" else "全选",
                onMiddleClick = if (visibleIds.isEmpty()) null else {
                    {
                        selectedIds = if (visibleAllSelected) {
                            selectedIds - visibleIds
                        } else {
                            selectedIds + visibleIds
                        }
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            state = listState,
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 8.dp,
                bottom = padding.calculateBottomPadding() + 84.dp
            )
        ) {
            item {
                SettingsCard {
                    InputRow("搜索", "名称 / 备注 / wxid", query) { query = it }
                }
            }
            item {
                PickerSectionHeader(
                    text = if (visible.isEmpty()) kind.title else "${kind.title} · ${visible.size} 项"
                )
            }
            when {
                loading -> item { SettingsCard { EmptyText("正在载入...") } }
                error.isNotBlank() -> item { SettingsCard { EmptyText(error) } }
                visible.isEmpty() -> item { SettingsCard { EmptyText(kind.emptyText) } }
                else -> visible.forEach { option ->
                    item {
                        ContactListCard(
                            option = option,
                            selected = option.id in selectedIds,
                            multiSelect = true,
                            onClick = {
                                selectedIds = if (option.id in selectedIds) {
                                    selectedIds - option.id
                                } else {
                                    selectedIds + option.id
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

internal fun loadConversationGroupAutomaticOptions(
    kind: ConversationGroupAutomaticPickerKind
): List<ContactOption> {
    val contacts = WeChatApis.contacts() ?: throw IllegalStateException("联系人列表不可用")
    val rows = when (kind) {
        ConversationGroupAutomaticPickerKind.GROUPS -> {
            contacts.getPickerGroups().mapNotNull { it.toOption(group = true) }
        }
        ConversationGroupAutomaticPickerKind.OFFICIAL_INCLUDE,
        ConversationGroupAutomaticPickerKind.OFFICIAL_EXCLUDE -> {
            contacts.getPickerOfficialAccounts().mapNotNull { it.toOption(group = false, official = true) }
        }
    }
    return rows.distinctBy { it.id }
        .sortedBy { it.label.lowercase(Locale.US) }
}

@Composable
internal fun ConversationGroupEditorPage(
    context: Context,
    group: ConversationGroup,
    groups: List<ConversationGroup>,
    existing: Boolean,
    onBack: () -> Unit,
    onPickParent: (ConversationGroup) -> Unit,
    onPickConversations: (ConversationGroup) -> Unit,
    onSelectPathGroup: (ConversationGroup, String?) -> Unit,
    onSave: (ConversationGroup) -> Unit,
    onReorder: (ConversationGroupStore.ReorderAction, ConversationGroup) -> Unit,
    onDelete: () -> Unit
) {
    var name by remember(group) { mutableStateOf(group.name) }
    var pinned by remember(group) { mutableStateOf(group.pinned) }
    var showDeleteConfirm by remember(group.id) { mutableStateOf(false) }
    var showReorderChoices by remember(group.id) { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val scrollBehavior = MiuixScrollBehavior()
    val draft = group.copy(name = name.trim(), pinned = pinned)
    val pathGroups = remember(groups, group.parentId) {
        conversationGroupPathItems(groups, group.parentId)
    }
    val siblings = remember(groups, group.parentId) {
        groups.filter { it.parentId == group.parentId && it.pinned == group.pinned }
            .sortedBy { it.order }
    }
    val siblingIndex = siblings.indexOfFirst { it.id == group.id }
    PageScaffold(
        title = if (existing) "编辑聊天分组" else "新增聊天分组",
        largeTitle = if (existing) "编辑聊天分组" else "新增聊天分组",
        scrollBehavior = scrollBehavior,
        bottomBar = {
            BottomActionBar(
                primaryText = "保存分组",
                onPrimaryClick = {
                    if (draft.name.isBlank()) {
                        Toast.makeText(context, "请输入分组名称", Toast.LENGTH_SHORT).show()
                    } else {
                        onSave(draft)
                    }
                },
                secondaryText = "返回",
                onSecondaryClick = onBack
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            state = listState,
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 8.dp,
                bottom = padding.calculateBottomPadding() + 84.dp
            )
        ) {
            item {
                BreadcrumbBar(
                    items = listOf(BreadcrumbItem(path = "", text = "微信首页")) +
                        pathGroups.map { BreadcrumbItem(path = it.id, text = it.name) },
                    onItemClick = { index ->
                        onSelectPathGroup(
                            draft,
                            if (index == 0) null else pathGroups.getOrNull(index - 1)?.id
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item { SmallTitle(text = "分组内容") }
            item {
                SettingsCard {
                    InputRow("分组名称", "同一层级内不能重名", name) { name = it }
                    if (!existing) {
                        InsetDivider()
                        SwitchRow(
                            checked = pinned,
                            title = "主页置顶",
                            summary = "新建后显示在当前层级的置顶分组区域",
                            onCheckedChange = { pinned = it }
                        )
                    }
                    InsetDivider()
                    ActionRow(
                        title = "上级分组",
                        summary = pathGroups.joinToString(" / ") { it.name }.ifBlank { "微信首页" }
                    ) { onPickParent(draft) }
                    InsetDivider()
                    ActionRow(
                        title = "会话",
                        summary = if (group.conversationIds.isEmpty()) "未选择会话" else "已选择 ${group.conversationIds.size} 个会话"
                    ) { onPickConversations(draft) }
                }
            }
            if (existing) {
                item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "操作") }
                item {
                    SettingsCard {
                        if (siblings.size > 1 && siblingIndex >= 0) {
                            ActionRow(
                                title = "分组位置",
                                summary = "当前${if (group.pinned) "置顶" else "普通"}分组第 " +
                                    "${siblingIndex + 1} 个，共 ${siblings.size} 个"
                            ) { showReorderChoices = true }
                            InsetDivider()
                        }
                        ActionRow(
                            title = "删除分组",
                            summary = "子分组移到上一级，直属会话移到上级分组或微信首页"
                        ) { showDeleteConfirm = true }
                    }
                }
            }
        }
    }
    if (showReorderChoices) {
        val actions = buildList {
            if (siblingIndex > 0) {
                add(Triple("移到顶部", "设为当前层级的第一个分组", ConversationGroupStore.ReorderAction.TOP))
                add(Triple("上移", "向前移动一个位置", ConversationGroupStore.ReorderAction.UP))
            }
            if (siblingIndex in 0 until siblings.lastIndex) {
                add(Triple("下移", "向后移动一个位置", ConversationGroupStore.ReorderAction.DOWN))
                add(Triple("移到底部", "设为当前层级的最后一个分组", ConversationGroupStore.ReorderAction.BOTTOM))
            }
        }
        WindowDialog(
            show = true,
            title = "分组位置",
            onDismissRequest = { showReorderChoices = false },
            content = {
                Column {
                    actions.forEachIndexed { index, (title, summary, action) ->
                        SelectRow(title = title, summary = summary) {
                            showReorderChoices = false
                            onReorder(action, draft)
                        }
                        if (index < actions.lastIndex) InsetDivider()
                    }
                }
            }
        )
    }
    if (showDeleteConfirm) {
        WindowDialog(
            show = true,
            title = "删除聊天分组",
            onDismissRequest = { showDeleteConfirm = false },
            content = {
                Column {
                    Text(
                        text = "确定删除“${group.name}”？聊天记录、联系人和群聊不会被删除。",
                        color = MiuixTheme.colorScheme.onSurface,
                        fontSize = 14.sp
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        TextButton(
                            text = "取消",
                            onClick = { showDeleteConfirm = false },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.textButtonColorsPrimary()
                        )
                        TextButton(
                            text = "确认删除",
                            onClick = {
                                showDeleteConfirm = false
                                onDelete()
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.textButtonColorsPrimary()
                        )
                    }
                }
            }
        )
    }
}

@Composable
internal fun ConversationGroupConversationPickerPage(
    context: Context,
    group: ConversationGroup,
    groups: List<ConversationGroup>,
    onBack: () -> Unit,
    onConfirm: (List<String>) -> Unit
) {
    var loading by remember(group.id) { mutableStateOf(true) }
    var error by remember(group.id) { mutableStateOf("") }
    var pickerData by remember(group.id) { mutableStateOf(ConversationGroupPickerData()) }
    var query by remember(group.id) { mutableStateOf("") }
    var contactFilter by remember(group.id) { mutableStateOf(ContactPickerFilter.ALL) }
    var selectedLabel by remember(group.id) { mutableStateOf("") }
    var selectedConversationGroupId by remember(group.id) { mutableStateOf("") }
    val initialSelected = remember(group) { group.conversationIds.toSet() }
    var selectedIds by remember(group) { mutableStateOf(initialSelected) }
    val listState = rememberLazyListState()
    val scrollBehavior = MiuixScrollBehavior()

    LaunchedEffect(group.id, groups) {
        loading = true
        error = ""
        val result = withContext(Dispatchers.IO) {
            runCatching { loadConversationGroupOptions(groups, group.id) }
        }
        loading = false
        result.onSuccess { pickerData = it }
            .onFailure { error = it.message ?: "读取会话列表失败" }
    }
    val conversations = pickerData.options
    val conversationGroupFilters = remember(groups, conversations) {
        ConversationGroupPickerSupport.filters(groups, conversations.map { it.id })
    }
    val conversationGroupChoices = remember(conversationGroupFilters) {
        listOf(PopupChoice("全部会话", "")) + conversationGroupFilters.map {
            PopupChoice(it.name, it.id)
        }
    }
    LaunchedEffect(conversationGroupFilters) {
        if (selectedConversationGroupId.isNotBlank() &&
            conversationGroupFilters.none { it.id == selectedConversationGroupId }
        ) {
            selectedConversationGroupId = ""
        }
    }
    val selectedConversationIds = remember(conversationGroupFilters, selectedConversationGroupId) {
        conversationGroupFilters.firstOrNull { it.id == selectedConversationGroupId }?.conversationIds
    }
    val scopedConversations = remember(conversations, selectedConversationIds) {
        selectedConversationIds?.let { ids -> conversations.filter { it.id in ids } } ?: conversations
    }
    val labels = remember(scopedConversations) {
        scopedConversations.flatMap { it.labels }
            .distinct()
            .sortedWith(compareBy { it.lowercase(Locale.US) })
    }
    LaunchedEffect(contactFilter, labels) {
        if (contactFilter == ContactPickerFilter.LABELS) {
            if (selectedLabel.isBlank() || selectedLabel !in labels) {
                selectedLabel = labels.firstOrNull().orEmpty()
            }
        } else if (selectedLabel.isNotBlank()) {
            selectedLabel = ""
        }
    }
    val lower = query.trim().lowercase(Locale.US)
    val filtered = scopedConversations.filter {
        when (contactFilter) {
            ContactPickerFilter.FRIENDS -> it.id in pickerData.friendIds
            ContactPickerFilter.GROUPS -> it.group
            ContactPickerFilter.OFFICIALS -> it.id in pickerData.officialIds
            ContactPickerFilter.LABELS -> selectedLabel.isNotBlank() &&
                it.id in pickerData.friendIds &&
                selectedLabel in it.labels
            ContactPickerFilter.ALL -> true
        }
    }
    val visible = filtered.filter { it.matchesSearch(lower) }
        .selectedFirst { initialSelected.contains(it.id) }
    val visibleIds = visible.map { it.id }.toSet()
    val visibleAllSelected = visibleIds.isNotEmpty() && visibleIds.all(selectedIds::contains)
    PageScaffold(
        title = "选择分组会话",
        largeTitle = "选择分组会话",
        scrollBehavior = scrollBehavior,
        bottomBar = {
            BottomActionBar(
                primaryText = "保存选择",
                onPrimaryClick = { onConfirm(selectedIds.toList()) },
                secondaryText = "返回",
                onSecondaryClick = onBack,
                middleText = if (visibleIds.isNotEmpty()) {
                    if (visibleAllSelected) "取消全选" else "全选"
                } else null,
                onMiddleClick = if (visibleIds.isNotEmpty()) {
                    {
                        selectedIds = if (visibleAllSelected) selectedIds - visibleIds else selectedIds + visibleIds
                    }
                } else null
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            state = listState,
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 8.dp,
                bottom = padding.calculateBottomPadding() + 84.dp
            )
        ) {
            item {
                SettingsCard {
                    ContactFilterRow(
                        selected = contactFilter,
                        enableLabels = true,
                        enableOfficials = true,
                        onSelected = {
                            contactFilter = it
                            query = ""
                        }
                    )
                }
            }
            if (contactFilter == ContactPickerFilter.LABELS) {
                item {
                    SettingsCard {
                        ContactLabelFilterRow(
                            labels = labels,
                            selected = selectedLabel,
                            onSelected = {
                                selectedLabel = it
                                query = ""
                            }
                        )
                    }
                }
            }
            if (conversationGroupChoices.size > 1) {
                item {
                    SettingsCard {
                        PopupChoiceRow(
                            title = "聊天分组",
                            summary = "只显示所选分组及其子分组中的会话",
                            options = conversationGroupChoices,
                            currentValue = selectedConversationGroupId,
                            onValueChanged = {
                                selectedConversationGroupId = it
                                query = ""
                            }
                        )
                    }
                }
            }
            item {
                SettingsCard {
                    InputRow("搜索", "会话名称 / 备注 / wxid", query) { query = it }
                }
            }
            item {
                PickerSectionHeader(
                    text = if (visible.isNotEmpty()) {
                        "${contactFilter.title} · ${visible.size} 项"
                    } else {
                        contactFilter.title
                    }
                )
            }
            when {
                loading -> item { SettingsCard { EmptyText("正在载入会话...") } }
                error.isNotBlank() -> item { SettingsCard { EmptyText(error) } }
                visible.isEmpty() -> item { SettingsCard { EmptyText("没有匹配的会话") } }
                else -> visible.forEach { option ->
                    item {
                        ContactListCard(
                            option = option,
                            selected = selectedIds.contains(option.id),
                            multiSelect = true,
                            onClick = {
                                selectedIds = if (selectedIds.contains(option.id)) {
                                    selectedIds - option.id
                                } else {
                                    selectedIds + option.id
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
internal fun ConversationGroupParentPickerPage(
    group: ConversationGroup,
    groups: List<ConversationGroup>,
    onBack: () -> Unit,
    onSelected: (String?) -> Unit
) {
    val excluded = remember(groups, group.id) {
        ConversationGroupStore.descendantIds(groups, group.id) + group.id
    }
    val candidates = remember(groups, excluded) {
        flattenConversationGroups(groups).filterNot { it.group.id in excluded }
    }
    val listState = rememberLazyListState()
    val scrollBehavior = MiuixScrollBehavior()
    PageScaffold(
        title = "选择上级分组",
        largeTitle = "选择上级分组",
        scrollBehavior = scrollBehavior,
        bottomBar = {
            BottomActionBar(
                primaryText = "微信首页",
                onPrimaryClick = { onSelected(null) },
                secondaryText = "返回",
                onSecondaryClick = onBack
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            state = listState,
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 8.dp,
                bottom = padding.calculateBottomPadding() + 84.dp
            )
        ) {
            item { SmallTitle(text = "可用分组") }
            item {
                SettingsCard {
                    if (candidates.isEmpty()) {
                        EmptyText("没有其它可用分组")
                    } else {
                        candidates.forEachIndexed { index, row ->
                            SelectRow(
                                title = "${"  ".repeat(row.depth)}${row.group.name}",
                                summary = row.path,
                                onClick = { onSelected(row.group.id) }
                            )
                            if (index < candidates.lastIndex) InsetDivider()
                        }
                    }
                }
            }
        }
    }
}

internal data class FlattenedConversationGroup(
    val group: ConversationGroup,
    val depth: Int,
    val path: String
)

internal fun flattenConversationGroups(groups: List<ConversationGroup>): List<FlattenedConversationGroup> {
    val normalized = ConversationGroupStore.normalize(groups)
    val byParent = normalized.groupBy { it.parentId }
    val result = arrayListOf<FlattenedConversationGroup>()
    val visited = hashSetOf<String>()
    fun visit(parentId: String?, depth: Int, path: List<String>) {
        byParent[parentId].orEmpty().sortedBy { it.order }.forEach { group ->
            if (!visited.add(group.id)) return@forEach
            val nextPath = path + group.name
            result += FlattenedConversationGroup(group, depth, nextPath.joinToString(" / "))
            visit(group.id, depth + 1, nextPath)
        }
    }
    visit(null, 0, emptyList())
    return result
}

internal fun conversationGroupPathItems(
    groups: List<ConversationGroup>,
    groupId: String?
): List<ConversationGroup> {
    if (groupId.isNullOrBlank()) return emptyList()
    val byId = groups.associateBy { it.id }
    val path = ArrayDeque<ConversationGroup>()
    val visited = HashSet<String>()
    var current = byId[groupId]
    while (current != null && visited.add(current.id)) {
        path.addFirst(current)
        current = current.parentId?.let(byId::get)
    }
    return path.toList()
}

internal fun conversationGroupPath(groups: List<ConversationGroup>, groupId: String): String {
    return flattenConversationGroups(groups).firstOrNull { it.group.id == groupId }?.path.orEmpty()
}

internal data class ConversationGroupPickerData(
    val options: List<ContactOption> = emptyList(),
    val friendIds: Set<String> = emptySet(),
    val officialIds: Set<String> = emptySet()
)

internal fun loadConversationGroupOptions(
    groups: List<ConversationGroup>,
    currentGroupId: String
): ConversationGroupPickerData {
    val conversationApi = WeChatApis.conversations() ?: throw IllegalStateException("会话列表不可用")
    val contactApi = WeChatApis.contact().contacts()
    val assignedIds = groups.flatMap { it.conversationIds }.toSet()
    val friendIds = runCatching {
        contactApi?.getPickerContacts().orEmpty()
            .map { it.wxId.trim() }
            .filter { it.isNotBlank() }
            .toSet()
    }.getOrDefault(emptySet())
    val officialIds = runCatching {
        contactApi?.getPickerOfficialAccounts().orEmpty()
            .map { it.wxId.trim() }
            .filter { it.isNotBlank() }
            .toSet()
    }.getOrDefault(emptySet())
    val labelsByUser = linkedMapOf<String, MutableList<String>>()
    runCatching { contactApi?.getContactLabelList().orEmpty() }
        .getOrDefault(emptyList())
        .forEach { label ->
            val labelName = label.labelName.ifBlank { label.labelId }
            if (labelName.isBlank()) return@forEach
            label.userNameList.forEach { wxId ->
                if (wxId.isNotBlank()) {
                    labelsByUser.getOrPut(wxId) { arrayListOf() }.add(labelName)
                }
            }
        }
    val paths = flattenConversationGroups(groups).associate { it.group.id to it.path }
    val ownerLabels = buildMap {
        groups.filterNot { it.id == currentGroupId }.forEach { owner ->
            owner.conversationIds.forEach { talker ->
                paths[owner.id]?.takeIf(String::isNotBlank)?.let { put(talker, "已在：$it") }
            }
        }
    }
    val rows = conversationApi.getRecentConversationUsernames(10000)
    val result = linkedMapOf<String, ContactOption>()
    rows.forEach { username ->
        val talker = username.trim()
        if (talker.isBlank() || ConversationGroupRuntime.isVirtualTalker(talker) ||
            talker in CONVERSATION_GROUP_EXCLUDED_TALKERS
        ) return@forEach
        val contact = runCatching { contactApi?.getContact(talker) }.getOrNull()
        if (contact == null && talker != "filehelper" && talker !in assignedIds) return@forEach
        val group = contact?.isGroup() == true
        result[talker] = ContactOption(
            id = talker,
            label = contact?.pickerDisplayName(group)
                .orEmpty()
                .ifBlank { conversationApi.getConversationTitle(talker).ifBlank { talker } },
            group = group,
            avatarUrl = contact?.avatarUrl.orEmpty(),
            avatarBackupUrl = contact?.avatarBackupUrl.orEmpty(),
            labels = labelsByUser[talker].orEmpty().distinct(),
            official = contact?.isOfficialAccount() == true,
            extraSummary = ownerLabels[talker].orEmpty(),
            searchAliases = listOfNotNull(
                contact?.remarkName,
                contact?.nickname,
                contact?.customWxId,
                talker
            ).filter { it.isNotBlank() }.distinct()
        )
    }
    assignedIds.filterNot(result::containsKey).forEach { talker ->
        val contact = runCatching { contactApi?.getContact(talker) }.getOrNull()
        val group = contact?.isGroup() == true ||
            talker.endsWith("@chatroom") ||
            talker.endsWith("@im.chatroom")
        result[talker] = ContactOption(
            id = talker,
            label = contact?.pickerDisplayName(group)
                .orEmpty()
                .ifBlank { conversationApi.getConversationTitle(talker).ifBlank { talker } },
            group = group,
            avatarUrl = contact?.avatarUrl.orEmpty(),
            avatarBackupUrl = contact?.avatarBackupUrl.orEmpty(),
            labels = labelsByUser[talker].orEmpty().distinct(),
            official = contact?.isOfficialAccount() == true || talker.startsWith("gh_"),
            extraSummary = ownerLabels[talker].orEmpty(),
            searchAliases = listOfNotNull(
                contact?.remarkName,
                contact?.nickname,
                contact?.customWxId,
                talker
            ).filter { it.isNotBlank() }.distinct()
        )
    }
    return ConversationGroupPickerData(
        options = result.values.toList(),
        friendIds = friendIds,
        officialIds = officialIds + result.values.filter { it.official }.map { it.id }
    )
}

internal val CONVERSATION_GROUP_EXCLUDED_TALKERS = setOf(
    "message_fold",
    "conversationboxservice",
    "officialaccounts",
    "appbrand_notify_message",
    "notifymessage",
    "qmessage",
    "floatbottle"
)
}

internal sealed class TextVoiceRoute {
    object Main : TextVoiceRoute()
    object VoicePicker : TextVoiceRoute()
}

internal object AudioTransformSettingsPage {
    @Composable
    operator fun invoke(
        context: Context,
        provider: FeatureSettingsProvider,
        onBack: () -> Unit
    ) {
    val sp = remember { HchatStorage.preferences(context, AudioTransformSettings.PREFS_NAME) }
    val bridge = remember { AudioTransformBridge() }
    val handler = remember { Handler(Looper.getMainLooper()) }
    val mainListState = rememberLazyListState()
    var route by remember { mutableStateOf<AudioTransformRoute>(AudioTransformRoute.Main) }
    var mode by remember {
        mutableStateOf(
            sp.getInt(
                AudioTransformSettings.KEY_MODE,
                AudioTransformSettings.DEFAULT_MODE
            )
        )
    }
    var inputPath by rememberSaveable { mutableStateOf("") }
    var inputName by rememberSaveable { mutableStateOf("") }
    var selectedTalker by rememberSaveable {
        mutableStateOf(
            sp.getString(AudioTransformSettings.KEY_LAST_TALKER, "").orEmpty()
        )
    }
    var splitEnabled by remember {
        mutableStateOf(
            sp.getBoolean(
                AudioTransformSettings.KEY_SPLIT_ENABLED,
                AudioTransformSettings.DEFAULT_SPLIT_ENABLED
            )
        )
    }
    var splitDurationSeconds by rememberSaveable {
        mutableStateOf(
            sp.getLong(
                AudioTransformSettings.KEY_SPLIT_DURATION_SECONDS,
                AudioTransformSettings.DEFAULT_SPLIT_DURATION_SECONDS
            ).coerceAtLeast(AudioTransformSettings.MIN_SPLIT_DURATION_SECONDS).toString()
        )
    }
    var clipStartMillis by rememberSaveable { mutableStateOf(0L) }
    var clipEndMillis by rememberSaveable { mutableStateOf(0L) }
    val splitCancellation = remember { AtomicBoolean(false) }
    val splitWorker = remember { AtomicReference<Thread?>(null) }
    var running by remember { mutableStateOf(false) }
    var resultText by remember { mutableStateOf("") }

    DisposableEffect(Unit) {
        onDispose {
            splitCancellation.set(true)
            splitWorker.getAndSet(null)?.interrupt()
        }
    }

    fun updateResult(text: String, toast: Boolean = false) {
        resultText = text
        if (toast && text.isNotBlank()) {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }
    }

    fun startSend() {
        if (running) return
        val sourcePath = inputPath.takeIf { it.isNotBlank() && File(it).isFile }
        if (sourcePath == null) {
            updateResult("请先选择输入文件", toast = true)
            return
        }
        val talker = selectedTalker.trim()
        if (talker.isEmpty()) {
            updateResult("请先选择聊天", toast = true)
            return
        }
        val validate = audioTransformValidateInput(mode, sourcePath, bridge)
        if (validate != null) {
            updateResult(validate, toast = true)
            return
        }
        val shouldSplit = splitEnabled
        val segmentDuration = if (shouldSplit) splitDurationSeconds.toLongOrNull() else null
        val rangeStart = clipStartMillis.coerceAtLeast(0L)
        val rangeEnd = clipEndMillis.takeIf { it > 0L }
        if (shouldSplit) {
            if (segmentDuration == null) {
                updateResult("请输入有效的每段时长", toast = true)
                return
            }
            if (rangeEnd != null && rangeEnd <= rangeStart) {
                updateResult("结束时间必须晚于开始时间", toast = true)
                return
            }
            if (segmentDuration < AudioTransformSettings.MIN_SPLIT_DURATION_SECONDS) {
                updateResult(
                    "每段时长最少为 ${AudioTransformSettings.MIN_SPLIT_DURATION_SECONDS} 秒",
                    toast = true
                )
                return
            }
        }
        val talkerLabel = audioTransformTalkerSummary(talker)
        splitCancellation.set(false)
        running = true
        resultText = if (shouldSplit) "正在准备切割" else "正在发送到 $talkerLabel"
        val worker = Thread({
            var sentCount = 0
            var totalCount = 0
            val message = try {
                if (!shouldSplit) {
                    val ok = runCatching {
                        WeChatApis.media()?.voices()?.send(talker, sourcePath) == true
                    }.getOrDefault(false)
                    if (ok) "已发送到 $talkerLabel" else "发送失败，请稍后重试"
                } else {
                    AudioTransformSegmenter.prepare(
                        bridge = bridge,
                        sourcePath = sourcePath,
                        cacheDir = context.cacheDir,
                        durationSeconds = segmentDuration!!,
                        startMillis = rangeStart,
                        endMillis = rangeEnd,
                        cancelled = splitCancellation,
                        onProgress = { progress ->
                            handler.post {
                                if (!splitCancellation.get()) resultText = progress
                            }
                        }
                    ).use { batch ->
                        totalCount = batch.segments.size
                        batch.segments.forEachIndexed { index, segment ->
                            if (splitCancellation.get()) throw AudioTransformCancelledException()
                            handler.post {
                                if (!splitCancellation.get()) {
                                    resultText = "正在发送 ${index + 1}/$totalCount"
                                }
                            }
                            val sent = WeChatApis.media()?.voices()?.send(
                                talker,
                                segment.file.absolutePath,
                                segment.durationMillis
                            ) == true
                            if (sent) sentCount++
                            if (splitCancellation.get()) throw AudioTransformCancelledException()
                            if (!sent) {
                                throw IllegalStateException("第 ${index + 1} 段发送失败")
                            }
                            if (sentCount < totalCount) {
                                repeat(5) {
                                    if (splitCancellation.get()) throw AudioTransformCancelledException()
                                    Thread.sleep(100L)
                                }
                            }
                        }
                    }
                    "已发送 $totalCount 段到 $talkerLabel"
                }
            } catch (_: AudioTransformCancelledException) {
                if (totalCount > 0) "已取消，已发送 $sentCount/$totalCount 段" else "已取消"
            } catch (throwable: Throwable) {
                if (splitCancellation.get() || throwable is InterruptedException) {
                    if (totalCount > 0) "已取消，已发送 $sentCount/$totalCount 段" else "已取消"
                } else {
                    val detail = throwable.message ?: "未知错误"
                    if (totalCount > 0) {
                        "发送失败，已发送 $sentCount/$totalCount 段：$detail"
                    } else {
                        "切割发送失败：$detail"
                    }
                }
            }
            if (shouldSplit) splitWorker.compareAndSet(Thread.currentThread(), null)
            handler.post {
                running = false
                updateResult(message, toast = true)
            }
        }, "Hchat-AudioTransform-Send")
        if (shouldSplit) splitWorker.set(worker)
        worker.start()
    }

    fun startSave(outputUri: Uri, outputName: String) {
        if (running) return
        val sourcePath = inputPath.takeIf { it.isNotBlank() && File(it).isFile }
        if (sourcePath == null) {
            updateResult("请先选择输入文件", toast = true)
            return
        }
        val validate = audioTransformValidateInput(mode, sourcePath, bridge)
        if (validate != null) {
            updateResult(validate, toast = true)
            return
        }
        running = true
        resultText = "正在转换并保存"
        Thread({
            val sourceFile = File(sourcePath)
            val tempDir = File(context.cacheDir, "audio_transform_output").apply { mkdirs() }
            val tempFile = File(
                tempDir,
                "audio_${System.currentTimeMillis()}${audioTransformOutputExtension(mode)}"
            )
            val result = runCatching {
                val code = when (mode) {
                    AudioTransformSettings.MODE_AUDIO_TO_SILK_SAVE -> {
                        if (bridge.getFileType(sourcePath) == 1) {
                            sourceFile.copyTo(tempFile, overwrite = true)
                            0
                        } else {
                            bridge.autoToSilk(sourcePath, tempFile.absolutePath, AudioTransformBridge.DEFAULT_HZ)
                        }
                    }
                    AudioTransformSettings.MODE_SILK_TO_MP3_SAVE -> {
                        bridge.silkToMp3(sourcePath, tempFile.absolutePath, AudioTransformBridge.DEFAULT_HZ)
                    }
                    AudioTransformSettings.MODE_SILK_TO_M4A_SAVE -> {
                        bridge.silkToM4a(sourcePath, tempFile.absolutePath, AudioTransformBridge.DEFAULT_HZ)
                    }
                    else -> -2
                }
                if (code != 0) {
                    false to "转换失败：${bridge.getErrorMessage(code)}"
                } else if (!tempFile.isFile || tempFile.length() <= 0L) {
                    false to "转换失败：输出文件为空"
                } else if (!copyAudioTransformFileToUri(context, tempFile, outputUri)) {
                    false to "保存失败：无法写入目标文件"
                } else {
                    true to "已保存为 ${outputName.ifBlank { audioTransformSuggestedFileName(inputName, mode) }}"
                }
            }.getOrElse {
                false to "转换异常：${it.message ?: "未知错误"}"
            }
            if (tempFile.exists()) {
                runCatching { tempFile.delete() }
            }
            handler.post {
                running = false
                updateResult(result.second, toast = true)
            }
        }, "Hchat-AudioTransform-Save").start()
    }

    fun launchSavePicker() {
        if (running) return
        val sourcePath = inputPath.takeIf { it.isNotBlank() && File(it).isFile }
        if (sourcePath == null) {
            updateResult("请先选择输入文件", toast = true)
            return
        }
        val activity = context as? Activity
        if (activity == null) {
            updateResult("当前页面无法打开保存选择器", toast = true)
            return
        }
        AudioTransformDocumentBridge.launchOutput(
            activity = activity,
            suggestedFileName = audioTransformSuggestedFileName(inputName, mode),
            mimeType = audioTransformOutputMime(mode)
        ) { uri, name ->
            startSave(uri, name)
        }
    }

    SettingsRouteTransition(
        targetState = route,
        label = "AudioTransformRoute",
        depthOf = { it.depth() }
    ) { currentRoute ->
        when (currentRoute) {
            AudioTransformRoute.Main -> {
                AudioTransformMainPage(
                    provider = provider,
                    mode = mode,
                    inputPath = inputPath,
                    inputName = inputName,
                    talker = selectedTalker,
                    splitEnabled = splitEnabled,
                    splitDurationSeconds = splitDurationSeconds,
                    clipStartMillis = clipStartMillis,
                    clipEndMillis = clipEndMillis,
                    running = running,
                    resultText = resultText,
                    listState = mainListState,
                    onBack = onBack,
                    onModeChange = { next ->
                        mode = next
                        sp.edit().putInt(AudioTransformSettings.KEY_MODE, next).apply()
                    },
                    onPickInput = {
                        val activity = context as? Activity
                        if (activity == null) {
                            updateResult("当前页面无法打开文件选择器", toast = true)
                        } else {
                            AudioTransformDocumentBridge.launchInput(activity) { path, name ->
                                inputPath = path
                                inputName = name
                                clipStartMillis = 0L
                                clipEndMillis = 0L
                                resultText = ""
                            }
                        }
                    },
                    onPickTalker = { route = AudioTransformRoute.ContactPicker },
                    onSplitEnabledChange = { enabled ->
                        splitEnabled = enabled
                        sp.edit().putBoolean(AudioTransformSettings.KEY_SPLIT_ENABLED, enabled).apply()
                    },
                    onSplitDurationChange = { next ->
                        splitDurationSeconds = next
                        next.toLongOrNull()
                            ?.takeIf { it >= AudioTransformSettings.MIN_SPLIT_DURATION_SECONDS }
                            ?.let { duration ->
                                sp.edit().putLong(
                                    AudioTransformSettings.KEY_SPLIT_DURATION_SECONDS,
                                    duration
                                ).apply()
                            }
                    },
                    onClipRangeChange = { source, start, end ->
                        if (inputPath == source) {
                            clipStartMillis = start.coerceAtLeast(0L)
                            clipEndMillis = end.coerceAtLeast(0L)
                        }
                    },
                    onClearInput = {
                        inputPath = ""
                        inputName = ""
                        clipStartMillis = 0L
                        clipEndMillis = 0L
                    },
                    onCancel = {
                        splitCancellation.set(true)
                        splitWorker.get()?.interrupt()
                        resultText = "正在取消"
                    },
                    onStart = {
                        if (mode == AudioTransformSettings.MODE_AUDIO_TO_SILK_SEND) {
                            startSend()
                        } else {
                            launchSavePicker()
                        }
                    }
                )
            }
            AudioTransformRoute.ContactPicker -> {
                ContactPickerPage(
                    context = context,
                    request = ContactPickerRequest(
                        title = "选择发送聊天",
                        mode = ContactPickerMode.ALL_CHATS,
                        multiSelect = false,
                        existingValue = formatIds(listOfNotNull(selectedTalker.takeIf { it.isNotBlank() })),
                        onValue = {}
                    ),
                    onBack = { route = AudioTransformRoute.Main },
                    onConfirm = { selected ->
                        selectedTalker = selected.firstOrNull()?.id.orEmpty()
                        sp.edit().putString(AudioTransformSettings.KEY_LAST_TALKER, selectedTalker).apply()
                        route = AudioTransformRoute.Main
                    }
                )
            }
        }
    }
}

@Composable
internal fun AudioTransformMainPage(
    provider: FeatureSettingsProvider,
    mode: Int,
    inputPath: String,
    inputName: String,
    talker: String,
    splitEnabled: Boolean,
    splitDurationSeconds: String,
    clipStartMillis: Long,
    clipEndMillis: Long,
    running: Boolean,
    resultText: String,
    listState: LazyListState,
    onBack: () -> Unit,
    onModeChange: (Int) -> Unit,
    onPickInput: () -> Unit,
    onPickTalker: () -> Unit,
    onSplitEnabledChange: (Boolean) -> Unit,
    onSplitDurationChange: (String) -> Unit,
    onClipRangeChange: (String, Long, Long) -> Unit,
    onClearInput: () -> Unit,
    onCancel: () -> Unit,
    onStart: () -> Unit
) {
    val scrollBehavior = MiuixScrollBehavior()
    val actionText = if (mode == AudioTransformSettings.MODE_AUDIO_TO_SILK_SEND) "开始发送" else "开始转换"
    val cancellable = running && mode == AudioTransformSettings.MODE_AUDIO_TO_SILK_SEND && splitEnabled
    PageScaffold(
        title = provider.title(),
        largeTitle = provider.title(),
        scrollBehavior = scrollBehavior,
        bottomBar = {
            BottomActionBar(
                primaryText = actionText,
                onPrimaryClick = onStart,
                secondaryText = if (cancellable) "取消" else "返回",
                onSecondaryClick = if (cancellable) onCancel else onBack
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            state = listState,
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 8.dp,
                bottom = padding.calculateBottomPadding() + 84.dp
            )
        ) {
            item { SmallTitle(text = "转换方式") }
            item {
                SettingsCard {
                    PopupChoiceRow(
                        title = "操作",
                        summary = audioTransformModeSummary(mode),
                        options = audioTransformModeOptions(),
                        currentValue = mode.toString(),
                        onValueChanged = { next -> onModeChange(next.toIntOrNull() ?: mode) },
                        enabled = !running
                    )
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "文件") }
            item {
                SettingsCard {
                    ActionRow("选择输入文件", audioTransformInputSummary(inputPath, inputName)) {
                        if (!running) onPickInput()
                    }
                    if (inputPath.isNotBlank()) {
                        InsetDivider()
                        AudioTransformPreviewEditor(
                            inputPath = inputPath,
                            inputName = inputName,
                            clipEnabled = mode == AudioTransformSettings.MODE_AUDIO_TO_SILK_SEND &&
                                splitEnabled,
                            clipStartMillis = clipStartMillis,
                            clipEndMillis = clipEndMillis,
                            enabled = !running,
                            onRangeChange = onClipRangeChange
                        )
                        InsetDivider()
                        ActionRow("清空输入文件", "移除当前已选文件") {
                            if (!running) onClearInput()
                        }
                    }
                    if (mode != AudioTransformSettings.MODE_AUDIO_TO_SILK_SEND) {
                        InsetDivider()
                        InfoRow("输出文件", audioTransformSuggestedFileName(inputName, mode))
                    }
                }
            }
            if (mode == AudioTransformSettings.MODE_AUDIO_TO_SILK_SEND) {
                item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "发送") }
                item {
                    SettingsCard {
                        ActionRow("选择聊天", audioTransformTalkerSummary(talker)) {
                            if (!running) onPickTalker()
                        }
                        InsetDivider()
                        SwitchRow(
                            checked = splitEnabled,
                            title = "裁剪与自动分割",
                            summary = "按选定起止区间裁剪，并按时长依次发送",
                            enabled = !running,
                            onCheckedChange = onSplitEnabledChange
                        )
                        if (splitEnabled) {
                            InsetDivider()
                            NumberInputRow(
                                title = "自动分割（秒）",
                                summary = "单位秒，最少 1 秒",
                                value = splitDurationSeconds,
                                onValueChange = onSplitDurationChange
                            )
                        }
                    }
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "状态") }
            item {
                SettingsCard {
                    InfoRow("当前状态", if (running) "处理中" else "待开始")
                    if (resultText.isNotBlank()) {
                        InsetDivider()
                        InfoRow("最近结果", resultText)
                    }
                }
            }
        }
    }
}

@Composable
internal fun AudioTransformPreviewEditor(
    inputPath: String,
    inputName: String,
    clipEnabled: Boolean,
    clipStartMillis: Long,
    clipEndMillis: Long,
    enabled: Boolean,
    onRangeChange: (String, Long, Long) -> Unit
) {
    val context = LocalContext.current
    val currentRangeChange by rememberUpdatedState(onRangeChange)
    val currentClipStart by rememberUpdatedState(clipStartMillis)
    val currentClipEnd by rememberUpdatedState(clipEndMillis)
    val currentClipEnabled by rememberUpdatedState(clipEnabled)
    var playablePath by remember(inputPath) { mutableStateOf("") }
    var previewPreparing by remember(inputPath) { mutableStateOf(true) }
    var previewError by remember(inputPath) { mutableStateOf("") }
    var player by remember(inputPath) { mutableStateOf<MediaPlayer?>(null) }
    var prepared by remember(inputPath) { mutableStateOf(false) }
    var playing by remember(inputPath) { mutableStateOf(false) }
    var durationMillis by remember(inputPath) { mutableStateOf(0) }
    var positionMillis by remember(inputPath) { mutableStateOf(0) }
    var playerError by remember(inputPath) { mutableStateOf("") }

    LaunchedEffect(inputPath, enabled) {
        playablePath = ""
        previewError = ""
        playerError = ""
        if (!enabled) {
            previewPreparing = false
            return@LaunchedEffect
        }
        previewPreparing = true
        var generatedPreview: File? = null
        try {
            val resolved = withContext(Dispatchers.IO) {
                val source = File(inputPath)
                check(source.isFile) { "输入文件不存在" }
                val previewBridge = AudioTransformBridge()
                if (previewBridge.getFileType(inputPath) != 1) {
                    inputPath
                } else {
                    val previewDir = File(context.cacheDir, "audio_transform_preview").apply { mkdirs() }
                    val output = File(
                        previewDir,
                        "preview_${System.currentTimeMillis()}_${System.nanoTime()}.m4a"
                    )
                    generatedPreview = output
                    val code = previewBridge.silkToM4a(
                        inputPath,
                        output.absolutePath,
                        AudioTransformBridge.DEFAULT_HZ
                    )
                    check(code == 0 && output.isFile && output.length() > 0L) {
                        "试听准备失败：${previewBridge.getErrorMessage(code)}"
                    }
                    output.absolutePath
                }
            }
            playablePath = resolved
            previewPreparing = false
            awaitCancellation()
        } catch (cancelled: CancellationException) {
            throw cancelled
        } catch (throwable: Throwable) {
            previewPreparing = false
            previewError = throwable.message ?: "试听准备失败"
        } finally {
            generatedPreview?.let { runCatching { it.delete() } }
        }
    }

    DisposableEffect(playablePath, enabled) {
        val path = playablePath
        var effectPlayer: MediaPlayer? = null
        prepared = false
        playing = false
        if (path.isNotBlank() && enabled) {
            runCatching {
                MediaPlayer().also { media ->
                    effectPlayer = media
                    media.setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build()
                    )
                    media.setDataSource(path)
                    media.setOnPreparedListener { preparedPlayer ->
                        if (player !== preparedPlayer) return@setOnPreparedListener
                        val duration = preparedPlayer.duration.coerceAtLeast(0)
                        durationMillis = duration
                        prepared = duration > 0
                        playing = false
                        val maxStart = (duration - 1).coerceAtLeast(0).toLong()
                        val start = currentClipStart.coerceIn(0L, maxStart)
                        val end = currentClipEnd
                            .takeIf { it > start }
                            ?.coerceAtMost(duration.toLong())
                            ?: duration.toLong()
                        positionMillis = start.toInt()
                        runCatching { preparedPlayer.seekTo(positionMillis) }
                        currentRangeChange(inputPath, start, end)
                    }
                    media.setOnCompletionListener { completed ->
                        if (player !== completed) return@setOnCompletionListener
                        playing = false
                        val reset = if (currentClipEnabled) {
                            currentClipStart.coerceIn(
                                0L,
                                completed.duration.coerceAtLeast(0).toLong()
                            ).toInt()
                        } else {
                            0
                        }
                        runCatching { completed.seekTo(reset) }
                        positionMillis = reset
                    }
                    media.setOnErrorListener { failed, _, _ ->
                        if (player !== failed) return@setOnErrorListener true
                        prepared = false
                        playing = false
                        playerError = "当前音频无法试听"
                        true
                    }
                    player = media
                    media.prepareAsync()
                }
            }.onFailure {
                val failedPlayer = effectPlayer
                effectPlayer = null
                if (player === failedPlayer) player = null
                failedPlayer?.let { media ->
                    media.setOnPreparedListener(null)
                    media.setOnCompletionListener(null)
                    media.setOnErrorListener(null)
                    runCatching { media.release() }
                }
                prepared = false
                playing = false
                playerError = "当前音频无法试听"
            }
        }
        onDispose {
            val disposingPlayer = effectPlayer
            effectPlayer = null
            disposingPlayer?.let { media ->
                media.setOnPreparedListener(null)
                media.setOnCompletionListener(null)
                media.setOnErrorListener(null)
                runCatching { if (media.isPlaying) media.stop() }
                runCatching { media.release() }
            }
            if (player === disposingPlayer) player = null
            prepared = false
            playing = false
        }
    }

    LaunchedEffect(enabled, player, playing, prepared, clipEnabled, clipStartMillis, clipEndMillis) {
        val media = player
        if (!enabled && media != null && playing) {
            runCatching { media.pause() }
            playing = false
        }
        while (enabled && prepared && playing && media != null) {
            val current = runCatching { media.currentPosition.coerceAtLeast(0) }
                .getOrDefault(positionMillis)
            val end = if (clipEnabled) {
                clipEndMillis.takeIf { it > clipStartMillis }
                    ?.coerceAtMost(durationMillis.toLong())
                    ?.toInt()
                    ?: durationMillis
            } else {
                durationMillis
            }
            if (end > 0 && current >= end) {
                runCatching { media.pause() }
                val reset = if (clipEnabled) {
                    clipStartMillis.coerceIn(0L, end.toLong()).toInt()
                } else {
                    0
                }
                runCatching { media.seekTo(reset) }
                positionMillis = reset
                playing = false
                break
            }
            positionMillis = current
            delay(80L)
        }
    }

    fun seekTo(target: Int) {
        val media = player ?: return
        if (!prepared || durationMillis <= 0) return
        val maxStart = (durationMillis - 1).coerceAtLeast(0).toLong()
        val rangeStart = clipStartMillis.coerceIn(0L, maxStart).toInt()
        val rangeEnd = clipEndMillis
            .takeIf { it > rangeStart }
            ?.coerceAtMost(durationMillis.toLong())
            ?.toInt()
            ?: durationMillis
        val lowerBound = if (playing && clipEnabled) rangeStart else 0
        val upperBound = if (playing && clipEnabled) rangeEnd else durationMillis
        val next = target.coerceIn(lowerBound, upperBound.coerceAtLeast(lowerBound))
        runCatching { media.seekTo(next) }
        positionMillis = next
    }

    val effectiveClipStart = clipStartMillis.coerceIn(0L, durationMillis.coerceAtLeast(0).toLong())
    val effectiveClipEnd = clipEndMillis
        .takeIf { it > effectiveClipStart }
        ?.coerceAtMost(durationMillis.toLong())
        ?: durationMillis.toLong()
    val statusText = when {
        previewPreparing -> "正在准备试听"
        previewError.isNotBlank() -> previewError
        playerError.isNotBlank() -> playerError
        !enabled -> "处理中"
        !prepared -> "正在加载"
        playing -> "播放中"
        else -> "可试听"
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 14.dp)) {
            Text(
                text = inputName.ifBlank { File(inputPath).name.ifBlank { "已选音频" } },
                color = MiuixTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val playEnabled = enabled && prepared
                Box(
                    modifier = Modifier.size(40.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            if (playEnabled) {
                                MiuixTheme.colorScheme.primary
                            } else {
                                MiuixTheme.colorScheme.onSurface.copy(alpha = 0.08f)
                            }
                        )
                        .clickable(enabled = playEnabled) {
                            val media = player ?: return@clickable
                            if (playing) {
                                runCatching { media.pause() }
                                playing = false
                            } else {
                                val start = if (clipEnabled) {
                                    effectiveClipStart.toInt()
                                } else {
                                    0
                                }
                                val end = if (clipEnabled) {
                                    effectiveClipEnd.toInt()
                                } else {
                                    durationMillis
                                }
                                if (positionMillis < start || positionMillis >= end) {
                                    seekTo(start)
                                }
                                runCatching {
                                    media.start()
                                    playing = true
                                }.onFailure {
                                    playerError = "当前音频无法试听"
                                    playing = false
                                }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        imageVector = if (playing) NavIcons.Pause else NavIcons.Play,
                        contentDescription = if (playing) "暂停" else "播放",
                        colorFilter = ColorFilter.tint(
                            if (playEnabled) Color.White else MiuixTheme.colorScheme.onSurfaceVariantSummary
                        ),
                        modifier = Modifier.size(22.dp)
                    )
                }
                Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
                    Text(
                        text = "${formatAudioTransformTime(positionMillis.toLong())} / " +
                            formatAudioTransformTime(durationMillis.toLong()),
                        color = MiuixTheme.colorScheme.onSurface,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = statusText,
                        color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Slider(
                value = positionMillis.coerceIn(0, durationMillis.coerceAtLeast(0)).toFloat(),
                onValueChange = { next ->
                    if (enabled && prepared) seekTo(next.toInt())
                },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                    .graphicsLayer(alpha = if (enabled && prepared) 1f else 0.45f),
                valueRange = 0f..durationMillis.coerceAtLeast(1).toFloat(),
                steps = 0,
                showKeyPoints = false
            )
            if (clipEnabled && durationMillis > 0) {
                Text(
                    text = "选定区间  ${formatAudioTransformTime(effectiveClipStart)} - " +
                        formatAudioTransformTime(effectiveClipEnd),
                    color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
        if (clipEnabled) {
            InsetDivider()
            ActionRow(
                title = "开始时间",
                summary = if (prepared) {
                    formatAudioTransformTime(effectiveClipStart)
                } else {
                    "等待音频加载"
                }
            ) {
                if (!enabled || !prepared) return@ActionRow
                if (positionMillis.toLong() >= effectiveClipEnd) {
                    Toast.makeText(context, "开始时间必须早于结束时间", Toast.LENGTH_SHORT).show()
                } else {
                    currentRangeChange(inputPath, positionMillis.toLong(), effectiveClipEnd)
                }
            }
            InsetDivider()
            ActionRow(
                title = "结束时间",
                summary = if (prepared) {
                    formatAudioTransformTime(effectiveClipEnd)
                } else {
                    "等待音频加载"
                }
            ) {
                if (!enabled || !prepared) return@ActionRow
                if (positionMillis.toLong() <= effectiveClipStart) {
                    Toast.makeText(context, "结束时间必须晚于开始时间", Toast.LENGTH_SHORT).show()
                } else {
                    currentRangeChange(inputPath, effectiveClipStart, positionMillis.toLong())
                }
            }
            InsetDivider()
            ActionRow("重新设置", "恢复完整音频区间") {
                if (!enabled || !prepared) return@ActionRow
                currentRangeChange(inputPath, 0L, durationMillis.toLong())
                seekTo(0)
            }
        }
    }
}

internal fun formatAudioTransformTime(millis: Long): String {
    val totalSeconds = millis.coerceAtLeast(0L) / 1000L
    val seconds = totalSeconds % 60L
    val minutes = totalSeconds / 60L % 60L
    val hours = totalSeconds / 3600L
    return if (hours > 0L) {
        String.format(Locale.ROOT, "%d:%02d:%02d", hours, minutes, seconds)
    } else {
        String.format(Locale.ROOT, "%02d:%02d", minutes, seconds)
    }
}

internal fun audioTransformModeOptions(): List<PopupChoice<String>> = listOf(
    PopupChoice("任意音频转silk保存", AudioTransformSettings.MODE_AUDIO_TO_SILK_SAVE.toString()),
    PopupChoice("任意音频转silk发送", AudioTransformSettings.MODE_AUDIO_TO_SILK_SEND.toString()),
    PopupChoice("Silk 转 MP3 保存", AudioTransformSettings.MODE_SILK_TO_MP3_SAVE.toString()),
    PopupChoice("Silk 转 M4A 保存", AudioTransformSettings.MODE_SILK_TO_M4A_SAVE.toString())
)

internal fun audioTransformModeSummary(mode: Int): String = when (mode) {
    AudioTransformSettings.MODE_AUDIO_TO_SILK_SEND -> "把任意音频转成微信语音并直接发送"
    AudioTransformSettings.MODE_SILK_TO_MP3_SAVE -> "把 Silk 语音导出为 MP3 文件"
    AudioTransformSettings.MODE_SILK_TO_M4A_SAVE -> "把 Silk 语音导出为 M4A 文件"
    else -> "把任意音频转成 Silk 并保存到本地"
}

internal fun audioTransformValidateInput(
    mode: Int,
    inputPath: String,
    bridge: AudioTransformBridge
): String? {
    if (!File(inputPath).isFile) return "输入文件不存在"
    if (mode == AudioTransformSettings.MODE_SILK_TO_MP3_SAVE ||
        mode == AudioTransformSettings.MODE_SILK_TO_M4A_SAVE
    ) {
        val fileType = bridge.getFileType(inputPath)
        if (fileType != 1) return "当前模式只支持 Silk 输入"
    }
    return null
}

internal fun audioTransformSuggestedFileName(inputName: String, mode: Int): String {
    val rawName = inputName.trim().ifBlank { "audio_output" }
    val base = rawName.substringBeforeLast('.').ifBlank { rawName }
    return base + audioTransformOutputExtension(mode)
}

internal fun audioTransformOutputExtension(mode: Int): String = when (mode) {
    AudioTransformSettings.MODE_SILK_TO_MP3_SAVE -> ".mp3"
    AudioTransformSettings.MODE_SILK_TO_M4A_SAVE -> ".m4a"
    else -> ".silk"
}

internal fun audioTransformOutputMime(mode: Int): String = when (mode) {
    AudioTransformSettings.MODE_SILK_TO_MP3_SAVE -> "audio/mpeg"
    AudioTransformSettings.MODE_SILK_TO_M4A_SAVE -> "audio/mp4"
    else -> "application/octet-stream"
}

internal fun audioTransformInputSummary(inputPath: String, inputName: String): String {
    if (inputPath.isBlank()) return "未选择"
    return inputName.ifBlank { File(inputPath).name.ifBlank { "已选择文件" } }
}

internal fun audioTransformTalkerSummary(talker: String): String {
    if (talker.isBlank()) return "未选择"
    val display = runCatching { WeChatApis.contacts()?.getDisplayName(talker) }.getOrNull().orEmpty()
    return if (display.isBlank() || display == talker) talker else "$display · $talker"
}

internal fun copyAudioTransformFileToUri(context: Context, source: File, uri: Uri): Boolean {
    return runCatching {
        context.contentResolver.openOutputStream(uri, "w")?.use { output ->
            source.inputStream().use { input -> input.copyTo(output) }
        } != null
    }.getOrDefault(false)
}
}

internal object PluginMarketUi {

internal enum class PluginMarketUploadPhase {
    QUEUED,
    UPLOADING,
    SUCCESS,
    PENDING_REVIEW,
    FAILED
}

internal data class PluginMarketUploadStatus(
    val phase: PluginMarketUploadPhase,
    val message: String
)

@Composable
fun ScriptPluginMarketPage(
    context: Context,
    onBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val scrollBehavior = MiuixScrollBehavior()
    var query by rememberSaveable { mutableStateOf("") }
    var sort by rememberSaveable { mutableStateOf("latest") }
    var refreshVersion by remember { mutableStateOf(0) }
    var loading by remember { mutableStateOf(true) }
    var loadError by remember { mutableStateOf("") }
    var plugins by remember { mutableStateOf<List<PluginMarketPlugin>>(emptyList()) }
    var pluginCount by remember { mutableStateOf(0) }
    var selectedPlugin by remember { mutableStateOf<PluginMarketPlugin?>(null) }
    var detailPlugin by remember { mutableStateOf<PluginMarketPlugin?>(null) }
    var detailLoading by remember { mutableStateOf(false) }
    var detailError by remember { mutableStateOf("") }
    var detailRetryVersion by remember { mutableStateOf(0) }
    var historyVersions by remember { mutableStateOf<List<PluginMarketHistoryVersion>>(emptyList()) }
    var historyLoading by remember { mutableStateOf(false) }
    var historyError by remember { mutableStateOf("") }
    var historyInstallVersionId by remember { mutableStateOf<String?>(null) }
    var comments by remember { mutableStateOf<List<PluginMarketComment>>(emptyList()) }
    var commentsLoading by remember { mutableStateOf(false) }
    var commentsError by remember { mutableStateOf("") }
    var commentsRetryVersion by remember { mutableStateOf(0) }
    var currentUserIdentity by remember { mutableStateOf<PluginMarketUserIdentity?>(null) }
    var interactionError by remember { mutableStateOf("") }
    var likedByCurrentUser by remember { mutableStateOf(false) }
    var followingByCurrentUser by remember { mutableStateOf(false) }
    var likeCountLoaded by remember { mutableStateOf(false) }
    var commentCountLoaded by remember { mutableStateOf(false) }
    var updatingLike by remember { mutableStateOf(false) }
    var updatingFollow by remember { mutableStateOf(false) }
    var commentDraft by remember { mutableStateOf("") }
    var submittingComment by remember { mutableStateOf(false) }
    var deletingCommentId by remember { mutableStateOf<String?>(null) }
    var detailTargetExists by remember { mutableStateOf(false) }
    var installingRemoteId by remember { mutableStateOf<String?>(null) }
    var deletingRemoteId by remember { mutableStateOf<String?>(null) }
    var installMessage by remember { mutableStateOf("") }
    var pendingOverwrite by remember { mutableStateOf<PluginMarketPlugin?>(null) }
    var showUploadDialog by remember { mutableStateOf(false) }
    var notifications by remember { mutableStateOf<List<PluginMarketNotification>>(emptyList()) }
    var notificationUnreadCount by remember { mutableStateOf(0L) }
    var notificationsLoading by remember { mutableStateOf(true) }
    var notificationsError by remember { mutableStateOf("") }
    var notificationRefreshVersion by remember { mutableStateOf(0) }
    var showNotifications by remember { mutableStateOf(false) }

    fun updateSocialCounts(
        remotePluginId: String,
        likeCount: Long? = null,
        commentCount: Long? = null
    ) {
        fun updated(plugin: PluginMarketPlugin): PluginMarketPlugin = plugin.copy(
            likeCount = likeCount ?: plugin.likeCount,
            commentCount = commentCount ?: plugin.commentCount
        )
        detailPlugin = detailPlugin?.let { if (it.remotePluginId == remotePluginId) updated(it) else it }
        selectedPlugin = selectedPlugin?.let { if (it.remotePluginId == remotePluginId) updated(it) else it }
        plugins = plugins.map { if (it.remotePluginId == remotePluginId) updated(it) else it }
    }

    LaunchedEffect(query, sort, refreshVersion) {
        delay(250L)
        loading = true
        loadError = ""
        val result = withContext(Dispatchers.IO) {
            PluginMarketRepository.list(
                context = context,
                query = query.trim(),
                sort = sort,
                limit = Int.MAX_VALUE
            )
        }
        result.fold(
            onSuccess = { page ->
                plugins = page.items
                pluginCount = page.count
            },
            onFailure = { error ->
                loadError = pluginMarketErrorMessage(error)
                if (plugins.isEmpty()) pluginCount = 0
            }
        )
        loading = false
    }

    LaunchedEffect(notificationRefreshVersion) {
        notificationsLoading = true
        notificationsError = ""
        val result = withContext(Dispatchers.IO) {
            PluginMarketRepository.notifications(context, limit = Int.MAX_VALUE)
        }
        result.fold(
            onSuccess = { page ->
                notifications = page.items
                notificationUnreadCount = page.unreadCount
            },
            onFailure = { error ->
                notificationsError = pluginMarketErrorMessage(error)
                if (notifications.isEmpty()) notificationUnreadCount = 0L
            }
        )
        notificationsLoading = false
    }

    LaunchedEffect(showNotifications, notificationsLoading) {
        if (!showNotifications || notificationsLoading) return@LaunchedEffect
        val unreadIds = notifications.filterNot { it.read }.map { it.notificationId }
        if (unreadIds.isEmpty()) return@LaunchedEffect
        val result = withContext(Dispatchers.IO) {
            PluginMarketRepository.markNotificationsRead(context, unreadIds)
        }
        result.onSuccess { readResult ->
            notificationUnreadCount = readResult.unreadCount
            val markedIds = unreadIds.toHashSet()
            notifications = notifications.map { item ->
                if (item.notificationId in markedIds) item.copy(read = true) else item
            }
        }
    }

    LaunchedEffect(selectedPlugin?.remotePluginId, detailRetryVersion) {
        val selected = selectedPlugin ?: return@LaunchedEffect
        detailLoading = true
        detailError = ""
        detailPlugin = null
        historyLoading = true
        historyError = ""
        historyVersions = emptyList()
        detailTargetExists = false
        installMessage = ""
        val (detailResult, historyResult) = withContext(Dispatchers.IO) {
            val loadedDetail = PluginMarketRepository.detail(context, selected.remotePluginId).map { detail ->
                val exists = PluginMarketInstaller.existingLocalPluginId(context, detail) != null
                detail to exists
            }
            loadedDetail to PluginMarketRepository.history(context, selected.remotePluginId)
        }
        detailResult.fold(
            onSuccess = { (detail, exists) ->
                val currentSummary = selectedPlugin?.takeIf {
                    it.remotePluginId == selected.remotePluginId
                }
                detailPlugin = detail.copy(
                    likeCount = if (likeCountLoaded) {
                        currentSummary?.likeCount ?: detail.likeCount
                    } else {
                        detail.likeCount
                    },
                    commentCount = if (commentCountLoaded) {
                        currentSummary?.commentCount ?: detail.commentCount
                    } else {
                        detail.commentCount
                    }
                )
                detailTargetExists = exists
            },
            onFailure = { error ->
                detailError = pluginMarketErrorMessage(error)
                detailTargetExists = false
            }
        )
        historyResult.fold(
            onSuccess = { historyVersions = it },
            onFailure = { historyError = pluginMarketErrorMessage(it) }
        )
        detailLoading = false
        historyLoading = false
    }

    LaunchedEffect(selectedPlugin?.remotePluginId, commentsRetryVersion) {
        val selected = selectedPlugin ?: return@LaunchedEffect
        commentsLoading = true
        commentsError = ""
        interactionError = ""
        comments = emptyList()
        currentUserIdentity = null
        likedByCurrentUser = false
        likeCountLoaded = false
        commentCountLoaded = false
        val identityResult = withContext(Dispatchers.IO) { PluginMarketRepository.currentUserIdentity(context) }
        val commentsResult = withContext(Dispatchers.IO) { PluginMarketRepository.comments(context, selected.remotePluginId) }
        val likedResult = withContext(Dispatchers.IO) { PluginMarketRepository.likeStatus(context, selected.remotePluginId) }
        val followedResult = withContext(Dispatchers.IO) { PluginMarketRepository.followStatus(context, selected.remotePluginId) }
        identityResult.fold(
            onSuccess = { currentUserIdentity = it },
            onFailure = { interactionError = pluginMarketErrorMessage(it) }
        )
        commentsResult.fold(
            onSuccess = { page ->
                comments = page.items
                commentCountLoaded = true
                updateSocialCounts(selected.remotePluginId, commentCount = page.total)
            },
            onFailure = { commentsError = pluginMarketErrorMessage(it) }
        )
        likedResult.fold(
            onSuccess = { likeResult ->
                likedByCurrentUser = likeResult.liked
                likeCountLoaded = true
                updateSocialCounts(selected.remotePluginId, likeCount = likeResult.likeCount)
            },
            onFailure = {
                if (interactionError.isBlank()) interactionError = pluginMarketErrorMessage(it)
            }
        )
        followedResult.fold(
            onSuccess = { followingByCurrentUser = it },
            onFailure = { if (interactionError.isBlank()) interactionError = pluginMarketErrorMessage(it) }
        )
        commentsLoading = false
    }

    fun performInstall(plugin: PluginMarketPlugin, overwrite: Boolean) {
        if (installingRemoteId != null) return
        installingRemoteId = plugin.remotePluginId
        installMessage = ""
        scope.launch {
            val result = withContext(Dispatchers.IO) {
                PluginMarketRepository.install(context, plugin, overwrite)
            }
            result.fold(
                onSuccess = { installed ->
                    detailTargetExists = true
                    installed.downloadCount?.let { count ->
                        detailPlugin = detailPlugin?.copy(downloadCount = count)
                        selectedPlugin = selectedPlugin?.copy(downloadCount = count)
                        plugins = plugins.map { item ->
                            if (item.remotePluginId == plugin.remotePluginId) {
                                item.copy(downloadCount = count)
                            } else {
                                item
                            }
                        }
                    }
                    installMessage = if (installed.replacedExisting) {
                        "更新成功，插件已保持禁用"
                    } else {
                        "安装成功，插件默认禁用"
                    }
                    Toast.makeText(context, installMessage, Toast.LENGTH_SHORT).show()
                },
                onFailure = { error ->
                    installMessage = "安装失败: ${pluginMarketErrorMessage(error)}"
                }
            )
            installingRemoteId = null
        }
    }

    fun requestInstall(plugin: PluginMarketPlugin) {
        if (installingRemoteId != null) return
        installingRemoteId = plugin.remotePluginId
        installMessage = ""
        scope.launch {
            val exists = withContext(Dispatchers.IO) {
                PluginMarketInstaller.existingLocalPluginId(context, plugin) != null
            }
            installingRemoteId = null
            if (exists) {
                pendingOverwrite = plugin
            } else {
                performInstall(plugin, overwrite = false)
            }
        }
    }

    fun requestHistoryInstall(version: PluginMarketHistoryVersion) {
        val selected = selectedPlugin ?: return
        if (historyInstallVersionId != null || installingRemoteId != null) return
        historyInstallVersionId = version.versionId
        scope.launch {
            val result = withContext(Dispatchers.IO) {
                PluginMarketRepository.historyDetail(
                    context,
                    selected.remotePluginId,
                    version.versionId
                )
            }
            historyInstallVersionId = null
            result.fold(
                onSuccess = ::requestInstall,
                onFailure = {
                    Toast.makeText(
                        context,
                        "读取历史版本失败: ${pluginMarketErrorMessage(it)}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            )
        }
    }

    fun deleteOwnedPlugin(plugin: PluginMarketPlugin) {
        if (deletingRemoteId != null || installingRemoteId != null) return
        deletingRemoteId = plugin.remotePluginId
        scope.launch {
            val result = withContext(Dispatchers.IO) {
                PluginMarketRepository.deleteOwnedRemote(context, plugin.remotePluginId)
            }
            deletingRemoteId = null
            result.fold(
                onSuccess = {
                    selectedPlugin = null
                    detailPlugin = null
                    historyVersions = emptyList()
                    plugins = plugins.filterNot { it.remotePluginId == plugin.remotePluginId }
                    pluginCount = (pluginCount - 1).coerceAtLeast(0)
                    refreshVersion++
                    Toast.makeText(context, "线上插件已删除，本地插件不受影响", Toast.LENGTH_SHORT).show()
                },
                onFailure = { error ->
                    Toast.makeText(
                        context,
                        "删除失败: ${pluginMarketErrorMessage(error)}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            )
        }
    }

    fun togglePluginLike(plugin: PluginMarketPlugin) {
        if (updatingLike) return
        if (currentUserIdentity == null) {
            Toast.makeText(
                context,
                interactionError.ifBlank { "当前微信账号资料尚未就绪" },
                Toast.LENGTH_LONG
            ).show()
            return
        }
        val removeLike = likedByCurrentUser
        updatingLike = true
        interactionError = ""
        scope.launch {
            val result = withContext(Dispatchers.IO) {
                if (removeLike) {
                    PluginMarketRepository.unlike(context, plugin.remotePluginId)
                } else {
                    PluginMarketRepository.like(context, plugin.remotePluginId)
                }
            }
            result.fold(
                onSuccess = { likeResult ->
                    likedByCurrentUser = likeResult.liked
                    likeCountLoaded = true
                    updateSocialCounts(plugin.remotePluginId, likeCount = likeResult.likeCount)
                },
                onFailure = { error ->
                    interactionError = pluginMarketErrorMessage(error)
                    Toast.makeText(context, "操作失败: $interactionError", Toast.LENGTH_LONG).show()
                }
            )
            updatingLike = false
        }
    }

    fun togglePluginFollow(plugin: PluginMarketPlugin) {
        if (updatingFollow || currentUserIdentity == null) return
        updatingFollow = true
        scope.launch {
            val result = withContext(Dispatchers.IO) {
                PluginMarketRepository.toggleFollow(context, plugin.remotePluginId, followingByCurrentUser)
            }
            result.fold(
                onSuccess = { followingByCurrentUser = it },
                onFailure = { interactionError = pluginMarketErrorMessage(it) }
            )
            updatingFollow = false
        }
    }
    fun submitPluginComment(plugin: PluginMarketPlugin) {
        if (submittingComment) return
        val content = commentDraft.trim()
        if (content.isBlank()) {
            Toast.makeText(context, "评论内容不能为空", Toast.LENGTH_SHORT).show()
            return
        }
        if (currentUserIdentity == null) {
            Toast.makeText(
                context,
                interactionError.ifBlank { "当前微信账号资料尚未就绪" },
                Toast.LENGTH_LONG
            ).show()
            return
        }
        submittingComment = true
        interactionError = ""
        scope.launch {
            val result = withContext(Dispatchers.IO) {
                PluginMarketRepository.addComment(context, plugin.remotePluginId, content)
            }
            result.fold(
                onSuccess = { mutation ->
                    mutation.comment?.let { added ->
                        comments = (listOf(added) + comments.filterNot { it.commentId == added.commentId })
                    }
                    commentDraft = ""
                    commentCountLoaded = true
                    updateSocialCounts(plugin.remotePluginId, commentCount = mutation.commentCount)
                },
                onFailure = { error ->
                    interactionError = pluginMarketErrorMessage(error)
                    Toast.makeText(context, "评论失败: $interactionError", Toast.LENGTH_LONG).show()
                }
            )
            submittingComment = false
        }
    }

    fun submitPluginReply(
        plugin: PluginMarketPlugin,
        parentComment: PluginMarketComment,
        content: String,
        onCompleted: (Boolean) -> Unit
    ) {
        if (submittingComment) {
            onCompleted(false)
            return
        }
        if (currentUserIdentity == null) {
            Toast.makeText(
                context,
                interactionError.ifBlank { "当前微信账号资料尚未就绪" },
                Toast.LENGTH_LONG
            ).show()
            onCompleted(false)
            return
        }
        submittingComment = true
        interactionError = ""
        scope.launch {
            val result = withContext(Dispatchers.IO) {
                PluginMarketRepository.replyComment(
                    context,
                    plugin.remotePluginId,
                    parentComment,
                    content
                )
            }
            var succeeded = false
            result.fold(
                onSuccess = { mutation ->
                    mutation.comment?.let { added ->
                        comments = (listOf(added) + comments.filterNot {
                            it.commentId == added.commentId
                        })
                    }
                    commentCountLoaded = true
                    updateSocialCounts(plugin.remotePluginId, commentCount = mutation.commentCount)
                    succeeded = true
                },
                onFailure = { error ->
                    interactionError = pluginMarketErrorMessage(error)
                    Toast.makeText(context, "回复失败: $interactionError", Toast.LENGTH_LONG).show()
                }
            )
            submittingComment = false
            onCompleted(succeeded)
        }
    }

    fun openNotifications() {
        showNotifications = true
        notificationRefreshVersion++
    }

    fun deletePluginComment(plugin: PluginMarketPlugin, comment: PluginMarketComment) {
        if (deletingCommentId != null) return
        if (!comment.canDelete) {
            Toast.makeText(context, "只能删除自己的评论", Toast.LENGTH_SHORT).show()
            return
        }
        deletingCommentId = comment.commentId
        interactionError = ""
        scope.launch {
            val result = withContext(Dispatchers.IO) {
                PluginMarketRepository.deleteOwnComment(context, plugin.remotePluginId, comment)
            }
            result.fold(
                onSuccess = { mutation ->
                    comments = comments.filterNot { it.commentId == comment.commentId }
                    commentCountLoaded = true
                    updateSocialCounts(plugin.remotePluginId, commentCount = mutation.commentCount)
                    if (mutation.commentCount != comments.size.toLong()) commentsRetryVersion++
                },
                onFailure = { error ->
                    interactionError = pluginMarketErrorMessage(error)
                    Toast.makeText(context, "删除评论失败: $interactionError", Toast.LENGTH_LONG).show()
                }
            )
            deletingCommentId = null
        }
    }

    PageScaffold(
        title = "在线插件",
        largeTitle = "在线插件",
        scrollBehavior = scrollBehavior,
        topBarActions = {
            PluginMarketNotificationBell(
                unreadCount = notificationUnreadCount,
                onClick = ::openNotifications
            )
        },
        bottomBar = {
            BottomActionBar(
                primaryText = "上传本地插件",
                onPrimaryClick = { showUploadDialog = true },
                secondaryText = "返回",
                onSecondaryClick = onBack
            )
        }
    ) { padding ->
        PullToRefresh(
            isRefreshing = loading,
            onRefresh = { if (!loading) refreshVersion++ },
            modifier = Modifier.fillMaxSize(),
            topAppBarScrollBehavior = scrollBehavior,
            refreshTexts = listOf("下拉刷新", "松开刷新", "正在刷新", "刷新完成"),
            contentPadding = PaddingValues(0.dp)
        ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            state = listState,
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 8.dp,
                bottom = padding.calculateBottomPadding() + 84.dp
            )
        ) {
            item {
                SearchBarSurface(
                    query = query,
                    placeholder = "搜索插件、作者或目录名",
                    modifier = Modifier.padding(horizontal = 12.dp),
                    onQueryChange = { query = it }
                )
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "浏览") }
            item {
                SettingsCard {
                    PopupChoiceRow(
                        title = "排序方式",
                        summary = if (sort == "downloads") "热门（按下载量）" else "最新发布",
                        options = listOf(
                            PopupChoice("最新", "latest"),
                            PopupChoice("热门", "downloads")
                        ),
                        currentValue = sort,
                        onValueChanged = { sort = it }
                    )
                    InsetDivider()
                    ActionRow(
                        title = "刷新",
                        summary = if (loading) "正在加载在线插件" else "重新获取当前列表"
                    ) {
                        refreshVersion++
                    }
                }
            }
            item {
                SettingsCard(modifier = Modifier.padding(top = 10.dp)) {
                    Text(
                        text = "社区插件由用户上传，安装前请核对作者、说明和文件内容。下载后的插件默认禁用。",
                        color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                        fontSize = 13.sp,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 14.dp)
                    )
                }
            }
            item {
                SmallTitle(
                    modifier = Modifier.padding(top = 10.dp),
                    text = "在线插件(${if (loading && plugins.isEmpty()) "-" else pluginCount})"
                )
            }
            if (loadError.isNotBlank()) {
                item {
                    SettingsCard {
                        Column {
                            EmptyText(loadError)
                            TextButton(
                                text = "重试",
                                onClick = { refreshVersion++ },
                                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 12.dp),
                                colors = ButtonDefaults.textButtonColorsPrimary()
                            )
                        }
                    }
                }
            } else if (loading && plugins.isEmpty()) {
                item { SettingsCard { EmptyText("正在加载在线插件...") } }
            } else if (plugins.isEmpty()) {
                item {
                    SettingsCard {
                        EmptyText(if (query.isBlank()) "暂无在线插件" else "没有匹配的在线插件")
                    }
                }
            } else {
                item {
                    SettingsCard {
                        plugins.forEachIndexed { index, plugin ->
                            PluginMarketListRow(plugin) {
                                selectedPlugin = plugin
                            }
                            if (index != plugins.lastIndex) InsetDivider()
                        }
                    }
                }
            }
        }
        }
    }

    selectedPlugin?.let { summary ->
        PluginMarketDetailDialog(
            summary = summary,
            detail = detailPlugin,
            loading = detailLoading,
            error = detailError,
            historyVersions = historyVersions,
            historyLoading = historyLoading,
            historyError = historyError,
            historyInstallVersionId = historyInstallVersionId,
            installed = detailTargetExists,
            installing = installingRemoteId == summary.remotePluginId,
            deleting = deletingRemoteId == summary.remotePluginId,
            owned = PluginMarketRepository.ownsRemotePlugin(context, summary.remotePluginId),
            installMessage = installMessage,
            likedByCurrentUser = likedByCurrentUser,
            followingByCurrentUser = followingByCurrentUser,
            updatingLike = updatingLike,
            updatingFollow = updatingFollow,
            comments = comments,
            commentsLoading = commentsLoading,
            commentsError = commentsError,
            currentUserWxId = currentUserIdentity?.wxId.orEmpty(),
            interactionError = interactionError,
            commentDraft = commentDraft,
            submittingComment = submittingComment,
            deletingCommentId = deletingCommentId,
            onRetry = { detailRetryVersion++ },
            onRetryComments = { commentsRetryVersion++ },
            onInstall = ::requestInstall,
            onInstallHistory = ::requestHistoryInstall,
            onToggleLike = { togglePluginLike(summary) },
            onToggleFollow = { togglePluginFollow(summary) },
            onCommentDraftChanged = { value -> commentDraft = value.take(1000) },
            onSubmitComment = { submitPluginComment(summary) },
            onDeleteComment = { comment -> deletePluginComment(summary, comment) },
            onSubmitReply = { comment, content, onCompleted ->
                submitPluginReply(summary, comment, content, onCompleted)
            },
            onDelete = { deleteOwnedPlugin(summary) },
            onDismiss = {
                if (
                    installingRemoteId == null &&
                    deletingRemoteId == null &&
                    !updatingLike &&
                    !submittingComment &&
                    deletingCommentId == null
                ) {
                    selectedPlugin = null
                    detailPlugin = null
                    detailError = ""
                    historyVersions = emptyList()
                    historyError = ""
                    comments = emptyList()
                    commentsError = ""
                    currentUserIdentity = null
                    interactionError = ""
                    commentDraft = ""
                    installMessage = ""
                }
            }
        )
    }

    pendingOverwrite?.let { plugin ->
        WindowDialog(
            show = true,
            title = "覆盖本地插件",
            onDismissRequest = { pendingOverwrite = null },
            content = {
                Column {
                    Text(
                        text = "本地已存在目录“${PluginMarketInstaller.defaultLocalPluginId(plugin)}”。更新会覆盖 main.java、main.java.bshs、info.prop 和 README.md，并保留其它本地配置文件。更新后插件保持禁用。",
                        color = MiuixTheme.colorScheme.onSurface,
                        fontSize = 14.sp
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 14.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        TextButton(
                            text = "取消",
                            onClick = { pendingOverwrite = null },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.textButtonColorsPrimary()
                        )
                        TextButton(
                            text = "确认覆盖",
                            onClick = {
                                pendingOverwrite = null
                                performInstall(plugin, overwrite = true)
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.textButtonColorsPrimary()
                        )
                    }
                }
            }
        )
    }

    if (showUploadDialog) {
        PluginMarketUploadDialog(
            context = context,
            onDismiss = { uploaded ->
                showUploadDialog = false
                if (uploaded) refreshVersion++
            }
        )
    }

    if (showNotifications) {
        PluginMarketNotificationDialog(
            notifications = notifications,
            loading = notificationsLoading,
            error = notificationsError,
            onRetry = { notificationRefreshVersion++ },
            onNotificationClick = { notification ->
                showNotifications = false
                val target = plugins.firstOrNull {
                    it.remotePluginId == notification.remotePluginId
                } ?: PluginMarketPlugin(
                    remotePluginId = notification.remotePluginId,
                    sourcePluginId = "",
                    name = notification.pluginName,
                    author = "",
                    version = "",
                    versionId = "",
                    updateTime = "",
                    downloadCount = 0L,
                    likeCount = 0L,
                    commentCount = 0L,
                    description = "",
                    files = emptyList()
                )
                val activity = context as? Activity
                    ?: WeChatApis.currentActivity()?.currentActivity() as? Activity
                if (activity == null) {
                    selectedPlugin = target
                } else {
                    activity.window?.decorView?.postOnAnimation {
                        if (!activity.isFinishing && !activity.isDestroyed) selectedPlugin = target
                    }
                }
            },
            onDismiss = { showNotifications = false }
        )
    }
}

@Composable
internal fun PluginMarketNotificationBell(
    unreadCount: Long,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.size(40.dp).responsiveTap(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Image(
            imageVector = NavIcons.Bell,
            contentDescription = "回复通知",
            colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.onSurface),
            modifier = Modifier.size(24.dp)
        )
        if (unreadCount > 0L) {
            Box(
                modifier = Modifier.align(Alignment.TopEnd)
                    .height(16.dp)
                    .sizeIn(minWidth = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFD93025))
                    .padding(horizontal = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (unreadCount > 99L) "99+" else unreadCount.toString(),
                    color = Color.White,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
internal fun PluginMarketNotificationDialog(
    notifications: List<PluginMarketNotification>,
    loading: Boolean,
    error: String,
    onRetry: () -> Unit,
    onNotificationClick: (PluginMarketNotification) -> Unit,
    onDismiss: () -> Unit
) {
    WindowDialog(
        show = true,
        title = "回复通知",
        onDismissRequest = onDismiss,
        content = {
            Column(modifier = Modifier.fillMaxWidth().heightIn(max = 520.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "共 ${notifications.size} 条",
                        color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                        fontSize = 12.sp
                    )
                    TextButton(
                        text = if (loading) "正在刷新" else "刷新",
                        onClick = onRetry,
                        enabled = !loading,
                        colors = ButtonDefaults.textButtonColorsPrimary()
                    )
                }
                Column(
                    modifier = Modifier.fillMaxWidth().weight(1f, fill = false)
                        .verticalScroll(rememberScrollState())
                ) {
                    when {
                        loading && notifications.isEmpty() -> EmptyText("正在加载回复通知...")
                        error.isNotBlank() && notifications.isEmpty() -> {
                            EmptyText(error)
                            TextButton(
                                text = "重试",
                                onClick = onRetry,
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.textButtonColorsPrimary()
                            )
                        }
                        notifications.isEmpty() -> EmptyText("暂无回复通知")
                        else -> notifications.forEachIndexed { index, notification ->
                            PluginMarketNotificationRow(
                                notification = notification,
                                onClick = { onNotificationClick(notification) }
                            )
                            if (index != notifications.lastIndex) Spacer(Modifier.height(8.dp))
                        }
                    }
                }
                TextButton(
                    text = "关闭",
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                    colors = ButtonDefaults.textButtonColorsPrimary()
                )
            }
        }
    )
}

@Composable
internal fun PluginMarketNotificationRow(
    notification: PluginMarketNotification,
    onClick: () -> Unit
) {
    var pressed by remember(notification.notificationId) { mutableStateOf(false) }
    val feedbackColor = rememberPressFeedbackColor(pressed)
    Column(
        modifier = Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(
                if (pressed) feedbackColor else MiuixTheme.colorScheme.secondaryVariant
            )
            .responsiveTap(onClick = onClick, onPressedChange = { pressed = it })
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        Text(
            text = when (notification.type) {
                "plugin_comment" -> "${notification.actorNickname.ifBlank { "微信用户" }} 评论了你的插件"
                "plugin_update" -> "你关注的插件发布了新版本"
                else -> "${notification.actorNickname.ifBlank { "微信用户" }} 回复了你的评论"
            },
            color = MiuixTheme.colorScheme.onSurface,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = notification.pluginName.ifBlank { notification.remotePluginId },
            color = MiuixTheme.colorScheme.primary,
            fontSize = 12.sp,
            modifier = Modifier.fillMaxWidth().padding(top = 2.dp)
        )
        Text(
            text = notification.content,
            color = MiuixTheme.colorScheme.onSurface,
            fontSize = 13.sp,
            modifier = Modifier.fillMaxWidth().padding(top = 7.dp)
        )
        Text(
            text = "原评论：${notification.originalContent}",
            color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
            fontSize = 12.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth().padding(top = 5.dp)
        )
        Text(
            text = formatPluginMarketTime(notification.createdAt),
            color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
            fontSize = 11.sp,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
internal fun PluginMarketListRow(
    plugin: PluginMarketPlugin,
    onClick: () -> Unit
) {
    var pressed by remember { mutableStateOf(false) }
    val pressFeedbackColor = rememberPressFeedbackColor(pressed)
    Row(
        modifier = Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(pressFeedbackColor)
            .responsiveTap(onClick = onClick, onPressedChange = { pressed = it })
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = plugin.name.ifBlank { plugin.remotePluginId },
                color = MiuixTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = buildString {
                    append("作者: ").append(plugin.author.ifBlank { "未知" })
                    append(" | 版本: ").append(plugin.version.ifBlank { "未知" })
                    append(" | 下载: ").append(plugin.downloadCount)
                    append("\n点赞: ").append(plugin.likeCount)
                    append(" | 评论: ").append(plugin.commentCount)
                    append("\n更新: ").append(formatPluginMarketTime(plugin.updateTime))
                },
                color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                fontSize = 12.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
        Text(
            text = "›",
            color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
            fontSize = 22.sp,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
internal fun PluginMarketDetailDialog(
    summary: PluginMarketPlugin,
    detail: PluginMarketPlugin?,
    loading: Boolean,
    error: String,
    historyVersions: List<PluginMarketHistoryVersion>,
    historyLoading: Boolean,
    historyError: String,
    historyInstallVersionId: String?,
    installed: Boolean,
    installing: Boolean,
    deleting: Boolean,
    owned: Boolean,
    installMessage: String,
    likedByCurrentUser: Boolean,
    followingByCurrentUser: Boolean,
    updatingLike: Boolean,
    updatingFollow: Boolean,
    comments: List<PluginMarketComment>,
    commentsLoading: Boolean,
    commentsError: String,
    currentUserWxId: String,
    interactionError: String,
    commentDraft: String,
    submittingComment: Boolean,
    deletingCommentId: String?,
    onRetry: () -> Unit,
    onRetryComments: () -> Unit,
    onInstall: (PluginMarketPlugin) -> Unit,
    onInstallHistory: (PluginMarketHistoryVersion) -> Unit,
    onToggleLike: () -> Unit,
    onToggleFollow: () -> Unit,
    onCommentDraftChanged: (String) -> Unit,
    onSubmitComment: () -> Unit,
    onDeleteComment: (PluginMarketComment) -> Unit,
    onSubmitReply: (PluginMarketComment, String, (Boolean) -> Unit) -> Unit,
    onDelete: () -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var confirmDelete by remember(summary.remotePluginId) { mutableStateOf(false) }
    var pendingDeleteComment by remember(summary.remotePluginId) {
        mutableStateOf<PluginMarketComment?>(null)
    }
    var replyTarget by remember(summary.remotePluginId) {
        mutableStateOf<PluginMarketComment?>(null)
    }
    var replyDraft by remember(summary.remotePluginId) { mutableStateOf("") }
    WindowDialog(
        show = true,
        title = summary.name.ifBlank { "插件详情" },
        onDismissRequest = onDismiss,
        content = {
            Column(modifier = Modifier.fillMaxWidth().heightIn(max = 560.dp).imePadding()) {
                Column(
                    modifier = Modifier.fillMaxWidth().weight(1f, fill = false)
                        .verticalScroll(rememberScrollState())
                ) {
                    val shownPlugin = detail ?: summary
                    PluginMarketDialogInfoRow("作者", shownPlugin.author.ifBlank { "未知" })
                    PluginMarketDialogInfoRow("版本", shownPlugin.version.ifBlank { "未知" })
                    PluginMarketDialogInfoRow("更新时间", formatPluginMarketTime(shownPlugin.updateTime))
                    PluginMarketDialogInfoRow("下载量", shownPlugin.downloadCount.toString())
                    PluginMarketDialogInfoRow("点赞数", shownPlugin.likeCount.toString())
                    PluginMarketDialogInfoRow("评论数", shownPlugin.commentCount.toString())
                    PluginMarketDialogInfoRow("插件 ID", summary.remotePluginId)
                    if (detail?.description?.isNotBlank() == true) {
                        Text(
                            text = detail.description,
                            color = MiuixTheme.colorScheme.onSurface,
                            fontSize = 14.sp,
                            modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
                        )
                    }
                    if (detail != null) {
                        Text(
                            text = "包含文件",
                            color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                        Text(
                            text = detail.files.joinToString("、") { it.name }.ifBlank { "未知" },
                            color = MiuixTheme.colorScheme.onSurface,
                            fontSize = 13.sp,
                            modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                        )
                        detail.files.firstOrNull { it.name.equals("README.md", ignoreCase = true) }
                            ?.content?.takeIf { it.isNotBlank() }
                            ?.let { readme ->
                                Text(
                                    text = "插件说明",
                                    color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(top = 12.dp)
                                )
                                Box(modifier = Modifier.fillMaxWidth().padding(top = 4.dp)) {
                                    MarkdownUi.Content(
                                        context = context,
                                        markdown = readme,
                                        contentPadding = PaddingValues(0.dp)
                                    )
                                }
                            }
                    }
                    PluginMarketSocialSection(
                        likeCount = shownPlugin.likeCount,
                        commentCount = shownPlugin.commentCount,
                        likedByCurrentUser = likedByCurrentUser,
                        followingByCurrentUser = followingByCurrentUser,
                        updatingLike = updatingLike,
                        updatingFollow = updatingFollow,
                        comments = comments,
                        commentsLoading = commentsLoading,
                        commentsError = commentsError,
                        currentUserWxId = currentUserWxId,
                        interactionError = interactionError,
                        commentDraft = commentDraft,
                        submittingComment = submittingComment,
                        deletingCommentId = deletingCommentId,
                        enabled = !installing && !deleting,
                        onToggleLike = onToggleLike,
                        onToggleFollow = onToggleFollow,
                        onCommentDraftChanged = onCommentDraftChanged,
                        onSubmitComment = onSubmitComment,
                        onRetryComments = onRetryComments,
                        onDeleteComment = { pendingDeleteComment = it },
                        onReplyComment = { comment ->
                            replyTarget = comment
                            replyDraft = ""
                        }
                    )
                    PluginMarketHistoryUi.HistoryList(
                        versions = historyVersions,
                        loading = historyLoading,
                        error = historyError,
                        installingVersionId = historyInstallVersionId,
                        enabled = !installing,
                        onRetry = onRetry,
                        onInstall = onInstallHistory
                    )
                    Text(
                        text = "社区插件可能执行敏感操作，请确认来源可信。安装或更新完成后插件保持禁用。",
                        color = Color(0xFFD93025),
                        fontSize = 13.sp,
                        modifier = Modifier.fillMaxWidth().padding(top = 14.dp)
                    )
                    when {
                        loading -> EmptyText("正在加载插件详情...")
                        error.isNotBlank() -> {
                            EmptyText(error)
                            TextButton(
                                text = "重新加载",
                                onClick = onRetry,
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.textButtonColorsPrimary()
                            )
                        }
                    }
                    if (installMessage.isNotBlank()) {
                        Text(
                            text = installMessage,
                            color = if (installMessage.startsWith("安装失败")) {
                                Color(0xFFD93025)
                            } else {
                                MiuixTheme.colorScheme.primary
                            },
                            fontSize = 13.sp,
                            modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                val activeReply = replyTarget
                if (activeReply != null) {
                    val focusRequester = remember(activeReply.commentId) { FocusRequester() }
                    val keyboardController = LocalSoftwareKeyboardController.current
                    LaunchedEffect(activeReply.commentId) {
                        focusRequester.requestFocus()
                        keyboardController?.show()
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MiuixTheme.colorScheme.secondaryVariant)
                            .padding(horizontal = 12.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = "回复 ${activeReply.userNickname.ifBlank { "微信用户" }}",
                            color = MiuixTheme.colorScheme.onSurface,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = activeReply.content.replace('\n', ' ').trim(),
                            color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                            fontSize = 12.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.fillMaxWidth().padding(top = 2.dp)
                        )
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(MiuixTheme.colorScheme.background)
                                .padding(horizontal = 10.dp, vertical = 9.dp)
                        ) {
                            if (replyDraft.isBlank()) {
                                Text(
                                    text = "回复 ${activeReply.userNickname.ifBlank { "微信用户" }}",
                                    color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                                    fontSize = 14.sp
                                )
                            }
                            BasicTextField(
                                value = replyDraft,
                                onValueChange = { replyDraft = it.take(1000) },
                                enabled = !submittingComment,
                                minLines = 1,
                                maxLines = 4,
                                textStyle = TextStyle(
                                    color = MiuixTheme.colorScheme.onSurface,
                                    fontSize = 14.sp
                                ),
                                cursorBrush = SolidColor(MiuixTheme.colorScheme.primary),
                                modifier = Modifier.fillMaxWidth().focusRequester(focusRequester)
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${replyDraft.length}/1000",
                                color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                                fontSize = 11.sp
                            )
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                TextButton(
                                    text = "取消",
                                    onClick = {
                                        replyTarget = null
                                        replyDraft = ""
                                    },
                                    enabled = !submittingComment,
                                    colors = ButtonDefaults.textButtonColorsPrimary()
                                )
                                TextButton(
                                    text = if (submittingComment) "正在回复" else "发送",
                                    onClick = {
                                        val content = replyDraft.trim()
                                        if (content.isNotBlank()) {
                                            onSubmitReply(activeReply, content) { succeeded ->
                                                if (succeeded) {
                                                    replyTarget = null
                                                    replyDraft = ""
                                                }
                                            }
                                        }
                                    },
                                    enabled = replyDraft.isNotBlank() && !submittingComment,
                                    colors = ButtonDefaults.textButtonColorsPrimary()
                                )
                            }
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 14.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        TextButton(
                            text = "关闭",
                            onClick = onDismiss,
                            enabled = !installing && !deleting && !updatingLike &&
                                !submittingComment && deletingCommentId == null,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.textButtonColorsPrimary()
                        )
                        if (owned) {
                            TextButton(
                                text = if (deleting) "正在删除" else "删除",
                                onClick = { confirmDelete = true },
                                enabled = !installing && !deleting && !updatingLike &&
                                    !submittingComment && deletingCommentId == null,
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.textButtonColorsPrimary()
                            )
                        }
                        if (installing) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                size = 18.dp
                            )
                        }
                        TextButton(
                            text = when {
                                installing -> "正在处理"
                                installed -> "更新"
                                else -> "安装"
                            },
                            onClick = { detail?.let(onInstall) },
                            enabled = detail != null && !loading && error.isBlank() && !installing &&
                                !deleting && !updatingLike && !submittingComment && deletingCommentId == null,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.textButtonColorsPrimary()
                        )
                    }
                }
            }
        }
    )
    if (confirmDelete) {
        WindowDialog(
            show = true,
            title = "删除线上插件",
            onDismissRequest = { confirmDelete = false },
            content = {
                Column {
                    Text(
                        text = "确定删除“${summary.name.ifBlank { summary.remotePluginId }}”吗？线上插件及全部历史版本都会被删除，本地插件不会删除。此操作无法撤销。",
                        color = MiuixTheme.colorScheme.onSurface,
                        fontSize = 14.sp
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 14.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        TextButton(
                            text = "取消",
                            onClick = { confirmDelete = false },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.textButtonColorsPrimary()
                        )
                        TextButton(
                            text = "确认删除",
                            onClick = {
                                confirmDelete = false
                                onDelete()
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.textButtonColorsPrimary()
                        )
                    }
                }
            }
        )
    }
    pendingDeleteComment?.let { comment ->
        WindowDialog(
            show = true,
            title = "删除评论",
            onDismissRequest = { pendingDeleteComment = null },
            content = {
                Column {
                    Text(
                        text = "确定删除这条评论吗？此操作无法撤销。",
                        color = MiuixTheme.colorScheme.onSurface,
                        fontSize = 14.sp
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 14.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        TextButton(
                            text = "取消",
                            onClick = { pendingDeleteComment = null },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.textButtonColorsPrimary()
                        )
                        TextButton(
                            text = "确认删除",
                            onClick = {
                                pendingDeleteComment = null
                                onDeleteComment(comment)
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.textButtonColorsPrimary()
                        )
                    }
                }
            }
        )
    }
}

@Composable
internal fun PluginMarketSocialSection(
    likeCount: Long,
    commentCount: Long,
    likedByCurrentUser: Boolean,
    followingByCurrentUser: Boolean,
    updatingLike: Boolean,
    updatingFollow: Boolean,
    comments: List<PluginMarketComment>,
    commentsLoading: Boolean,
    commentsError: String,
    currentUserWxId: String,
    interactionError: String,
    commentDraft: String,
    submittingComment: Boolean,
    deletingCommentId: String?,
    enabled: Boolean,
    onToggleLike: () -> Unit,
    onToggleFollow: () -> Unit,
    onCommentDraftChanged: (String) -> Unit,
    onSubmitComment: () -> Unit,
    onRetryComments: () -> Unit,
    onDeleteComment: (PluginMarketComment) -> Unit,
    onReplyComment: (PluginMarketComment) -> Unit
) {
    Text(
        text = "互动",
        color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
        fontSize = 12.sp,
        modifier = Modifier.padding(top = 16.dp)
    )
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(
            text = when {
                updatingLike -> "正在处理"
                likedByCurrentUser -> "取消点赞"
                else -> "点赞"
            },
            onClick = onToggleLike,
            enabled = enabled && currentUserWxId.isNotBlank() && !updatingLike,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.textButtonColorsPrimary()
        )
        TextButton(
            text = when {
                updatingFollow -> "正在处理"
                followingByCurrentUser -> "取消关注"
                else -> "关注插件"
            },
            onClick = onToggleFollow,
            enabled = enabled && currentUserWxId.isNotBlank() && !updatingFollow,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.textButtonColorsPrimary()
        )
        Text(
            text = "$likeCount 个赞 · $commentCount 条评论",
            color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
            fontSize = 13.sp,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
    Text(
        text = "发表评论",
        color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
        fontSize = 12.sp,
        modifier = Modifier.padding(top = 10.dp)
    )
    BasicTextField(
        value = commentDraft,
        onValueChange = onCommentDraftChanged,
        enabled = enabled && currentUserWxId.isNotBlank() && !submittingComment,
        minLines = 2,
        maxLines = 4,
        textStyle = TextStyle(color = MiuixTheme.colorScheme.onSurface, fontSize = 14.sp),
        cursorBrush = SolidColor(MiuixTheme.colorScheme.primary),
        decorationBox = { innerTextField ->
            Box(contentAlignment = Alignment.TopStart) {
                if (commentDraft.isBlank()) {
                    Text(
                        text = if (currentUserWxId.isBlank()) "当前微信账号资料尚未就绪" else "填写评论内容",
                        color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                        fontSize = 14.sp
                    )
                }
                innerTextField()
            }
        },
        modifier = Modifier.fillMaxWidth().padding(top = 5.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MiuixTheme.colorScheme.secondaryVariant)
            .padding(horizontal = 12.dp, vertical = 10.dp)
    )
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${commentDraft.length}/1000",
            color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
            fontSize = 12.sp
        )
        TextButton(
            text = if (submittingComment) "正在发表" else "发表",
            onClick = onSubmitComment,
            enabled = enabled && currentUserWxId.isNotBlank() &&
                commentDraft.isNotBlank() && !submittingComment,
            colors = ButtonDefaults.textButtonColorsPrimary()
        )
    }
    if (interactionError.isNotBlank()) {
        Text(
            text = interactionError,
            color = Color(0xFFD93025),
            fontSize = 12.sp,
            modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
        )
    }
    Text(
        text = "评论 ($commentCount)",
        color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
        fontSize = 12.sp,
        modifier = Modifier.padding(top = 10.dp)
    )
    val commentThreads = remember(comments) { buildPluginMarketCommentThreads(comments) }
    when {
        commentsLoading -> EmptyText("正在加载评论...")
        commentsError.isNotBlank() -> {
            EmptyText(commentsError)
            TextButton(
                text = "重试",
                onClick = onRetryComments,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.textButtonColorsPrimary()
            )
        }
        comments.isEmpty() -> EmptyText("暂无评论")
        else -> commentThreads.forEachIndexed { index, thread ->
            PluginMarketCommentRow(
                comment = thread.root,
                deleting = deletingCommentId == thread.root.commentId,
                enabled = enabled && deletingCommentId == null,
                onDelete = { onDeleteComment(thread.root) },
                onReply = { onReplyComment(thread.root) }
            )
            if (thread.replies.isNotEmpty()) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(start = 14.dp, bottom = 8.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(MiuixTheme.colorScheme.secondaryVariant)
                        .padding(horizontal = 10.dp, vertical = 3.dp)
                ) {
                    thread.replies.forEachIndexed { replyIndex, reply ->
                        PluginMarketReplyRow(
                            comment = reply,
                            deleting = deletingCommentId == reply.commentId,
                            enabled = enabled && deletingCommentId == null,
                            onDelete = { onDeleteComment(reply) },
                            onReply = { onReplyComment(reply) }
                        )
                        if (replyIndex != thread.replies.lastIndex) InsetDivider()
                    }
                }
            }
            if (index != commentThreads.lastIndex) InsetDivider()
        }
    }
}

@Composable
internal fun PluginMarketCommentRow(
    comment: PluginMarketComment,
    deleting: Boolean,
    enabled: Boolean,
    onDelete: () -> Unit,
    onReply: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier.weight(1f)
                .clip(RoundedCornerShape(6.dp))
                .clickable(enabled = enabled, onClick = onReply)
                .padding(vertical = 7.dp)
        ) {
            Text(
                text = comment.userNickname.ifBlank { "微信用户" },
                color = MiuixTheme.colorScheme.onSurface,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = formatPluginMarketTime(comment.createdAt),
                color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                fontSize = 11.sp
            )
            Text(
                text = comment.content,
                color = MiuixTheme.colorScheme.onSurface,
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
            )
        }
        if (comment.canDelete) {
            TextButton(
                text = if (deleting) "正在删除" else "删除",
                onClick = onDelete,
                enabled = enabled && !deleting,
                colors = ButtonDefaults.textButtonColorsPrimary()
            )
        }
    }
}

@Composable
internal fun PluginMarketReplyRow(
    comment: PluginMarketComment,
    deleting: Boolean,
    enabled: Boolean,
    onDelete: () -> Unit,
    onReply: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 1.dp),
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier.weight(1f)
                .clip(RoundedCornerShape(4.dp))
                .clickable(enabled = enabled, onClick = onReply)
                .padding(vertical = 6.dp)
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontWeight = FontWeight.Medium)) {
                        append(comment.userNickname.ifBlank { "微信用户" })
                    }
                    append(" 回复 ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Medium)) {
                        append(comment.replyToNickname.ifBlank { "微信用户" })
                    }
                    append("：")
                    append(comment.content)
                },
                color = MiuixTheme.colorScheme.onSurface,
                fontSize = 13.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = formatPluginMarketTime(comment.createdAt),
                color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                fontSize = 11.sp,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
        if (comment.canDelete) {
            TextButton(
                text = if (deleting) "正在删除" else "删除",
                onClick = onDelete,
                enabled = enabled && !deleting,
                colors = ButtonDefaults.textButtonColorsPrimary()
            )
        }
    }
}

internal data class PluginMarketCommentThread(
    val root: PluginMarketComment,
    val replies: List<PluginMarketComment>
)

internal fun buildPluginMarketCommentThreads(
    comments: List<PluginMarketComment>
): List<PluginMarketCommentThread> {
    val commentsById = comments.associateBy { it.commentId }
    val roots = comments.filter { comment ->
        comment.parentCommentId.isBlank() || comment.parentCommentId !in commentsById
    }.sortedWith(
        compareByDescending<PluginMarketComment> { it.createdAt }
            .thenByDescending { it.commentId }
    )
    val repliesByRoot = linkedMapOf<String, MutableList<PluginMarketComment>>()
    comments.filter { it.parentCommentId.isNotBlank() }.forEach { comment ->
        var cursor = comment
        val visited = mutableSetOf(comment.commentId)
        while (cursor.parentCommentId.isNotBlank()) {
            val parent = commentsById[cursor.parentCommentId] ?: break
            if (!visited.add(parent.commentId)) break
            cursor = parent
        }
        if (cursor.commentId != comment.commentId) {
            repliesByRoot.getOrPut(cursor.commentId) { mutableListOf() }.add(comment)
        }
    }
    return roots.map { root ->
        PluginMarketCommentThread(
            root = root,
            replies = repliesByRoot[root.commentId].orEmpty().sortedWith(
                compareBy<PluginMarketComment> { it.createdAt }.thenBy { it.commentId }
            )
        )
    }
}

@Composable
internal fun PluginMarketDialogInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
            fontSize = 13.sp
        )
        Text(
            text = value,
            color = MiuixTheme.colorScheme.onSurface,
            fontSize = 13.sp,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f).padding(start = 16.dp)
        )
    }
}

@Composable
internal fun PluginMarketUploadDialog(
    context: Context,
    onDismiss: (Boolean) -> Unit
) {
    val scope = rememberCoroutineScope()
    var loading by remember { mutableStateOf(true) }
    var loadError by remember { mutableStateOf("") }
    var localPlugins by remember { mutableStateOf<List<ScriptPluginRuntime.ScriptPlugin>>(emptyList()) }
    var query by rememberSaveable { mutableStateOf("") }
    var selectedIds by remember { mutableStateOf<Set<String>>(emptySet()) }
    var remoteNames by remember { mutableStateOf<Map<String, String>>(emptyMap()) }
    var releaseNotes by remember { mutableStateOf<Map<String, String>>(emptyMap()) }
    var extraFiles by remember { mutableStateOf<Map<String, List<PluginMarketFile>>>(emptyMap()) }
    var statuses by remember { mutableStateOf<Map<String, PluginMarketUploadStatus>>(emptyMap()) }
    var uploading by remember { mutableStateOf(false) }
    var uploadedAny by remember { mutableStateOf(false) }
    var reloadVersion by remember { mutableStateOf(0) }

    LaunchedEffect(reloadVersion) {
        loading = true
        loadError = ""
        val result = runCatching {
            withContext(Dispatchers.IO) { ScriptPluginRuntime.listPlugins(context) }
        }
        result.fold(
            onSuccess = { plugins ->
                localPlugins = plugins
                remoteNames = plugins.associate { plugin ->
                    plugin.id to (plugin.displayName ?: plugin.name).ifBlank { plugin.id }
                }
                releaseNotes = plugins.associate { plugin ->
                    plugin.id to releaseNotes[plugin.id].orEmpty()
                }
                extraFiles = extraFiles.filterKeys { id -> plugins.any { it.id == id } }
                selectedIds = selectedIds.intersect(plugins.mapTo(LinkedHashSet()) { it.id })
            },
            onFailure = { error -> loadError = pluginMarketErrorMessage(error) }
        )
        loading = false
    }

    val normalizedQuery = query.trim().lowercase(Locale.US)
    val filteredPlugins = localPlugins.filter { plugin ->
        normalizedQuery.isEmpty() ||
            plugin.id.lowercase(Locale.US).contains(normalizedQuery) ||
            plugin.name.lowercase(Locale.US).contains(normalizedQuery) ||
            plugin.author.lowercase(Locale.US).contains(normalizedQuery)
    }
    val allFilteredSelected = filteredPlugins.isNotEmpty() && filteredPlugins.all { it.id in selectedIds }

    fun startUpload() {
        if (uploading || selectedIds.isEmpty()) return
        val ordered = localPlugins.filter { it.id in selectedIds }
        if (ordered.any { remoteNames[it.id].orEmpty().trim().isBlank() }) {
            Toast.makeText(context, "在线插件名不能为空", Toast.LENGTH_SHORT).show()
            return
        }
        statuses = ordered.associate { plugin ->
            plugin.id to PluginMarketUploadStatus(PluginMarketUploadPhase.QUEUED, "等待上传")
        }
        uploading = true
        scope.launch {
            var publishedCount = 0
            var pendingCount = 0
            ordered.forEach { plugin ->
                statuses = statuses + (
                    plugin.id to PluginMarketUploadStatus(PluginMarketUploadPhase.UPLOADING, "正在上传")
                )
                val result = withContext(Dispatchers.IO) {
                    PluginMarketRepository.upload(
                        context = context,
                        localPluginId = plugin.id,
                        remoteName = remoteNames[plugin.id].orEmpty().trim(),
                        releaseNotes = releaseNotes[plugin.id].orEmpty().trim(),
                        extraFiles = extraFiles[plugin.id].orEmpty()
                    )
                }
                result.fold(
                    onSuccess = { ownership ->
                        uploadedAny = true
                        when (ownership.reviewStatus) {
                            PluginMarketReviewStatus.PENDING -> {
                                pendingCount++
                                statuses = statuses + (
                                    plugin.id to PluginMarketUploadStatus(
                                        PluginMarketUploadPhase.PENDING_REVIEW,
                                        "上传成功，待审核 · ${ownership.remotePluginId}"
                                    )
                                )
                            }
                            PluginMarketReviewStatus.APPROVED -> {
                                publishedCount++
                                statuses = statuses + (
                                    plugin.id to PluginMarketUploadStatus(
                                        PluginMarketUploadPhase.SUCCESS,
                                        "上传成功 · ${ownership.remotePluginId}"
                                    )
                                )
                            }
                        }
                    },
                    onFailure = { error ->
                        statuses = statuses + (
                            plugin.id to PluginMarketUploadStatus(
                                PluginMarketUploadPhase.FAILED,
                                "上传失败: ${pluginMarketErrorMessage(error)}"
                            )
                        )
                    }
                )
            }
            uploading = false
            Toast.makeText(
                context,
                "上传完成：已发布 $publishedCount，待审核 $pendingCount，失败 ${ordered.size - publishedCount - pendingCount}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    WindowDialog(
        show = true,
        title = "上传本地插件",
        onDismissRequest = { if (!uploading) onDismiss(uploadedAny) },
        content = {
            Column(modifier = Modifier.fillMaxWidth().heightIn(max = 620.dp)) {
                SearchBarSurface(
                    query = query,
                    placeholder = "搜索本地插件",
                    onQueryChange = { if (!uploading) query = it }
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "已选 ${selectedIds.size} / ${localPlugins.size}",
                        color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                        fontSize = 12.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = if (allFilteredSelected) "取消当前筛选" else "全选当前筛选",
                        color = if (filteredPlugins.isEmpty() || uploading) {
                            MiuixTheme.colorScheme.onSurfaceVariantSummary
                        } else {
                            MiuixTheme.colorScheme.primary
                        },
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.clickable(enabled = filteredPlugins.isNotEmpty() && !uploading) {
                            val filteredIds = filteredPlugins.mapTo(LinkedHashSet()) { it.id }
                            selectedIds = if (allFilteredSelected) {
                                selectedIds - filteredIds
                            } else {
                                selectedIds + filteredIds
                            }
                        }.padding(horizontal = 8.dp, vertical = 6.dp)
                    )
                }
                LazyColumn(modifier = Modifier.fillMaxWidth().weight(1f, fill = false)) {
                    when {
                        loading -> item { EmptyText("正在读取本地插件...") }
                        loadError.isNotBlank() -> item {
                            Column {
                                EmptyText(loadError)
                                TextButton(
                                    text = "重试",
                                    onClick = { reloadVersion++ },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = ButtonDefaults.textButtonColorsPrimary()
                                )
                            }
                        }
                        localPlugins.isEmpty() -> item { EmptyText("没有可运行的本地插件") }
                        filteredPlugins.isEmpty() -> item { EmptyText("没有匹配的本地插件") }
                        else -> items(filteredPlugins.size, key = { filteredPlugins[it].id }) { index ->
                            val plugin = filteredPlugins[index]
                            if (index > 0) InsetDivider(start = 0.dp)
                            PluginMarketUploadRow(
                                plugin = plugin,
                                selected = plugin.id in selectedIds,
                                remoteName = remoteNames[plugin.id].orEmpty(),
                                releaseNotes = releaseNotes[plugin.id].orEmpty(),
                                extraFiles = extraFiles[plugin.id].orEmpty(),
                                status = statuses[plugin.id],
                                enabled = !uploading,
                                onSelectedChange = { selected ->
                                    selectedIds = if (selected) selectedIds + plugin.id else selectedIds - plugin.id
                                },
                                onRemoteNameChange = { name ->
                                    remoteNames = remoteNames + (plugin.id to name.take(100))
                                },
                                onReleaseNotesChange = { notes ->
                                    releaseNotes = releaseNotes + (plugin.id to notes.take(500))
                                },
                                onPickExtraFiles = {
                                    ScriptPluginAgentUi.findAgentActivity(context)?.let { activity ->
                                        PluginMarketExtraFilePickerBridge.launch(activity) { files ->
                                            val existing = extraFiles[plugin.id].orEmpty()
                                            val merged = (files + existing).distinctBy {
                                                it.name.lowercase(Locale.ROOT)
                                            }
                                            extraFiles = extraFiles + (plugin.id to merged)
                                        }
                                    } ?: Toast.makeText(
                                        context,
                                        "无法打开文件选择器",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                onClearExtraFiles = {
                                    extraFiles = extraFiles - plugin.id
                                }
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    TextButton(
                        text = "关闭",
                        onClick = { onDismiss(uploadedAny) },
                        enabled = !uploading,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.textButtonColorsPrimary()
                    )
                    TextButton(
                        text = if (uploading) "正在上传" else "上传已选",
                        onClick = ::startUpload,
                        enabled = !uploading && selectedIds.isNotEmpty(),
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.textButtonColorsPrimary()
                    )
                }
            }
        }
    )
}

@Composable
internal fun PluginMarketUploadRow(
    plugin: ScriptPluginRuntime.ScriptPlugin,
    selected: Boolean,
    remoteName: String,
    releaseNotes: String,
    extraFiles: List<PluginMarketFile>,
    status: PluginMarketUploadStatus?,
    enabled: Boolean,
    onSelectedChange: (Boolean) -> Unit,
    onRemoteNameChange: (String) -> Unit,
    onReleaseNotesChange: (String) -> Unit,
    onPickExtraFiles: () -> Unit,
    onClearExtraFiles: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().clickable(enabled = enabled) { onSelectedChange(!selected) }
                .padding(horizontal = 4.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = plugin.name.ifBlank { plugin.id },
                    color = MiuixTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = buildString {
                        append(plugin.id)
                        append(" · ").append(plugin.author.ifBlank { "未知作者" })
                        append(" · ").append(plugin.version.ifBlank { "未知版本" })
                    },
                    color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            SelectionMark(selected = selected, multiSelect = true)
        }
        if (selected) {
            Text(
                text = "在线名称",
                color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
            BasicTextField(
                value = remoteName,
                onValueChange = onRemoteNameChange,
                enabled = enabled,
                singleLine = true,
                textStyle = TextStyle(color = MiuixTheme.colorScheme.onSurface, fontSize = 14.sp),
                cursorBrush = SolidColor(MiuixTheme.colorScheme.primary),
                modifier = Modifier.fillMaxWidth().padding(top = 5.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MiuixTheme.colorScheme.secondaryVariant)
                    .padding(horizontal = 12.dp, vertical = 10.dp)
            )
            Text(
                text = "更新说明",
                color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 4.dp, top = 8.dp)
            )
            BasicTextField(
                value = releaseNotes,
                onValueChange = onReleaseNotesChange,
                enabled = enabled,
                minLines = 2,
                maxLines = 3,
                textStyle = TextStyle(color = MiuixTheme.colorScheme.onSurface, fontSize = 14.sp),
                cursorBrush = SolidColor(MiuixTheme.colorScheme.primary),
                decorationBox = { innerTextField ->
                    Box(contentAlignment = Alignment.TopStart) {
                        if (releaseNotes.isBlank()) {
                            Text(
                                text = "填写本次版本的更新内容",
                                color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                                fontSize = 14.sp
                            )
                        }
                        innerTextField()
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(top = 5.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MiuixTheme.colorScheme.secondaryVariant)
                    .padding(horizontal = 12.dp, vertical = 10.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "附加文件${if (extraFiles.isEmpty()) "" else " (${extraFiles.size})"}",
                        color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                        fontSize = 12.sp
                    )
                    if (extraFiles.isNotEmpty()) {
                        Text(
                            text = extraFiles.joinToString("、") { it.name },
                            color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                            fontSize = 12.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
                TextButton(
                    text = "选择",
                    onClick = onPickExtraFiles,
                    enabled = enabled,
                    colors = ButtonDefaults.textButtonColorsPrimary()
                )
                if (extraFiles.isNotEmpty()) {
                    TextButton(
                        text = "清空",
                        onClick = onClearExtraFiles,
                        enabled = enabled,
                        colors = ButtonDefaults.textButtonColorsPrimary()
                    )
                }
            }
        }
        if (status != null) {
            val color = when (status.phase) {
                PluginMarketUploadPhase.SUCCESS -> MiuixTheme.colorScheme.primary
                PluginMarketUploadPhase.PENDING_REVIEW -> MiuixTheme.colorScheme.onSurfaceVariantSummary
                PluginMarketUploadPhase.FAILED -> Color(0xFFD93025)
                else -> MiuixTheme.colorScheme.onSurfaceVariantSummary
            }
            Text(
                text = status.message,
                color = color,
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth().padding(start = 4.dp, top = 6.dp)
            )
        }
    }
}

internal fun pluginMarketErrorMessage(error: Throwable): String {
    if (error is PluginMarketException && error.errorCode == "UPLOADER_BLACKLISTED") {
        return "当前微信账号已被禁止上传在线插件"
    }
    return error.message?.trim().takeUnless { it.isNullOrBlank() } ?: error.javaClass.simpleName
}

}

internal object PluginMarketHistoryUi {
    @Composable
    fun HistoryList(
        versions: List<PluginMarketHistoryVersion>,
        loading: Boolean,
        error: String,
        installingVersionId: String?,
        enabled: Boolean,
        onRetry: () -> Unit,
        onInstall: (PluginMarketHistoryVersion) -> Unit
    ) {
        Text(
            text = "历史版本",
            color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 14.dp)
        )
        when {
            loading -> EmptyText("正在加载历史版本...")
            error.isNotBlank() -> {
                EmptyText(error)
                TextButton(
                    text = "重新加载",
                    onClick = onRetry,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.textButtonColorsPrimary()
                )
            }
            versions.isEmpty() -> EmptyText("暂无历史版本")
            else -> versions.forEachIndexed { index, version ->
                if (index > 0) InsetDivider(start = 0.dp)
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = buildString {
                                append(version.versionName.ifBlank { "未命名版本" })
                                if (index == 0) append("（最新）")
                            },
                            color = MiuixTheme.colorScheme.onSurface,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "${formatPluginMarketTime(version.createdAt)} · ${formatBytes(version.totalSize)}",
                            color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                            fontSize = 11.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "更新说明：${version.releaseNotes.ifBlank { "暂无更新说明" }}",
                            color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 3.dp)
                        )
                    }
                    TextButton(
                        text = if (installingVersionId == version.versionId) "读取中" else "安装",
                        onClick = { onInstall(version) },
                        enabled = enabled && installingVersionId == null,
                        colors = ButtonDefaults.textButtonColorsPrimary()
                    )
                }
            }
        }
    }

    private fun formatBytes(size: Long): String = when {
        size >= 1024L * 1024L -> String.format(Locale.US, "%.1f MiB", size / (1024.0 * 1024.0))
        size >= 1024L -> String.format(Locale.US, "%.1f KiB", size / 1024.0)
        else -> "$size B"
    }
}

internal val pluginMarketBeijingTimeFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
        .withZone(ZoneId.of("Asia/Shanghai"))

internal object ScriptPluginAgentUi {

internal data class ScriptPluginAgentPendingWrite(
    val messageIndex: Int,
    val draft: ScriptPluginAgentDraft,
    val diff: String,
    val isNewPlugin: Boolean,
    val confirmsCreation: Boolean,
    val risky: Boolean
)

internal data class ScriptPluginAgentPendingDelete(
    val messageIndex: Int,
    val pluginId: String,
    val pluginName: String
)

internal data class ScriptPluginAgentPendingWorkspaceChange(
    val messageIndex: Int,
    val change: ScriptPluginAgentWorkspaceChange,
    val isNewPlugin: Boolean,
    val hasDeletedPaths: Boolean,
    val risky: Boolean,
    val applyError: String = ""
)

internal class ScriptPluginAgentWorkspaceToolApproval(
    val confirmation: ScriptPluginAgentWorkspaceToolConfirmation
) {
    private val decision = AtomicReference<ScriptPluginAgentWorkspaceWriteDecision?>(null)
    private val latch = CountDownLatch(1)

    fun resolve(value: ScriptPluginAgentWorkspaceWriteDecision) {
        if (decision.compareAndSet(null, value)) latch.countDown()
    }

    fun await(cancellation: ScriptPluginAgentCancellation): ScriptPluginAgentWorkspaceWriteDecision {
        while (!latch.await(200L, TimeUnit.MILLISECONDS)) cancellation.throwIfCancelled()
        cancellation.throwIfCancelled()
        return decision.get() ?: ScriptPluginAgentWorkspaceWriteDecision.CANCEL
    }
}

internal data class ScriptPluginAgentPendingMessage(
    val id: Long,
    val content: String,
    val attachments: List<ScriptPluginAgentAttachment>,
    val quotedMessage: ScriptPluginAgentQuotedMessage?
)

internal data class ScriptPluginAgentRenderedMessage(
    val sourceIndex: Int,
    val message: ScriptPluginAgentChatMessage
)

internal class ScriptPluginAgentSessionRuntimeState(session: ScriptPluginAgentSession) {
    val id = session.id
    val title = mutableStateOf(session.title)
    val createdAt = mutableStateOf(session.createdAt)
    val messages = mutableStateOf(session.messages)
    val draft = mutableStateOf(session.draft)
    val targetPluginId = mutableStateOf(session.targetPluginId)
    val workspaceSessionId = mutableStateOf(session.id)
    val conversationSummary = mutableStateOf(session.conversationSummary)
    val nativeToolHistory = mutableStateOf(session.nativeToolHistory)
    val protocolTranscript = mutableStateOf(session.protocolTranscript)
    val compactedMessageCount = mutableStateOf(session.compactedMessageCount)
    val pinned = mutableStateOf(session.pinned)
    val locked = mutableStateOf(session.locked)
    val sortOrder = mutableStateOf(session.sortOrder)
    val resumeState = mutableStateOf(session.resumeState)
    val messageInput = mutableStateOf("")
    val pendingAttachments = mutableStateOf<List<ScriptPluginAgentAttachment>>(emptyList())
    val pendingQuotedMessage = mutableStateOf<ScriptPluginAgentQuotedMessage?>(null)
    val pendingMessages = mutableStateOf<List<ScriptPluginAgentPendingMessage>>(emptyList())
    val pendingMessagesExpanded = mutableStateOf(true)
    val nextPendingMessageId = mutableStateOf(1L)
    val generating = mutableStateOf(false)
    val generationStartedAt = mutableStateOf(0L)
    val generationHasVisibleReply = mutableStateOf(false)
    val contextCompacting = mutableStateOf(false)
    val contextCompactionStartedAt = mutableStateOf(0L)
    val applyingFileOperation = mutableStateOf(false)
    val pendingWrite = mutableStateOf<ScriptPluginAgentPendingWrite?>(null)
    val pendingDelete = mutableStateOf<ScriptPluginAgentPendingDelete?>(null)
    val pendingWorkspaceChange = mutableStateOf<ScriptPluginAgentPendingWorkspaceChange?>(null)
    val pendingToolApproval = mutableStateOf<ScriptPluginAgentWorkspaceToolApproval?>(null)
    val showPendingWorkspaceDialog = mutableStateOf(false)
    val showPendingWriteDialog = mutableStateOf(false)
    val showPendingDeleteDialog = mutableStateOf(false)
    val showToolApprovalDialog = mutableStateOf(false)
    val activeGenerationId = mutableStateOf("")
    val activeAssistantIndex = mutableStateOf(-1)
    val activeCancellation = mutableStateOf<ScriptPluginAgentCancellation?>(null)
    private val lastSnapshotAt = AtomicLong(session.updatedAt)
    private val checkpointSeq = AtomicLong(session.checkpointSeq)

    fun snapshot(updatedAt: Long = nextSnapshotAt()): ScriptPluginAgentSession {
        val workspaceBelongsToSession = workspaceSessionId.value == id
        return ScriptPluginAgentSession(
            id = id,
            title = title.value,
            createdAt = createdAt.value,
            updatedAt = updatedAt,
            messages = messages.value,
            draft = draft.value.takeIf { workspaceBelongsToSession },
            targetPluginId = targetPluginId.value.takeIf { workspaceBelongsToSession }.orEmpty(),
            conversationSummary = conversationSummary.value,
            nativeToolHistory = nativeToolHistory.value,
            protocolTranscript = protocolTranscript.value,
            compactedMessageCount = compactedMessageCount.value.coerceIn(0, messages.value.size),
            pinned = pinned.value,
            locked = locked.value,
            sortOrder = sortOrder.value,
            resumeState = resumeState.value,
            checkpointSeq = checkpointSeq.incrementAndGet()
        )
    }

    private fun nextSnapshotAt(): Long {
        return lastSnapshotAt.updateAndGet { previous ->
            maxOf(System.currentTimeMillis(), previous + 1L)
        }
    }

    fun isBusy(): Boolean {
        return isWorking() ||
            pendingToolApproval.value != null || pendingWorkspaceChange.value != null ||
            pendingWrite.value != null || pendingDelete.value != null
    }

    fun isWorking(): Boolean {
        return generating.value || contextCompacting.value || applyingFileOperation.value
    }
}

internal class ScriptPluginAgentSessionCheckpointSaver(
    context: Context,
    private val state: ScriptPluginAgentSessionRuntimeState
) {
    private val appContext = context.applicationContext ?: context
    private val handler = Handler(Looper.getMainLooper())
    private var scheduled = false
    private var lastQueuedAt = 0L
    private val saveRunnable = Runnable {
        scheduled = false
        persist()
    }

    fun schedule(force: Boolean = false) {
        val now = SystemClock.uptimeMillis()
        if (force || now - lastQueuedAt >= 600L) {
            handler.removeCallbacks(saveRunnable)
            scheduled = false
            persist()
            return
        }
        if (!scheduled) {
            scheduled = true
            handler.postDelayed(saveRunnable, (600L - (now - lastQueuedAt)).coerceAtLeast(1L))
        }
    }

    private fun persist() {
        if (!ScriptPluginAgentSessionStore.hasConversation(state.messages.value)) return
        lastQueuedAt = SystemClock.uptimeMillis()
        ScriptPluginAgentSessionStore.saveAsync(appContext, state.snapshot())
    }
}

internal object ScriptPluginAgentRuntime {
    private val sessions = java.util.concurrent.ConcurrentHashMap<String, ScriptPluginAgentSessionRuntimeState>()

    @Volatile
    private var resumeSessionId: String? = null

    fun createSession(): ScriptPluginAgentSessionRuntimeState {
        return stateFor(ScriptPluginAgentSessionStore.newSession())
    }

    fun stateFor(session: ScriptPluginAgentSession): ScriptPluginAgentSessionRuntimeState {
        return sessions.computeIfAbsent(session.id) { ScriptPluginAgentSessionRuntimeState(session) }
    }

    fun consumeResumeSession(context: Context): ScriptPluginAgentSessionRuntimeState? {
        val id = resumeSessionId
        resumeSessionId = null
        if (id != null) {
            return sessions[id] ?: ScriptPluginAgentSessionStore.load(context, id)?.let(::stateFor)
        }
        val interrupted = ScriptPluginAgentSessionStore.list(context).firstOrNull { session ->
            val checkpoint = session.resumeState?.takeIf { it.autoOpen } ?: return@firstOrNull false
            session.messages.any { message ->
                message.turnId == checkpoint.turnId &&
                    (message.status == "interrupted" || message.status == "error" ||
                        message.toolEvents.any { it.status == "interrupted" })
            }
        }
        return interrupted?.let(::stateFor)
    }

    fun resumeOnNextEntry(sessionId: String) {
        resumeSessionId = sessionId
    }

    fun snapshots(): List<ScriptPluginAgentSession> {
        return sessions.values
            .map { it.snapshot() }
            .filter { ScriptPluginAgentSessionStore.hasConversation(it.messages) }
    }

    fun isBusy(sessionId: String): Boolean {
        return sessions[sessionId]?.isBusy() == true
    }

    fun workingSessionIds(): Set<String> {
        return sessions.values
            .filter { it.isWorking() }
            .mapTo(LinkedHashSet()) { it.id }
    }

    fun updateMetadata(session: ScriptPluginAgentSession) {
        sessions[session.id]?.let { state ->
            state.title.value = session.title
            state.pinned.value = session.pinned
            state.locked.value = session.locked
            state.sortOrder.value = session.sortOrder
        }
    }

    fun remove(sessionId: String) {
        sessions.remove(sessionId)
        if (resumeSessionId == sessionId) resumeSessionId = null
    }
}

@Composable
fun ScriptPluginAgentWorkspacePage(
    context: Context,
    onBack: () -> Unit,
    onExitHandlerChanged: ((() -> Unit)?) -> Unit
) {
    val initialSessionState = remember(context) {
        ScriptPluginAgentRuntime.consumeResumeSession(context) ?: ScriptPluginAgentRuntime.createSession()
    }
    var activeSessionState by remember { mutableStateOf(initialSessionState) }
    val renderedSessionState = activeSessionState
    val activeSessionId = renderedSessionState.id
    var sessionTitle by renderedSessionState.title
    var sessionMessages by renderedSessionState.messages
    var sessionDraft by renderedSessionState.draft
    var targetPluginId by renderedSessionState.targetPluginId
    var workspaceSessionId by renderedSessionState.workspaceSessionId
    var conversationSummary by renderedSessionState.conversationSummary
    var nativeToolHistory by renderedSessionState.nativeToolHistory
    var protocolTranscript by renderedSessionState.protocolTranscript
    var compactedMessageCount by renderedSessionState.compactedMessageCount
    var sessionPinned by renderedSessionState.pinned
    var sessionLocked by renderedSessionState.locked
    var sessionSortOrder by renderedSessionState.sortOrder
    var resumeState by renderedSessionState.resumeState
    var messageInput by renderedSessionState.messageInput
    var pendingAttachments by renderedSessionState.pendingAttachments
    var pendingQuotedMessage by renderedSessionState.pendingQuotedMessage
    var pendingMessages by renderedSessionState.pendingMessages
    var pendingMessagesExpanded by renderedSessionState.pendingMessagesExpanded
    var nextPendingMessageId by renderedSessionState.nextPendingMessageId
    val initialProfile = remember(context) { ScriptPluginAgentSettings.loadActiveProfile(context) }
    val savedConfig = initialProfile.config
    var activeProfileId by remember { mutableStateOf(initialProfile.id) }
    var activeProfileName by remember { mutableStateOf(initialProfile.name) }
    var apiBase by remember { mutableStateOf(savedConfig.apiBaseUrl) }
    var endpointMode by remember { mutableStateOf(savedConfig.endpointMode) }
    var apiKey by remember { mutableStateOf(savedConfig.apiKey) }
    var model by remember { mutableStateOf(savedConfig.model) }
    var reasoningEffort by remember { mutableStateOf(savedConfig.reasoningEffort) }
    var mcpServers by remember { mutableStateOf(savedConfig.mcpServers) }
    var autoCompactEnabled by remember { mutableStateOf(savedConfig.autoCompactEnabled) }
    var webSearchEnabled by remember { mutableStateOf(savedConfig.webSearchEnabled) }
    var workspaceWriteApprovalMode by remember { mutableStateOf(savedConfig.workspaceWriteApprovalMode) }
    var promptCacheMode by remember { mutableStateOf(savedConfig.promptCacheMode) }
    var compactTokenThreshold by remember { mutableStateOf(savedConfig.compactTokenThreshold.toString()) }
    var codexProfiles by remember(context) { mutableStateOf(CodexSettings.profiles(context)) }
    var codexProfile by remember(context) { mutableStateOf(CodexSettings.active(context)) }
    var showCodexModels by remember { mutableStateOf(false) }
    var settingsTab by remember { mutableStateOf(0) }
    var testingCodexConnection by remember { mutableStateOf(false) }
    var showHistory by remember { mutableStateOf(false) }
    var showConfig by remember { mutableStateOf(false) }
    var showModelPicker by remember { mutableStateOf(false) }
    var showQuickProfilePicker by remember { mutableStateOf(false) }
    var showQuickOptions by remember { mutableStateOf(false) }
    var showSkills by remember { mutableStateOf(false) }
    var openSkillsAfterQuickOptions by remember { mutableStateOf(false) }
    var skillBackHandler by remember { mutableStateOf<(() -> Unit)?>(null) }
    var generating by renderedSessionState.generating
    var generationStartedAt by renderedSessionState.generationStartedAt
    var generationHasVisibleReply by renderedSessionState.generationHasVisibleReply
    var contextCompacting by renderedSessionState.contextCompacting
    var contextCompactionStartedAt by renderedSessionState.contextCompactionStartedAt
    var applyingFileOperation by renderedSessionState.applyingFileOperation
    var pendingWrite by renderedSessionState.pendingWrite
    var pendingDelete by renderedSessionState.pendingDelete
    var pendingWorkspaceChange by renderedSessionState.pendingWorkspaceChange
    var pendingToolApproval by renderedSessionState.pendingToolApproval
    var showPendingWorkspaceDialog by renderedSessionState.showPendingWorkspaceDialog
    var showPendingWriteDialog by renderedSessionState.showPendingWriteDialog
    var showPendingDeleteDialog by renderedSessionState.showPendingDeleteDialog
    var showToolApprovalDialog by renderedSessionState.showToolApprovalDialog
    var testingConnection by remember { mutableStateOf(false) }
    var activeGenerationId by renderedSessionState.activeGenerationId
    var activeAssistantIndex by renderedSessionState.activeAssistantIndex
    var activeCancellation by renderedSessionState.activeCancellation
    var historyVersion by remember { mutableStateOf(0) }
    var profileVersion by remember { mutableStateOf(0) }
    val sessions = remember(historyVersion, showHistory) {
        if (showHistory) ScriptPluginAgentSessionStore.list(context) else emptyList()
    }
    val profiles = remember(profileVersion) { ScriptPluginAgentSettings.loadProfiles(context) }
    val checkpointSaver = remember(renderedSessionState) {
        ScriptPluginAgentSessionCheckpointSaver(context, renderedSessionState)
    }
    val exitPrepared = remember { AtomicBoolean(false) }

    fun hasPendingConfirmation(): Boolean {
        return pendingToolApproval != null ||
            pendingWorkspaceChange != null ||
            pendingWrite != null ||
            pendingDelete != null
    }

    fun showPendingConfirmation(): Boolean {
        when {
            pendingToolApproval != null -> showToolApprovalDialog = true
            pendingWorkspaceChange != null -> showPendingWorkspaceDialog = true
            pendingWrite != null -> showPendingWriteDialog = true
            pendingDelete != null -> showPendingDeleteDialog = true
            else -> return false
        }
        return true
    }

    fun saveCurrentSession() {
        if (!ScriptPluginAgentSessionStore.hasConversation(sessionMessages)) {
            val emptySessionId = activeSessionId
            ScriptPluginAgentRuntime.remove(emptySessionId)
            Thread({
                runCatching {
                    ScriptPluginAgentSessionStore.discardEmptySession(context, emptySessionId)
                }.onFailure { error ->
                    h.Hchat.utils.HLog.e(
                        "[Hchat:ScriptAgent] 清理空会话失败: $emptySessionId",
                        error
                    )
                }
            }, "Hchat-Agent-Empty-Session-Delete").start()
            return
        }
        ScriptPluginAgentSessionStore.revive(activeSessionId)
        ScriptPluginAgentSessionStore.saveAsync(context, renderedSessionState.snapshot())
        historyVersion++
    }

    fun openSession(session: ScriptPluginAgentSession, saveCurrent: Boolean = true) {
        if (saveCurrent) {
            if (!generating) {
                resumeState = resumeState?.copy(autoOpen = false, updatedAt = System.currentTimeMillis())
            }
            saveCurrentSession()
        }
        activeSessionState = ScriptPluginAgentRuntime.stateFor(session)
        showConfig = false
        showModelPicker = false
        showQuickProfilePicker = false
        showQuickOptions = false
        showSkills = false
        showHistory = false
    }

    fun newSession(saveCurrent: Boolean = true) {
        if (saveCurrent) {
            if (!generating) {
                resumeState = resumeState?.copy(autoOpen = false, updatedAt = System.currentTimeMillis())
            }
            saveCurrentSession()
        }
        activeSessionState = ScriptPluginAgentRuntime.createSession()
        showConfig = false
        showModelPicker = false
        showQuickProfilePicker = false
        showQuickOptions = false
        showSkills = false
        showHistory = false
    }

    fun currentConfig(): ScriptPluginAgentConfig {
        return ScriptPluginAgentConfig(
            apiBaseUrl = ScriptPluginAgentSettings.normalizedApiAddress(apiBase, endpointMode),
            apiPath = "",
            apiKey = apiKey.trim(),
            model = model.trim(),
            mcpServers = mcpServers,
            autoCompactEnabled = autoCompactEnabled,
            compactTokenThreshold = compactTokenThreshold.toIntOrNull()
                ?.coerceIn(2_000, 1_000_000)
                ?: ScriptPluginAgentSettings.DEFAULT_COMPACT_TOKEN_THRESHOLD,
            webSearchEnabled = webSearchEnabled,
            workspaceWriteApprovalMode = workspaceWriteApprovalMode,
            promptCacheMode = promptCacheMode,
            endpointMode = endpointMode,
            reasoningEffort = ScriptPluginAgentReasoning.effectiveEffort(endpointMode, model, reasoningEffort)
        )
    }

    fun endpointError(config: ScriptPluginAgentConfig): String {
        if (ScriptPluginAgentSettings.isValidRequestUrl(config)) return ""
        return if (config.endpointMode == ScriptPluginAgentSettings.ENDPOINT_MODE_CUSTOM_URL) {
            "请填写完整的 HTTP(S) 请求链接"
        } else {
            "API 地址无效"
        }
    }

    fun applyProfile(profile: ScriptPluginAgentProfile) {
        activeProfileId = profile.id
        activeProfileName = profile.name
        apiBase = profile.config.apiBaseUrl
        endpointMode = profile.config.endpointMode
        apiKey = profile.config.apiKey
        model = profile.config.model
        reasoningEffort = profile.config.reasoningEffort
        mcpServers = profile.config.mcpServers
        autoCompactEnabled = profile.config.autoCompactEnabled
        webSearchEnabled = profile.config.webSearchEnabled
        workspaceWriteApprovalMode = profile.config.workspaceWriteApprovalMode
        promptCacheMode = profile.config.promptCacheMode
        compactTokenThreshold = profile.config.compactTokenThreshold.toString()
    }

    fun selectAttachments() {
        if (applyingFileOperation) return
        val activity = context as? Activity
        if (activity == null) {
            Toast.makeText(context, "当前页面无法打开文件选择器", Toast.LENGTH_SHORT).show()
            return
        }
        ScriptPluginAgentAttachmentPickerBridge.launch(
            activity,
            ScriptPluginAgentSessionStore.attachmentDir(context, activeSessionId)
        ) { selected ->
            val retained = (pendingAttachments + selected)
                .distinctBy { it.sourceUri.ifBlank { it.path } }
                .take(12)
            val retainedPaths = retained.mapTo(HashSet()) { it.path }
            ScriptPluginAgentSessionStore.cleanupAttachments(
                context,
                selected.filterNot { it.path in retainedPaths }
            )
            pendingAttachments = retained
        }
    }

    fun cancelGeneration() {
        val cancellation = activeCancellation ?: return
        val cancelledTurnId = activeGenerationId
        pendingToolApproval?.resolve(ScriptPluginAgentWorkspaceWriteDecision.CANCEL)
        pendingToolApproval = null
        showToolApprovalDialog = false
        cancellation.cancel()
        resumeState = resumeState?.copy(autoOpen = false, updatedAt = System.currentTimeMillis())
        val indices = if (activeAssistantIndex in sessionMessages.indices) {
            setOf(activeAssistantIndex)
        } else {
            sessionMessages.indices.filter { sessionMessages[it].turnId == activeGenerationId }.toSet()
        }
        if (indices.isNotEmpty()) {
            val interruptedAt = System.currentTimeMillis()
            sessionMessages = sessionMessages.toMutableList().also { messages ->
                indices.forEach { index ->
                    val current = messages[index]
                    messages[index] = current.copy(
                        status = "interrupted",
                        completedAt = interruptedAt,
                        progress = mergeScriptPluginAgentProgress(current.progress, "已中断"),
                        toolEvents = current.toolEvents.map { event ->
                            if (event.status == "running" || event.status == "queued") {
                                event.copy(
                                    status = "interrupted",
                                    progress = "已中断",
                                    finishedAt = interruptedAt
                                )
                            } else {
                                event
                            }
                        }
                    )
                }
            }
            nativeToolHistory = ScriptPluginAgentToolResultStore.rebuildNativeToolHistory(
                context,
                ScriptPluginAgentContext.modelMessagesForTurn(
                    sessionMessages.drop(compactedMessageCount.coerceIn(0, sessionMessages.size)),
                    cancelledTurnId
                )
            )
        }
        checkpointSaver.schedule(force = true)
        activeCancellation = null
        activeGenerationId = ""
        activeAssistantIndex = -1
        generating = false
        contextCompacting = false
        contextCompactionStartedAt = 0L
        generationStartedAt = 0L
        generationHasVisibleReply = false
    }

    fun recordFileOperation(messageIndex: Int, progress: String) {
        val current = sessionMessages.getOrNull(messageIndex) ?: return
        sessionMessages = sessionMessages.toMutableList().also {
            it[messageIndex] = current.copy(
                progress = mergeScriptPluginAgentProgress(current.progress, progress),
                status = "complete"
            )
        }
        saveCurrentSession()
    }

    fun appendWorkspaceStatusMessage(
        messageIndex: Int,
        content: String,
        progress: String = "",
        draftSnapshot: ScriptPluginAgentDraft? = null,
        clearsDraft: Boolean = false
    ) {
        val source = sessionMessages.getOrNull(messageIndex) ?: return
        val now = System.currentTimeMillis()
        sessionMessages = sessionMessages + ScriptPluginAgentChatMessage(
            role = "assistant",
            content = content,
            turnId = source.turnId,
            parentMessageId = source.id,
            phase = "workspace_status",
            progress = progress,
            status = "complete",
            draftSnapshot = draftSnapshot,
            clearsDraft = clearsDraft,
            createdAt = now,
            completedAt = now
        )
        saveCurrentSession()
    }

    fun applyWorkspaceChange(messageIndex: Int, change: ScriptPluginAgentWorkspaceChange) {
        if (applyingFileOperation) return
        applyingFileOperation = true
        recordFileOperation(
            messageIndex,
            if (change.deletePlugin) "正在删除插件: ${change.pluginId}" else "正在提交插件工作区变更"
        )
        Thread({
            val result = ScriptPluginAgentWorkspaceTools.apply(context, change)
            Handler(Looper.getMainLooper()).post {
                applyingFileOperation = false
                result.onSuccess {
                    resumeState = null
                    val nextDraft = change.draft
                    val clearsCurrentDraft = change.deletePlugin &&
                        (sessionDraft?.pluginId.equals(change.pluginId, ignoreCase = true) ||
                            targetPluginId.equals(change.pluginId, ignoreCase = true))
                    if (clearsCurrentDraft) {
                        sessionDraft = null
                        targetPluginId = ""
                    } else if (nextDraft != null) {
                        sessionDraft = nextDraft
                        targetPluginId = nextDraft.pluginId
                    }
                    appendWorkspaceStatusMessage(
                        messageIndex = messageIndex,
                        content = when {
                            change.deletePlugin -> "插件已删除。"
                            change.existed -> "插件修改已提交到真实插件目录。"
                            else -> "插件已创建并写入真实插件目录。"
                        },
                        progress = when {
                            change.deletePlugin -> "已删除插件: ${change.pluginId}"
                            change.existed -> "已更新插件: ${change.pluginId}"
                            else -> "已创建插件: ${change.pluginId}"
                        },
                        draftSnapshot = sessionDraft,
                        clearsDraft = clearsCurrentDraft
                    )
                    Toast.makeText(
                        context,
                        when {
                            change.deletePlugin -> "插件已删除"
                            change.existed -> "插件已更新，当前为禁用状态"
                            else -> "插件已创建，当前为禁用状态"
                        },
                        Toast.LENGTH_LONG
                    ).show()
                }.onFailure { error ->
                    val reason = error.message ?: "未知错误"
                    pendingWorkspaceChange = ScriptPluginAgentPendingWorkspaceChange(
                        messageIndex = messageIndex,
                        change = change,
                        isNewPlugin = !change.existed,
                        hasDeletedPaths = change.deletePlugin || change.deletedPaths.isNotEmpty(),
                        risky = change.warnings.any { it.risky },
                        applyError = reason
                    )
                    showPendingWorkspaceDialog = true
                    appendWorkspaceStatusMessage(
                        messageIndex = messageIndex,
                        content = "插件修改尚未提交：$reason",
                        progress = "提交失败，暂存变更已保留，可直接重试"
                    )
                    Toast.makeText(context, "$reason\n暂存修改已保留，可直接重试", Toast.LENGTH_LONG).show()
                }
            }
        }, "Hchat-Script-Agent-Workspace-Apply").start()
    }

    fun prepareWorkspaceChange(
        messageIndex: Int,
        change: ScriptPluginAgentWorkspaceChange,
        requireConfirmation: Boolean
    ) {
        appendWorkspaceStatusMessage(
            messageIndex = messageIndex,
            content = if (change.deletePlugin) {
                "插件删除操作已暂存，尚未提交。"
            } else {
                "插件修改已暂存，尚未写入真实插件目录。"
            }
        )
        if (!requireConfirmation) {
            applyWorkspaceChange(messageIndex, change)
            return
        }
        val isNewPlugin = !change.existed
        val hasDeletedPaths = change.deletePlugin || change.deletedPaths.isNotEmpty()
        val risky = change.warnings.any { it.risky }
        pendingWorkspaceChange = ScriptPluginAgentPendingWorkspaceChange(
            messageIndex = messageIndex,
            change = change,
            isNewPlugin = isNewPlugin,
            hasDeletedPaths = hasDeletedPaths,
            risky = risky
        )
        showPendingWorkspaceDialog = true
        recordFileOperation(
            messageIndex,
            when {
                change.deletePlugin -> "等待确认删除插件"
                isNewPlugin && risky -> "等待确认创建插件和高风险代码"
                isNewPlugin -> "等待确认创建插件"
                hasDeletedPaths && risky -> "等待确认删除路径和高风险代码"
                hasDeletedPaths -> "等待确认删除插件路径"
                risky -> "等待确认高风险修改"
                else -> "等待确认插件修改"
            }
        )
    }

    fun writeAgentDraft(messageIndex: Int, draft: ScriptPluginAgentDraft, isNewPlugin: Boolean) {
        if (applyingFileOperation) return
        applyingFileOperation = true
        recordFileOperation(messageIndex, if (isNewPlugin) "正在创建插件" else "正在写入插件修改")
        Thread({
            val result = ScriptPluginAgentWriter.save(context, draft, overwrite = !isNewPlugin)
            Handler(Looper.getMainLooper()).post {
                applyingFileOperation = false
                result.onSuccess {
                    resumeState = null
                    sessionDraft = draft
                    targetPluginId = draft.pluginId
                    val current = sessionMessages.getOrNull(messageIndex)
                    if (current != null) {
                        sessionMessages = sessionMessages.toMutableList().also { messages ->
                            messages[messageIndex] = current.copy(
                                progress = mergeScriptPluginAgentProgress(
                                    current.progress,
                                    if (isNewPlugin) "已创建插件: ${draft.pluginId}" else "已更新插件: ${draft.pluginId}"
                                ),
                                status = "complete",
                                draftSnapshot = draft,
                                clearsDraft = false
                            )
                        }
                    }
                    saveCurrentSession()
                    Toast.makeText(
                        context,
                        if (isNewPlugin) "插件已创建，当前为禁用状态" else "插件已更新，当前为禁用状态",
                        Toast.LENGTH_LONG
                    ).show()
                }.onFailure { error ->
                    recordFileOperation(messageIndex, "写入失败: ${error.message ?: "未知错误"}")
                    Toast.makeText(context, error.message ?: "写入插件失败", Toast.LENGTH_LONG).show()
                }
            }
        }, "Hchat-Script-Agent-Write").start()
    }

    fun prepareAgentDraft(
        messageIndex: Int,
        draft: ScriptPluginAgentDraft,
        modifyingExisting: Boolean,
        requireConfirmation: Boolean
    ) {
        val validation = ScriptPluginAgentValidator.validate(draft)
        if (!validation.canSave) {
            val errors = validation.errors.joinToString("；") { it.message }
            recordFileOperation(messageIndex, "静态检查未通过，未写入: $errors")
            Toast.makeText(context, errors, Toast.LENGTH_LONG).show()
            return
        }
        val isNewPlugin = !File(ScriptPluginRuntime.scriptDir(context), draft.pluginId).exists()
        val confirmsCreation = isNewPlugin || !modifyingExisting
        val risky = validation.warnings.any { it.risky }
        if (!requireConfirmation) {
            writeAgentDraft(messageIndex, draft, isNewPlugin)
            return
        }
        pendingWrite = ScriptPluginAgentPendingWrite(
            messageIndex = messageIndex,
            draft = draft,
            diff = sessionMessages.getOrNull(messageIndex)?.diff.orEmpty(),
            isNewPlugin = isNewPlugin,
            confirmsCreation = confirmsCreation,
            risky = risky
        )
        showPendingWriteDialog = true
        recordFileOperation(
            messageIndex,
            when {
                isNewPlugin && risky -> "等待确认创建插件和高风险代码"
                confirmsCreation && risky -> "等待确认替换同名插件和高风险代码"
                isNewPlugin -> "等待确认创建插件"
                confirmsCreation -> "等待确认替换同名插件"
                risky -> "等待确认高风险代码"
                else -> "等待确认插件修改"
            }
        )
    }

    fun deleteAgentPlugin(messageIndex: Int, pluginId: String) {
        if (applyingFileOperation) return
        applyingFileOperation = true
        recordFileOperation(messageIndex, "正在删除插件: $pluginId")
        Thread({
            val result = ScriptPluginAgentWriter.delete(context, pluginId)
            Handler(Looper.getMainLooper()).post {
                applyingFileOperation = false
                result.onSuccess {
                    resumeState = null
                    val clearsCurrentDraft = sessionDraft?.pluginId.equals(pluginId, ignoreCase = true) ||
                        targetPluginId.equals(pluginId, ignoreCase = true)
                    if (clearsCurrentDraft) {
                        sessionDraft = null
                        targetPluginId = ""
                    }
                    val current = sessionMessages.getOrNull(messageIndex)
                    if (current != null) {
                        sessionMessages = sessionMessages.toMutableList().also { messages ->
                            messages[messageIndex] = current.copy(
                                progress = mergeScriptPluginAgentProgress(current.progress, "已删除插件: $pluginId"),
                                status = "complete",
                                draftSnapshot = sessionDraft,
                                clearsDraft = clearsCurrentDraft
                            )
                        }
                    }
                    saveCurrentSession()
                    Toast.makeText(context, "插件已删除", Toast.LENGTH_LONG).show()
                }.onFailure { error ->
                    recordFileOperation(messageIndex, "删除失败: ${error.message ?: "未知错误"}")
                    Toast.makeText(context, error.message ?: "删除插件失败", Toast.LENGTH_LONG).show()
                }
            }
        }, "Hchat-Script-Agent-Delete").start()
    }

    fun preparePluginDelete(messageIndex: Int, requestedId: String, requireConfirmation: Boolean) {
        val plugin = ScriptPluginRuntime.listPlugins(context).firstOrNull {
            it.id.equals(requestedId.trim(), ignoreCase = true)
        }
        if (plugin == null) {
            recordFileOperation(messageIndex, "未找到插件目录，未执行删除")
            Toast.makeText(context, "未找到要删除的插件", Toast.LENGTH_LONG).show()
            return
        }
        if (!requireConfirmation) {
            deleteAgentPlugin(messageIndex, plugin.id)
            return
        }
        pendingDelete = ScriptPluginAgentPendingDelete(
            messageIndex = messageIndex,
            pluginId = plugin.id,
            pluginName = plugin.displayName ?: plugin.name
        )
        showPendingDeleteDialog = true
        recordFileOperation(messageIndex, "等待确认删除插件: ${plugin.id}")
    }

    fun discardResumeWorkspace(state: ScriptPluginAgentResumeState? = resumeState) {
        val checkpoint = state?.workspaceCheckpoint ?: return
        Thread({
            runCatching { ScriptPluginAgentWorkspaceTools.discard(context, checkpoint) }
                .onFailure { error ->
                    h.Hchat.utils.HLog.e("[Hchat:ScriptAgent] 清理恢复工作区失败", error)
                }
        }, "Hchat-Script-Agent-Workspace-Discard").start()
    }

    fun sendMessage(
        content: String = messageInput,
        attachments: List<ScriptPluginAgentAttachment> = pendingAttachments,
        quotedMessage: ScriptPluginAgentQuotedMessage? = pendingQuotedMessage,
        consumeComposer: Boolean = true,
        resumeFrom: ScriptPluginAgentResumeState? = null
    ): Boolean {
        if (showPendingConfirmation()) return false
        if (generating || contextCompacting || applyingFileOperation) return false
        val message = content.trim()
        if (message.isBlank() && attachments.isEmpty()) {
            Toast.makeText(context, "请输入消息", Toast.LENGTH_SHORT).show()
            return false
        }
        val config = currentConfig()
        if (config.apiBaseUrl.isBlank() || config.model.isBlank()) {
            showConfig = true
            Toast.makeText(context, "请先完成 Agent 配置", Toast.LENGTH_SHORT).show()
            return false
        }
        endpointError(config).takeIf { it.isNotBlank() }?.let { error ->
            showConfig = true
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            return false
        }
        if (config.mcpServers.any { it.enabled && it.endpoint.isBlank() }) {
            showConfig = true
            Toast.makeText(context, "请填写已启用 MCP 的 Endpoint", Toast.LENGTH_SHORT).show()
            return false
        }
        ScriptPluginAgentSettings.save(context, config)
        val startedAt = System.currentTimeMillis()
        val resumeSource = resumeFrom?.let { checkpoint ->
            sessionMessages.lastOrNull { it.role == "user" && it.id == checkpoint.sourceUserMessageId }
                ?: sessionMessages.lastOrNull { it.role == "user" && it.turnId == checkpoint.turnId }
        }
        if (resumeFrom != null && resumeSource == null) {
            Toast.makeText(context, "原任务记录已不存在，无法继续", Toast.LENGTH_LONG).show()
            return false
        }
        val runId = resumeFrom?.turnId?.takeIf { it.isNotBlank() }
            ?: java.util.UUID.randomUUID().toString()
        val userMessageId = resumeSource?.id ?: java.util.UUID.randomUUID().toString()
        val nextMessages = if (resumeSource != null) {
            sessionMessages
        } else {
            sessionMessages + ScriptPluginAgentChatMessage(
                role = "user",
                content = message.ifBlank { "请分析附件内容" },
                id = userMessageId,
                turnId = runId,
                phase = "user",
                attachments = attachments,
                quotedMessage = quotedMessage,
                createdAt = startedAt,
                completedAt = startedAt
            )
        }
        if (sessionMessages.isEmpty()) sessionTitle = ScriptPluginAgentSessionStore.titleFrom(nextMessages)
        val assistantIndex = nextMessages.size
        sessionMessages = nextMessages + ScriptPluginAgentChatMessage(
            role = "assistant",
            content = "",
            turnId = runId,
            parentMessageId = userMessageId,
            phase = "assistant",
            progress = "正在连接模型",
            status = "streaming",
            createdAt = startedAt
        )
        if (resumeFrom == null) discardResumeWorkspace()
        resumeState = resumeFrom?.copy(
            turnId = runId,
            sourceUserMessageId = userMessageId,
            autoOpen = true,
            updatedAt = startedAt
        ) ?: ScriptPluginAgentResumeState(
            turnId = runId,
            sourceUserMessageId = userMessageId,
            startedAt = startedAt,
            updatedAt = startedAt
        )
        if (consumeComposer) {
            messageInput = ""
            pendingAttachments = emptyList()
            pendingQuotedMessage = null
        }
        saveCurrentSession()
        val previousSummary = conversationSummary
        val previousProtocolTranscript = protocolTranscript
        val previousCompactedCount = compactedMessageCount.coerceIn(0, nextMessages.size)
        val workspaceBelongsToSession = workspaceSessionId == activeSessionId
        val draftSnapshot = sessionDraft.takeIf { workspaceBelongsToSession }
        val targetSnapshot = targetPluginId.takeIf { workspaceBelongsToSession }.orEmpty()
        val cancellation = ScriptPluginAgentCancellation()
        val liveAssistantIndex = AtomicInteger(assistantIndex)
        activeGenerationId = runId
        activeAssistantIndex = assistantIndex
        activeCancellation = cancellation
        generationStartedAt = startedAt
        generationHasVisibleReply = false
        generating = true
        Thread({
            var requestSummary = previousSummary
            var requestCompactedCount = previousCompactedCount
            var requestProtocolTranscript = previousProtocolTranscript
            var compactProgress = ""
            var compactApplied = false
            var compactBoundary = previousCompactedCount
            fun modelMessagesFrom(startIndex: Int): List<ScriptPluginAgentChatMessage> {
                val modeled = ScriptPluginAgentContext.modelMessagesForTurn(
                    nextMessages.drop(startIndex),
                    runId
                ).filterNot { message ->
                    resumeSource != null && message.role == "assistant" && message.turnId == runId &&
                        (message.status == "error" || message.status == "interrupted")
                }
                val source = resumeSource ?: return modeled
                return if (modeled.any { it.id == source.id }) modeled else listOf(source) + modeled
            }
            if (cancellation.isCancelled) return@Thread
            val requestMessages = modelMessagesFrom(requestCompactedCount)
            var requestNativeToolHistory = ScriptPluginAgentToolResultStore.rebuildNativeToolHistory(
                context,
                requestMessages
            )
            val request = ScriptPluginAgentRequest(
                existing = null,
                messages = requestMessages,
                currentDraft = draftSnapshot,
                targetPluginId = targetSnapshot,
                conversationSummary = requestSummary,
                compactedMessageCount = requestCompactedCount,
                nativeToolHistory = requestNativeToolHistory,
                protocolTranscript = requestProtocolTranscript,
                lockedTaskGoal = resumeFrom?.taskGoal.orEmpty(),
                agentWorkContext = resumeFrom?.workContext.orEmpty().let { previous ->
                    if (resumeFrom == null) previous else listOf(
                        previous,
                        if (resumeFrom.workspaceCheckpoint != null) {
                            "上一轮任务意外中断。客户端将校验并恢复已保存的插件暂存工作区；继续同一目标和执行位置，不得重新执行已成功的工具调用或写入。"
                        } else {
                            "上一轮任务意外中断。继续同一目标并复用已完成的工具结果，不得自动重放已成功的 MCP 或未知副作用操作。"
                        }
                    ).filter { it.isNotBlank() }.joinToString("\n").takeLast(16_000)
                },
                workspaceCheckpoint = resumeFrom?.workspaceCheckpoint,
                sessionId = activeSessionId,
                turnId = runId,
                allowedLocalPaths = ScriptPluginAgentLocalFiles.extractMentionedPaths(
                    nextMessages.asSequence()
                        .filter { it.role == "user" }
                        .joinToString("\n") { it.content }
                ).map { it.path }
            )
            val result = ScriptPluginAgentSessionStore.withMaterializedAttachments(
                context,
                request.messages.flatMap { it.attachments }
            ) {
                ScriptPluginAgentClient.generate(
                    context,
                    config,
                    request,
                    cancellation,
                    { confirmation ->
                        val approval = ScriptPluginAgentWorkspaceToolApproval(confirmation)
                        Handler(Looper.getMainLooper()).post {
                            if (activeGenerationId == runId) {
                                pendingToolApproval = approval
                                showToolApprovalDialog = true
                            } else {
                                approval.resolve(ScriptPluginAgentWorkspaceWriteDecision.CANCEL)
                            }
                        }
                        try {
                            approval.await(cancellation)
                        } finally {
                            Handler(Looper.getMainLooper()).post {
                                if (pendingToolApproval === approval) {
                                    pendingToolApproval = null
                                    showToolApprovalDialog = false
                                }
                            }
                        }
                    }
                ) { update ->
                    Handler(Looper.getMainLooper()).post {
                        if (activeGenerationId != runId) return@post
                        when (update.phase) {
                        "compaction_start" -> {
                            // All preceding tool/assistant events are already enqueued before this boundary.
                            compactBoundary = ScriptPluginAgentContext.compactionBoundary(sessionMessages)
                            contextCompacting = true
                            contextCompactionStartedAt = System.currentTimeMillis()
                        }

                        "compaction_end" -> {
                            contextCompacting = false
                            contextCompactionStartedAt = 0L
                            compactProgress = update.progress
                        }

                        "context_compacted" -> {
                            contextCompacting = false
                            contextCompactionStartedAt = 0L
                            compactApplied = true
                            compactProgress = update.progress
                            requestSummary = update.checkpointConversationSummary ?: requestSummary
                            requestProtocolTranscript = update.checkpointProtocolTranscript ?: requestProtocolTranscript
                            requestNativeToolHistory = update.checkpointNativeToolHistory ?: requestNativeToolHistory
                            requestCompactedCount = compactBoundary.coerceIn(0, sessionMessages.size)
                            conversationSummary = requestSummary
                            protocolTranscript = requestProtocolTranscript
                            nativeToolHistory = requestNativeToolHistory
                            compactedMessageCount = requestCompactedCount
                            checkpointSaver.schedule(force = true)
                        }

                        "checkpoint" -> {
                            update.resumeState?.let { resumeState = it }
                            update.checkpointNativeToolHistory?.let {
                                nativeToolHistory = it
                                requestNativeToolHistory = it
                            }
                            update.checkpointProtocolTranscript?.let {
                                protocolTranscript = it
                                requestProtocolTranscript = it
                            }
                            update.checkpointConversationSummary?.let {
                                conversationSummary = it
                                requestSummary = it
                            }
                            update.checkpointCompactedMessageCount?.let {
                                compactedMessageCount = it.coerceIn(0, sessionMessages.size)
                                requestCompactedCount = compactedMessageCount
                            }
                            checkpointSaver.schedule(force = true)
                        }

                        "protocol_checkpoint" -> {
                            update.checkpointProtocolTranscript?.let {
                                protocolTranscript = it
                                requestProtocolTranscript = it
                            }
                            checkpointSaver.schedule(force = true)
                        }

                        "assistant_start" -> {
                            val streamId = update.streamId
                            var index = if (streamId.isNotBlank()) {
                                sessionMessages.indexOfLast { message ->
                                    message.role == "assistant" && message.streamId == streamId
                                }
                            } else {
                                liveAssistantIndex.get()
                            }
                            if (index !in sessionMessages.indices) {
                                val placeholderIndex = liveAssistantIndex.get()
                                val placeholder = sessionMessages.getOrNull(placeholderIndex)
                                val canAdoptPlaceholder = placeholderIndex == sessionMessages.lastIndex &&
                                    placeholder?.role == "assistant" &&
                                    placeholder.status == "streaming" &&
                                    placeholder.streamId.isBlank() &&
                                    placeholder.content.isBlank() &&
                                    placeholder.reasoning.isBlank() &&
                                    placeholder.toolEvents.isEmpty()
                                if (canAdoptPlaceholder) {
                                    index = placeholderIndex
                                } else {
                                    val previousIndex = liveAssistantIndex.get()
                                    val previous = sessionMessages.getOrNull(previousIndex)
                                    if (previous != null && previous.role == "assistant" && previous.status == "streaming") {
                                        sessionMessages = sessionMessages.toMutableList().also { messages ->
                                            messages[previousIndex] = previous.copy(
                                                status = "complete",
                                                completedAt = System.currentTimeMillis()
                                            )
                                        }
                                    }
                                }
                            }
                            if (index !in sessionMessages.indices) {
                                sessionMessages = sessionMessages.toMutableList().also { messages ->
                                    messages += ScriptPluginAgentChatMessage(
                                        role = "assistant",
                                        content = "",
                                        turnId = update.turnId,
                                        parentMessageId = update.parentMessageId,
                                        phase = "assistant",
                                        status = "streaming",
                                        streamId = streamId
                                    )
                                }
                                index = sessionMessages.lastIndex
                            }
                            liveAssistantIndex.set(index)
                            activeAssistantIndex = index
                            val assistant = sessionMessages[index]
                            val next = assistant.copy(
                                id = streamId.ifBlank { assistant.id },
                                turnId = update.turnId.ifBlank { assistant.turnId },
                                parentMessageId = update.parentMessageId.ifBlank { assistant.parentMessageId },
                                phase = "assistant",
                                status = "streaming",
                                streamId = streamId.ifBlank { assistant.streamId }
                            )
                            if (next != assistant) sessionMessages = sessionMessages.toMutableList().also {
                                it[index] = next
                            }
                            checkpointSaver.schedule()
                        }

                        "assistant_reset" -> {
                            val index = if (update.streamId.isNotBlank()) {
                                sessionMessages.indexOfLast { message ->
                                    message.role == "assistant" && message.streamId == update.streamId
                                }
                            } else {
                                liveAssistantIndex.get()
                            }
                            val current = sessionMessages.getOrNull(index)
                            if (current != null && current.role == "assistant") {
                                sessionMessages = sessionMessages.toMutableList().also { messages ->
                                    messages.removeAt(index)
                                }
                                liveAssistantIndex.set(-1)
                                activeAssistantIndex = -1
                                checkpointSaver.schedule()
                            }
                        }

                        "working" -> {
                            // Working is a transport state. Keep it out of the assistant message body.
                        }

                        "tool_start", "tool_update" -> {
                            val event = update.toolEvents?.firstOrNull() ?: return@post
                            if (update.phase == "tool_start") {
                                val index = if (update.streamId.isNotBlank()) {
                                    sessionMessages.indexOfLast { message ->
                                        message.role == "assistant" && message.streamId == update.streamId
                                    }
                                } else {
                                    liveAssistantIndex.get()
                                }
                                val current = sessionMessages.getOrNull(index)
                                if (current != null && current.role == "assistant") {
                                    sessionMessages = sessionMessages.toMutableList().also { messages ->
                                        if (index == messages.lastIndex &&
                                            (current.content.isBlank() ||
                                                isScriptPluginAgentControlPreview(current.content)) &&
                                            current.diff.isBlank()
                                        ) {
                                            messages[index] = current.copy(
                                                content = "",
                                                status = "complete",
                                                phase = "assistant_tool_call",
                                                completedAt = System.currentTimeMillis()
                                            )
                                        } else {
                                            messages[index] = current.copy(
                                                status = "complete",
                                                completedAt = System.currentTimeMillis()
                                            )
                                        }
                                    }
                                }
                                liveAssistantIndex.set(-1)
                            }
                            val existingToolIndex = sessionMessages.indexOfLast { message ->
                                message.role == "tool" &&
                                    (message.toolEvents.any { it.id == update.toolEventId } ||
                                        (event.turnId.isNotBlank() &&
                                            message.turnId == event.turnId &&
                                            message.parentMessageId == event.parentAssistantMessageId))
                            }
                            val toolIndex = if (existingToolIndex >= 0) {
                                sessionMessages = sessionMessages.toMutableList().also { messages ->
                                    val current = messages[existingToolIndex]
                                    val nextEvents = current.toolEvents.toMutableList().apply {
                                        val eventIndex = indexOfFirst { it.id == event.id }
                                        if (eventIndex >= 0) set(eventIndex, event) else add(event)
                                    }
                                    val hasPending = nextEvents.any {
                                        it.status == "running" || it.status == "queued"
                                    }
                                    messages[existingToolIndex] = current.copy(
                                        turnId = event.turnId.ifBlank { current.turnId },
                                        parentMessageId = event.parentAssistantMessageId,
                                        phase = "tool",
                                        toolEvents = nextEvents,
                                        status = if (hasPending) {
                                            "streaming"
                                        } else {
                                            "complete"
                                        },
                                        completedAt = if (hasPending) 0L else nextEvents.maxOfOrNull {
                                            it.finishedAt
                                        } ?: event.finishedAt
                                    )
                                }
                                existingToolIndex
                            } else {
                                sessionMessages = sessionMessages + ScriptPluginAgentChatMessage(
                                    role = "tool",
                                    content = "",
                                    id = ScriptPluginAgentEventIds.toolGroup(
                                        event.turnId,
                                        event.parentAssistantMessageId
                                    ),
                                    turnId = event.turnId,
                                    parentMessageId = event.parentAssistantMessageId,
                                    phase = "tool",
                                    toolEvents = listOf(event),
                                    status = if (event.status == "running" || event.status == "queued") {
                                        "streaming"
                                    } else {
                                        "complete"
                                    },
                                    completedAt = event.finishedAt,
                                    streamId = update.streamId
                                )
                                sessionMessages.lastIndex
                            }
                            activeAssistantIndex = toolIndex
                            checkpointSaver.schedule(
                                force = event.status != "running" && event.status != "queued"
                            )
                        }

                        else -> {
                            if (update.reply.isNotBlank()) generationHasVisibleReply = true
                            val index = if (update.streamId.isNotBlank()) {
                                sessionMessages.indexOfLast { message ->
                                    message.role == "assistant" && message.streamId == update.streamId
                                }
                            } else {
                                liveAssistantIndex.get()
                            }
                            if (index !in sessionMessages.indices) return@post
                            val current = sessionMessages[index]
                            val next = current.copy(
                                content = if (update.replyRevision) {
                                    update.reply
                                } else {
                                    mergeScriptPluginAgentReply(current.content, update.reply)
                                },
                                reasoning = if (update.reasoningRevision) {
                                    update.reasoning
                                } else {
                                    ScriptPluginAgentTextMerge.mergeReply(current.reasoning, update.reasoning)
                                },
                                status = "streaming"
                            )
                            if (next != current) sessionMessages = sessionMessages.toMutableList().also {
                                it[index] = next
                            }
                            checkpointSaver.schedule()
                        }
                        }
                    }
                }
            }
            Handler(Looper.getMainLooper()).post {
                if (activeGenerationId != runId) return@post
                generating = false
                contextCompacting = false
                contextCompactionStartedAt = 0L
                generationStartedAt = 0L
                generationHasVisibleReply = false
                activeCancellation = null
                activeGenerationId = ""
                activeAssistantIndex = -1
                pendingToolApproval = null
                showToolApprovalDialog = false
                conversationSummary = requestSummary
                compactedMessageCount = requestCompactedCount
                nativeToolHistory = requestNativeToolHistory
                protocolTranscript = requestProtocolTranscript
                result.onSuccess { turn ->
                    if (turn.protocolTranscript.isNotBlank()) {
                        protocolTranscript = turn.protocolTranscript
                    }
                    if (turn.nativeToolHistory.isNotBlank()) {
                        nativeToolHistory = turn.nativeToolHistory
                    } else {
                        nativeToolHistory = ScriptPluginAgentToolResultStore.mergeNativeToolHistory(
                            context,
                            requestNativeToolHistory,
                            ScriptPluginAgentContext.modelMessagesForTurn(
                                sessionMessages.drop(requestCompactedCount),
                                runId
                            )
                        )
                    }
                    var finalAssistantIndex = liveAssistantIndex.get()
                    val finalMessage = sessionMessages.getOrNull(finalAssistantIndex)
                    if (finalAssistantIndex !in sessionMessages.indices ||
                        finalMessage?.role != "assistant" ||
                        finalAssistantIndex != sessionMessages.lastIndex
                    ) {
                        sessionMessages = sessionMessages + ScriptPluginAgentChatMessage(
                            role = "assistant",
                            content = "",
                            turnId = runId,
                            parentMessageId = userMessageId,
                            phase = "assistant",
                            status = "streaming"
                        )
                        finalAssistantIndex = sessionMessages.lastIndex
                        liveAssistantIndex.set(finalAssistantIndex)
                    }
                    val previousDraft = sessionDraft
                    val nextDraft = turn.draft?.let { value ->
                        ScriptPluginAgentValidator.normalize(
                            if (turn.targetPluginId.isNotBlank()) value.copy(pluginId = turn.targetPluginId) else value
                        )
                    }
                    val actualDiff = nextDraft?.let { ScriptPluginAgentDiff.between(previousDraft, it) }.orEmpty()
                    val currentMessage = sessionMessages.getOrNull(finalAssistantIndex)
                    val displayedToolEventIds = sessionMessages.asSequence()
                        .flatMap { message -> message.toolEvents.asSequence() }
                        .map { event -> event.id }
                        .toSet()
                    val missingToolEvents = turn.toolEvents.filterNot { event ->
                        event.id in displayedToolEventIds
                    }
                    val completedMessage = (currentMessage ?: ScriptPluginAgentChatMessage("assistant", ""))
                        .copy(
                        content = mergeScriptPluginAgentReply(currentMessage?.content.orEmpty(), turn.reply),
                        progress = listOf(
                            compactProgress.takeIf { compactApplied }.orEmpty(),
                            turn.progress
                        ).fold(currentMessage?.progress.orEmpty()) {
                            current, next -> mergeScriptPluginAgentProgress(current, next)
                        },
                        diff = turn.diff.ifBlank { actualDiff },
                        toolEvents = (currentMessage?.toolEvents.orEmpty() + missingToolEvents)
                            .distinctBy { event -> event.id },
                        status = "complete",
                        completedAt = System.currentTimeMillis(),
                        phase = "assistant",
                        draftSnapshot = null,
                        clearsDraft = false
                    )
                    sessionMessages = sessionMessages.toMutableList().also {
                        if (finalAssistantIndex in it.indices) it[finalAssistantIndex] = completedMessage
                        else it += completedMessage
                    }
                    val awaitsFileOperation =
                        (turn.status.equals("workspace_ready", ignoreCase = true) && turn.workspaceChange != null) ||
                            turn.status.equals("delete", ignoreCase = true) ||
                            (turn.status.equals("ready", ignoreCase = true) && nextDraft != null)
                    if (!awaitsFileOperation) resumeState = null
                    if (turn.title.isNotBlank() && sessionMessages.count { it.role == "user" } <= 1) {
                        sessionTitle = turn.title.take(32)
                    }
                    when {
                        turn.status.equals("workspace_ready", ignoreCase = true) && turn.workspaceChange != null -> {
                            prepareWorkspaceChange(
                                finalAssistantIndex,
                                turn.workspaceChange,
                                workspaceWriteApprovalMode == ScriptPluginAgentSettings.WRITE_APPROVAL_ASK
                            )
                        }
                        turn.status.equals("delete", ignoreCase = true) -> {
                            preparePluginDelete(
                                finalAssistantIndex,
                                turn.targetPluginId,
                                workspaceWriteApprovalMode == ScriptPluginAgentSettings.WRITE_APPROVAL_ASK
                            )
                        }
                        turn.status.equals("ready", ignoreCase = true) && nextDraft != null -> {
                            val modifyingExisting = turn.targetPluginId.isNotBlank() ||
                                sessionDraft?.pluginId.equals(nextDraft.pluginId, ignoreCase = true)
                            prepareAgentDraft(
                                finalAssistantIndex,
                                nextDraft,
                                modifyingExisting,
                                workspaceWriteApprovalMode == ScriptPluginAgentSettings.WRITE_APPROVAL_ASK
                            )
                        }
                        else -> {
                            if (turn.targetPluginId.isNotBlank()) targetPluginId = turn.targetPluginId
                            saveCurrentSession()
                        }
                    }
                }.onFailure { error ->
                    var failureIndex = liveAssistantIndex.get()
                    val failureMessage = sessionMessages.getOrNull(failureIndex)
                    if (failureIndex !in sessionMessages.indices ||
                        failureMessage?.role != "assistant" ||
                        failureIndex != sessionMessages.lastIndex
                    ) {
                        sessionMessages = sessionMessages + ScriptPluginAgentChatMessage(
                            role = "assistant",
                            content = "",
                            turnId = runId,
                            parentMessageId = userMessageId,
                            phase = "assistant",
                            status = "error"
                        )
                        failureIndex = sessionMessages.lastIndex
                    }
                    val currentMessage = sessionMessages.getOrNull(failureIndex)
                    if (currentMessage != null) {
                        sessionMessages = sessionMessages.toMutableList().also { messages ->
                            messages[failureIndex] = currentMessage.copy(
                                status = if (cancellation.isCancellation(error)) "interrupted" else "error",
                                completedAt = System.currentTimeMillis(),
                                progress = mergeScriptPluginAgentProgress(
                                    currentMessage.progress,
                                    if (cancellation.isCancellation(error)) "已中断" else "请求失败"
                                )
                            )
                        }
                    }
                    nativeToolHistory = ScriptPluginAgentToolResultStore.mergeNativeToolHistory(
                        context,
                        requestNativeToolHistory,
                        ScriptPluginAgentContext.modelMessagesForTurn(
                            sessionMessages.drop(requestCompactedCount),
                            runId
                        )
                    )
                    saveCurrentSession()
                    if (!cancellation.isCancellation(error)) {
                        Toast.makeText(context, error.message ?: "请求失败", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }, "Hchat-Script-Agent-Chat").start()
        return true
    }

    fun queueMessage() {
        if (!generating || applyingFileOperation) return
        val message = messageInput.trim()
        if (message.isBlank() && pendingAttachments.isEmpty()) return
        pendingMessages = pendingMessages + ScriptPluginAgentPendingMessage(
            id = nextPendingMessageId++,
            content = message,
            attachments = pendingAttachments,
            quotedMessage = pendingQuotedMessage
        )
        pendingMessagesExpanded = true
        messageInput = ""
        pendingAttachments = emptyList()
        pendingQuotedMessage = null
        Toast.makeText(context, "已加入待发送队列", Toast.LENGTH_SHORT).show()
    }

    fun editPendingMessage(id: Long) {
        val pending = pendingMessages.firstOrNull { it.id == id } ?: return
        pendingMessages = pendingMessages.filterNot { it.id == id }
        messageInput = pending.content
        pendingAttachments = pending.attachments
        pendingQuotedMessage = pending.quotedMessage
    }

    fun sendPendingMessageNow(id: Long) {
        val pending = pendingMessages.firstOrNull { it.id == id } ?: return
        pendingMessages = listOf(pending) + pendingMessages.filterNot { it.id == id }
        if (generating) cancelGeneration()
    }

    fun compactContext() {
        if (generating || contextCompacting || applyingFileOperation) return
        if (showPendingConfirmation()) return
        val start = compactedMessageCount.coerceIn(0, sessionMessages.size)
        val messagesToCompact = sessionMessages.drop(start)
        if (messagesToCompact.isEmpty()) {
            Toast.makeText(context, "没有需要压缩的新上下文", Toast.LENGTH_SHORT).show()
            return
        }
        val config = currentConfig()
        val summarySnapshot = conversationSummary
        val protocolTranscriptSnapshot = protocolTranscript
        val draftSnapshot = sessionDraft
        val targetSnapshot = targetPluginId
        val compactedSize = sessionMessages.size
        val runId = "compact:${java.util.UUID.randomUUID()}"
        val cancellation = ScriptPluginAgentCancellation()
        contextCompacting = true
        contextCompactionStartedAt = System.currentTimeMillis()
        activeGenerationId = runId
        activeAssistantIndex = -1
        activeCancellation = cancellation
        Toast.makeText(context, "正在压缩上下文", Toast.LENGTH_SHORT).show()
        Thread({
            val nativeHistorySnapshot = ScriptPluginAgentToolResultStore.rebuildNativeToolHistory(
                context,
                messagesToCompact
            )
            val estimatedTokensBefore = ScriptPluginAgentContext.estimateTokens(
                summarySnapshot,
                messagesToCompact,
                draftSnapshot,
                nativeHistorySnapshot,
                protocolTranscriptSnapshot
            )
            val result = ScriptPluginAgentClient.compact(
                config = config,
                previousSummary = summarySnapshot,
                messages = messagesToCompact,
                currentDraft = draftSnapshot,
                targetPluginId = targetSnapshot,
                cancellation = cancellation,
                protocolTranscript = protocolTranscriptSnapshot
            )
            Handler(Looper.getMainLooper()).post {
                if (activeGenerationId != runId) return@post
                contextCompacting = false
                contextCompactionStartedAt = 0L
                activeGenerationId = ""
                activeAssistantIndex = -1
                activeCancellation = null
                result.onSuccess { summary ->
                    val estimatedTokensAfter = ScriptPluginAgentContext.estimateTokens(
                        summary,
                        emptyList(),
                        draftSnapshot,
                        ""
                    )
                    if (estimatedTokensAfter < estimatedTokensBefore) {
                        conversationSummary = summary
                        nativeToolHistory = ""
                        protocolTranscript = ""
                        compactedMessageCount = compactedSize.coerceAtMost(sessionMessages.size)
                        saveCurrentSession()
                        Toast.makeText(
                            context,
                            "上下文已压缩：估算 $estimatedTokensBefore → $estimatedTokensAfter Token",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            "压缩结果未减少上下文，已保留原上下文",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }.onFailure {
                    Toast.makeText(context, it.message ?: "上下文压缩失败", Toast.LENGTH_LONG).show()
                }
            }
        }, "Hchat-Script-Agent-Compact").start()
    }

    fun saveConfig() {
        val threshold = compactTokenThreshold.toIntOrNull()
        if (threshold == null || threshold !in 2_000..1_000_000) {
            Toast.makeText(context, "自动压缩阈值需为 2000 到 1000000", Toast.LENGTH_SHORT).show()
            return
        }
        val config = currentConfig()
        if (config.apiBaseUrl.isBlank() || config.model.isBlank()) {
            Toast.makeText(context, "API 地址和模型不能为空", Toast.LENGTH_SHORT).show()
            return
        }
        endpointError(config).takeIf { it.isNotBlank() }?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            return
        }
        if (config.mcpServers.any { it.enabled && it.endpoint.isBlank() }) {
            Toast.makeText(context, "已启用 MCP 的 Endpoint 不能为空", Toast.LENGTH_SHORT).show()
            return
        }
        ScriptPluginAgentSettings.save(context, config)
        apiBase = config.apiBaseUrl
        model = config.model
        reasoningEffort = config.reasoningEffort
        val activeProfile = ScriptPluginAgentSettings.loadActiveProfile(context)
        activeProfileId = activeProfile.id
        activeProfileName = activeProfile.name
        profileVersion++
        showConfig = false
        Toast.makeText(context, "Agent 配置已保存", Toast.LENGTH_SHORT).show()
    }

    fun refreshCodexProfiles() {
        codexProfiles = CodexSettings.profiles(context)
        codexProfile = CodexSettings.active(context)
    }

    fun saveCodexConfig() {
        if (codexProfile.baseUrl.isBlank() || codexProfile.model.isBlank()) {
            Toast.makeText(context, "codex 的 API 地址和模型不能为空", Toast.LENGTH_SHORT).show()
            return
        }
        CodexSettings.save(context, codexProfile)
        refreshCodexProfiles()
        Toast.makeText(context, "codex 配置已保存", Toast.LENGTH_SHORT).show()
    }

    fun selectCodexProfile(id: String) {
        CodexSettings.setActive(context, id)
        refreshCodexProfiles()
    }

    fun createCodexProfile(name: String) {
        CodexSettings.createProfile(context, name)
        refreshCodexProfiles()
    }

    fun renameCodexProfile(name: String) {
        CodexSettings.renameProfile(context, codexProfile.id, name)
        refreshCodexProfiles()
    }

    fun deleteCodexProfile() {
        CodexSettings.deleteProfile(context, codexProfile.id)
        refreshCodexProfiles()
    }

    fun testCodexConnection() {
        if (testingCodexConnection) return
        val profile = codexProfile
        if (profile.baseUrl.isBlank() || profile.apiKey.isBlank()) {
            Toast.makeText(context, "请先填写 API 地址和模型密钥", Toast.LENGTH_SHORT).show()
            return
        }
        testingCodexConnection = true
        Toast.makeText(context, "正在测试连接", Toast.LENGTH_SHORT).show()
        Thread({
            val result = CodexSettings.testConnection(profile.baseUrl, profile.apiKey)
            Handler(Looper.getMainLooper()).post {
                testingCodexConnection = false
                result.onSuccess {
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                }.onFailure {
                    Toast.makeText(context, CodexSettings.friendlyError(it), Toast.LENGTH_LONG).show()
                }
            }
        }, "Hchat-Codex-Test").start()
    }

    fun selectProfile(profile: ScriptPluginAgentProfile) {
        if (profile.id == activeProfileId) return
        val appliesToNextRequest = generating
        ScriptPluginAgentSettings.save(context, currentConfig())
        ScriptPluginAgentSettings.setActiveProfile(context, profile.id)
        applyProfile(ScriptPluginAgentSettings.loadActiveProfile(context))
        profileVersion++
        Toast.makeText(
            context,
            if (appliesToNextRequest) "已切换配置，将用于下一次请求" else "已切换配置",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun setWebSearchEnabled(enabled: Boolean) {
        val nextConfig = currentConfig().copy(webSearchEnabled = enabled)
        webSearchEnabled = enabled
        ScriptPluginAgentSettings.save(context, nextConfig)
        profileVersion++
    }

    fun setMcpServerEnabled(id: String, enabled: Boolean) {
        val nextServers = mcpServers.map { server ->
            if (server.id == id) server.copy(enabled = enabled) else server
        }
        mcpServers = nextServers
        ScriptPluginAgentSettings.save(context, currentConfig().copy(mcpServers = nextServers))
        profileVersion++
    }

    fun setWorkspaceWriteApprovalMode(mode: String) {
        workspaceWriteApprovalMode = mode
        ScriptPluginAgentSettings.save(
            context,
            currentConfig().copy(workspaceWriteApprovalMode = mode)
        )
        profileVersion++
    }

    fun setPromptCacheMode(mode: String) {
        promptCacheMode = mode
        ScriptPluginAgentSettings.save(
            context,
            currentConfig().copy(promptCacheMode = mode)
        )
        profileVersion++
    }

    fun setReasoningEffort(effort: String) {
        val selected = ScriptPluginAgentReasoning.effectiveEffort(endpointMode, model, effort)
        ScriptPluginAgentSettings.save(context, currentConfig().copy(reasoningEffort = selected))
        reasoningEffort = selected
        profileVersion++
        Toast.makeText(context, "推理强度已保存，将用于下一次请求", Toast.LENGTH_SHORT).show()
    }

    fun createProfile(name: String) {
        runCatching {
            ScriptPluginAgentSettings.save(context, currentConfig())
            val created = ScriptPluginAgentSettings.createProfile(context, name)
            applyProfile(created)
            profileVersion++
        }.onFailure {
            Toast.makeText(context, it.message ?: "新建配置失败", Toast.LENGTH_SHORT).show()
        }
    }

    fun renameProfile(name: String) {
        runCatching {
            val renamed = ScriptPluginAgentSettings.renameProfile(context, activeProfileId, name)
            activeProfileName = renamed.name
            profileVersion++
        }.onFailure {
            Toast.makeText(context, it.message ?: "重命名失败", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteActiveProfile() {
        runCatching {
            ScriptPluginAgentSettings.save(context, currentConfig())
            val next = ScriptPluginAgentSettings.deleteProfile(context, activeProfileId)
            applyProfile(next)
            profileVersion++
        }.onFailure {
            Toast.makeText(context, it.message ?: "删除配置失败", Toast.LENGTH_SHORT).show()
        }
    }

    fun testAgentConnection() {
        if (testingConnection) return
        val config = currentConfig()
        if (config.apiBaseUrl.isBlank() || config.model.isBlank()) {
            Toast.makeText(context, "请先填写 API 地址和模型", Toast.LENGTH_SHORT).show()
            return
        }
        endpointError(config).takeIf { it.isNotBlank() }?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            return
        }
        testingConnection = true
        Toast.makeText(context, "正在测试连接", Toast.LENGTH_SHORT).show()
        Thread({
            val result = ScriptPluginAgentClient.testConnection(config)
            Handler(Looper.getMainLooper()).post {
                testingConnection = false
                result.onSuccess {
                    Toast.makeText(context, "连接成功", Toast.LENGTH_SHORT).show()
                }.onFailure {
                    Toast.makeText(context, it.message ?: "连接失败", Toast.LENGTH_LONG).show()
                }
            }
        }, "Hchat-Script-Agent-Test").start()
    }

    fun draftForMessages(messages: List<ScriptPluginAgentChatMessage>): ScriptPluginAgentDraft? {
        return messages.asReversed()
            .firstOrNull { it.draftSnapshot != null || it.clearsDraft }
            ?.draftSnapshot
    }

    fun rebuildNativeHistory(messages: List<ScriptPluginAgentChatMessage>): String {
        return ScriptPluginAgentToolResultStore.rebuildNativeToolHistory(context, messages)
    }

    fun deleteToolResults(messages: List<ScriptPluginAgentChatMessage>) {
        messages.asSequence()
            .flatMap { it.toolEvents.asSequence() }
            .map { it.resultHandle }
            .filter { it.isNotBlank() }
            .distinct()
            .forEach { ScriptPluginAgentToolResultStore.deleteHandle(context, it) }
    }

    fun prepareMessageHistoryChange(
        retainedMessages: List<ScriptPluginAgentChatMessage>,
        removedMessages: List<ScriptPluginAgentChatMessage>,
        actionName: String,
        onReady: (String) -> Boolean
    ) {
        val mutationState = renderedSessionState
        val sourceMessages = mutationState.messages.value
        mutationState.applyingFileOperation.value = true
        Thread({
            val historyResult = runCatching { rebuildNativeHistory(retainedMessages) }
            Handler(Looper.getMainLooper()).post {
                if (mutationState.messages.value !== sourceMessages) {
                    mutationState.applyingFileOperation.value = false
                    Toast.makeText(context, "会话内容已变化，请重试", Toast.LENGTH_SHORT).show()
                    return@post
                }
                historyResult.fold(
                    onSuccess = { restoredHistory ->
                        mutationState.applyingFileOperation.value = false
                        val applied = runCatching { onReady(restoredHistory) }
                            .onFailure { error ->
                                h.Hchat.utils.HLog.e(
                                    "[Hchat:ScriptAgent] ${actionName}失败",
                                    error
                                )
                                Toast.makeText(context, "${actionName}失败，请重试", Toast.LENGTH_SHORT).show()
                            }
                            .getOrDefault(false)
                        if (applied) {
                            val snapshot = mutationState.snapshot()
                            val sessionId = mutationState.id
                            Thread({
                                val saveResult = runCatching {
                                    if (ScriptPluginAgentSessionStore.hasConversation(snapshot.messages)) {
                                        ScriptPluginAgentSessionStore.revive(sessionId)
                                        ScriptPluginAgentSessionStore.save(context, snapshot)
                                    } else {
                                        ScriptPluginAgentSessionStore.delete(context, sessionId)
                                    }
                                }
                                saveResult.onSuccess {
                                    runCatching {
                                        if (removedMessages.isNotEmpty()) deleteToolResults(removedMessages)
                                    }.onFailure { error ->
                                        h.Hchat.utils.HLog.e(
                                            "[Hchat:ScriptAgent] ${actionName}清理工具结果失败",
                                            error
                                        )
                                    }
                                }.onFailure { error ->
                                    h.Hchat.utils.HLog.e(
                                        "[Hchat:ScriptAgent] ${actionName}保存会话失败",
                                        error
                                    )
                                    Handler(Looper.getMainLooper()).post {
                                        Toast.makeText(
                                            context,
                                            "${actionName}已完成，但会话保存失败",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            }, "Hchat-Agent-History-Save").start()
                        }
                    },
                    onFailure = { error ->
                        mutationState.applyingFileOperation.value = false
                        h.Hchat.utils.HLog.e("[Hchat:ScriptAgent] ${actionName}重建历史失败", error)
                        Toast.makeText(context, "${actionName}失败，请重试", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }, "Hchat-Agent-History-Change").start()
    }

    fun removeMessageTree(index: Int): Pair<List<ScriptPluginAgentChatMessage>, List<ScriptPluginAgentChatMessage>> {
        val root = sessionMessages[index]
        val removedIds = linkedSetOf(root.id)
        var changed: Boolean
        do {
            changed = false
            sessionMessages.forEach { message ->
                if (message.id !in removedIds && message.parentMessageId in removedIds) {
                    removedIds += message.id
                    changed = true
                }
            }
        } while (changed)
        return sessionMessages.partition { it.id !in removedIds }
    }

    fun createMessageBranch(index: Int) {
        if (generating || contextCompacting || applyingFileOperation || hasPendingConfirmation() ||
            index !in sessionMessages.indices
        ) return
        val sourceMessages = sessionMessages.take(index + 1)
        val sourceSessionId = activeSessionId
        val sourceTitle = sessionTitle
        val sourceTargetPluginId = targetPluginId
        val branchId = java.util.UUID.randomUUID().toString().replace("-", "")
        applyingFileOperation = true
        Thread({
            val result = runCatching {
                val branchMessages = ScriptPluginAgentToolResultStore.copySessionResults(
                    context,
                    sourceSessionId,
                    branchId,
                    sourceMessages
                )
                val branchDraft = draftForMessages(branchMessages)
                val now = System.currentTimeMillis()
                ScriptPluginAgentSession(
                    id = branchId,
                    title = (sourceTitle.ifBlank { "新对话" } + " 分支").take(32),
                    createdAt = now,
                    updatedAt = now,
                    messages = branchMessages,
                    draft = branchDraft,
                    targetPluginId = branchDraft?.pluginId?.ifBlank { sourceTargetPluginId }
                        ?: sourceTargetPluginId,
                    conversationSummary = "",
                    nativeToolHistory = rebuildNativeHistory(branchMessages),
                    protocolTranscript = "",
                    compactedMessageCount = 0
                ).also { branch -> ScriptPluginAgentSessionStore.save(context, branch) }
            }
            Handler(Looper.getMainLooper()).post {
                applyingFileOperation = false
                result.onSuccess { branch ->
                    openSession(branch)
                    Toast.makeText(context, "已从此处创建分支", Toast.LENGTH_SHORT).show()
                    historyVersion++
                }.onFailure { error ->
                    h.Hchat.utils.HLog.e("[Hchat:ScriptAgent] 创建会话分支失败", error)
                    Toast.makeText(context, error.message ?: "创建分支失败", Toast.LENGTH_LONG).show()
                }
            }
        }, "Hchat-Agent-Create-Branch").start()
    }

    fun editAndResendMessage(index: Int) {
        if (generating || contextCompacting || applyingFileOperation || hasPendingConfirmation() ||
            index !in sessionMessages.indices
        ) return
        val selected = sessionMessages[index]
        if (selected.role != "user") return
        val sourceMessages = sessionMessages
        val retainedMessages = sourceMessages.take(index)
        val removedMessages = sourceMessages.drop(index)
        val restoredDraft = draftForMessages(retainedMessages)
        val previousResumeState = resumeState
        prepareMessageHistoryChange(
            retainedMessages = retainedMessages,
            removedMessages = removedMessages,
            actionName = "编辑重发"
        ) { restoredHistory ->
            sessionMessages = retainedMessages
            sessionDraft = restoredDraft
            conversationSummary = ""
            nativeToolHistory = restoredHistory
            protocolTranscript = ""
            discardResumeWorkspace(previousResumeState)
            resumeState = null
            compactedMessageCount = 0
            messageInput = selected.content
            pendingAttachments = selected.attachments
            pendingQuotedMessage = selected.quotedMessage
            if (retainedMessages.isEmpty()) historyVersion++
            Toast.makeText(context, "已覆盖后续记录并填入原消息", Toast.LENGTH_SHORT).show()
            true
        }
    }

    fun quoteAgentMessage(index: Int) {
        val selected = sessionMessages.getOrNull(index) ?: return
        val quotedContent = selected.content.trim()
        if (quotedContent.isBlank()) {
            Toast.makeText(context, "当前消息没有可引用内容", Toast.LENGTH_SHORT).show()
            return
        }
        pendingQuotedMessage = ScriptPluginAgentQuotedMessage(
            role = selected.role,
            content = quotedContent.take(24_000),
            createdAt = selected.createdAt
        )
    }

    fun regenerateAssistantMessage(index: Int) {
        if (generating || contextCompacting || applyingFileOperation || hasPendingConfirmation() ||
            index !in sessionMessages.indices
        ) return
        val selected = sessionMessages[index]
        val sourceIndex = (index - 1 downTo 0).firstOrNull { sessionMessages[it].role == "user" } ?: return
        val source = sessionMessages[sourceIndex]
        if (selected.role != "assistant") return
        val config = currentConfig()
        if (config.apiBaseUrl.isBlank() || config.model.isBlank()) {
            showConfig = true
            Toast.makeText(context, "请先完成 Agent 配置", Toast.LENGTH_SHORT).show()
            return
        }
        endpointError(config).takeIf { it.isNotBlank() }?.let { error ->
            showConfig = true
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            return
        }
        if (config.mcpServers.any { it.enabled && it.endpoint.isBlank() }) {
            showConfig = true
            Toast.makeText(context, "请填写已启用 MCP 的 Endpoint", Toast.LENGTH_SHORT).show()
            return
        }
        val sourceMessages = sessionMessages
        val retainedMessages = sourceMessages.take(sourceIndex)
        val removedMessages = sourceMessages.drop(sourceIndex)
        val restoredDraft = draftForMessages(retainedMessages)
        val previousResumeState = resumeState
        prepareMessageHistoryChange(
            retainedMessages = retainedMessages,
            removedMessages = removedMessages,
            actionName = "重新生成"
        ) { restoredHistory ->
            sessionMessages = retainedMessages
            sessionDraft = restoredDraft
            targetPluginId = restoredDraft?.pluginId.orEmpty()
            conversationSummary = ""
            nativeToolHistory = restoredHistory
            protocolTranscript = ""
            discardResumeWorkspace(previousResumeState)
            resumeState = null
            compactedMessageCount = 0
            val started = sendMessage(
                content = source.content,
                attachments = source.attachments,
                quotedMessage = source.quotedMessage,
                consumeComposer = false
            )
            if (started) Toast.makeText(context, "正在重新生成", Toast.LENGTH_SHORT).show()
            started
        }
    }

    fun continueInterruptedTask(index: Int) {
        if (generating || contextCompacting || applyingFileOperation || index !in sessionMessages.indices) return
        val selected = sessionMessages[index]
        val turnInterrupted = selected.status == "interrupted" || selected.status == "error" ||
            sessionMessages.any { message ->
                message.turnId == selected.turnId && (
                    message.status == "interrupted" || message.status == "error" ||
                        message.toolEvents.any { it.status == "interrupted" }
                )
            }
        if (!turnInterrupted) return
        val sourceIndex = (index downTo 0).firstOrNull { candidate ->
            val message = sessionMessages[candidate]
            message.role == "user" && (
                selected.turnId.isBlank() || message.turnId == selected.turnId
            )
        } ?: return
        val source = sessionMessages[sourceIndex]
        val savedCheckpoint = resumeState?.takeIf { it.turnId == selected.turnId }
        val hasLaterTurnMessages = sessionMessages.drop(index + 1).any {
            it.turnId == selected.turnId
        }
        if (savedCheckpoint == null && hasLaterTurnMessages) {
            Toast.makeText(context, "该中断记录已经继续处理", Toast.LENGTH_SHORT).show()
            return
        }
        val checkpoint = savedCheckpoint
            ?: ScriptPluginAgentResumeState(
                turnId = selected.turnId,
                sourceUserMessageId = source.id,
                startedAt = source.createdAt,
                updatedAt = System.currentTimeMillis()
            )
        val started = sendMessage(
            content = source.content,
            attachments = emptyList(),
            quotedMessage = null,
            consumeComposer = false,
            resumeFrom = checkpoint
        )
        if (started) Toast.makeText(context, "正在从已保存进度继续", Toast.LENGTH_SHORT).show()
    }

    fun rollbackToMessage(messageId: String) {
        val index = sessionMessages.indexOfFirst { it.id == messageId }
        if (generating || contextCompacting || applyingFileOperation || hasPendingConfirmation() ||
            index < 0
        ) return
        val sourceMessages = sessionMessages
        val retainedMessages = sourceMessages.take(index + 1)
        val removedMessages = sourceMessages.drop(index + 1)
        val restoredDraft = draftForMessages(retainedMessages)
        val rollbackResumeState = resumeState
        prepareMessageHistoryChange(
            retainedMessages = retainedMessages,
            removedMessages = removedMessages,
            actionName = "回滚会话"
        ) { restoredHistory ->
            sessionMessages = retainedMessages
            sessionDraft = restoredDraft
            conversationSummary = ""
            nativeToolHistory = restoredHistory
            protocolTranscript = ""
            discardResumeWorkspace(rollbackResumeState)
            resumeState = null
            compactedMessageCount = 0
            Toast.makeText(context, "已回滚到所选消息", Toast.LENGTH_SHORT).show()
            true
        }
    }

    fun deleteAgentMessage(index: Int) {
        if (generating || contextCompacting || applyingFileOperation || hasPendingConfirmation() ||
            index !in sessionMessages.indices
        ) return
        val hadLaterMessages = index < sessionMessages.lastIndex
        val (remainingMessages, removedMessages) = removeMessageTree(index)
        val restoredDraft = draftForMessages(remainingMessages)
        val nextDraft = if (restoredDraft != null || !hadLaterMessages) restoredDraft else sessionDraft
        val previousResumeState = resumeState
        prepareMessageHistoryChange(
            retainedMessages = remainingMessages,
            removedMessages = removedMessages,
            actionName = "删除消息"
        ) { restoredHistory ->
            sessionMessages = remainingMessages
            sessionDraft = nextDraft
            conversationSummary = ""
            nativeToolHistory = restoredHistory
            protocolTranscript = ""
            discardResumeWorkspace(previousResumeState)
            resumeState = null
            compactedMessageCount = 0
            if (remainingMessages.isEmpty()) {
                historyVersion++
            }
            Toast.makeText(context, "消息已删除", Toast.LENGTH_SHORT).show()
            true
        }
    }

    fun currentSessionSnapshot(updatedAt: Long = System.currentTimeMillis()): ScriptPluginAgentSession {
        return renderedSessionState.snapshot(updatedAt)
    }

    fun persistSession(session: ScriptPluginAgentSession) {
        ScriptPluginAgentSessionStore.save(context, session)
        ScriptPluginAgentRuntime.updateMetadata(session)
        if (session.id == activeSessionId) {
            sessionTitle = session.title
            sessionPinned = session.pinned
            sessionLocked = session.locked
            sessionSortOrder = session.sortOrder
        }
        historyVersion++
    }

    fun renameSession(session: ScriptPluginAgentSession, title: String) {
        val cleanTitle = title.replace(Regex("\\s+"), " ").trim().take(32)
        if (cleanTitle.isBlank()) return
        val source = if (session.id == activeSessionId) {
            currentSessionSnapshot(session.updatedAt)
        } else {
            session
        }
        persistSession(source.copy(title = cleanTitle))
    }

    fun setSessionPinned(session: ScriptPluginAgentSession, pinned: Boolean) {
        val source = if (session.id == activeSessionId) {
            currentSessionSnapshot(session.updatedAt)
        } else {
            session
        }
        val nextOrder = if (pinned) {
            (ScriptPluginAgentSessionStore.list(context).maxOfOrNull { it.sortOrder } ?: source.sortOrder) + 1L
        } else {
            source.sortOrder
        }
        persistSession(source.copy(pinned = pinned, sortOrder = nextOrder))
    }

    fun setSessionLocked(session: ScriptPluginAgentSession, locked: Boolean) {
        val source = if (session.id == activeSessionId) {
            currentSessionSnapshot(session.updatedAt)
        } else {
            session
        }
        persistSession(source.copy(locked = locked))
    }

    fun persistSessionOrder(ordered: List<ScriptPluginAgentSession>) {
        val topOrder = (ordered.maxOfOrNull { it.sortOrder } ?: System.currentTimeMillis()) + ordered.size + 1L
        val reordered = ordered.mapIndexed { index, item -> item.copy(sortOrder = topOrder - index) }
        reordered.firstOrNull { it.id == activeSessionId }?.let { sessionSortOrder = it.sortOrder }
        Thread({
            runCatching {
                reordered.forEach { ScriptPluginAgentSessionStore.save(context, it) }
            }.onSuccess {
                Handler(Looper.getMainLooper()).post { historyVersion++ }
            }.onFailure { error ->
                h.Hchat.utils.HLog.e("[Hchat:ScriptAgent] 保存历史会话排序失败", error)
            }
        }, "Hchat-Agent-Session-Order").start()
    }

    fun deleteSession(session: ScriptPluginAgentSession) {
        if (session.locked) {
            Toast.makeText(context, "请先解锁该对话", Toast.LENGTH_SHORT).show()
            return
        }
        if (ScriptPluginAgentRuntime.isBusy(session.id)) {
            Toast.makeText(context, "该对话正在运行或有待确认操作，请先处理后再删除", Toast.LENGTH_SHORT).show()
            return
        }
        discardResumeWorkspace(
            if (session.id == activeSessionId) resumeState else session.resumeState
        )
        ScriptPluginAgentSessionStore.delete(context, session.id)
        ScriptPluginAgentRuntime.remove(session.id)
        historyVersion++
        if (session.id == activeSessionId) {
            val next = ScriptPluginAgentSessionStore.list(context).firstOrNull()
            if (next == null) newSession(saveCurrent = false) else openSession(next, saveCurrent = false)
        }
    }

    fun prepareForExit() {
        if (!exitPrepared.compareAndSet(false, true)) return
        val hasConversation = ScriptPluginAgentSessionStore.hasConversation(sessionMessages)
        saveCurrentSession()
        if (hasConversation) ScriptPluginAgentRuntime.resumeOnNextEntry(activeSessionId)
    }

    fun exitPage() {
        prepareForExit()
        onBack()
    }

    val latestExitHandler = rememberUpdatedState(newValue = {
        if (showSkills) {
            skillBackHandler?.invoke() ?: run { showSkills = false }
        } else {
            exitPage()
        }
    })
    val latestPrepareForExit = rememberUpdatedState(newValue = { prepareForExit() })
    DisposableEffect(onExitHandlerChanged) {
        val handler = { latestExitHandler.value.invoke() }
        onExitHandlerChanged(handler)
        onDispose {
            onExitHandlerChanged(null)
            latestPrepareForExit.value.invoke()
        }
    }

    LaunchedEffect(
        generating,
        applyingFileOperation,
        pendingWrite,
        pendingDelete,
        pendingWorkspaceChange,
        showConfig,
        showModelPicker,
        showQuickProfilePicker,
        showQuickOptions,
        showSkills,
        pendingMessages
    ) {
        if (
            !generating &&
            !applyingFileOperation &&
            pendingWrite == null &&
            pendingDelete == null &&
            pendingWorkspaceChange == null &&
            !showConfig &&
            !showModelPicker &&
            !showQuickProfilePicker &&
            !showQuickOptions &&
            !showSkills &&
            pendingMessages.isNotEmpty()
        ) {
            delay(250L)
            val next = pendingMessages.firstOrNull() ?: return@LaunchedEffect
            if (
                sendMessage(
                    content = next.content,
                    attachments = next.attachments,
                    quotedMessage = next.quotedMessage,
                    consumeComposer = false
                )
            ) {
                pendingMessages = pendingMessages.filterNot { it.id == next.id }
            }
        }
    }

    if (showToolApprovalDialog) {
        pendingToolApproval?.let { approval ->
            var alwaysAllow by remember(approval.confirmation.eventId) { mutableStateOf(false) }
            WindowDialog(
                show = true,
                title = approval.confirmation.toolName,
                onDismissRequest = { showToolApprovalDialog = false },
                content = {
                    Column(modifier = Modifier.fillMaxWidth().heightIn(max = 520.dp)) {
                        Column(
                            modifier = Modifier.fillMaxWidth().weight(1f, fill = false)
                                .verticalScroll(rememberScrollState())
                        ) {
                            Text(
                                text = "Agent 正在修改插件 ${approval.confirmation.pluginId}，确认后继续执行。",
                                color = MiuixTheme.colorScheme.onSurface,
                                fontSize = 13.sp
                            )
                            Text(
                                text = "代码差异",
                                color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(top = 12.dp)
                            )
                            ScriptPluginAgentDiffCodeBlock(
                                approval.confirmation.diff.ifBlank { "无代码变化" }
                            )
                        }
                        ScriptPluginAgentAlwaysAllowRow(
                            checked = alwaysAllow,
                            summary = "确认后，本轮后续写入和最终提交不再询问",
                            onCheckedChange = { alwaysAllow = it }
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            TextButton(
                                text = "取消修改",
                                onClick = {
                                    showToolApprovalDialog = false
                                    pendingToolApproval = null
                                    approval.resolve(ScriptPluginAgentWorkspaceWriteDecision.CANCEL)
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.textButtonColorsPrimary()
                            )
                            TextButton(
                                text = "确认并继续",
                                onClick = {
                                    if (alwaysAllow) {
                                        setWorkspaceWriteApprovalMode(
                                            ScriptPluginAgentSettings.WRITE_APPROVAL_ALWAYS_ALLOW
                                        )
                                    }
                                    showToolApprovalDialog = false
                                    pendingToolApproval = null
                                    approval.resolve(
                                        if (alwaysAllow) {
                                            ScriptPluginAgentWorkspaceWriteDecision.ALWAYS_ALLOW
                                        } else {
                                            ScriptPluginAgentWorkspaceWriteDecision.APPROVE_ONCE
                                        }
                                    )
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.textButtonColorsPrimary()
                            )
                        }
                    }
                }
            )
        }
    }
    if (showPendingWorkspaceDialog) pendingWorkspaceChange?.let { pending ->
        var alwaysAllow by remember(pending.change.stagingPath) { mutableStateOf(false) }
        fun dismissWorkspaceChange() {
            showPendingWorkspaceDialog = false
            pendingWorkspaceChange = null
            resumeState = null
            Thread({
                ScriptPluginAgentWorkspaceTools.discard(context, pending.change)
            }, "Hchat-Script-Agent-Workspace-Discard").start()
            recordFileOperation(pending.messageIndex, "已取消插件工作区变更")
        }
        WindowDialog(
            show = true,
            title = when {
                pending.change.deletePlugin -> "删除插件"
                pending.isNewPlugin -> "创建插件"
                pending.hasDeletedPaths -> "确认删除路径"
                pending.risky -> "确认高风险修改"
                else -> "确认修改"
            },
            onDismissRequest = { showPendingWorkspaceDialog = false },
            content = {
                Column(modifier = Modifier.fillMaxWidth().heightIn(max = 520.dp)) {
                    Column(
                        modifier = Modifier.fillMaxWidth().weight(1f, fill = false)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = buildString {
                                when {
                                    pending.change.deletePlugin -> append(
                                        "Agent 将删除插件“${pending.change.pluginName}”（${pending.change.pluginId}）。"
                                    )
                                    pending.isNewPlugin -> append(
                                        "Agent 将创建插件“${pending.change.pluginName}”（${pending.change.pluginId}）。"
                                    )
                                    else -> append("Agent 将更新插件 ${pending.change.pluginId}。")
                                }
                                if (!pending.change.deletePlugin) append("提交后插件保持禁用。")
                                if (pending.applyError.isNotBlank()) {
                                    append("\n\n上次提交失败：").append(pending.applyError)
                                    append("\n暂存修改仍在，可直接重试；若目标代码文件已被其它操作修改，请取消后让 Agent 重新读取。")
                                }
                                val summaries = buildList {
                                    if (pending.change.createdPaths.isNotEmpty()) {
                                        add("新增 ${pending.change.createdPaths.size} 项")
                                    }
                                    if (pending.change.modifiedPaths.isNotEmpty()) {
                                        add("修改 ${pending.change.modifiedPaths.size} 项")
                                    }
                                    if (pending.change.deletedPaths.isNotEmpty()) {
                                        add("删除 ${pending.change.deletedPaths.size} 项")
                                    }
                                }
                                if (summaries.isNotEmpty()) {
                                    append("\n\n").append(summaries.joinToString("，"))
                                }
                                val changedPaths = (
                                    pending.change.createdPaths +
                                        pending.change.modifiedPaths +
                                        pending.change.deletedPaths
                                    ).distinct().take(6)
                                if (changedPaths.isNotEmpty()) {
                                    append("\n").append(changedPaths.joinToString("\n") { "- $it" })
                                }
                                if (pending.risky) {
                                    append("\n\n静态检查发现高风险代码：")
                                    append(
                                        pending.change.warnings.filter { it.risky }
                                            .joinToString("；") { it.message }
                                    )
                                }
                            },
                            color = MiuixTheme.colorScheme.onSurface,
                            fontSize = 13.sp
                        )
                        Text(
                            text = "代码差异",
                            color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                        ScriptPluginAgentDiffCodeBlock(pending.change.diff.ifBlank { "无代码变化" })
                    }
                    ScriptPluginAgentAlwaysAllowRow(
                        checked = alwaysAllow,
                        summary = "确认后，后续插件修改不再询问",
                        onCheckedChange = { alwaysAllow = it }
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        TextButton(
                            text = "取消",
                            onClick = { dismissWorkspaceChange() },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.textButtonColorsPrimary()
                        )
                        TextButton(
                            text = when {
                                pending.change.deletePlugin -> "确认删除"
                                pending.isNewPlugin -> "确认创建"
                                pending.applyError.isNotBlank() -> "重试提交"
                                else -> "确认提交"
                            },
                            onClick = {
                                if (alwaysAllow) {
                                    setWorkspaceWriteApprovalMode(
                                        ScriptPluginAgentSettings.WRITE_APPROVAL_ALWAYS_ALLOW
                                    )
                                }
                                showPendingWorkspaceDialog = false
                                pendingWorkspaceChange = null
                                applyWorkspaceChange(pending.messageIndex, pending.change)
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.textButtonColorsPrimary()
                        )
                    }
                }
            }
        )
    }
    if (showPendingWriteDialog) pendingWrite?.let { pending ->
        var alwaysAllow by remember(pending.messageIndex, pending.draft.pluginId) { mutableStateOf(false) }
        fun dismissPendingWrite() {
            showPendingWriteDialog = false
            pendingWrite = null
            resumeState = null
            recordFileOperation(
                pending.messageIndex,
                when {
                    pending.isNewPlugin -> "已取消创建插件"
                    pending.confirmsCreation -> "已取消替换同名插件"
                    pending.risky -> "已取消高风险修改"
                    else -> "已取消插件修改"
                }
            )
        }
        WindowDialog(
            show = true,
            title = when {
                pending.isNewPlugin -> "创建插件"
                pending.confirmsCreation -> "替换同名插件"
                pending.risky -> "确认高风险修改"
                else -> "确认修改"
            },
            onDismissRequest = { showPendingWriteDialog = false },
            content = {
                Column(modifier = Modifier.fillMaxWidth().heightIn(max = 520.dp)) {
                    Column(
                        modifier = Modifier.fillMaxWidth().weight(1f, fill = false)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = buildString {
                                if (pending.isNewPlugin) {
                                    append("Agent 将创建插件“${pending.draft.pluginName}”（${pending.draft.pluginId}）。创建后默认禁用。")
                                } else if (pending.confirmsCreation) {
                                    append("目录 ${pending.draft.pluginId} 已存在。继续会把它作为同名插件更新，并保持禁用。")
                                } else {
                                    append("Agent 将直接修改插件 ${pending.draft.pluginId}，写入后插件会保持禁用。")
                                }
                                if (pending.risky) {
                                    append("\n\n静态检查发现网络、反射、Hook、文件删除或进程执行等高风险代码，请确认代码来源和用途。")
                                }
                            },
                            color = MiuixTheme.colorScheme.onSurface,
                            fontSize = 13.sp
                        )
                        Text(
                            text = "代码差异",
                            color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                        ScriptPluginAgentDiffCodeBlock(pending.diff.ifBlank { "无代码变化" })
                    }
                    ScriptPluginAgentAlwaysAllowRow(
                        checked = alwaysAllow,
                        summary = "确认后，后续插件修改不再询问",
                        onCheckedChange = { alwaysAllow = it }
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        TextButton(
                            text = "取消",
                            onClick = { dismissPendingWrite() },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.textButtonColorsPrimary()
                        )
                        TextButton(
                            text = when {
                                pending.isNewPlugin -> "确认创建"
                                pending.confirmsCreation -> "确认替换"
                                else -> "确认修改"
                            },
                            onClick = {
                                if (alwaysAllow) {
                                    setWorkspaceWriteApprovalMode(
                                        ScriptPluginAgentSettings.WRITE_APPROVAL_ALWAYS_ALLOW
                                    )
                                }
                                showPendingWriteDialog = false
                                pendingWrite = null
                                writeAgentDraft(pending.messageIndex, pending.draft, pending.isNewPlugin)
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.textButtonColorsPrimary()
                        )
                    }
                }
            }
        )
    }
    if (showPendingDeleteDialog) pendingDelete?.let { pending ->
        var alwaysAllow by remember(pending.messageIndex, pending.pluginId) { mutableStateOf(false) }
        fun dismissPendingDelete() {
            showPendingDeleteDialog = false
            pendingDelete = null
            resumeState = null
            recordFileOperation(pending.messageIndex, "已取消删除插件")
        }
        WindowDialog(
            show = true,
            title = "删除插件",
            onDismissRequest = { showPendingDeleteDialog = false },
            content = {
                Column {
                    Text(
                        text = "确定删除插件“${pending.pluginName}”（${pending.pluginId}）吗？插件目录内的全部文件都会被删除，此操作无法撤销。",
                        color = MiuixTheme.colorScheme.onSurface,
                        fontSize = 13.sp
                    )
                    ScriptPluginAgentAlwaysAllowRow(
                        checked = alwaysAllow,
                        summary = "确认后，后续插件修改不再询问",
                        onCheckedChange = { alwaysAllow = it }
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        TextButton(
                            text = "取消",
                            onClick = { dismissPendingDelete() },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.textButtonColorsPrimary()
                        )
                        TextButton(
                            text = "确认删除",
                            onClick = {
                                if (alwaysAllow) {
                                    setWorkspaceWriteApprovalMode(
                                        ScriptPluginAgentSettings.WRITE_APPROVAL_ALWAYS_ALLOW
                                    )
                                }
                                showPendingDeleteDialog = false
                                pendingDelete = null
                                deleteAgentPlugin(pending.messageIndex, pending.pluginId)
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.textButtonColorsPrimary()
                        )
                    }
                }
            }
        )
    }
    if (showQuickProfilePicker) {
        ScriptPluginAgentQuickProfileDialog(
            profiles = profiles,
            activeProfileId = activeProfileId,
            onSelected = {
                selectProfile(it)
                showQuickProfilePicker = false
            },
            onDismiss = { showQuickProfilePicker = false }
        )
    }
    if (showQuickOptions) {
        ScriptPluginAgentQuickOptionsDialog(
            webSearchEnabled = webSearchEnabled,
            workspaceWriteApprovalMode = workspaceWriteApprovalMode,
            promptCacheMode = promptCacheMode,
            endpointMode = endpointMode,
            model = model,
            reasoningEffort = reasoningEffort,
            mcpServers = mcpServers,
            onReasoningEffortChanged = { setReasoningEffort(it) },
            onWebSearchChanged = { setWebSearchEnabled(it) },
            onWorkspaceWriteApprovalChanged = { setWorkspaceWriteApprovalMode(it) },
            onPromptCacheModeChanged = { setPromptCacheMode(it) },
            onMcpChanged = { id, enabled -> setMcpServerEnabled(id, enabled) },
            onOpenSkills = {
                showQuickOptions = false
                openSkillsAfterQuickOptions = true
            },
            onDismiss = { showQuickOptions = false }
        )
    }
    LaunchedEffect(showQuickOptions, openSkillsAfterQuickOptions) {
        if (!showQuickOptions && openSkillsAfterQuickOptions) {
            // Wait until WindowDialog has left composition before opening the next page.
            val activity = findAgentActivity(context)
            val decor = activity?.window?.decorView
            val openPage = Runnable {
                openSkillsAfterQuickOptions = false
                if (activity != null && !activity.isFinishing && !activity.isDestroyed) {
                    showSkills = true
                }
            }
            if (decor != null) {
                decor.postOnAnimation(openPage)
                try {
                    awaitCancellation()
                } finally {
                    decor.removeCallbacks(openPage)
                }
            } else {
                openSkillsAfterQuickOptions = false
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            showModelPicker -> ScriptPluginAgentModelPickerPage(
                config = currentConfig(),
                currentModel = model,
                onSelected = { selectedModel, selectedEffort ->
                    val appliesToNextRequest = generating
                    ScriptPluginAgentSettings.save(
                        context,
                        currentConfig().copy(model = selectedModel, reasoningEffort = selectedEffort)
                    )
                    model = selectedModel
                    reasoningEffort = selectedEffort
                    profileVersion++
                    showModelPicker = false
                    Toast.makeText(
                        context,
                        if (appliesToNextRequest) "已应用模型设置，将用于下一次请求" else "已应用模型设置",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                onBack = { showModelPicker = false }
            )
            showCodexModels -> CodexModelPickerPage(
                baseUrl = codexProfile.baseUrl,
                apiKey = codexProfile.apiKey,
                currentModel = codexProfile.model,
                currentEffort = codexProfile.effort,
                onSetModel = { selectedModel, selectedEffort ->
                    codexProfile = codexProfile.copy(model = selectedModel, effort = selectedEffort)
                    showCodexModels = false
                },
                onBack = { showCodexModels = false }
            )
            showConfig -> ScriptPluginAgentConfigPage(
                profiles = profiles,
                activeProfileId = activeProfileId,
                activeProfileName = activeProfileName,
                testingConnection = testingConnection,
                apiBase = apiBase,
                endpointMode = endpointMode,
                apiKey = apiKey,
                model = model,
                reasoningEffort = reasoningEffort,
                mcpServers = mcpServers,
                autoCompactEnabled = autoCompactEnabled,
                compactTokenThreshold = compactTokenThreshold,
                promptCacheMode = promptCacheMode,
                onApiBaseChange = { apiBase = it },
                onEndpointModeChange = { mode ->
                    endpointMode = mode
                    ScriptPluginAgentSettings.presetRequestUrl(mode)
                        .takeIf { it.isNotBlank() }
                        ?.let { apiBase = it }
                },
                onApiKeyChange = { apiKey = it },
                onModelChange = { model = it },
                onReasoningEffortChange = { reasoningEffort = it },
                onMcpServersChange = { mcpServers = it },
                onAutoCompactEnabledChange = { autoCompactEnabled = it },
                onCompactTokenThresholdChange = { compactTokenThreshold = it.filter(Char::isDigit) },
                onPromptCacheModeChange = { promptCacheMode = it },
                codexProfile = codexProfile,
                codexProfiles = codexProfiles,
                onCodexProfileChange = { codexProfile = it },
                onCodexProfileSelected = { selectCodexProfile(it) },
                onCodexCreateProfile = { createCodexProfile(it) },
                onCodexRenameProfile = { renameCodexProfile(it) },
                onCodexDeleteProfile = { deleteCodexProfile() },
                onCodexOpenModels = { showCodexModels = true },
                onCodexTestConnection = { testCodexConnection() },
                testingCodexConnection = testingCodexConnection,
                settingsTab = settingsTab,
                onSettingsTabSelected = { settingsTab = it },
                onCodexSave = { saveCodexConfig() },
                onProfileSelected = { selectProfile(it) },
                onCreateProfile = { createProfile(it) },
                onRenameProfile = { renameProfile(it) },
                onDeleteProfile = { deleteActiveProfile() },
                onTestConnection = { testAgentConnection() },
                onOpenModels = { showModelPicker = true },
                onOpenSkills = { showSkills = true },
                onCompact = { compactContext() },
                onSave = { saveConfig() },
                onBack = { showConfig = false }
            )
            else -> ScriptPluginAgentChatPage(
                title = sessionTitle,
                messages = sessionMessages,
                messageInput = messageInput,
                pendingAttachments = pendingAttachments,
                pendingQuotedMessage = pendingQuotedMessage,
                pendingMessages = pendingMessages,
                pendingMessagesExpanded = pendingMessagesExpanded,
                activeProfileName = activeProfileName,
                activeModel = model,
                enabled = !generating && !contextCompacting && !applyingFileOperation &&
                    !hasPendingConfirmation(),
                inputEnabled = !contextCompacting && !applyingFileOperation &&
                    !hasPendingConfirmation(),
                generating = generating,
                contextCompacting = contextCompacting,
                workingStartedAt = if (contextCompacting) contextCompactionStartedAt else generationStartedAt,
                workingLabel = when {
                    contextCompacting && generating -> "正在自动压缩上下文"
                    contextCompacting -> "正在压缩上下文"
                    else -> ""
                },
                showWorking = generating && !generationHasVisibleReply,
                pendingConfirmationLabel = when {
                    pendingToolApproval != null -> "${pendingToolApproval?.confirmation?.toolName}待确认"
                    pendingWorkspaceChange != null -> "最终插件修改待确认"
                    pendingWrite != null -> "最终插件写入待确认"
                    pendingDelete != null -> "删除插件待确认"
                    else -> null
                },
                onMessageInputChange = { messageInput = it },
                onSend = { sendMessage() },
                onQueue = { queueMessage() },
                onCancel = { cancelGeneration() },
                onAttach = { selectAttachments() },
                onRemoveAttachment = { path ->
                    val removed = pendingAttachments.filter { it.path == path }
                    pendingAttachments = pendingAttachments.filterNot { it.path == path }
                    ScriptPluginAgentSessionStore.cleanupAttachments(context, removed)
                },
                onClearQuotedMessage = { pendingQuotedMessage = null },
                onPendingMessagesExpandedChange = { pendingMessagesExpanded = it },
                onEditPendingMessage = { editPendingMessage(it) },
                onDeletePendingMessage = { id ->
                    pendingMessages.firstOrNull { it.id == id }?.let { pending ->
                        ScriptPluginAgentSessionStore.cleanupAttachments(context, pending.attachments)
                    }
                    pendingMessages = pendingMessages.filterNot { it.id == id }
                },
                onSendPendingMessageNow = { sendPendingMessageNow(it) },
                onOpenProfilePicker = { showQuickProfilePicker = true },
                onOpenModelPicker = { showModelPicker = true },
                onOpenQuickOptions = { showQuickOptions = true },
                onOpenPendingConfirmation = { showPendingConfirmation() },
                onCompact = { compactContext() },
                onOpenHistory = { showHistory = true },
                onOpenConfig = {
                    if (!generating && !contextCompacting && !applyingFileOperation &&
                        !showPendingConfirmation()
                    ) {
                        showConfig = true
                    }
                },
                onEditAndResend = { editAndResendMessage(it) },
                onQuoteMessage = { quoteAgentMessage(it) },
                onContinueMessage = { continueInterruptedTask(it) },
                onRegenerateMessage = { regenerateAssistantMessage(it) },
                onRollback = { rollbackToMessage(it) },
                onDeleteMessage = { deleteAgentMessage(it) },
                onCreateBranch = { createMessageBranch(it) },
                onBack = { exitPage() }
            )
        }
        AnimatedVisibility(
            visible = showSkills,
            enter = slideInHorizontally(tween(240)) { it } + fadeIn(tween(160)),
            exit = slideOutHorizontally(tween(220)) { it } + fadeOut(tween(140))
        ) {
            ScriptPluginAgentSkillsPage(
                context = context,
                onBack = { showSkills = false },
                onBackHandlerChanged = { skillBackHandler = it },
                onUse = { name ->
                    messageInput = "\$$name " + messageInput
                    showSkills = false
                    showConfig = false
                }
            )
        }
        if (showHistory) {
            val workingSessionIds = ScriptPluginAgentRuntime.workingSessionIds()
            val visibleSessions = if (ScriptPluginAgentSessionStore.hasConversation(sessionMessages)) {
                val currentSession = currentSessionSnapshot()
                (sessions.filterNot { it.id == activeSessionId } + currentSession).distinctBy { it.id }
            } else {
                sessions.filterNot { it.id == activeSessionId }
            }
            val runtimeSessions = ScriptPluginAgentRuntime.snapshots()
            ScriptPluginAgentHistoryDrawer(
                sessions = ScriptPluginAgentSessionStore.sorted(
                    (visibleSessions + runtimeSessions).distinctBy { it.id }
                ),
                activeId = activeSessionId,
                workingSessionIds = workingSessionIds,
                enabled = true,
                onNewSession = { newSession() },
                onSelect = { openSession(it) },
                onRename = { session, title -> renameSession(session, title) },
                onCommitOrder = { persistSessionOrder(it) },
                onSetPinned = { session, pinned -> setSessionPinned(session, pinned) },
                onSetLocked = { session, locked -> setSessionLocked(session, locked) },
                onDelete = { deleteSession(it) },
                onClose = { showHistory = false }
            )
        }
    }
}

internal fun promptCacheModeChoices(): List<PopupChoice<String>> = listOf(
    PopupChoice("自动", ScriptPluginAgentSettings.PROMPT_CACHE_AUTO),
    PopupChoice("强制", ScriptPluginAgentSettings.PROMPT_CACHE_FORCE),
    PopupChoice("关闭", ScriptPluginAgentSettings.PROMPT_CACHE_OFF)
)

internal fun promptCacheModeSummary(mode: String, endpointMode: String): String {
    if (endpointMode == ScriptPluginAgentSettings.ENDPOINT_MODE_GEMINI) {
        return "Gemini 不使用 OpenAI 或 Anthropic 的显式缓存字段"
    }
    return when (mode) {
        ScriptPluginAgentSettings.PROMPT_CACHE_FORCE ->
            "向兼容接口发送稳定缓存标识，不支持时自动回退"
        ScriptPluginAgentSettings.PROMPT_CACHE_OFF ->
            "不发送显式缓存字段"
        else -> "官方接口显式缓存，其他接口使用服务端默认策略"
    }
}

@Composable
internal fun ScriptPluginAgentConfigPage(
    profiles: List<ScriptPluginAgentProfile>,
    activeProfileId: String,
    activeProfileName: String,
    testingConnection: Boolean,
    apiBase: String,
    endpointMode: String,
    apiKey: String,
    model: String,
    reasoningEffort: String,
    mcpServers: List<ScriptPluginAgentMcpServer>,
    autoCompactEnabled: Boolean,
    compactTokenThreshold: String,
    promptCacheMode: String,
    onApiBaseChange: (String) -> Unit,
    onEndpointModeChange: (String) -> Unit,
    onApiKeyChange: (String) -> Unit,
    onModelChange: (String) -> Unit,
    onReasoningEffortChange: (String) -> Unit,
    onMcpServersChange: (List<ScriptPluginAgentMcpServer>) -> Unit,
    onAutoCompactEnabledChange: (Boolean) -> Unit,
    onCompactTokenThresholdChange: (String) -> Unit,
    onPromptCacheModeChange: (String) -> Unit,
    onProfileSelected: (ScriptPluginAgentProfile) -> Unit,
    onCreateProfile: (String) -> Unit,
    onRenameProfile: (String) -> Unit,
    onDeleteProfile: () -> Unit,
    onTestConnection: () -> Unit,
    onOpenModels: () -> Unit,
    onOpenSkills: () -> Unit,
    onCompact: () -> Unit,
    codexProfile: CodexSettings.Profile,
    codexProfiles: List<CodexSettings.Profile>,
    onCodexProfileChange: (CodexSettings.Profile) -> Unit,
    onCodexProfileSelected: (String) -> Unit,
    onCodexCreateProfile: (String) -> Unit,
    onCodexRenameProfile: (String) -> Unit,
    onCodexDeleteProfile: () -> Unit,
    onCodexOpenModels: () -> Unit,
    onCodexTestConnection: () -> Unit,
    testingCodexConnection: Boolean,
    settingsTab: Int,
    onSettingsTabSelected: (Int) -> Unit,
    onCodexSave: () -> Unit,
    onSave: () -> Unit,
    onBack: () -> Unit
) {
    var profileDialog by remember { mutableStateOf("") }
    var profileNameDraft by remember(activeProfileName, profileDialog) {
        mutableStateOf(if (profileDialog == "rename") activeProfileName else "")
    }
    var showDeleteConfirm by remember { mutableStateOf(false) }
    var codexProfileDialog by remember { mutableStateOf("") }
    var codexProfileNameDraft by remember(codexProfile.name, codexProfileDialog) {
        mutableStateOf(if (codexProfileDialog == "rename") codexProfile.name else "")
    }
    var codexDeleteConfirm by remember { mutableStateOf(false) }
    val resolvedEndpoint = remember(apiBase, endpointMode, model) {
        ScriptPluginAgentSettings.requestUrl(apiBase, endpointMode, model)
    }

    if (profileDialog.isNotBlank()) {
        WindowDialog(
            show = true,
            title = if (profileDialog == "rename") "重命名配置" else "新建配置",
            onDismissRequest = { profileDialog = "" },
            content = {
                Column {
                    InputRow(
                        title = "配置名称",
                        summary = "用于区分不同服务和模型",
                        value = profileNameDraft,
                        onValueChange = { profileNameDraft = it.take(32) }
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        TextButton(
                            text = "取消",
                            onClick = { profileDialog = "" },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.textButtonColorsPrimary()
                        )
                        TextButton(
                            text = "确认",
                            onClick = {
                                if (profileNameDraft.isNotBlank()) {
                                    if (profileDialog == "rename") {
                                        onRenameProfile(profileNameDraft)
                                    } else {
                                        onCreateProfile(profileNameDraft)
                                    }
                                    profileDialog = ""
                                }
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.textButtonColorsPrimary()
                        )
                    }
                }
            }
        )
    }
    if (showDeleteConfirm) {
        WindowDialog(
            show = true,
            title = "删除配置",
            onDismissRequest = { showDeleteConfirm = false },
            content = {
                Column {
                    Text(
                        text = "确定删除“$activeProfileName”吗？",
                        color = MiuixTheme.colorScheme.onSurface,
                        fontSize = 14.sp
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 14.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        TextButton(
                            text = "取消",
                            onClick = { showDeleteConfirm = false },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.textButtonColorsPrimary()
                        )
                        TextButton(
                            text = "删除",
                            onClick = {
                                showDeleteConfirm = false
                                onDeleteProfile()
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.textButtonColorsPrimary()
                        )
                    }
                }
            }
        )
    }
    if (codexProfileDialog.isNotBlank()) {
        WindowDialog(
            show = true,
            title = if (codexProfileDialog == "rename") "重命名 codex 配置" else "新建 codex 配置",
            onDismissRequest = { codexProfileDialog = "" },
            content = {
                Column {
                    InputRow(
                        title = "配置名称",
                        summary = "用于区分不同服务和模型",
                        value = codexProfileNameDraft,
                        onValueChange = { codexProfileNameDraft = it.take(32) }
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        TextButton(
                            text = "取消",
                            onClick = { codexProfileDialog = "" },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.textButtonColorsPrimary()
                        )
                        TextButton(
                            text = "确认",
                            onClick = {
                                if (codexProfileNameDraft.isNotBlank()) {
                                    if (codexProfileDialog == "rename") {
                                        onCodexRenameProfile(codexProfileNameDraft)
                                    } else {
                                        onCodexCreateProfile(codexProfileNameDraft)
                                    }
                                    codexProfileDialog = ""
                                }
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.textButtonColorsPrimary()
                        )
                    }
                }
            }
        )
    }
    if (codexDeleteConfirm) {
        WindowDialog(
            show = true,
            title = "删除 codex 配置",
            onDismissRequest = { codexDeleteConfirm = false },
            content = {
                Column {
                    Text(
                        text = "确定删除“${codexProfile.name}”吗？",
                        color = MiuixTheme.colorScheme.onSurface,
                        fontSize = 14.sp
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 14.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        TextButton(
                            text = "取消",
                            onClick = { codexDeleteConfirm = false },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.textButtonColorsPrimary()
                        )
                        TextButton(
                            text = "删除",
                            onClick = {
                                codexDeleteConfirm = false
                                onCodexDeleteProfile()
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.textButtonColorsPrimary()
                        )
                    }
                }
            }
        )
    }

    val listState = rememberLazyListState()
    val scrollBehavior = MiuixScrollBehavior()
    PageScaffold(
        title = "Agent 配置",
        largeTitle = "Agent 配置",
        scrollBehavior = scrollBehavior,
        bottomBar = {
            BottomActionBar(
                primaryText = if (settingsTab == 1) "保存 codex 配置" else "保存配置",
                onPrimaryClick = if (settingsTab == 1) onCodexSave else onSave,
                secondaryText = "返回",
                onSecondaryClick = onBack
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            state = listState,
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 8.dp,
                bottom = padding.calculateBottomPadding() + 84.dp
            )
        ) {
            item {
                TabRow(
                    tabs = listOf("Agent 设置", "codex 设置"),
                    selectedTabIndex = settingsTab,
                    onTabSelected = { onSettingsTabSelected(it) },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }
            if (settingsTab == 1) {
                codexTabItems(
                    profile = codexProfile,
                    profiles = codexProfiles,
                    onProfileChange = onCodexProfileChange,
                    onProfileSelected = onCodexProfileSelected,
                    onCreateProfile = { codexProfileDialog = "create" },
                    onRenameProfile = { codexProfileDialog = "rename" },
                    onDeleteProfile = { codexDeleteConfirm = true },
                    onTestConnection = onCodexTestConnection,
                    testingConnection = testingCodexConnection,
                    onOpenModels = onCodexOpenModels
                )
            } else {
                item { SmallTitle(text = "模型配置") }
                item {
                    SettingsCard {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, top = 10.dp, end = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "选择模型配置",
                                color = MiuixTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.weight(1f)
                            )
                            TextButton(
                                text = "+ 新建",
                                onClick = {
                                    profileNameDraft = ""
                                    profileDialog = "create"
                                },
                                minWidth = 0.dp,
                                minHeight = 34.dp,
                                cornerRadius = 10.dp,
                                insideMargin = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                colors = ButtonDefaults.textButtonColorsPrimary()
                            )
                        }
                        PopupChoiceRow(
                            title = "当前配置",
                            summary = activeProfileName,
                            options = profiles.map { PopupChoice(it.name, it.id) },
                            currentValue = activeProfileId,
                            onValueChanged = { id -> profiles.firstOrNull { it.id == id }?.let(onProfileSelected) }
                        )
                        InsetDivider()
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 6.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            TextButton(
                                text = "重命名",
                                onClick = {
                                    profileNameDraft = activeProfileName
                                    profileDialog = "rename"
                                },
                                modifier = Modifier.weight(1f),
                                minWidth = 0.dp,
                                minHeight = 34.dp,
                                cornerRadius = 10.dp,
                                insideMargin = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                colors = ButtonDefaults.textButtonColorsPrimary()
                            )
                            TextButton(
                                text = "删除",
                                onClick = { showDeleteConfirm = true },
                                enabled = profiles.size > 1,
                                modifier = Modifier.weight(1f),
                                minWidth = 0.dp,
                                minHeight = 34.dp,
                                cornerRadius = 10.dp,
                                insideMargin = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                colors = ButtonDefaults.textButtonColorsPrimary()
                            )
                            TextButton(
                                text = if (testingConnection) "测试中" else "测试连接",
                                onClick = onTestConnection,
                                enabled = !testingConnection,
                                modifier = Modifier.weight(1f),
                                minWidth = 0.dp,
                                minHeight = 34.dp,
                                cornerRadius = 10.dp,
                                insideMargin = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                colors = ButtonDefaults.textButtonColorsPrimary()
                            )
                        }
                    }
                }
                item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "API 设置") }
                item {
                    SettingsCard {
                        PopupChoiceRow(
                            title = "接口类型",
                            summary = ScriptPluginAgentSettings.endpointModeLabel(endpointMode),
                            options = listOf(
                                PopupChoice("OpenAI 兼容", ScriptPluginAgentSettings.ENDPOINT_MODE_OPENAI_COMPATIBLE),
                                PopupChoice("OpenAI", ScriptPluginAgentSettings.ENDPOINT_MODE_OPENAI),
                                PopupChoice("DeepSeek", ScriptPluginAgentSettings.ENDPOINT_MODE_DEEPSEEK),
                                PopupChoice("OpenRouter", ScriptPluginAgentSettings.ENDPOINT_MODE_OPENROUTER),
                                PopupChoice("硅基流动", ScriptPluginAgentSettings.ENDPOINT_MODE_SILICONFLOW),
                                PopupChoice("Gemini", ScriptPluginAgentSettings.ENDPOINT_MODE_GEMINI),
                                PopupChoice("Anthropic", ScriptPluginAgentSettings.ENDPOINT_MODE_ANTHROPIC),
                                PopupChoice("自定义请求链接", ScriptPluginAgentSettings.ENDPOINT_MODE_CUSTOM_URL)
                            ),
                            currentValue = endpointMode,
                            onValueChanged = onEndpointModeChange
                        )
                        InsetDivider()
                        InputRow(
                            if (endpointMode == ScriptPluginAgentSettings.ENDPOINT_MODE_CUSTOM_URL) {
                                "请求链接"
                            } else {
                                "API 地址"
                            },
                            if (endpointMode == ScriptPluginAgentSettings.ENDPOINT_MODE_GEMINI) {
                                "自动补全 Gemini 模型与 generateContent 请求路径"
                            } else if (endpointMode == ScriptPluginAgentSettings.ENDPOINT_MODE_ANTHROPIC) {
                                "自动补全 /v1/messages"
                            } else if (endpointMode == ScriptPluginAgentSettings.ENDPOINT_MODE_CUSTOM_URL) {
                                "手动填写完整 URL，不会自动补全或改写"
                            } else {
                                "可填写域名、/v1 地址或完整 /chat/completions 地址"
                            },
                            apiBase,
                            onValueChange = onApiBaseChange
                        )
                        if (resolvedEndpoint.isNotBlank()) {
                            InsetDivider()
                            ScriptPluginAgentEndpointPreview(resolvedEndpoint)
                        }
                        InsetDivider()
                        InputRow("API Key", "留空表示接口不需要密钥", apiKey, onValueChange = onApiKeyChange)
                        InsetDivider()
                        InputRow("模型 ID", "可直接填写，无需先获取模型列表", model, onValueChange = onModelChange)
                        InsetDivider()
                        ScriptPluginAgentReasoningRow(endpointMode, model, reasoningEffort, onReasoningEffortChange)
                        InsetDivider()
                        ActionRow("选择模型", "手动输入模型 ID 或从接口获取列表") { onOpenModels() }
                        InsetDivider()
                        PopupChoiceRow(
                            title = "提示缓存",
                            summary = promptCacheModeSummary(promptCacheMode, endpointMode),
                            options = promptCacheModeChoices(),
                            currentValue = promptCacheMode,
                            onValueChanged = onPromptCacheModeChange
                        )
                    }
                }
                item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "上下文") }
                item {
                    SettingsCard {
                        SwitchRow(
                            checked = autoCompactEnabled,
                            title = "自动压缩上下文",
                            summary = "达到设定阈值后压缩较早对话",
                            onCheckedChange = onAutoCompactEnabledChange
                        )
                        if (autoCompactEnabled) {
                            InsetDivider()
                            InputRow(
                                "压缩阈值",
                                "Token 估算值，范围 2000 到 1000000",
                                compactTokenThreshold,
                                onValueChange = onCompactTokenThresholdChange
                            )
                        }
                        InsetDivider()
                        ActionRow("立即压缩当前会话", "保留本地历史和当前代码草稿") { onCompact() }
                    }
                }
                item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "技能") }
                item {
                    SettingsCard {
                        ActionRow("Skill 管理", "编写或导入技能，让 Agent 按任务读取专用指南") { onOpenSkills() }
                    }
                }
                item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "MCP 工具") }
                item {
                    SettingsCard {
                        ActionRow("添加 MCP", "可同时启用多个远程 MCP 服务器") {
                            val nextIndex = mcpServers.size + 1
                            onMcpServersChange(
                                mcpServers + ScriptPluginAgentMcpServer(
                                    id = java.util.UUID.randomUUID().toString().replace("-", ""),
                                    name = "MCP $nextIndex"
                                )
                            )
                        }
                    }
                }
                mcpServers.forEachIndexed { index, server ->
                    item(key = "agent-mcp-${server.id}") {
                        SettingsCard(modifier = Modifier.padding(top = 8.dp)) {
                            InputRow(
                                "名称",
                                "用于区分工具来源",
                                server.name,
                                onValueChange = { value ->
                                    onMcpServersChange(
                                        mcpServers.map { if (it.id == server.id) it.copy(name = value.take(32)) else it }
                                    )
                                }
                            )
                            InsetDivider()
                            SwitchRow(
                                checked = server.enabled,
                                title = "启用 ${server.name.ifBlank { "MCP ${index + 1}" }}",
                                summary = if (server.enabled) "此服务器的工具可供 Agent 调用" else "此服务器不会连接或提供工具",
                                onCheckedChange = { enabled ->
                                    onMcpServersChange(
                                        mcpServers.map { if (it.id == server.id) it.copy(enabled = enabled) else it }
                                    )
                                }
                            )
                            if (server.enabled) {
                                InsetDivider()
                                InputRow(
                                    "MCP Endpoint",
                                    "例如 https://example.com/mcp",
                                    server.endpoint,
                                    onValueChange = { value ->
                                        onMcpServersChange(
                                            mcpServers.map { if (it.id == server.id) it.copy(endpoint = value) else it }
                                        )
                                    }
                                )
                                InsetDivider()
                                InputRow(
                                    "Authorization",
                                    "可选，例如 Bearer token",
                                    server.authorization,
                                    onValueChange = { value ->
                                        onMcpServersChange(
                                            mcpServers.map {
                                                if (it.id == server.id) it.copy(authorization = value) else it
                                            }
                                        )
                                    }
                                )
                            }
                            InsetDivider()
                            ActionRow("删除 MCP", "移除此服务器配置") {
                                onMcpServersChange(mcpServers.filterNot { it.id == server.id })
                            }
                        }
                    }
                }
            }
        }
    }
}

internal fun LazyListScope.codexTabItems(
    profile: CodexSettings.Profile,
    profiles: List<CodexSettings.Profile>,
    onProfileChange: (CodexSettings.Profile) -> Unit,
    onProfileSelected: (String) -> Unit,
    onCreateProfile: () -> Unit,
    onRenameProfile: () -> Unit,
    onDeleteProfile: () -> Unit,
    onTestConnection: () -> Unit,
    testingConnection: Boolean,
    onOpenModels: () -> Unit
) {
    item { SmallTitle(text = "模型配置") }
    item {
        SettingsCard {
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, top = 10.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "选择模型配置",
                    color = MiuixTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )
                TextButton(
                    text = "+ 新建",
                    onClick = onCreateProfile,
                    minWidth = 0.dp,
                    minHeight = 34.dp,
                    cornerRadius = 10.dp,
                    insideMargin = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                    colors = ButtonDefaults.textButtonColorsPrimary()
                )
            }
            PopupChoiceRow(
                title = "当前配置",
                summary = profile.name,
                options = profiles.map { PopupChoice(it.name, it.id) },
                currentValue = profile.id,
                onValueChanged = { id ->
                    profiles.firstOrNull { it.id == id }?.let { onProfileSelected(it.id) }
                }
            )
            InsetDivider()
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextButton(
                    text = "重命名",
                    onClick = onRenameProfile,
                    modifier = Modifier.weight(1f),
                    minWidth = 0.dp,
                    minHeight = 34.dp,
                    cornerRadius = 10.dp,
                    insideMargin = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                    colors = ButtonDefaults.textButtonColorsPrimary()
                )
                TextButton(
                    text = "删除",
                    onClick = onDeleteProfile,
                    enabled = profiles.size > 1,
                    modifier = Modifier.weight(1f),
                    minWidth = 0.dp,
                    minHeight = 34.dp,
                    cornerRadius = 10.dp,
                    insideMargin = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                    colors = ButtonDefaults.textButtonColorsPrimary()
                )
                TextButton(
                    text = if (testingConnection) "测试中" else "测试连接",
                    onClick = onTestConnection,
                    enabled = !testingConnection,
                    modifier = Modifier.weight(1f),
                    minWidth = 0.dp,
                    minHeight = 34.dp,
                    cornerRadius = 10.dp,
                    insideMargin = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                    colors = ButtonDefaults.textButtonColorsPrimary()
                )
            }
        }
    }
    item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "codex 配置") }
    item {
        SettingsCard {
            InputRow(
                "API 地址",
                "可填写域名、/v1 地址或完整接口地址",
                profile.baseUrl,
                onValueChange = { onProfileChange(profile.copy(baseUrl = it)) }
            )
            val resolvedCodexEndpoint = remember(profile.baseUrl) { CodexSettings.resolvedBaseUrl(profile.baseUrl) }
            if (resolvedCodexEndpoint.isNotBlank()) {
                InsetDivider()
                ScriptPluginAgentEndpointPreview(resolvedCodexEndpoint)
            }
            InsetDivider()
            InputRow(
                "API Key",
                "模型密钥",
                profile.apiKey,
                onValueChange = { onProfileChange(profile.copy(apiKey = it)) }
            )
            InsetDivider()
            InputRow(
                "模型",
                "填接口里的模型名，也可以点下面「选择模型」拉取",
                profile.model,
                onValueChange = { onProfileChange(profile.copy(model = it)) }
            )
            InsetDivider()
            ActionRow("选择模型", "手动输入模型名，或从接口拉取模型列表") { onOpenModels() }
            InsetDivider()
            PopupChoiceRow(
                title = "思考程度",
                summary = ScriptPluginAgentReasoning.label(profile.effort),
                options = CodexSettings.EFFORT_OPTIONS.map { PopupChoice(it.second, it.first) },
                currentValue = profile.effort,
                onValueChanged = { onProfileChange(profile.copy(effort = it)) }
            )
        }
    }
    item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "MCP 服务") }
    item {
        SettingsCard {
            ActionRow("添加 MCP", "远程 MCP 服务器，保存后写入 codex 配置") {
                val next = profile.mcpServers.size + 1
                onProfileChange(
                    profile.copy(mcpServers = profile.mcpServers + CodexSettings.McpServer(name = "MCP $next"))
                )
            }
        }
    }
    profile.mcpServers.forEachIndexed { index, server ->
        item(key = "codex-mcp-${server.id}") {
            SettingsCard(modifier = Modifier.padding(top = 8.dp)) {
                InputRow(
                    "名称",
                    "只能是字母数字，写入 codex 配置时作为服务器名",
                    server.name,
                    onValueChange = { value ->
                        onProfileChange(
                            profile.copy(
                                mcpServers = profile.mcpServers.map {
                                    if (it.id == server.id) it.copy(name = value.take(32)) else it
                                }
                            )
                        )
                    }
                )
                InsetDivider()
                SwitchRow(
                    checked = server.enabled,
                    title = "启用 ${server.name.ifBlank { "MCP ${index + 1}" }}",
                    summary = if (server.enabled) "保存后写入 codex 配置" else "不会写入 codex 配置",
                    onCheckedChange = { enabled ->
                        onProfileChange(
                            profile.copy(
                                mcpServers = profile.mcpServers.map {
                                    if (it.id == server.id) it.copy(enabled = enabled) else it
                                }
                            )
                        )
                    }
                )
                if (server.enabled) {
                    InsetDivider()
                    InputRow(
                        "MCP Endpoint",
                        "例如 https://example.com/mcp",
                        server.url,
                        onValueChange = { value ->
                            onProfileChange(
                                profile.copy(
                                    mcpServers = profile.mcpServers.map {
                                        if (it.id == server.id) it.copy(url = value) else it
                                    }
                                )
                            )
                        }
                    )
                    InsetDivider()
                    InputRow(
                        "访问令牌",
                        "可选，按环境变量注入，不写进配置文件",
                        server.token,
                        onValueChange = { value ->
                            onProfileChange(
                                profile.copy(
                                    mcpServers = profile.mcpServers.map {
                                        if (it.id == server.id) it.copy(token = value) else it
                                    }
                                )
                            )
                        }
                    )
                }
                InsetDivider()
                ActionRow("删除 MCP", "移除此服务器配置") {
                    onProfileChange(profile.copy(mcpServers = profile.mcpServers.filterNot { it.id == server.id }))
                }
            }
        }
    }
    item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "Ubuntu 终端环境") }
    item { ScriptPluginAgentTerminalCard() }
}

@Composable
internal fun ScriptPluginAgentTerminalCard() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var status by remember {
        mutableStateOf(h.Hchat.hooks.items.script.agent.ProotEnvironment.refreshStatus(context))
    }
    var diskText by remember { mutableStateOf("") }
    var showDeleteConfirm by remember { mutableStateOf(false) }
    var showTerminal by remember { mutableStateOf(false) }
    // 终端显示字体（未下载的先按需下载，下载后切换即时生效）
    var fontId by remember {
        mutableStateOf(h.Hchat.hooks.items.script.agent.TerminalFonts.selectedId(context))
    }
    // 内置 codex 更新（服务器清单驱动，不依赖官方 chatgpt.com 通道）
    var codexVersion by remember { mutableStateOf("") }
    var codexRemote by remember { mutableStateOf<h.Hchat.hooks.items.script.agent.CodexUpdater.Release?>(null) }
    var codexBusy by remember { mutableStateOf(false) }
    var codexProgress by remember { mutableStateOf("") }
    var codexMessage by remember { mutableStateOf("") }
    var showCodexConfirm by remember { mutableStateOf(false) }
    // codex 网页版：在手机浏览器里用内置 codex
    var webInstalled by remember { mutableStateOf(false) }
    var webRunning by remember { mutableStateOf(false) }
    var webBusy by remember { mutableStateOf(false) }
    var webProgress by remember { mutableStateOf("") }
    var webMessage by remember { mutableStateOf("") }
    var webAutoKeep by remember { mutableStateOf(false) }
    var webRestarts by remember { mutableStateOf(0) }

    fun refreshDisk() {
        scope.launch(Dispatchers.IO) {
            val bytes = h.Hchat.hooks.items.script.agent.ProotEnvironment.diskUsage(context)
            withContext(Dispatchers.Main) {
                diskText = when {
                    bytes <= 0 -> ""
                    bytes >= 1073741824L -> String.format(Locale.US, "占用 %.2f GB", bytes / 1073741824.0)
                    else -> String.format(Locale.US, "占用 %.0f MB", bytes / 1048576.0)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        status = h.Hchat.hooks.items.script.agent.ProotEnvironment.refreshStatus(context)
        refreshDisk()
    }

    fun refreshCodex() {
        scope.launch(Dispatchers.IO) {
            val inst = h.Hchat.hooks.items.script.agent.CodexUpdater.installed(context)
            val remote = h.Hchat.hooks.items.script.agent.CodexUpdater.check(context).getOrNull()
            val need = remote != null && h.Hchat.hooks.items.script.agent.CodexUpdater.needsUpdate(context, remote)
            withContext(Dispatchers.Main) {
                codexVersion = inst?.version.orEmpty()
                if (need) codexRemote = remote
                if (h.Hchat.hooks.items.script.agent.CodexUpdater.isBusy()) {
                    codexMessage = "后台下载中 ${h.Hchat.hooks.items.script.agent.CodexUpdater.progress()}%"
                }
            }
        }
    }

    fun checkCodexUpdate() {
        if (codexBusy) return
        scope.launch(Dispatchers.IO) {
            val remote = h.Hchat.hooks.items.script.agent.CodexUpdater.check(context).getOrElse { error ->
                val inst = h.Hchat.hooks.items.script.agent.CodexUpdater.installed(context)
                withContext(Dispatchers.Main) {
                    codexVersion = inst?.version.orEmpty()
                    codexMessage = "检查失败：${error.message.orEmpty()}"
                }
                return@launch
            }
            val inst = h.Hchat.hooks.items.script.agent.CodexUpdater.installed(context)
            val need = h.Hchat.hooks.items.script.agent.CodexUpdater.needsUpdate(context, remote)
            withContext(Dispatchers.Main) {
                codexVersion = inst?.version.orEmpty()
                codexRemote = if (need) remote else null
                if (need) {
                    codexMessage = ""
                    showCodexConfirm = true
                } else {
                    codexMessage = "已是最新版本 ${inst?.version.orEmpty()}"
                }
            }
        }
    }

    fun runCodexUpdate(release: h.Hchat.hooks.items.script.agent.CodexUpdater.Release) {
        if (codexBusy) return
        showCodexConfirm = false
        codexBusy = true
        codexProgress = "准备下载"
        codexMessage = ""
        scope.launch(Dispatchers.IO) {
            val updater = h.Hchat.hooks.items.script.agent.CodexUpdater
            if (!updater.isBusy() && !updater.start(context, release)) {
                withContext(Dispatchers.Main) {
                    codexBusy = false
                    codexProgress = ""
                    codexMessage = "已有下载任务在进行"
                }
                return@launch
            }
            // 下载在模块后台线程，这里只刷新进度；断了下次从断点续传
            while (updater.isBusy()) {
                val pct = updater.progress()
                val stage = updater.stage()
                withContext(Dispatchers.Main) { codexProgress = "$stage（$pct%）" }
                delay(500)
            }
            val inst = updater.installed(context)
            val failed = updater.lastError()
            withContext(Dispatchers.Main) {
                codexBusy = false
                codexProgress = ""
                codexVersion = inst?.version.orEmpty()
                codexRemote = null
                codexMessage = if (failed.isBlank()) "已更新到 ${inst?.version.orEmpty()}"
                else "更新失败：$failed"
            }
        }
    }

    fun refreshWeb() {
        scope.launch(Dispatchers.IO) {
            val updater = h.Hchat.hooks.items.script.agent.CodexWebUpdater
            val server = h.Hchat.hooks.items.script.agent.CodexWebServer
            val guard = h.Hchat.hooks.items.script.agent.CodexWebGuard
            val installed = updater.isInstalled(context)
            val running = server.isRunning()
            val busy = updater.isBusy()
            val pct = updater.progress()
            val autoKeep = guard.isEnabled(context)
            val restarts = guard.restartCount()
            withContext(Dispatchers.Main) {
                webInstalled = installed
                webRunning = running
                webAutoKeep = autoKeep
                webRestarts = restarts
                if (busy) webMessage = "后台下载中 $pct%"
            }
        }
    }

    // 一次点击走完：没装就先下载运行环境 → 启动服务 → 打开浏览器
    fun openCodexWeb() {
        if (webBusy) return
        webBusy = true
        webMessage = ""
        scope.launch(Dispatchers.IO) {
            val updater = h.Hchat.hooks.items.script.agent.CodexWebUpdater
            val server = h.Hchat.hooks.items.script.agent.CodexWebServer
            val installed = updater.isInstalled(context)
            val release = updater.check(context).getOrNull()
            val needPrepare = !installed ||
                (release != null && updater.needsUpdate(context, release))
            if (needPrepare) {
                if (updater.isBusy()) {
                    // 上次没下完（下载在模块线程，关页面不中断），接着等它
                } else if (release == null) {
                    withContext(Dispatchers.Main) {
                        webBusy = false
                        webMessage = "获取运行环境清单失败，请检查插件仓库地址"
                    }
                    return@launch
                } else if (!updater.start(context, release)) {
                    withContext(Dispatchers.Main) {
                        webBusy = false
                        webMessage = "已有下载任务在进行"
                    }
                    return@launch
                }
                withContext(Dispatchers.Main) { webProgress = "准备下载" }
                // 只刷新进度，下载本身在后台线程；中途断了下次从断点续传
                while (updater.isBusy()) {
                    val pct = updater.progress()
                    val stage = updater.stage()
                    withContext(Dispatchers.Main) { webProgress = "$stage（$pct%）" }
                    delay(500)
                }
                if (!updater.isInstalled(context)) {
                    withContext(Dispatchers.Main) {
                        webBusy = false
                        webProgress = ""
                        webInstalled = false
                        webMessage = "安装失败：${updater.lastError().ifBlank { "下载未完成" }}"
                    }
                    return@launch
                }
            }
            withContext(Dispatchers.Main) {
                webInstalled = true
                webProgress = "正在启动服务…"
            }
            val started = server.start(context)
            if (started.isSuccess) {
                // 手动启动即视为要保活：不给用户留“下次还得自己点”的坑
                h.Hchat.hooks.items.script.agent.CodexWebGuard.enable(context)
            }
            withContext(Dispatchers.Main) {
                webBusy = false
                webProgress = ""
                webRunning = server.isRunning()
                if (started.isSuccess) {
                    webAutoKeep = true
                    webMessage = "已启动，正在打开浏览器"
                    server.openInBrowser(context)
                } else {
                    webMessage = "启动失败：${started.exceptionOrNull()?.message.orEmpty()}"
                }
            }
        }
    }

    fun stopCodexWeb() {
        if (webBusy) return
        scope.launch(Dispatchers.IO) {
            val server = h.Hchat.hooks.items.script.agent.CodexWebServer
            // 手动停止同时关掉保活，否则会被自动拉起
            h.Hchat.hooks.items.script.agent.CodexWebGuard.disable(context)
            server.stop(context)
            val running = server.isRunning()
            withContext(Dispatchers.Main) {
                webRunning = running
                webAutoKeep = false
                webMessage = "已停止服务"
            }
        }
    }

    // 进页面时读一次内置 codex 版本，并顺带查服务器有无新版（失败静默，不打扰用户）
    LaunchedEffect(Unit) { refreshCodex() }
    LaunchedEffect(Unit) { refreshWeb() }

    val installing = status.state ==
        h.Hchat.hooks.items.script.agent.ProotEnvironment.State.INSTALLING
    val ready = status.state ==
        h.Hchat.hooks.items.script.agent.ProotEnvironment.State.READY

    fun startInstall(force: Boolean) {
        if (installing) return
        scope.launch(Dispatchers.IO) {
            h.Hchat.hooks.items.script.agent.ProotEnvironment.install(context, force = force) { pct, msg ->
                scope.launch(Dispatchers.Main) {
                    status = h.Hchat.hooks.items.script.agent.ProotEnvironment.currentStatus()
                }
            }
            val done = h.Hchat.hooks.items.script.agent.ProotEnvironment.refreshStatus(context)
            withContext(Dispatchers.Main) {
                status = done
                refreshDisk()
            }
        }
    }

    if (showDeleteConfirm) {
        WindowDialog(
            show = true,
            title = "删除终端环境",
            onDismissRequest = { showDeleteConfirm = false },
            content = {
                Column {
                    Text(
                        text = "将删除已下载的 Ubuntu 环境并释放磁盘空间。下次使用需重新下载。",
                        color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                        fontSize = 13.sp
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        TextButton(
                            text = "取消",
                            onClick = { showDeleteConfirm = false },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.textButtonColorsPrimary()
                        )
                        TextButton(
                            text = "删除",
                            onClick = {
                                showDeleteConfirm = false
                                scope.launch(Dispatchers.IO) {
                                    h.Hchat.hooks.items.script.agent.TermuxSessions.closeAll(context)
                                    h.Hchat.hooks.items.script.agent.ProotEnvironment.uninstall(context)
                                    val s = h.Hchat.hooks.items.script.agent.ProotEnvironment.refreshStatus(context)
                                    withContext(Dispatchers.Main) {
                                        status = s
                                        refreshDisk()
                                    }
                                }
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.textButtonColors()
                        )
                    }
                }
            }
        )
    }

    SettingsCard {
        val stateSummary = when (status.state) {
            h.Hchat.hooks.items.script.agent.ProotEnvironment.State.READY ->
                "已就绪${if (diskText.isNotBlank()) "，$diskText" else ""}"
            h.Hchat.hooks.items.script.agent.ProotEnvironment.State.INSTALLING ->
                if (status.progress in 0..100) "安装中 ${status.progress}% · ${status.message}"
                else "安装中 · ${status.message}"
            h.Hchat.hooks.items.script.agent.ProotEnvironment.State.ERROR ->
                status.message.ifBlank { "安装失败" }
            h.Hchat.hooks.items.script.agent.ProotEnvironment.State.DEGRADED ->
                "降级模式，部分功能不可用"
            else -> "未安装，run_command 需要此环境（约 290MB 下载，已内置 codex 与 DexClub）"
        }
        InfoRow(
            label = "环境状态",
            value = stateSummary
        )
        InsetDivider()
        if (!ready) {
            ActionRow(
                if (installing) "正在安装…" else "安装 Ubuntu 终端",
                "首次需联网下载，完成后 Agent 可用 run_command"
            ) { if (!installing) startInstall(false) }
        } else {
            ActionRow("打开终端", "在 Ubuntu 环境里直接敲命令") { showTerminal = true }
            InsetDivider()
            ActionRow(
                title = if (webRunning) "codex 网页版 · 运行中" else "codex 网页版",
                summary = when {
                    webBusy -> webProgress.ifBlank { "准备中…" }
                    webMessage.isNotBlank() -> webMessage
                    !webInstalled -> "点按下载运行环境（约 214MB），之后在手机浏览器里用 codex"
                    webRunning -> "在手机浏览器里使用，点按直接打开"
                    else -> "点按启动并在浏览器里打开"
                }
            ) { openCodexWeb() }
            if (webInstalled && webRunning) {
                InsetDivider()
                ActionRow("停止网页版服务", "关闭后台运行的服务，释放内存") { stopCodexWeb() }
            }
            if (webInstalled) {
                InsetDivider()
                SwitchRow(
                    checked = webAutoKeep,
                    title = "后台保活",
                    summary = when {
                        !webAutoKeep -> "关闭后服务掉线需要手动重新启动"
                        webRunning && webRestarts > 0 -> "掉线自动重启，本次已恢复 $webRestarts 次"
                        webRunning -> "服务掉线自动重启，微信重启后自动恢复"
                        else -> "已开启，正在拉起服务…"
                    },
                    onCheckedChange = { on ->
                        webAutoKeep = on
                        scope.launch(Dispatchers.IO) {
                            val guard = h.Hchat.hooks.items.script.agent.CodexWebGuard
                            if (on) guard.enable(context) else guard.disable(context)
                            delay(600)
                            withContext(Dispatchers.Main) { refreshWeb() }
                        }
                    }
                )
            }
            InsetDivider()
            PopupChoiceRow(
                title = "终端字体",
                summary = "终端显示字体，未下载的需联网获取（每个约 200KB）",
                options = h.Hchat.hooks.items.script.agent.TerminalFonts.ALL.map {
                    PopupChoice(it.label, it.id)
                },
                currentValue = fontId,
                onValueChanged = { id ->
                    val fonts = h.Hchat.hooks.items.script.agent.TerminalFonts
                    val font = fonts.byId(id)
                    if (font.fileName.isEmpty() || fonts.isDownloaded(context, font)) {
                        fontId = id
                        fonts.select(context, id)
                    } else {
                        val activity = context as? Activity
                            ?: WeChatApis.currentActivity()?.currentActivity() as? Activity
                        if (activity != null) {
                            VoiceForwardMiuixDialog.showConfirm(
                                activity = activity,
                                title = "下载字体",
                                message = "「${font.label}」尚未下载（约 ${font.sizeKb}KB），需要联网下载后使用，是否下载？",
                                onResult = { ok ->
                                    if (ok) {
                                        fonts.download(context, font,
                                            onSuccess = {
                                                fontId = id
                                                fonts.select(context, id)
                                            },
                                            onError = { msg ->
                                                Toast.makeText(context, "字体下载失败：$msg", Toast.LENGTH_SHORT).show()
                                            })
                                    }
                                },
                                onDismiss = {}
                            )
                        }
                    }
                }
            )
            InsetDivider()
            ActionRow(
                title = when {
                    codexBusy -> "正在更新 codex…"
                    codexRemote != null -> "更新 codex 到 ${codexRemote?.version}"
                    codexVersion.isBlank() -> "内置 codex"
                    else -> "内置 codex ${codexVersion}"
                },
                summary = when {
                    codexBusy -> codexProgress.ifBlank { "准备中…" }
                    codexMessage.isNotBlank() -> codexMessage
                    codexVersion.isBlank() -> "点按检查服务器上的新版 codex"
                    else -> "点按检查更新"
                }
            ) {
                if (codexRemote != null) showCodexConfirm = true else checkCodexUpdate()
            }
            InsetDivider()
            ActionRow("重新安装", "重新下载并解压 Ubuntu 环境") {
                if (!installing) {
                    // 换 rootfs 前先关掉所有常驻会话（否则跑着的 shell 挂的是旧挂载，且旧 dexclub 会指向已删路径）
                    h.Hchat.hooks.items.script.agent.TermuxSessions.closeAll(context)
                    startInstall(true)
                }
            }
            InsetDivider()
            ActionRow("删除终端环境", "释放磁盘空间") { showDeleteConfirm = true }
        }
    }

    if (showCodexConfirm) {
        codexRemote?.let { release ->
            WindowDialog(
                show = true,
                title = "更新内置 codex ${release.version}",
                onDismissRequest = { showCodexConfirm = false },
                content = {
                    Column {
                        Text(
                            text = release.notes.ifBlank { "有新的 codex 版本可用。" },
                            color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                            fontSize = 13.sp
                        )
                        Text(
                            text = "包大小 ${formatFileSize(release.size)}，下载解压期间请勿退出微信。",
                            color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(top = 6.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            TextButton(
                                text = "取消",
                                onClick = { showCodexConfirm = false },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.textButtonColorsPrimary()
                            )
                            TextButton(
                                text = "更新",
                                onClick = { runCodexUpdate(release) },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.textButtonColors()
                            )
                        }
                    }
                }
            )
        }
    }

    if (showTerminal) {
        ScriptPluginAgentTerminalDialog(onDismiss = { showTerminal = false })
    }
}

@Composable
internal fun ScriptPluginAgentTerminalDialog(onDismiss: () -> Unit) {
    val context = LocalContext.current
    var ready by remember { mutableStateOf(h.Hchat.hooks.items.script.agent.ProotEnvironment.isReady(context)) }
    var upgrading by remember { mutableStateOf(false) }
    var upgradeText by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        val env = h.Hchat.hooks.items.script.agent.ProotEnvironment
        if (!env.needsUpgrade(context)) return@LaunchedEffect
        upgrading = true
        upgradeText = "正在准备终端环境…"
        h.Hchat.hooks.items.script.agent.TermuxSessions.closeAll(context)
        withContext(Dispatchers.IO) {
            env.install(context) { pct, msg -> upgradeText = "$msg $pct%" }
        }
        upgrading = false
        ready = env.isReady(context)
    }
    var pageRef by remember {
        mutableStateOf<h.Hchat.hooks.items.script.agent.TermuxTerminalPage?>(null)
    }
    val density = LocalDensity.current

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false,
        ),
    ) {
        val dialogView = LocalView.current
        SideEffect {
            var parent: android.view.ViewParent? = dialogView.parent
            while (parent != null) {
                val window = (parent as? DialogWindowProvider)?.window
                if (window != null) {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
                    window.setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING or
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED
                    )
                    window.setDimAmount(0f)
                    window.setBackgroundDrawable(android.graphics.drawable.ColorDrawable(0xFF000000.toInt()))
                    break
                }
                parent = parent.parent
            }
        }
        Box(modifier = Modifier.fillMaxSize().background(Color(0xFF000000))) {
            if (upgrading) {
                Text(
                    text = "$upgradeText\n正在更新终端环境（内置 codex 与 DexClub，约 290MB），完成后自动进入终端。",
                    color = Color(0xFF9AA0A6),
                    fontSize = 13.sp,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.padding(20.dp)
                )
            } else if (!ready) {
                Text(
                    text = "Ubuntu 终端环境未安装。\n请先在「Agent 配置 → Ubuntu 终端环境」里点安装，完成后再打开终端。",
                    color = Color(0xFF9AA0A6),
                    fontSize = 13.sp,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.padding(20.dp)
                )
            } else {
                AndroidView(
                    factory = { ctx ->
                        h.Hchat.hooks.items.script.agent.TermuxTerminalPage(ctx).also { page ->
                            pageRef = page
                            // ☰ →「关闭终端」：结束全部会话后由视图请求关掉对话框
                            page.setOnExitRequested { onDismiss() }
                            page.start()
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
                val barsTopPx = with(density) {
                    WindowInsets.statusBars.asPaddingValues().calculateTopPadding().roundToPx()
                }
                val imeBottom = WindowInsets.ime.asPaddingValues().calculateBottomPadding()
                val bottomPx = with(density) { imeBottom.roundToPx() }
                SideEffect {
                    pageRef?.applyInsetsFromCompose(barsTopPx, bottomPx, imeBottom > 0.dp)
                }
            }
            DisposableEffect(Unit) {
                onDispose { pageRef?.detach() }
            }
        }
    }
}

@Composable
internal fun ScriptPluginAgentEndpointPreview(endpoint: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)) {
        Text(
            text = "实际请求地址",
            color = MiuixTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = endpoint,
            color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
internal fun ScriptPluginAgentReasoningRow(
    endpointMode: String,
    model: String,
    effort: String,
    onValueChanged: (String) -> Unit
) {
    val options = ScriptPluginAgentReasoning.options(endpointMode, model)
    PopupChoiceRow(
        title = "推理强度",
        summary = ScriptPluginAgentReasoning.description(endpointMode, model),
        options = options.map { (value, label) -> PopupChoice(label, value) },
        currentValue = ScriptPluginAgentReasoning.effectiveEffort(endpointMode, model, effort),
        onValueChanged = onValueChanged
    )
}

@Composable
internal fun CodexModelPickerPage(
    baseUrl: String,
    apiKey: String,
    currentModel: String,
    currentEffort: String,
    onSetModel: (String, String) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val scrollBehavior = MiuixScrollBehavior()
    var models by remember(baseUrl, apiKey) { mutableStateOf<List<String>>(emptyList()) }
    var pendingModel by remember(currentModel) { mutableStateOf(currentModel) }
    var pendingEffort by remember(currentEffort) { mutableStateOf(currentEffort) }
    var query by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var resultText by remember { mutableStateOf("") }
    val visibleModels = remember(models, query) {
        val keyword = query.trim()
        if (keyword.isBlank()) models else models.filter { it.contains(keyword, ignoreCase = true) }
    }

    fun loadModels() {
        if (loading) return
        loading = true
        resultText = "正在拉取模型"
        scope.launch {
            val outcome = withContext(Dispatchers.IO) { CodexSettings.fetchModels(baseUrl, apiKey) }
            loading = false
            outcome.onSuccess { fetched ->
                models = fetched
                resultText = if (fetched.isEmpty()) "未获取到模型，可手动填写" else "已获取 ${fetched.size} 个模型"
            }.onFailure {
                resultText = "拉取失败：" + (it.message ?: "未知错误")
            }
        }
    }

    PageScaffold(
        title = "选择模型",
        largeTitle = "选择模型",
        scrollBehavior = scrollBehavior,
        bottomBar = {
            BottomActionBar(
                primaryText = "使用该模型",
                onPrimaryClick = {
                    val selected = pendingModel.trim()
                    if (selected.isBlank()) {
                        Toast.makeText(context, "请输入或选择模型", Toast.LENGTH_SHORT).show()
                    } else {
                        onSetModel(selected, CodexSettings.normalizeEffort(pendingEffort))
                    }
                },
                secondaryText = "返回",
                onSecondaryClick = onBack
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            state = listState,
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 8.dp,
                bottom = padding.calculateBottomPadding() + 84.dp
            )
        ) {
            item {
                SettingsCard {
                    InputRow(
                        "模型",
                        "可直接手动填写，不依赖拉取结果",
                        pendingModel,
                        onValueChange = { pendingModel = it }
                    )
                    InsetDivider()
                    PopupChoiceRow(
                        title = "思考程度",
                        summary = ScriptPluginAgentReasoning.label(pendingEffort),
                        options = CodexSettings.EFFORT_OPTIONS.map { PopupChoice(it.second, it.first) },
                        currentValue = pendingEffort,
                        onValueChanged = { pendingEffort = it }
                    )
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "从列表选择（可选）") }
            item {
                SettingsCard {
                    ActionRow(
                        "拉取模型列表",
                        resultText.ifBlank { "从当前 API 地址获取模型列表" }
                    ) { loadModels() }
                    if (models.isNotEmpty()) {
                        InsetDivider()
                        InputRow("搜索", "输入模型名称", query, onValueChange = { query = it })
                    }
                }
            }
            visibleModels.take(300).forEach { modelName ->
                item(key = modelName) {
                    SettingsCard(modifier = Modifier.padding(top = 6.dp)) {
                        ActionRow(
                            title = modelName,
                            summary = when {
                                modelName == pendingModel.trim() -> "已选择"
                                modelName == currentModel -> "当前模型"
                                else -> ""
                            }
                        ) { pendingModel = modelName }
                    }
                }
            }
        }
    }
}

@Composable
internal fun ScriptPluginAgentModelPickerPage(
    config: ScriptPluginAgentConfig,
    currentModel: String,
    onSelected: (String, String) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val scrollBehavior = MiuixScrollBehavior()
    var models by remember(config.apiBaseUrl, config.apiKey, config.endpointMode) {
        mutableStateOf<List<String>>(emptyList())
    }
    var pendingModel by remember(currentModel) { mutableStateOf(currentModel) }
    var pendingReasoningEffort by remember(config.reasoningEffort) {
        mutableStateOf(config.reasoningEffort)
    }
    var query by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var loadError by remember { mutableStateOf("") }
    val visibleModels = remember(models, query) {
        val keyword = query.trim()
        if (keyword.isBlank()) models else models.filter { it.contains(keyword, ignoreCase = true) }
    }

    fun loadModels() {
        if (loading) return
        loading = true
        loadError = ""
        scope.launch {
            val result = withContext(Dispatchers.IO) { ScriptPluginAgentClient.fetchModels(config) }
            loading = false
            result.onSuccess { fetched ->
                models = fetched
                if (fetched.isEmpty()) loadError = "未获取到模型"
            }.onFailure {
                loadError = it.message ?: "拉取模型失败"
            }
        }
    }

    PageScaffold(
        title = "模型选择",
        largeTitle = "模型选择",
        scrollBehavior = scrollBehavior,
        bottomBar = {
            BottomActionBar(
                primaryText = "应用模型设置",
                onPrimaryClick = {
                    val selectedModel = pendingModel.trim()
                    if (selectedModel.isBlank()) {
                        Toast.makeText(context, "请输入或选择模型 ID", Toast.LENGTH_SHORT).show()
                    } else {
                        onSelected(
                            selectedModel,
                            ScriptPluginAgentReasoning.effectiveEffort(
                                config.endpointMode, selectedModel, pendingReasoningEffort
                            )
                        )
                    }
                },
                secondaryText = "返回",
                onSecondaryClick = onBack
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            state = listState,
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 8.dp,
                bottom = padding.calculateBottomPadding() + 84.dp
            )
        ) {
            item {
                SettingsCard {
                    InputRow(
                        "模型 ID",
                        "可手动输入；列表获取失败或未包含该模型时仍可使用",
                        pendingModel,
                        onValueChange = { pendingModel = it }
                    )
                    InsetDivider()
                    ScriptPluginAgentReasoningRow(
                        config.endpointMode,
                        pendingModel,
                        pendingReasoningEffort,
                        onValueChanged = { pendingReasoningEffort = it }
                    )
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "从列表选择（可选）") }
            item {
                SettingsCard {
                    ActionRow(
                        "获取模型列表",
                        when {
                            loading -> "正在拉取模型"
                            loadError.isNotBlank() -> loadError
                            models.isEmpty() -> "按需从当前接口获取，不影响手动输入"
                            else -> "已获取 ${models.size} 个模型"
                        }
                    ) { loadModels() }
                    if (models.isNotEmpty()) {
                        InsetDivider()
                        InputRow("搜索", "输入模型名称", query) { query = it }
                    }
                }
            }
            visibleModels.take(300).forEach { modelName ->
                item(key = modelName) {
                    SettingsCard(modifier = Modifier.padding(top = 6.dp)) {
                        ActionRow(
                            title = modelName,
                            summary = when {
                                modelName == pendingModel.trim() -> "已选择"
                                modelName == currentModel -> "当前模型"
                                else -> ""
                            }
                        ) { pendingModel = modelName }
                    }
                }
            }
        }
    }
}

internal fun scriptPluginAgentRenderedMessages(
    messages: List<ScriptPluginAgentChatMessage>
): List<ScriptPluginAgentRenderedMessage> {
    val firstToolIndexByTurn = HashMap<String, Int>()
    messages.forEachIndexed { index, message ->
        if (message.role == "tool" && message.turnId.isNotBlank()) {
            firstToolIndexByTurn.putIfAbsent(message.turnId, index)
        }
    }
    val eventsByTurn = messages.asSequence()
        .filter { it.role == "tool" && it.turnId.isNotBlank() }
        .groupBy { it.turnId }
        .mapValues { (_, groups) -> groups.flatMap { it.toolEvents }.distinctBy { it.id } }
    return buildList {
        messages.forEachIndexed { index, message ->
            if (message.role != "tool" || message.turnId.isBlank()) {
                add(ScriptPluginAgentRenderedMessage(index, message))
                return@forEachIndexed
            }
            if (firstToolIndexByTurn[message.turnId] != index) return@forEachIndexed
            val events = eventsByTurn[message.turnId].orEmpty()
            val hasPending = events.any { it.status == "queued" || it.status == "running" }
            val wasInterrupted = events.any { it.status == "interrupted" }
            add(
                ScriptPluginAgentRenderedMessage(
                    sourceIndex = index,
                    message = message.copy(
                        toolEvents = events,
                        status = when {
                            hasPending -> "streaming"
                            wasInterrupted -> "interrupted"
                            else -> "complete"
                        },
                        completedAt = events.maxOfOrNull { it.finishedAt } ?: message.completedAt
                    )
                )
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun ScriptPluginAgentChatPage(
    title: String,
    messages: List<ScriptPluginAgentChatMessage>,
    messageInput: String,
    pendingAttachments: List<ScriptPluginAgentAttachment>,
    pendingQuotedMessage: ScriptPluginAgentQuotedMessage?,
    pendingMessages: List<ScriptPluginAgentPendingMessage>,
    pendingMessagesExpanded: Boolean,
    activeProfileName: String,
    activeModel: String,
    enabled: Boolean,
    inputEnabled: Boolean,
    generating: Boolean,
    contextCompacting: Boolean,
    workingStartedAt: Long,
    workingLabel: String,
    showWorking: Boolean,
    pendingConfirmationLabel: String?,
    onMessageInputChange: (String) -> Unit,
    onSend: () -> Unit,
    onQueue: () -> Unit,
    onCancel: () -> Unit,
    onAttach: () -> Unit,
    onRemoveAttachment: (String) -> Unit,
    onClearQuotedMessage: () -> Unit,
    onPendingMessagesExpandedChange: (Boolean) -> Unit,
    onEditPendingMessage: (Long) -> Unit,
    onDeletePendingMessage: (Long) -> Unit,
    onSendPendingMessageNow: (Long) -> Unit,
    onOpenProfilePicker: () -> Unit,
    onOpenModelPicker: () -> Unit,
    onOpenQuickOptions: () -> Unit,
    onOpenPendingConfirmation: () -> Unit,
    onCompact: () -> Unit,
    onOpenHistory: () -> Unit,
    onOpenConfig: () -> Unit,
    onEditAndResend: (Int) -> Unit,
    onQuoteMessage: (Int) -> Unit,
    onContinueMessage: (Int) -> Unit,
    onRegenerateMessage: (Int) -> Unit,
    onRollback: (String) -> Unit,
    onDeleteMessage: (Int) -> Unit,
    onCreateBranch: (Int) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val scrollBehavior = MiuixScrollBehavior()
    val imeVisible = WindowInsets.isImeVisible
    var actionMessageId by remember { mutableStateOf<String?>(null) }
    var infoMessageId by remember { mutableStateOf<String?>(null) }
    var showScrollToBottom by remember { mutableStateOf(false) }
    var autoFollowOutput by remember { mutableStateOf(true) }
    var previousRenderedMessageCount by remember { mutableStateOf(-1) }
    val actionMessage = actionMessageId?.let { id -> messages.firstOrNull { it.id == id } }
    val infoMessage = infoMessageId?.let { id -> messages.firstOrNull { it.id == id } }
    val renderedMessages = remember(messages) {
        scriptPluginAgentRenderedMessages(messages)
    }
    var showTerminal by remember { mutableStateOf(false) }
    if (showTerminal) {
        ScriptPluginAgentTerminalDialog(onDismiss = { showTerminal = false })
    }

    fun consumeActionMessageIndex(): Int? {
        val messageId = actionMessageId
        actionMessageId = null
        return messageId?.let { id -> messages.indexOfFirst { it.id == id }.takeIf { it >= 0 } }
    }

    DisposableEffect(context) {
        val window = findAgentActivity(context)?.window
        val originalSoftInputMode = window?.attributes?.softInputMode
        if (window != null && originalSoftInputMode != null) {
            val state = originalSoftInputMode and WindowManager.LayoutParams.SOFT_INPUT_MASK_STATE
            window.setSoftInputMode(state or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }
        onDispose {
            if (window != null && originalSoftInputMode != null) {
                window.setSoftInputMode(originalSoftInputMode)
            }
        }
    }

    if (actionMessage != null) {
        ScriptPluginAgentMessageMenu(
            message = actionMessage,
            onDismiss = { actionMessageId = null },
            onCopy = {
                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? android.content.ClipboardManager
                clipboard?.setPrimaryClip(ClipData.newPlainText("插件 Agent 消息", actionMessage.content))
                Toast.makeText(context, "消息已复制", Toast.LENGTH_SHORT).show()
                actionMessageId = null
            },
            onReadAloud = {
                ScriptPluginAgentSpeech.speak(context, actionMessage.content)
                Toast.makeText(context, "开始朗读", Toast.LENGTH_SHORT).show()
                actionMessageId = null
            },
            onQuote = {
                val index = consumeActionMessageIndex()
                if (index != null) onQuoteMessage(index)
            },
            onRegenerate = if (actionMessage.role == "assistant" && actionMessage.status != "streaming") {
                {
                    val index = consumeActionMessageIndex()
                    if (index != null) onRegenerateMessage(index)
                }
            } else {
                null
            },
            onEditAndResend = if (actionMessage.role == "user") {
                {
                    val index = consumeActionMessageIndex()
                    if (index != null) onEditAndResend(index)
                }
            } else {
                null
            },
            onRollback = {
                val messageId = actionMessageId
                actionMessageId = null
                if (messageId != null) {
                    val renderedIndex = renderedMessages.indexOfFirst { it.message.id == messageId }
                    if (renderedIndex >= 0) listState.requestScrollToItem(renderedIndex)
                    onRollback(messageId)
                }
            },
            onDelete = {
                val index = consumeActionMessageIndex()
                if (index != null) onDeleteMessage(index)
            },
            onCreateBranch = {
                val index = consumeActionMessageIndex()
                if (index != null) onCreateBranch(index)
            },
            onInfo = {
                infoMessageId = actionMessageId
                actionMessageId = null
            }
        )
    }
    if (infoMessage != null) {
        ScriptPluginAgentMessageInfoDialog(
            message = infoMessage,
            onDismiss = { infoMessageId = null }
        )
    }
    LaunchedEffect(renderedMessages.size) {
        val previousCount = previousRenderedMessageCount
        previousRenderedMessageCount = renderedMessages.size
        if (messages.isNotEmpty()) {
            autoFollowOutput = true
            if (previousCount >= 0 && renderedMessages.size < previousCount) {
                listState.requestScrollToItem(renderedMessages.size)
            } else {
                delay(80L)
                listState.animateScrollToLatest(renderedMessages.size)
            }
        }
    }
    LaunchedEffect(
        messages.lastOrNull()?.content,
        messages.lastOrNull()?.reasoning,
        messages.lastOrNull()?.toolEvents?.size,
        messages.lastOrNull()?.progress
    ) {
        if (messages.isNotEmpty() && autoFollowOutput) {
            delay(40L)
            listState.scrollToLatest(renderedMessages.size)
        }
    }
    LaunchedEffect(listState, messages.isNotEmpty()) {
        snapshotFlow { listState.isScrollInProgress to listState.isNearBottom() }.collect { (scrolling, atBottom) ->
            if (atBottom) autoFollowOutput = true
            else if (scrolling) autoFollowOutput = false
            showScrollToBottom = !atBottom && messages.isNotEmpty()
        }
    }
    LaunchedEffect(imeVisible) {
        if (imeVisible && autoFollowOutput && messages.isNotEmpty()) {
            delay(180L)
            listState.scrollToLatest(renderedMessages.size)
        }
    }
    LaunchedEffect(contextCompacting) {
        if (contextCompacting && !generating && messages.isNotEmpty()) {
            autoFollowOutput = true
            delay(40L)
            listState.animateScrollToLatest(renderedMessages.size)
        }
    }
    PageScaffold(
        title = title.ifBlank { "插件 Agent" },
        largeTitle = "",
        scrollBehavior = scrollBehavior,
        onBack = onBack,
        topBarActions = {
            Image(
                imageVector = NavIcons.Terminal,
                contentDescription = "Ubuntu 终端",
                colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.onSurface),
                modifier = Modifier.size(24.dp).responsiveTap(onClick = { showTerminal = true })
            )
            Image(
                imageVector = NavIcons.Compact,
                contentDescription = "压缩上下文",
                colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.onSurface),
                modifier = Modifier.padding(start = 14.dp).size(24.dp).responsiveTap(onClick = onCompact)
            )
            Image(
                imageVector = NavIcons.History,
                contentDescription = "会话历史",
                colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.onSurface),
                modifier = Modifier.padding(start = 14.dp).size(24.dp).responsiveTap(onClick = onOpenHistory)
            )
            Image(
                imageVector = NavIcons.Settings,
                contentDescription = "Agent 配置",
                colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.onSurface),
                modifier = Modifier.padding(start = 14.dp).size(24.dp).responsiveTap(onClick = onOpenConfig)
            )
        },
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().imePadding()) {
            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection),
                    state = listState,
                    verticalArrangement = Arrangement.Bottom,
                    contentPadding = PaddingValues(
                        top = padding.calculateTopPadding() + 8.dp,
                        bottom = 8.dp
                    )
                ) {
                    if (messages.isEmpty()) {
                        item {
                            Text(
                                text = "开始一个新会话",
                                color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                                fontSize = 14.sp,
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 28.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        renderedMessages.forEach { rendered ->
                            val index = rendered.sourceIndex
                            val message = rendered.message
                            item(key = message.id) {
                                ScriptPluginAgentMessageBlock(
                                    message = message,
                                    showWorking = showWorking && index == messages.lastIndex,
                                    workingStartedAt = workingStartedAt,
                                    workingLabel = workingLabel,
                                    longPressEnabled = enabled && message.role != "tool",
                                    onLongPress = { actionMessageId = message.id },
                                    onContinue = if (
                                        !generating &&
                                        (message.status == "interrupted" || message.status == "error") &&
                                        messages.drop(index + 1).none { it.role == "user" }
                                    ) {
                                        { onContinueMessage(index) }
                                    } else null,
                                    onRetry = if (
                                        !generating && message.role == "assistant" &&
                                        (message.status == "interrupted" || message.status == "error")
                                    ) {
                                        { onRegenerateMessage(index) }
                                    } else null
                                )
                            }
                        }
                        if (contextCompacting && !generating) {
                            item(key = "agent-context-compaction") {
                                Row(
                                    modifier = Modifier.fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 10.dp)
                                ) {
                                    ScriptPluginAgentWorkingIndicator(
                                        startedAt = workingStartedAt,
                                        label = workingLabel
                                    )
                                }
                            }
                        }
                        item(key = "agent-message-tail") {
                            Spacer(modifier = Modifier.height(1.dp))
                        }
                    }
                }
                if (showScrollToBottom && messages.isNotEmpty()) {
                    Image(
                        imageVector = NavIcons.MoveDown,
                        contentDescription = "回到底部",
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier.align(Alignment.BottomEnd)
                            .padding(end = 16.dp, bottom = 8.dp)
                            .size(36.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(MiuixTheme.colorScheme.primary)
                            .clickable {
                                showScrollToBottom = false
                                autoFollowOutput = true
                                scope.launch { listState.animateScrollToLatest(renderedMessages.size) }
                            }
                            .padding(7.dp)
                    )
                }
            }
            if (pendingConfirmationLabel != null) {
                ScriptPluginAgentPendingConfirmationBar(
                    label = pendingConfirmationLabel,
                    onClick = onOpenPendingConfirmation
                )
            }
            Box(
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = padding.calculateBottomPadding())
            ) {
                ScriptPluginAgentComposer(
                    value = messageInput,
                    attachments = pendingAttachments,
                    quotedMessage = pendingQuotedMessage,
                    pendingMessages = pendingMessages,
                    pendingMessagesExpanded = pendingMessagesExpanded,
                    activeProfileName = activeProfileName,
                    activeModel = activeModel,
                    enabled = inputEnabled,
                    generating = generating || contextCompacting,
                    onValueChange = onMessageInputChange,
                    onSend = onSend,
                    onQueue = onQueue,
                    onCancel = onCancel,
                    onAttach = onAttach,
                    onRemoveAttachment = onRemoveAttachment,
                    onClearQuotedMessage = onClearQuotedMessage,
                    onPendingMessagesExpandedChange = onPendingMessagesExpandedChange,
                    onEditPendingMessage = onEditPendingMessage,
                    onDeletePendingMessage = onDeletePendingMessage,
                    onSendPendingMessageNow = onSendPendingMessageNow,
                    onOpenProfilePicker = onOpenProfilePicker,
                    onOpenModelPicker = onOpenModelPicker,
                    onOpenQuickOptions = onOpenQuickOptions
                )
            }
        }
    }
}

@Composable
internal fun ScriptPluginAgentAlwaysAllowRow(
    checked: Boolean,
    summary: String,
    onCheckedChange: (Boolean) -> Unit
) {
    SwitchRow(
        checked = checked,
        title = "始终允许",
        summary = summary,
        onCheckedChange = onCheckedChange
    )
}

@Composable
internal fun ScriptPluginAgentPendingConfirmationBar(label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .background(MiuixTheme.colorScheme.surfaceVariant)
            .responsiveTap(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector = NavIcons.Modules,
            contentDescription = null,
            colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.primary),
            modifier = Modifier.size(20.dp)
        )
        Column(modifier = Modifier.padding(start = 10.dp).weight(1f)) {
            Text(
                text = "待确认的插件修改",
                color = MiuixTheme.colorScheme.onSurface,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = label,
                color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                fontSize = 11.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Text(
            text = "查看 Diff",
            color = MiuixTheme.colorScheme.primary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}

internal suspend fun LazyListState.animateScrollToLatest(index: Int) {
    animateScrollToItem(index)
}

internal suspend fun LazyListState.scrollToLatest(index: Int) {
    scrollToItem(index)
}

internal fun LazyListState.isNearBottom(thresholdPx: Int = 120): Boolean {
    val info = layoutInfo
    if (info.totalItemsCount == 0) return true
    val lastVisible = info.visibleItemsInfo.lastOrNull() ?: return true
    if (lastVisible.index < info.totalItemsCount - 1) return false
    return lastVisible.offset + lastVisible.size <= info.viewportEndOffset + thresholdPx
}

@Composable
internal fun ScriptPluginAgentMessageBlock(
    message: ScriptPluginAgentChatMessage,
    showWorking: Boolean,
    workingStartedAt: Long,
    workingLabel: String,
    longPressEnabled: Boolean,
    onLongPress: () -> Unit,
    onContinue: (() -> Unit)?,
    onRetry: (() -> Unit)?
) {
    if (message.phase == "assistant_tool_call" && message.content.isBlank()) return
    val context = LocalContext.current
    val waitingForReply = message.role == "assistant" &&
        message.status == "streaming" &&
        message.content.isBlank()
    if (waitingForReply && !showWorking && message.reasoning.isBlank() && message.toolEvents.isEmpty()) return
    val longPressModifier = Modifier.agentMessageLongPress(
        key = message.id.hashCode().toLong(),
        enabled = longPressEnabled,
        onLongPress = onLongPress
    )
    if (message.role == "user") {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Box(
                modifier = Modifier
                    .then(longPressModifier)
                    .clip(RoundedCornerShape(18.dp))
                    .background(MiuixTheme.colorScheme.primary.copy(alpha = 0.14f))
                    .padding(horizontal = 14.dp, vertical = 10.dp)
            ) {
                Column {
                    message.quotedMessage?.let { quoted ->
                        ScriptPluginAgentQuotedMessagePreview(
                            quotedMessage = quoted,
                            maxLines = 3,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    Text(text = message.content, color = MiuixTheme.colorScheme.onSurface, fontSize = 14.sp)
                    if (message.attachments.isNotEmpty()) {
                        Text(
                            text = message.attachments.joinToString("\n") { "附件: ${it.name}" },
                            color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 6.dp)
                        )
                    }
                }
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxWidth().then(longPressModifier)
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            if (waitingForReply) {
                if (showWorking) {
                    ScriptPluginAgentWorkingIndicator(
                        startedAt = workingStartedAt.takeIf { it > 0L } ?: message.createdAt,
                        label = workingLabel
                    )
                }
            } else if (message.role == "tool") {
                Text(
                    text = "工具调用",
                    color = MiuixTheme.colorScheme.primary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            ScriptPluginAgentReasoningGroup(message)
            ScriptPluginAgentToolGroup(message)
            if (message.role != "tool" && message.content.isBlank() && !waitingForReply) {
                Text(
                    text = when (message.status) {
                        "interrupted" -> "已中断，尚未收到回复"
                        else -> "本轮没有返回可显示内容"
                    },
                    color = MiuixTheme.colorScheme.onSurface,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = if (message.toolEvents.isNotEmpty()) 8.dp else 4.dp)
                )
            } else if (message.role != "tool") {
                if (message.status == "streaming") {
                    Text(
                        text = message.content,
                        color = MiuixTheme.colorScheme.onSurface,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                } else {
                    MarkdownUi.Content(
                        context = context,
                        markdown = message.content,
                        contentPadding = PaddingValues(top = if (message.toolEvents.isNotEmpty()) 8.dp else 4.dp),
                        bodyFontSize = 14.sp,
                        onCopyCode = { code ->
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? android.content.ClipboardManager
                            clipboard?.setPrimaryClip(ClipData.newPlainText("Agent 代码", code))
                            Toast.makeText(context, "代码已复制", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
            if (message.status == "interrupted" || message.status == "error") {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (message.status == "interrupted") "已中断" else "请求失败",
                        color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                        fontSize = 12.sp,
                        modifier = Modifier.weight(1f)
                    )
                    if (onContinue != null) {
                        TextButton(
                            text = "继续任务",
                            onClick = onContinue,
                            minWidth = 0.dp,
                            minHeight = 30.dp,
                            insideMargin = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                            colors = ButtonDefaults.textButtonColorsPrimary()
                        )
                    }
                    if (onRetry != null) {
                        TextButton(
                            text = "重新开始",
                            onClick = onRetry,
                            minWidth = 0.dp,
                            minHeight = 30.dp,
                            insideMargin = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                            colors = ButtonDefaults.textButtonColorsPrimary()
                        )
                    }
                }
            }
            if (message.role != "tool" && message.diff.isNotBlank()) {
                ScriptPluginAgentDiffGroup("message:${message.createdAt}", message.diff)
            }
        }
    }
}

internal fun isScriptPluginAgentControlPreview(content: String): Boolean {
    return content.trim().startsWith("准备调用")
}

@Composable
internal fun ScriptPluginAgentWorkingIndicator(startedAt: Long, label: String = "") {
    var elapsedSeconds by remember(startedAt) {
        mutableStateOf(((System.currentTimeMillis() - startedAt).coerceAtLeast(0L) / 1_000L))
    }
    LaunchedEffect(startedAt) {
        while (true) {
            elapsedSeconds = (System.currentTimeMillis() - startedAt).coerceAtLeast(0L) / 1_000L
            delay(1_000L)
        }
    }
    val elapsedText = if (elapsedSeconds < 60L) {
        "${elapsedSeconds}s"
    } else {
        "${elapsedSeconds / 60L}m ${elapsedSeconds % 60L}s"
    }
    Text(
        text = buildString {
            append("Working (").append(elapsedText).append(')')
            if (label.isNotBlank()) append(" · ").append(label)
        },
        color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium
    )
}

fun findAgentActivity(context: Context): Activity? {
    var current: Context? = context
    while (current is ContextWrapper) {
        if (current is Activity) return current
        current = current.baseContext
    }
    return current as? Activity
}

@Composable
internal fun ScriptPluginAgentReasoningGroup(message: ScriptPluginAgentChatMessage) {
    val context = LocalContext.current
    val reasoning = message.reasoning
    if (reasoning.isEmpty()) return
    var expanded by rememberSaveable(message.createdAt, "reasoning") {
        mutableStateOf(false)
    }
    val rotation by animateFloatAsState(
        targetValue = if (expanded) 90f else 0f,
        animationSpec = tween(220),
        label = "agentReasoningArrow"
    )
    Column(modifier = Modifier.fillMaxWidth().padding(top = 5.dp).animateContentSize()) {
        Row(
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(6.dp))
                .responsiveTap(onClick = {
                    expanded = !expanded
                })
                .padding(vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = NavIcons.Expand,
                contentDescription = if (expanded) "收起思考过程" else "展开思考过程",
                colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.onSurfaceVariantSummary),
                modifier = Modifier.size(18.dp).graphicsLayer { rotationZ = rotation }
            )
            Text(
                text = if (message.status == "streaming") "思考中" else "思考过程",
                color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 7.dp)
            )
        }
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn(tween(160)),
            exit = fadeOut(tween(120))
        ) {
            MarkdownUi.Text(
                context = context,
                text = reasoning,
                color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth().padding(start = 25.dp, bottom = 6.dp)
            )
        }
    }
}

@Composable
internal fun ScriptPluginAgentQuotedMessagePreview(
    quotedMessage: ScriptPluginAgentQuotedMessage,
    maxLines: Int,
    modifier: Modifier = Modifier,
    onClear: (() -> Unit)? = null
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(MiuixTheme.colorScheme.onSurface.copy(alpha = 0.05f))
            .padding(start = 8.dp, top = 6.dp, end = if (onClear == null) 8.dp else 4.dp, bottom = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = if (quotedMessage.role == "assistant") "引用 Agent" else "引用用户",
                color = MiuixTheme.colorScheme.primary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = quotedMessage.content,
                color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                fontSize = 11.sp,
                maxLines = maxLines,
                overflow = TextOverflow.Ellipsis
            )
        }
        if (onClear != null) {
            Image(
                imageVector = NavIcons.Close,
                contentDescription = "取消引用",
                colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.onSurfaceVariantSummary),
                modifier = Modifier.padding(start = 6.dp).size(24.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .clickable(onClick = onClear)
                    .padding(5.dp)
            )
        }
    }
}

@Composable
internal fun ScriptPluginAgentToolGroup(message: ScriptPluginAgentChatMessage) {
    val context = LocalContext.current
    if (message.toolEvents.isEmpty()) return
    val streaming = message.status == "streaming"
    var expanded by rememberSaveable(message.createdAt) { mutableStateOf(streaming) }
    var userOverride by rememberSaveable(message.createdAt, "work-override") {
        mutableStateOf<Boolean?>(null)
    }
    var detailEvent by remember(message.createdAt) { mutableStateOf<ScriptPluginAgentToolEvent?>(null) }
    detailEvent?.let { event ->
        ScriptPluginAgentToolEventDialog(
            event = event,
            onCopy = { value ->
                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? android.content.ClipboardManager
                clipboard?.setPrimaryClip(ClipData.newPlainText(event.name, value))
                Toast.makeText(context, "已复制", Toast.LENGTH_SHORT).show()
            },
            onDismiss = { detailEvent = null }
        )
    }
    LaunchedEffect(streaming) {
        if (userOverride == null) expanded = streaming
    }
    val rotation by animateFloatAsState(
        targetValue = if (expanded) 90f else 0f,
        animationSpec = tween(250),
        label = "agentWorkArrow"
    )
    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 5.dp).animateContentSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(6.dp))
                .responsiveTap(onClick = {
                    expanded = !expanded
                    userOverride = expanded
                })
                .padding(vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = NavIcons.Expand,
                contentDescription = if (expanded) "收起" else "展开",
                colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.onSurfaceVariantSummary),
                modifier = Modifier.size(18.dp).graphicsLayer { rotationZ = rotation }
            )
            Text(
                text = "工具调用（${message.toolEvents.size}）",
                color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 7.dp)
            )
        }
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn(tween(180)),
            exit = fadeOut(tween(140))
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(start = 24.dp, bottom = 6.dp)) {
                message.toolEvents.forEach { event ->
                    ScriptPluginAgentToolEventRow(event) { detailEvent = event }
                }
            }
        }
    }
}

@Composable
internal fun ScriptPluginAgentToolEventRow(
    event: ScriptPluginAgentToolEvent,
    onClick: () -> Unit
) {
    val workspaceWrite = isScriptPluginAgentWorkspaceWriteEvent(event)
    val icon = when (event.kind) {
        "search" -> NavIcons.Search
        "plugin", "workspace" -> NavIcons.Modules
        else -> NavIcons.Compact
    }
    val statusText = when (event.status) {
        "queued" -> "排队"
        "running" -> "进行中"
        "success" -> "完成"
        "interrupted" -> "已中断"
        else -> "失败"
    }
    val durationMs = if (event.startedAt > 0L && event.finishedAt >= event.startedAt) {
        event.finishedAt - event.startedAt
    } else {
        0L
    }
    val durationText = if (durationMs >= 1_000L) {
        "${durationMs / 1_000L}.${(durationMs % 1_000L) / 100}s"
    } else if (durationMs > 0L) {
        "${durationMs}ms"
    } else {
        ""
    }
    val canOpen = if (workspaceWrite) {
        event.diff.isNotBlank()
    } else {
        event.arguments.isNotBlank() || event.diff.isNotBlank() || event.result.isNotBlank()
    }
    Column(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(6.dp))
            .then(if (canOpen) Modifier.responsiveTap(onClick = onClick) else Modifier)
            .padding(vertical = 5.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                imageVector = icon,
                contentDescription = null,
                colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.primary),
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = event.name,
                color = MiuixTheme.colorScheme.primary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 8.dp).weight(1f)
            )
            Text(
                text = listOf(statusText, durationText).filter { it.isNotBlank() }.joinToString(" "),
                color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                fontSize = 11.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        if (workspaceWrite) {
            if (event.progress.isNotBlank() && event.status == "running") {
                Text(
                    text = event.progress,
                    color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                    fontSize = 11.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth().padding(start = 26.dp, top = 2.dp)
                )
            }
            return@Column
        }
        if (event.arguments.isNotBlank()) {
            Text(
                text = scriptPluginAgentToolPreview(event.arguments),
                color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth().padding(start = 26.dp, top = 2.dp)
            )
        }
        if (event.progress.isNotBlank() && event.status == "running") {
            Text(
                text = event.progress,
                color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                fontSize = 11.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth().padding(start = 26.dp, top = 2.dp)
            )
        }
        if (event.diff.isNotBlank()) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 26.dp, top = 3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    imageVector = NavIcons.Modules,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.primary),
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = "查看 Diff",
                    color = MiuixTheme.colorScheme.primary,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(start = 6.dp)
                )
            }
        }
        if (event.result.isNotBlank()) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 26.dp, top = 3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    imageVector = NavIcons.Back,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.onSurfaceVariantSummary),
                    modifier = Modifier.size(14.dp).graphicsLayer { rotationZ = 180f }
                )
                Text(
                    text = scriptPluginAgentToolPreview(event.result),
                    color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                    fontSize = 11.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 6.dp).weight(1f)
                )
            }
        }
    }
}

@Composable
internal fun ScriptPluginAgentToolEventDialog(
    event: ScriptPluginAgentToolEvent,
    onCopy: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var fullResult by remember(event.id) { mutableStateOf<String?>(null) }
    var loadingFullResult by remember(event.id) { mutableStateOf(false) }
    WindowDialog(
        show = true,
        title = event.name,
        onDismissRequest = onDismiss,
        content = {
            Column(
                modifier = Modifier.fillMaxWidth().heightIn(max = 460.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                val workspaceWrite = isScriptPluginAgentWorkspaceWriteEvent(event)
                if (workspaceWrite && event.diff.isNotBlank()) {
                    ScriptPluginAgentDiffCodeBlock(event.diff)
                }
                if (!workspaceWrite && event.arguments.isNotBlank()) {
                    Text(
                        text = "调用参数",
                        color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
                    )
                    MarkdownUi.CodeBlock(event.arguments)
                }
                if (!workspaceWrite && event.diff.isNotBlank()) {
                    Text(
                        text = "代码差异",
                        color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
                    )
                    ScriptPluginAgentDiffCodeBlock(event.diff)
                }
                if (!workspaceWrite && event.result.isNotBlank()) {
                    Text(
                        text = if (event.status == "success") "执行结果" else "执行信息",
                        color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
                    )
                    ScriptPluginAgentToolResultBlock(event, event.result)
                    TextButton(
                        text = "复制结果",
                        onClick = { onCopy(event.result) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.textButtonColorsPrimary()
                    )
                }
                if (!workspaceWrite && event.resultHandle.isNotBlank()) {
                    Text(
                        text = "完整结果已保存（${event.resultLength} 字符）",
                        color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
                    )
                    TextButton(
                        text = if (loadingFullResult) "正在读取完整结果" else "加载完整结果",
                        enabled = !loadingFullResult,
                        onClick = {
                            loadingFullResult = true
                            scope.launch {
                                fullResult = withContext(Dispatchers.IO) {
                                    ScriptPluginAgentToolResultStore.readAll(context, event.resultHandle).getOrNull()
                                }
                                loadingFullResult = false
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.textButtonColorsPrimary()
                    )
                    fullResult?.let { value ->
                        ScriptPluginAgentToolResultBlock(event, value)
                        TextButton(
                            text = "复制完整结果",
                            onClick = { onCopy(value) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.textButtonColorsPrimary()
                        )
                    }
                }
                TextButton(
                    text = "关闭",
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    colors = ButtonDefaults.textButtonColorsPrimary()
                )
            }
        }
    )
}

internal fun isScriptPluginAgentWorkspaceWriteEvent(event: ScriptPluginAgentToolEvent): Boolean {
    if (event.name == "写入插件文件" || event.name == "修改插件文件") return true
    if (event.kind != "workspace") return false
    val protocolName = event.protocolName.lowercase(Locale.ROOT)
    return protocolName == "write_file" ||
        protocolName == "apply_patch" ||
        protocolName.endsWith(".write_file") ||
        protocolName.endsWith(".apply_patch") ||
        protocolName.endsWith("_write_file") ||
        protocolName.endsWith("_apply_patch")
}

@Composable
internal fun ScriptPluginAgentDiffGroup(stateKey: String, diff: String) {
    var expanded by rememberSaveable(stateKey, "diff") { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (expanded) 90f else 0f,
        animationSpec = tween(250),
        label = "agentDiffArrow"
    )
    Column(modifier = Modifier.fillMaxWidth().padding(top = 6.dp).animateContentSize()) {
        Row(
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(6.dp))
                .responsiveTap(onClick = { expanded = !expanded })
                .padding(vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = NavIcons.Expand,
                contentDescription = if (expanded) "收起代码变更" else "展开代码变更",
                colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.onSurfaceVariantSummary),
                modifier = Modifier.size(18.dp).graphicsLayer { rotationZ = rotation }
            )
            Text(
                text = "代码变更",
                color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 7.dp)
            )
        }
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn(tween(180)),
            exit = fadeOut(tween(140))
        ) {
            ScriptPluginAgentDiffCodeBlock(diff)
        }
    }
}

@Composable
internal fun ScriptPluginAgentToolResultBlock(event: ScriptPluginAgentToolEvent, value: String) {
    val isDiffTool = event.name == "查看代码差异" ||
        event.protocolName.contains("show_diff", ignoreCase = true)
    val diff = if (isDiffTool) scriptPluginAgentDiffFromToolResult(value) else null
    if (diff != null) {
        ScriptPluginAgentDiffCodeBlock(diff)
    } else {
        MarkdownUi.CodeBlock(value)
    }
}

internal fun scriptPluginAgentDiffFromToolResult(value: String): String? {
    return runCatching {
        JSONObject(value).optString("diff", "").takeIf { it.isNotBlank() }
    }.getOrNull()
}

@Composable
internal fun ScriptPluginAgentDiffCodeBlock(diff: String) {
    val darkTheme = isSystemInDarkTheme()
    val additionText = if (darkTheme) Color(0xFF7EE787) else Color(0xFF116329)
    val additionBackground = if (darkTheme) Color(0xFF123820) else Color(0xFFE6FFEC)
    val deletionText = if (darkTheme) Color(0xFFFFA198) else Color(0xFFCF222E)
    val deletionBackground = if (darkTheme) Color(0xFF3D1C20) else Color(0xFFFFEBE9)
    val hunkText = if (darkTheme) Color(0xFF79C0FF) else Color(0xFF0969DA)
    val hunkBackground = if (darkTheme) Color(0xFF162A42) else Color(0xFFDDF4FF)
    val neutralText = MiuixTheme.colorScheme.onSurface
    val headerText = MiuixTheme.colorScheme.onSurfaceVariantSummary

    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MiuixTheme.colorScheme.surfaceVariant)
            .horizontalScroll(rememberScrollState())
            .padding(vertical = 6.dp)
    ) {
        diff.lines().forEach { line ->
            val isHeader = line.startsWith("diff --git ") ||
                line.startsWith("--- ") ||
                line.startsWith("+++ ") ||
                line.startsWith("index ") ||
                line.startsWith("new file mode ") ||
                line.startsWith("deleted file mode ") ||
                line.startsWith("similarity index ") ||
                line.startsWith("rename from ") ||
                line.startsWith("rename to ") ||
                line.startsWith("Binary files ") ||
                line.startsWith("Only in ")
            val (textColor, backgroundColor) = when {
                line.startsWith("@@") -> hunkText to hunkBackground
                isHeader -> headerText to Color.Transparent
                line.startsWith("+") -> additionText to additionBackground
                line.startsWith("-") -> deletionText to deletionBackground
                else -> neutralText to Color.Transparent
            }
            Text(
                text = line.ifEmpty { " " },
                color = textColor,
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace,
                softWrap = false,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
                    .background(backgroundColor)
                    .padding(horizontal = 10.dp, vertical = 1.dp)
            )
        }
    }
}

internal fun scriptPluginAgentToolPreview(value: String): String {
    return value.replace(Regex("\\s+"), " ").trim()
}

@Composable
internal fun ScriptPluginAgentMessageMenu(
    message: ScriptPluginAgentChatMessage,
    onDismiss: () -> Unit,
    onCopy: () -> Unit,
    onReadAloud: () -> Unit,
    onQuote: () -> Unit,
    onRegenerate: (() -> Unit)?,
    onEditAndResend: (() -> Unit)?,
    onRollback: () -> Unit,
    onDelete: () -> Unit,
    onCreateBranch: () -> Unit,
    onInfo: () -> Unit
) {
    WindowDialog(
        show = true,
        title = "消息操作",
        onDismissRequest = onDismiss,
        content = {
            Column {
                ScriptPluginAgentMessageActionRow(NavIcons.Copy, "复制消息", onCopy)
                InsetDivider()
                ScriptPluginAgentMessageActionRow(NavIcons.Volume, "朗读消息", onReadAloud)
                InsetDivider()
                ScriptPluginAgentMessageActionRow(NavIcons.Quote, "引用消息", onQuote)
                if (onRegenerate != null) {
                    InsetDivider()
                    ScriptPluginAgentMessageActionRow(NavIcons.Refresh, "重新生成", onRegenerate)
                }
                if (onEditAndResend != null) {
                    InsetDivider()
                    ScriptPluginAgentMessageActionRow(NavIcons.Edit, "编辑并重发", onEditAndResend)
                }
                InsetDivider()
                ScriptPluginAgentMessageActionRow(NavIcons.History, "回滚到此处", onRollback)
                InsetDivider()
                ScriptPluginAgentMessageActionRow(NavIcons.Delete, "删除", onDelete)
                InsetDivider()
                ScriptPluginAgentMessageActionRow(NavIcons.Branch, "创建分支", onCreateBranch)
                InsetDivider()
                ScriptPluginAgentMessageActionRow(NavIcons.Info, "信息", onInfo)
                if (message.content.isBlank()) {
                    Text(
                        text = "当前消息没有正文",
                        color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }
    )
}

@Composable
internal fun ScriptPluginAgentMessageActionRow(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(6.dp))
            .responsiveTap(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector = icon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.onSurfaceVariantSummary),
            modifier = Modifier.size(22.dp)
        )
        Text(
            text = text,
            color = MiuixTheme.colorScheme.onSurface,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 14.dp)
        )
    }
}

@Composable
internal fun ScriptPluginAgentMessageInfoDialog(
    message: ScriptPluginAgentChatMessage,
    onDismiss: () -> Unit
) {
    val status = when (message.status) {
        "streaming" -> "生成中"
        "interrupted" -> "已中断"
        "error" -> "请求失败"
        else -> "已完成"
    }
    WindowDialog(
        show = true,
        title = "消息信息",
        onDismissRequest = onDismiss,
        content = {
            Column {
                InfoRow(
                    "角色",
                    when (message.role) {
                        "user" -> "用户"
                        "tool" -> "工具"
                        else -> "Agent"
                    }
                )
                InsetDivider()
                InfoRow("时间", scheduledTaskTimeText(message.createdAt))
                InsetDivider()
                InfoRow("状态", status)
                InsetDivider()
                InfoRow("字符数", message.content.length.toString())
                InsetDivider()
                InfoRow(
                    "Token 估算",
                    ScriptPluginAgentContext.estimateTokens("", listOf(message), null).toString()
                )
                if (message.attachments.isNotEmpty()) {
                    InsetDivider()
                    InfoRow("附件", message.attachments.size.toString())
                }
                TextButton(
                    text = "关闭",
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    colors = ButtonDefaults.textButtonColorsPrimary()
                )
            }
        }
    )
}

internal fun mergeScriptPluginAgentProgress(current: String, next: String): String {
    val cleanNext = next.trim()
    return cleanNext.ifBlank { current }
}

internal fun mergeScriptPluginAgentReply(current: String, incoming: String): String {
    if (incoming.isBlank()) return current
    return ScriptPluginAgentTextMerge.mergeReply(current, incoming)
}

internal fun Modifier.agentMessageLongPress(
    key: Long,
    enabled: Boolean,
    onLongPress: () -> Unit
): Modifier = pointerInput(key, enabled) {
    if (!enabled) return@pointerInput
    awaitEachGesture {
        val down = awaitFirstDown(
            requireUnconsumed = false,
            pass = PointerEventPass.Initial
        )
        val releasedOrMoved = withTimeoutOrNull(viewConfiguration.longPressTimeoutMillis) {
            while (true) {
                val event = awaitPointerEvent(PointerEventPass.Initial)
                val change = event.changes.firstOrNull { it.id == down.id }
                    ?: return@withTimeoutOrNull true
                if (!change.pressed) return@withTimeoutOrNull true
                if (
                    abs(change.position.x - down.position.x) > viewConfiguration.touchSlop ||
                    abs(change.position.y - down.position.y) > viewConfiguration.touchSlop
                ) {
                    return@withTimeoutOrNull true
                }
            }
            @Suppress("UNREACHABLE_CODE")
            false
        }
        if (releasedOrMoved == null) {
            onLongPress()
            while (true) {
                val event = awaitPointerEvent(PointerEventPass.Initial)
                val change = event.changes.firstOrNull { it.id == down.id }
                change?.consume()
                if (change == null || !change.pressed) break
            }
        }
    }
}

@Composable
internal fun ScriptPluginAgentHistoryDrawer(
    sessions: List<ScriptPluginAgentSession>,
    activeId: String,
    workingSessionIds: Set<String>,
    enabled: Boolean,
    onNewSession: () -> Unit,
    onSelect: (ScriptPluginAgentSession) -> Unit,
    onRename: (ScriptPluginAgentSession, String) -> Unit,
    onCommitOrder: (List<ScriptPluginAgentSession>) -> Unit,
    onSetPinned: (ScriptPluginAgentSession, Boolean) -> Unit,
    onSetLocked: (ScriptPluginAgentSession, Boolean) -> Unit,
    onDelete: (ScriptPluginAgentSession) -> Unit,
    onClose: () -> Unit
) {
    val context = LocalContext.current
    val uiPrefs = remember(context) { HchatStorage.preferences(context, UI_PREFS_NAME) }
    var actionSessionId by remember { mutableStateOf<String?>(null) }
    var renameSessionId by remember { mutableStateOf<String?>(null) }
    var deleteSessionId by remember { mutableStateOf<String?>(null) }
    var draggedSessionId by remember { mutableStateOf<String?>(null) }
    var dragOrderChanged by remember { mutableStateOf(false) }
    var orderedSessions by remember { mutableStateOf(sessions) }
    var showGestureHint by remember {
        mutableStateOf(uiPrefs.getBoolean(KEY_AGENT_HISTORY_GESTURE_HINT, true))
    }
    var titleDraft by remember { mutableStateOf("") }
    val actionSession = actionSessionId?.let { id -> orderedSessions.firstOrNull { it.id == id } }
    val renameSession = renameSessionId?.let { id -> orderedSessions.firstOrNull { it.id == id } }
    val deleteSession = deleteSessionId?.let { id -> orderedSessions.firstOrNull { it.id == id } }

    LaunchedEffect(sessions) {
        if (draggedSessionId == null) orderedSessions = sessions
    }

    fun movedSessions(session: ScriptPluginAgentSession, delta: Int): List<ScriptPluginAgentSession>? {
        val next = orderedSessions.toMutableList()
        val from = next.indexOfFirst { it.id == session.id }
        val to = from + delta
        if (from !in next.indices || to !in next.indices || next[from].pinned != next[to].pinned) return null
        val moved = next.removeAt(from)
        next.add(to, moved)
        return next
    }

    if (renameSession != null) {
        WindowDialog(
            show = true,
            title = "编辑标题",
            onDismissRequest = { renameSessionId = null },
            content = {
                Column {
                    InputRow(
                        title = "对话标题",
                        summary = "最多 32 个字符",
                        value = titleDraft,
                        onValueChange = { titleDraft = it.take(32) }
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        TextButton(
                            text = "取消",
                            onClick = { renameSessionId = null },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.textButtonColorsPrimary()
                        )
                        TextButton(
                            text = "保存",
                            onClick = {
                                if (titleDraft.isNotBlank()) {
                                    onRename(renameSession, titleDraft)
                                    renameSessionId = null
                                }
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.textButtonColorsPrimary()
                        )
                    }
                }
            }
        )
    }
    if (deleteSession != null) {
        WindowDialog(
            show = true,
            title = "删除对话",
            onDismissRequest = { deleteSessionId = null },
            content = {
                Column {
                    Text(
                        text = "确定删除“${deleteSession.title.ifBlank { "新对话" }}”吗？此操作无法撤销。",
                        color = MiuixTheme.colorScheme.onSurface,
                        fontSize = 14.sp
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 14.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        TextButton(
                            text = "取消",
                            onClick = { deleteSessionId = null },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.textButtonColorsPrimary()
                        )
                        TextButton(
                            text = "删除",
                            onClick = {
                                deleteSessionId = null
                                onDelete(deleteSession)
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.textButtonColorsPrimary()
                        )
                    }
                }
            }
        )
    }
    if (actionSession != null) {
        val index = orderedSessions.indexOfFirst { it.id == actionSession.id }
        val canMoveUp = index > 0 && orderedSessions[index - 1].pinned == actionSession.pinned
        val canMoveDown = index in 0 until orderedSessions.lastIndex &&
            orderedSessions[index + 1].pinned == actionSession.pinned
        WindowDialog(
            show = true,
            title = "对话历史",
            onDismissRequest = { actionSessionId = null },
            content = {
                Column {
                    Text(
                        text = actionSession.title.ifBlank { "新对话" },
                        color = MiuixTheme.colorScheme.onSurface,
                        fontSize = 15.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 8.dp, bottom = 10.dp),
                        textAlign = TextAlign.Center
                    )
                    ScriptPluginAgentHistoryActionRow(NavIcons.Edit, "编辑标题") {
                        titleDraft = actionSession.title
                        renameSessionId = actionSession.id
                        actionSessionId = null
                    }
                    ScriptPluginAgentHistoryActionRow(NavIcons.MoveUp, "上移", enabled = canMoveUp) {
                        movedSessions(actionSession, -1)?.let { next ->
                            orderedSessions = next
                            onCommitOrder(next)
                        }
                        actionSessionId = null
                    }
                    ScriptPluginAgentHistoryActionRow(NavIcons.MoveDown, "下移", enabled = canMoveDown) {
                        movedSessions(actionSession, 1)?.let { next ->
                            orderedSessions = next
                            onCommitOrder(next)
                        }
                        actionSessionId = null
                    }
                    ScriptPluginAgentHistoryActionRow(
                        NavIcons.Pin,
                        if (actionSession.pinned) "取消置顶" else "置顶聊天"
                    ) {
                        onSetPinned(actionSession, !actionSession.pinned)
                        actionSessionId = null
                    }
                    ScriptPluginAgentHistoryActionRow(
                        if (actionSession.locked) NavIcons.Unlock else NavIcons.Lock,
                        if (actionSession.locked) "解锁聊天" else "锁定聊天"
                    ) {
                        onSetLocked(actionSession, !actionSession.locked)
                        actionSessionId = null
                    }
                    ScriptPluginAgentHistoryActionRow(
                        NavIcons.Delete,
                        if (actionSession.locked) "删除（已锁定）" else "删除",
                        destructive = true,
                        enabled = !actionSession.locked
                    ) {
                        deleteSessionId = actionSession.id
                        actionSessionId = null
                    }
                    TextButton(
                        text = "取消",
                        onClick = { actionSessionId = null },
                        modifier = Modifier.fillMaxWidth().padding(top = 2.dp),
                        colors = ButtonDefaults.textButtonColorsPrimary()
                    )
                }
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.28f)).responsiveTap(onClick = onClose)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(0.84f)
                .fillMaxHeight()
                .background(MiuixTheme.colorScheme.background)
                .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "对话历史",
                    color = MiuixTheme.colorScheme.onSurface,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )
                TextButton(
                    text = "关闭",
                    onClick = onClose,
                    colors = ButtonDefaults.textButtonColorsPrimary()
                )
            }
            TextButton(
                text = "+  新建对话",
                onClick = onNewSession,
                enabled = enabled,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 4.dp),
                colors = ButtonDefaults.textButtonColorsPrimary()
            )
            if (showGestureHint) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .responsiveTap(onClick = {
                            showGestureHint = false
                            uiPrefs.edit().putBoolean(KEY_AGENT_HISTORY_GESTURE_HINT, false).apply()
                        })
                        .padding(horizontal = 18.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        imageVector = NavIcons.Swipe,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.onSurfaceVariantSummary),
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "左右滑动可编辑或删除（点击不再显示）",
                        color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 9.dp)
                    )
                }
            }
            InsetDivider()
            LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(vertical = 8.dp)) {
                orderedSessions.forEach { session ->
                    item(key = session.id) {
                        ScriptPluginAgentHistoryItem(
                            modifier = if (draggedSessionId == session.id) {
                                Modifier
                            } else {
                                Modifier.animateItem(
                                    fadeInSpec = null,
                                    placementSpec = tween(durationMillis = 180),
                                    fadeOutSpec = null
                                )
                            },
                            session = session,
                            selected = session.id == activeId,
                            working = session.id in workingSessionIds,
                            enabled = enabled,
                            onSelect = { onSelect(session) },
                            onLongPress = { actionSessionId = session.id },
                            onSwipeRename = {
                                titleDraft = session.title
                                renameSessionId = session.id
                            },
                            onSwipeDelete = {
                                if (session.locked) {
                                    Toast.makeText(context, "请先解锁该对话", Toast.LENGTH_SHORT).show()
                                } else {
                                    deleteSessionId = session.id
                                }
                            },
                            onDragMove = { delta ->
                                movedSessions(session, delta)?.let { next ->
                                    orderedSessions = next
                                    dragOrderChanged = true
                                    true
                                } ?: false
                            },
                            onDragStateChange = { dragging ->
                                if (dragging) {
                                    draggedSessionId = session.id
                                    dragOrderChanged = false
                                } else if (draggedSessionId == session.id) {
                                    draggedSessionId = null
                                    if (dragOrderChanged) onCommitOrder(orderedSessions)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
internal fun ScriptPluginAgentWorkingSpinner(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "agentHistoryWorking")
    val spinnerColor = MiuixTheme.colorScheme.primary
    val rotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 900, easing = LinearEasing)
        ),
        label = "agentHistoryWorkingRotation"
    )
    Canvas(
        modifier = modifier
            .size(18.dp)
            .graphicsLayer { rotationZ = rotation }
    ) {
        drawArc(
            color = spinnerColor,
            startAngle = -90f,
            sweepAngle = 285f,
            useCenter = false,
            style = Stroke(width = 2.dp.toPx())
        )
    }
}

@Composable
internal fun ScriptPluginAgentHistoryItem(
    modifier: Modifier = Modifier,
    session: ScriptPluginAgentSession,
    selected: Boolean,
    working: Boolean,
    enabled: Boolean,
    onSelect: () -> Unit,
    onLongPress: () -> Unit,
    onSwipeRename: () -> Unit,
    onSwipeDelete: () -> Unit,
    onDragMove: (Int) -> Boolean,
    onDragStateChange: (Boolean) -> Unit
) {
    val density = LocalDensity.current
    val actionWidthPx = with(density) { 82.dp.toPx() }
    var offsetX by remember(session.id) { mutableStateOf(0f) }
    var itemHeightPx by remember(session.id) { mutableStateOf(0f) }
    var dragDistanceY by remember(session.id) { mutableStateOf(0f) }
    var dragging by remember(session.id) { mutableStateOf(false) }
    val currentOnDragMove by rememberUpdatedState(onDragMove)
    val currentOnDragStateChange by rememberUpdatedState(onDragStateChange)
    val visualDragY by animateFloatAsState(
        targetValue = dragDistanceY,
        animationSpec = tween(durationMillis = if (dragging) 0 else 170),
        label = "AgentHistoryDragOffset"
    )
    val dragScale by animateFloatAsState(
        targetValue = if (dragging) 1.015f else 1f,
        animationSpec = tween(durationMillis = 120),
        label = "AgentHistoryDragScale"
    )
    Box(
        modifier = modifier
            .zIndex(if (dragging) 1f else 0f)
            .graphicsLayer {
                translationY = visualDragY
                scaleX = dragScale
                scaleY = dragScale
                shadowElevation = if (dragging) with(density) { 8.dp.toPx() } else 0f
                shape = RoundedCornerShape(8.dp)
            }
            .fillMaxWidth().padding(horizontal = 12.dp, vertical = 3.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        if (offsetX != 0f) {
            Box(
                modifier = Modifier.matchParentSize(),
                contentAlignment = if (offsetX > 0f) Alignment.CenterStart else Alignment.CenterEnd
            ) {
                Box(
                    modifier = Modifier.fillMaxHeight().sizeIn(minWidth = 82.dp)
                        .background(
                            if (offsetX > 0f) MiuixTheme.colorScheme.primary.copy(alpha = 0.88f)
                            else Color(0xFFD93025)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (offsetX > 0f) "编辑" else "删除",
                        color = Color.White,
                        fontSize = 13.sp
                    )
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth()
                .onSizeChanged { itemHeightPx = it.height.toFloat() }
                .graphicsLayer { translationX = offsetX }
                .background(MiuixTheme.colorScheme.background)
                .background(
                    if (selected) MiuixTheme.colorScheme.primary.copy(alpha = 0.13f)
                    else Color.Transparent
                )
                .pointerInput(session.id, enabled) {
                    if (!enabled) return@pointerInput
                    awaitEachGesture {
                        val down = awaitFirstDown(requireUnconsumed = false)
                        var totalX = 0f
                        var totalY = 0f
                        var horizontal = false
                        while (true) {
                            val event = awaitPointerEvent()
                            val change = event.changes.firstOrNull { it.id == down.id } ?: break
                            totalX += change.position.x - change.previousPosition.x
                            totalY += change.position.y - change.previousPosition.y
                            if (!horizontal && abs(totalX) > viewConfiguration.touchSlop && abs(totalX) > abs(totalY) * 1.25f) {
                                horizontal = true
                            }
                            if (horizontal) {
                                offsetX = totalX.coerceIn(-actionWidthPx, actionWidthPx)
                                change.consume()
                            }
                            if (!change.pressed) break
                        }
                        val action = when {
                            offsetX >= actionWidthPx * 0.58f -> 1
                            offsetX <= -actionWidthPx * 0.58f -> -1
                            else -> 0
                        }
                        offsetX = 0f
                        if (action > 0) onSwipeRename() else if (action < 0) onSwipeDelete()
                    }
                }
                .pointerInput(session.id, enabled) {
                    detectTapGestures(
                        onTap = { if (enabled) onSelect() },
                        onLongPress = { position ->
                            if (enabled && position.x > with(density) { 38.dp.toPx() }) onLongPress()
                        }
                    )
                }
                .padding(horizontal = 14.dp, vertical = 11.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = NavIcons.Drag,
                contentDescription = "长按拖动排序",
                colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.onSurfaceVariantSummary),
                modifier = Modifier.size(22.dp)
                    .pointerInput(session.id, enabled, itemHeightPx) {
                        if (!enabled) return@pointerInput
                        detectDragGesturesAfterLongPress(
                            onDragStart = {
                                dragDistanceY = 0f
                                dragging = true
                                currentOnDragStateChange(true)
                            },
                            onDragEnd = {
                                dragging = false
                                dragDistanceY = 0f
                                currentOnDragStateChange(false)
                            },
                            onDragCancel = {
                                dragging = false
                                dragDistanceY = 0f
                                currentOnDragStateChange(false)
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                dragDistanceY += dragAmount.y
                                val step = (itemHeightPx.takeIf { it > 0f }
                                    ?: with(density) { 56.dp.toPx() }) + with(density) { 6.dp.toPx() }
                                while (dragDistanceY <= -step * 0.5f) {
                                    if (currentOnDragMove(-1)) {
                                        dragDistanceY += step
                                    } else {
                                        dragDistanceY = -step * 0.45f
                                        break
                                    }
                                }
                                while (dragDistanceY >= step * 0.5f) {
                                    if (currentOnDragMove(1)) {
                                        dragDistanceY -= step
                                    } else {
                                        dragDistanceY = step * 0.45f
                                        break
                                    }
                                }
                            }
                        )
                    }
            )
            Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
                Text(
                    text = session.title.ifBlank { "新对话" },
                    color = MiuixTheme.colorScheme.onSurface,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                val lastUser = session.messages.lastOrNull { it.role == "user" }?.content.orEmpty()
                if (lastUser.isNotBlank() && lastUser != session.title) {
                    Text(
                        text = lastUser,
                        color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 3.dp)
                    )
                }
            }
            if (session.pinned) {
                Image(
                    imageVector = NavIcons.Pin,
                    contentDescription = "已置顶",
                    colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.primary),
                    modifier = Modifier.padding(start = 6.dp).size(17.dp)
                )
            }
            if (session.locked) {
                Image(
                    imageVector = NavIcons.Lock,
                    contentDescription = "已锁定",
                    colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.onSurfaceVariantSummary),
                    modifier = Modifier.padding(start = 6.dp).size(17.dp)
                )
            }
            if (working) {
                ScriptPluginAgentWorkingSpinner(Modifier.padding(start = 8.dp))
            }
        }
    }
}

@Composable
internal fun ScriptPluginAgentHistoryActionRow(
    icon: ImageVector,
    text: String,
    destructive: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val contentColor = when {
        !enabled -> MiuixTheme.colorScheme.onSurfaceVariantSummary.copy(alpha = 0.45f)
        destructive -> Color(0xFFD93025)
        else -> MiuixTheme.colorScheme.onSurface
    }
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(
                if (destructive) Color(0xFFD93025).copy(alpha = 0.08f)
                else MiuixTheme.colorScheme.secondaryVariant
            )
            .clickable(enabled = enabled, onClick = onClick)
            .padding(horizontal = 18.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector = icon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(contentColor),
            modifier = Modifier.size(22.dp)
        )
        Text(
            text = text,
            color = contentColor,
            fontSize = 15.sp,
            modifier = Modifier.padding(start = 18.dp)
        )
    }
}

@Composable
internal fun ScriptPluginAgentQuickProfileDialog(
    profiles: List<ScriptPluginAgentProfile>,
    activeProfileId: String,
    onSelected: (ScriptPluginAgentProfile) -> Unit,
    onDismiss: () -> Unit
) {
    WindowDialog(
        show = true,
        title = "切换模型配置",
        onDismissRequest = onDismiss,
        content = {
            LazyColumn(modifier = Modifier.fillMaxWidth().heightIn(max = 360.dp)) {
                profiles.forEach { profile ->
                    item(key = profile.id) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .clip(RoundedCornerShape(6.dp))
                                .clickable { onSelected(profile) }
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = profile.name,
                                    color = MiuixTheme.colorScheme.onSurface,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = profile.config.model.ifBlank { "未设置模型" },
                                    color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                                    fontSize = 12.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            if (profile.id == activeProfileId) {
                                Text(
                                    text = "当前",
                                    color = MiuixTheme.colorScheme.primary,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(start = 12.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
internal fun ScriptPluginAgentQuickOptionsDialog(
    webSearchEnabled: Boolean,
    workspaceWriteApprovalMode: String,
    promptCacheMode: String,
    endpointMode: String,
    model: String,
    reasoningEffort: String,
    onReasoningEffortChanged: (String) -> Unit,
    mcpServers: List<ScriptPluginAgentMcpServer>,
    onWebSearchChanged: (Boolean) -> Unit,
    onWorkspaceWriteApprovalChanged: (String) -> Unit,
    onPromptCacheModeChanged: (String) -> Unit,
    onMcpChanged: (String, Boolean) -> Unit,
    onOpenSkills: () -> Unit,
    onDismiss: () -> Unit
) {
    WindowDialog(
        show = true,
        title = "快捷选项",
        onDismissRequest = onDismiss,
        content = {
            Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
                ScriptPluginAgentReasoningRow(
                    endpointMode = endpointMode,
                    model = model,
                    effort = reasoningEffort,
                    onValueChanged = onReasoningEffortChanged
                )
                InsetDivider()
                SwitchRow(
                    checked = webSearchEnabled,
                    title = "联网搜索",
                    summary = if (webSearchEnabled) "允许 Agent 按需查询公开资料" else "已关闭，Agent 不会发起搜索",
                    onCheckedChange = onWebSearchChanged
                )
                InsetDivider()
                PopupChoiceRow(
                    title = "插件文件修改确认",
                    summary = if (workspaceWriteApprovalMode == ScriptPluginAgentSettings.WRITE_APPROVAL_ASK) {
                        "每次询问工具写入和最终提交"
                    } else {
                        "始终允许并自动提交"
                    },
                    options = listOf(
                        PopupChoice("每次询问", ScriptPluginAgentSettings.WRITE_APPROVAL_ASK),
                        PopupChoice("始终允许", ScriptPluginAgentSettings.WRITE_APPROVAL_ALWAYS_ALLOW)
                    ),
                    currentValue = workspaceWriteApprovalMode,
                    onValueChanged = onWorkspaceWriteApprovalChanged
                )
                InsetDivider()
                PopupChoiceRow(
                    title = "提示缓存",
                    summary = promptCacheModeSummary(promptCacheMode, endpointMode),
                    options = promptCacheModeChoices(),
                    currentValue = promptCacheMode,
                    onValueChanged = onPromptCacheModeChanged
                )
                InsetDivider()
                ActionRow("Skill 管理", "启用技能、编辑指南或从文件导入") { onOpenSkills() }
                if (mcpServers.isNotEmpty()) {
                    InsetDivider()
                    Text(
                        text = "MCP 服务器",
                        color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                    mcpServers.forEachIndexed { index, server ->
                        if (index > 0) InsetDivider()
                        SwitchRow(
                            checked = server.enabled,
                            title = server.name.ifBlank { "MCP ${index + 1}" },
                            summary = server.endpoint.ifBlank { "未配置 Endpoint" },
                            onCheckedChange = { onMcpChanged(server.id, it) }
                        )
                    }
                } else {
                    Text(
                        text = "暂无 MCP 服务器，请先在 Agent 配置中添加。",
                        color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                    )
                }
            }
        }
    )
}

@Composable
internal fun ScriptPluginAgentPendingMessagePanel(
    messages: List<ScriptPluginAgentPendingMessage>,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onEdit: (Long) -> Unit,
    onDelete: (Long) -> Unit,
    onSendNow: (Long) -> Unit
) {
    if (messages.isEmpty()) return
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(start = 10.dp, top = 8.dp, end = 10.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(MiuixTheme.colorScheme.secondaryVariant)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().clickable { onExpandedChange(!expanded) }
                .padding(horizontal = 10.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "待发送 ${messages.size}",
                color = MiuixTheme.colorScheme.onSurface,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
            Image(
                imageVector = if (expanded) NavIcons.MoveUp else NavIcons.MoveDown,
                contentDescription = if (expanded) "收起待发送消息" else "展开待发送消息",
                colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.onSurfaceVariantSummary),
                modifier = Modifier.size(18.dp)
            )
        }
        if (expanded) {
            LazyColumn(modifier = Modifier.fillMaxWidth().heightIn(max = 150.dp)) {
                messages.forEachIndexed { index, message ->
                    item(key = message.id) {
                        if (index > 0) InsetDivider()
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 6.dp, top = 5.dp, bottom = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = buildString {
                                    if (message.quotedMessage != null) append("引用 · ")
                                    append(message.content.ifBlank { "${message.attachments.size} 个附件" })
                                },
                                color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                                fontSize = 12.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f)
                            )
                            ScriptPluginAgentPendingMessageAction(
                                icon = NavIcons.Edit,
                                description = "编辑待发送消息"
                            ) { onEdit(message.id) }
                            ScriptPluginAgentPendingMessageAction(
                                icon = NavIcons.Send,
                                description = "立即发送"
                            ) { onSendNow(message.id) }
                            ScriptPluginAgentPendingMessageAction(
                                icon = NavIcons.Delete,
                                description = "删除待发送消息"
                            ) { onDelete(message.id) }
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun ScriptPluginAgentPendingMessageAction(
    icon: ImageVector,
    description: String,
    onClick: () -> Unit
) {
    Image(
        imageVector = icon,
        contentDescription = description,
        colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.onSurfaceVariantSummary),
        modifier = Modifier.padding(start = 4.dp).size(26.dp)
            .clip(RoundedCornerShape(4.dp))
            .clickable(onClick = onClick)
            .padding(5.dp)
    )
}

@Composable
internal fun ScriptPluginAgentQuickChoice(
    label: String,
    value: String,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.height(28.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(MiuixTheme.colorScheme.secondaryVariant)
            .clickable(enabled = enabled, onClick = onClick)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label · $value",
            color = if (enabled) {
                MiuixTheme.colorScheme.onSurfaceVariantSummary
            } else {
                MiuixTheme.colorScheme.onSurfaceVariantSummary.copy(alpha = 0.5f)
            },
            fontSize = 11.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
        Image(
            imageVector = NavIcons.MoveDown,
            contentDescription = null,
            colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.onSurfaceVariantSummary),
            modifier = Modifier.padding(start = 3.dp).size(14.dp)
        )
    }
}

@Composable
internal fun ScriptPluginAgentComposer(
    value: String,
    attachments: List<ScriptPluginAgentAttachment>,
    quotedMessage: ScriptPluginAgentQuotedMessage?,
    pendingMessages: List<ScriptPluginAgentPendingMessage>,
    pendingMessagesExpanded: Boolean,
    activeProfileName: String,
    activeModel: String,
    enabled: Boolean,
    generating: Boolean,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
    onQueue: () -> Unit,
    onCancel: () -> Unit,
    onAttach: () -> Unit,
    onRemoveAttachment: (String) -> Unit,
    onClearQuotedMessage: () -> Unit,
    onPendingMessagesExpandedChange: (Boolean) -> Unit,
    onEditPendingMessage: (Long) -> Unit,
    onDeletePendingMessage: (Long) -> Unit,
    onSendPendingMessageNow: (Long) -> Unit,
    onOpenProfilePicker: () -> Unit,
    onOpenModelPicker: () -> Unit,
    onOpenQuickOptions: () -> Unit
) {
    val canSend = value.isNotBlank() || attachments.isNotEmpty()
    val actionEnabled = enabled && canSend
    val actionBackground = MiuixTheme.colorScheme.primary
    val actionTint = Color.White
    Column(
        modifier = Modifier.fillMaxWidth()
            .background(MiuixTheme.colorScheme.background.copy(alpha = 0.96f))
    ) {
        quotedMessage?.let { quoted ->
            ScriptPluginAgentQuotedMessagePreview(
                quotedMessage = quoted,
                maxLines = 1,
                modifier = Modifier.padding(start = 10.dp, top = 8.dp, end = 10.dp),
                onClear = onClearQuotedMessage
            )
        }
        ScriptPluginAgentPendingMessagePanel(
            messages = pendingMessages,
            expanded = pendingMessagesExpanded,
            onExpandedChange = onPendingMessagesExpandedChange,
            onEdit = onEditPendingMessage,
            onDelete = onDeletePendingMessage,
            onSendNow = onSendPendingMessageNow
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 10.dp, top = 8.dp, end = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            ScriptPluginAgentQuickChoice(
                label = "配置",
                value = activeProfileName.ifBlank { "默认配置" },
                enabled = enabled,
                modifier = Modifier.weight(1f),
                onClick = onOpenProfilePicker
            )
            ScriptPluginAgentQuickChoice(
                label = "模型",
                value = activeModel.ifBlank { "未设置" },
                enabled = enabled,
                modifier = Modifier.weight(1f),
                onClick = onOpenModelPicker
            )
            Image(
                imageVector = NavIcons.Settings,
                contentDescription = "快捷选项",
                colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.onSurfaceVariantSummary),
                modifier = Modifier.size(28.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(MiuixTheme.colorScheme.secondaryVariant)
                    .clickable(enabled = enabled, onClick = onOpenQuickOptions)
                    .padding(6.dp)
            )
        }
        if (attachments.isNotEmpty()) {
            Row(
                modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState())
                    .padding(start = 12.dp, top = 8.dp, end = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                attachments.forEach { attachment ->
                    Row(
                        modifier = Modifier.clip(RoundedCornerShape(6.dp))
                            .background(MiuixTheme.colorScheme.secondaryVariant)
                            .padding(start = 8.dp, top = 6.dp, bottom = 6.dp, end = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = attachment.name,
                            color = MiuixTheme.colorScheme.onSurface,
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.sizeIn(maxWidth = 180.dp)
                        )
                        Image(
                            imageVector = NavIcons.Close,
                            contentDescription = "移除附件",
                            colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.onSurfaceVariantSummary),
                            modifier = Modifier.padding(start = 4.dp).size(18.dp)
                                .responsiveTap(onClick = { onRemoveAttachment(attachment.path) })
                        )
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 10.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MiuixTheme.colorScheme.secondaryVariant)
                .padding(start = 8.dp, top = 9.dp, end = 12.dp, bottom = 9.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Image(
                imageVector = NavIcons.Attach,
                contentDescription = "添加附件",
                colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.onSurfaceVariantSummary),
                modifier = Modifier.padding(end = 8.dp, bottom = 1.dp).size(24.dp)
                    .responsiveTap(onClick = onAttach)
            )
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                enabled = enabled,
                minLines = 1,
                maxLines = 4,
                textStyle = TextStyle(color = MiuixTheme.colorScheme.onSurface, fontSize = 14.sp),
                modifier = Modifier.weight(1f).align(Alignment.Top).heightIn(min = 34.dp),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxWidth().heightIn(min = 34.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (value.isBlank()) {
                            Text(
                                text = "输入消息",
                                color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                                fontSize = 14.sp
                            )
                        }
                        innerTextField()
                    }
                }
            )
            if (generating) {
                Image(
                    imageVector = NavIcons.Stop,
                    contentDescription = "停止生成",
                    colorFilter = ColorFilter.tint(Color(0xFFD93025)),
                    modifier = Modifier.padding(start = 6.dp).size(30.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color.White)
                        .clickable(onClick = onCancel)
                        .padding(7.dp)
                )
            }
            if (!generating || canSend) {
                Image(
                    imageVector = if (generating) NavIcons.Add else NavIcons.Send,
                    contentDescription = if (generating) "加入待发送队列" else "发送",
                    colorFilter = ColorFilter.tint(actionTint),
                    modifier = Modifier.padding(start = 6.dp)
                        .size(34.dp)
                        .clip(RoundedCornerShape(17.dp))
                        .background(actionBackground)
                        .clickable(enabled = actionEnabled) {
                            if (generating) onQueue() else onSend()
                        }
                        .padding(7.dp)
                )
            }
        }
    }
}

}

internal object ScriptPluginSettingsMiuixContent {

@Composable
fun ScriptPluginSettingsContent(
    context: Context,
    onOpenReadme: (ScriptPluginRuntime.ScriptPlugin) -> Unit,
    onOpenMarket: () -> Unit,
    onOpenAgent: () -> Unit,
    onOpenManager: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val sp = remember { HchatStorage.preferences(context, ScriptPluginSettings.PREFS_NAME) }
    val pluginRootPath = remember(context) { ScriptPluginRuntime.scriptDir(context).absolutePath }
    var pluginListVersion by remember { mutableStateOf(0) }
    val managedPlugins = remember(pluginListVersion) { ScriptPluginManager.listForDisplay(context) }
    val plugins = remember(managedPlugins) { managedPlugins.map { it.plugin } }
    val pinnedIds = remember(managedPlugins) {
        managedPlugins.filter { it.pinned }.mapTo(LinkedHashSet()) { it.plugin.id }
    }
    var globalEnabled by remember {
        mutableStateOf(sp.getBoolean(ScriptPluginSettings.KEY_ENABLE, ScriptPluginSettings.DEFAULT_ENABLE))
    }
    var pluginEnabledStates by remember(plugins) {
        mutableStateOf(plugins.associate { it.id to ScriptPluginRuntime.isPluginEnabled(context, it.id) })
    }
    var showPluginRootDialog by remember { mutableStateOf(false) }
    var actionPlugin by remember { mutableStateOf<ScriptPluginRuntime.ScriptPlugin?>(null) }
    var renamePlugin by remember { mutableStateOf<ScriptPluginRuntime.ScriptPlugin?>(null) }
    var deletePlugin by remember { mutableStateOf<ScriptPluginRuntime.ScriptPlugin?>(null) }
    DisposableEffect(context) {
        val subscription = ScriptPluginRuntime.subscribePluginCatalog(context) {
            Handler(Looper.getMainLooper()).post {
                pluginEnabledStates = ScriptPluginRuntime.listPlugins(context)
                    .associate { it.id to ScriptPluginRuntime.isPluginEnabled(context, it.id) }
                globalEnabled = sp.getBoolean(
                    ScriptPluginSettings.KEY_ENABLE,
                    ScriptPluginSettings.DEFAULT_ENABLE
                )
                pluginListVersion++
            }
        }
        onDispose { subscription.unsubscribe() }
    }

    Column {
        SettingsCard {
            PathSwitchRow(
                globalEnabled,
                "插件总开关",
                "启动时自动加载已启用插件\n相关说明:\n请确认插件安全再进行加载,\n否则造成的后果需自行承担。",
                onInfoClick = { showPluginRootDialog = true },
            ) { next ->
                val old = globalEnabled
                globalEnabled = next
                Thread({
                    val result = ScriptPluginRuntime.setGlobalEnabled(context, next)
                    Handler(Looper.getMainLooper()).post {
                        if (result.isFailure) {
                            globalEnabled = old
                            Toast.makeText(
                                context,
                                "切换失败: ${result.exceptionOrNull()?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            pluginListVersion++
                        }
                    }
                }, "Hchat-Script-Global").start()
            }
        }
        if (showPluginRootDialog) {
            ScriptPluginPathDialog(
                context = context,
                title = "插件目录",
                path = pluginRootPath,
                onClose = { showPluginRootDialog = false }
            )
        }
        SettingsCard(modifier = Modifier.padding(top = 10.dp)) {
            ActionRow("插件 Agent", "按需求生成或修改脚本插件") {
                onOpenAgent()
            }
        }
        SettingsCard(modifier = Modifier.padding(top = 10.dp)) {
            ActionRow("在线插件", "浏览、安装或上传社区脚本插件") {
                onOpenMarket()
            }
        }
        SettingsCard(modifier = Modifier.padding(top = 10.dp)) {
            ActionRow("本地插件管理", "排序、置顶、导入、导出或批量管理插件") {
                onOpenManager()
            }
        }
        SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "本地插件(${plugins.size})")
        SettingsCard {
            if (plugins.isEmpty()) {
                Text(
                    text = "暂无插件",
                    color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
                )
            } else {
                plugins.forEachIndexed { index, plugin ->
                    val checked = pluginEnabledStates[plugin.id]
                        ?: ScriptPluginRuntime.isPluginEnabled(context, plugin.id)
                    val summary = buildString {
                        append(plugin.dir.name)
                        append("\n")
                        append("作者: ")
                        append(plugin.author.ifBlank { "未知" })
                        append(" | 更新于: ")
                        append(plugin.updateTime.ifBlank { "未知" })
                    }
                    val title = buildString {
                        append(plugin.displayName ?: "未知")
                        append("(")
                        append(plugin.version.ifBlank { "未知" })
                        append(")")
                    }
                    ScriptPluginRow(
                        checked = checked,
                        title = title,
                        summary = summary,
                        showSettings = ScriptPluginRuntime.canOpenSettings(plugin),
                        onOpenReadme = { onOpenReadme(plugin) },
                        onOpenSettings = {
                            val result = ScriptPluginRuntime.callOpenSettings(plugin.id)
                            if (result.isFailure) {
                                Toast.makeText(
                                    context,
                                    result.exceptionOrNull()?.message ?: "打开设置失败",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        onOpenManager = { actionPlugin = plugin },
                        onCheckedChange = { next ->
                            val oldMap = pluginEnabledStates
                            pluginEnabledStates = pluginEnabledStates + (plugin.id to next)
                            Thread({
                                val result = ScriptPluginRuntime.setPluginEnabled(context, plugin.id, next)
                                Handler(Looper.getMainLooper()).post {
                                    if (result.isFailure) {
                                        pluginEnabledStates = oldMap
                                        Toast.makeText(
                                            context,
                                            "加载[${plugin.displayName ?: "未知"}]失败，已自动关闭",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }, "Hchat-Script-${plugin.id}").start()
                        }
                    )
                    if (index != plugins.lastIndex) InsetDivider()
                }
            }
        }
        actionPlugin?.let { plugin ->
            ScriptPluginActionDialog(
                plugin = plugin,
                pinned = plugin.id in pinnedIds,
                onDismiss = { actionPlugin = null },
                onPinChanged = { pinned ->
                    actionPlugin = null
                    scope.launch {
                        val result = withContext(Dispatchers.IO) {
                            ScriptPluginManager.setPinned(context, listOf(plugin.id), pinned)
                        }
                        result.fold(
                            onSuccess = {
                                pluginListVersion++
                                Toast.makeText(
                                    context,
                                    if (pinned) "已置顶" else "已取消置顶",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            onFailure = { showScriptPluginManagerError(context, it) }
                        )
                    }
                },
                onRename = {
                    actionPlugin = null
                    showAfterDialogDismiss(context) { renamePlugin = plugin }
                },
                onExport = {
                    actionPlugin = null
                    showAfterDialogDismiss(context) {
                        launchScriptPluginExport(
                            context = context,
                            plugins = listOf(plugin)
                        )
                    }
                },
                onDelete = {
                    actionPlugin = null
                    showAfterDialogDismiss(context) { deletePlugin = plugin }
                }
            )
        }
        renamePlugin?.let { plugin ->
            ScriptPluginRenameDialog(
                plugin = plugin,
                onDismiss = { renamePlugin = null },
                onConfirm = { name ->
                    renamePlugin = null
                    scope.launch {
                        val result = withContext(Dispatchers.IO) {
                            ScriptPluginManager.renamePlugin(context, plugin.id, name)
                        }
                        result.fold(
                            onSuccess = {
                                pluginListVersion++
                                Toast.makeText(context, "已重命名", Toast.LENGTH_SHORT).show()
                            },
                            onFailure = { showScriptPluginManagerError(context, it) }
                        )
                    }
                }
            )
        }
        deletePlugin?.let { plugin ->
            ScriptPluginDeleteDialog(
                pluginNames = listOf(plugin.displayName ?: plugin.name.ifBlank { plugin.id }),
                onDismiss = { deletePlugin = null },
                onConfirm = {
                    deletePlugin = null
                    scope.launch {
                        val result = withContext(Dispatchers.IO) {
                            ScriptPluginManager.deletePlugin(context, plugin.id)
                        }
                        result.fold(
                            onSuccess = {
                                pluginListVersion++
                                Toast.makeText(context, "插件已删除", Toast.LENGTH_SHORT).show()
                            },
                            onFailure = { showScriptPluginManagerError(context, it) }
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun ScriptPluginManagerPage(
    context: Context,
    onBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val scrollBehavior = MiuixScrollBehavior()
    var query by rememberSaveable { mutableStateOf("") }
    var refreshVersion by remember { mutableStateOf(0) }
    var loading by remember { mutableStateOf(true) }
    var managedPlugins by remember {
        mutableStateOf<List<ScriptPluginManager.ManagedPlugin>>(emptyList())
    }
    var selectedIds by remember { mutableStateOf<Set<String>>(emptySet()) }
    var draggedPluginId by remember { mutableStateOf<String?>(null) }
    var dragOrderChanged by remember { mutableStateOf(false) }
    var actionPlugin by remember { mutableStateOf<ScriptPluginManager.ManagedPlugin?>(null) }
    var renamePlugin by remember { mutableStateOf<ScriptPluginRuntime.ScriptPlugin?>(null) }
    var deletePlugins by remember { mutableStateOf<List<ScriptPluginRuntime.ScriptPlugin>>(emptyList()) }
    var importInspection by remember { mutableStateOf<ScriptPluginManager.ImportInspection?>(null) }
    var importOverwriteIds by remember { mutableStateOf<Set<String>>(emptySet()) }
    var busyText by remember { mutableStateOf("") }
    val pageActive = remember { AtomicBoolean(true) }
    val activeImportSessionId = remember { AtomicReference<String?>(null) }

    LaunchedEffect(refreshVersion) {
        loading = true
        val result = withContext(Dispatchers.IO) {
            runCatching {
                ScriptPluginManager.cleanupStaleDisplayIds(context).getOrThrow()
                ScriptPluginManager.listForDisplay(context)
            }
        }
        result.fold(
            onSuccess = { plugins ->
                managedPlugins = plugins
                selectedIds = selectedIds.intersect(plugins.mapTo(LinkedHashSet()) { it.plugin.id })
            },
            onFailure = { showScriptPluginManagerError(context, it) }
        )
        loading = false
    }
    DisposableEffect(context) {
        val subscription = ScriptPluginRuntime.subscribePluginCatalog(context) {
            Handler(Looper.getMainLooper()).post { refreshVersion++ }
        }
        onDispose { subscription.unsubscribe() }
    }
    DisposableEffect(Unit) {
        pageActive.set(true)
        onDispose {
            pageActive.set(false)
            activeImportSessionId.getAndSet(null)?.let { sessionId ->
                Thread({ ScriptPluginManager.discardImport(context, sessionId) }, "Hchat-Plugin-Import-Cleanup").start()
            }
        }
    }

    val normalizedQuery = query.trim().lowercase(Locale.ROOT)
    val visiblePlugins = managedPlugins.filter { item ->
        val plugin = item.plugin
        normalizedQuery.isEmpty() ||
            plugin.id.lowercase(Locale.ROOT).contains(normalizedQuery) ||
            plugin.name.lowercase(Locale.ROOT).contains(normalizedQuery) ||
            plugin.author.lowercase(Locale.ROOT).contains(normalizedQuery) ||
            plugin.version.lowercase(Locale.ROOT).contains(normalizedQuery)
    }
    val pinnedPlugins = visiblePlugins.filter { it.pinned }
    val normalPlugins = visiblePlugins.filterNot { it.pinned }
    val visibleIds = visiblePlugins.mapTo(LinkedHashSet()) { it.plugin.id }
    val allVisibleSelected = visibleIds.isNotEmpty() && visibleIds.all { it in selectedIds }

    fun refresh() {
        refreshVersion++
    }

    fun setPinned(ids: Set<String>, pinned: Boolean) {
        if (ids.isEmpty() || busyText.isNotEmpty()) return
        busyText = if (pinned) "正在置顶" else "正在取消置顶"
        scope.launch {
            val result = withContext(Dispatchers.IO) {
                ScriptPluginManager.setPinned(context, ids, pinned)
            }
            busyText = ""
            result.fold(
                onSuccess = {
                    refresh()
                    Toast.makeText(
                        context,
                        if (pinned) "已置顶 ${ids.size} 个插件" else "已取消置顶 ${ids.size} 个插件",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                onFailure = { showScriptPluginManagerError(context, it) }
            )
        }
    }

    fun commitOrder() {
        val ids = managedPlugins.map { it.plugin.id }
        scope.launch {
            val result = withContext(Dispatchers.IO) {
                ScriptPluginManager.saveDisplayOrder(context, ids)
            }
            result.onFailure { showScriptPluginManagerError(context, it) }
        }
    }

    fun movePlugin(item: ScriptPluginManager.ManagedPlugin, delta: Int): Boolean {
        if (query.isNotBlank()) return false
        val index = managedPlugins.indexOfFirst { it.plugin.id == item.plugin.id }
        val targetIndex = index + delta
        if (index < 0 || targetIndex !in managedPlugins.indices) return false
        if (managedPlugins[targetIndex].pinned != item.pinned) return false
        val next = managedPlugins.toMutableList()
        val moved = next.removeAt(index)
        next.add(targetIndex, moved)
        managedPlugins = next
        return true
    }

    fun launchImportPicker() {
        val activity = context as? Activity
        if (activity == null) {
            Toast.makeText(context, "无法打开文件选择器", Toast.LENGTH_SHORT).show()
            return
        }
        ScriptPluginDocumentBridge.launchImport(activity) { uri ->
            if (!pageActive.get()) return@launchImport
            busyText = "正在检查导入包"
            Thread({
                val result = runCatching {
                    context.contentResolver.openInputStream(uri)?.use { input ->
                        ScriptPluginManager.inspectImport(context, input).getOrThrow()
                    } ?: error("无法读取所选文件")
                }
                if (!pageActive.get()) {
                    result.getOrNull()?.let { inspection ->
                        ScriptPluginManager.discardImport(context, inspection.sessionId)
                    }
                    return@Thread
                }
                Handler(Looper.getMainLooper()).post {
                    if (!pageActive.get()) {
                        result.getOrNull()?.let { inspection ->
                            Thread(
                                { ScriptPluginManager.discardImport(context, inspection.sessionId) },
                                "Hchat-Plugin-Import-Late-Cleanup"
                            ).start()
                        }
                        return@post
                    }
                    busyText = ""
                    result.fold(
                        onSuccess = { inspection ->
                            activeImportSessionId.set(inspection.sessionId)
                            importInspection = inspection
                            importOverwriteIds = emptySet()
                        },
                        onFailure = { showScriptPluginManagerError(context, it) }
                    )
                }
            }, "Hchat-Plugin-Import-Inspect").start()
        }
    }

    PageScaffold(
        title = "本地插件管理",
        largeTitle = "本地插件管理",
        scrollBehavior = scrollBehavior,
        onBack = onBack,
        bottomBar = { BottomActionBar("返回", onBack) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            state = listState,
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 8.dp,
                bottom = padding.calculateBottomPadding() + 84.dp
            )
        ) {
            item {
                SearchBarSurface(
                    query = query,
                    placeholder = "搜索本地插件",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    onQueryChange = { query = it }
                )
            }
            item { SmallTitle(modifier = Modifier.padding(top = 6.dp), text = "管理") }
            item {
                SettingsCard {
                    ScriptPluginManagerCommandRow(
                        icon = NavIcons.Import,
                        title = "导入插件",
                        summary = "从 ZIP 文件导入，导入后默认关闭",
                        enabled = busyText.isEmpty(),
                        onClick = ::launchImportPicker
                    )
                    InsetDivider()
                    ScriptPluginSelectionToolbar(
                        selectedCount = selectedIds.size,
                        allVisibleSelected = allVisibleSelected,
                        enabled = visibleIds.isNotEmpty() && busyText.isEmpty(),
                        onSelectAll = {
                            selectedIds = if (allVisibleSelected) {
                                selectedIds - visibleIds
                            } else {
                                selectedIds + visibleIds
                            }
                        },
                        onInvert = {
                            selectedIds = (selectedIds - visibleIds) + (visibleIds - selectedIds)
                        }
                    )
                    if (selectedIds.isNotEmpty()) {
                        InsetDivider()
                        ScriptPluginBatchActions(
                            enabled = busyText.isEmpty(),
                            onPin = { setPinned(selectedIds, true) },
                            onUnpin = { setPinned(selectedIds, false) },
                            onExport = {
                                val selected = managedPlugins.filter { it.plugin.id in selectedIds }.map { it.plugin }
                                launchScriptPluginExport(context, selected)
                            },
                            onDelete = {
                                deletePlugins = managedPlugins
                                    .filter { it.plugin.id in selectedIds }
                                    .map { it.plugin }
                            }
                        )
                    }
                }
            }
            if (busyText.isNotEmpty()) {
                item {
                    Text(
                        text = busyText,
                        color = MiuixTheme.colorScheme.primary,
                        fontSize = 13.sp,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 12.dp)
                    )
                }
            }
            when {
                loading -> item { EmptyText("正在加载插件") }
                managedPlugins.isEmpty() -> item { EmptyText("暂无本地插件") }
                visiblePlugins.isEmpty() -> item { EmptyText("没有匹配的插件") }
                else -> {
                    if (pinnedPlugins.isNotEmpty()) {
                        item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "已置顶(${pinnedPlugins.size})") }
                        item {
                            SettingsCard {
                                pinnedPlugins.forEachIndexed { index, item ->
                                    key(item.plugin.id) {
                                        ScriptPluginManagerRow(
                                            modifier = Modifier,
                                            item = item,
                                            selected = item.plugin.id in selectedIds,
                                            dragEnabled = query.isBlank() && busyText.isEmpty(),
                                            onSelectedChange = { selected ->
                                                selectedIds = if (selected) {
                                                    selectedIds + item.plugin.id
                                                } else {
                                                    selectedIds - item.plugin.id
                                                }
                                            },
                                            onOpenActions = { actionPlugin = item },
                                            onDragMove = { delta -> movePlugin(item, delta) },
                                            onDragStateChange = { dragging ->
                                                if (dragging) {
                                                    draggedPluginId = item.plugin.id
                                                    dragOrderChanged = false
                                                } else if (draggedPluginId == item.plugin.id) {
                                                    draggedPluginId = null
                                                    if (dragOrderChanged) commitOrder()
                                                }
                                            },
                                            onOrderChanged = { dragOrderChanged = true }
                                        )
                                        if (index != pinnedPlugins.lastIndex) InsetDivider()
                                    }
                                }
                            }
                        }
                    }
                    if (normalPlugins.isNotEmpty()) {
                        item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "其他插件(${normalPlugins.size})") }
                        item {
                            SettingsCard {
                                normalPlugins.forEachIndexed { index, item ->
                                    key(item.plugin.id) {
                                        ScriptPluginManagerRow(
                                            modifier = Modifier,
                                            item = item,
                                            selected = item.plugin.id in selectedIds,
                                            dragEnabled = query.isBlank() && busyText.isEmpty(),
                                            onSelectedChange = { selected ->
                                                selectedIds = if (selected) {
                                                    selectedIds + item.plugin.id
                                                } else {
                                                    selectedIds - item.plugin.id
                                                }
                                            },
                                            onOpenActions = { actionPlugin = item },
                                            onDragMove = { delta -> movePlugin(item, delta) },
                                            onDragStateChange = { dragging ->
                                                if (dragging) {
                                                    draggedPluginId = item.plugin.id
                                                    dragOrderChanged = false
                                                } else if (draggedPluginId == item.plugin.id) {
                                                    draggedPluginId = null
                                                    if (dragOrderChanged) commitOrder()
                                                }
                                            },
                                            onOrderChanged = { dragOrderChanged = true }
                                        )
                                        if (index != normalPlugins.lastIndex) InsetDivider()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    actionPlugin?.let { item ->
        ScriptPluginActionDialog(
            plugin = item.plugin,
            pinned = item.pinned,
            onDismiss = { actionPlugin = null },
            onPinChanged = { pinned ->
                actionPlugin = null
                setPinned(setOf(item.plugin.id), pinned)
            },
            onRename = {
                actionPlugin = null
                showAfterDialogDismiss(context) { renamePlugin = item.plugin }
            },
            onExport = {
                actionPlugin = null
                showAfterDialogDismiss(context) {
                    launchScriptPluginExport(context, listOf(item.plugin))
                }
            },
            onDelete = {
                actionPlugin = null
                showAfterDialogDismiss(context) { deletePlugins = listOf(item.plugin) }
            }
        )
    }
    renamePlugin?.let { plugin ->
        ScriptPluginRenameDialog(
            plugin = plugin,
            onDismiss = { renamePlugin = null },
            onConfirm = { name ->
                renamePlugin = null
                busyText = "正在重命名"
                scope.launch {
                    val result = withContext(Dispatchers.IO) {
                        ScriptPluginManager.renamePlugin(context, plugin.id, name)
                    }
                    busyText = ""
                    result.fold(
                        onSuccess = {
                            refresh()
                            Toast.makeText(context, "已重命名", Toast.LENGTH_SHORT).show()
                        },
                        onFailure = { showScriptPluginManagerError(context, it) }
                    )
                }
            }
        )
    }
    if (deletePlugins.isNotEmpty()) {
        val pendingDelete = deletePlugins
        ScriptPluginDeleteDialog(
            pluginNames = pendingDelete.map { it.displayName ?: it.name.ifBlank { it.id } },
            onDismiss = { deletePlugins = emptyList() },
            onConfirm = {
                deletePlugins = emptyList()
                busyText = "正在删除插件"
                scope.launch {
                    val result = withContext(Dispatchers.IO) {
                        ScriptPluginManager.deletePlugins(context, pendingDelete.map { it.id })
                    }
                    busyText = ""
                    result.fold(
                        onSuccess = {
                            selectedIds = selectedIds - pendingDelete.mapTo(LinkedHashSet()) { it.id }
                            refresh()
                            Toast.makeText(context, "已删除 ${pendingDelete.size} 个插件", Toast.LENGTH_SHORT).show()
                        },
                        onFailure = { showScriptPluginManagerError(context, it) }
                    )
                }
            }
        )
    }
    importInspection?.let { inspection ->
        ScriptPluginImportDialog(
            inspection = inspection,
            overwriteIds = importOverwriteIds,
            applying = busyText == "正在导入插件",
            onOverwriteChanged = { pluginId, overwrite ->
                importOverwriteIds = if (overwrite) {
                    importOverwriteIds + pluginId
                } else {
                    importOverwriteIds - pluginId
                }
            },
            onDismiss = {
                activeImportSessionId.set(null)
                importInspection = null
                Thread(
                    { ScriptPluginManager.discardImport(context, inspection.sessionId) },
                    "Hchat-Plugin-Import-Discard"
                ).start()
            },
            onConfirm = {
                busyText = "正在导入插件"
                scope.launch {
                    val actions = inspection.conflicts.associateWith { pluginId ->
                        if (pluginId in importOverwriteIds) {
                            ScriptPluginManager.ImportConflictAction.OVERWRITE
                        } else {
                            ScriptPluginManager.ImportConflictAction.SKIP
                        }
                    }
                    val result = withContext(Dispatchers.IO) {
                        ScriptPluginManager.applyImport(context, inspection.sessionId, actions)
                    }
                    busyText = ""
                    result.fold(
                        onSuccess = { imported ->
                            activeImportSessionId.set(null)
                            importInspection = null
                            importOverwriteIds = emptySet()
                            refresh()
                            Toast.makeText(
                                context,
                                "已导入 ${imported.importedPluginIds.size} 个插件，保持关闭状态",
                                Toast.LENGTH_LONG
                            ).show()
                        },
                        onFailure = { showScriptPluginManagerError(context, it) }
                    )
                }
            }
        )
    }
}

@Composable
internal fun ScriptPluginSelectionToolbar(
    selectedCount: Int,
    allVisibleSelected: Boolean,
    enabled: Boolean,
    onSelectAll: () -> Unit,
    onInvert: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = if (selectedCount == 0) "未选择插件" else "已选择 $selectedCount 个",
            color = MiuixTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
        TextButton(
            text = if (allVisibleSelected) "取消全选" else "全选",
            enabled = enabled,
            onClick = onSelectAll,
            colors = ButtonDefaults.textButtonColorsPrimary()
        )
        TextButton(
            text = "反选",
            enabled = enabled,
            onClick = onInvert,
            colors = ButtonDefaults.textButtonColorsPrimary()
        )
    }
}

@Composable
internal fun ScriptPluginBatchActions(
    enabled: Boolean,
    onPin: () -> Unit,
    onUnpin: () -> Unit,
    onExport: () -> Unit,
    onDelete: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ScriptPluginCompactAction("置顶", enabled, Modifier.weight(1f), onPin)
            ScriptPluginCompactAction("取消置顶", enabled, Modifier.weight(1f), onUnpin)
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ScriptPluginCompactAction("导出", enabled, Modifier.weight(1f), onExport)
            ScriptPluginCompactAction("删除", enabled, Modifier.weight(1f), onDelete, destructive = true)
        }
    }
}

@Composable
internal fun ScriptPluginCompactAction(
    text: String,
    enabled: Boolean,
    modifier: Modifier,
    onClick: () -> Unit,
    destructive: Boolean = false
) {
    val color = when {
        !enabled -> MiuixTheme.colorScheme.onSurfaceVariantSummary.copy(alpha = 0.45f)
        destructive -> Color(0xFFD93025)
        else -> MiuixTheme.colorScheme.primary
    }
    Box(
        modifier = modifier.height(40.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color.copy(alpha = 0.10f))
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = color, fontSize = 13.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
internal fun ScriptPluginManagerCommandRow(
    icon: ImageVector,
    title: String,
    summary: String,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val color = if (enabled) MiuixTheme.colorScheme.onSurface else {
        MiuixTheme.colorScheme.onSurfaceVariantSummary.copy(alpha = 0.45f)
    }
    Row(
        modifier = Modifier.fillMaxWidth().clickable(enabled = enabled, onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector = icon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(color),
            modifier = Modifier.size(22.dp)
        )
        Column(modifier = Modifier.weight(1f).padding(start = 14.dp)) {
            Text(text = title, color = color, fontWeight = FontWeight.Medium)
            Text(text = summary, color = MiuixTheme.colorScheme.onSurfaceVariantSummary, fontSize = 12.sp)
        }
    }
}

@Composable
internal fun ScriptPluginManagerRow(
    modifier: Modifier,
    item: ScriptPluginManager.ManagedPlugin,
    selected: Boolean,
    dragEnabled: Boolean,
    onSelectedChange: (Boolean) -> Unit,
    onOpenActions: () -> Unit,
    onDragMove: (Int) -> Boolean,
    onDragStateChange: (Boolean) -> Unit,
    onOrderChanged: () -> Unit
) {
    val plugin = item.plugin
    val density = LocalDensity.current
    var itemHeightPx by remember(plugin.id) { mutableStateOf(0f) }
    var dragDistanceY by remember(plugin.id) { mutableStateOf(0f) }
    var dragging by remember(plugin.id) { mutableStateOf(false) }
    val currentOnDragMove by rememberUpdatedState(onDragMove)
    val currentOnDragStateChange by rememberUpdatedState(onDragStateChange)
    val currentOnOrderChanged by rememberUpdatedState(onOrderChanged)
    val visualDragY by animateFloatAsState(
        targetValue = dragDistanceY,
        animationSpec = tween(durationMillis = if (dragging) 0 else 160),
        label = "ScriptPluginDragOffset"
    )
    Row(
        modifier = modifier.zIndex(if (dragging) 1f else 0f)
            .graphicsLayer {
                translationY = visualDragY
                shadowElevation = if (dragging) with(density) { 7.dp.toPx() } else 0f
                shape = RoundedCornerShape(8.dp)
            }
            .fillMaxWidth()
            .onSizeChanged { itemHeightPx = it.height.toFloat() }
            .clickable { onSelectedChange(!selected) }
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SelectionMark(selected = selected, multiSelect = true)
        Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = plugin.displayName ?: plugin.name.ifBlank { plugin.id },
                    color = MiuixTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, fill = false)
                )
                if (item.pinned) {
                    Image(
                        imageVector = NavIcons.Pin,
                        contentDescription = "已置顶",
                        colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.primary),
                        modifier = Modifier.padding(start = 6.dp).size(16.dp)
                    )
                }
            }
            Text(
                text = buildString {
                    append(plugin.id)
                    append(" · ").append(plugin.author.ifBlank { "未知作者" })
                    append(" · ").append(plugin.version.ifBlank { "未知版本" })
                },
                color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Image(
            imageVector = NavIcons.More,
            contentDescription = "管理插件",
            colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.onSurfaceVariantSummary),
            modifier = Modifier.padding(start = 8.dp).size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    onOpenActions()
                }
                .padding(5.dp)
        )
        Image(
            imageVector = NavIcons.Drag,
            contentDescription = if (dragEnabled) "长按拖动排序" else "搜索时不可排序",
            colorFilter = ColorFilter.tint(
                MiuixTheme.colorScheme.onSurfaceVariantSummary.copy(alpha = if (dragEnabled) 1f else 0.35f)
            ),
            modifier = Modifier.padding(start = 4.dp).size(32.dp)
                .pointerInput(plugin.id, dragEnabled, itemHeightPx) {
                    if (!dragEnabled) return@pointerInput
                    detectDragGesturesAfterLongPress(
                        onDragStart = {
                            dragDistanceY = 0f
                            dragging = true
                            currentOnDragStateChange(true)
                        },
                        onDragEnd = {
                            dragging = false
                            dragDistanceY = 0f
                            currentOnDragStateChange(false)
                        },
                        onDragCancel = {
                            dragging = false
                            dragDistanceY = 0f
                            currentOnDragStateChange(false)
                        },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            dragDistanceY += dragAmount.y
                            val step = itemHeightPx.takeIf { it > 0f } ?: with(density) { 62.dp.toPx() }
                            while (dragDistanceY <= -step * 0.5f) {
                                if (currentOnDragMove(-1)) {
                                    dragDistanceY += step
                                    currentOnOrderChanged()
                                } else {
                                    dragDistanceY = -step * 0.45f
                                    break
                                }
                            }
                            while (dragDistanceY >= step * 0.5f) {
                                if (currentOnDragMove(1)) {
                                    dragDistanceY -= step
                                    currentOnOrderChanged()
                                } else {
                                    dragDistanceY = step * 0.45f
                                    break
                                }
                            }
                        }
                    )
                }
                .padding(5.dp)
        )
    }
}

@Composable
internal fun ScriptPluginActionDialog(
    plugin: ScriptPluginRuntime.ScriptPlugin,
    pinned: Boolean,
    onDismiss: () -> Unit,
    onPinChanged: (Boolean) -> Unit,
    onRename: () -> Unit,
    onExport: () -> Unit,
    onDelete: () -> Unit
) {
    WindowDialog(
        show = true,
        title = plugin.displayName ?: plugin.name.ifBlank { plugin.id },
        onDismissRequest = onDismiss,
        content = {
            Column {
                ScriptPluginDialogActionRow(
                    icon = NavIcons.Pin,
                    text = if (pinned) "取消置顶" else "置顶",
                    onClick = { onPinChanged(!pinned) }
                )
                ScriptPluginDialogActionRow(NavIcons.Edit, "重命名", onClick = onRename)
                ScriptPluginDialogActionRow(NavIcons.Export, "导出", onClick = onExport)
                ScriptPluginDialogActionRow(NavIcons.Delete, "删除", destructive = true, onClick = onDelete)
            }
        }
    )
}

@Composable
internal fun ScriptPluginDialogActionRow(
    icon: ImageVector,
    text: String,
    destructive: Boolean = false,
    onClick: () -> Unit
) {
    val color = if (destructive) Color(0xFFD93025) else MiuixTheme.colorScheme.onSurface
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(
                if (destructive) Color(0xFFD93025).copy(alpha = 0.08f)
                else MiuixTheme.colorScheme.secondaryVariant
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 18.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector = icon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(color),
            modifier = Modifier.size(22.dp)
        )
        Text(text = text, color = color, fontSize = 15.sp, modifier = Modifier.padding(start = 18.dp))
    }
}

@Composable
internal fun ScriptPluginRenameDialog(
    plugin: ScriptPluginRuntime.ScriptPlugin,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var name by remember(plugin.id) {
        mutableStateOf(plugin.displayName ?: plugin.name.ifBlank { plugin.id })
    }
    WindowDialog(
        show = true,
        title = "重命名插件",
        onDismissRequest = onDismiss,
        content = {
            Column {
                Text(
                    text = "仅修改展示名称，插件目录和 ID 保持不变",
                    color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                    fontSize = 12.sp
                )
                BasicTextField(
                    value = name,
                    onValueChange = { name = it.take(100) },
                    singleLine = true,
                    textStyle = TextStyle(color = MiuixTheme.colorScheme.onSurface, fontSize = 15.sp),
                    cursorBrush = SolidColor(MiuixTheme.colorScheme.primary),
                    modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MiuixTheme.colorScheme.secondaryVariant)
                        .padding(horizontal = 12.dp, vertical = 11.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    TextButton(
                        text = "取消",
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.textButtonColorsPrimary()
                    )
                    TextButton(
                        text = "确定",
                        onClick = { onConfirm(name.trim()) },
                        enabled = name.trim().isNotEmpty(),
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.textButtonColorsPrimary()
                    )
                }
            }
        }
    )
}

@Composable
internal fun ScriptPluginDeleteDialog(
    pluginNames: List<String>,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    WindowDialog(
        show = true,
        title = if (pluginNames.size == 1) "删除插件" else "批量删除插件",
        onDismissRequest = onDismiss,
        content = {
            Column {
                Text(
                    text = if (pluginNames.size == 1) {
                        "确定删除“${pluginNames.first()}”吗？插件会先停止运行，删除后无法恢复。"
                    } else {
                        "确定删除已选的 ${pluginNames.size} 个插件吗？插件会先停止运行，删除后无法恢复。"
                    },
                    color = MiuixTheme.colorScheme.onSurface,
                    fontSize = 14.sp
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 14.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    TextButton(
                        text = "取消",
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.textButtonColorsPrimary()
                    )
                    TextButton(
                        text = "删除",
                        onClick = onConfirm,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.textButtonColorsPrimary()
                    )
                }
            }
        }
    )
}

@Composable
internal fun ScriptPluginImportDialog(
    inspection: ScriptPluginManager.ImportInspection,
    overwriteIds: Set<String>,
    applying: Boolean,
    onOverwriteChanged: (String, Boolean) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    WindowDialog(
        show = true,
        title = "导入插件",
        onDismissRequest = { if (!applying) onDismiss() },
        content = {
            Column(modifier = Modifier.fillMaxWidth().heightIn(max = 560.dp)) {
                Text(
                    text = "共 ${inspection.plugins.size} 个插件。已有同 ID 插件默认跳过，可单独选择覆盖。",
                    color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                    fontSize = 12.sp
                )
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().weight(1f, fill = false).padding(top = 10.dp)
                ) {
                    items(inspection.plugins, key = { it.pluginId }) { plugin ->
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .clickable(enabled = !applying && plugin.conflict) {
                                    onOverwriteChanged(plugin.pluginId, plugin.pluginId !in overwriteIds)
                                }
                                .padding(horizontal = 10.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = plugin.name.ifBlank { plugin.pluginId },
                                    color = MiuixTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Medium,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = if (plugin.conflict) {
                                        if (plugin.pluginId in overwriteIds) "覆盖已有插件" else "跳过已有插件"
                                    } else {
                                        "新增插件"
                                    },
                                    color = if (plugin.conflict && plugin.pluginId in overwriteIds) {
                                        Color(0xFFD93025)
                                    } else {
                                        MiuixTheme.colorScheme.onSurfaceVariantSummary
                                    },
                                    fontSize = 12.sp
                                )
                            }
                            if (plugin.conflict) {
                                SelectionMark(
                                    selected = plugin.pluginId in overwriteIds,
                                    multiSelect = true
                                )
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    TextButton(
                        text = "取消",
                        onClick = onDismiss,
                        enabled = !applying,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.textButtonColorsPrimary()
                    )
                    TextButton(
                        text = if (applying) "正在导入" else "导入",
                        onClick = onConfirm,
                        enabled = !applying,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.textButtonColorsPrimary()
                    )
                }
            }
        }
    )
}

internal fun launchScriptPluginExport(
    context: Context,
    plugins: List<ScriptPluginRuntime.ScriptPlugin>
) {
    if (plugins.isEmpty()) {
        Toast.makeText(context, "未选择插件", Toast.LENGTH_SHORT).show()
        return
    }
    val activity = context as? Activity
    if (activity == null) {
        Toast.makeText(context, "无法打开文件选择器", Toast.LENGTH_SHORT).show()
        return
    }
    val fileName = scriptPluginExportFileName(plugins)
    ScriptPluginDocumentBridge.launchExport(activity, fileName) { uri ->
        Thread({
            val result = runCatching {
                context.contentResolver.openOutputStream(uri, "w")?.use { output ->
                    ScriptPluginManager.exportPlugins(context, plugins.map { it.id }, output).getOrThrow()
                } ?: error("无法写入所选文件")
            }
            if (result.isFailure) runCatching { context.contentResolver.delete(uri, null, null) }
            Handler(Looper.getMainLooper()).post {
                result.fold(
                    onSuccess = { exported ->
                        Toast.makeText(
                            context,
                            "已导出 ${exported.exportedPluginIds.size} 个插件",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    onFailure = { showScriptPluginManagerError(context, it) }
                )
            }
        }, "Hchat-Plugin-Export").start()
    }
}

internal fun scriptPluginExportFileName(plugins: List<ScriptPluginRuntime.ScriptPlugin>): String {
    val base = if (plugins.size == 1) {
        plugins.first().displayName ?: plugins.first().name.ifBlank { plugins.first().id }
    } else {
        "Hchat_脚本插件_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())}"
    }
    val safe = base.removeSuffix(".zip")
        .replace(Regex("""[\\/:*?\"<>|]"""), "_")
        .trim()
        .ifBlank { "Hchat_脚本插件" }
    return "$safe.zip"
}

internal fun showAfterDialogDismiss(context: Context, action: () -> Unit) {
    val activity = context as? Activity
    if (activity != null) {
        activity.window.decorView.postOnAnimation(action)
    } else {
        Handler(Looper.getMainLooper()).post(action)
    }
}

internal fun showScriptPluginManagerError(context: Context, error: Throwable) {
    h.Hchat.utils.HLog.e("[Hchat:ScriptManager] ${error.message ?: "操作失败"}", error)
    Toast.makeText(context, error.message ?: "操作失败", Toast.LENGTH_LONG).show()
}

@Composable
internal fun PathSwitchRow(
    checked: Boolean,
    title: String,
    summary: String,
    onInfoClick: () -> Unit,
    onCheckedChange: (Boolean) -> Unit
) {
    var pressed by remember { mutableStateOf(false) }
    val pressFeedbackColor = rememberPressFeedbackColor(pressed)
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(pressFeedbackColor)
                .responsiveTap(
                    onClick = onInfoClick,
                    onPressedChange = { pressed = it }
                )
                .padding(horizontal = 12.dp, vertical = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = title,
                    color = MiuixTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f, fill = false)
                )
                ClickHintTag()
            }
            Text(text = summary, color = MiuixTheme.colorScheme.onSurfaceVariantSummary, fontSize = 12.sp)
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}

@Composable
internal fun ScriptPluginRow(
    checked: Boolean,
    title: String,
    summary: String,
    showSettings: Boolean,
    onOpenReadme: () -> Unit,
    onOpenSettings: () -> Unit,
    onOpenManager: () -> Unit,
    onCheckedChange: (Boolean) -> Unit
) {
    var pressed by remember { mutableStateOf(false) }
    val pressFeedbackColor = rememberPressFeedbackColor(pressed)
    Row(
        modifier = Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(pressFeedbackColor)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
                .responsiveTap(
                    onClick = onOpenReadme,
                    onPressedChange = { pressed = it }
                )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = title,
                    color = MiuixTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f, fill = false)
                )
                ClickHintTag()
            }
            Text(text = summary, color = MiuixTheme.colorScheme.onSurfaceVariantSummary, fontSize = 12.sp)
        }
        if (showSettings) {
            Image(
                imageVector = NavIcons.Settings,
                contentDescription = "插件设置",
                colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.onSurfaceVariantSummary),
                modifier = Modifier
                    .padding(start = 10.dp, end = 8.dp)
                    .size(22.dp)
                    .responsiveTap(onClick = onOpenSettings)
            )
        }
        Image(
            imageVector = NavIcons.More,
            contentDescription = "管理插件",
            colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.onSurfaceVariantSummary),
            modifier = Modifier
                .padding(start = if (showSettings) 0.dp else 10.dp, end = 8.dp)
                .size(22.dp)
                .responsiveTap(onClick = onOpenManager)
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
internal fun ScriptPluginPathDialog(
    context: Context,
    title: String,
    path: String,
    onClose: () -> Unit
) {
    WindowDialog(
        show = true,
        title = title,
        onDismissRequest = onClose,
        content = {
            Column {
                Text(
                    text = path,
                    color = MiuixTheme.colorScheme.onSurface,
                    fontSize = 13.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MiuixTheme.colorScheme.surfaceVariant)
                        .padding(horizontal = 12.dp, vertical = 10.dp)
                )
                TextButton(
                    text = "复制路径",
                    onClick = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? android.content.ClipboardManager
                        clipboard?.setPrimaryClip(ClipData.newPlainText("HchatScriptDir", path))
                        Toast.makeText(context, "已复制", Toast.LENGTH_SHORT).show()
                        onClose()
                    },
                    modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                    colors = ButtonDefaults.textButtonColorsPrimary()
                )
                TextButton(
                    text = "关闭",
                    onClick = onClose,
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    colors = ButtonDefaults.textButtonColorsPrimary()
                )
            }
        }
    )
}

@Composable
fun FirstUseAgreementDialog(
    context: Context,
    onCancel: () -> Unit,
    onAccepted: () -> Unit
) {
    var input by remember { mutableStateOf("") }
    var remainingSeconds by remember { mutableStateOf(30) }
    val acceptedText = TermsGate.AGREEMENT_TEXT
    LaunchedEffect(Unit) {
        while (remainingSeconds > 0) {
            delay(1000L)
            remainingSeconds -= 1
        }
    }
    val agreement = remember {
        """
        Hchat 使用协议与免责声明

        1. 本模块为免费模块，仅供个人学习、研究、测试、逆向分析和备份用途使用，不提供任何商业授权、售后承诺或稳定性保证。
        2. 本模块与微信、腾讯及其关联主体无关，不代表官方立场，也不是官方客户端、官方插件或官方服务的一部分。
        3. 禁止倒卖、付费分发、捆绑销售、二次打包收费、引流售卖、以捐赠名义收费，禁止冒充作者、官方渠道或授权代理发布。
        4. 请勿在国内公开平台、群组、论坛、短视频平台、网盘分享页、应用市场或其它公开渠道传播、推广、引流、售卖或组织分发本模块。
        5. 本模块可能会修改微信运行时行为，使用后可能出现功能异常、消息异常、账号风控、限制登录、数据异常、闪退、掉线、模块冲突或其它不可预期问题。
        6. 使用者应自行确认所在地法律法规、平台协议、设备环境和账号风险；因安装、使用、传播、修改、二次分发或与其它模块共存产生的任何后果均由使用者自行承担。
        7. 禁止将本模块用于骚扰、欺诈、刷量、营销轰炸、盗取信息、破坏服务稳定性、绕过平台风控、侵犯他人权益或其它违法违规用途。
        8. 本模块不保证适配所有微信版本、系统版本、设备环境、热更新状态和其它模块共存环境，也不承诺持续维护、及时修复或提供任何形式的服务保障。
        9. 如果你不同意以上任一条款，请点击取消并停止使用本模块。

        如果你理解并接受以上内容，请在下方输入“$acceptedText”后继续使用。
        """.trimIndent()
    }
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val dialogMaxHeight = (screenHeight * 0.78f).coerceAtMost(640.dp)
    val agreementMaxHeight = (screenHeight * 0.42f).coerceIn(220.dp, 430.dp)
    WindowDialog(
        show = true,
        title = "使用协议",
        onDismissRequest = {},
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = dialogMaxHeight)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = agreementMaxHeight)
                        .verticalScroll(rememberScrollState())
                        .clip(RoundedCornerShape(10.dp))
                        .background(MiuixTheme.colorScheme.surfaceVariant)
                        .padding(horizontal = 14.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = agreement,
                        color = MiuixTheme.colorScheme.onSurface,
                        fontSize = 13.sp,
                        lineHeight = 20.sp
                    )
                }
                Text(
                    text = "请输入“$acceptedText”确认",
                    color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 14.dp)
                )
                BasicTextField(
                    value = input,
                    onValueChange = { input = it },
                    singleLine = true,
                    textStyle = TextStyle(
                        color = MiuixTheme.colorScheme.onSurface,
                        fontSize = 15.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MiuixTheme.colorScheme.secondaryVariant)
                        .padding(horizontal = 12.dp, vertical = 11.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    TextButton(
                        text = "取消",
                        onClick = onCancel,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.textButtonColorsPrimary()
                    )
                    TextButton(
                        text = if (remainingSeconds > 0) "继续（${remainingSeconds}s）" else "同意并继续",
                        onClick = {
                            if (remainingSeconds > 0) {
                                Toast.makeText(context, "请等待 ${remainingSeconds} 秒后继续", Toast.LENGTH_SHORT).show()
                            } else if (input.trim() == acceptedText) {
                                if (TermsGate.accept(context)) {
                                    onAccepted()
                                } else {
                                    Toast.makeText(context, "协议状态保存失败，请重试", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(context, "请输入“$acceptedText”后继续", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.textButtonColorsPrimary()
                    )
                }
            }
        }
    )
}

@Composable
fun ScriptPluginReadmeDialog(
    context: Context,
    plugin: ScriptPluginRuntime.ScriptPlugin,
    onClose: () -> Unit
) {
    val readme = remember(plugin.id) {
        runCatching {
            val file = File(plugin.dir, "README.md")
            if (file.isFile) file.readText(Charsets.UTF_8) else ""
        }.getOrDefault("")
    }
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val dialogMaxHeight = (screenHeight * 0.78f).coerceAtMost(640.dp)
    val readmeMaxHeight = (screenHeight * 0.58f).coerceIn(240.dp, 520.dp)

    WindowDialog(
        show = true,
        title = plugin.displayName ?: "未知",
        onDismissRequest = onClose,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = dialogMaxHeight)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = readmeMaxHeight)
                        .verticalScroll(rememberScrollState())
                ) {
                    if (readme.isBlank()) {
                        Text(
                            text = "暂无说明",
                            color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    } else {
                        MarkdownUi.Content(context, readme)
                    }
                }
                TextButton(
                    text = "关闭",
                    onClick = onClose,
                    modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                    colors = ButtonDefaults.textButtonColorsPrimary()
                )
            }
        }
    )
}

}

internal object MarkdownUi {

@Composable
fun Content(
    context: Context,
    markdown: String,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
    bodyFontSize: androidx.compose.ui.unit.TextUnit = 13.sp,
    onCopyCode: ((String) -> Unit)? = null
) {
    val lines = markdown.replace("\r\n", "\n").replace('\r', '\n').lines()
    var inCodeBlock = false
    var inlineState = MarkdownInlineState()
    val codeLines = ArrayList<String>()
    Column(modifier = Modifier.fillMaxWidth().padding(contentPadding)) {
        lines.forEach { rawLine ->
            val line = rawLine.trimEnd()
            if (line.trimStart().startsWith("```")) {
                if (inCodeBlock) {
                    CodeBlock(codeLines.joinToString("\n"), onCopyCode)
                    codeLines.clear()
                    inCodeBlock = false
                } else {
                    inCodeBlock = true
                    codeLines.clear()
                }
                return@forEach
            }
            if (inCodeBlock) {
                codeLines += rawLine
                return@forEach
            }
            val nextState = MarkdownLine(context, line, inlineState, bodyFontSize)
            inlineState = nextState
        }
        if (inCodeBlock && codeLines.isNotEmpty()) {
            CodeBlock(codeLines.joinToString("\n"), onCopyCode)
        }
    }
}

@Composable
internal fun MarkdownLine(
    context: Context,
    line: String,
    inlineState: MarkdownInlineState,
    bodyFontSize: androidx.compose.ui.unit.TextUnit = 13.sp
): MarkdownInlineState {
    val trimmed = line.trim()
    return when {
        trimmed.isBlank() -> {
            Box(modifier = Modifier.height(8.dp))
            inlineState
        }
        trimmed.matches(Regex("""-{3,}|_{3,}|\*{3,}""")) -> {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .height(1.dp)
                    .background(MiuixTheme.colorScheme.outline)
            )
            inlineState
        }
        trimmed.startsWith("#") -> {
            val level = trimmed.takeWhile { it == '#' }.length.coerceIn(1, 6)
            val text = trimmed.drop(level).trim()
            MarkdownTextResult(
                context = context,
                text = text,
                inlineState = inlineState,
                modifier = Modifier.padding(top = if (level <= 2) 10.dp else 8.dp, bottom = 4.dp),
                color = MiuixTheme.colorScheme.onSurface,
                fontSize = when (level) {
                    1 -> 22.sp
                    2 -> 19.sp
                    3 -> 17.sp
                    else -> 15.sp
                },
                fontWeight = FontWeight.SemiBold
            )
        }
        trimmed.startsWith(">") -> {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            ) {
                Box(
                    modifier = Modifier.padding(end = 8.dp)
                        .size(width = 3.dp, height = 20.dp)
                        .background(MiuixTheme.colorScheme.primary)
                )
                MarkdownTextResult(
                    context = context,
                    text = trimmed.removePrefix(">").trim(),
                    inlineState = inlineState,
                    color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                    fontSize = bodyFontSize,
                    modifier = Modifier.weight(1f)
                )
            }
            inlineState
        }
        trimmed.startsWith("- ") || trimmed.startsWith("* ") || trimmed.startsWith("+ ") -> {
            MarkdownBullet(context, trimmed.drop(2).trim(), inlineState, fontSize = bodyFontSize)
        }
        Regex("""^\d+[.)]\s+.*""").matches(trimmed) -> {
            val marker = trimmed.substringBefore(' ').trim()
            MarkdownBullet(context, trimmed.removePrefix(marker).trim(), inlineState, marker, bodyFontSize)
        }
        else -> {
            MarkdownTextResult(
                context = context,
                text = line,
                inlineState = inlineState,
                fontSize = bodyFontSize,
                modifier = Modifier.padding(vertical = 2.dp)
            )
        }
    }
}

@Composable
internal fun MarkdownBullet(
    context: Context,
    text: String,
    inlineState: MarkdownInlineState,
    marker: String = "•",
    fontSize: androidx.compose.ui.unit.TextUnit = 13.sp
): MarkdownInlineState {
    var nextState = inlineState
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)) {
        Text(
            text = marker,
            color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
            fontSize = fontSize,
            modifier = Modifier.padding(end = 8.dp)
        )
        nextState = MarkdownTextResult(
            context = context,
            text = text,
            inlineState = inlineState,
            fontSize = fontSize,
            modifier = Modifier.weight(1f)
        )
    }
    return nextState
}

@Composable
fun CodeBlock(code: String, onCopy: ((String) -> Unit)? = null) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MiuixTheme.colorScheme.surfaceVariant)
    ) {
        if (onCopy != null) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 10.dp, top = 4.dp, end = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "代码",
                    color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                    fontSize = 11.sp,
                    modifier = Modifier.weight(1f)
                )
                Image(
                    imageVector = NavIcons.Copy,
                    contentDescription = "复制代码",
                    colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.onSurfaceVariantSummary),
                    modifier = Modifier.size(26.dp).clip(RoundedCornerShape(4.dp))
                        .clickable { onCopy(code) }
                        .padding(6.dp)
                )
            }
        }
        Text(
            text = code,
            color = MiuixTheme.colorScheme.onSurface,
            fontSize = 12.sp,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun Text(
    context: Context,
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MiuixTheme.colorScheme.onSurface,
    fontSize: androidx.compose.ui.unit.TextUnit = 13.sp,
    fontWeight: FontWeight? = null
) {
    MarkdownTextResult(context, text, MarkdownInlineState(), modifier, color, fontSize, fontWeight)
}

@Composable
internal fun MarkdownTextResult(
    context: Context,
    text: String,
    inlineState: MarkdownInlineState,
    modifier: Modifier = Modifier,
    color: Color = MiuixTheme.colorScheme.onSurface,
    fontSize: androidx.compose.ui.unit.TextUnit = 13.sp,
    fontWeight: FontWeight? = null
): MarkdownInlineState {
    val primary = MiuixTheme.colorScheme.primary
    val result = remember(text, primary, inlineState.bold) {
        buildMarkdownAnnotatedString(text, primary, inlineState)
    }
    ClickableText(
        text = result.text,
        modifier = modifier,
        style = TextStyle(color = color, fontSize = fontSize, fontWeight = fontWeight),
        onClick = { offset ->
            result.text.getStringAnnotations(MARKDOWN_LINK_TAG, offset, offset)
                .firstOrNull()
                ?.let { annotation -> openMarkdownLink(context, annotation.item) }
        }
    )
    return result.state
}

internal data class MarkdownInlineState(val bold: Boolean = false)

internal data class MarkdownInlineResult(
    val text: AnnotatedString,
    val state: MarkdownInlineState
)

internal fun buildMarkdownAnnotatedString(
    text: String,
    accent: Color,
    initialState: MarkdownInlineState
): MarkdownInlineResult {
    var state = initialState
    val annotated = buildAnnotatedString {
        state = appendInlineMarkdown(text, accent, state)
    }
    return MarkdownInlineResult(annotated, state)
}

internal fun AnnotatedString.Builder.appendInlineMarkdown(
    text: String,
    accent: Color,
    initialState: MarkdownInlineState
): MarkdownInlineState {
    var state = initialState
    var segmentStart = 0
    MARKDOWN_LINK_REGEX.findAll(text).forEach { match ->
        if (match.range.first > segmentStart) {
            state = appendInlineMarkdownSegment(
                text.substring(segmentStart, match.range.first),
                accent,
                state
            )
        }
        val label = match.groupValues[1]
        val url = match.groupValues[2].trim()
        if (label.isNotBlank() && url.isNotBlank()) {
            pushStringAnnotation(MARKDOWN_LINK_TAG, url)
            appendStyledInline(label, accent, state, link = true)
            pop()
        } else {
            append(match.value)
        }
        segmentStart = match.range.last + 1
    }
    if (segmentStart < text.length) {
        state = appendInlineMarkdownSegment(text.substring(segmentStart), accent, state)
    }
    return state
}

internal fun AnnotatedString.Builder.appendInlineMarkdownSegment(
    text: String,
    accent: Color,
    initialState: MarkdownInlineState
): MarkdownInlineState {
    var state = initialState
    var index = 0
    while (index < text.length) {
        when {
            text.startsWith("**", index) -> {
                state = state.copy(bold = !state.bold)
                index += 2
            }
            text[index] == '`' -> {
                val end = text.indexOf('`', index + 1)
                if (end > index) {
                    withStyle(SpanStyle(color = accent, fontFamily = FontFamily.Monospace)) {
                        append(text.substring(index + 1, end))
                    }
                    index = end + 1
                } else {
                    append(text[index])
                    index++
                }
            }
            else -> {
                appendStyledInline(text[index].toString(), accent, state, link = false)
                index++
            }
        }
    }
    return state
}

internal fun AnnotatedString.Builder.appendStyledInline(
    value: String,
    accent: Color,
    state: MarkdownInlineState,
    link: Boolean
) {
    val style = SpanStyle(
        color = if (link) accent else Color.Unspecified,
        fontWeight = when {
            link -> FontWeight.Medium
            state.bold -> FontWeight.SemiBold
            else -> null
        }
    )
    withStyle(style) {
        append(value)
    }
}

internal fun openMarkdownLink(context: Context, url: String) {
    val value = url.trim()
    if (value.isBlank()) return
    runCatching {
        val normalized = if (value.contains("://")) value else "https://$value"
        val uri = Uri.parse(normalized)
        val intent = Intent(Intent.ACTION_VIEW, uri)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}

}

internal data class ColorPickerSelection(val hue: Float, val saturation: Float, val value: Float)

internal const val MODULE_UPDATE_PREFS = "hchat_module_updates"

internal const val MODULE_UPDATE_IGNORED_CODE = "ignored_version_code"

internal data class ModuleVersionHistory(
    val versionName: String,
    val versionCode: Int,
    val releaseNotes: String,
    val size: Long,
    val downloadUrl: String
) {
    companion object {
        fun fromJson(value: JSONObject, baseUrl: String): ModuleVersionHistory = ModuleVersionHistory(
            value.optString("versionName"),
            value.optInt("versionCode"),
            value.optString("releaseNotes"),
            value.optLong("size"),
            value.optString("apkUrl").let { if (it.startsWith("http")) it else baseUrl.trimEnd('/') + it }
        )
    }
}

internal data class ModuleUpdateInfo(val versionName: String, val versionCode: Int, val releaseNotes: String, val downloadUrl: String) {
    companion object {
        fun fromJson(value: JSONObject, baseUrl: String): ModuleUpdateInfo = ModuleUpdateInfo(
            value.optString("versionName"), value.optInt("versionCode"), value.optString("releaseNotes"),
            value.optString("apkUrl").let { if (it.startsWith("http")) it else baseUrl.trimEnd('/') + it }
        )
    }
}

internal class EmbeddedComposeOwner : LifecycleOwner, SavedStateRegistryOwner, ViewModelStoreOwner,
    NavigationEventDispatcherOwner {
    private val lifecycleRegistry = LifecycleRegistry(this)
    private val savedStateRegistryController = SavedStateRegistryController.create(this)
    private val store = ViewModelStore()
    private val navigationDispatcher = NavigationEventDispatcher()
    private val composeRuntime = lazy(LazyThreadSafetyMode.NONE) {
        EmbeddedComposeRuntime(lifecycleRegistry)
    }
    private var restored = false

    override val lifecycle: Lifecycle
        get() = lifecycleRegistry

    override val savedStateRegistry: SavedStateRegistry
        get() = savedStateRegistryController.savedStateRegistry

    override val viewModelStore: ViewModelStore
        get() = store

    override val navigationEventDispatcher: NavigationEventDispatcher
        get() = navigationDispatcher

    fun install(view: View) {
        EmbeddedComposeOwnerInstaller.install(view, this, this, this, this)
    }

    fun clear(view: View) {
        EmbeddedComposeOwnerInstaller.clear(view)
    }

    fun installComposition(view: ComposeView) {
        composeRuntime.value.install(view)
    }

    fun attach() {
        if (!restored) {
            savedStateRegistryController.performRestore(Bundle.EMPTY)
            restored = true
        }
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
        lifecycleRegistry.currentState = Lifecycle.State.STARTED
        lifecycleRegistry.currentState = Lifecycle.State.RESUMED
    }

    fun destroy() {
        if (lifecycleRegistry.currentState != Lifecycle.State.DESTROYED) {
            lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
        }
        if (composeRuntime.isInitialized()) composeRuntime.value.destroy()
        navigationDispatcher.dispose()
        store.clear()
    }
}

internal enum class ContactPickerMode {
    FRIENDS,
    GROUPS,
    BOTH,
    ALL_CHATS
}

internal enum class ContactPickerFilter(
    val label: String,
    val title: String
) {
    FRIENDS("好友", "好友"),
    GROUPS("群聊", "群聊"),
    OFFICIALS("公众号", "公众号"),
    LABELS("标签", "标签好友"),
    ALL("全部", "全部")
}

internal enum class ContactPickerScopeTab(val label: String) {
    FRIENDS("好友"),
    LABELS("标签"),
    CONVERSATION_GROUPS("聊天分组")
}

internal data class ContactPickerScope(
    val name: String,
    val memberIds: Set<String>
)

internal enum class ConversationRuleCategory(val label: String) {
    OFFICIAL("公众号"),
    GROUP("群聊"),
    FRIEND("好友"),
    ALL("全部")
}

internal enum class MessageBlockContactKind {
    FRIEND,
    GROUP,
    OFFICIAL
}

internal enum class MessageBlockContactFilter(
    val label: String,
    val title: String
) {
    FRIENDS("好友", "好友"),
    GROUPS("群聊", "群聊"),
    OFFICIALS("公众号", "公众号"),
    LABELS("标签", "标签好友"),
    ALL("全部", "全部")
}

internal data class ContactPickerRequest(
    val title: String,
    val mode: ContactPickerMode,
    val multiSelect: Boolean,
    val existingValue: String,
    val onValue: (String) -> Unit,
    val enableLabels: Boolean = false,
    val enableGroupLabels: Boolean = true,
    val enableScopeSelection: Boolean = false,
    val singleConfirmText: String = "发送"
)

internal enum class AutoMessageForwardRoute {
    MAIN,
    RULES,
    EDITOR,
    CONTACTS,
    MEMBERS,
    TYPES,
    REPLACEMENTS
}

internal enum class AutoMessageForwardContactField {
    SOURCES,
    TARGETS
}

internal data class FavoritePickerRequest(
    val title: String,
    val existingValue: String,
    val onValue: (String) -> Unit,
    val multiSelect: Boolean = true,
    val delimiter: String = "|"
)

internal enum class FavoritePickerFilter(val label: String) {
    IMAGE("图片"),
    TEXT("文字"),
    VOICE("语音"),
    VIDEO("视频"),
    OTHER("其他"),
    ALL("全部");

    fun matches(type: Int): Boolean = when (this) {
        IMAGE -> type == 2
        TEXT -> type == 1
        VOICE -> type == 3
        VIDEO -> type == 4
        OTHER -> type !in setOf(1, 2, 3, 4)
        ALL -> true
    }
}

internal data class FavoriteMediaPreview(
    val item: WeChatFavoriteItem,
    val path: String
)

internal data class MessageBlockContactPickerRequest(
    val title: String,
    val existingValue: String,
    val onValue: (String) -> Unit,
    val allowOfficialAccounts: Boolean = true
)

internal data class GroupMemberPickerRequest(
    val title: String,
    val existingValue: String,
    val onValue: (String) -> Unit,
    val allowedGroupIds: Set<String>? = null
)

internal data class RedPacketTemplateEditorRequest(
    val index: Int,
    val template: RedPacketRuleTemplate,
    val canDelete: Boolean
)

internal data class RedPacketBindingEditorRequest(
    val index: Int,
    val binding: RedPacketRuleBinding,
    val canDelete: Boolean
)

internal data class TransferTemplateEditorRequest(
    val index: Int,
    val template: TransferRuleTemplate,
    val canDelete: Boolean
)

internal data class TransferBindingEditorRequest(
    val index: Int,
    val binding: TransferRuleBinding,
    val canDelete: Boolean
)

internal data class MessageBlockTemplateEditorRequest(
    val index: Int,
    val template: MessageBlockTemplate,
    val canDelete: Boolean
)

internal data class MessageBlockBindingEditorRequest(
    val index: Int,
    val binding: MessageBlockBinding,
    val canDelete: Boolean
)

internal data class MessageBlockBatchBindingEditorRequest(
    val title: String,
    val bindings: List<MessageBlockBinding>
)

internal data class GroupLeaveTemplateEditorRequest(
    val index: Int,
    val template: GroupLeaveReplyTemplate,
    val canDelete: Boolean
)

internal data class MessageBlockTypeOption(
    val key: String,
    val title: String,
    val summary: String
)

internal data class MessageBlockRuleState(
    val typeAll: Boolean,
    val types: Set<String>,
    val textKeywords: String
)

internal data class OptionPickerRequest(
    val title: String,
    val options: List<OptionItem>,
    val currentValue: Int,
    val onSelected: (OptionItem) -> Unit
)

internal data class OptionItem(
    val label: String,
    val value: Int,
    val summary: String = ""
)

internal data class PopupChoice<T>(
    val label: String,
    val value: T
)

internal data class ContactOption(
    val id: String,
    val label: String,
    val group: Boolean,
    val avatarUrl: String,
    val avatarBackupUrl: String,
    val labels: List<String> = emptyList(),
    val official: Boolean = false,
    val extraSummary: String = "",
    val searchAliases: List<String> = emptyList()
)

internal data class MessageBlockContactOption(
    val contact: ContactOption,
    val kind: MessageBlockContactKind,
    val labelNames: List<String> = emptyList()
)

internal data class MessageBlockLabelOption(
    val id: String,
    val name: String,
    val contactIds: List<String>
)

internal data class MessageBlockContactData(
    val contacts: List<MessageBlockContactOption> = emptyList(),
    val labels: List<MessageBlockLabelOption> = emptyList()
)

internal data class MessageBlockInitialState(
    val templates: List<MessageBlockTemplate>,
    val bindings: List<MessageBlockBinding>,
    val shouldPersist: Boolean
)

internal const val FAVORITE_BACKGROUND_BATCH_DELAY_MS = 40L
