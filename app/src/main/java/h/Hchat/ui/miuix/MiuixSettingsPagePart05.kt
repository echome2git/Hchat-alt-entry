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

@Composable
internal fun MomentsAutoForwardMainPage(
    provider: FeatureSettingsProvider,
    sp: SharedPreferences,
    listState: LazyListState,
    targetCount: Int,
    replacementRuleCount: Int,
    onBack: () -> Unit,
    onOpenReplacementRules: () -> Unit,
    onPickTargets: () -> Unit
) {
    var enabled by remember {
        mutableStateOf(
            sp.getBoolean(
                MomentsAutoForwardSettings.KEY_ENABLE,
                MomentsAutoForwardSettings.DEFAULT_ENABLE
            )
        )
    }
    var allowText by remember {
        mutableStateOf(
            sp.getBoolean(
                MomentsAutoForwardSettings.KEY_ALLOW_TEXT,
                MomentsAutoForwardSettings.DEFAULT_ALLOW_TEXT
            )
        )
    }
    var allowImage by remember {
        mutableStateOf(
            sp.getBoolean(
                MomentsAutoForwardSettings.KEY_ALLOW_IMAGE,
                MomentsAutoForwardSettings.DEFAULT_ALLOW_IMAGE
            )
        )
    }
    var allowVideo by remember {
        mutableStateOf(
            sp.getBoolean(
                MomentsAutoForwardSettings.KEY_ALLOW_VIDEO,
                MomentsAutoForwardSettings.DEFAULT_ALLOW_VIDEO
            )
        )
    }
    var allowLivePhoto by remember {
        mutableStateOf(
            sp.getBoolean(
                MomentsAutoForwardSettings.KEY_ALLOW_LIVE_PHOTO,
                MomentsAutoForwardSettings.DEFAULT_ALLOW_LIVE_PHOTO
            )
        )
    }
    var allowLink by remember {
        mutableStateOf(
            sp.getBoolean(
                MomentsAutoForwardSettings.KEY_ALLOW_LINK,
                MomentsAutoForwardSettings.DEFAULT_ALLOW_LINK
            )
        )
    }
    var allowMusic by remember {
        mutableStateOf(
            sp.getBoolean(
                MomentsAutoForwardSettings.KEY_ALLOW_MUSIC,
                MomentsAutoForwardSettings.DEFAULT_ALLOW_MUSIC
            )
        )
    }
    var allowOther by remember {
        mutableStateOf(
            sp.getBoolean(
                MomentsAutoForwardSettings.KEY_ALLOW_OTHER,
                MomentsAutoForwardSettings.DEFAULT_ALLOW_OTHER
            )
        )
    }
    var allowUnknown by remember {
        mutableStateOf(
            sp.getBoolean(
                MomentsAutoForwardSettings.KEY_ALLOW_UNKNOWN,
                MomentsAutoForwardSettings.DEFAULT_ALLOW_UNKNOWN
            )
        )
    }
    var delayMode by remember {
        mutableStateOf(
            sp.getInt(
                MomentsAutoForwardSettings.KEY_DELAY_MODE,
                MomentsAutoForwardSettings.DEFAULT_DELAY_MODE
            )
        )
    }
    var fixedDelay by remember {
        mutableStateOf(
            sp.getInt(
                MomentsAutoForwardSettings.KEY_FIXED_DELAY_SECONDS,
                MomentsAutoForwardSettings.DEFAULT_FIXED_DELAY_SECONDS
            ).toString()
        )
    }
    var randomMin by remember {
        mutableStateOf(
            sp.getInt(
                MomentsAutoForwardSettings.KEY_RANDOM_MIN_SECONDS,
                MomentsAutoForwardSettings.DEFAULT_RANDOM_MIN_SECONDS
            ).toString()
        )
    }
    var randomMax by remember {
        mutableStateOf(
            sp.getInt(
                MomentsAutoForwardSettings.KEY_RANDOM_MAX_SECONDS,
                MomentsAutoForwardSettings.DEFAULT_RANDOM_MAX_SECONDS
            ).toString()
        )
    }
    var dailyLimit by remember {
        mutableStateOf(
            sp.getInt(
                MomentsAutoForwardSettings.KEY_DAILY_LIMIT,
                MomentsAutoForwardSettings.DEFAULT_DAILY_LIMIT
            ).toString()
        )
    }
    var includeKeywords by remember {
        mutableStateOf(
            sp.getString(
                MomentsAutoForwardSettings.KEY_INCLUDE_KEYWORDS,
                MomentsAutoForwardSettings.DEFAULT_INCLUDE_KEYWORDS
            ).orEmpty()
        )
    }
    var includeKeywordsEnabled by remember {
        mutableStateOf(MomentsAutoForwardSettings.includeKeywordsEnabled(sp))
    }
    var excludeKeywords by remember {
        mutableStateOf(
            sp.getString(
                MomentsAutoForwardSettings.KEY_EXCLUDE_KEYWORDS,
                MomentsAutoForwardSettings.DEFAULT_EXCLUDE_KEYWORDS
            ).orEmpty()
        )
    }
    var excludeKeywordsEnabled by remember {
        mutableStateOf(MomentsAutoForwardSettings.excludeKeywordsEnabled(sp))
    }
    var replaceKeywordsEnabled by remember {
        mutableStateOf(
            sp.getBoolean(
                MomentsAutoForwardSettings.KEY_REPLACE_KEYWORDS_ENABLE,
                MomentsAutoForwardSettings.DEFAULT_REPLACE_KEYWORDS_ENABLE
            )
        )
    }
    var contentTemplate by remember {
        mutableStateOf(
            sp.getString(
                MomentsAutoForwardSettings.KEY_CONTENT_TEMPLATE,
                MomentsAutoForwardSettings.DEFAULT_CONTENT_TEMPLATE
            ).orEmpty()
        )
    }
    var logEnabled by remember {
        mutableStateOf(
            sp.getBoolean(
                MomentsAutoForwardSettings.KEY_LOG_ENABLE,
                MomentsAutoForwardSettings.DEFAULT_LOG_ENABLE
            )
        )
    }
    var logs by remember {
        mutableStateOf(
            sp.getString(
                MomentsAutoForwardSettings.KEY_LOGS,
                MomentsAutoForwardSettings.DEFAULT_LOGS
            ).orEmpty()
        )
    }
    val templateVariables = remember {
        listOf(
            TemplateVariable(MomentsAutoForwardSettings.VARIABLE_CONTENT, "原文"),
            TemplateVariable(MomentsAutoForwardSettings.VARIABLE_SENDER, "发布者"),
            TemplateVariable(MomentsAutoForwardSettings.VARIABLE_WXID, "发布者ID"),
            TemplateVariable(MomentsAutoForwardSettings.VARIABLE_TYPE, "类型"),
            TemplateVariable(MomentsAutoForwardSettings.VARIABLE_SNS_ID, "朋友圈ID")
        )
    }
    val targetSummary = if (targetCount == 0) {
        "未选择好友，自动转发不会执行"
    } else {
        "已选择 $targetCount 位好友"
    }
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
            item { SmallTitle(text = "自动转发") }
            item {
                SettingsCard {
                    SwitchRow(enabled, "朋友圈自动转发", "按规则静默转发指定好友的新朋友圈") {
                        enabled = it
                        sp.edit()
                            .putBoolean(MomentsAutoForwardSettings.KEY_ENABLE, it)
                            .putLong(
                                MomentsAutoForwardSettings.KEY_ENABLED_AT_SECONDS,
                                if (it) System.currentTimeMillis() / 1000L else 0L
                            )
                            .apply()
                    }
                }
            }
            if (enabled) {
                item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "转发好友") }
                item {
                    SettingsCard {
                        ActionRow("转发好友", targetSummary, onPickTargets)
                    }
                }
                item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "内容类型") }
                item {
                    SettingsCard {
                        SwitchRow(allowText, "文字", "允许自动转发") {
                            allowText = it
                            sp.edit().putBoolean(MomentsAutoForwardSettings.KEY_ALLOW_TEXT, it).apply()
                        }
                        InsetDivider()
                        SwitchRow(allowImage, "图片/图文", "允许自动转发") {
                            allowImage = it
                            sp.edit().putBoolean(MomentsAutoForwardSettings.KEY_ALLOW_IMAGE, it).apply()
                        }
                        InsetDivider()
                        SwitchRow(allowVideo, "视频/视文", "允许自动转发") {
                            allowVideo = it
                            sp.edit().putBoolean(MomentsAutoForwardSettings.KEY_ALLOW_VIDEO, it).apply()
                        }
                        InsetDivider()
                        SwitchRow(allowLivePhoto, "实况照片", "允许自动转发") {
                            allowLivePhoto = it
                            sp.edit().putBoolean(MomentsAutoForwardSettings.KEY_ALLOW_LIVE_PHOTO, it).apply()
                        }
                        InsetDivider()
                        SwitchRow(allowLink, "网页/链接", "按正文和链接转为文字朋友圈") {
                            allowLink = it
                            sp.edit().putBoolean(MomentsAutoForwardSettings.KEY_ALLOW_LINK, it).apply()
                        }
                        InsetDivider()
                        SwitchRow(allowMusic, "音乐", "按正文和链接转为文字朋友圈") {
                            allowMusic = it
                            sp.edit().putBoolean(MomentsAutoForwardSettings.KEY_ALLOW_MUSIC, it).apply()
                        }
                        InsetDivider()
                        SwitchRow(allowOther, "其他卡片", "按可解析内容转为文字朋友圈") {
                            allowOther = it
                            sp.edit().putBoolean(MomentsAutoForwardSettings.KEY_ALLOW_OTHER, it).apply()
                        }
                        InsetDivider()
                        SwitchRow(allowUnknown, "未知类型", "按可解析内容转发，默认关闭") {
                            allowUnknown = it
                            sp.edit().putBoolean(MomentsAutoForwardSettings.KEY_ALLOW_UNKNOWN, it).apply()
                        }
                    }
                }
                item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "执行方式") }
                item {
                    SettingsCard {
                        PopupOptionRow(
                            title = "转发延迟",
                            summary = if (delayMode == MomentsAutoForwardSettings.DELAY_RANDOM) {
                                "在范围内随机等待"
                            } else {
                                "每条等待固定时间"
                            },
                            options = optionItems(
                                "固定延迟" to MomentsAutoForwardSettings.DELAY_FIXED,
                                "随机延迟" to MomentsAutoForwardSettings.DELAY_RANDOM
                            ),
                            currentValue = delayMode,
                            onValueChanged = {
                                delayMode = it
                                sp.edit().putInt(MomentsAutoForwardSettings.KEY_DELAY_MODE, it).apply()
                            }
                        )
                        if (delayMode == MomentsAutoForwardSettings.DELAY_FIXED) {
                            InsetDivider()
                            NumberInputRow("等待时间", "单位秒，最少 0 秒", fixedDelay) {
                                fixedDelay = it
                                it.toIntOrNull()?.let { value ->
                                    sp.edit()
                                        .putInt(
                                            MomentsAutoForwardSettings.KEY_FIXED_DELAY_SECONDS,
                                            value.coerceAtLeast(0)
                                        )
                                        .apply()
                                }
                            }
                        } else {
                            InsetDivider()
                            NumberInputRow("最短等待", "单位秒，最少 0 秒", randomMin) {
                                randomMin = it
                                it.toIntOrNull()?.let { value ->
                                    sp.edit()
                                        .putInt(
                                            MomentsAutoForwardSettings.KEY_RANDOM_MIN_SECONDS,
                                            value.coerceAtLeast(0)
                                        )
                                        .apply()
                                }
                            }
                            InsetDivider()
                            NumberInputRow("最长等待", "单位秒，不能小于最短等待", randomMax) {
                                randomMax = it
                                it.toIntOrNull()?.let { value ->
                                    sp.edit()
                                        .putInt(
                                            MomentsAutoForwardSettings.KEY_RANDOM_MAX_SECONDS,
                                            value.coerceAtLeast(0)
                                        )
                                        .apply()
                                }
                            }
                        }
                        InsetDivider()
                        NumberInputRow("每日转发上限", "每天最多转发的朋友圈数量，0 表示不限制", dailyLimit) {
                            dailyLimit = it
                            it.toIntOrNull()?.let { value ->
                                sp.edit()
                                    .putInt(MomentsAutoForwardSettings.KEY_DAILY_LIMIT, value.coerceAtLeast(0))
                                    .apply()
                            }
                        }
                    }
                }
                item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "内容处理") }
                item {
                    SettingsCard {
                        SwitchRow(
                            includeKeywordsEnabled,
                            "包含关键词筛选",
                            "开启后，正文需命中任意关键词才转发"
                        ) {
                            includeKeywordsEnabled = it
                            sp.edit()
                                .putBoolean(MomentsAutoForwardSettings.KEY_INCLUDE_KEYWORDS_ENABLE, it)
                                .apply()
                        }
                        if (includeKeywordsEnabled) {
                            InsetDivider()
                            InputRow(
                                "包含关键词",
                                "多个关键词用逗号或换行分隔",
                                includeKeywords,
                                minLines = 2
                            ) {
                                includeKeywords = it
                                sp.edit().putString(MomentsAutoForwardSettings.KEY_INCLUDE_KEYWORDS, it).apply()
                            }
                        }
                        InsetDivider()
                        SwitchRow(
                            excludeKeywordsEnabled,
                            "排除关键词筛选",
                            "开启后，正文命中任意关键词时不转发，优先于包含规则"
                        ) {
                            excludeKeywordsEnabled = it
                            sp.edit()
                                .putBoolean(MomentsAutoForwardSettings.KEY_EXCLUDE_KEYWORDS_ENABLE, it)
                                .apply()
                        }
                        if (excludeKeywordsEnabled) {
                            InsetDivider()
                            InputRow(
                                "排除关键词",
                                "多个关键词用逗号或换行分隔",
                                excludeKeywords,
                                minLines = 2
                            ) {
                                excludeKeywords = it
                                sp.edit().putString(MomentsAutoForwardSettings.KEY_EXCLUDE_KEYWORDS, it).apply()
                            }
                        }
                        InsetDivider()
                        SwitchRow(
                            replaceKeywordsEnabled,
                            "替换关键词",
                            "转发前按多条规则替换朋友圈原文"
                        ) {
                            replaceKeywordsEnabled = it
                            sp.edit()
                                .putBoolean(MomentsAutoForwardSettings.KEY_REPLACE_KEYWORDS_ENABLE, it)
                                .apply()
                        }
                        if (replaceKeywordsEnabled) {
                            InsetDivider()
                            ActionRow(
                                "替换规则",
                                if (replacementRuleCount == 0) {
                                    "暂无规则"
                                } else {
                                    "已设置 $replacementRuleCount 条规则"
                                },
                                onOpenReplacementRules
                            )
                        }
                        InsetDivider()
                        VariableInputRow(
                            "文案模板",
                            "支持下方变量",
                            contentTemplate,
                            templateVariables,
                            minLines = 3
                        ) {
                            contentTemplate = it
                            sp.edit().putString(MomentsAutoForwardSettings.KEY_CONTENT_TEMPLATE, it).apply()
                        }
                    }
                }
                item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "运行记录") }
                item {
                    SettingsCard {
                        SwitchRow(logEnabled, "记录运行日志", "记录自动转发执行情况") {
                            logEnabled = it
                            sp.edit().putBoolean(MomentsAutoForwardSettings.KEY_LOG_ENABLE, it).apply()
                        }
                        if (logEnabled) {
                            InsetDivider()
                            ActionRow(
                                "刷新日志",
                                if (logs.isBlank()) "暂无记录" else "${logs.lineSequence().count()} 条记录"
                            ) {
                                logs = sp.getString(
                                    MomentsAutoForwardSettings.KEY_LOGS,
                                    MomentsAutoForwardSettings.DEFAULT_LOGS
                                ).orEmpty()
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
                                ActionRow("清空日志", "删除当前自动转发运行记录") {
                                    sp.edit().remove(MomentsAutoForwardSettings.KEY_LOGS).apply()
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

@Composable
internal fun KeywordReplacementRulesPage(
    context: Context,
    initialRules: List<KeywordReplacementRule>,
    onBack: () -> Unit,
    onSave: (List<KeywordReplacementRule>) -> Unit
) {
    var rules by remember(initialRules) { mutableStateOf(initialRules) }
    val listState = rememberLazyListState()
    val scrollBehavior = MiuixScrollBehavior()

    fun updateRule(index: Int, update: (KeywordReplacementRule) -> KeywordReplacementRule) {
        if (index !in rules.indices) return
        rules = rules.toMutableList().also { next -> next[index] = update(next[index]) }
    }

    fun save() {
        val normalized = rules.map { it.copy(keyword = it.keyword.trim()) }
        if (normalized.any { it.keyword.isEmpty() }) {
            Toast.makeText(context, "原关键词不能为空", Toast.LENGTH_SHORT).show()
            return
        }
        val duplicate = normalized.groupingBy { it.keyword.lowercase(Locale.ROOT) }
            .eachCount()
            .entries
            .firstOrNull { it.value > 1 }
            ?.key
        if (duplicate != null) {
            Toast.makeText(context, "存在重复的原关键词", Toast.LENGTH_SHORT).show()
            return
        }
        onSave(normalized)
    }

    PageScaffold(
        title = "替换规则",
        largeTitle = "替换规则",
        scrollBehavior = scrollBehavior,
        bottomBar = { BottomActionBar("保存", ::save, "返回", onBack) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            state = listState,
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 8.dp,
                bottom = padding.calculateBottomPadding() + 84.dp
            )
        ) {
            item { SmallTitle(text = "关键词替换") }
            if (rules.isEmpty()) {
                item {
                    SettingsCard {
                        InfoRow("替换规则", "暂无规则")
                    }
                }
            } else {
                rules.forEachIndexed { index, rule ->
                    item(key = "keyword-replacement-$index") {
                        SettingsCard {
                            InputRow(
                                "原关键词",
                                "匹配时不区分大小写",
                                rule.keyword
                            ) { value ->
                                updateRule(index) { it.copy(keyword = value) }
                            }
                            InsetDivider()
                            InputRow(
                                "替换内容",
                                "留空表示删除原关键词",
                                rule.replacement
                            ) { value ->
                                updateRule(index) { it.copy(replacement = value) }
                            }
                            InsetDivider()
                            ActionRow("删除本条", "移除第 ${index + 1} 条替换规则") {
                                rules = rules.toMutableList().also { it.removeAt(index) }
                            }
                        }
                    }
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "操作") }
            item {
                SettingsCard {
                    ActionRow("新增规则", "添加一条关键词替换规则") {
                        rules = rules + KeywordReplacementRule("", "")
                    }
                }
            }
        }
    }
}

@Composable
internal fun MomentsAutoRefreshMiuixPage(
    context: Context,
    provider: FeatureSettingsProvider,
    onBack: () -> Unit
) {
    val sp = remember { HchatStorage.preferences(context, MomentsAutoRefreshSettings.PREFS_NAME) }
    var enabled by remember { mutableStateOf(sp.getBoolean(MomentsAutoRefreshSettings.KEY_ENABLE, false)) }
    var interval by remember {
        mutableStateOf(sp.getInt(MomentsAutoRefreshSettings.KEY_INTERVAL_SECONDS, MomentsAutoRefreshSettings.DEFAULT_INTERVAL_SECONDS).toString())
    }
    var timeWindow by remember {
        mutableStateOf(sp.getBoolean(MomentsAutoRefreshSettings.KEY_TIME_WINDOW_ENABLE, MomentsAutoRefreshSettings.DEFAULT_TIME_WINDOW_ENABLE))
    }
    var startTime by remember {
        mutableStateOf(sp.getString(MomentsAutoRefreshSettings.KEY_START_TIME, MomentsAutoRefreshSettings.DEFAULT_START_TIME).orEmpty())
    }
    var endTime by remember {
        mutableStateOf(sp.getString(MomentsAutoRefreshSettings.KEY_END_TIME, MomentsAutoRefreshSettings.DEFAULT_END_TIME).orEmpty())
    }
    var lastTime by remember { mutableStateOf(sp.getLong(MomentsAutoRefreshSettings.KEY_LAST_TIME, 0L)) }
    var lastResult by remember { mutableStateOf(sp.getString(MomentsAutoRefreshSettings.KEY_LAST_RESULT, "").orEmpty()) }
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
            item { SmallTitle(text = "后台刷新") }
            item {
                SettingsCard {
                    SwitchRow(enabled, "朋友圈自动刷新", "按设定间隔获取新的朋友圈内容") {
                        enabled = it
                        sp.edit().putBoolean(MomentsAutoRefreshSettings.KEY_ENABLE, it).apply()
                    }
                    if (enabled) {
                        InsetDivider()
                        NumberInputRow("刷新间隔", "单位秒，最少 0 秒", interval) {
                            interval = it
                            it.toIntOrNull()?.let { value ->
                                sp.edit().putInt(MomentsAutoRefreshSettings.KEY_INTERVAL_SECONDS, value.coerceAtLeast(0)).apply()
                            }
                        }
                        InsetDivider()
                        SwitchRow(timeWindow, "限制刷新时段", "支持跨零点时段") {
                            timeWindow = it
                            sp.edit().putBoolean(MomentsAutoRefreshSettings.KEY_TIME_WINDOW_ENABLE, it).apply()
                        }
                        if (timeWindow) {
                            InsetDivider()
                            TimeOfDayPickerRow("开始时间", startTime) {
                                startTime = it
                                sp.edit().putString(MomentsAutoRefreshSettings.KEY_START_TIME, it).apply()
                            }
                            InsetDivider()
                            TimeOfDayPickerRow("结束时间", endTime) {
                                endTime = it
                                sp.edit().putString(MomentsAutoRefreshSettings.KEY_END_TIME, it).apply()
                            }
                        }
                    }
                }
            }
            if (enabled) {
                item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "状态") }
                item {
                    SettingsCard {
                        ActionRow(
                            "刷新状态",
                            if (lastTime <= 0L) "尚未执行" else SimpleDateFormat("MM-dd HH:mm:ss", Locale.CHINA).format(Date(lastTime)) + " · " + lastResult
                        ) {
                            lastTime = sp.getLong(MomentsAutoRefreshSettings.KEY_LAST_TIME, 0L)
                            lastResult = sp.getString(MomentsAutoRefreshSettings.KEY_LAST_RESULT, "").orEmpty()
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun MomentsContactFilterMiuixPage(
    context: Context,
    provider: FeatureSettingsProvider,
    onBack: () -> Unit
) {
    val sp = remember { HchatStorage.preferences(context, MomentsContactFilterSettings.PREFS_NAME) }
    var route by remember { mutableStateOf<MomentsContactFilterRoute>(MomentsContactFilterRoute.Main) }
    var targets by remember {
        mutableStateOf(
            parseIds(
                sp.getString(
                    MomentsContactFilterSettings.KEY_TARGETS,
                    MomentsContactFilterSettings.DEFAULT_TARGETS
                ).orEmpty()
            )
        )
    }

    SettingsRouteTransition(
        targetState = route,
        label = "MomentsContactFilterRoute",
        depthOf = { if (it is MomentsContactFilterRoute.Main) 0 else 1 }
    ) { current ->
        when (current) {
            MomentsContactFilterRoute.Main -> MomentsContactFilterMainPage(
                provider = provider,
                sp = sp,
                targets = targets,
                onBack = onBack,
                onPickTargets = {
                    route = MomentsContactFilterRoute.ContactPicker(
                        ContactPickerRequest(
                            title = "选择朋友圈过滤对象",
                            mode = ContactPickerMode.FRIENDS,
                            multiSelect = true,
                            existingValue = formatIds(targets),
                            onValue = {},
                            enableLabels = true,
                            enableScopeSelection = true
                        )
                    )
                }
            )
            is MomentsContactFilterRoute.ContactPicker -> ContactPickerPage(
                context = context,
                request = current.request,
                onBack = { route = MomentsContactFilterRoute.Main },
                onConfirm = { picked ->
                    targets = picked.map { it.id }.filter { it.isNotBlank() }.toSet()
                    sp.edit()
                        .putString(MomentsContactFilterSettings.KEY_TARGETS, formatIds(targets))
                        .apply()
                    route = MomentsContactFilterRoute.Main
                }
            )
        }
    }
}

@Composable
internal fun MomentsContactFilterMainPage(
    provider: FeatureSettingsProvider,
    sp: SharedPreferences,
    targets: Set<String>,
    onBack: () -> Unit,
    onPickTargets: () -> Unit
) {
    var enabled by remember {
        mutableStateOf(
            sp.getBoolean(
                MomentsContactFilterSettings.KEY_ENABLE,
                MomentsContactFilterSettings.DEFAULT_ENABLE
            )
        )
    }
    var mode by remember {
        mutableStateOf(
            sp.getInt(
                MomentsContactFilterSettings.KEY_MODE,
                MomentsContactFilterSettings.DEFAULT_MODE
            )
        )
    }
    val targetSummary = when {
        targets.isEmpty() -> "未选择好友，暂不筛选"
        mode == MomentsContactFilterSettings.MODE_INCLUDE_ONLY -> "只显示所选 ${targets.size} 位好友"
        else -> "隐藏所选 ${targets.size} 位好友"
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
            item { SmallTitle(text = "朋友圈") }
            item {
                SettingsCard {
                    SwitchRow(
                        checked = enabled,
                        title = "朋友圈过滤",
                        summary = "按好友范围过滤朋友圈内容"
                    ) {
                        enabled = it
                        sp.edit().putBoolean(MomentsContactFilterSettings.KEY_ENABLE, it).apply()
                    }
                    if (enabled) {
                        InsetDivider()
                        PopupOptionRow(
                            title = "过滤模式",
                            summary = if (mode == MomentsContactFilterSettings.MODE_INCLUDE_ONLY) {
                                "只看所选好友的朋友圈"
                            } else {
                                "过滤所选好友的朋友圈"
                            },
                            options = optionItems(
                                "过滤所选好友" to MomentsContactFilterSettings.MODE_EXCLUDE,
                                "只看所选好友" to MomentsContactFilterSettings.MODE_INCLUDE_ONLY
                            ),
                            currentValue = mode,
                            onValueChanged = {
                                mode = it
                                sp.edit().putInt(MomentsContactFilterSettings.KEY_MODE, it).apply()
                            }
                        )
                        InsetDivider()
                        ActionRow(
                            title = "过滤对象",
                            summary = targetSummary
                        ) {
                            onPickTargets()
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun MomentsKeywordBlockMiuixPage(
    context: Context,
    provider: FeatureSettingsProvider,
    onBack: () -> Unit
) {
    val sp = remember { HchatStorage.preferences(context, MomentsKeywordBlockSettings.PREFS_NAME) }
    var enabled by remember {
        mutableStateOf(
            sp.getBoolean(
                MomentsKeywordBlockSettings.KEY_ENABLE,
                MomentsKeywordBlockSettings.DEFAULT_ENABLE
            )
        )
    }
    var keywords by remember {
        mutableStateOf(
            sp.getString(
                MomentsKeywordBlockSettings.KEY_KEYWORDS,
                MomentsKeywordBlockSettings.DEFAULT_KEYWORDS
            ).orEmpty()
        )
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
            item { SmallTitle(text = "朋友圈") }
            item {
                SettingsCard {
                    SwitchRow(
                        checked = enabled,
                        title = "朋友圈关键词屏蔽",
                        summary = "隐藏正文命中任意关键词的朋友圈"
                    ) {
                        enabled = it
                        sp.edit().putBoolean(MomentsKeywordBlockSettings.KEY_ENABLE, it).apply()
                    }
                    if (enabled) {
                        InsetDivider()
                        InputRow(
                            title = "屏蔽关键词",
                            summary = "多个关键词用逗号或换行分隔",
                            value = keywords,
                            minLines = 3
                        ) {
                            keywords = it
                            sp.edit().putString(MomentsKeywordBlockSettings.KEY_KEYWORDS, it).apply()
                        }
                    }
                }
            }
        }
    }
}
