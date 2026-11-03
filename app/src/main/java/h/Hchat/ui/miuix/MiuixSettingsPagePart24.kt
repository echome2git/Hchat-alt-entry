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
internal fun ScheduledTaskChatContentRows(
    task: ScheduledTaskItem,
    onTaskChange: (ScheduledTaskItem) -> Unit,
    onPickFiles: (Int, Int) -> Unit,
    onPickFavorite: (Int) -> Unit,
    massSendOnly: Boolean = false
) {
    val items = scheduledTaskEditableItems(task)
    items.forEachIndexed { index, item ->
        if (index > 0) InsetDivider()
        if (item.type == ScheduledTaskSettings.TYPE_SELECTED_MESSAGE) {
            InfoRow(
                label = if (items.size == 1) "发送类型" else "发送类型 ${index + 1}",
                value = scheduledTaskTypeLabel(item.type)
            )
            InsetDivider()
            InfoRow(
                label = "消息",
                value = SelectedMessageSnapshot.decode(item.value)?.label() ?: "不可用"
            )
        } else {
            PopupChoiceRow(
                title = if (items.size == 1) "发送类型" else "发送类型 ${index + 1}",
                summary = if (massSendOnly) {
                    selectedMessagesContentTypeLabel(item.type)
                } else {
                    scheduledTaskTypeLabel(item.type)
                },
                options = if (massSendOnly) selectedMessagesContentTypeChoices() else scheduledTaskTypeChoices(),
                currentValue = item.type.toString(),
                onValueChanged = { value ->
                    val type = value.toIntOrNull() ?: if (massSendOnly) {
                        ScheduledTaskSettings.TYPE_TEXT
                    } else {
                        ScheduledTaskSettings.TYPE_TEXT
                    }
                    onTaskChange(updateScheduledTaskContentItemType(task, index, type))
                }
            )
            InsetDivider()
        }
        if (item.type != ScheduledTaskSettings.TYPE_SELECTED_MESSAGE && scheduledTaskUsesText(item.type)) {
            VariableInputRow(
                title = if (massSendOnly && item.type == ScheduledTaskSettings.TYPE_XML) {
                    "视频号内容"
                } else {
                    scheduledTaskContentTitle(item.type)
                },
                summary = if (item.type == ScheduledTaskSettings.TYPE_XML) {
                    if (massSendOnly) "输入视频号分享消息 XML" else "输入 <msg><appmsg>...</appmsg></msg>"
                } else {
                    "输入要发送的内容"
                },
                value = item.value,
                variables = if (massSendOnly) emptyList() else scheduledTaskTemplateVariables,
                minLines = if (item.type == ScheduledTaskSettings.TYPE_XML) 4 else 2
            ) {
                onTaskChange(updateScheduledTaskContentItemValue(task, index, it))
            }
        } else if (item.type != ScheduledTaskSettings.TYPE_SELECTED_MESSAGE &&
            item.type == ScheduledTaskSettings.TYPE_FAVORITE
        ) {
            ActionRow(scheduledTaskPickerTitle(item.type), favoriteSummary(item.value)) {
                onPickFavorite(index)
            }
            if (item.value.isNotBlank()) {
                InsetDivider()
                ActionRow("清空收藏", "移除当前收藏") {
                    onTaskChange(updateScheduledTaskContentItemValue(task, index, ""))
                }
            }
        } else if (item.type != ScheduledTaskSettings.TYPE_SELECTED_MESSAGE) {
            ActionRow("选择文件", scheduledTaskMediaSummary(item.type, listOf(item.value))) {
                onPickFiles(index, item.type)
            }
            if (item.value.isNotBlank()) {
                InsetDivider()
                ActionRow("清空文件", "移除当前文件") {
                    onTaskChange(updateScheduledTaskContentItemValue(task, index, ""))
                }
            }
        }
        if (items.size > 1) {
            InsetDivider()
            ActionRow("删除本条", "移除第 ${index + 1} 条内容") {
                onTaskChange(removeScheduledTaskContentItem(task, index))
            }
        }
    }
    if (items.none { it.type == ScheduledTaskSettings.TYPE_SELECTED_MESSAGE }) {
        InsetDivider()
        ActionRow("新增内容", "添加一条按顺序发送的内容") {
            onTaskChange(
                appendScheduledTaskContentItem(
                    task,
                    ScheduledTaskSettings.TYPE_TEXT
                )
            )
        }
    }
}

