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
internal fun CustomNotificationRuleEditorPage(
    context: Context,
    initialRule: CustomNotificationRule,
    listState: LazyListState,
    isDefault: Boolean = false,
    onBack: () -> Unit,
    onSave: (CustomNotificationRule) -> Unit,
    onDelete: () -> Unit,
    onPickOnlyMembers: (String) -> Unit,
    onPickBlockMembers: (String) -> Unit
) {
    var enabled by remember(initialRule) { mutableStateOf(initialRule.enabled) }
    var dnd by remember(initialRule) { mutableStateOf(initialRule.mode == CustomNotificationSettings.MODE_DND) }
    var vibrate by remember(initialRule) { mutableStateOf(initialRule.vibrate) }
    var sound by remember(initialRule) { mutableStateOf(initialRule.sound) }
    var markRead by remember(initialRule) { mutableStateOf(initialRule.markRead) }
    var quickReply by remember(initialRule) { mutableStateOf(initialRule.quickReply) }
    var quoteQuickReply by remember(initialRule) { mutableStateOf(initialRule.quoteQuickReply) }
    var mergeByTalker by remember(initialRule) { mutableStateOf(initialRule.mergeByTalker) }
    var showDetail by remember(initialRule) { mutableStateOf(initialRule.showDetail) }
    var ignoreWechatDnd by remember(initialRule) { mutableStateOf(initialRule.ignoreWechatDnd) }
    var muteEnable by remember(initialRule) { mutableStateOf(initialRule.muteEnable) }
    var muteStart by remember(initialRule) { mutableStateOf(initialRule.muteStart) }
    var muteEnd by remember(initialRule) { mutableStateOf(initialRule.muteEnd) }
    var ringtone by remember(initialRule) { mutableStateOf(initialRule.ringtone) }
    var blockAtAll by remember(initialRule) { mutableStateOf(initialRule.blockAtAll) }
    var blockAtMe by remember(initialRule) { mutableStateOf(initialRule.blockAtMe) }
    var onlyMembers by remember(initialRule) { mutableStateOf(initialRule.onlyMembers) }
    var blockMembers by remember(initialRule) { mutableStateOf(initialRule.blockMembers) }
    val scrollBehavior = MiuixScrollBehavior()

    PageScaffold(
        title = when {
            isDefault && initialRule.official -> "默认公众号通知"
            isDefault && initialRule.group -> "默认群聊通知"
            isDefault -> "默认私聊通知"
            initialRule.group -> "群聊通知配置"
            initialRule.official -> "公众号通知配置"
            else -> "私聊通知配置"
        },
        largeTitle = initialRule.label.ifBlank { initialRule.talker },
        scrollBehavior = scrollBehavior,
        bottomBar = {
            BottomActionBar(
                primaryText = "保存",
                onPrimaryClick = {
                    onSave(
                        initialRule.copy(
                            enabled = enabled,
                            mode = if (dnd) CustomNotificationSettings.MODE_DND else CustomNotificationSettings.MODE_NOTIFY,
                            vibrate = vibrate,
                            sound = sound,
                            markRead = markRead,
                            quickReply = quickReply,
                            quoteQuickReply = quoteQuickReply,
                            mergeByTalker = mergeByTalker,
                            showDetail = showDetail,
                            ignoreWechatDnd = ignoreWechatDnd,
                            muteEnable = muteEnable,
                            muteStart = CustomNotificationSettings.normalizeTime(muteStart, CustomNotificationSettings.DEFAULT_MUTE_START),
                            muteEnd = CustomNotificationSettings.normalizeTime(muteEnd, CustomNotificationSettings.DEFAULT_MUTE_END),
                            ringtone = ringtone,
                            blockAtAll = blockAtAll,
                            blockAtMe = blockAtMe,
                            onlyMembers = if (isDefault) "" else CustomNotificationSettings.normalizeMemberRules(onlyMembers),
                            blockMembers = if (isDefault) "" else CustomNotificationSettings.normalizeMemberRules(blockMembers)
                        )
                    )
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
            item { SmallTitle(text = "会话") }
            item {
                SettingsCard {
                    if (isDefault) {
                        InfoRow(
                            "类型",
                            when {
                                initialRule.official -> "未单独配置的公众号"
                                initialRule.group -> "未单独配置的群聊"
                                else -> "未单独配置的私聊"
                            }
                        )
                    } else {
                        InfoRow("名称", initialRule.label.ifBlank { initialRule.talker })
                        InsetDivider()
                        InfoRow("ID", initialRule.talker)
                    }
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "通知") }
            item {
                SettingsCard {
                    SwitchRow(
                        enabled,
                        if (isDefault) "启用默认规则" else "启用此会话规则",
                        if (isDefault) "开启后接管未单独配置的对应会话通知" else "关闭后不再接管该会话通知"
                    ) { enabled = it }
                    InsetDivider()
                    SwitchRow(dnd, "免打扰", "开启后不弹自定义通知，也会拦截原生通知") { dnd = it }
                    InsetDivider()
                    SwitchRow(
                        ignoreWechatDnd,
                        "忽略微信自带的消息免打扰",
                        "开启后，该会话在微信中设置免打扰仍会弹出自定义通知"
                    ) { ignoreWechatDnd = it }
                    InsetDivider()
                    SwitchRow(vibrate, "震动", "通知时震动") { vibrate = it }
                    InsetDivider()
                    SwitchRow(sound, "铃声", "通知时播放系统或自定义铃声") { sound = it }
                    InsetDivider()
                    SwitchRow(markRead, "已读按钮", "通知栏直接标记当前会话已读") { markRead = it }
                    InsetDivider()
                    SwitchRow(quickReply, "快捷回复", "通知栏直接回复文本消息") { quickReply = it }
                    if (quickReply) {
                        InsetDivider()
                        SwitchRow(quoteQuickReply, "引用消息回复", "快捷回复时引用触发通知的原消息") {
                            quoteQuickReply = it
                        }
                    }
                    InsetDivider()
                    SwitchRow(mergeByTalker, "合并同会话通知", "同一 wxid 的新消息更新到一条通知") { mergeByTalker = it }
                    InsetDivider()
                    SwitchRow(showDetail, "通知显示消息详情", "关闭后只显示收到一条新消息") { showDetail = it }
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "铃声") }
            item {
                SettingsCard {
                    ActionRow("选择系统铃声", ringtoneDisplayName(context, ringtone, RedPacketSettings.NOTIFY_SOUND_MODE_SYSTEM)) {
                        val activity = context as? Activity
                        if (activity == null) {
                            Toast.makeText(context, "当前页面无法打开铃声选择器", Toast.LENGTH_SHORT).show()
                        } else {
                            RingtonePickerBridge.launchSystem(activity, ringtone) { picked ->
                                ringtone = CustomNotificationRuntime.freezeRingtoneUri(context, picked)
                                Toast.makeText(context, "铃声已选择，保存后生效", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    InsetDivider()
                    ActionRow("从文件选择铃声", if (ringtone.isBlank()) "未选择" else ringtoneDisplayName(context, ringtone, RedPacketSettings.NOTIFY_SOUND_MODE_CUSTOM)) {
                        val activity = context as? Activity
                        if (activity == null) {
                            Toast.makeText(context, "当前页面无法打开文件选择器", Toast.LENGTH_SHORT).show()
                        } else {
                            RingtonePickerBridge.launchFile(activity) { picked ->
                                ringtone = CustomNotificationRuntime.freezeRingtoneUri(context, picked)
                                Toast.makeText(context, "铃声已选择，保存后生效", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    if (ringtone.isNotBlank()) {
                        InsetDivider()
                        ActionRow("清空铃声", "恢复跟随系统") {
                            ringtone = ""
                            Toast.makeText(context, "铃声已清空，保存后生效", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "静默时段") }
            item {
                SettingsCard {
                    SwitchRow(muteEnable, "开启时段静默", "指定时间内不弹通知") { muteEnable = it }
                    if (muteEnable) {
                        InsetDivider()
                        TimeOfDayPickerRow("开始时间", muteStart) { muteStart = it }
                        InsetDivider()
                        TimeOfDayPickerRow("结束时间", muteEnd) { muteEnd = it }
                    }
                }
            }
            if (initialRule.group) {
                item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "群聊过滤") }
                item {
                    SettingsCard {
                        if (!isDefault) {
                            ActionRow("仅显示成员通知", memberRulesSummary(onlyMembers)) {
                                onPickOnlyMembers(onlyMembers)
                            }
                            InsetDivider()
                            ActionRow("屏蔽成员通知", memberRulesSummary(blockMembers)) {
                                onPickBlockMembers(blockMembers)
                            }
                            InsetDivider()
                        }
                        SwitchRow(blockAtAll, "屏蔽@所有人", "命中 @所有人 时不弹通知") { blockAtAll = it }
                        InsetDivider()
                        SwitchRow(blockAtMe, "屏蔽@我", "命中 @我 时不弹通知") { blockAtMe = it }
                    }
                }
            }
            if (!isDefault) {
                item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "操作") }
                item {
                    SettingsCard {
                        ActionRow("删除规则", "移除该会话自定义通知配置") {
                            onDelete()
                        }
                    }
                }
            }
        }
    }
}

internal fun customNotificationRuleFromContact(option: ContactOption): CustomNotificationRule {
    return CustomNotificationRule(
        id = option.id,
        talker = option.id,
        label = option.label.ifBlank { option.id },
        group = option.group,
        official = option.official
    )
}

internal fun customNotificationRuleTitle(rule: CustomNotificationRule): String {
    val prefix = when {
        rule.group -> "群聊"
        rule.official -> "公众号"
        else -> "私聊"
    }
    val state = if (rule.enabled) "" else "（关闭）"
    return "$prefix · ${rule.label.ifBlank { rule.talker }}$state"
}

internal fun customNotificationRuleSummary(context: Context, rule: CustomNotificationRule): String {
    val items = ArrayList<String>()
    if (!rule.enabled) items += "未启用"
    if (rule.mode == CustomNotificationSettings.MODE_DND) items += "免打扰"
    if (rule.sound) items += if (rule.ringtone.isBlank()) "铃声" else ringtoneDisplayName(context, rule.ringtone, RedPacketSettings.NOTIFY_SOUND_MODE_CUSTOM)
    if (rule.vibrate) items += "震动"
    if (rule.markRead) items += "已读按钮"
    if (rule.quickReply) items += if (rule.quoteQuickReply) "引用快捷回复" else "快捷回复"
    if (rule.mergeByTalker) items += "合并通知"
    if (rule.ignoreWechatDnd) items += "忽略微信免打扰"
    if (rule.muteEnable) items += "${rule.muteStart}-${rule.muteEnd}静默"
    if (rule.group) {
        if (rule.onlyMembers.isNotBlank()) items += "仅${CustomNotificationSettings.splitMemberRules(rule.onlyMembers).size}成员"
        if (rule.blockMembers.isNotBlank()) items += "屏蔽${CustomNotificationSettings.splitMemberRules(rule.blockMembers).size}成员"
        if (rule.blockAtAll) items += "屏蔽@所有人"
        if (rule.blockAtMe) items += "屏蔽@我"
    }
    return items.ifEmpty { listOf("点击配置通知规则") }.joinToString(" / ")
}

internal fun memberRulesSummary(value: String): String {
    val count = CustomNotificationSettings.splitMemberRules(value).size
    return if (count == 0) "未设置" else "已设置 $count 项"
}

internal fun memberRulesToGroupEntries(groupId: String, value: String): String {
    return CustomNotificationSettings.splitMemberRules(value)
        .map { member -> groupMemberEntry(groupId, member) }
        .joinToString(",")
}

internal fun groupEntriesToMemberRules(groupId: String, entries: List<String>): String {
    val members = entries.mapNotNull { entry ->
        val normalized = entry.trim()
        val separators = charArrayOf('/', '#', ':', '：')
        val index = separators.map { normalized.indexOf(it) }
            .filter { it > 0 }
            .minOrNull()
            ?: return@mapNotNull normalized.takeIf { it.isNotBlank() }
        val group = normalized.substring(0, index).trim()
        val member = normalized.substring(index + 1).trim()
        if (group == groupId && member.isNotBlank()) member else null
    }
    return members.distinct().joinToString(",")
}

@Composable
internal fun KeywordNotificationMiuixPage(
    context: Context,
    provider: FeatureSettingsProvider,
    onBack: () -> Unit
) {
    val settings = remember { KeywordNotificationSettings(context) }
    val sp = remember { HchatStorage.preferences(context, KeywordNotificationSettings.PREFS_NAME) }
    var route by remember { mutableStateOf<KeywordNotificationRoute>(KeywordNotificationRoute.Main) }
    var keywords by remember { mutableStateOf(settings.keywords()) }
    var excludeContacts by remember { mutableStateOf(settings.excludeContacts()) }
    var includeContacts by remember { mutableStateOf(settings.includeContacts()) }
    val keywordMainListState = rememberLazyListState()
    val keywordListState = rememberLazyListState()
    var keywordQuery by remember { mutableStateOf("") }

    fun saveKeywords(next: List<KeywordRule>) {
        val clean = next.map { it.copy(keyword = it.keyword.trim()) }
            .filter { it.keyword.isNotBlank() }
            .distinctBy { it.keyword }
        keywords = clean
        settings.saveKeywords(clean)
    }

    fun saveContacts(includeMode: Boolean, ids: Set<String>) {
        if (includeMode) {
            includeContacts = ids
            settings.saveIncludeContacts(ids)
        } else {
            excludeContacts = ids
            settings.saveExcludeContacts(ids)
        }
    }

    SettingsRouteTransition(
        targetState = route,
        label = "KeywordNotificationRoute",
        depthOf = { it.depth() }
    ) { currentRoute ->
        when (currentRoute) {
            KeywordNotificationRoute.Main -> KeywordNotificationMainPage(
                context = context,
                provider = provider,
                sp = sp,
                keywords = keywords,
                excludeContacts = excludeContacts,
                includeContacts = includeContacts,
                listState = keywordMainListState,
                onBack = onBack,
                onOpenKeywords = { route = KeywordNotificationRoute.KeywordList },
                onOpenTemplates = { route = KeywordNotificationRoute.TemplateEditor },
                onPickContacts = { includeMode ->
                    val current = if (includeMode) includeContacts else excludeContacts
                    route = KeywordNotificationRoute.ContactPicker(
                        request = ContactPickerRequest(
                            title = if (includeMode) "选择仅生效聊天" else "选择排除聊天",
                            mode = ContactPickerMode.BOTH,
                            multiSelect = true,
                            existingValue = current.joinToString(","),
                            onValue = {},
                            enableLabels = true
                        ),
                        includeMode = includeMode
                    )
                },
                onClearContacts = { includeMode ->
                    saveContacts(includeMode, emptySet())
                    Toast.makeText(context, if (includeMode) "已清空仅生效名单" else "已清空排除名单", Toast.LENGTH_SHORT).show()
                }
            )
            KeywordNotificationRoute.KeywordList -> KeywordNotificationKeywordListPage(
                keywords = keywords,
                query = keywordQuery,
                onQueryChange = { keywordQuery = it },
                listState = keywordListState,
                onBack = { route = KeywordNotificationRoute.Main },
                onAdd = { route = KeywordNotificationRoute.KeywordEditor(null) },
                onEdit = { route = KeywordNotificationRoute.KeywordEditor(it.keyword) },
                onClear = {
                    saveKeywords(emptyList())
                    Toast.makeText(context, "已清空关键词", Toast.LENGTH_SHORT).show()
                }
            )
            is KeywordNotificationRoute.KeywordEditor -> {
                val old = currentRoute.oldKeyword?.let { oldKeyword -> keywords.firstOrNull { it.keyword == oldKeyword } }
                KeywordNotificationKeywordEditorPage(
                    initial = old,
                    onBack = { route = KeywordNotificationRoute.KeywordList },
                    onSave = { saved ->
                        if (saved.keyword.isBlank()) {
                            Toast.makeText(context, "关键词不能为空", Toast.LENGTH_SHORT).show()
                        } else if (currentRoute.oldKeyword != saved.keyword && keywords.any { it.keyword == saved.keyword }) {
                            Toast.makeText(context, "该关键词已存在", Toast.LENGTH_SHORT).show()
                        } else {
                            val next = if (currentRoute.oldKeyword == null) {
                                keywords + saved
                            } else {
                                keywords.map { if (it.keyword == currentRoute.oldKeyword) saved else it }
                            }
                            saveKeywords(next)
                            Toast.makeText(context, "关键词已保存", Toast.LENGTH_SHORT).show()
                            route = KeywordNotificationRoute.KeywordList
                        }
                    },
                    onDelete = if (old == null) {
                        null
                    } else {
                        {
                            saveKeywords(keywords.filterNot { it.keyword == old.keyword })
                            Toast.makeText(context, "关键词已删除", Toast.LENGTH_SHORT).show()
                            route = KeywordNotificationRoute.KeywordList
                        }
                    }
                )
            }
            KeywordNotificationRoute.TemplateEditor -> KeywordNotificationTemplatePage(
                context = context,
                sp = sp,
                onBack = { route = KeywordNotificationRoute.Main }
            )
            is KeywordNotificationRoute.ContactPicker -> ContactPickerPage(
                context = context,
                request = currentRoute.request,
                onBack = { route = KeywordNotificationRoute.Main },
                onConfirm = { picked ->
                    val ids = picked.map { it.id }.filter { it.isNotBlank() }.toSet()
                    saveContacts(currentRoute.includeMode, ids)
                    Toast.makeText(context, "名单已保存", Toast.LENGTH_SHORT).show()
                    route = KeywordNotificationRoute.Main
                }
            )
        }
    }
}

@Composable
internal fun TextSpeechMiuixPage(
    context: Context,
    provider: FeatureSettingsProvider,
    onBack: () -> Unit
) {
    val settings = remember { TextSpeechSettings(context) }
    val sp = remember { HchatStorage.preferences(context, TextSpeechSettings.PREFS_NAME) }
    var route by remember { mutableStateOf<TextSpeechRoute>(TextSpeechRoute.Main) }
    var allowedContacts by remember { mutableStateOf(settings.allowedContacts()) }
    val mainListState = rememberLazyListState()

    fun saveAllowedContacts(ids: Set<String>) {
        allowedContacts = ids
        settings.saveAllowedContacts(ids)
    }

    SettingsRouteTransition(
        targetState = route,
        label = "TextSpeechRoute",
        depthOf = { it.depth() }
    ) { currentRoute ->
        when (currentRoute) {
            TextSpeechRoute.Main -> TextSpeechMainPage(
                provider = provider,
                sp = sp,
                allowedContacts = allowedContacts,
                listState = mainListState,
                onBack = onBack,
                onPickContacts = {
                    route = TextSpeechRoute.ContactPicker(
                        ContactPickerRequest(
                            title = "设置允许名单",
                            mode = ContactPickerMode.BOTH,
                            multiSelect = true,
                            existingValue = allowedContacts.joinToString("|"),
                            onValue = {},
                            enableLabels = true
                        )
                    )
                }
            )
            is TextSpeechRoute.ContactPicker -> ContactPickerPage(
                context = context,
                request = currentRoute.request,
                onBack = { route = TextSpeechRoute.Main },
                onConfirm = { picked ->
                    saveAllowedContacts(
                        picked.map { it.id }
                            .filter { it.isNotBlank() }
                            .toSet()
                    )
                    Toast.makeText(context, "允许名单已保存", Toast.LENGTH_SHORT).show()
                    route = TextSpeechRoute.Main
                }
            )
        }
    }
}

@Composable
internal fun TextSpeechMainPage(
    provider: FeatureSettingsProvider,
    sp: SharedPreferences,
    allowedContacts: Set<String>,
    listState: LazyListState,
    onBack: () -> Unit,
    onPickContacts: () -> Unit
) {
    val context = LocalContext.current
    val scrollBehavior = MiuixScrollBehavior()
    var enabled by remember {
        mutableStateOf(sp.getBoolean(TextSpeechSettings.KEY_ENABLE, TextSpeechSettings.DEFAULT_ENABLE))
    }
    var ttsEngine by remember {
        mutableStateOf(
            sp.getString(TextSpeechSettings.KEY_TTS_ENGINE, TextSpeechSettings.DEFAULT_TTS_ENGINE)
                ?: TextSpeechSettings.DEFAULT_TTS_ENGINE
        )
    }
    var ttsVoice by remember {
        mutableStateOf(
            sp.getString(TextSpeechSettings.KEY_TTS_VOICE, TextSpeechSettings.DEFAULT_TTS_VOICE)
                ?: TextSpeechSettings.DEFAULT_TTS_VOICE
        )
    }
    var ttsVoiceChoices by remember { mutableStateOf<List<PopupChoice<String>>>(emptyList()) }
    var ttsVoiceLoading by remember { mutableStateOf(true) }
    var ttsVoiceError by remember { mutableStateOf("") }
    var ttsFallbackEnginePackage by remember { mutableStateOf("") }
    val installedTtsEngines = remember(context) { TextSpeechEngineCatalog.installed(context) }
    val ttsFallbackEngineLabel = remember(installedTtsEngines, ttsFallbackEnginePackage) {
        installedTtsEngines.firstOrNull { it.packageName == ttsFallbackEnginePackage }
            ?.label
            ?: ttsFallbackEnginePackage
    }
    val ttsEngineOptions = remember(installedTtsEngines, ttsEngine) {
        buildList {
            add(PopupChoice("跟随系统默认", TextSpeechSettings.DEFAULT_TTS_ENGINE))
            installedTtsEngines.forEach { add(PopupChoice(it.label, it.packageName)) }
            if (ttsEngine.isNotBlank() && installedTtsEngines.none { it.packageName == ttsEngine }) {
                add(PopupChoice("已不可用（$ttsEngine）", ttsEngine))
            }
        }
    }
    val ttsVoiceOptions = remember(ttsVoiceChoices, ttsVoice) {
        buildList {
            add(PopupChoice("跟随引擎默认", TextSpeechSettings.DEFAULT_TTS_VOICE))
            addAll(ttsVoiceChoices)
            if (ttsVoice.isNotBlank() && ttsVoiceChoices.none { it.value == ttsVoice }) {
                add(PopupChoice("已不可用（$ttsVoice）", ttsVoice))
            }
        }
    }
    DisposableEffect(context, ttsEngine) {
        ttsVoiceLoading = true
        ttsVoiceError = ""
        ttsFallbackEnginePackage = ""
        ttsVoiceChoices = emptyList()
        val handle = TextSpeechVoiceCatalog.load(context, ttsEngine) { result ->
            ttsVoiceChoices = result.options.map { PopupChoice(it.label, it.name) }
            ttsVoiceError = result.error
            ttsFallbackEnginePackage = if (result.usedFallback) {
                result.activeEnginePackage.ifBlank { "其它可用引擎" }
            } else {
                ""
            }
            ttsVoiceLoading = false
        }
        onDispose { handle.cancel() }
    }
    var volumeControl by remember {
        mutableStateOf(
            sp.getBoolean(
                TextSpeechSettings.KEY_VOLUME_CONTROL,
                TextSpeechSettings.DEFAULT_VOLUME_CONTROL
            )
        )
    }
    var playVoiceMessages by remember {
        mutableStateOf(
            sp.getBoolean(
                TextSpeechSettings.KEY_PLAY_VOICE_MESSAGES,
                TextSpeechSettings.DEFAULT_PLAY_VOICE_MESSAGES
            )
        )
    }
    var customAnnouncement by remember {
        mutableStateOf(
            sp.getBoolean(
                TextSpeechSettings.KEY_ANNOUNCE_SENDER,
                TextSpeechSettings.DEFAULT_ANNOUNCE_SENDER
            )
        )
    }
    var announcementTemplate by remember {
        mutableStateOf(
            sp.getString(
                TextSpeechSettings.KEY_ANNOUNCEMENT_TEMPLATE,
                TextSpeechSettings.DEFAULT_ANNOUNCEMENT_TEMPLATE
            ) ?: TextSpeechSettings.DEFAULT_ANNOUNCEMENT_TEMPLATE
        )
    }
    var timeFormat by remember {
        mutableStateOf(
            sp.getString(TextSpeechSettings.KEY_TIME_FORMAT, TextSpeechSettings.DEFAULT_TIME_FORMAT)
                ?: TextSpeechSettings.DEFAULT_TIME_FORMAT
        )
    }
    var respectWechatDnd by remember {
        mutableStateOf(
            sp.getBoolean(
                TextSpeechSettings.KEY_RESPECT_WECHAT_DND,
                TextSpeechSettings.DEFAULT_RESPECT_WECHAT_DND
            )
        )
    }
    var quietEnabled by remember {
        mutableStateOf(
            sp.getBoolean(
                TextSpeechSettings.KEY_QUIET_ENABLE,
                TextSpeechSettings.DEFAULT_QUIET_ENABLE
            )
        )
    }
    var quietStart by remember {
        mutableStateOf(
            sp.getString(TextSpeechSettings.KEY_QUIET_START, TextSpeechSettings.DEFAULT_QUIET_START)
                ?: TextSpeechSettings.DEFAULT_QUIET_START
        )
    }
    var quietEnd by remember {
        mutableStateOf(
            sp.getString(TextSpeechSettings.KEY_QUIET_END, TextSpeechSettings.DEFAULT_QUIET_END)
                ?: TextSpeechSettings.DEFAULT_QUIET_END
        )
    }

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
            item { SmallTitle(text = "播报") }
            item {
                SettingsCard {
                    SwitchRow(
                        checked = enabled,
                        title = "自动播放语音",
                        summary = "播报允许名单内收到的消息"
                    ) {
                        enabled = it
                        sp.edit().putBoolean(TextSpeechSettings.KEY_ENABLE, it).apply()
                    }
                    if (enabled) {
                        InsetDivider()
                        PopupChoiceRow(
                            title = "语音引擎",
                            summary = "选择 TTS 播报使用的语音引擎",
                            options = ttsEngineOptions,
                            currentValue = ttsEngine,
                            onValueChanged = {
                                ttsEngine = it
                                ttsVoice = TextSpeechSettings.DEFAULT_TTS_VOICE
                                sp.edit()
                                    .putString(TextSpeechSettings.KEY_TTS_ENGINE, it)
                                    .putString(
                                        TextSpeechSettings.KEY_TTS_VOICE,
                                        TextSpeechSettings.DEFAULT_TTS_VOICE
                                    )
                                    .apply()
                            }
                        )
                        InsetDivider()
                        PopupChoiceRow(
                            title = "播报角色",
                            summary = when {
                                ttsVoiceLoading -> "正在读取所选引擎的播报角色"
                                ttsVoiceError.isNotBlank() -> "读取失败：$ttsVoiceError"
                                ttsFallbackEnginePackage.isNotBlank() ->
                                    "系统默认不可用，已临时使用 $ttsFallbackEngineLabel"
                                ttsVoiceChoices.isEmpty() -> "所选引擎未提供可选角色，将跟随引擎默认"
                                else -> "选择所选引擎提供的播报角色"
                            },
                            options = ttsVoiceOptions,
                            currentValue = ttsVoice,
                            enabled = !ttsVoiceLoading,
                            onValueChanged = {
                                ttsVoice = it
                                sp.edit().putString(TextSpeechSettings.KEY_TTS_VOICE, it).apply()
                            }
                        )
                        InsetDivider()
                        SwitchRow(
                            checked = playVoiceMessages,
                            title = "播放语音消息",
                            summary = "收到语音时直接播放原语音，不使用 TTS"
                        ) {
                            playVoiceMessages = it
                            sp.edit()
                                .putBoolean(TextSpeechSettings.KEY_PLAY_VOICE_MESSAGES, it)
                                .apply()
                        }
                        InsetDivider()
                        SwitchRow(
                            checked = volumeControl,
                            title = "音键控制",
                            summary = "音量减：暂停\n音量加：继续\n暂停时再按音量减：跳过"
                        ) {
                            volumeControl = it
                            sp.edit().putBoolean(TextSpeechSettings.KEY_VOLUME_CONTROL, it).apply()
                        }
                        InsetDivider()
                        SwitchRow(
                            checked = customAnnouncement,
                            title = "自定义播报内容",
                            summary = "分别使用消息对应的昵称、群聊和正文变量"
                        ) {
                            customAnnouncement = it
                            sp.edit().putBoolean(TextSpeechSettings.KEY_ANNOUNCE_SENDER, it).apply()
                        }
                        if (customAnnouncement) {
                            InsetDivider()
                            VariableInputRow(
                                title = "播报内容格式",
                                summary = "点击下方中文变量插入到光标位置",
                                value = announcementTemplate,
                                variables = textSpeechTemplateVariables,
                                minLines = 3
                            ) {
                                announcementTemplate = it
                                sp.edit()
                                    .putString(TextSpeechSettings.KEY_ANNOUNCEMENT_TEMPLATE, it)
                                    .apply()
                            }
                            if (announcementTemplate.contains(TextSpeechSettings.VAR_MESSAGE_TIME)) {
                                InsetDivider()
                                InputRow(
                                    title = "消息时间格式",
                                    summary = if (TextSpeechSettings.isValidTimeFormat(timeFormat)) {
                                        "例如 HH:mm:ss 或 yyyy-MM-dd HH:mm:ss"
                                    } else {
                                        "格式无效，当前输入不会保存"
                                    },
                                    value = timeFormat
                                ) {
                                    timeFormat = it
                                    if (TextSpeechSettings.isValidTimeFormat(it)) {
                                        sp.edit().putString(
                                            TextSpeechSettings.KEY_TIME_FORMAT,
                                            TextSpeechSettings.normalizeTimeFormat(it)
                                        ).apply()
                                    }
                                }
                            }
                            InsetDivider()
                            ActionRow(
                                title = "恢复默认格式",
                                summary = TextSpeechSettings.DEFAULT_ANNOUNCEMENT_TEMPLATE
                            ) {
                                announcementTemplate = TextSpeechSettings.DEFAULT_ANNOUNCEMENT_TEMPLATE
                                timeFormat = TextSpeechSettings.DEFAULT_TIME_FORMAT
                                sp.edit()
                                    .putString(
                                        TextSpeechSettings.KEY_ANNOUNCEMENT_TEMPLATE,
                                        TextSpeechSettings.DEFAULT_ANNOUNCEMENT_TEMPLATE
                                    )
                                    .putString(
                                        TextSpeechSettings.KEY_TIME_FORMAT,
                                        TextSpeechSettings.DEFAULT_TIME_FORMAT
                                    )
                                    .apply()
                            }
                        }
                        InsetDivider()
                        SwitchRow(
                            checked = respectWechatDnd,
                            title = "免打扰不播报",
                            summary = "私聊或群聊开启微信消息免打扰后停止播报"
                        ) {
                            respectWechatDnd = it
                            sp.edit()
                                .putBoolean(TextSpeechSettings.KEY_RESPECT_WECHAT_DND, it)
                                .apply()
                        }
                        InsetDivider()
                        SwitchRow(
                            checked = quietEnabled,
                            title = "定时免打扰",
                            summary = "指定时段内暂停自动播报"
                        ) {
                            quietEnabled = it
                            sp.edit().putBoolean(TextSpeechSettings.KEY_QUIET_ENABLE, it).apply()
                        }
                        if (quietEnabled) {
                            InsetDivider()
                            TimeOfDayPickerRow("开始时间", quietStart) {
                                quietStart = it
                                sp.edit().putString(TextSpeechSettings.KEY_QUIET_START, it).apply()
                            }
                            InsetDivider()
                            TimeOfDayPickerRow("结束时间", quietEnd) {
                                quietEnd = it
                                sp.edit().putString(TextSpeechSettings.KEY_QUIET_END, it).apply()
                            }
                        }
                        InsetDivider()
                        ActionRow(
                            title = "设置允许名单",
                            summary = if (allowedContacts.isEmpty()) {
                                "未设置"
                            } else {
                                "已选择 ${allowedContacts.size} 个聊天"
                            },
                            onClick = onPickContacts
                        )
                    }
                }
            }
        }
    }
}

@Composable
internal fun ZombieCheckMiuixPage(
    context: Context,
    provider: FeatureSettingsProvider,
    onBack: () -> Unit
) {
    val settings = remember { ZombieCheckSettings(context) }
    var route by remember { mutableStateOf<ZombieCheckRoute>(ZombieCheckRoute.Main) }
    var targetIds by remember { mutableStateOf(settings.targetIds()) }
    var excludedIds by remember { mutableStateOf(settings.excludedIds()) }
    var pendingDeleteTargets by remember { mutableStateOf(emptyList<ZombieCheckDeleteTarget>()) }

    SettingsRouteTransition(
        targetState = route,
        label = "ZombieCheckRoute",
        depthOf = { if (it is ZombieCheckRoute.Main) 0 else 1 }
    ) { currentRoute ->
        when (currentRoute) {
            ZombieCheckRoute.Main -> ZombieCheckMainPage(
                context = context,
                provider = provider,
                targetIds = targetIds,
                excludedIds = excludedIds,
                pendingDeleteTargets = pendingDeleteTargets,
                onBack = onBack,
                onPickTargets = {
                    route = ZombieCheckRoute.ContactPicker(
                        ZombieCheckSelection.TARGETS,
                        ContactPickerRequest(
                            title = "选择检测好友",
                            mode = ContactPickerMode.FRIENDS,
                            multiSelect = true,
                            existingValue = targetIds.joinToString("|"),
                            onValue = {},
                            enableLabels = true
                        )
                    )
                },
                onPickExcluded = {
                    route = ZombieCheckRoute.ContactPicker(
                        ZombieCheckSelection.EXCLUDED,
                        ContactPickerRequest(
                            title = "选择排除好友",
                            mode = ContactPickerMode.FRIENDS,
                            multiSelect = true,
                            existingValue = excludedIds.joinToString("|"),
                            onValue = {},
                            enableLabels = true
                        )
                    )
                },
                onPickDelete = {
                    val detectedIds = ZombieCheckController.snapshot().results
                        .filter { it.type == ZombieCheckResultType.DEAD }
                        .map { it.wxid }
                        .toSet()
                    route = ZombieCheckRoute.ContactPicker(
                        ZombieCheckSelection.DELETE,
                        ContactPickerRequest(
                            title = "批量删除好友",
                            mode = ContactPickerMode.FRIENDS,
                            multiSelect = true,
                            existingValue = detectedIds.joinToString("|"),
                            onValue = {},
                            enableLabels = true
                        )
                    )
                },
                onDismissDelete = { pendingDeleteTargets = emptyList() },
                onConfirmDelete = { clearRecord, delaySeconds ->
                    val result = ZombieCheckController.deleteFriends(
                        pendingDeleteTargets.map { it.wxid },
                        clearRecord,
                        delaySeconds
                    )
                    pendingDeleteTargets = emptyList()
                    result
                }
            )
            is ZombieCheckRoute.ContactPicker -> ContactPickerPage(
                context = context,
                request = currentRoute.request,
                onBack = { route = ZombieCheckRoute.Main },
                onConfirm = { picked ->
                    val ids = picked.map { it.id }.filter { it.isNotBlank() }.toSet()
                    when (currentRoute.selection) {
                        ZombieCheckSelection.TARGETS -> {
                            targetIds = ids
                            settings.saveTargetIds(ids)
                        }
                        ZombieCheckSelection.EXCLUDED -> {
                            excludedIds = ids
                            settings.saveExcludedIds(ids)
                        }
                        ZombieCheckSelection.DELETE -> {
                            pendingDeleteTargets = picked.map {
                                ZombieCheckDeleteTarget(it.id, it.label.ifBlank { it.id })
                            }
                        }
                    }
                    route = ZombieCheckRoute.Main
                }
            )
        }
    }
}
