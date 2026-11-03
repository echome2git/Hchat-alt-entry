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

// 从 MiuixSettingsPage.kt 拆出的顶层函数。
// 保持同包且使用 internal，避免 MiuixSettingsPageKt 超过 JVM 类文件上限。

internal fun isLiquidGlassSupported(): Boolean = Build.VERSION.SDK_INT >= 33

@Composable
internal fun RegisterSettingsBackHandler(onBack: (() -> Unit)?) {
    val registry = LocalSettingsBackHandlerRegistry.current
    val currentOnBack by rememberUpdatedState(onBack)
    val token = remember { Any() }
    DisposableEffect(registry, token, onBack != null) {
        if (registry != null && onBack != null) {
            registry.register(token) { currentOnBack?.invoke() }
        }
        onDispose { registry?.unregister(token) }
    }
}

internal fun Intent.preferSystemDocumentsUi(context: Context): Intent {
    val candidates = listOf(
        "com.google.android.documentsui",
        "com.android.documentsui"
    )
    for (packageName in candidates) {
        val copy = Intent(this).setPackage(packageName)
        val resolved = runCatching {
            context.packageManager.queryIntentActivities(copy, 0)
        }.getOrDefault(emptyList())
        if (resolved.isNotEmpty()) {
            setPackage(packageName)
            break
        }
    }
    return this
}

@Composable
internal fun Modifier.responsiveTap(
    onClick: () -> Unit,
    onPressedChange: (Boolean) -> Unit = {}
): Modifier {
    val currentOnClick by rememberUpdatedState(onClick)
    val currentOnPressedChange by rememberUpdatedState(onPressedChange)
    return pointerInput(Unit) {
        awaitEachGesture {
            awaitFirstDown(requireUnconsumed = false)
            currentOnPressedChange(true)
            val up = waitForUpOrCancellation()
            currentOnPressedChange(false)
            if (up != null) {
                currentOnClick()
            }
        }
    }
}

@Composable
internal fun rememberPressFeedbackColor(pressed: Boolean): Color {
    var feedbackVisible by remember { mutableStateOf(false) }
    LaunchedEffect(pressed) {
        if (pressed) {
            feedbackVisible = true
        } else {
            delay(PRESS_RELEASE_DELAY_MS)
            feedbackVisible = false
        }
    }
    val target = if (feedbackVisible) {
        MiuixTheme.colorScheme.onSurface.copy(alpha = 0.075f)
    } else {
        Color.Transparent
    }
    return animateColorAsState(
        targetValue = target,
        animationSpec = tween(durationMillis = if (pressed) 90 else 210),
        label = "PressFeedback"
    ).value
}