internal fun scheduledTaskUsesText(type: Int): Boolean {
    return ScheduledTaskSettings.scheduledTypeUsesText(type)
}

internal fun scheduledTaskEditableItems(task: ScheduledTaskItem): List<ScheduledTaskContentItem> {
    return task.items.ifEmpty {
        if (scheduledTaskUsesText(task.type)) {
            val contents = task.contentItems.ifEmpty { listOf(task.content) }
            contents.map { ScheduledTaskContentItem(task.type, it) }
        } else {
            task.mediaPaths.map { ScheduledTaskContentItem(task.type, it) }
        }
    }.ifEmpty {
        listOf(ScheduledTaskContentItem(ScheduledTaskSettings.TYPE_TEXT, ""))
    }.map {
        val type = it.type.takeIf { value -> ScheduledTaskSettings.scheduledTypeIsValid(value) }
            ?: ScheduledTaskSettings.TYPE_TEXT
        ScheduledTaskContentItem(type, it.value)
    }
}

internal fun updateScheduledTaskContentItemType(
    task: ScheduledTaskItem,
    index: Int,
    type: Int
): ScheduledTaskItem {
    val items = scheduledTaskEditableItems(task).toMutableList()
    if (index !in items.indices) return task
    val previous = items[index]
    val keepValue = previous.type == type ||
        (
            scheduledTaskUsesText(previous.type) == scheduledTaskUsesText(type) &&
                previous.type != ScheduledTaskSettings.TYPE_FAVORITE &&
                type != ScheduledTaskSettings.TYPE_FAVORITE
            )
    items[index] = ScheduledTaskContentItem(type, if (keepValue) previous.value else "")
    return task.copy(items = items)
}

internal fun updateScheduledTaskContentItemValue(
    task: ScheduledTaskItem,
    index: Int,
    value: String
): ScheduledTaskItem {
    val items = scheduledTaskEditableItems(task).toMutableList()
    if (index !in items.indices) return task
    items[index] = items[index].copy(value = value)
    return task.copy(items = items)
}

internal fun updateScheduledTaskMediaItem(
    task: ScheduledTaskItem,
    index: Int,
    type: Int,
    paths: List<String>
): ScheduledTaskItem {
    val nextPaths = paths.map { it.trim() }.filter { it.isNotBlank() }.distinct()
    if (nextPaths.isEmpty()) return task
    val items = scheduledTaskEditableItems(task).toMutableList()
    if (index !in items.indices) return task
    items[index] = ScheduledTaskContentItem(type, nextPaths.first())
    nextPaths.drop(1).forEachIndexed { offset, path ->
        items.add(index + 1 + offset, ScheduledTaskContentItem(type, path))
    }
    return task.copy(items = items)
}

internal fun appendScheduledTaskContentItem(
    task: ScheduledTaskItem,
    defaultType: Int = ScheduledTaskSettings.TYPE_TEXT
): ScheduledTaskItem {
    val items = scheduledTaskEditableItems(task) + ScheduledTaskContentItem(defaultType, "")
    return task.copy(items = items)
}

internal fun removeScheduledTaskContentItem(task: ScheduledTaskItem, index: Int): ScheduledTaskItem {
    val items = scheduledTaskEditableItems(task).toMutableList()
    if (items.size <= 1 || index !in items.indices) return task
    items.removeAt(index)
    return task.copy(items = items)
}

internal fun scheduledTaskContentTitle(type: Int): String {
    return if (type == ScheduledTaskSettings.TYPE_XML) "XML 内容" else "发送内容"
}

internal fun scheduledTaskHasOrderedItems(task: ScheduledTaskItem): Boolean {
    return scheduledTaskEditableItems(task).size > 1
}

internal fun scheduledTaskItemIntervalTitle(): String {
    return "多条间隔"
}

internal fun scheduledTaskTargetTypeChoices(): List<PopupChoice<String>> = listOf(
    PopupChoice("聊天", ScheduledTaskSettings.TARGET_CHAT.toString()),
    PopupChoice("朋友圈", ScheduledTaskSettings.TARGET_MOMENTS.toString())
)

internal fun scheduledTaskSendChannelChoices(): List<PopupChoice<String>> = listOf(
    PopupChoice("模块通道", ScheduledTaskSettings.SEND_CHANNEL_MODULE.toString()),
    PopupChoice("微信原生群发助手", ScheduledTaskSettings.SEND_CHANNEL_OFFICIAL.toString())
)

