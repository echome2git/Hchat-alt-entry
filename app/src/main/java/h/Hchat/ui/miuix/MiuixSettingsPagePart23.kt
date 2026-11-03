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
internal fun TextVoiceMainPage(
    provider: FeatureSettingsProvider,
    sp: SharedPreferences,
    selectedVoice: String,
    selectedEngine: String,
    selectedTtsVoice: String,
    onBack: () -> Unit,
    onPickVoice: () -> Unit,
    onEngineChanged: (String) -> Unit,
    onTtsVoiceChanged: (String) -> Unit
) {
    val context = LocalContext.current
    val listState = rememberLazyListState()
    val scrollBehavior = MiuixScrollBehavior()
    val installedTtsEngines = remember(context) { TextSpeechEngineCatalog.installed(context) }
    val engineOptions = remember(installedTtsEngines, selectedEngine) {
        buildList {
            add(PopupChoice("在线语音", TextVoiceSettings.ENGINE_ONLINE))
            add(PopupChoice("跟随系统默认 TTS", TextVoiceSettings.ENGINE_TTS_PREFIX))
            installedTtsEngines.forEach { option ->
                add(PopupChoice(option.label, TextVoiceSettings.ENGINE_TTS_PREFIX + option.packageName))
            }
            val selectedPackage = TextVoiceSettings.ttsPackage(selectedEngine)
            if (TextVoiceSettings.isTtsEngine(selectedEngine) &&
                selectedPackage.isNotBlank() &&
                installedTtsEngines.none { it.packageName == selectedPackage }
            ) {
                add(PopupChoice("已不可用（$selectedPackage）", selectedEngine))
            }
        }
    }
    var ttsVoiceChoices by remember { mutableStateOf<List<PopupChoice<String>>>(emptyList()) }
    var ttsVoiceLoading by remember { mutableStateOf(false) }
    var ttsVoiceError by remember { mutableStateOf("") }
    var ttsFallbackEnginePackage by remember { mutableStateOf("") }
    val ttsFallbackEngineLabel = remember(installedTtsEngines, ttsFallbackEnginePackage) {
        installedTtsEngines.firstOrNull { it.packageName == ttsFallbackEnginePackage }
            ?.label
            ?: ttsFallbackEnginePackage
    }
    DisposableEffect(context, selectedEngine) {
        val useTts = TextVoiceSettings.isTtsEngine(selectedEngine)
        ttsVoiceLoading = useTts
        ttsVoiceError = ""
        ttsFallbackEnginePackage = ""
        ttsVoiceChoices = emptyList()
        val handle = if (useTts) {
            TextSpeechVoiceCatalog.load(
                context,
                TextVoiceSettings.ttsPackage(selectedEngine)
            ) { result ->
                ttsVoiceChoices = result.options.map { PopupChoice(it.label, it.name) }
                ttsVoiceError = result.error
                ttsFallbackEnginePackage = if (result.usedFallback) {
                    result.activeEnginePackage.ifBlank { "其它可用引擎" }
                } else {
                    ""
                }
                ttsVoiceLoading = false
            }
        } else {
            null
        }
        onDispose { handle?.cancel() }
    }
    val ttsVoiceOptions = remember(ttsVoiceChoices, selectedTtsVoice) {
        buildList {
            add(PopupChoice("跟随引擎默认", TextVoiceSettings.DEFAULT_TTS_VOICE))
            addAll(ttsVoiceChoices)
            if (selectedTtsVoice.isNotBlank() && ttsVoiceChoices.none { it.value == selectedTtsVoice }) {
                add(PopupChoice("已不可用（$selectedTtsVoice）", selectedTtsVoice))
            }
        }
    }
    var sendEnabled by remember {
        mutableStateOf(
            sp.getBoolean(TextVoiceSettings.KEY_SEND_ENABLE, TextVoiceSettings.DEFAULT_SEND_ENABLE)
        )
    }
    var playEnabled by remember {
        mutableStateOf(
            sp.getBoolean(TextVoiceSettings.KEY_PLAY_ENABLE, TextVoiceSettings.DEFAULT_PLAY_ENABLE)
        )
    }
    var speechRate by remember {
        mutableStateOf(
            TextVoiceSettings.normalizeSpeechRate(
                sp.getFloat(TextVoiceSettings.KEY_SPEECH_RATE, TextVoiceSettings.DEFAULT_SPEECH_RATE)
            )
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
            item { SmallTitle(text = "文本转语音") }
            item {
                SettingsCard {
                    SwitchRow(
                        checked = sendEnabled,
                        title = "文本转换语音模式（发送文字转语音）",
                        summary = "聊天输入 #tts 开启或关闭当前会话，输入 #tts e 使用英文模式"
                    ) {
                        sendEnabled = it
                        sp.edit().putBoolean(TextVoiceSettings.KEY_SEND_ENABLE, it).apply()
                    }
                    InsetDivider()
                    SwitchRow(
                        checked = playEnabled,
                        title = "文字转换语音播放",
                        summary = "长按文字消息后选择“转语音播放[H]”"
                    ) {
                        playEnabled = it
                        sp.edit().putBoolean(TextVoiceSettings.KEY_PLAY_ENABLE, it).apply()
                    }
                    if (sendEnabled || playEnabled) {
                        InsetDivider()
                        PopupChoiceRow(
                            title = "转换引擎",
                            summary = "选择在线语音或设备上已安装的 TTS 引擎",
                            options = engineOptions,
                            currentValue = selectedEngine,
                            onValueChanged = onEngineChanged
                        )
                        InsetDivider()
                        if (TextVoiceSettings.isTtsEngine(selectedEngine)) {
                            PopupChoiceRow(
                                title = "TTS 角色",
                                summary = when {
                                    ttsVoiceLoading -> "正在读取所选引擎的角色"
                                    ttsVoiceError.isNotBlank() -> "读取失败：$ttsVoiceError"
                                    ttsFallbackEnginePackage.isNotBlank() ->
                                        "系统默认不可用，已临时使用 $ttsFallbackEngineLabel"
                                    ttsVoiceChoices.isEmpty() -> "所选引擎未提供可选角色，将跟随引擎默认"
                                    else -> "选择所选 TTS 引擎提供的角色"
                                },
                                options = ttsVoiceOptions,
                                currentValue = selectedTtsVoice,
                                enabled = !ttsVoiceLoading,
                                onValueChanged = onTtsVoiceChanged
                            )
                        } else {
                            ActionRow(
                                title = "在线语音",
                                summary = TextVoiceSettings.voiceLabel(selectedVoice),
                                onClick = onPickVoice
                            )
                        }
                        InsetDivider()
                        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 14.dp)) {
                            Text(
                                text = "语速 ${String.format(Locale.ROOT, "%.1f", speechRate)}x",
                                color = MiuixTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Medium
                            )
                            Slider(
                                value = speechRate,
                                onValueChange = {
                                    speechRate = TextVoiceSettings.normalizeSpeechRate(it)
                                },
                                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                                valueRange = TextVoiceSettings.MIN_SPEECH_RATE..TextVoiceSettings.MAX_SPEECH_RATE,
                                steps = 28,
                                onValueChangeFinished = {
                                    sp.edit()
                                        .putFloat(TextVoiceSettings.KEY_SPEECH_RATE, speechRate)
                                        .apply()
                                },
                                showKeyPoints = true,
                                keyPoints = listOf(0.1f, 1.0f, 2.0f, 3.0f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun VoiceForwardMiuixPage(
    context: Context,
    provider: FeatureSettingsProvider,
    onBack: () -> Unit
) {
    val sp = remember { HchatStorage.preferences(context, VoiceForwardSettings.PREFS_NAME) }
    val legacyDefault = remember { sp.getBoolean(VoiceForwardSettings.KEY_ENABLE, VoiceForwardSettings.DEFAULT_ENABLE) }
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
            item { SmallTitle(text = "聊天语音") }
            item {
                SettingsCard {
                    SwitchRow(
                        sp,
                        VoiceForwardSettings.KEY_CHAT_FORWARD_ENABLE,
                        "聊天语音转发",
                        "长按聊天语音后显示转发入口",
                        legacyDefault
                    )
                    InsetDivider()
                    SwitchRow(
                        sp,
                        VoiceForwardSettings.KEY_CHAT_SAVE_ENABLE,
                        "聊天语音保存",
                        "长按聊天语音后显示保存入口",
                        legacyDefault
                    )
                    InsetDivider()
                    SwitchRow(
                        sp,
                        VoiceForwardSettings.KEY_CHAT_MULTI_FORWARD_ENABLE,
                        "多选语音转发",
                        "多选语音后显示逐条转发入口",
                        legacyDefault
                    )
                    InsetDivider()
                    SwitchRow(
                        sp,
                        VoiceForwardSettings.KEY_CHAT_MULTI_MERGE_ENABLE,
                        "多选语音合并",
                        "多选语音后合成为一条语音，可转发或保存为 MP3",
                        false
                    )
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "收藏语音") }
            item {
                SettingsCard {
                    SwitchRow(
                        sp,
                        VoiceForwardSettings.KEY_FAVORITE_FORWARD_ENABLE,
                        "收藏语音转发",
                        "长按收藏语音后显示转发入口",
                        legacyDefault
                    )
                    InsetDivider()
                    SwitchRow(
                        sp,
                        VoiceForwardSettings.KEY_FAVORITE_SAVE_ENABLE,
                        "收藏语音保存",
                        "长按收藏语音后显示保存入口",
                        legacyDefault
                    )
                }
            }
        }
    }
}

@Composable
internal fun FeatureListCard(
    providers: List<FeatureSettingsProvider>,
    emptyText: String,
    onOpenProvider: (FeatureSettingsProvider) -> Unit
) {
    SettingsCard {
        if (providers.isEmpty()) {
            EmptyText(emptyText)
        } else {
            providers.forEachIndexed { index, provider ->
                FeatureRow(
                    provider = provider,
                    onClick = { onOpenProvider(provider) }
                )
                if (index < providers.lastIndex) {
                    InsetDivider()
                }
            }
        }
    }
}

@Composable
internal fun FeatureGroupRow(group: FeatureGroupEntry, onClick: () -> Unit) {
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
            Text(text = group.title, color = MiuixTheme.colorScheme.onSurface, fontWeight = FontWeight.Medium)
            Text(
                text = "${group.providers.size} 项功能",
                color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                fontSize = 12.sp
            )
        }
        Text(text = "›", color = MiuixTheme.colorScheme.onSurfaceVariantSummary, fontSize = 22.sp)
    }
}

@Composable
internal fun FeatureRow(provider: FeatureSettingsProvider, onClick: () -> Unit) {
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
            Text(text = provider.title(), color = MiuixTheme.colorScheme.onSurface, fontWeight = FontWeight.Medium)
            Text(text = provider.subtitle(), color = MiuixTheme.colorScheme.onSurfaceVariantSummary, fontSize = 12.sp)
        }
        Text(text = "›", color = MiuixTheme.colorScheme.onSurfaceVariantSummary, fontSize = 22.sp)
    }
}

@Composable
internal fun ScheduledTaskMiuixPage(
    context: Context,
    provider: FeatureSettingsProvider,
    onBack: () -> Unit
) {
    val settings = remember { ScheduledTaskSettings(context) }
    val sp = remember { HchatStorage.preferences(context, ScheduledTaskSettings.PREFS_NAME) }
    var route by remember { mutableStateOf<ScheduledTaskRoute>(ScheduledTaskRoute.Main) }
    var enabled by remember {
        mutableStateOf(
            sp.getBoolean(
                ScheduledTaskSettings.KEY_ENABLE,
                ScheduledTaskSettings.DEFAULT_ENABLE
            )
        )
    }
    var tasks by remember { mutableStateOf(settings.tasks()) }
    var draft by remember { mutableStateOf(ScheduledTaskSettings.newDraft()) }
    var editingTaskId by remember { mutableStateOf<String?>(null) }
    val mainListState = rememberLazyListState()
    val editorListState = rememberLazyListState()

    fun reloadTasks() {
        tasks = settings.tasks()
    }

    fun persistTasks(next: List<ScheduledTaskItem>) {
        settings.saveTasks(next)
        reloadTasks()
        ScheduledTaskRuntimeCoordinator.reload()
    }

    fun saveDraft(closeAfterSave: Boolean = true): Boolean {
        var target = ScheduledTaskSettings.normalizeForSave(draft)
        val now = System.currentTimeMillis()
        val momentsError = ScheduledTaskSettings.momentsValidationError(target)
        val normalizedItems = ScheduledTaskSettings.normalizedItems(target)
        val channelError = if (
            target.targetType == ScheduledTaskSettings.TARGET_CHAT &&
            target.sendChannel == ScheduledTaskSettings.SEND_CHANNEL_OFFICIAL
        ) {
            if (normalizedItems.all { it.type == ScheduledTaskSettings.TYPE_SELECTED_MESSAGE }) {
                val snapshots = normalizedItems.mapNotNull { SelectedMessageSnapshot.decode(it.value) }
                if (snapshots.size != normalizedItems.size) {
                    "定时转发消息快照无效"
                } else {
                    SelectedMessagesRuntimeCoordinator.snapshotValidationError(
                        target.sendChannel,
                        snapshots
                    )
                }
            } else {
                SelectedMessagesRuntimeCoordinator.validationError(target.sendChannel, normalizedItems)
            }
        } else {
            null
        }
        val planTimes = ScheduledTaskSettings.normalizedPlanTimes(target)
        val error = when {
            target.targetType == ScheduledTaskSettings.TARGET_CHAT && target.targetIds.isEmpty() -> "请选择发送对象"
            momentsError != null -> momentsError
            normalizedItems.isEmpty() -> "请配置发送内容"
            channelError != null -> channelError
            planTimes.isEmpty() -> "请添加计划时间"
            target.repeatType == ScheduledTaskSettings.REPEAT_WEEKLY && target.repeatDays.isEmpty() -> "请选择每周重复日期"
            target.repeatType == ScheduledTaskSettings.REPEAT_NONE && planTimes.any { it <= now } -> "计划时间需要晚于当前时间"
            else -> null
        }
        if (error != null) {
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            return false
        }
        if (target.repeatType != ScheduledTaskSettings.REPEAT_NONE) {
            val nextPlanTimes = planTimes.map { planTime ->
                ScheduledTaskSettings.resolveNextPlanTime(
                    planTime,
                    target.repeatType,
                    target.repeatDays,
                    now
                )
            }.filter { it > 0L }
            if (nextPlanTimes.isEmpty()) {
                Toast.makeText(context, "无法计算下次执行时间", Toast.LENGTH_SHORT).show()
                return false
            }
            target = updateScheduledTaskPlanTimes(target, nextPlanTimes)
        }
        val nextTasks = tasks.filterNot { it.id == target.id } + target
        persistTasks(nextTasks)
        draft = target
        if (closeAfterSave) {
            route = ScheduledTaskRoute.Main
        }
        Toast.makeText(context, "任务已保存", Toast.LENGTH_SHORT).show()
        return true
    }

    SettingsRouteTransition(
        targetState = route,
        label = "ScheduledTaskRoute",
        depthOf = { it.depth() }
    ) { currentRoute ->
        when (currentRoute) {
            ScheduledTaskRoute.Main -> {
                ScheduledTaskMainPage(
                    provider = provider,
                    enabled = enabled,
                    tasks = tasks,
                    listState = mainListState,
                    onBack = onBack,
                    onEnabledChange = { checked ->
                        enabled = checked
                        sp.edit().putBoolean(ScheduledTaskSettings.KEY_ENABLE, checked).apply()
                        ScheduledTaskRuntimeCoordinator.reload()
                    },
                    onAdd = {
                        editingTaskId = null
                        draft = ScheduledTaskSettings.newDraft()
                        route = ScheduledTaskRoute.Editor
                    },
                    onEdit = { task ->
                        editingTaskId = task.id
                        draft = task
                        route = ScheduledTaskRoute.Editor
                    }
                )
            }
            ScheduledTaskRoute.Editor -> {
                ScheduledTaskEditorPage(
                    context = context,
                    task = draft,
                    isEditing = editingTaskId != null,
                    listState = editorListState,
                    onBack = { route = ScheduledTaskRoute.Main },
                    onTaskChange = { draft = it },
                    onPickTargets = { route = ScheduledTaskRoute.ContactPicker },
                    onPickFiles = { itemIndex, itemType ->
                        val activity = context as? Activity
                        if (activity == null) {
                            Toast.makeText(context, "当前页面无法打开文件选择器", Toast.LENGTH_SHORT).show()
                        } else {
                            ScheduledTaskFilePickerBridge.launch(activity, itemType) { paths ->
                                draft = updateScheduledTaskMediaItem(draft, itemIndex, itemType, paths)
                            }
                        }
                    },
                    onPickMomentsMedia = { itemType ->
                        val activity = context as? Activity
                        if (activity == null) {
                            Toast.makeText(context, "当前页面无法打开文件选择器", Toast.LENGTH_SHORT).show()
                        } else {
                            ScheduledTaskFilePickerBridge.launch(activity, itemType) { paths ->
                                if (itemType == ScheduledTaskSettings.TYPE_IMAGE && paths.size > 9) {
                                    Toast.makeText(context, "朋友圈最多保留前 9 张图片", Toast.LENGTH_SHORT).show()
                                } else if (itemType == ScheduledTaskSettings.TYPE_VIDEO && paths.size > 1) {
                                    Toast.makeText(context, "朋友圈只保留第 1 个视频", Toast.LENGTH_SHORT).show()
                                }
                                draft = updateScheduledTaskMomentsMedia(draft, itemType, paths)
                            }
                        }
                    },
                    onPickFavorite = { itemIndex ->
                        scheduledTaskEditableItems(draft).getOrNull(itemIndex)?.let { item ->
                            route = ScheduledTaskRoute.FavoritePicker(
                                itemIndex,
                                FavoritePickerRequest(
                                    title = scheduledTaskPickerTitle(item.type),
                                    existingValue = item.value,
                                    onValue = { value ->
                                        draft = updateScheduledTaskContentItemValue(draft, itemIndex, value)
                                    },
                                    multiSelect = false,
                                    delimiter = "|"
                                )
                            )
                        }
                    },
                    onSave = { saveDraft() },
                    onDelete = if (editingTaskId == null) {
                        null
                    } else {
                        {
                            persistTasks(tasks.filterNot { it.id == draft.id })
                            route = ScheduledTaskRoute.Main
                            Toast.makeText(context, "任务已删除", Toast.LENGTH_SHORT).show()
                        }
                    },
                    onExecuteNow = if (editingTaskId == null) {
                        null
                    } else {
                        {
                            if (saveDraft(closeAfterSave = false)) {
                                if (ScheduledTaskRuntimeCoordinator.executeNow(draft.id)) {
                                    Toast.makeText(context, "任务已加入立即执行队列", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "立即执行失败", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                )
            }
            ScheduledTaskRoute.ContactPicker -> {
                val official = draft.sendChannel == ScheduledTaskSettings.SEND_CHANNEL_OFFICIAL
                ContactPickerPage(
                    context = context,
                    request = ContactPickerRequest(
                        title = if (official) "选择原生群发好友" else "选择发送对象",
                        mode = if (official) ContactPickerMode.FRIENDS else ContactPickerMode.ALL_CHATS,
                        multiSelect = true,
                        existingValue = formatIds(draft.targetIds),
                        onValue = {},
                        enableLabels = true
                    ),
                    onBack = { route = ScheduledTaskRoute.Editor },
                    onConfirm = { selected ->
                        draft = draft.copy(targetIds = selected.map { it.id })
                        route = ScheduledTaskRoute.Editor
                    }
                )
            }
            is ScheduledTaskRoute.FavoritePicker -> {
                FavoritePickerPage(
                    request = currentRoute.request,
                    onBack = { route = ScheduledTaskRoute.Editor }
                )
            }
        }
    }
}

@Composable
internal fun ScheduledTaskMainPage(
    provider: FeatureSettingsProvider,
    enabled: Boolean,
    tasks: List<ScheduledTaskItem>,
    listState: LazyListState,
    onBack: () -> Unit,
    onEnabledChange: (Boolean) -> Unit,
    onAdd: () -> Unit,
    onEdit: (ScheduledTaskItem) -> Unit
) {
    val scrollBehavior = MiuixScrollBehavior()
    PageScaffold(
        title = provider.title(),
        largeTitle = provider.title(),
        scrollBehavior = scrollBehavior,
        bottomBar = {
            BottomActionBar(
                primaryText = "新增任务",
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
            item { SmallTitle(text = "发送计划") }
            item {
                SettingsCard {
                    SwitchRow(
                        checked = enabled,
                        title = "启用定时任务",
                        summary = "开启后按计划时间发送聊天消息或发布朋友圈"
                    ) { onEnabledChange(it) }
                    InsetDivider()
                    InfoRow("当前任务", if (tasks.isEmpty()) "暂无任务" else "${tasks.size} 个")
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "任务列表") }
            when {
                tasks.isEmpty() -> item {
                    SettingsCard {
                        EmptyText("还没有定时任务，点击底部“新增任务”。")
                    }
                }
                else -> tasks.forEach { task ->
                    item {
                        SettingsCard {
                            ActionRow(
                                scheduledTaskTitle(task),
                                scheduledTaskSummary(task)
                            ) {
                                onEdit(task)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun ScheduledTaskEditorPage(
    context: Context,
    task: ScheduledTaskItem,
    isEditing: Boolean,
    listState: LazyListState,
    onBack: () -> Unit,
    onTaskChange: (ScheduledTaskItem) -> Unit,
    onPickTargets: () -> Unit,
    onPickFiles: (Int, Int) -> Unit,
    onPickMomentsMedia: (Int) -> Unit,
    onPickFavorite: (Int) -> Unit,
    onSave: () -> Unit,
    onDelete: (() -> Unit)?,
    onExecuteNow: (() -> Unit)?
) {
    val scrollBehavior = MiuixScrollBehavior()
    PageScaffold(
        title = if (isEditing) "编辑任务" else "新增任务",
        largeTitle = if (isEditing) "编辑任务" else "新增任务",
        scrollBehavior = scrollBehavior,
        bottomBar = {
            BottomActionBar(
                primaryText = "保存任务",
                onPrimaryClick = onSave,
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
            item { SmallTitle(text = "备注") }
            item {
                SettingsCard {
                    InputRow(
                        title = "任务备注",
                        summary = "用于在任务列表区分任务，可留空",
                        value = task.remark,
                        onValueChange = { onTaskChange(task.copy(remark = it)) }
                    )
                }
            }
            item { SmallTitle(text = "任务目标") }
            item {
                SettingsCard {
                    if (scheduledTaskEditableItems(task).any { it.type == ScheduledTaskSettings.TYPE_SELECTED_MESSAGE }) {
                        InfoRow(label = "发送到", value = "聊天")
                    } else {
                        PopupChoiceRow(
                            title = "发送到",
                            summary = scheduledTaskTargetTypeLabel(task.targetType),
                            options = scheduledTaskTargetTypeChoices(),
                            currentValue = task.targetType.toString(),
                            onValueChanged = { value ->
                                val targetType = value.toIntOrNull() ?: ScheduledTaskSettings.TARGET_CHAT
                                onTaskChange(updateScheduledTaskTargetType(task, targetType))
                            }
                        )
                    }
                    if (task.targetType == ScheduledTaskSettings.TARGET_CHAT) {
                        InsetDivider()
                        PopupChoiceRow(
                            title = "发送通道",
                            summary = scheduledTaskSendChannelLabel(task.sendChannel),
                            options = scheduledTaskSendChannelChoices(),
                            currentValue = task.sendChannel.toString(),
                            onValueChanged = { value ->
                                val channel = value.toIntOrNull()
                                    ?.takeIf { ScheduledTaskSettings.sendChannelIsValid(it) }
                                    ?: ScheduledTaskSettings.SEND_CHANNEL_MODULE
                                onTaskChange(
                                    task.copy(
                                        sendChannel = channel,
                                        targetIds = if (channel == task.sendChannel) {
                                            task.targetIds
                                        } else {
                                            emptyList()
                                        }
                                    )
                                )
                            }
                        )
                        InsetDivider()
                        ActionRow(
                            if (task.sendChannel == ScheduledTaskSettings.SEND_CHANNEL_OFFICIAL) {
                                "选择好友"
                            } else {
                                "选择聊天"
                            },
                            scheduledTaskTargetSummary(task.targetIds)
                        ) {
                            onPickTargets()
                        }
                    }
                }
            }
            item {
                SmallTitle(
                    modifier = Modifier.padding(top = 10.dp),
                    text = if (task.targetType == ScheduledTaskSettings.TARGET_MOMENTS) "朋友圈内容" else "发送内容"
                )
            }
            item {
                SettingsCard {
                    if (task.targetType == ScheduledTaskSettings.TARGET_MOMENTS) {
                        PopupChoiceRow(
                            title = "朋友圈类型",
                            summary = scheduledTaskMomentsTypeLabel(task.momentsType),
                            options = scheduledTaskMomentsTypeChoices(),
                            currentValue = task.momentsType.toString(),
                            onValueChanged = { value ->
                                val type = value.toIntOrNull() ?: ScheduledTaskSettings.MOMENTS_TEXT
                                onTaskChange(updateScheduledTaskMomentsType(task, type))
                            }
                        )
                        if (scheduledTaskMomentsUsesText(task.momentsType)) {
                            InsetDivider()
                            VariableInputRow(
                                title = "朋友圈文字",
                                summary = "输入要发布的朋友圈文案",
                                value = scheduledTaskMomentsText(task),
                                variables = emptyList(),
                                minLines = 3
                            ) {
                                onTaskChange(updateScheduledTaskMomentsText(task, it))
                            }
                        }
                        val mediaType = scheduledTaskMomentsMediaType(task.momentsType)
                        if (mediaType != null) {
                            val paths = scheduledTaskMomentsMediaPaths(task, mediaType)
                            InsetDivider()
                            ActionRow(
                                if (mediaType == ScheduledTaskSettings.TYPE_IMAGE) "选择图片" else "选择视频",
                                scheduledTaskMediaSummary(mediaType, paths)
                            ) {
                                onPickMomentsMedia(mediaType)
                            }
                            if (paths.isNotEmpty()) {
                                InsetDivider()
                                ActionRow("清空媒体", "移除已选择的朋友圈媒体") {
                                    onTaskChange(updateScheduledTaskMomentsMedia(task, mediaType, emptyList()))
                                }
                            }
                        }
                    } else {
                        ScheduledTaskChatContentRows(
                            task = task,
                            onTaskChange = onTaskChange,
                            onPickFiles = onPickFiles,
                            onPickFavorite = onPickFavorite,
                            massSendOnly = task.sendChannel == ScheduledTaskSettings.SEND_CHANNEL_OFFICIAL
                        )
                    }
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "执行计划") }
            item {
                SettingsCard {
                    val planTimes = ScheduledTaskSettings.normalizedPlanTimes(task)
                    planTimes.forEachIndexed { index, planTime ->
                        if (index > 0) InsetDivider()
                        ActionRow("计划时间 ${index + 1}", scheduledTaskTimeText(planTime)) {
                            val activity = context as? Activity
                            if (activity == null) {
                                Toast.makeText(context, "当前页面无法打开时间选择器", Toast.LENGTH_SHORT).show()
                            } else {
                                showScheduledTaskTimeActionPicker(
                                    activity = activity,
                                    canDelete = planTimes.size > 1,
                                    onEdit = {
                                        showScheduledTaskDateTimePicker(activity, planTime) { picked ->
                                            val next = planTimes.toMutableList().apply { set(index, picked) }
                                            onTaskChange(updateScheduledTaskPlanTimes(task, next))
                                        }
                                    },
                                    onDelete = {
                                        onTaskChange(
                                            updateScheduledTaskPlanTimes(
                                                task,
                                                planTimes.filterIndexed { currentIndex, _ -> currentIndex != index }
                                            )
                                        )
                                    }
                                )
                            }
                        }
                    }
                    InsetDivider()
                    ActionRow("新增计划时间", "同一任务可在多个时间分别执行") {
                        val activity = context as? Activity
                        if (activity == null) {
                            Toast.makeText(context, "当前页面无法打开时间选择器", Toast.LENGTH_SHORT).show()
                        } else {
                            val initialTime = planTimes.lastOrNull()
                                ?.let { it + 5 * 60 * 1000L }
                                ?: (System.currentTimeMillis() + 5 * 60 * 1000L)
                            showScheduledTaskDateTimePicker(activity, initialTime) { picked ->
                                onTaskChange(updateScheduledTaskPlanTimes(task, planTimes + picked))
                            }
                        }
                    }
                    InsetDivider()
                    PopupChoiceRow(
                        title = "重复方式",
                        summary = scheduledTaskRepeatLabel(task.repeatType),
                        options = scheduledTaskRepeatChoices(),
                        currentValue = task.repeatType.toString(),
                        onValueChanged = { value ->
                            val repeatType = value.toIntOrNull() ?: ScheduledTaskSettings.REPEAT_NONE
                            onTaskChange(
                                task.copy(
                                    repeatType = repeatType,
                                    repeatDays = if (repeatType == ScheduledTaskSettings.REPEAT_WEEKLY) {
                                        task.repeatDays.ifEmpty { setOf(Calendar.MONDAY) }
                                    } else {
                                        emptySet()
                                    }
                                )
                            )
                        }
                    )
                    if (task.repeatType == ScheduledTaskSettings.REPEAT_WEEKLY) {
                        InsetDivider()
                        ScheduledTaskWeekdayRow(task.repeatDays) { day ->
                            val next = if (task.repeatDays.contains(day)) {
                                task.repeatDays - day
                            } else {
                                task.repeatDays + day
                            }
                            onTaskChange(task.copy(repeatDays = next))
                        }
                    }
                    InsetDivider()
                    SwitchRow(
                        checked = task.sendOnTimeout,
                        title = "超时补发",
                        summary = "微信进程错过执行时间后，恢复时自动补发"
                    ) {
                        onTaskChange(task.copy(sendOnTimeout = it))
                    }
                }
            }
            if (
                task.targetType == ScheduledTaskSettings.TARGET_CHAT &&
                task.sendChannel == ScheduledTaskSettings.SEND_CHANNEL_MODULE
            ) {
                item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "发送节奏") }
                item {
                    SettingsCard {
                        NumberInputRow("聊天间隔", "单位秒，多个聊天之间的等待时间", task.intervalSeconds.toString()) {
                            onTaskChange(task.copy(intervalSeconds = it.toIntOrNull()?.coerceIn(0, 3600) ?: 0))
                        }
                        if (scheduledTaskHasOrderedItems(task)) {
                            InsetDivider()
                            NumberInputRow(scheduledTaskItemIntervalTitle(), "单位秒，同一聊天连续发送的等待时间", task.mediaIntervalSeconds.toString()) {
                                onTaskChange(task.copy(mediaIntervalSeconds = it.toIntOrNull()?.coerceIn(0, 3600) ?: 0))
                            }
                        }
                    }
                }
            }
            if (onExecuteNow != null || onDelete != null) {
                item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "操作") }
                item {
                    SettingsCard {
                        if (onExecuteNow != null) {
                            ActionRow("立即执行", "保存当前内容后立刻发送一次") {
                                onExecuteNow()
                            }
                        }
                        if (onExecuteNow != null && onDelete != null) {
                            InsetDivider()
                        }
                        if (onDelete != null) {
                            ActionRow("删除任务", "移除当前定时任务") {
                                onDelete()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun ScheduledTaskWeekdayRow(
    selectedDays: Set<Int>,
    onToggle: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        scheduledTaskWeekdayOptions().forEach { (day, label) ->
            ContactFilterChip(
                text = label,
                selected = selectedDays.contains(day),
                onClick = { onToggle(day) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}
