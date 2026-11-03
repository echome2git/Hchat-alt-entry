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
internal fun AutoReplyRuleEditorContent(
    rule: AutoReplyRule,
    listState: LazyListState,
    onRuleChange: (AutoReplyRule) -> Unit,
    onBack: () -> Unit,
    onSave: () -> Unit,
    onEditSteps: () -> Unit,
    onOptionPicker: (OptionPickerRequest) -> Unit,
    onContactPicker: (ContactPickerRequest) -> Unit,
    onGroupMemberPicker: (GroupMemberPickerRequest) -> Unit
) {
    val current = rule
    val scrollBehavior = MiuixScrollBehavior()
    PageScaffold(
        title = "编辑规则",
        largeTitle = "编辑规则",
        scrollBehavior = scrollBehavior,
        bottomBar = {
            BottomActionBar(
                primaryText = "保存",
                onPrimaryClick = onSave,
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
                    SwitchRow(current.enabled, "启用规则", "关闭后保留配置但不触发") { onRuleChange(current.copy(enabled = it)) }
                    InsetDivider()
                    InputRow("规则名称", "用于列表里识别规则", current.name) { onRuleChange(current.copy(name = it)) }
                    InsetDivider()
                    PopupOptionRow(
                        title = "匹配方式",
                        summary = autoReplyMatchLabel(current.matchType),
                        options = autoReplyMatchOptions(),
                        currentValue = current.matchType,
                        onValueChanged = {
                            onRuleChange(
                                current.copy(
                                    matchType = it,
                                    keyword = if (it == AutoReplySettings.MATCH_ANY) "" else current.keyword
                                )
                            )
                        }
                    )
                    if (current.matchType != AutoReplySettings.MATCH_ANY) {
                        InsetDivider()
                        InputRow("关键词", "多个关键词用 |、逗号或换行分隔", current.keyword, minLines = 2) {
                            onRuleChange(current.copy(keyword = it))
                        }
                    } else {
                        InsetDivider()
                        InputRow("排除关键词", "包含任一关键词时不触发，多个用 |、逗号或换行分隔", current.excludedKeywords, minLines = 2) {
                            onRuleChange(current.copy(excludedKeywords = it))
                        }
                    }
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "范围") }
            item {
                SettingsCard {
                    PopupOptionRow(
                        title = "生效范围",
                        summary = autoReplyTargetLabel(current.targetMode),
                        options = autoReplyTargetOptions(),
                        currentValue = current.targetMode,
                        onValueChanged = { onRuleChange(current.copy(targetMode = it)) }
                    )
                    if (current.targetMode == AutoReplySettings.TARGET_SPECIFIC) {
                        InsetDivider()
                        ActionRow("选择指定好友", autoReplySelectedIdSummary(formatIds(current.targetIds.filter { !isAutoReplyGroupId(it) }))) {
                            onContactPicker(ContactPickerRequest(
                                title = "选择指定好友",
                                mode = ContactPickerMode.FRIENDS,
                                multiSelect = true,
                                existingValue = formatIds(current.targetIds.filter { !isAutoReplyGroupId(it) }),
                                onValue = { onRuleChange(current.copy(targetIds = replaceAutoReplyTypedIds(current.targetIds, parseIds(it), group = false))) },
                                enableLabels = true
                            ))
                        }
                        InsetDivider()
                        ActionRow("选择指定群聊", autoReplySelectedIdSummary(formatIds(current.targetIds.filter { isAutoReplyGroupId(it) }))) {
                            onContactPicker(ContactPickerRequest(
                                title = "选择指定群聊",
                                mode = ContactPickerMode.GROUPS,
                                multiSelect = true,
                                existingValue = formatIds(current.targetIds.filter { isAutoReplyGroupId(it) }),
                                onValue = { onRuleChange(current.copy(targetIds = replaceAutoReplyTypedIds(current.targetIds, parseIds(it), group = true))) }
                            ))
                        }
                        InsetDivider()
                        ActionRow("选择指定群成员", autoReplySelectedIdSummary(formatIds(current.includedGroupMembers))) {
                            onGroupMemberPicker(GroupMemberPickerRequest(
                                title = "选择指定群成员",
                                existingValue = formatIds(current.includedGroupMembers),
                                onValue = { onRuleChange(current.copy(includedGroupMembers = parseIds(it))) }
                            ))
                        }
                    } else {
                        InsetDivider()
                        ActionRow("选择排除好友", autoReplySelectedIdSummary(formatIds(current.excludedIds.filter { !isAutoReplyGroupId(it) }))) {
                            onContactPicker(ContactPickerRequest(
                                title = "选择排除好友",
                                mode = ContactPickerMode.FRIENDS,
                                multiSelect = true,
                                existingValue = formatIds(current.excludedIds.filter { !isAutoReplyGroupId(it) }),
                                onValue = { onRuleChange(current.copy(excludedIds = replaceAutoReplyTypedIds(current.excludedIds, parseIds(it), group = false))) },
                                enableLabels = true
                            ))
                        }
                        InsetDivider()
                        ActionRow("选择排除群聊", autoReplySelectedIdSummary(formatIds(current.excludedIds.filter { isAutoReplyGroupId(it) }))) {
                            onContactPicker(ContactPickerRequest(
                                title = "选择排除群聊",
                                mode = ContactPickerMode.GROUPS,
                                multiSelect = true,
                                existingValue = formatIds(current.excludedIds.filter { isAutoReplyGroupId(it) }),
                                onValue = { onRuleChange(current.copy(excludedIds = replaceAutoReplyTypedIds(current.excludedIds, parseIds(it), group = true))) }
                            ))
                        }
                        InsetDivider()
                        ActionRow("选择排除群成员", autoReplySelectedIdSummary(formatIds(current.excludedGroupMembers))) {
                            onGroupMemberPicker(GroupMemberPickerRequest(
                                title = "选择排除群成员",
                                existingValue = formatIds(current.excludedGroupMembers),
                                onValue = { onRuleChange(current.copy(excludedGroupMembers = parseIds(it))) }
                            ))
                        }
                    }
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "触发") }
            item {
                SettingsCard {
                    PopupOptionRow(
                        title = "@ 触发",
                        summary = autoReplyAtLabel(current.atTrigger),
                        options = autoReplyAtOptions(),
                        currentValue = current.atTrigger,
                        onValueChanged = { onRuleChange(current.copy(atTrigger = it)) }
                    )
                    InsetDivider()
                    PopupOptionRow(
                        title = "拍一拍",
                        summary = autoReplyPatLabel(current.patTrigger),
                        options = autoReplyPatOptions(),
                        currentValue = current.patTrigger,
                        onValueChanged = { onRuleChange(current.copy(patTrigger = it)) }
                    )
                    InsetDivider()
                    TimeOfDayPickerRow("开始时间", current.startTime, allowEmpty = true) {
                        onRuleChange(current.copy(startTime = it))
                    }
                    InsetDivider()
                    TimeOfDayPickerRow("结束时间", current.endTime, allowEmpty = true) {
                        onRuleChange(current.copy(endTime = it))
                    }
                    InsetDivider()
                    NumberInputRow("最大回复次数", "0 表示不限制，按规则/会话/发送者统计", current.maxReplyCount.toString()) {
                        onRuleChange(current.copy(maxReplyCount = it.toIntOrNull()?.coerceAtLeast(0) ?: 0))
                    }
                    InsetDivider()
                    NumberInputRow("回复冷却时间", "单位秒，0 表示不限制；同一规则在同一会话内冷却", current.cooldownSeconds.toString()) {
                        onRuleChange(current.copy(cooldownSeconds = it.toLongOrNull()?.coerceAtLeast(0L) ?: 0L))
                    }
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "回复") }
            item {
                SettingsCard {
                    SwitchRow(current.replyAsQuote, "引用回复", "文本和 AI 可引用原消息回复") { onRuleChange(current.copy(replyAsQuote = it)) }
                    InsetDivider()
                    ActionRow("回复步骤", describeAutoReplySteps(current.steps), onEditSteps)
                }
            }
        }
    }
}