internal fun scheduledTaskSendChannelLabel(channel: Int): String {
    return if (channel == ScheduledTaskSettings.SEND_CHANNEL_OFFICIAL) {
        "微信原生群发助手"
    } else {
        "模块通道"
    }
}

internal fun scheduledTaskTargetTypeLabel(type: Int): String = when (type) {
    ScheduledTaskSettings.TARGET_MOMENTS -> "朋友圈"
    else -> "聊天"
}

internal fun scheduledTaskMomentsTypeChoices(): List<PopupChoice<String>> = listOf(
    PopupChoice("文字", ScheduledTaskSettings.MOMENTS_TEXT.toString()),
    PopupChoice("图文", ScheduledTaskSettings.MOMENTS_TEXT_IMAGE.toString()),
    PopupChoice("视文", ScheduledTaskSettings.MOMENTS_TEXT_VIDEO.toString()),
    PopupChoice("图片", ScheduledTaskSettings.MOMENTS_IMAGE.toString()),
    PopupChoice("视频", ScheduledTaskSettings.MOMENTS_VIDEO.toString())
)

internal fun scheduledTaskMomentsTypeLabel(type: Int): String = when (type) {
    ScheduledTaskSettings.MOMENTS_TEXT_IMAGE -> "图文"
    ScheduledTaskSettings.MOMENTS_TEXT_VIDEO -> "视文"
    ScheduledTaskSettings.MOMENTS_IMAGE -> "图片"
    ScheduledTaskSettings.MOMENTS_VIDEO -> "视频"
    else -> "文字"
}

internal fun scheduledTaskMomentsUsesText(type: Int): Boolean {
    return type == ScheduledTaskSettings.MOMENTS_TEXT ||
        type == ScheduledTaskSettings.MOMENTS_TEXT_IMAGE ||
        type == ScheduledTaskSettings.MOMENTS_TEXT_VIDEO
}

internal fun scheduledTaskMomentsMediaType(type: Int): Int? = when (type) {
    ScheduledTaskSettings.MOMENTS_TEXT_IMAGE,
    ScheduledTaskSettings.MOMENTS_IMAGE -> ScheduledTaskSettings.TYPE_IMAGE
    ScheduledTaskSettings.MOMENTS_TEXT_VIDEO,
    ScheduledTaskSettings.MOMENTS_VIDEO -> ScheduledTaskSettings.TYPE_VIDEO
    else -> null
}

internal fun scheduledTaskMomentsText(task: ScheduledTaskItem): String {
    return task.items.firstOrNull { it.type == ScheduledTaskSettings.TYPE_TEXT }?.value
        ?: task.content.takeIf { task.type == ScheduledTaskSettings.TYPE_TEXT }.orEmpty()
}

internal fun scheduledTaskMomentsMediaPaths(task: ScheduledTaskItem, mediaType: Int): List<String> {
    val itemPaths = task.items.asSequence()
        .filter { it.type == mediaType }
        .map { it.value.trim() }
        .filter { it.isNotBlank() }
        .distinct()
        .toList()
    if (itemPaths.isNotEmpty()) return itemPaths
    return if (task.type == mediaType) task.mediaPaths else emptyList()
}

internal fun updateScheduledTaskTargetType(task: ScheduledTaskItem, targetType: Int): ScheduledTaskItem {
    val safeTarget = targetType.takeIf { ScheduledTaskSettings.targetTypeIsValid(it) }
        ?: ScheduledTaskSettings.TARGET_CHAT
    if (safeTarget == task.targetType) return task
    if (safeTarget == ScheduledTaskSettings.TARGET_CHAT) {
        return task.copy(targetType = safeTarget)
    }
    val hasText = scheduledTaskMomentsText(task).isNotBlank()
    val hasImages = scheduledTaskMomentsMediaPaths(task, ScheduledTaskSettings.TYPE_IMAGE).isNotEmpty()
    val hasVideo = scheduledTaskMomentsMediaPaths(task, ScheduledTaskSettings.TYPE_VIDEO).isNotEmpty()
    val momentsType = when {
        hasText && hasImages -> ScheduledTaskSettings.MOMENTS_TEXT_IMAGE
        hasText && hasVideo -> ScheduledTaskSettings.MOMENTS_TEXT_VIDEO
        hasImages -> ScheduledTaskSettings.MOMENTS_IMAGE
        hasVideo -> ScheduledTaskSettings.MOMENTS_VIDEO
        else -> ScheduledTaskSettings.MOMENTS_TEXT
    }
    return task.copy(
        targetType = safeTarget,
        momentsType = momentsType,
        sendChannel = ScheduledTaskSettings.SEND_CHANNEL_MODULE,
        targetIds = emptyList()
    )
}