@Composable
internal fun HchatMiuixTheme(context: Context, content: @Composable () -> Unit) {
    val colors = if (isDarkMode(context)) {
        darkColorScheme()
    } else {
        lightColorScheme()
    }
    MiuixTheme(colors = colors, content = content)
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun MainSettingsPage(
    context: Context,
    providers: List<FeatureSettingsProvider>,
    listState: LazyListState,
    selectedTab: MainTab,
    configVersion: Int,
    onSelectedTab: (MainTab) -> Unit,
    onOpenSearch: () -> Unit,
    onOpenProvider: (FeatureSettingsProvider) -> Unit,
    onOpenGroup: (FeatureGroupEntry) -> Unit,
    onOpenScriptPluginReadme: (ScriptPluginRuntime.ScriptPlugin) -> Unit,
    onOpenScriptPluginAgent: () -> Unit,
    onOpenScriptPluginMarket: () -> Unit,
    onOpenScriptPluginManager: () -> Unit,
    onConfigImported: () -> Unit
) {
    val uiPrefs = remember(configVersion) { HchatStorage.preferences(context, UI_PREFS_NAME) }
    val glassSupported = remember { isLiquidGlassSupported() }
    var floatingNav by remember(configVersion) { mutableStateOf(uiPrefs.getBoolean(KEY_FLOATING_NAV, true)) }
    var glassNav by remember(configVersion) { mutableStateOf(glassSupported && uiPrefs.getBoolean(KEY_GLASS_NAV, true)) }
    val entertainmentListState = rememberLazyListState()
    val pluginListState = rememberLazyListState()
    val settingsListState = rememberLazyListState()
    val practicalProviders = providers.filter { it.category() == FeatureSettingsProvider.CATEGORY_PRACTICAL }
    val entertainmentProviders = providers.filter { it.category() == FeatureSettingsProvider.CATEGORY_ENTERTAINMENT }
    val enhanceProviders = providers.filter {
        it.category() == FeatureSettingsProvider.CATEGORY_ENHANCE &&
            it.featureId() != ScriptPluginFeature.ID
    }
    val practicalGroups = remember(practicalProviders, enhanceProviders) {
        practicalFeatureGroups(practicalProviders, enhanceProviders)
    }
    val entertainmentGroups = remember(entertainmentProviders) {
        entertainmentFeatureGroups(entertainmentProviders)
    }
    val scrollBehavior = MiuixScrollBehavior()
    val scope = rememberCoroutineScope()
    var gateUpdate by remember { mutableStateOf<ModuleUpdateInfo?>(null) }
    var showGateUpdate by remember { mutableStateOf(false) }
    val gatePrefs = remember { HchatStorage.preferences(context, MODULE_UPDATE_PREFS) }
    LaunchedEffect(Unit) {
        val result = withContext(Dispatchers.IO) {
            runCatching {
                val root = PluginMarketClient.moduleUpdate(context).getOrThrow()
                ModuleUpdateInfo.fromJson(root, PluginMarketSettings.serviceUrl(context))
            }
        }
        result.onSuccess { info ->
            gateUpdate = info
            val hasNewerVersion = info.versionCode > BuildConfig.VERSION_CODE
            gatePrefs.edit().putBoolean("has_update", hasNewerVersion).apply()
            if (hasNewerVersion && gatePrefs.getInt(MODULE_UPDATE_IGNORED_CODE, -1) != info.versionCode) {
                showGateUpdate = true
            }
        }
    }
    gateUpdate?.takeIf { showGateUpdate && it.versionCode > BuildConfig.VERSION_CODE }?.let { update ->
        ModuleUpdateDialog(
            context = context,
            activity = context as? Activity,
            update = update,
            prefs = gatePrefs,
            onDismiss = { showGateUpdate = false }
        )
    }
    val tabs = MainTab.entries
    val pagerState = rememberPagerState(
        initialPage = selectedTab.ordinal,
        pageCount = { tabs.size }
    )
    LaunchedEffect(pagerState.currentPage) {
        tabs.getOrNull(pagerState.currentPage)?.let(onSelectedTab)
    }
    val activeTab = tabs.getOrElse(pagerState.currentPage) { MainTab.PRACTICAL }
    PageScaffold(
        title = "Hchat",
        largeTitle = "Hchat",
        scrollBehavior = scrollBehavior,
        bottomBar = {
            MainNavigationBar(
                context = context,
                selectedTab = activeTab,
                floating = floatingNav,
                glass = glassSupported && glassNav,
                backdrop = it,
                onSelected = { tab ->
                    scope.launch { pagerState.springAnimateToPage(tab.ordinal) }
                }
            )
        }
    ) { padding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize(),
            key = { tabs[it] }
        ) { page ->
            val tab = tabs[page]
            val activeListState = when (tab) {
                MainTab.PRACTICAL -> listState
                MainTab.ENTERTAINMENT -> entertainmentListState
                MainTab.PLUGIN -> pluginListState
                MainTab.SETTINGS -> settingsListState
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
                state = activeListState,
                contentPadding = PaddingValues(
                    top = padding.calculateTopPadding() + 8.dp,
                    bottom = padding.calculateBottomPadding() + 78.dp
                )
            ) {
                item {
                    SettingsSearchEntry(onClick = onOpenSearch)
                }
                when (tab) {
                    MainTab.PRACTICAL -> {
                        featureGroupMenu(
                            title = "实用功能",
                            groups = practicalGroups,
                            onOpenGroup = onOpenGroup
                        )
                        if (practicalProviders.isEmpty() && enhanceProviders.isEmpty()) {
                            item { SmallTitle(text = "实用功能") }
                            item { FeatureListCard(emptyList(), "暂无实用功能", onOpenProvider) }
                        }
                    }
                    MainTab.ENTERTAINMENT -> {
                        featureGroupMenu(
                            title = "娱乐功能",
                            groups = entertainmentGroups,
                            onOpenGroup = onOpenGroup
                        )
                        if (entertainmentProviders.isEmpty()) {
                            item { SmallTitle(text = "娱乐功能") }
                            item { FeatureListCard(emptyList(), "暂无娱乐功能", onOpenProvider) }
                        }
                    }
                    MainTab.PLUGIN -> {
                        item { SmallTitle(text = "脚本插件") }
                        item {
                            ScriptPluginSettingsMiuixContent.ScriptPluginSettingsContent(
                                context,
                                onOpenScriptPluginReadme,
                                onOpenScriptPluginMarket,
                                onOpenScriptPluginAgent,
                                onOpenScriptPluginManager
                            )
                        }
                    }
                    MainTab.SETTINGS -> {
                        item { SmallTitle(text = "设置") }
                        item {
                            SettingsCard {
                                SwitchRow(
                                    checked = floatingNav,
                                    title = "悬浮底栏",
                                    summary = "使用悬浮样式的底部导航栏",
                                    onCheckedChange = {
                                        floatingNav = it
                                        uiPrefs.edit().putBoolean(KEY_FLOATING_NAV, it).apply()
                                    }
                                )
                                InsetDivider()
                                SwitchRow(
                                    checked = glassSupported && glassNav,
                                    title = "液态玻璃",
                                    summary = if (glassSupported) {
                                        "启用悬浮底栏的液态玻璃效果"
                                    } else {
                                        "Android 13 以下不支持液态玻璃效果"
                                    },
                                    enabled = glassSupported,
                                    onCheckedChange = {
                                        if (!glassSupported) return@SwitchRow
                                        glassNav = it
                                        uiPrefs.edit().putBoolean(KEY_GLASS_NAV, it).apply()
                                    }
                                )
                            }
                        }
                        item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "关于") }
                        item { AboutCard(context) }
                        item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "配置") }
                        item {
                            ConfigBackupCard(
                                context = context,
                                onImported = onConfigImported
                            )
                        }
                        item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "群组") }
                        item { GroupLinksCard(context) }
                        item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "致谢") }
                        item { ThanksCard(context) }
                        item {
                            Text(
                                modifier = Modifier.fillMaxWidth().padding(top = 18.dp),
                                text = "Hchat",
                                color = MiuixTheme.colorScheme.onBackgroundVariant,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

internal fun List<FeatureSettingsProvider>.filterByIds(vararg ids: String): List<FeatureSettingsProvider> {
    val order = ids.toList()
    return filter { it.featureId() in order }
        .sortedBy { order.indexOf(it.featureId()) }
}

internal fun practicalFeatureGroups(
    practicalProviders: List<FeatureSettingsProvider>,
    enhanceProviders: List<FeatureSettingsProvider>
): List<FeatureGroupEntry> {
    return listOf(
        FeatureGroupEntry(
            title = "聊天",
            providers = practicalProviders.filterByIds(
                AntiRecallFeature.ID,
                MultiRecallFeature.ID,
                AutoReplyFeature.ID,
                MessageAffixFeature.ID,
                TypingReportBlockFeature.ID,
                PatBlockFeature.ID,
                AutoOriginalImageFeature.ID,
                AutoViewOriginalFeature.ID,
                RemoveForwardLimitFeature.ID,
                MessageBlockFeature.ID,
                QuickMarkReadFeature.ID,
                MessageDetailsSettingsProvider.FEATURE_ID,
                QuoteDeleteClearFeature.ID,
                SwipeQuoteFeature.ID,
                EmojiSaveFeature.ID,
                CallMediaLimitFeature.ID,
                CallRingtoneBlockFeature.ID
            )
        ),
        FeatureGroupEntry(
            title = "语音",
            providers = practicalProviders.filterByIds(
                AudioTransformFeature.ID,
                TextVoiceFeature.ID,
                FakeVoiceDurationFeature.ID,
                VoicePreviewFeature.ID,
                VoiceForwardFeature.ID
            )
        ),
        FeatureGroupEntry(
            title = "红包转账",
            providers = practicalProviders.filterByIds(
                AutoRedPacketFeature.ID,
                AutoGiftFeature.ID,
                AutoTransferFeature.ID,
                FakeWalletBalanceFeature.ID,
                RedPacketDetailsSettingsProvider.FEATURE_ID
            )
        ),
        FeatureGroupEntry(title = "增强", providers = enhanceProviders),
        FeatureGroupEntry(
            title = "群组",
            providers = practicalProviders.filterByIds(
                GroupChatLabelFeature.ID,
                AtAllNotificationBlockFeature.ID,
                GroupLeaveMonitorFeature.ID,
                GroupRenameMonitorFeature.ID
            )
        ),
        FeatureGroupEntry(
            title = "朋友圈",
            providers = practicalProviders.filterByIds(
                MomentsAutoLikeFeature.ID,
                MomentsAutoCommentFeature.ID,
                MomentsAutoForwardFeature.ID,
                MomentsAutoRefreshFeature.ID,
                MomentsContactFilterFeature.ID,
                MomentsKeywordBlockFeature.ID,
                MomentsBottomDetailFeature.ID,
                MomentsPostNotificationFeature.ID,
                OriginalMomentsUploadFeature.ID,
                MomentsUploadTailFeature.ID,
                SnsAntiRecallFeature.ID,
                MomentsFakeLikeSettingsProvider.ID,
                MomentsFakeCommentSettingsProvider.ID,
                MomentsFakeForwardSettingsProvider.ID,
                RemoveMomentsAdsFeature.ID
            )
        ),
        FeatureGroupEntry(
            title = "美化",
            providers = practicalProviders.filterByIds(
                MessageBubbleFeature.ID,
                MessageTextColorFeature.ID,
                HomeTextColorFeature.ID,
                ChatTimeStyleFeature.ID,
                InputHintFeature.ID,
                HideChatAvatarFeature.ID,
                RoundAvatarFeature.ID,
                CustomFriendAvatarFeature.ID,
                CustomBottomBarFeature.ID,
                FloatingBottomBarSettings.FEATURE_ID
            )
        ),
        FeatureGroupEntry(
            title = "界面",
            providers = practicalProviders.filterByIds(
                SettingsFeature.ID,
                PluginAgentEntryProvider.ID,
                FloatingShortcutFeature.ID,
                ProfileIdFeature.ID,
                GroupMemberHistorySettingsProvider.FEATURE_ID,
                QuickContactEditFeature.ID,
                QuickGroupChatLabelFeature.ID,
                QuickMomentsFeature.ID,
                QuickTerminateFeature.ID,
                HideChatMenuFeature.ID
            )
        ),
        FeatureGroupEntry(
            title = "杂项",
            providers = practicalProviders.filterByIds(
                WeChatKeepAliveFeature.ID,
                WeChatTabletFeature.ID,
                DisableHotUpdateFeature.ID,
                FakeMiniProgramBaseLibFeature.ID,
                SkipMiniProgramVideoAdsFeature.ID,
                SkipGlobalMiniProgramSplashAdsFeature.ID,
                FakeLocationFeature.ID,
                GameEmojiFeature.ID,
                FakeScanCameraFeature.ID,
                FinderMediaDownloadFeature.ID,
                SkipWebRiskSettingsProvider.FEATURE_ID
            )
        )
    ).filter { it.providers.isNotEmpty() }
}

internal fun entertainmentFeatureGroups(
    entertainmentProviders: List<FeatureSettingsProvider>
): List<FeatureGroupEntry> {
    return listOf(
        FeatureGroupEntry(
            title = "点歌",
            providers = entertainmentProviders.filterByIds(QQMusicOrderFeature.ID)
        ),
        FeatureGroupEntry(
            title = "群聊",
            providers = entertainmentProviders.filterByIds(
                RealNameTailFeature.ID,
                GroupNicknameColorSettings.FEATURE_ID,
                MemberTitleFeature.ID
            )
        ),
        FeatureGroupEntry(
            title = "状态",
            providers = entertainmentProviders.filterByIds(StatusTextLimitFeature.ID)
        ),
        FeatureGroupEntry(
            title = "聊天",
            providers = entertainmentProviders.filterByIds(EditMessageFeature.ID)
        ),
        FeatureGroupEntry(
            title = "抓包",
            providers = entertainmentProviders.filterByIds(ProtobufPacketFeature.ID)
        ),
        FeatureGroupEntry(
            title = "调试",
            providers = entertainmentProviders.filterByIds(CrashReportSettingsProvider.ID)
        )
    ).filter { it.providers.isNotEmpty() }
}

@Composable
internal fun SettingsSearchEntry(onClick: () -> Unit) {
    SearchBarSurface(
        query = "",
        placeholder = "搜索功能和插件",
        readOnly = true,
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
        onClick = onClick,
        onQueryChange = {}
    )
}

@Composable
internal fun SettingsSearchPage(
    context: Context,
    providers: List<FeatureSettingsProvider>,
    onBack: () -> Unit,
    onOpenProvider: (FeatureSettingsProvider, FeatureGroupEntry?) -> Unit,
    onOpenScriptPluginReadme: (ScriptPluginRuntime.ScriptPlugin) -> Unit
) {
    var query by rememberSaveable { mutableStateOf("") }
    var pluginListVersion by remember { mutableStateOf(0) }
    val uiPrefs = remember { HchatStorage.preferences(context, UI_PREFS_NAME) }
    var searchHistory by remember { mutableStateOf(readSearchHistory(uiPrefs)) }
    val practicalProviders = providers.filter { it.category() == FeatureSettingsProvider.CATEGORY_PRACTICAL }
    val entertainmentProviders = providers.filter { it.category() == FeatureSettingsProvider.CATEGORY_ENTERTAINMENT }
    val enhanceProviders = providers.filter {
        it.category() == FeatureSettingsProvider.CATEGORY_ENHANCE &&
            it.featureId() != ScriptPluginFeature.ID
    }
    val groups = remember(practicalProviders, enhanceProviders, entertainmentProviders) {
        practicalFeatureGroups(practicalProviders, enhanceProviders) +
            entertainmentFeatureGroups(entertainmentProviders)
    }
    val featureResults = remember(query, groups, providers) {
        filterFeatureSearchResults(query, groups, providers)
    }
    val plugins = remember(pluginListVersion) {
        ScriptPluginManager.listForDisplay(context).map { it.plugin }
    }
    val pluginResults = remember(query, plugins) { filterPluginSearchResults(query, plugins) }
    val listState = rememberLazyListState()
    val focusRequester = remember { FocusRequester() }
    fun recordSearch() {
        val next = rememberSearchKeyword(uiPrefs, query)
        searchHistory = next
    }
    DisposableEffect(context) {
        val subscription = ScriptPluginRuntime.subscribePluginCatalog(context) {
            Handler(Looper.getMainLooper()).post { pluginListVersion++ }
        }
        onDispose { subscription.unsubscribe() }
    }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MiuixTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState,
            contentPadding = PaddingValues(
                top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 74.dp,
                bottom = navigationButtonBottomInset() + NAVIGATION_BUTTON_EXTRA_GAP + 24.dp
            )
        ) {
            if (query.isBlank()) {
                item { SearchHistorySection(searchHistory, onSelect = { query = it }, onClear = {
                    saveSearchHistory(uiPrefs, emptyList())
                    searchHistory = emptyList()
                }) }
            } else {
                item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "功能(${featureResults.size})") }
                item {
                    FeatureSearchResultCard(
                        results = featureResults,
                        onOpenProvider = { provider, group ->
                            recordSearch()
                            onOpenProvider(provider, group)
                        }
                    )
                }
                item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "插件(${pluginResults.size})") }
                item {
                    PluginSearchResultCard(
                        plugins = pluginResults,
                        onOpenReadme = { plugin ->
                            recordSearch()
                            onOpenScriptPluginReadme(plugin)
                        }
                    )
                }
            }
        }
        SearchPageTopBar(
            query = query,
            focusRequester = focusRequester,
            onQueryChange = { query = it },
            onCancel = onBack
        )
    }
}

