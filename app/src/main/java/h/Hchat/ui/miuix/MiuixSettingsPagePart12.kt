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
internal fun AutoReplyAiPage(context: Context, sp: SharedPreferences, onBack: () -> Unit) {
    val settings = remember { AutoReplySettings(context) }
    val scope = rememberCoroutineScope()
    var xiaozhiServe by remember { mutableStateOf(settings.xiaozhiConfig().serveUrl) }
    var xiaozhiOta by remember { mutableStateOf(settings.xiaozhiConfig().otaUrl) }
    var xiaozhiConsole by remember { mutableStateOf(settings.xiaozhiConfig().consoleUrl) }
    var xiaozhiConsolePhone by remember { mutableStateOf(settings.xiaozhiConfig().consolePhone) }
    var xiaozhiConsoleToken by remember { mutableStateOf(settings.xiaozhiConfig().consoleToken) }
    var xiaozhiConsoleAgentId by remember { mutableStateOf(settings.xiaozhiConfig().consoleAgentId) }
    var xiaozhiConsoleModel by remember { mutableStateOf(settings.xiaozhiConfig().consoleModel) }
    var xiaozhiVoiceRole by remember { mutableStateOf(settings.xiaozhiConfig().voiceRole) }
    var xiaozhiMusicMcp by remember { mutableStateOf(settings.xiaozhiConfig().musicMcpEnabled) }
    var xiaozhiMcpBridge by remember { mutableStateOf(settings.xiaozhiConfig().mcpBridgeEnabled) }
    var xiaozhiMcpEndpoint by remember { mutableStateOf(settings.xiaozhiConfig().mcpEndpointUrl) }
    var xiaozhiMcpKugouEnabled by remember { mutableStateOf(settings.xiaozhiConfig().mcpKugouEnabled) }
    var xiaozhiMcpKugouPluginId by remember { mutableStateOf(settings.xiaozhiConfig().mcpKugouPluginId) }
    var xiaozhiMcpKugouFunction by remember { mutableStateOf(settings.xiaozhiConfig().mcpKugouFunctionName) }
    var xiaozhiMcpReadySeconds by remember { mutableStateOf(settings.xiaozhiConfig().mcpReadySeconds.toString()) }
    var xiaozhiMcpIdleSeconds by remember { mutableStateOf(settings.xiaozhiConfig().mcpIdleSeconds.toString()) }
    var xiaozhiMcpStatus by remember { mutableStateOf(XiaozhiMcpStatus(false, "未查询", "点击刷新查询小智控制台")) }
    var configs by remember { mutableStateOf(settings.zhiliaConfigs()) }
    var activeName by remember { mutableStateOf(settings.activeZhiliaName()) }
    val activeConfig = configs.firstOrNull { it.name == activeName } ?: configs.firstOrNull() ?: settings.activeZhiliaConfig()
    var cfgName by remember(activeName, configs) { mutableStateOf(activeConfig.name) }
    var apiKey by remember(activeName, configs) { mutableStateOf(activeConfig.apiKey) }
    var apiBase by remember(activeName, configs) { mutableStateOf(activeConfig.apiBaseUrl) }
    var apiPath by remember(activeName, configs) { mutableStateOf(activeConfig.apiPath) }
    var model by remember(activeName, configs) { mutableStateOf(activeConfig.model) }
    var prompt by remember(activeName, configs) { mutableStateOf(activeConfig.systemPrompt) }
    var contextLimit by remember(activeName, configs) { mutableStateOf(activeConfig.contextLimit.toString()) }
    var modelQuery by remember { mutableStateOf("") }
    var fetchedModels by remember { mutableStateOf<List<String>>(emptyList()) }
    var favoriteModels by remember(apiBase) { mutableStateOf(settings.favoriteModels(apiBase)) }
    var loadingText by remember { mutableStateOf("") }
    var testResult by remember { mutableStateOf("") }
    var xiaozhiBindResult by remember { mutableStateOf("") }
    var xiaozhiCaptchaSvg by remember { mutableStateOf("") }
    var xiaozhiCaptchaCode by remember { mutableStateOf("") }
    var xiaozhiSmsCode by remember { mutableStateOf("") }
    var xiaozhiConsoleResult by remember { mutableStateOf("") }
    var xiaozhiAgents by remember { mutableStateOf<List<XiaozhiAgentOption>>(emptyList()) }
    var xiaozhiModels by remember { mutableStateOf<List<XiaozhiModelOption>>(emptyList()) }
    var xiaozhiVoices by remember { mutableStateOf<List<XiaozhiVoiceOption>>(emptyList()) }
    var route by remember { mutableStateOf<AutoReplyAiRoute>(AutoReplyAiRoute.Main) }
    var zhiliaPendingName by remember { mutableStateOf(activeName) }
    var pendingModel by remember { mutableStateOf(model) }
    val mainListState = rememberLazyListState()
    val xiaozhiListState = rememberLazyListState()
    val zhiliaListState = rememberLazyListState()
    val zhiliaConfigListState = rememberLazyListState()
    val zhiliaModelListState = rememberLazyListState()

    fun currentZhiliaConfig(): AutoReplyZhiliaConfig {
        return AutoReplyZhiliaConfig(
            name = cfgName,
            apiKey = apiKey.trim(),
            apiBaseUrl = apiBase.trim(),
            apiPath = apiPath.trim(),
            model = model.trim(),
            systemPrompt = prompt,
            contextLimit = contextLimit.toIntOrNull() ?: AutoReplySettings.DEFAULT_AI_CONTEXT_LIMIT
        ).normalized()
    }

    fun currentXiaozhiConfig(): AutoReplyXiaozhiConfig {
        return AutoReplyXiaozhiConfig(
            serveUrl = xiaozhiServe,
            otaUrl = xiaozhiOta,
            consoleUrl = xiaozhiConsole,
            consolePhone = xiaozhiConsolePhone,
            consoleToken = xiaozhiConsoleToken,
            consoleAgentId = xiaozhiConsoleAgentId,
            consoleModel = xiaozhiConsoleModel,
            voiceRole = xiaozhiVoiceRole,
            musicMcpEnabled = xiaozhiMusicMcp,
            mcpBridgeEnabled = xiaozhiMcpBridge,
            mcpEndpointUrl = xiaozhiMcpEndpoint,
            mcpKugouEnabled = xiaozhiMcpKugouEnabled,
            mcpKugouPluginId = xiaozhiMcpKugouPluginId,
            mcpKugouFunctionName = xiaozhiMcpKugouFunction,
            mcpReadySeconds = xiaozhiMcpReadySeconds.toIntOrNull()?.coerceIn(1, 30)
                ?: AutoReplySettings.DEFAULT_XIAOZHI_MCP_READY_SECONDS,
            mcpIdleSeconds = xiaozhiMcpIdleSeconds.toIntOrNull()?.coerceIn(10, 600)
                ?: AutoReplySettings.DEFAULT_XIAOZHI_MCP_IDLE_SECONDS
        )
    }

    fun saveXiaozhi(showToast: Boolean) {
        settings.saveXiaozhiConfig(currentXiaozhiConfig())
        if (showToast) Toast.makeText(context, "小智AI配置已保存", Toast.LENGTH_SHORT).show()
    }

    fun xiaozhiNeedToken(): Boolean {
        if (xiaozhiConsoleToken.trim().isBlank()) {
            xiaozhiConsoleResult = "请先登录小智控制台"
            Toast.makeText(context, "请先登录小智控制台", Toast.LENGTH_SHORT).show()
            return true
        }
        return false
    }

    fun refreshXiaozhiCaptcha(updateStatus: Boolean = true) {
        if (updateStatus) xiaozhiConsoleResult = "正在刷新图形验证码..."
        scope.launch {
            val result = withContext(Dispatchers.IO) {
                runCatching { XiaozhiConsoleApi.fetchCaptchaSvg() }
            }
            result.onSuccess {
                xiaozhiCaptchaSvg = it
                xiaozhiCaptchaCode = ""
                if (updateStatus) xiaozhiConsoleResult = "请输入图形验证码"
            }.onFailure {
                xiaozhiConsoleResult = "刷新图形验证码失败: ${it.message}"
            }
        }
    }

    fun sendXiaozhiSmsCode() {
        val phone = xiaozhiConsolePhone.trim()
        val captcha = xiaozhiCaptchaCode.trim()
        if (phone.isBlank() || captcha.isBlank()) {
            Toast.makeText(context, "请填写手机号和图形验证码", Toast.LENGTH_SHORT).show()
            return
        }
        settings.saveXiaozhiConfig(currentXiaozhiConfig())
        xiaozhiConsoleResult = "正在发送短信验证码..."
        scope.launch {
            val result = withContext(Dispatchers.IO) {
                runCatching { XiaozhiConsoleApi.sendSmsCode(phone, captcha) }
            }
            result.onSuccess {
                xiaozhiConsoleResult = it
                xiaozhiCaptchaCode = ""
                refreshXiaozhiCaptcha(updateStatus = false)
            }.onFailure {
                xiaozhiConsoleResult = "发送短信验证码失败: ${it.message}"
                xiaozhiCaptchaCode = ""
                refreshXiaozhiCaptcha(updateStatus = false)
            }
            Toast.makeText(context, xiaozhiConsoleResult, Toast.LENGTH_SHORT).show()
        }
    }

    fun loginXiaozhiConsole() {
        val phone = xiaozhiConsolePhone.trim()
        val sms = xiaozhiSmsCode.trim()
        if (phone.isBlank() || sms.isBlank()) {
            Toast.makeText(context, "请填写手机号和短信验证码", Toast.LENGTH_SHORT).show()
            return
        }
        xiaozhiConsoleResult = "正在登录小智控制台..."
        scope.launch {
            val result = withContext(Dispatchers.IO) {
                runCatching { XiaozhiConsoleApi.phoneLogin(phone, sms) }
            }
            result.onSuccess { token ->
                xiaozhiConsoleToken = token
                xiaozhiSmsCode = ""
                xiaozhiCaptchaCode = ""
                settings.saveXiaozhiConfig(currentXiaozhiConfig())
                xiaozhiConsoleResult = "登录成功，token 已保存"
                Toast.makeText(context, "小智控制台登录成功", Toast.LENGTH_SHORT).show()
                val agentsResult = withContext(Dispatchers.IO) {
                    runCatching { XiaozhiConsoleApi.fetchAgents(token) }
                }
                agentsResult.onSuccess { agents ->
                    xiaozhiAgents = agents
                    agents.firstOrNull()?.let { agent ->
                        if (xiaozhiConsoleAgentId.isBlank()) xiaozhiConsoleAgentId = agent.id
                        if (xiaozhiConsoleModel.isBlank()) xiaozhiConsoleModel = agent.model
                        if (xiaozhiVoiceRole.isBlank()) xiaozhiVoiceRole = agent.voice
                        settings.saveXiaozhiConfig(currentXiaozhiConfig())
                    }
                    xiaozhiConsoleResult = "登录成功，已拉取 ${agents.size} 个智能体"
                }.onFailure {
                    xiaozhiConsoleResult = "登录成功，拉取智能体失败: ${it.message}"
                }
            }.onFailure {
                xiaozhiConsoleResult = "登录失败: ${it.message}"
            }
        }
    }

    fun fetchXiaozhiAgents(openPicker: Boolean) {
        if (xiaozhiNeedToken()) return
        xiaozhiConsoleResult = "正在拉取智能体..."
        scope.launch {
            val result = withContext(Dispatchers.IO) {
                runCatching { XiaozhiConsoleApi.fetchAgents(xiaozhiConsoleToken) }
            }
            result.onSuccess { agents ->
                xiaozhiAgents = agents
                xiaozhiConsoleResult = "已拉取 ${agents.size} 个智能体"
                if (openPicker && agents.isNotEmpty()) {
                    val currentIndex = agents.indexOfFirst { it.id == xiaozhiConsoleAgentId }.coerceAtLeast(0)
                    route = AutoReplyAiRoute.OptionPicker(
                        OptionPickerRequest(
                            title = "选择小智智能体",
                            options = agents.mapIndexed { index, agent ->
                                OptionItem(
                                    label = agent.name,
                                    value = index,
                                    summary = listOf(agent.assistantName, agent.model, agent.voice).filter { it.isNotBlank() }.joinToString(" / ")
                                )
                            },
                            currentValue = currentIndex,
                            onSelected = { selected ->
                                agents.getOrNull(selected.value)?.let { agent ->
                                    xiaozhiConsoleAgentId = agent.id
                                    xiaozhiConsoleModel = agent.model
                                    xiaozhiVoiceRole = agent.voice
                                    settings.saveXiaozhiConfig(currentXiaozhiConfig())
                                }
                                route = AutoReplyAiRoute.Xiaozhi
                            }
                        )
                    )
                }
            }.onFailure {
                xiaozhiConsoleResult = "拉取智能体失败: ${it.message}"
            }
        }
    }

    fun fetchXiaozhiModels(openPicker: Boolean) {
        if (xiaozhiNeedToken()) return
        xiaozhiConsoleResult = "正在拉取模型列表..."
        scope.launch {
            val result = withContext(Dispatchers.IO) {
                runCatching { XiaozhiConsoleApi.fetchModels(xiaozhiConsoleToken) }
            }
            result.onSuccess { models ->
                xiaozhiModels = models
                xiaozhiConsoleResult = "已拉取 ${models.size} 个模型"
                if (openPicker && models.isNotEmpty()) {
                    val currentIndex = models.indexOfFirst { it.id == xiaozhiConsoleModel }.coerceAtLeast(0)
                    route = AutoReplyAiRoute.OptionPicker(
                        OptionPickerRequest(
                            title = "选择小智模型",
                            options = models.mapIndexed { index, item -> OptionItem(item.name, index, item.id) },
                            currentValue = currentIndex,
                            onSelected = { selected ->
                                models.getOrNull(selected.value)?.let { item ->
                                    xiaozhiConsoleModel = item.id
                                    settings.saveXiaozhiConfig(currentXiaozhiConfig())
                                }
                                route = AutoReplyAiRoute.Xiaozhi
                            }
                        )
                    )
                }
            }.onFailure {
                xiaozhiConsoleResult = "拉取模型失败: ${it.message}"
            }
        }
    }

    fun fetchXiaozhiVoices(openPicker: Boolean) {
        if (xiaozhiNeedToken()) return
        xiaozhiConsoleResult = "正在拉取语音角色..."
        scope.launch {
            val result = withContext(Dispatchers.IO) {
                runCatching { XiaozhiConsoleApi.fetchVoices(xiaozhiConsoleToken) }
            }
            result.onSuccess { voices ->
                xiaozhiVoices = voices
                xiaozhiConsoleResult = "已拉取 ${voices.size} 个语音角色"
                if (openPicker && voices.isNotEmpty()) {
                    val currentIndex = voices.indexOfFirst { it.id == xiaozhiVoiceRole }.coerceAtLeast(0)
                    route = AutoReplyAiRoute.OptionPicker(
                        OptionPickerRequest(
                            title = "选择语音角色",
                            options = voices.mapIndexed { index, item ->
                                OptionItem(item.name, index, listOf(item.id, item.languages.joinToString(",")).filter { it.isNotBlank() }.joinToString(" / "))
                            },
                            currentValue = currentIndex,
                            onSelected = { selected ->
                                voices.getOrNull(selected.value)?.let { item ->
                                    xiaozhiVoiceRole = item.id
                                    settings.saveXiaozhiConfig(currentXiaozhiConfig())
                                }
                                route = AutoReplyAiRoute.Xiaozhi
                            }
                        )
                    )
                }
            }.onFailure {
                xiaozhiConsoleResult = "拉取语音角色失败: ${it.message}"
            }
        }
    }

    fun saveXiaozhiConsoleAgentConfig() {
        if (xiaozhiNeedToken()) return
        val agentId = xiaozhiConsoleAgentId.trim()
        if (agentId.isBlank()) {
            Toast.makeText(context, "请先选择智能体", Toast.LENGTH_SHORT).show()
            return
        }
        settings.saveXiaozhiConfig(currentXiaozhiConfig())
        xiaozhiConsoleResult = "正在保存到小智控制台..."
        scope.launch {
            val result = withContext(Dispatchers.IO) {
                runCatching {
                    XiaozhiConsoleApi.saveAgentConfig(
                        token = xiaozhiConsoleToken,
                        agentId = agentId,
                        model = xiaozhiConsoleModel,
                        voice = xiaozhiVoiceRole
                    )
                }
            }
            result.onSuccess { agent ->
                xiaozhiConsoleAgentId = agent.id
                xiaozhiConsoleModel = agent.model
                xiaozhiVoiceRole = agent.voice
                settings.saveXiaozhiConfig(currentXiaozhiConfig())
                xiaozhiConsoleResult = "已保存到控制台: ${agent.name}"
                Toast.makeText(context, "小智控制台配置已保存", Toast.LENGTH_SHORT).show()
            }.onFailure {
                xiaozhiConsoleResult = "保存到控制台失败: ${it.message}"
            }
        }
    }

    fun saveZhilia(showToast: Boolean) {
        val saved = currentZhiliaConfig()
        val next = configs.filterNot { it.name == activeName || it.name == saved.name } + saved
        settings.saveZhiliaConfigs(next, saved.name)
        configs = settings.zhiliaConfigs()
        activeName = saved.name
        if (sp.getBoolean(AutoReplySettings.KEY_AI_CLEAR_CONTEXT_ON_SAVE, AutoReplySettings.DEFAULT_AI_CLEAR_CONTEXT_ON_SAVE)) {
            AutoReplyRuntime.clearAiHistories()
        }
        if (showToast) Toast.makeText(context, "智聊AI配置已保存", Toast.LENGTH_SHORT).show()
    }

    fun switchZhiliaTo(name: String) {
        if (name.isBlank()) {
            Toast.makeText(context, "请选择配置", Toast.LENGTH_SHORT).show()
            return
        }
        if (name == activeName) {
            zhiliaPendingName = activeName
            Toast.makeText(context, "已是当前配置", Toast.LENGTH_SHORT).show()
            return
        }
        saveZhilia(showToast = false)
        settings.saveZhiliaConfigs(settings.zhiliaConfigs(), name)
        configs = settings.zhiliaConfigs()
        activeName = settings.activeZhiliaName()
        zhiliaPendingName = activeName
        Toast.makeText(context, "已切换到 $activeName", Toast.LENGTH_SHORT).show()
    }

    fun uniqueConfigName(base: String): String {
        val names = configs.map { it.name }.toSet()
        if (base !in names) return base
        var index = 2
        while ("$base$index" in names) index++
        return "$base$index"
    }

    val visibleModels = remember(fetchedModels, modelQuery, favoriteModels) {
        val keyword = modelQuery.trim().lowercase(Locale.US)
        fetchedModels
            .filter { keyword.isBlank() || it.lowercase(Locale.US).contains(keyword) }
            .sortedWith(compareByDescending<String> { it in favoriteModels }.thenBy { it.lowercase(Locale.US) })
    }

    SettingsRouteTransition(
        targetState = route,
        label = "AutoReplyAiRoute",
        depthOf = { it.depth() }
    ) { currentRoute ->
        when (currentRoute) {
            AutoReplyAiRoute.Main -> {
                val scrollBehavior = MiuixScrollBehavior()
                PageScaffold(
                    title = "AI 配置",
                    largeTitle = "AI 配置",
                    scrollBehavior = scrollBehavior,
                    bottomBar = { BottomActionBar("返回", onBack) }
                ) { padding ->
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
                        state = mainListState,
                        contentPadding = PaddingValues(top = padding.calculateTopPadding() + 8.dp, bottom = padding.calculateBottomPadding() + 84.dp)
                    ) {
                        item { SmallTitle(text = "配置分组") }
                        item {
                            SettingsCard {
                                ActionRow("小智AI配置", xiaozhiServe.ifBlank { "未设置 WebSocket 地址" }) {
                                    route = AutoReplyAiRoute.Xiaozhi
                                }
                                InsetDivider()
                                ActionRow("智聊AI配置", "当前启用：$activeName") {
                                    route = AutoReplyAiRoute.Zhilia
                                }
                            }
                        }
                    }
                }
            }
            AutoReplyAiRoute.Xiaozhi -> {
                val scrollBehavior = MiuixScrollBehavior()
                PageScaffold(
                    title = "小智AI配置",
                    largeTitle = "小智AI配置",
                    scrollBehavior = scrollBehavior,
                    bottomBar = {
                        BottomActionBar(
                            primaryText = "保存小智",
                            onPrimaryClick = { saveXiaozhi(showToast = true) },
                            secondaryText = "返回",
                            onSecondaryClick = { route = AutoReplyAiRoute.Main }
                        )
                    }
                ) { padding ->
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
                        state = xiaozhiListState,
                        contentPadding = PaddingValues(top = padding.calculateTopPadding() + 8.dp, bottom = padding.calculateBottomPadding() + 84.dp)
                    ) {
                        item { SmallTitle(text = "连接地址") }
                        item {
                            SettingsCard {
                                InputRow("WebSocket 地址", "小智服务地址", xiaozhiServe) { xiaozhiServe = it }
                                InsetDivider()
                                InputRow("OTA 地址", "小智 OTA 地址", xiaozhiOta) { xiaozhiOta = it }
                                InsetDivider()
                                InputRow("控制台地址", "用于查看或绑定设备", xiaozhiConsole) { xiaozhiConsole = it }
                            }
                        }
                        item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "设备") }
                        item {
                            SettingsCard {
                                InfoRow("UUID", AutoReplyRuntime.xiaozhiDeviceUuid(context))
                                InsetDivider()
                                InfoRow("MAC 地址", AutoReplyRuntime.xiaozhiDeviceMac(context))
                                InsetDivider()
                                ActionRow("绑定设备", xiaozhiBindResult.ifBlank { "请求 OTA 获取验证码或绑定状态" }) {
                                    xiaozhiBindResult = "正在请求..."
                                    scope.launch {
                                        val result = withContext(Dispatchers.IO) {
                                            AutoReplyRuntime.bindXiaozhiDevice(context, currentXiaozhiConfig())
                                        }
                                        xiaozhiBindResult = result
                                        Toast.makeText(context, result.lineSequence().firstOrNull().orEmpty(), Toast.LENGTH_SHORT).show()
                                    }
                                }
                                val xiaozhiBindLink = remember(xiaozhiBindResult) { extractFirstHttpUrl(xiaozhiBindResult) }
                                if (xiaozhiBindLink != null) {
                                    InsetDivider()
                                    InfoRow("绑定链接", xiaozhiBindLink) {
                                        openExternalLink(context, xiaozhiBindLink)
                                    }
                                }
                            }
                        }
                        item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "控制台登录") }
                        item {
                            SettingsCard {
                                InfoRow("登录状态", if (xiaozhiConsoleToken.isBlank()) "未登录" else "已保存 token")
                                InsetDivider()
                                InputRow("手机号", "登录成功后会记住手机号", xiaozhiConsolePhone) { xiaozhiConsolePhone = it }
                                InsetDivider()
                                ActionRow("刷新图形验证码", if (xiaozhiCaptchaSvg.isBlank()) "先刷新，再输入图片上的字符" else "已刷新，输入下方图形验证码") {
                                    refreshXiaozhiCaptcha()
                                }
                                if (xiaozhiCaptchaSvg.isNotBlank()) {
                                    InsetDivider()
                                    XiaozhiCaptchaSvgView(xiaozhiCaptchaSvg)
                                }
                                InsetDivider()
                                InputRow("图形验证码", "输入上方图片里的字符", xiaozhiCaptchaCode) { xiaozhiCaptchaCode = it }
                                InsetDivider()
                                ActionRow("发送短信验证码", "需要先填写手机号和图形验证码") {
                                    sendXiaozhiSmsCode()
                                }
                                InsetDivider()
                                InputRow("短信验证码", "输入手机收到的验证码", xiaozhiSmsCode) { xiaozhiSmsCode = it }
                                InsetDivider()
                                ActionRow("登录并保存 token", "登录成功后自动保存 token 并拉取智能体") {
                                    loginXiaozhiConsole()
                                }
                                if (xiaozhiConsoleResult.isNotBlank()) {
                                    InsetDivider()
                                    InfoRow("控制台状态", xiaozhiConsoleResult)
                                }
                            }
                        }
                        item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "控制台配置") }
                        item {
                            SettingsCard {
                                ActionRow("选择智能体", xiaozhiAgentSummary(xiaozhiConsoleAgentId, xiaozhiAgents)) {
                                    fetchXiaozhiAgents(openPicker = true)
                                }
                                InsetDivider()
                                InfoRow("Agent ID", xiaozhiConsoleAgentId.ifBlank { "未选择" })
                                InsetDivider()
                                ActionRow("选择模型", xiaozhiModelSummary(xiaozhiConsoleModel, xiaozhiModels)) {
                                    fetchXiaozhiModels(openPicker = true)
                                }
                                InsetDivider()
                                ActionRow("选择语音角色", xiaozhiVoiceSummary(xiaozhiVoiceRole, xiaozhiVoices)) {
                                    fetchXiaozhiVoices(openPicker = true)
                                }
                                InsetDivider()
                                ActionRow("保存到小智控制台", "读取当前智能体配置后只替换模型和语音角色") {
                                    saveXiaozhiConsoleAgentConfig()
                                }
                            }
                        }
                        item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "回复") }
                        item {
                            SettingsCard {
                                InputRow("语音角色ID", "留空使用控制台当前角色", xiaozhiVoiceRole) { xiaozhiVoiceRole = it }
                                InsetDivider()
                                SwitchRow(
                                    checked = xiaozhiMusicMcp,
                                    title = "提示官方 Music 工具",
                                    summary = "点歌类问题会提示小智优先使用控制台启用的官方 Music MCP",
                                    onCheckedChange = { xiaozhiMusicMcp = it }
                                )
                            }
                        }
                        item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "MCP桥接") }
                        item {
                            SettingsCard {
                                SwitchRow(
                                    checked = xiaozhiMcpBridge,
                                    title = "启用MCP桥接实验",
                                    summary = "把 Hchat 能力暴露给小智调用；端点地址包含 token，请勿外传",
                                    onCheckedChange = { xiaozhiMcpBridge = it }
                                )
                                InsetDivider()
                                InputRow(
                                    "MCP Endpoint",
                                    "wss://api.xiaozhi.me/mcp/?token=...；留空不连接",
                                    xiaozhiMcpEndpoint
                                ) { xiaozhiMcpEndpoint = it }
                                InsetDivider()
                                XiaozhiMcpStatusRow(xiaozhiMcpStatus)
                                InsetDivider()
                                ActionRow("刷新连接状态", xiaozhiMcpStatus.detail) {
                                    if (xiaozhiConsoleToken.trim().isBlank() || xiaozhiConsoleAgentId.trim().isBlank()) {
                                        Toast.makeText(context, "请先登录并选择智能体", Toast.LENGTH_SHORT).show()
                                    } else {
                                        xiaozhiMcpStatus = XiaozhiMcpStatus(false, "查询中", "正在查询小智控制台...")
                                        scope.launch {
                                            val result = withContext(Dispatchers.IO) {
                                                runCatching {
                                                    XiaozhiConsoleApi.fetchMcpEndpointStatus(
                                                        xiaozhiConsoleToken,
                                                        xiaozhiConsoleAgentId
                                                    )
                                                }
                                            }
                                            result.onSuccess {
                                                xiaozhiMcpStatus = it
                                            }.onFailure {
                                                xiaozhiMcpStatus = XiaozhiMcpStatus(false, "查询失败", it.message.orEmpty())
                                            }
                                        }
                                    }
                                }
                                InsetDivider()
                                InputRow(
                                    "就绪等待时长",
                                    "断开后首次触发等待 MCP ready 的秒数，默认 5，可填 1-30",
                                    xiaozhiMcpReadySeconds
                                ) { xiaozhiMcpReadySeconds = it.filter { ch -> ch.isDigit() }.take(2) }
                                InsetDivider()
                                InputRow(
                                    "自动断开时长",
                                    "空闲多少秒后断开 MCP，默认 90，可填 10-600",
                                    xiaozhiMcpIdleSeconds
                                ) { xiaozhiMcpIdleSeconds = it.filter { ch -> ch.isDigit() }.take(3) }
                                InsetDivider()
                                SwitchRow(
                                    checked = xiaozhiMcpKugouEnabled,
                                    title = "调用点歌工具",
                                    summary = "让小智理解自然语言后调用本地点歌插件发送音乐卡片",
                                    onCheckedChange = { xiaozhiMcpKugouEnabled = it }
                                )
                                if (xiaozhiMcpKugouEnabled) {
                                    InsetDivider()
                                    InputRow(
                                        "点歌插件ID",
                                        "例如 QQ点歌",
                                        xiaozhiMcpKugouPluginId
                                    ) { xiaozhiMcpKugouPluginId = it }
                                    InsetDivider()
                                    InputRow(
                                        "点歌函数名",
                                        "例如 queryKugouMusic",
                                        xiaozhiMcpKugouFunction
                                    ) { xiaozhiMcpKugouFunction = it }
                                }
                            }
                        }
                        item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "运行状态") }
                        item {
                            SettingsCard {
                                ActionRow("立即清空上下文", "清除当前运行中的小智对话记忆") {
                                    AutoReplyRuntime.clearAiHistories()
                                    Toast.makeText(context, "小智上下文已清空", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }
            AutoReplyAiRoute.Zhilia -> {
                val scrollBehavior = MiuixScrollBehavior()
                PageScaffold(
                    title = "智聊AI配置",
                    largeTitle = "智聊AI配置",
                    scrollBehavior = scrollBehavior,
                    bottomBar = {
                        BottomActionBar(
                            primaryText = "保存智聊",
                            onPrimaryClick = { saveZhilia(showToast = true) },
                            secondaryText = "返回",
                            onSecondaryClick = { route = AutoReplyAiRoute.Main }
                        )
                    }
                ) { padding ->
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
                        state = zhiliaListState,
                        contentPadding = PaddingValues(top = padding.calculateTopPadding() + 8.dp, bottom = padding.calculateBottomPadding() + 84.dp)
                    ) {
                        item { SmallTitle(text = "当前配置") }
                        item {
                            SettingsCard {
                                InfoRow("当前启用", activeName)
                                InsetDivider()
                                InputRow("配置名称", "例如 DeepSeek 主账号", cfgName) { cfgName = it }
                                InsetDivider()
                                InputRow("API Key", "OpenAI 兼容接口密钥", apiKey) { apiKey = it }
                                InsetDivider()
                                InputRow("API 地址", "例如 https://api.xxx.com/v1", apiBase) { apiBase = it }
                                InsetDivider()
                                InputRow("API 路径", "默认 /chat/completions", apiPath) { apiPath = it }
                                InsetDivider()
                                InputRow("模型", "例如 deepseek-ai/DeepSeek-V3", model) { model = it }
                                InsetDivider()
                                InputRow("系统指令", "AI 角色设定和回复要求", prompt, minLines = 4) { prompt = it }
                                InsetDivider()
                                NumberInputRow("上下文轮数", "0 表示不保留上下文", contextLimit) { contextLimit = it }
                                InsetDivider()
                                SwitchRow(sp, AutoReplySettings.KEY_AI_STREAM, "流式请求", "失败时会自动尝试非流式", AutoReplySettings.DEFAULT_AI_STREAM)
                                InsetDivider()
                                SwitchRow(sp, AutoReplySettings.KEY_AI_CLEAR_CONTEXT_ON_SAVE, "保存后清空上下文", "修改人设或模型后避免继续沿用旧对话", AutoReplySettings.DEFAULT_AI_CLEAR_CONTEXT_ON_SAVE)
                            }
                        }
                        item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "配置管理") }
                        item {
                            SettingsCard {
                                ActionRow("配置列表", "${configs.size} 个配置，当前启用：$activeName") {
                                    zhiliaPendingName = activeName
                                    route = AutoReplyAiRoute.ZhiliaConfigs
                                }
                            }
                        }
                        item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "模型工具") }
                        item {
                            SettingsCard {
                                ActionRow("模型选择", model.ifBlank { "未设置模型" }) {
                                    pendingModel = model
                                    route = AutoReplyAiRoute.ZhiliaModels
                                }
                                InsetDivider()
                                ActionRow("测试连通性", testResult.ifBlank { "测试当前模型的流式和非流式请求" }) {
                                    val config = currentZhiliaConfig().toAiConfig(sp.getBoolean(AutoReplySettings.KEY_AI_STREAM, AutoReplySettings.DEFAULT_AI_STREAM))
                                    testResult = "正在测试..."
                                    scope.launch {
                                        testResult = withContext(Dispatchers.IO) {
                                            AutoReplyRuntime.testAiConnectivity(config)
                                        }
                                        Toast.makeText(context, testResult, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                        item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "运行状态") }
                        item {
                            SettingsCard {
                                ActionRow("立即清空上下文", "清除当前运行中的 AI 对话记忆") {
                                    AutoReplyRuntime.clearAiHistories()
                                    Toast.makeText(context, "AI 上下文已清空", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }
            AutoReplyAiRoute.ZhiliaConfigs -> {
                val scrollBehavior = MiuixScrollBehavior()
                PageScaffold(
                    title = "智聊配置列表",
                    largeTitle = "智聊配置列表",
                    scrollBehavior = scrollBehavior,
                    bottomBar = {
                        BottomActionBar(
                            primaryText = "切换到所选",
                            onPrimaryClick = { switchZhiliaTo(zhiliaPendingName) },
                            secondaryText = "返回",
                            onSecondaryClick = { route = AutoReplyAiRoute.Zhilia }
                        )
                    }
                ) { padding ->
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
                        state = zhiliaConfigListState,
                        contentPadding = PaddingValues(top = padding.calculateTopPadding() + 8.dp, bottom = padding.calculateBottomPadding() + 84.dp)
                    ) {
                        item { SmallTitle(text = "配置") }
                        configs.forEach { config ->
                            item {
                                SettingsCard(modifier = Modifier.padding(bottom = 8.dp)) {
                                    ActionRow(
                                        title = config.name,
                                        summary = buildString {
                                            append(config.model.ifBlank { "未设置模型" })
                                            if (config.name == activeName) append(" · 当前")
                                            if (config.name == zhiliaPendingName && config.name != activeName) append(" · 已选择")
                                        }
                                    ) {
                                        zhiliaPendingName = config.name
                                        Toast.makeText(context, "已选择 ${config.name}", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                        item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "管理") }
                        item {
                            SettingsCard {
                                ActionRow("新增空配置", "创建后留在此页，可再手动切换") {
                                    val newName = uniqueConfigName("新配置")
                                    val next = configs + AutoReplyZhiliaConfig(newName, "", AutoReplySettings.DEFAULT_AI_API_BASE, AutoReplySettings.DEFAULT_AI_API_PATH, AutoReplySettings.DEFAULT_AI_MODEL, "", AutoReplySettings.DEFAULT_AI_CONTEXT_LIMIT)
                                    settings.saveZhiliaConfigs(next, activeName)
                                    configs = settings.zhiliaConfigs()
                                    zhiliaPendingName = newName
                                    Toast.makeText(context, "已新增 $newName", Toast.LENGTH_SHORT).show()
                                }
                                InsetDivider()
                                ActionRow("复制当前输入", "按智聊编辑页当前输入复制配置") {
                                    val newName = uniqueConfigName("${cfgName.ifBlank { activeName }}_副本")
                                    val next = configs + currentZhiliaConfig().copy(name = newName)
                                    settings.saveZhiliaConfigs(next, activeName)
                                    configs = settings.zhiliaConfigs()
                                    zhiliaPendingName = newName
                                    Toast.makeText(context, "已复制 $newName", Toast.LENGTH_SHORT).show()
                                }
                                if (configs.size > 1) {
                                    InsetDivider()
                                    ActionRow("删除当前配置", "删除正在编辑的配置，至少保留一个") {
                                        val next = configs.filterNot { it.name == activeName || it.name == cfgName }
                                        val nextActive = next.firstOrNull()?.name ?: AutoReplySettings.DEFAULT_ZHILIA_CONFIG_NAME
                                        settings.saveZhiliaConfigs(next, nextActive)
                                        configs = settings.zhiliaConfigs()
                                        activeName = settings.activeZhiliaName()
                                        zhiliaPendingName = activeName
                                        Toast.makeText(context, "已删除当前配置", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                    }
                }
            }
            AutoReplyAiRoute.ZhiliaModels -> {
                val scrollBehavior = MiuixScrollBehavior()
                PageScaffold(
                    title = "智聊模型选择",
                    largeTitle = "智聊模型选择",
                    scrollBehavior = scrollBehavior,
                    bottomBar = {
                        BottomActionBar(
                            primaryText = "使用所选模型",
                            onPrimaryClick = {
                                model = pendingModel
                                Toast.makeText(context, "已选择模型: $pendingModel", Toast.LENGTH_SHORT).show()
                            },
                            secondaryText = "返回",
                            onSecondaryClick = { route = AutoReplyAiRoute.Zhilia }
                        )
                    }
                ) { padding ->
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
                        state = zhiliaModelListState,
                        contentPadding = PaddingValues(top = padding.calculateTopPadding() + 8.dp, bottom = padding.calculateBottomPadding() + 84.dp)
                    ) {
                        item { SmallTitle(text = "拉取模型") }
                        item {
                            SettingsCard {
                                ActionRow("拉取模型列表", if (loadingText.isBlank()) "从当前 API 地址获取 /models" else loadingText) {
                                    val base = apiBase
                                    val key = apiKey
                                    loadingText = "正在拉取模型..."
                                    scope.launch {
                                        val models = withContext(Dispatchers.IO) {
                                            AutoReplyRuntime.fetchAiModels(base, key)
                                        }
                                        fetchedModels = models
                                        loadingText = ""
                                        Toast.makeText(context, if (models.isEmpty()) "未获取到模型" else "已获取 ${models.size} 个模型", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                        if (visibleModels.isNotEmpty()) {
                            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "模型列表 · ${visibleModels.size}") }
                            item {
                                SettingsCard(modifier = Modifier.padding(bottom = 8.dp)) {
                                    InputRow("搜索", "输入模型名过滤", modelQuery) { modelQuery = it }
                                }
                            }
                            visibleModels.take(80).forEach { itemModel ->
                                item {
                                    ZhiliaModelSelectCard(
                                        modelName = itemModel,
                                        currentModel = model,
                                        pendingModel = pendingModel,
                                        favorite = itemModel in favoriteModels,
                                        onSelect = {
                                            pendingModel = itemModel
                                            Toast.makeText(context, "已选择 $itemModel", Toast.LENGTH_SHORT).show()
                                        },
                                        onToggleFavorite = {
                                            val next = if (itemModel in favoriteModels) favoriteModels - itemModel else favoriteModels + itemModel
                                            favoriteModels = next
                                            settings.saveFavoriteModels(apiBase, next)
                                            Toast.makeText(context, if (itemModel in next) "已收藏模型" else "已取消收藏", Toast.LENGTH_SHORT).show()
                                        }
                                    )
                                }
                            }
                        } else {
                            item { SettingsCard { EmptyText("暂无模型列表，先点击“拉取模型列表”。") } }
                        }
                    }
                }
            }
            is AutoReplyAiRoute.OptionPicker -> {
                OptionPickerPage(
                    request = currentRoute.request,
                    onBack = { route = AutoReplyAiRoute.Xiaozhi },
                    onSelected = { currentRoute.request.onSelected(it) }
                )
            }
        }
    }
}

@Composable
internal fun ZhiliaModelSelectCard(
    modelName: String,
    currentModel: String,
    pendingModel: String,
    favorite: Boolean,
    onSelect: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    val selected = modelName == pendingModel
    val current = modelName == currentModel
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 3.dp),
        cornerRadius = 14.dp
    ) {
        var pressed by remember { mutableStateOf(false) }
        val pressFeedbackColor = rememberPressFeedbackColor(pressed)
        Column(
            modifier = Modifier.fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(pressFeedbackColor)
                .responsiveTap(
                    onClick = onSelect,
                    onPressedChange = { pressed = it }
                )
                .padding(horizontal = 16.dp, vertical = 13.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = modelName, color = MiuixTheme.colorScheme.onSurface, fontWeight = FontWeight.Medium)
                    val state = buildString {
                        if (current) append("当前")
                        if (selected && !current) {
                            if (isNotEmpty()) append(" · ")
                            append("已选择")
                        }
                        if (favorite) {
                            if (isNotEmpty()) append(" · ")
                            append("已收藏")
                        }
                        if (isEmpty()) append("点击选择")
                    }
                    Text(text = state, color = MiuixTheme.colorScheme.onSurfaceVariantSummary, fontSize = 12.sp)
                }
                Text(
                    text = if (selected) "已选" else "选择",
                    color = if (selected) MiuixTheme.colorScheme.primary else MiuixTheme.colorScheme.onSurfaceVariantSummary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            InsetDivider()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { onToggleFavorite() }
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    text = if (favorite) "取消收藏" else "收藏",
                    color = MiuixTheme.colorScheme.primary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