internal fun updateScheduledTaskMomentsType(task: ScheduledTaskItem, momentsType: Int): ScheduledTaskItem {
    val safeType = momentsType.takeIf { ScheduledTaskSettings.momentsTypeIsValid(it) }
        ?: ScheduledTaskSettings.MOMENTS_TEXT
    return task.copy(momentsType = safeType)
}

internal fun updateScheduledTaskMomentsText(task: ScheduledTaskItem, value: String): ScheduledTaskItem {
    val items = task.items.filterNot { it.type == ScheduledTaskSettings.TYPE_TEXT }.toMutableList()
    items.add(0, ScheduledTaskContentItem(ScheduledTaskSettings.TYPE_TEXT, value))
    return task.copy(items = items)
}

internal fun updateScheduledTaskMomentsMedia(
    task: ScheduledTaskItem,
    mediaType: Int,
    paths: List<String>
): ScheduledTaskItem {
    val nextPaths = paths.asSequence()
        .map { it.trim() }
        .filter { it.isNotBlank() }
        .distinct()
        .let { values ->
            if (mediaType == ScheduledTaskSettings.TYPE_IMAGE) values.take(9) else values.take(1)
        }
        .toList()
    val items = task.items.filterNot { it.type == mediaType }.toMutableList()
    items += nextPaths.map { ScheduledTaskContentItem(mediaType, it) }
    return task.copy(items = items)
}

internal fun scheduledTaskTypeChoices(): List<PopupChoice<String>> = listOf(
    PopupChoice("文本", ScheduledTaskSettings.TYPE_TEXT.toString()),
    PopupChoice("图片", ScheduledTaskSettings.TYPE_IMAGE.toString()),
    PopupChoice("视频", ScheduledTaskSettings.TYPE_VIDEO.toString()),
    PopupChoice("文件", ScheduledTaskSettings.TYPE_FILE.toString()),
    PopupChoice("表情", ScheduledTaskSettings.TYPE_EMOJI.toString()),
    PopupChoice("语音", ScheduledTaskSettings.TYPE_VOICE.toString()),
    PopupChoice("XML", ScheduledTaskSettings.TYPE_XML.toString()),
    PopupChoice("收藏", ScheduledTaskSettings.TYPE_FAVORITE.toString())
)

internal fun selectedMessagesContentTypeChoices(): List<PopupChoice<String>> = listOf(
    PopupChoice("文字", ScheduledTaskSettings.TYPE_TEXT.toString()),
    PopupChoice("图片", ScheduledTaskSettings.TYPE_IMAGE.toString()),
    PopupChoice("视频", ScheduledTaskSettings.TYPE_VIDEO.toString()),
    PopupChoice("语音", ScheduledTaskSettings.TYPE_VOICE.toString()),
    PopupChoice("表情", ScheduledTaskSettings.TYPE_EMOJI.toString()),
    PopupChoice("视频号", ScheduledTaskSettings.TYPE_XML.toString())
)

internal fun selectedMessagesContentTypeLabel(type: Int): String = when (type) {
    ScheduledTaskSettings.TYPE_TEXT -> "文字"
    ScheduledTaskSettings.TYPE_IMAGE -> "图片"
    ScheduledTaskSettings.TYPE_VIDEO -> "视频"
    ScheduledTaskSettings.TYPE_VOICE -> "语音"
    ScheduledTaskSettings.TYPE_EMOJI -> "表情"
    ScheduledTaskSettings.TYPE_XML -> "视频号"
    else -> scheduledTaskTypeLabel(type)
}

internal fun scheduledTaskRepeatChoices(): List<PopupChoice<String>> = listOf(
    PopupChoice("单次", ScheduledTaskSettings.REPEAT_NONE.toString()),
    PopupChoice("每天", ScheduledTaskSettings.REPEAT_DAILY.toString()),
    PopupChoice("每周", ScheduledTaskSettings.REPEAT_WEEKLY.toString())
)