@Composable
internal fun SearchPageTopBar(
    query: String,
    focusRequester: FocusRequester,
    onQueryChange: (String) -> Unit,
    onCancel: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .background(MiuixTheme.colorScheme.background)
            .padding(
                top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 8.dp,
                start = 12.dp,
                end = 12.dp,
                bottom = 8.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SearchBarSurface(
            query = query,
            placeholder = "搜索功能和插件",
            modifier = Modifier.weight(1f),
            focusRequester = focusRequester,
            onQueryChange = onQueryChange
        )
        Text(
            text = "取消",
            color = MiuixTheme.colorScheme.primary,
            fontSize = 16.sp,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .clickable { onCancel() }
                .padding(horizontal = 4.dp, vertical = 10.dp)
        )
    }
}

@Composable
internal fun SearchBarSurface(
    query: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    focusRequester: FocusRequester? = null,
    onClick: (() -> Unit)? = null,
    onQueryChange: (String) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(MiuixTheme.colorScheme.secondaryVariant)
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector = NavIcons.Search,
            contentDescription = null,
            modifier = Modifier.size(22.dp),
            colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.onSurfaceVariantSummary)
        )
        if (readOnly) {
            Text(
                text = query.ifEmpty { placeholder },
                color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f).padding(start = 10.dp)
            )
        } else {
            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                singleLine = true,
                textStyle = TextStyle(
                    color = MiuixTheme.colorScheme.onSurface,
                    fontSize = 16.sp
                ),
                cursorBrush = SolidColor(MiuixTheme.colorScheme.primary),
                modifier = Modifier.weight(1f)
                    .then(if (focusRequester != null) Modifier.focusRequester(focusRequester) else Modifier)
                    .padding(start = 10.dp),
                decorationBox = { innerTextField ->
                    Box(contentAlignment = Alignment.CenterStart) {
                        if (query.isEmpty()) {
                            Text(
                                text = placeholder,
                                color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                                fontSize = 16.sp
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }
    }
}

@Composable
internal fun SearchHistorySection(
    history: List<String>,
    onSelect: (String) -> Unit,
    onClear: () -> Unit
) {
    if (history.isEmpty()) {
        EmptyText("暂无搜索记录")
        return
    }
    SmallTitle(text = "搜索记录")
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        history.chunked(3).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { keyword ->
                    ContactFilterChip(
                        text = keyword,
                        selected = false,
                        onClick = { onSelect(keyword) },
                        modifier = Modifier.weight(1f)
                    )
                }
                repeat(3 - row.size) {
                    Box(modifier = Modifier.weight(1f))
                }
            }
        }
    }
    Text(
        text = "清除搜索记录",
        color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
        fontSize = 14.sp,
        modifier = Modifier.fillMaxWidth()
            .padding(top = 18.dp)
            .clickable { onClear() },
        textAlign = TextAlign.Center
    )
}