@Composable
internal fun AutoReplyFriendPage(
    context: Context,
    title: String,
    sp: SharedPreferences,
    enabledKey: String,
    delayKey: String,
    tagEnabledKey: String,
    tagNameKey: String,
    steps: List<AutoReplyStep>,
    listState: LazyListState,
    onBack: () -> Unit,
    automationKeys: AutoReplyFriendAutomationKeys?,
    onSelectLabels: (() -> Unit)?,
    onEditSteps: () -> Unit
) {
    var delayMs by remember { mutableStateOf(sp.getLong(delayKey, 2000L).coerceAtLeast(0L).toString()) }
    var tagName by remember { mutableStateOf(sp.getString(tagNameKey, "") ?: "") }
    var labelDateFormat by remember {
        mutableStateOf(sp.getString(automationKeys?.labelDateFormat ?: "", "yyyy-MM-dd") ?: "yyyy-MM-dd")
    }
    val selectedLabelNames = sp.getString(automationKeys?.labelSelectedNames ?: "", "") ?: ""
    var remarkDateFormat by remember {
        mutableStateOf(sp.getString(automationKeys?.remarkDateFormat ?: "", "yyMMdd") ?: "yyMMdd")
    }
    var remarkCustomText by remember {
        mutableStateOf(sp.getString(automationKeys?.remarkCustomText ?: "", "") ?: "")
    }
    val hasAutomation = automationKeys != null && onSelectLabels != null
    var labelDateEnabled by remember { mutableStateOf(automationKeys?.let { sp.getBoolean(it.labelDateEnable, false) } ?: false) }
    var labelExistingEnabled by remember { mutableStateOf(automationKeys?.let { sp.getBoolean(it.labelExistingEnable, false) } ?: false) }
    var remarkDateEnabled by remember { mutableStateOf(automationKeys?.let { sp.getBoolean(it.remarkDateEnable, false) } ?: false) }
    var remarkCustomEnabled by remember { mutableStateOf(automationKeys?.let { sp.getBoolean(it.remarkCustomEnable, false) } ?: false) }
    var tagEnabled by remember { mutableStateOf(sp.getBoolean(tagEnabledKey, false)) }
    val scrollBehavior = MiuixScrollBehavior()
    PageScaffold(
        title = title,
        largeTitle = title,
        scrollBehavior = scrollBehavior,
        bottomBar = {
            BottomActionBar(
                primaryText = "保存",
                onPrimaryClick = {
                    sp.edit()
                        .putLong(delayKey, delayMs.toLongOrNull()?.coerceIn(0L, 600000L) ?: 0L)
                        .putString(tagNameKey, tagName.trim())
                        .also { editor ->
                            automationKeys?.let { keys ->
                                editor.putString(keys.labelDateFormat, labelDateFormat.trim().ifBlank { "yyyy-MM-dd" })
                                editor.putString(keys.remarkDateFormat, remarkDateFormat.trim().ifBlank { "yyMMdd" })
                                editor.putString(keys.remarkCustomText, remarkCustomText.trim())
                            }
                        }
                        .apply()
                    Toast.makeText(context, "设置已保存", Toast.LENGTH_SHORT).show()
                },
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
            item {
                SettingsCard {
                    SwitchRow(sp, enabledKey, "启用", "开启后自动处理这个场景", false)
                    InsetDivider()
                    NumberInputRow("延迟发送", "单位 ms，通过好友后等待再发欢迎内容", delayMs) { delayMs = it }
                    if (hasAutomation) {
                        val keys = automationKeys!!
                        InsetDivider()
                        SwitchRow(sp, keys.labelNewFriendEnable, "自动标签新加好友", "给好友添加“新加好友”标签", false)
                        InsetDivider()
                        SwitchRow(labelDateEnabled, "自动标签日期", "按日期格式生成一个标签") {
                            labelDateEnabled = it
                            sp.edit().putBoolean(keys.labelDateEnable, it).apply()
                        }
                        if (labelDateEnabled) {
                            InsetDivider()
                            InputRow("标签日期格式", "例如 yyyy-MM-dd", labelDateFormat) { labelDateFormat = it }
                        }
                        InsetDivider()
                        SwitchRow(labelExistingEnabled, "自动标签已有标签", "把好友加入已选择的微信标签") {
                            labelExistingEnabled = it
                            sp.edit().putBoolean(keys.labelExistingEnable, it).apply()
                        }
                        if (labelExistingEnabled) {
                            InsetDivider()
                            ActionRow("选择标签", autoReplyLabelSummary(selectedLabelNames), onSelectLabels!!)
                        }
                        InsetDivider()
                        SwitchRow(sp, keys.remarkNewFriendEnable, "自动备注新加好友", "备注里加入“新加好友”", false)
                        InsetDivider()
                        SwitchRow(sp, keys.remarkNicknameSuffixEnable, "加昵称后面", "备注格式为微信昵称 + 备注内容", false)
                        InsetDivider()
                        SwitchRow(remarkDateEnabled, "自动备注日期", "备注里加入日期") {
                            remarkDateEnabled = it
                            sp.edit().putBoolean(keys.remarkDateEnable, it).apply()
                        }
                        if (remarkDateEnabled) {
                            InsetDivider()
                            InputRow("备注日期格式", "例如 yyMMdd", remarkDateFormat) { remarkDateFormat = it }
                        }
                        InsetDivider()
                        SwitchRow(remarkCustomEnabled, "自动备注自定义文本", "备注里加入自定义内容") {
                            remarkCustomEnabled = it
                            sp.edit().putBoolean(keys.remarkCustomEnable, it).apply()
                        }
                        if (remarkCustomEnabled) {
                            InsetDivider()
                            InputRow("备注自定义文本", "例如 渠道A", remarkCustomText) { remarkCustomText = it }
                        }
                    } else {
                        InsetDivider()
                        SwitchRow(tagEnabled, "自动打标签", "处理后把好友加入指定标签") {
                            tagEnabled = it
                            sp.edit().putBoolean(tagEnabledKey, it).apply()
                        }
                        if (tagEnabled) {
                            InsetDivider()
                            InputRow("标签名称", "标签需要微信里已存在", tagName) { tagName = it }
                        }
                    }
                    InsetDivider()
                    ActionRow("回复步骤", describeAutoReplySteps(steps), onEditSteps)
                }
            }
        }
    }
}

@Composable
internal fun AutoReplyLabelPickerPage(
    context: Context,
    sp: SharedPreferences,
    selectedKey: String,
    listState: LazyListState,
    onBack: () -> Unit
) {
    var query by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf("") }
    var labels by remember { mutableStateOf<List<ContactLabelBean>>(emptyList()) }
    var selected by remember {
        mutableStateOf(splitAutoReplyNames(sp.getString(selectedKey, "") ?: "").toSet())
    }
    LaunchedEffect(Unit) {
        loading = true
        error = ""
        val api = WeChatApis.contact().contacts()
        if (api == null || !api.isAvailable) {
            labels = emptyList()
            error = "联系人标签不可用"
            loading = false
        } else {
            Thread({
                val result = runCatching { api.getContactLabelList() }
                Handler(Looper.getMainLooper()).post {
                    labels = result.getOrDefault(emptyList())
                        .filter { it.labelName.isNotBlank() || it.labelId.isNotBlank() }
                        .sortedBy { it.labelName.ifBlank { it.labelId }.lowercase(Locale.US) }
                    error = result.exceptionOrNull()?.message.orEmpty()
                    loading = false
                }
            }, "HchatAutoReplyLabels").start()
        }
    }
    val visible = labels.filter { label ->
        val name = label.labelName.ifBlank { label.labelId }
        val key = query.trim().lowercase(Locale.US)
        key.isBlank() || name.lowercase(Locale.US).contains(key)
    }
    val scrollBehavior = MiuixScrollBehavior()
    PageScaffold(
        title = "选择标签",
        largeTitle = "选择标签",
        scrollBehavior = scrollBehavior,
        bottomBar = {
            BottomActionBar(
                primaryText = "保存",
                onPrimaryClick = {
                    sp.edit()
                        .putString(selectedKey, selected.joinToString(";;;"))
                        .apply()
                    Toast.makeText(context, "标签已保存", Toast.LENGTH_SHORT).show()
                    onBack()
                },
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
            item { SmallTitle(text = "搜索") }
            item {
                SettingsCard {
                    InputRow("关键词", "按标签名筛选", query) { query = it }
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "标签") }
            when {
                loading -> item { SettingsCard { EmptyText("正在载入标签...") } }
                error.isNotEmpty() -> item { SettingsCard { EmptyText(error) } }
                visible.isEmpty() -> item { SettingsCard { EmptyText("没有匹配标签") } }
                else -> visible.forEach { label ->
                    val name = label.labelName.ifBlank { label.labelId }
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 3.dp),
                            cornerRadius = 14.dp
                        ) {
                            AutoReplyLabelRow(
                                name = name,
                                summary = "${label.userNameList.size} 人",
                                selected = selected.contains(name),
                                onClick = {
                                    selected = if (selected.contains(name)) selected - name else selected + name
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun AutoReplyLabelRow(
    name: String,
    summary: String,
    selected: Boolean,
    onClick: () -> Unit
) {
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
        Box(
            modifier = Modifier.size(42.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MiuixTheme.colorScheme.secondaryVariant),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name.take(1).ifEmpty { "签" },
                color = MiuixTheme.colorScheme.onSecondaryVariant,
                fontWeight = FontWeight.Bold
            )
        }
        Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
            Text(text = name, color = MiuixTheme.colorScheme.onSurface, fontWeight = FontWeight.Medium)
            Text(text = summary, color = MiuixTheme.colorScheme.onSurfaceVariantSummary, fontSize = 12.sp)
        }
        SelectionMark(selected = selected, multiSelect = true)
    }
}

internal fun autoReplyLabelSummary(value: String): String {
    val names = splitAutoReplyNames(value)
    return if (names.isEmpty()) "未选择" else names.take(3).joinToString("、") + if (names.size > 3) " 等 ${names.size} 个" else ""
}

@Composable
internal fun XiaozhiCaptchaSvgView(svg: String) {
    AndroidView(
        modifier = Modifier.fillMaxWidth().height(72.dp).padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MiuixTheme.colorScheme.secondaryVariant),
        factory = { context ->
            WebView(context).apply {
                setBackgroundColor(AndroidColor.TRANSPARENT)
                settings.javaScriptEnabled = false
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
            }
        },
        update = { view ->
            view.loadDataWithBaseURL(null, svg, "image/svg+xml", "UTF-8", null)
        }
    )
}

internal fun xiaozhiAgentSummary(agentId: String, agents: List<XiaozhiAgentOption>): String {
    if (agentId.isBlank()) return "未选择，点击拉取"
    val agent = agents.firstOrNull { it.id == agentId }
    return if (agent == null) "已保存 ID: $agentId" else "${agent.name} / ${agent.assistantName.ifBlank { agent.id }}"
}

internal fun xiaozhiModelSummary(model: String, models: List<XiaozhiModelOption>): String {
    if (model.isBlank()) return "未选择，点击拉取"
    val item = models.firstOrNull { it.id == model }
    return if (item == null) "已保存: $model" else "${item.name} / ${item.id}"
}

internal fun xiaozhiVoiceSummary(voice: String, voices: List<XiaozhiVoiceOption>): String {
    if (voice.isBlank()) return "未选择，留空使用控制台当前角色"
    val item = voices.firstOrNull { it.id == voice }
    return if (item == null) "已保存: $voice" else "${item.name} / ${item.id}"
}

internal fun splitAutoReplyNames(value: String): List<String> =
    value.split(";;;", "|", "\n")
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .distinct()