internal fun scheduledTaskTypeLabel(type: Int): String = when (type) {
    ScheduledTaskSettings.TYPE_TEXT -> "文本"
    ScheduledTaskSettings.TYPE_IMAGE -> "图片"
    ScheduledTaskSettings.TYPE_VIDEO -> "视频"
    ScheduledTaskSettings.TYPE_FILE -> "文件"
    ScheduledTaskSettings.TYPE_EMOJI -> "表情"
    ScheduledTaskSettings.TYPE_VOICE -> "语音"
    ScheduledTaskSettings.TYPE_XML -> "XML"
    ScheduledTaskSettings.TYPE_FAVORITE -> "收藏"
    ScheduledTaskSettings.TYPE_SELECTED_MESSAGE -> "聊天记录"
    else -> "未知"
}

internal fun scheduledTaskRepeatLabel(repeatType: Int): String = when (repeatType) {
    ScheduledTaskSettings.REPEAT_DAILY -> "每天"
    ScheduledTaskSettings.REPEAT_WEEKLY -> "每周"
    else -> "单次"
}

internal fun scheduledTaskTargetSummary(targetIds: List<String>): String {
    return if (targetIds.isEmpty()) "未选择" else "已选 ${targetIds.distinct().size} 个聊天"
}

internal fun scheduledTaskTargetSummary(task: ScheduledTaskItem): String {
    return if (task.targetType == ScheduledTaskSettings.TARGET_MOMENTS) {
        "朋友圈"
    } else {
        scheduledTaskTargetSummary(task.targetIds)
    }
}

internal fun scheduledTaskMediaSummary(type: Int, paths: List<String>): String {
    if (type == ScheduledTaskSettings.TYPE_FAVORITE) {
        return favoriteSummary(paths.joinToString("|"))
    }
    val values = paths.map { it.trim() }.filter { it.isNotBlank() }
    if (values.isEmpty()) return "未选择"
    val first = File(values.first()).name.ifBlank { "文件" }
    return if (values.size == 1) first else "$first 等 ${values.size} 个文件"
}

internal fun scheduledTaskTitle(task: ScheduledTaskItem): String {
    task.remark.trim().takeIf { it.isNotBlank() }?.let { return it }
    if (task.targetType == ScheduledTaskSettings.TARGET_MOMENTS) {
        return "朋友圈 · ${scheduledTaskMomentsTypeLabel(task.momentsType)}"
    }
    val countText = scheduledTaskItemCountText(task)
    return "${scheduledTaskTypeSummary(task)}$countText · ${scheduledTaskTargetSummary(task)}"
}

internal fun scheduledTaskItemCountText(task: ScheduledTaskItem): String {
    if (task.targetType == ScheduledTaskSettings.TARGET_MOMENTS) return ""
    val count = ScheduledTaskSettings.normalizedItems(task).size
    if (count <= 1) return ""
    return "(${count}条)"
}

internal fun scheduledTaskSummary(task: ScheduledTaskItem): String {
    val repeat = when (task.repeatType) {
        ScheduledTaskSettings.REPEAT_DAILY -> "每天"
        ScheduledTaskSettings.REPEAT_WEEKLY -> scheduledTaskRepeatDaysText(task.repeatDays)
        else -> "单次"
    }
    val result = if (task.lastExecutedTime > 0L) {
        " · 上次 ${task.lastSuccessCount}/${task.lastFailCount}"
    } else {
        ""
    }
    val channel = if (task.targetType == ScheduledTaskSettings.TARGET_CHAT) {
        scheduledTaskSendChannelLabel(task.sendChannel) + " / "
    } else {
        ""
    }
    val planTimes = ScheduledTaskSettings.normalizedPlanTimes(task)
    val timeSummary = if (planTimes.size <= 1) {
        scheduledTaskTimeText(task.planTime)
    } else {
        "${scheduledTaskTimeText(task.planTime)} 等 ${planTimes.size} 个时间"
    }
    val schedule = "$channel$timeSummary / $repeat$result"
    return if (task.remark.isBlank()) {
        schedule
    } else {
        val countText = scheduledTaskItemCountText(task)
        "${scheduledTaskTypeSummary(task)}$countText · ${scheduledTaskTargetSummary(task)} / $schedule"
    }
}

internal fun scheduledTaskTypeSummary(task: ScheduledTaskItem): String {
    if (task.targetType == ScheduledTaskSettings.TARGET_MOMENTS) {
        return scheduledTaskMomentsTypeLabel(task.momentsType)
    }
    val items = ScheduledTaskSettings.normalizedItems(task)
    if (items.isEmpty()) return scheduledTaskTypeLabel(task.type)
    val types = items.map { it.type }.distinct()
    return if (types.size == 1) scheduledTaskTypeLabel(types.first()) else "混合"
}