@Composable
internal fun FeatureSearchResultCard(
    results: List<FeatureSearchResult>,
    onOpenProvider: (FeatureSettingsProvider, FeatureGroupEntry?) -> Unit
) {
    SettingsCard {
        if (results.isEmpty()) {
            EmptyText("没有匹配功能")
        } else {
            results.forEachIndexed { index, result ->
                FeatureSearchResultRow(
                    result = result,
                    onClick = { onOpenProvider(result.provider, result.group) }
                )
                if (index < results.lastIndex) {
                    InsetDivider()
                }
            }
        }
    }
}

@Composable
internal fun FeatureSearchResultRow(result: FeatureSearchResult, onClick: () -> Unit) {
    var pressed by remember { mutableStateOf(false) }
    val pressFeedbackColor = rememberPressFeedbackColor(pressed)
    Row(
        modifier = Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(pressFeedbackColor)
            .responsiveTap(
                onClick = onClick,
                onPressedChange = { pressed = it }
            )
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = result.provider.title(), color = MiuixTheme.colorScheme.onSurface, fontWeight = FontWeight.Medium)
            Text(
                text = result.summary,
                color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                fontSize = 12.sp
            )
        }
        Text(text = "›", color = MiuixTheme.colorScheme.onSurfaceVariantSummary, fontSize = 22.sp)
    }
}

