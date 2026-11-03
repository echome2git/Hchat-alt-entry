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
internal fun QuoteDeleteClearMiuixPage(
    context: Context,
    provider: FeatureSettingsProvider,
    onBack: () -> Unit
) {
    val sp = remember { HchatStorage.preferences(context, QuoteDeleteClearSettings.PREFS_NAME) }
    val listState = rememberLazyListState()
    val scrollBehavior = MiuixScrollBehavior()

    PageScaffold(
        title = provider.title(),
        largeTitle = provider.title(),
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
            item { SmallTitle(text = "聊天输入框") }
            item {
                SettingsCard {
                    SwitchRow(
                        sp,
                        QuoteDeleteClearSettings.KEY_ENABLE,
                        "删除键清引用",
                        "输入框为空且已引用消息时，按输入法删除键直接取消引用",
                        QuoteDeleteClearSettings.DEFAULT_ENABLE
                    )
                }
            }
        }
    }
}

@Composable
internal fun EmojiSaveMiuixPage(
    context: Context,
    provider: FeatureSettingsProvider,
    onBack: () -> Unit
) {
    val sp = remember { HchatStorage.preferences(context, EmojiSaveSettings.PREFS_NAME) }
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
            item { SmallTitle(text = "聊天表情") }
            item {
                SettingsCard {
                    SwitchRow(
                        sp,
                        EmojiSaveSettings.KEY_ENABLE,
                        "保存表情",
                        "长按聊天表情后显示保存入口",
                        EmojiSaveSettings.DEFAULT_ENABLE
                    )
                }
            }
        }
    }
}

@Composable
internal fun SwipeQuoteMiuixPage(
    context: Context,
    provider: FeatureSettingsProvider,
    onBack: () -> Unit
) {
    val sp = remember { HchatStorage.preferences(context, SwipeQuoteSettings.PREFS_NAME) }
    val listState = rememberLazyListState()
    val scrollBehavior = MiuixScrollBehavior()

    PageScaffold(
        title = provider.title(),
        largeTitle = provider.title(),
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
            item { SmallTitle(text = "引用") }
            item {
                SettingsCard {
                    SwitchRow(
                        sp,
                        SwipeQuoteSettings.KEY_ENABLE,
                        "左滑引用回复",
                        "左滑消息后调用微信原生引用入口",
                        SwipeQuoteSettings.DEFAULT_ENABLE
                    )
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "复读触发方式") }
            item {
                SettingsCard {
                    SwitchRow(
                        sp,
                        SwipeQuoteSettings.KEY_REPEAT_ENABLE,
                        "右滑复读",
                        "右滑消息后复读到当前聊天",
                        SwipeQuoteSettings.DEFAULT_REPEAT_ENABLE
                    )
                    InsetDivider()
                    SwitchRow(
                        sp,
                        SwipeQuoteSettings.KEY_REPEAT_MENU_ENABLE,
                        "长按菜单复读",
                        "长按消息后点击 +1 复读到当前聊天",
                        SwipeQuoteSettings.DEFAULT_REPEAT_MENU_ENABLE
                    )
                }
            }
        }
    }
}