internal fun scheduledTaskWeekdayOptions(): List<Pair<Int, String>> = listOf(
    Calendar.MONDAY to "一",
    Calendar.TUESDAY to "二",
    Calendar.WEDNESDAY to "三",
    Calendar.THURSDAY to "四",
    Calendar.FRIDAY to "五",
    Calendar.SATURDAY to "六",
    Calendar.SUNDAY to "日"
)

internal fun scheduledTaskRepeatDaysText(days: Set<Int>): String {
    if (days.isEmpty()) return "每周"
    val labels = scheduledTaskWeekdayOptions()
        .filter { days.contains(it.first) }
        .joinToString("") { "周${it.second}" }
    return labels.ifBlank { "每周" }
}

internal fun scheduledTaskTimeText(timeMillis: Long): String {
    if (timeMillis <= 0L) return "未设置"
    return runCatching {
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(Date(timeMillis))
    }.getOrDefault("未设置")
}

internal fun showScheduledTaskDateTimePicker(
    activity: Activity,
    currentTimeMillis: Long,
    onPicked: (Long) -> Unit
) {
    val base = Calendar.getInstance().apply {
        timeInMillis = if (currentTimeMillis > 0L) currentTimeMillis else System.currentTimeMillis()
    }
    DatePickerDialog(
        activity,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            TimePickerDialog(
                activity,
                { _: TimePicker, hourOfDay: Int, minute: Int ->
                    showScheduledTaskSecondPicker(activity, base.get(Calendar.SECOND)) { second ->
                        val next = Calendar.getInstance().apply {
                            set(Calendar.YEAR, year)
                            set(Calendar.MONTH, month)
                            set(Calendar.DAY_OF_MONTH, dayOfMonth)
                            set(Calendar.HOUR_OF_DAY, hourOfDay)
                            set(Calendar.MINUTE, minute)
                            set(Calendar.SECOND, second)
                            set(Calendar.MILLISECOND, 0)
                        }
                        onPicked(next.timeInMillis)
                    }
                },
                base.get(Calendar.HOUR_OF_DAY),
                base.get(Calendar.MINUTE),
                true
            ).show()
        },
        base.get(Calendar.YEAR),
        base.get(Calendar.MONTH),
        base.get(Calendar.DAY_OF_MONTH)
    ).show()
}

internal fun showScheduledTaskTimeActionPicker(
    activity: Activity,
    canDelete: Boolean,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val choices = buildList {
        add("修改时间" to "重新选择该计划时间")
        if (canDelete) add("删除时间" to "从当前任务移除该计划时间")
    }
    VoiceForwardMiuixDialog.showChoices(
        activity = activity,
        title = "计划时间",
        summary = "",
        choices = choices,
        onSelected = { index ->
            if (index == 0) onEdit() else onDelete()
        },
        onDismiss = {}
    )
}

internal fun updateScheduledTaskPlanTimes(
    task: ScheduledTaskItem,
    values: List<Long>
): ScheduledTaskItem {
    val planTimes = values.filter { it > 0L }.distinct().sorted()
    return task.copy(
        planTime = planTimes.firstOrNull() ?: 0L,
        planTimes = planTimes
    )
}

internal fun showScheduledTaskSecondPicker(
    activity: Activity,
    initialSecond: Int,
    onPicked: (Int) -> Unit
) {
    val showDialog: () -> Unit = {
        VoiceForwardMiuixDialog.showNumberInput(
            activity = activity,
            title = "设置秒数",
            initialValue = initialSecond,
            minValue = 0,
            maxValue = 59,
            onConfirm = onPicked,
            onDismiss = {}
        )
    }
    activity.window?.decorView?.postOnAnimation {
        if (!activity.isFinishing && !activity.isDestroyed) showDialog()
    } ?: showDialog()
}

@Composable
internal fun FakeScanCameraMiuixPage(
    context: Context,
    provider: FeatureSettingsProvider,
    onBack: () -> Unit
) {
    val sp = remember { HchatStorage.preferences(context, FakeScanCameraSettings.PREFS_NAME) }
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
            item { SmallTitle(text = "扫码") }
            item {
                SettingsCard {
                    SwitchRow(
                        sp,
                        FakeScanCameraSettings.KEY_ENABLE,
                        "模拟相机扫码",
                        "让相册识别二维码按相机扫码来源处理",
                        FakeScanCameraSettings.DEFAULT_ENABLE
                    )
                }
            }
        }
    }
}