@Composable
internal fun PluginSearchResultCard(
    plugins: List<ScriptPluginRuntime.ScriptPlugin>,
    onOpenReadme: (ScriptPluginRuntime.ScriptPlugin) -> Unit
) {
    SettingsCard {
        if (plugins.isEmpty()) {
            EmptyText("没有匹配插件")
        } else {
            plugins.forEachIndexed { index, plugin ->
                PluginSearchResultRow(
                    plugin = plugin,
                    onClick = { onOpenReadme(plugin) }
                )
                if (index < plugins.lastIndex) {
                    InsetDivider()
                }
            }
        }
    }
}

@Composable
internal fun PluginSearchResultRow(plugin: ScriptPluginRuntime.ScriptPlugin, onClick: () -> Unit) {
    var pressed by remember { mutableStateOf(false) }
    val pressFeedbackColor = rememberPressFeedbackColor(pressed)
    val title = buildString {
        append(plugin.displayName ?: plugin.name.ifBlank { plugin.dir.name })
        if (plugin.version.isNotBlank()) {
            append("(")
            append(plugin.version)
            append(")")
        }
    }
    val summary = buildString {
        append(plugin.dir.name)
        if (plugin.author.isNotBlank()) {
            append("\n作者: ")
            append(plugin.author)
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(pressFeedbackColor)
            .responsiveTap(
                onClick = onClick,
                onPressedChange = { pressed = it }
            )
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
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
            Text(
                text = summary,
                color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                fontSize = 12.sp
            )
        }
        Text(text = "›", color = MiuixTheme.colorScheme.onSurfaceVariantSummary, fontSize = 22.sp)
    }
}