@Composable
internal fun FakeVoiceDurationMiuixPage(
    context: Context,
    provider: FeatureSettingsProvider,
    onBack: () -> Unit
) {
    val sp = remember { HchatStorage.preferences(context, FakeVoiceDurationSettings.PREFS_NAME) }
    val listState = rememberLazyListState()
    val scrollBehavior = MiuixScrollBehavior()
    var enabled by remember {
        mutableStateOf(
            sp.getBoolean(
                FakeVoiceDurationSettings.KEY_ENABLE,
                FakeVoiceDurationSettings.DEFAULT_ENABLE
            )
        )
    }
    var durationSeconds by remember {
        mutableStateOf(
            sp.getInt(
                FakeVoiceDurationSettings.KEY_DURATION_SECONDS,
                FakeVoiceDurationSettings.DEFAULT_DURATION_SECONDS
            ).toString()
        )
    }

    PageScaffold(
        title = provider.title(),
        largeTitle = provider.title(),
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
            item { SmallTitle(text = "聊天语音") }
            item {
                SettingsCard {
                    SwitchRow(
                        checked = enabled,
                        title = "伪造语音时长",
                        summary = "发送语音时使用自定义显示时长",
                        onCheckedChange = {
                            enabled = it
                            sp.edit().putBoolean(FakeVoiceDurationSettings.KEY_ENABLE, it).apply()
                        }
                    )
                    if (enabled) {
                        InsetDivider()
                        NumberInputRow("显示时长", "单位秒，1-60", durationSeconds) { next ->
                            val value = next.toIntOrNull()
                            durationSeconds = value?.coerceIn(
                                FakeVoiceDurationSettings.MIN_DURATION_SECONDS,
                                FakeVoiceDurationSettings.MAX_DURATION_SECONDS
                            )?.toString() ?: next
                            if (value == null) return@NumberInputRow
                            sp.edit().putInt(
                                FakeVoiceDurationSettings.KEY_DURATION_SECONDS,
                                value.coerceIn(
                                    FakeVoiceDurationSettings.MIN_DURATION_SECONDS,
                                    FakeVoiceDurationSettings.MAX_DURATION_SECONDS
                                )
                            ).apply()
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun VoicePreviewMiuixPage(
    context: Context,
    provider: FeatureSettingsProvider,
    onBack: () -> Unit
) {
    val sp = remember { HchatStorage.preferences(context, VoicePreviewSettings.PREFS_NAME) }
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
            item { SmallTitle(text = "聊天语音") }
            item {
                SettingsCard {
                    SwitchRow(
                        sp,
                        VoicePreviewSettings.KEY_ENABLE,
                        "语音消息预览",
                        "长按聊天语音后查看时长并播放",
                        VoicePreviewSettings.DEFAULT_ENABLE
                    )
                }
            }
        }
    }
}

@Composable
internal fun FakeMiniProgramBaseLibMiuixPage(
    context: Context,
    provider: FeatureSettingsProvider,
    onBack: () -> Unit
) {
    val sp = remember { HchatStorage.preferences(context, FakeMiniProgramBaseLibSettings.PREFS_NAME) }
    val listState = rememberLazyListState()
    val scrollBehavior = MiuixScrollBehavior()
    var enabled by remember {
        mutableStateOf(
            sp.getBoolean(
                FakeMiniProgramBaseLibSettings.KEY_ENABLE,
                FakeMiniProgramBaseLibSettings.DEFAULT_ENABLE
            )
        )
    }
    PageScaffold(
        title = provider.title(),
        largeTitle = provider.title(),
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
            item { SmallTitle(text = "小程序") }
            item {
                SettingsCard {
                    SwitchRow(
                        checked = enabled,
                        title = "兼容低版本小程序",
                        summary = "伪装启动基础库版本并阻止官方升级页",
                        onCheckedChange = {
                            enabled = it
                            sp.edit().putBoolean(FakeMiniProgramBaseLibSettings.KEY_ENABLE, it).apply()
                        }
                    )
                }
            }
        }
    }
}

@Composable
internal fun SkipMiniProgramVideoAdsMiuixPage(
    context: Context,
    provider: FeatureSettingsProvider,
    onBack: () -> Unit
) {
    val sp = remember {
        HchatStorage.preferences(context, SkipMiniProgramVideoAdsSettings.PREFS_NAME)
    }
    val listState = rememberLazyListState()
    val scrollBehavior = MiuixScrollBehavior()
    var enabled by remember {
        mutableStateOf(
            sp.getBoolean(
                SkipMiniProgramVideoAdsSettings.KEY_ENABLE,
                SkipMiniProgramVideoAdsSettings.DEFAULT_ENABLE
            )
        )
    }
    PageScaffold(
        title = provider.title(),
        largeTitle = provider.title(),
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
            item { SmallTitle(text = "小程序") }
            item {
                SettingsCard {
                    SwitchRow(
                        checked = enabled,
                        title = "跳过小程序视频广告",
                        summary = "自动跳过小程序视频广告",
                        onCheckedChange = {
                            enabled = it
                            sp.edit().putBoolean(
                                SkipMiniProgramVideoAdsSettings.KEY_ENABLE,
                                it
                            ).apply()
                        }
                    )
                }
            }
        }
    }
}

@Composable
internal fun SkipGlobalMiniProgramSplashAdsMiuixPage(
    context: Context,
    provider: FeatureSettingsProvider,
    onBack: () -> Unit
) {
    val sp = remember {
        HchatStorage.preferences(context, SkipGlobalMiniProgramSplashAdsSettings.PREFS_NAME)
    }
    val listState = rememberLazyListState()
    val scrollBehavior = MiuixScrollBehavior()
    var enabled by remember {
        mutableStateOf(
            sp.getBoolean(
                SkipGlobalMiniProgramSplashAdsSettings.KEY_ENABLE,
                SkipGlobalMiniProgramSplashAdsSettings.DEFAULT_ENABLE
            )
        )
    }
    PageScaffold(
        title = provider.title(),
        largeTitle = provider.title(),
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
            item { SmallTitle(text = "小程序") }
            item {
                SettingsCard {
                    SwitchRow(
                        checked = enabled,
                        title = "跳过全局小程序开屏广告",
                        summary = "阻止所有小程序展示启动开屏广告，修改后需重启微信",
                        onCheckedChange = {
                            enabled = it
                            sp.edit().putBoolean(
                                SkipGlobalMiniProgramSplashAdsSettings.KEY_ENABLE,
                                it
                            ).apply()
                        }
                    )
                }
            }
        }
    }
}

@Composable
internal fun FakeLocationMiuixPage(
    context: Context,
    provider: FeatureSettingsProvider,
    onBack: () -> Unit
) {
    val sp = remember { HchatStorage.preferences(context, FakeLocationSettings.PREFS_NAME) }
    var enabled by remember {
        mutableStateOf(sp.getBoolean(FakeLocationSettings.KEY_ENABLE, FakeLocationSettings.DEFAULT_ENABLE))
    }
    var latitude by remember {
        mutableStateOf(sp.getString(FakeLocationSettings.KEY_LATITUDE, FakeLocationSettings.DEFAULT_LATITUDE).orEmpty())
    }
    var longitude by remember {
        mutableStateOf(sp.getString(FakeLocationSettings.KEY_LONGITUDE, FakeLocationSettings.DEFAULT_LONGITUDE).orEmpty())
    }
    var showManualInput by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val scrollBehavior = MiuixScrollBehavior()

    fun saveLocation(lat: Double, lon: Double) {
        latitude = formatFakeLocationCoordinate(lat)
        longitude = formatFakeLocationCoordinate(lon)
        sp.edit()
            .putString(FakeLocationSettings.KEY_LATITUDE, latitude)
            .putString(FakeLocationSettings.KEY_LONGITUDE, longitude)
            .apply()
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
            item { SmallTitle(text = "定位") }
            item {
                SettingsCard {
                    SwitchRow(
                        checked = enabled,
                        title = "启用虚拟定位",
                        summary = "同时覆盖微信和小程序获取的位置",
                        onCheckedChange = {
                            enabled = it
                            sp.edit().putBoolean(FakeLocationSettings.KEY_ENABLE, it).apply()
                        }
                    )
                    InsetDivider()
                    ActionRow("搜索并选择位置", "打开微信选择位置页，可搜索地点") {
                        val activity = context as? Activity
                        if (activity == null) {
                            Toast.makeText(context, "当前页面无法打开微信地图", Toast.LENGTH_SHORT).show()
                        } else {
                            NativeFakeLocationPickerBridge.launch(activity) { lat, lon ->
                                saveLocation(lat, lon)
                                Toast.makeText(context, "已保存虚拟定位", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    InsetDivider()
                    ActionRow("手动输入经纬度", "纬度 -90~90，经度 -180~180") {
                        showManualInput = true
                    }
                    InsetDivider()
                    InfoRow("当前纬度", latitude) {
                        copyFakeLocationCoordinate(context, "Hchat 纬度", latitude)
                    }
                    InsetDivider()
                    InfoRow("当前经度", longitude) {
                        copyFakeLocationCoordinate(context, "Hchat 经度", longitude)
                    }
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "说明") }
            item {
                SettingsCard {
                    InfoRow("生效范围", "微信和小程序定位")
                }
            }
        }
    }

    if (showManualInput) {
        FakeLocationManualInputDialog(
            initialLatitude = latitude,
            initialLongitude = longitude,
            onDismiss = { showManualInput = false },
            onConfirm = { lat, lon ->
                saveLocation(lat, lon)
                showManualInput = false
                Toast.makeText(context, "已保存虚拟定位", Toast.LENGTH_SHORT).show()
            }
        )
    }
}

@Composable
internal fun GameEmojiMiuixPage(
    context: Context,
    provider: FeatureSettingsProvider,
    onBack: () -> Unit
) {
    val sp = remember { HchatStorage.preferences(context, GameEmojiSettings.PREFS_NAME) }
    var fixedResult by remember {
        mutableStateOf(
            sp.getBoolean(
                GameEmojiSettings.KEY_FIXED_RESULT,
                GameEmojiSettings.DEFAULT_FIXED_RESULT
            )
        )
    }
    var pickBeforeSend by remember {
        mutableStateOf(
            sp.getBoolean(
                GameEmojiSettings.KEY_PICK_BEFORE_SEND,
                GameEmojiSettings.DEFAULT_PICK_BEFORE_SEND
            )
        )
    }
    var diceResult by remember {
        mutableStateOf(
            sp.getInt(GameEmojiSettings.KEY_DICE_RESULT, GameEmojiSettings.DEFAULT_DICE_RESULT)
                .coerceIn(1, 6)
        )
    }
    var rpsResult by remember {
        mutableStateOf(
            sp.getInt(GameEmojiSettings.KEY_RPS_RESULT, GameEmojiSettings.DEFAULT_RPS_RESULT)
                .coerceIn(GameEmojiSettings.RPS_SCISSORS, GameEmojiSettings.RPS_PAPER)
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
            item { SmallTitle(text = "发送方式") }
            item {
                SettingsCard {
                    SwitchRow(
                        checked = fixedResult,
                        title = "使用固定结果",
                        summary = "直接发送下方设置的骰子点数或猜拳结果",
                        onCheckedChange = { checked ->
                            fixedResult = checked
                            if (checked) pickBeforeSend = false
                            sp.edit()
                                .putBoolean(GameEmojiSettings.KEY_FIXED_RESULT, checked)
                                .putBoolean(
                                    GameEmojiSettings.KEY_PICK_BEFORE_SEND,
                                    if (checked) false else pickBeforeSend
                                )
                                .apply()
                        }
                    )
                    InsetDivider()
                    SwitchRow(
                        checked = pickBeforeSend,
                        title = "发送时选择",
                        summary = "每次发送骰子或猜拳前弹出结果选择",
                        onCheckedChange = { checked ->
                            pickBeforeSend = checked
                            if (checked) fixedResult = false
                            sp.edit()
                                .putBoolean(GameEmojiSettings.KEY_PICK_BEFORE_SEND, checked)
                                .putBoolean(
                                    GameEmojiSettings.KEY_FIXED_RESULT,
                                    if (checked) false else fixedResult
                                )
                                .apply()
                        }
                    )
                }
            }
            if (fixedResult) {
                item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "固定结果") }
                item {
                    SettingsCard {
                        PopupOptionRow(
                            title = "骰子点数",
                            summary = "$diceResult 点",
                            options = (1..6).map { OptionItem("$it 点", it, "") },
                            currentValue = diceResult,
                            onValueChanged = { value ->
                                diceResult = value
                                sp.edit().putInt(GameEmojiSettings.KEY_DICE_RESULT, value).apply()
                            }
                        )
                        InsetDivider()
                        PopupOptionRow(
                            title = "猜拳结果",
                            summary = GameEmojiSettings.rpsLabel(rpsResult),
                            options = listOf(
                                OptionItem("剪刀", GameEmojiSettings.RPS_SCISSORS, ""),
                                OptionItem("石头", GameEmojiSettings.RPS_ROCK, ""),
                                OptionItem("布", GameEmojiSettings.RPS_PAPER, "")
                            ),
                            currentValue = rpsResult,
                            onValueChanged = { value ->
                                rpsResult = value
                                sp.edit().putInt(GameEmojiSettings.KEY_RPS_RESULT, value).apply()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
internal fun FakeLocationManualInputDialog(
    initialLatitude: String,
    initialLongitude: String,
    onDismiss: () -> Unit,
    onConfirm: (Double, Double) -> Unit
) {
    var latitude by remember(initialLatitude) { mutableStateOf(initialLatitude) }
    var longitude by remember(initialLongitude) { mutableStateOf(initialLongitude) }
    val parsed = validFakeLocation(latitude.toDoubleOrNull(), longitude.toDoubleOrNull())
    WindowDialog(
        show = true,
        title = "手动输入经纬度",
        onDismissRequest = onDismiss,
        content = {
            Column(modifier = Modifier.fillMaxWidth()) {
                InputRow("纬度", "范围 -90 到 90", latitude) { latitude = cleanCoordinateInput(it) }
                InputRow("经度", "范围 -180 到 180", longitude) { longitude = cleanCoordinateInput(it) }
                if (parsed == null) {
                    Text(
                        text = "请输入有效的纬度和经度",
                        color = Color(0xFFD93025),
                        fontSize = 13.sp,
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                        textAlign = TextAlign.Center
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextButton(
                        text = "取消",
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.textButtonColorsPrimary()
                    )
                    TextButton(
                        text = "保存",
                        onClick = { parsed?.let { onConfirm(it.first, it.second) } },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.textButtonColorsPrimary()
                    )
                }
            }
        }
    )
}

internal fun cleanCoordinateInput(value: String): String {
    return value.filterIndexed { index, char ->
        char.isDigit() || char == '.' || (char == '-' && index == 0)
    }
}

internal fun validFakeLocation(latitude: Double?, longitude: Double?): Pair<Double, Double>? {
    if (latitude == null || longitude == null || !latitude.isFinite() || !longitude.isFinite()) return null
    if (latitude !in -90.0..90.0 || longitude !in -180.0..180.0) return null
    return latitude to longitude
}

internal fun parseNativeFakeLocation(text: String): Pair<Double, Double>? {
    val latitude = Regex("""(?:lat|latitude)\s*[:= ]\s*([-+]?[0-9]*\.?[0-9]+)""", RegexOption.IGNORE_CASE)
        .find(text)?.groupValues?.getOrNull(1)?.toDoubleOrNull()
    val longitude = Regex("""(?:lng|lon|longitude)\s*[:= ]\s*([-+]?[0-9]*\.?[0-9]+)""", RegexOption.IGNORE_CASE)
        .find(text)?.groupValues?.getOrNull(1)?.toDoubleOrNull()
    return validFakeLocation(latitude, longitude)
}

internal fun formatFakeLocationCoordinate(value: Double): String {
    return String.format(Locale.US, "%.6f", value)
}

internal fun copyFakeLocationCoordinate(context: Context, label: String, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? android.content.ClipboardManager
    clipboard?.setPrimaryClip(ClipData.newPlainText(label, text))
    Toast.makeText(context, "已复制", Toast.LENGTH_SHORT).show()
}

@Composable
internal fun MessageBubbleMiuixPage(
    context: Context,
    provider: FeatureSettingsProvider,
    onBack: () -> Unit
) {
    val sp = remember { HchatStorage.preferences(context, MessageBubbleSettings.PREFS_NAME) }
    var enabled by remember {
        mutableStateOf(sp.getBoolean(MessageBubbleSettings.KEY_ENABLE, MessageBubbleSettings.DEFAULT_ENABLE))
    }
    var separateDarkMode by remember {
        mutableStateOf(
            sp.getBoolean(
                MessageBubbleSettings.KEY_SEPARATE_DARK_MODE,
                MessageBubbleSettings.DEFAULT_SEPARATE_DARK_MODE
            )
        )
    }
    var revision by remember { mutableStateOf(0) }
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
            item { SmallTitle(text = "基础") }
            item {
                SettingsCard {
                    SwitchRow(
                        checked = enabled,
                        title = "启用消息气泡",
                        summary = "使用本地图片替换微信原生消息气泡",
                        onCheckedChange = {
                            enabled = it
                            sp.edit().putBoolean(MessageBubbleSettings.KEY_ENABLE, it).apply()
                        }
                    )
                    if (enabled) {
                        InsetDivider()
                        SwitchRow(
                            checked = separateDarkMode,
                            title = "深色模式单独设置",
                            summary = "未设置深色气泡时自动沿用对应浅色气泡",
                            onCheckedChange = {
                                separateDarkMode = it
                                sp.edit()
                                    .putBoolean(MessageBubbleSettings.KEY_SEPARATE_DARK_MODE, it)
                                    .apply()
                            }
                        )
                    }
                }
            }
            if (enabled) {
                item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "浅色模式") }
                item {
                    SettingsCard {
                        MessageBubbleAssetRow(context, MessageBubbleSlot.LEFT_LIGHT, revision) { revision++ }
                        InsetDivider()
                        MessageBubbleAssetRow(context, MessageBubbleSlot.RIGHT_LIGHT, revision) { revision++ }
                    }
                }
                if (separateDarkMode) {
                    item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "深色模式") }
                    item {
                        SettingsCard {
                            MessageBubbleAssetRow(context, MessageBubbleSlot.LEFT_DARK, revision) { revision++ }
                            InsetDivider()
                            MessageBubbleAssetRow(context, MessageBubbleSlot.RIGHT_DARK, revision) { revision++ }
                        }
                    }
                }
                MessageBubbleKind.values()
                    .filter { it != MessageBubbleKind.GENERAL }
                    .forEach { kind ->
                        item {
                            SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "${kind.displayName}气泡")
                        }
                        item {
                            SettingsCard {
                                if (kind == MessageBubbleKind.SYSTEM) {
                                    MessageBubbleAssetRow(
                                        context,
                                        MessageBubbleSlot.resolve(kind, false, false),
                                        revision
                                    ) { revision++ }
                                } else {
                                    MessageBubbleAssetRow(
                                        context,
                                        MessageBubbleSlot.resolve(kind, false, false),
                                        revision
                                    ) { revision++ }
                                    InsetDivider()
                                    MessageBubbleAssetRow(
                                        context,
                                        MessageBubbleSlot.resolve(kind, true, false),
                                        revision
                                    ) { revision++ }
                                }
                            }
                        }
                        if (separateDarkMode) {
                            item {
                                SettingsCard {
                                    if (kind == MessageBubbleKind.SYSTEM) {
                                        MessageBubbleAssetRow(
                                            context,
                                            MessageBubbleSlot.resolve(kind, false, true),
                                            revision
                                        ) { revision++ }
                                    } else {
                                        MessageBubbleAssetRow(
                                            context,
                                            MessageBubbleSlot.resolve(kind, false, true),
                                            revision
                                        ) { revision++ }
                                        InsetDivider()
                                        MessageBubbleAssetRow(
                                            context,
                                            MessageBubbleSlot.resolve(kind, true, true),
                                            revision
                                        ) { revision++ }
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
internal fun MessageBubbleAssetRow(
    context: Context,
    slot: MessageBubbleSlot,
    revision: Int,
    onChanged: () -> Unit
) {
    val selected = remember(slot, revision) { MessageBubbleStore.hasAsset(context, slot) }
    val emptySummary = when {
        slot.kind == MessageBubbleKind.GENERAL && slot.darkMode -> "未选择，沿用对应浅色气泡"
        slot.kind == MessageBubbleKind.GENERAL -> "未选择，保持微信原生气泡"
        slot.darkMode -> "未选择，沿用同类浅色气泡"
        else -> "未选择，保持微信原生气泡"
    }
    ActionRow(
        title = slot.displayName,
        summary = if (selected) "已选择，点击更换或恢复" else emptySummary
    ) {
        val activity = context as? Activity ?: return@ActionRow
        val pickAsset = {
            MessageBubblePicker.launch(activity, slot) { result ->
                when (result) {
                    MessageBubblePickResult.SAVED -> {
                        onChanged()
                        Toast.makeText(context, "${slot.displayName}已更新", Toast.LENGTH_SHORT).show()
                    }
                    MessageBubblePickResult.FAILED -> {
                        Toast.makeText(context, "气泡图片无效或读取失败", Toast.LENGTH_SHORT).show()
                    }
                    MessageBubblePickResult.CANCELLED -> Unit
                }
            }
        }
        if (!selected) {
            pickAsset()
        } else {
            VoiceForwardMiuixDialog.showChoices(
                activity = activity,
                title = slot.displayName,
                summary = "管理当前气泡图片",
                choices = listOf(
                    "更换气泡" to "重新选择一张本地图片",
                    "恢复微信气泡" to "删除当前自定义气泡"
                ),
                onSelected = { choice ->
                    if (choice == 0) {
                        pickAsset()
                    } else if (MessageBubbleStore.remove(context, slot)) {
                        onChanged()
                        Toast.makeText(context, "已恢复微信气泡", Toast.LENGTH_SHORT).show()
                    }
                },
                onDismiss = {}
            )
        }
    }
}