internal fun LazyListScope.featureGroupMenu(
    title: String,
    groups: List<FeatureGroupEntry>,
    onOpenGroup: (FeatureGroupEntry) -> Unit
) {
    if (groups.isEmpty()) return
    item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = title) }
    item {
        SettingsCard {
            groups.forEachIndexed { index, group ->
                FeatureGroupRow(
                    group = group,
                    onClick = { onOpenGroup(group) }
                )
                if (index < groups.lastIndex) {
                    InsetDivider()
                }
            }
        }
    }
}

@Composable
internal fun FeatureGroupSettingsPage(
    group: FeatureGroupEntry,
    listState: LazyListState,
    onBack: () -> Unit,
    onOpenProvider: (FeatureSettingsProvider) -> Unit
) {
    val scrollBehavior = MiuixScrollBehavior()
    PageScaffold(
        title = group.title,
        largeTitle = group.title,
        scrollBehavior = scrollBehavior,
        bottomBar = {
            BottomActionBar("返回", onBack)
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
            item { FeatureListCard(group.providers, "暂无功能", onOpenProvider) }
        }
    }
}

internal fun filterFeatureSearchResults(
    query: String,
    groups: List<FeatureGroupEntry>,
    allProviders: List<FeatureSettingsProvider>
): List<FeatureSearchResult> {
    val keyword = query.trim()
    if (keyword.isEmpty()) return emptyList()
    val grouped = LinkedHashSet<String>()
    val results = ArrayList<FeatureSearchResult>()
    groups.forEach { group ->
        group.providers.forEach { provider ->
            val matchedTerms = matchedFeatureSubTerms(keyword, provider)
            if (matchesFeature(keyword, provider, group.title) || matchedTerms.isNotEmpty()) {
                grouped.add(provider.featureId())
                results += FeatureSearchResult(
                    provider = provider,
                    group = group,
                    summary = featureSearchSummary(group.title, provider, matchedTerms)
                )
            }
        }
    }
    allProviders.forEach { provider ->
        val matchedTerms = matchedFeatureSubTerms(keyword, provider)
        if (provider.featureId() !in grouped && (matchesFeature(keyword, provider, null) || matchedTerms.isNotEmpty())) {
            results += FeatureSearchResult(
                provider = provider,
                group = null,
                summary = featureSearchSummary(null, provider, matchedTerms)
            )
        }
    }
    return results
}

internal fun featureSearchSummary(
    groupTitle: String?,
    provider: FeatureSettingsProvider,
    matchedTerms: List<String>
): String {
    return buildString {
        if (!groupTitle.isNullOrBlank()) {
            append(groupTitle)
            append(" / ")
        }
        append(provider.subtitle())
        if (matchedTerms.isNotEmpty()) {
            append("\n匹配: ")
            append(matchedTerms.joinToString("、"))
        }
    }
}

internal fun matchesFeature(
    query: String,
    provider: FeatureSettingsProvider,
    groupTitle: String?
): Boolean {
    return containsSearchText(provider.title(), query) ||
        containsSearchText(provider.subtitle(), query) ||
        containsSearchText(provider.featureId(), query) ||
        containsSearchText(groupTitle.orEmpty(), query)
}

internal fun matchedFeatureSubTerms(query: String, provider: FeatureSettingsProvider): List<String> {
    return featureSubSearchTerms(provider.featureId())
        .filter { containsSearchText(it, query) }
        .distinct()
        .take(5)
}
