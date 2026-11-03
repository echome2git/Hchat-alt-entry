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
internal fun ScriptPluginMiuixPage(
    context: Context,
    provider: FeatureSettingsProvider,
    onBack: () -> Unit,
    onOpenMarket: () -> Unit = {},
    onOpenAgent: () -> Unit = {},
    onOpenManager: () -> Unit = {}
) {
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
            item { SmallTitle(text = "脚本插件") }
            item {
                ScriptPluginSettingsMiuixContent.ScriptPluginSettingsContent(
                    context,
                    {},
                    onOpenMarket,
                    onOpenAgent,
                    onOpenManager
                )
            }
        }
    }
}

internal fun formatPluginMarketTime(value: String): String {
    val source = value.trim()
    if (source.isEmpty()) return "时间未知"
    val instant = runCatching { Instant.parse(source) }
        .recoverCatching { OffsetDateTime.parse(source, DateTimeFormatter.ISO_DATE_TIME).toInstant() }
        .recoverCatching {
            LocalDateTime.parse(source, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                .toInstant(ZoneOffset.UTC)
        }
        .getOrNull()
        ?: return source
    return pluginMarketBeijingTimeFormatter.format(instant)
}

@Composable
internal fun ClickHintTag() {
    Text(
        text = "单击",
        color = MiuixTheme.colorScheme.primary,
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
        maxLines = 1,
        softWrap = false,
        overflow = TextOverflow.Clip,
        modifier = Modifier
            .defaultMinSize(minWidth = 40.dp)
            .clip(RoundedCornerShape(999.dp))
            .background(MiuixTheme.colorScheme.primary.copy(alpha = 0.12f))
            .padding(horizontal = 8.dp, vertical = 2.dp)
    )
}

@Composable
internal fun WeChatTabletMiuixPage(
    context: Context,
    provider: FeatureSettingsProvider,
    onBack: () -> Unit
) {
    val sp = remember { HchatStorage.preferences(context, WeChatTabletSettings.PREFS_NAME) }
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
            item { SmallTitle(text = "平板模式") }
            item {
                SettingsCard {
                    SwitchRow(
                        sp,
                        WeChatTabletSettings.KEY_ENABLE,
                        "平板模式",
                        "开启平板模式，退出微信登陆生效",
                        WeChatTabletSettings.DEFAULT_ENABLE
                    )
                }
            }
        }
    }
}

@Composable
internal fun AutoTransferMiuixPage(
    context: Context,
    provider: FeatureSettingsProvider,
    onBack: () -> Unit
) {
    val sp = remember { HchatStorage.preferences(context, AutoTransferSettings.PREFS_NAME) }
    var templates by remember {
        mutableStateOf(TransferRuleConfig.parseTemplates(sp.getString(TransferRuleConfig.KEY_TEMPLATES, "")))
    }
    var bindings by remember {
        mutableStateOf(TransferRuleConfig.parseBindings(sp.getString(TransferRuleConfig.KEY_BINDINGS, "")))
    }
    var defaultTemplateId by remember {
        mutableStateOf(sp.getString(TransferRuleConfig.KEY_DEFAULT_TEMPLATE_ID, "") ?: "")
    }
    var whitelist by remember { mutableStateOf(sp.getString(AutoTransferSettings.KEY_WHITELIST, "") ?: "") }
    var blacklist by remember { mutableStateOf(sp.getString(AutoTransferSettings.KEY_BLACKLIST, "") ?: "") }
    var listMode by remember { mutableStateOf(sp.getInt(AutoTransferSettings.KEY_MODE, 0)) }
    var delayMs by remember { mutableStateOf(sp.getLong(AutoTransferSettings.KEY_DELAY_MS, 0L).toString()) }
    var delayMode by remember { mutableStateOf(sp.getInt(AutoTransferSettings.KEY_DELAY_MODE, TransferRuleConfig.DELAY_CUSTOM)) }
    var randomMinMs by remember { mutableStateOf(sp.getLong(AutoTransferSettings.KEY_DELAY_RANDOM_MIN, 500L).toString()) }
    var randomMaxMs by remember { mutableStateOf(sp.getLong(AutoTransferSettings.KEY_DELAY_RANDOM_MAX, 3000L).toString()) }
    var amountEnabled by remember { mutableStateOf(sp.getBoolean(AutoTransferSettings.KEY_AMOUNT_ENABLE, false)) }
    var amountCondition by remember { mutableStateOf(sp.getInt(AutoTransferSettings.KEY_AMOUNT_COND, 1)) }
    var amountAction by remember { mutableStateOf(sp.getInt(AutoTransferSettings.KEY_AMOUNT_ACTION, 0)) }
    var amountValue by remember { mutableStateOf(sp.getString(AutoTransferSettings.KEY_AMOUNT_VALUE, "0") ?: "0") }
    var keywords by remember { mutableStateOf(sp.getString(AutoTransferSettings.KEY_KEYWORDS, "") ?: "") }
    var keywordMode by remember { mutableStateOf(sp.getInt(AutoTransferSettings.KEY_KEYWORD_MODE, 0)) }
    var quietEnabled by remember { mutableStateOf(sp.getBoolean(AutoTransferSettings.KEY_QUIET_ENABLE, false)) }
    var quietStart by remember { mutableStateOf(formatRedPacketSecond(sp.getInt(AutoTransferSettings.KEY_QUIET_START_SECOND, 0))) }
    var quietEnd by remember { mutableStateOf(formatRedPacketSecond(sp.getInt(AutoTransferSettings.KEY_QUIET_END_SECOND, 0))) }
    var replySteps by remember { mutableStateOf(loadGlobalTransferReplySteps(sp)) }
    var groupReplySteps by remember { mutableStateOf(loadGlobalGroupTransferReplySteps(sp)) }
    var notifySystemEnabled by remember { mutableStateOf(sp.getBoolean(AutoTransferSettings.KEY_NOTIFY_SYSTEM_ENABLE, false)) }
    var notifyToastEnabled by remember { mutableStateOf(sp.getBoolean(AutoTransferSettings.KEY_NOTIFY_TOAST_ENABLE, false)) }
    var notifySoundEnabled by remember { mutableStateOf(sp.getBoolean(AutoTransferSettings.KEY_NOTIFY_SOUND_ENABLE, false)) }
    var notifySoundMode by remember { mutableStateOf(sp.getInt(AutoTransferSettings.KEY_NOTIFY_SOUND_MODE, AutoTransferSettings.NOTIFY_SOUND_MODE_SYSTEM)) }
    var notifyVibrateEnabled by remember { mutableStateOf(sp.getBoolean(AutoTransferSettings.KEY_NOTIFY_VIBRATE_ENABLE, false)) }
    var notifySoundUri by remember { mutableStateOf(sp.getString(AutoTransferSettings.KEY_NOTIFY_SOUND_URI, "") ?: "") }
    var notifyText by remember { mutableStateOf(sp.getString(AutoTransferSettings.KEY_NOTIFY_TEXT, "已收款 {amount} 元") ?: "") }
    var notifyToastText by remember { mutableStateOf(sp.getString(AutoTransferSettings.KEY_NOTIFY_TOAST_TEXT, "已收款 {amount} 元") ?: "") }
    var announceEnabled by remember { mutableStateOf(sp.getBoolean(AutoTransferSettings.KEY_ANNOUNCE_ENABLE, false)) }
    var announceText by remember { mutableStateOf(sp.getString(AutoTransferSettings.KEY_ANNOUNCE_TEXT, "收到转账 {amount} 元") ?: "") }
    var timeFormat by remember {
        mutableStateOf(
            PaymentTemplateTimeFormatter.normalizePattern(
                sp.getString(AutoTransferSettings.KEY_TIME_FORMAT, AutoTransferSettings.DEFAULT_TIME_FORMAT)
            )
        )
    }
    var receiveAccount by remember {
        mutableStateOf(
            sp.getString(
                AutoTransferSettings.KEY_RECEIVE_ACCOUNT,
                TransferReceiveAccountStore.DEFAULT_KEY
            ) ?: TransferReceiveAccountStore.DEFAULT_KEY
        )
    }
    val receiveAccountOptions = remember { transferReceiveAccountOptions(context) }
    if (receiveAccountOptions.none { it.value == receiveAccount }) {
        receiveAccount = TransferReceiveAccountStore.DEFAULT_KEY
    }
    var picker by remember { mutableStateOf<ContactPickerRequest?>(null) }
    var templateEditor by remember { mutableStateOf<TransferTemplateEditorRequest?>(null) }
    var bindingEditor by remember { mutableStateOf<TransferBindingEditorRequest?>(null) }
    var showTemplates by remember { mutableStateOf(false) }
    var showBindings by remember { mutableStateOf(false) }
    var showBatchApply by remember { mutableStateOf(false) }
    var showReplySteps by remember { mutableStateOf(false) }
    var replyTarget by remember { mutableStateOf(TransferReplyTarget.PRIVATE) }
    val listState = rememberLazyListState()
    val scrollBehavior = MiuixScrollBehavior()

    fun persistRules(
        nextTemplates: List<TransferRuleTemplate> = templates,
        nextBindings: List<TransferRuleBinding> = bindings,
        nextDefault: String = defaultTemplateId
    ) {
        sp.edit()
            .putString(TransferRuleConfig.KEY_TEMPLATES, TransferRuleConfig.encodeTemplates(nextTemplates))
            .putString(TransferRuleConfig.KEY_BINDINGS, TransferRuleConfig.encodeBindings(nextBindings))
            .putString(TransferRuleConfig.KEY_DEFAULT_TEMPLATE_ID, nextDefault)
            .commit()
    }

    val route: AutoTransferRoute = when {
        templateEditor != null -> AutoTransferRoute.TemplateEditor(templateEditor!!)
        bindingEditor != null -> AutoTransferRoute.BindingEditor(bindingEditor!!)
        picker != null -> AutoTransferRoute.ContactPicker(picker!!)
        showReplySteps -> AutoTransferRoute.GlobalReplySteps(replyTarget)
        showTemplates -> AutoTransferRoute.TemplateManager
        showBindings -> AutoTransferRoute.BindingManager
        showBatchApply -> AutoTransferRoute.BatchApply
        else -> AutoTransferRoute.Main
    }

    SettingsRouteTransition(
        targetState = route,
        label = "AutoTransferRouteTransition",
        depthOf = { it.depth() }
    ) { currentRoute ->
        when (currentRoute) {
            is AutoTransferRoute.ContactPicker -> {
                val request = currentRoute.request
                ContactPickerPage(
                    context = context,
                    request = request,
                    onBack = { picker = null },
                    onConfirm = { selected ->
                        request.onValue(formatIds(selected.map { it.id }))
                        picker = null
                    }
                )
            }
            is AutoTransferRoute.TemplateEditor -> TransferTemplateEditorPage(
                context = context,
                request = currentRoute.request,
                onBack = { templateEditor = null },
                onSave = { updated ->
                    val request = currentRoute.request
                    val next = if (request.index in templates.indices) {
                        templates.toMutableList().also { it[request.index] = updated }
                    } else templates + updated
                    templates = next
                    if (defaultTemplateId.isBlank()) defaultTemplateId = updated.id
                    persistRules(nextTemplates = next, nextDefault = defaultTemplateId)
                    templateEditor = null
                },
                onDelete = {
                    val request = currentRoute.request
                    if (request.index in templates.indices) {
                        val deleted = templates[request.index].id
                        templates = templates.toMutableList().also { it.removeAt(request.index) }
                        bindings = bindings.map { if (it.templateId == deleted) it.copy(templateId = "") else it }
                        if (defaultTemplateId == deleted) defaultTemplateId = templates.firstOrNull()?.id.orEmpty()
                        persistRules()
                    }
                    templateEditor = null
                }
            )
            is AutoTransferRoute.BindingEditor -> TransferBindingEditorPage(
                context = context,
                request = currentRoute.request,
                templates = templates,
                onBack = { bindingEditor = null },
                onSave = { updated ->
                    val base = bindings.toMutableList()
                    if (currentRoute.request.index in base.indices) base.removeAt(currentRoute.request.index)
                    bindings = upsertTransferBindings(base, listOf(updated))
                    persistRules(nextBindings = bindings)
                    bindingEditor = null
                },
                onDelete = {
                    if (currentRoute.request.index in bindings.indices) {
                        bindings = bindings.toMutableList().also { it.removeAt(currentRoute.request.index) }
                        persistRules(nextBindings = bindings)
                    }
                    bindingEditor = null
                }
            )
            AutoTransferRoute.TemplateManager -> TransferTemplateListPage(
                templates = templates,
                onBack = { showTemplates = false },
                onOpen = { index, value -> templateEditor = TransferTemplateEditorRequest(index, value, true) },
                onAdd = { templateEditor = TransferTemplateEditorRequest(templates.size, newTransferTemplate(templates.size + 1, sp), false) }
            )
            AutoTransferRoute.BindingManager -> TransferBindingListPage(
                bindings = bindings,
                templates = templates,
                onBack = { showBindings = false },
                onOpen = { index, value -> bindingEditor = TransferBindingEditorRequest(index, value, true) },
                onDeleteBindings = { targets ->
                    val targetIds = targets.mapTo(HashSet()) { it.id }
                    bindings = bindings.filterNot { it.id in targetIds }
                    persistRules(nextBindings = bindings)
                    Toast.makeText(context, "已删除 ${targets.size} 个适用聊天", Toast.LENGTH_SHORT).show()
                },
                onAdd = {
                    picker = ContactPickerRequest(
                        title = "选择适用聊天",
                        mode = ContactPickerMode.BOTH,
                        multiSelect = true,
                        existingValue = "",
                        enableLabels = true,
                        onValue = { value ->
                            val additions = parseIds(value).map { id ->
                                bindings.firstOrNull { it.targetId == id }
                                    ?: TransferRuleBinding(id, id, transferContactLabel(id), false, if (templates.size == 1) templates.first().id else "")
                            }
                            if (additions.size == 1) {
                                val item = additions.first()
                                val index = bindings.indexOfFirst { it.targetId == item.targetId }
                                bindingEditor = TransferBindingEditorRequest(index.takeIf { it >= 0 } ?: bindings.size, item, index >= 0)
                            } else if (additions.isNotEmpty()) {
                                bindings = upsertTransferBindings(bindings, additions)
                                persistRules(nextBindings = bindings)
                            }
                        }
                    )
                }
            )
            AutoTransferRoute.BatchApply -> TransferBatchApplyPage(
                templates = templates,
                bindings = bindings,
                onBack = { showBatchApply = false },
                onPickChats = { templateId ->
                    picker = ContactPickerRequest(
                        title = "批量套用收款模板",
                        mode = ContactPickerMode.BOTH,
                        multiSelect = true,
                        existingValue = formatIds(bindings.filter { it.templateId == templateId }.map { it.targetId }),
                        enableLabels = true,
                        onValue = { value ->
                            val selected = parseIds(value)
                            val retained = bindings.filterNot { it.templateId == templateId && it.targetId !in selected }
                            val additions = selected.map { id ->
                                TransferRuleBinding(id, id, transferContactLabel(id), true, templateId)
                            }
                            bindings = upsertTransferBindings(retained, additions)
                            persistRules(nextBindings = bindings)
                            Toast.makeText(context, "模板已套用到 ${selected.size} 个聊天", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            )
            is AutoTransferRoute.GlobalReplySteps -> RedPacketReplyStepsPage(
                context = context,
                title = if (currentRoute.target == TransferReplyTarget.GROUP) "群聊收款回复" else "私聊收款回复",
                initialSteps = if (currentRoute.target == TransferReplyTarget.GROUP) groupReplySteps else replySteps,
                templateVariables = transferTemplateVariables,
                onBack = { showReplySteps = false },
                onSave = {
                    if (currentRoute.target == TransferReplyTarget.GROUP) groupReplySteps = it else replySteps = it
                    showReplySteps = false
                }
            )
            AutoTransferRoute.Main -> PageScaffold(
                title = provider.title(),
                largeTitle = provider.title(),
                scrollBehavior = scrollBehavior,
                bottomBar = {
                    BottomActionBar(
                        primaryText = "保存设置",
                        onPrimaryClick = save@{
                            if (!PaymentTemplateTimeFormatter.isValidPattern(timeFormat)) {
                                Toast.makeText(context, "时间格式无效", Toast.LENGTH_SHORT).show()
                                return@save
                            }
                            val normalizedTimeFormat = PaymentTemplateTimeFormatter.normalizePattern(timeFormat)
                            val minDelay = randomMinMs.toLongOrNull()?.coerceIn(0L, 600000L) ?: 0L
                            val maxDelay = randomMaxMs.toLongOrNull()?.coerceIn(minDelay, 600000L) ?: minDelay
                            val cleanSteps = cleanRedPacketReplySteps(replySteps)
                            val cleanGroupSteps = cleanRedPacketReplySteps(groupReplySteps)
                            sp.edit()
                                .putString(AutoTransferSettings.KEY_WHITELIST, whitelist)
                                .putString(AutoTransferSettings.KEY_BLACKLIST, blacklist)
                                .putInt(AutoTransferSettings.KEY_MODE, listMode)
                                .putLong(AutoTransferSettings.KEY_DELAY_MS, delayMs.toLongOrNull()?.coerceIn(0L, 600000L) ?: 0L)
                                .putInt(AutoTransferSettings.KEY_DELAY_MODE, delayMode)
                                .putLong(AutoTransferSettings.KEY_DELAY_RANDOM_MIN, minDelay)
                                .putLong(AutoTransferSettings.KEY_DELAY_RANDOM_MAX, maxDelay)
                                .putString(AutoTransferSettings.KEY_AMOUNT_VALUE, amountValue)
                                .putBoolean(AutoTransferSettings.KEY_AMOUNT_ENABLE, amountEnabled)
                                .putInt(AutoTransferSettings.KEY_AMOUNT_COND, amountCondition)
                                .putInt(AutoTransferSettings.KEY_AMOUNT_ACTION, amountAction)
                                .putInt(AutoTransferSettings.KEY_KEYWORD_MODE, keywordMode)
                                .putString(AutoTransferSettings.KEY_KEYWORDS, if (keywordMode == 0) "" else keywords)
                                .putString(AutoTransferSettings.KEY_RECEIVE_ACCOUNT, receiveAccount)
                                .putBoolean(AutoTransferSettings.KEY_QUIET_ENABLE, quietEnabled)
                                .putInt(AutoTransferSettings.KEY_QUIET_START_SECOND, parseRedPacketSecond(quietStart, 0))
                                .putInt(AutoTransferSettings.KEY_QUIET_END_SECOND, parseRedPacketSecond(quietEnd, 0))
                                .putString(AutoTransferSettings.KEY_REPLY_ITEMS, RedPacketRuleConfig.encodeReplySteps(cleanSteps))
                                .putString(AutoTransferSettings.KEY_REPLY_GROUP_ITEMS, RedPacketRuleConfig.encodeReplySteps(cleanGroupSteps))
                                .putBoolean(AutoTransferSettings.KEY_REPLY_ENABLE, cleanSteps.isNotEmpty())
                                .putString(AutoTransferSettings.KEY_REPLY_TEXT, cleanSteps.firstOrNull()?.content.orEmpty())
                                .putBoolean(AutoTransferSettings.KEY_NOTIFY_SYSTEM_ENABLE, notifySystemEnabled)
                                .putBoolean(AutoTransferSettings.KEY_NOTIFY_TOAST_ENABLE, notifyToastEnabled)
                                .putBoolean(AutoTransferSettings.KEY_NOTIFY_SOUND_ENABLE, notifySoundEnabled)
                                .putInt(AutoTransferSettings.KEY_NOTIFY_SOUND_MODE, notifySoundMode)
                                .putBoolean(AutoTransferSettings.KEY_NOTIFY_VIBRATE_ENABLE, notifyVibrateEnabled)
                                .putString(AutoTransferSettings.KEY_NOTIFY_SOUND_URI, notifySoundUri)
                                .putString(AutoTransferSettings.KEY_NOTIFY_TEXT, notifyText)
                                .putString(AutoTransferSettings.KEY_NOTIFY_TOAST_TEXT, notifyToastText)
                                .putBoolean(AutoTransferSettings.KEY_ANNOUNCE_ENABLE, announceEnabled)
                                .putString(AutoTransferSettings.KEY_ANNOUNCE_TEXT, announceText)
                                .putString(AutoTransferSettings.KEY_TIME_FORMAT, normalizedTimeFormat)
                                .apply()
                            replySteps = cleanSteps
                            groupReplySteps = cleanGroupSteps
                            timeFormat = normalizedTimeFormat
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
                    contentPadding = PaddingValues(
                        top = padding.calculateTopPadding() + 8.dp,
                        bottom = padding.calculateBottomPadding() + 84.dp
                    )
                ) {
                    item { SmallTitle(text = "规则") }
                    item {
                        SettingsCard {
                            SwitchRow(sp, AutoTransferSettings.KEY_ENABLE, "自动收款", "自动领取待收款转账", false)
                            InsetDivider()
                            ActionRow("收款规则模板", if (templates.isEmpty()) "暂无模板" else "${templates.size} 个模板") { showTemplates = true }
                            InsetDivider()
                            PopupChoiceRow(
                                title = "默认规则",
                                summary = describeTransferDefault(defaultTemplateId, templates),
                                options = listOf(PopupChoice("旧版全局设置", "")) + templates.map { PopupChoice(it.name, it.id) },
                                currentValue = defaultTemplateId,
                                onValueChanged = {
                                    defaultTemplateId = it
                                    persistRules(nextDefault = it)
                                }
                            )
                            InsetDivider()
                            ActionRow("适用聊天", if (bindings.isEmpty()) "暂无单独配置" else "${bindings.size} 个聊天") { showBindings = true }
                            InsetDivider()
                            ActionRow("批量套用模板", "一次给多个聊天分配同一规则") { showBatchApply = true }
                        }
                    }
                    item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "全局收款") }
                    item {
                        SettingsCard {
                            PopupChoiceRow("收款位置", "使用微信当前账号提供的收款账户", receiveAccountOptions, receiveAccount, onValueChanged = { receiveAccount = it })
                            InsetDivider()
                            SwitchRow(sp, AutoTransferSettings.KEY_REFUND_REJECTED, "拒收时退回", "规则不通过时原路退回", false)
                            InsetDivider()
                            PopupOptionRow("收款延迟", transferDelayModeLabel(delayMode), redPacketDelayModeOptions(true), delayMode, onValueChanged = { delayMode = it })
                            if (delayMode == TransferRuleConfig.DELAY_CUSTOM) {
                                InsetDivider(); NumberInputRow("自定义延迟", "单位 ms", delayMs) { delayMs = it }
                            } else if (delayMode == TransferRuleConfig.DELAY_RANDOM) {
                                InsetDivider(); NumberInputRow("最小延迟", "单位 ms", randomMinMs) { randomMinMs = it }
                                InsetDivider(); NumberInputRow("最大延迟", "单位 ms", randomMaxMs) { randomMaxMs = it }
                            }
                        }
                    }
                    item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "全局过滤") }
                    item {
                        SettingsCard {
                            PopupOptionRow("收款范围", transferListModeLabel(listMode), optionItems("全部接收" to 0, "只接收白名单" to 1, "拒收黑名单" to 2), listMode, onValueChanged = { listMode = it; sp.edit().putInt(AutoTransferSettings.KEY_MODE, it).apply() })
                            if (listMode == 1 || listMode == 2) {
                                InsetDivider()
                                ActionRow(if (listMode == 1) "白名单" else "黑名单", autoReplySelectedIdSummary(if (listMode == 1) whitelist else blacklist)) {
                                    picker = ContactPickerRequest(if (listMode == 1) "选择白名单" else "选择黑名单", ContactPickerMode.BOTH, true, if (listMode == 1) whitelist else blacklist, {
                                        if (listMode == 1) whitelist = it else blacklist = it
                                    }, true)
                                }
                            }
                            InsetDivider()
                            SwitchRow(amountEnabled, "启用金额规则", "按转账金额决定接收或拒收") { amountEnabled = it; sp.edit().putBoolean(AutoTransferSettings.KEY_AMOUNT_ENABLE, it).apply() }
                            if (amountEnabled) {
                                InsetDivider(); PopupOptionRow("金额条件", transferAmountConditionLabel(amountCondition), optionItems("大于" to 0, "小于" to 1, "等于" to 2), amountCondition, onValueChanged = { amountCondition = it; sp.edit().putInt(AutoTransferSettings.KEY_AMOUNT_COND, it).apply() })
                                InsetDivider(); InputRow("金额数值", "单位元，例如 10.5", amountValue) { amountValue = it.filter { ch -> ch.isDigit() || ch == '.' } }
                                InsetDivider(); PopupOptionRow("命中后动作", transferAmountActionLabel(amountAction), optionItems("拒收/忽略" to 0, "仅接收满足条件" to 1), amountAction, onValueChanged = { amountAction = it; sp.edit().putInt(AutoTransferSettings.KEY_AMOUNT_ACTION, it).apply() })
                            }
                            InsetDivider()
                            PopupOptionRow("关键词规则", transferKeywordModeLabel(keywordMode), optionItems("不启用" to 0, "必须包含关键词" to 1, "包含则拒收" to 2), keywordMode, onValueChanged = { keywordMode = it; sp.edit().putInt(AutoTransferSettings.KEY_KEYWORD_MODE, it).apply() })
                            if (keywordMode != 0) {
                                InsetDivider(); InputRow("关键词", "多个关键词用 |、逗号或换行分隔", keywords, minLines = 2) { keywords = it }
                            }
                            InsetDivider()
                            SwitchRow(quietEnabled, "禁收时段", "指定时段内不自动收款") { quietEnabled = it }
                            if (quietEnabled) {
                                InsetDivider(); TimeOfDayPickerRow("开始时间", quietStart) { quietStart = it }
                                InsetDivider(); TimeOfDayPickerRow("结束时间", quietEnd) { quietEnd = it }
                            }
                        }
                    }
                    item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "模板变量") }
                    item {
                        SettingsCard {
                            InputRow(
                                "时间变量格式",
                                "用于 {time}，例如 yyyy-MM-dd HH:mm:ss",
                                timeFormat
                            ) { timeFormat = it }
                        }
                    }
                    item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "收款后回复") }
                    item {
                        SettingsCard {
                            SelectRow("私聊收款回复", describeRedPacketReplySteps(replySteps)) {
                                replyTarget = TransferReplyTarget.PRIVATE
                                showReplySteps = true
                            }
                            InsetDivider()
                            SelectRow("群聊收款回复", describeRedPacketReplySteps(groupReplySteps)) {
                                replyTarget = TransferReplyTarget.GROUP
                                showReplySteps = true
                            }
                        }
                    }
                    item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "成功提醒") }
                    item {
                        TransferNotificationSettingsCard(
                            context, notifySystemEnabled, { notifySystemEnabled = it }, notifyToastEnabled, { notifyToastEnabled = it },
                            notifySoundEnabled, { notifySoundEnabled = it }, notifySoundMode, { notifySoundMode = it; notifySoundUri = "" },
                            notifyVibrateEnabled, { notifyVibrateEnabled = it }, notifySoundUri, { notifySoundUri = it },
                            notifyText, { notifyText = it }, notifyToastText, { notifyToastText = it },
                            announceEnabled, { announceEnabled = it }, announceText, { announceText = it }
                        )
                    }
                }
            }
        }
    }
}

@Composable
internal fun TransferTemplateListPage(
    templates: List<TransferRuleTemplate>,
    onBack: () -> Unit,
    onOpen: (Int, TransferRuleTemplate) -> Unit,
    onAdd: () -> Unit
) {
    val scrollBehavior = MiuixScrollBehavior()
    PageScaffold(
        title = "收款规则模板",
        largeTitle = "收款规则模板",
        scrollBehavior = scrollBehavior,
        bottomBar = {
            BottomActionBar("新增模板", onAdd, "返回", onBack)
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 8.dp,
                bottom = padding.calculateBottomPadding() + 84.dp
            )
        ) {
            item { SmallTitle(text = "模板") }
            item {
                SettingsCard {
                    if (templates.isEmpty()) {
                        EmptyText("暂无模板。新增后可设为默认规则或分配给指定聊天。")
                    } else {
                        templates.forEachIndexed { index, template ->
                            SelectRow(
                                template.name.ifBlank { "模板 ${index + 1}" },
                                describeTransferTemplate(template)
                            ) { onOpen(index, template) }
                            if (index < templates.lastIndex) InsetDivider()
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun TransferBindingListPage(
    bindings: List<TransferRuleBinding>,
    templates: List<TransferRuleTemplate>,
    onBack: () -> Unit,
    onOpen: (Int, TransferRuleBinding) -> Unit,
    onAdd: () -> Unit,
    onDeleteBindings: (List<TransferRuleBinding>) -> Unit
) {
    val context = LocalContext.current
    var category by remember { mutableStateOf(ConversationRuleCategory.ALL) }
    var query by remember { mutableStateOf("") }
    var batchDeleteMode by remember { mutableStateOf(false) }
    var selectedIds by remember { mutableStateOf<Set<String>>(emptySet()) }
    var showDeleteConfirm by remember { mutableStateOf(false) }
    val lower = query.trim().lowercase(Locale.US)
    val visible = bindings.mapIndexed { index, value -> index to value }.filter { (_, value) ->
        conversationRuleCategoryMatches(value, category) && (
            lower.isBlank() || value.label.lowercase(Locale.US).contains(lower) ||
                value.targetId.lowercase(Locale.US).contains(lower) ||
                templates.firstOrNull { it.id == value.templateId }?.name?.lowercase(Locale.US)?.contains(lower) == true
            )
    }
    val visibleIds = visible.mapTo(LinkedHashSet()) { it.second.id }
    val allVisibleSelected = visibleIds.isNotEmpty() && visibleIds.all { it in selectedIds }
    val selectedBindings = bindings.filter { it.id in selectedIds }
    val scrollBehavior = MiuixScrollBehavior()
    PageScaffold(
        title = "适用聊天",
        largeTitle = "适用聊天",
        scrollBehavior = scrollBehavior,
        bottomBar = {
            if (batchDeleteMode) {
                BottomActionBar(
                    primaryText = "删除所选（${selectedBindings.size}）",
                    onPrimaryClick = {
                        if (selectedBindings.isEmpty()) {
                            Toast.makeText(context, "请先选择适用聊天", Toast.LENGTH_SHORT).show()
                        } else {
                            showDeleteConfirm = true
                        }
                    },
                    secondaryText = "取消",
                    onSecondaryClick = {
                        batchDeleteMode = false
                        selectedIds = emptySet()
                    },
                    middleText = if (visibleIds.isEmpty()) null else if (allVisibleSelected) "取消全选" else "全选",
                    onMiddleClick = if (visibleIds.isEmpty()) null else {
                        {
                            selectedIds = if (allVisibleSelected) {
                                selectedIds - visibleIds
                            } else {
                                selectedIds + visibleIds
                            }
                        }
                    }
                )
            } else {
                BottomActionBar(
                    primaryText = "添加聊天",
                    onPrimaryClick = onAdd,
                    secondaryText = "返回",
                    onSecondaryClick = onBack,
                    middleText = if (bindings.isEmpty()) null else "批量删除",
                    onMiddleClick = if (bindings.isEmpty()) null else {
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
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 8.dp,
                bottom = padding.calculateBottomPadding() + 84.dp
            )
        ) {
            item {
                SettingsCard {
                    ConversationRuleCategoryTabs(
                        selected = category,
                        onSelected = { category = it },
                        includeOfficial = false
                    )
                }
            }
            item { SettingsCard { InputRow("搜索聊天", "昵称 / ID / 模板名", query) { query = it } } }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "聊天 · ${visible.size}/${bindings.size} 项") }
            item {
                SettingsCard {
                    if (visible.isEmpty()) {
                        EmptyText(if (bindings.isEmpty()) "暂无适用聊天。" else "没有匹配结果。")
                    } else {
                        visible.forEachIndexed { row, (index, binding) ->
                            if (batchDeleteMode) {
                                OptionChoiceRow(
                                    item = OptionItem(
                                        label = binding.label.ifBlank { binding.targetId },
                                        value = index,
                                        summary = describeTransferBinding(binding, templates)
                                    ),
                                    selected = binding.id in selectedIds,
                                    onClick = {
                                        selectedIds = if (binding.id in selectedIds) {
                                            selectedIds - binding.id
                                        } else {
                                            selectedIds + binding.id
                                        }
                                    }
                                )
                            } else {
                                SelectRow(
                                    binding.label.ifBlank { binding.targetId },
                                    describeTransferBinding(binding, templates)
                                ) { onOpen(index, binding) }
                            }
                            if (row < visible.lastIndex) InsetDivider()
                        }
                    }
                }
            }
        }
    }
    BatchDeleteConfirmDialog(
        show = showDeleteConfirm,
        message = "将删除已选的 ${selectedBindings.size} 个适用聊天，此操作不可撤销。",
        labels = selectedBindings.map { it.label.ifBlank { it.targetId } },
        onDismiss = { showDeleteConfirm = false },
        onConfirm = {
            val targets = selectedBindings
            showDeleteConfirm = false
            batchDeleteMode = false
            selectedIds = emptySet()
            onDeleteBindings(targets)
        }
    )
}

@Composable
internal fun TransferBindingEditorPage(
    context: Context,
    request: TransferBindingEditorRequest,
    templates: List<TransferRuleTemplate>,
    onBack: () -> Unit,
    onSave: (TransferRuleBinding) -> Unit,
    onDelete: () -> Unit
) {
    var enabled by remember(request) { mutableStateOf(request.binding.enabled) }
    var templateId by remember(request) { mutableStateOf(request.binding.templateId) }
    val scrollBehavior = MiuixScrollBehavior()
    PageScaffold(
        title = request.binding.label.ifBlank { request.binding.targetId },
        largeTitle = request.binding.label.ifBlank { request.binding.targetId },
        scrollBehavior = scrollBehavior,
        bottomBar = {
            BottomActionBar(
                "保存聊天",
                {
                    onSave(request.binding.copy(enabled = enabled, templateId = templateId))
                    Toast.makeText(context, "适用聊天已保存", Toast.LENGTH_SHORT).show()
                },
                "返回",
                onBack
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 8.dp,
                bottom = padding.calculateBottomPadding() + 84.dp
            )
        ) {
            item { SmallTitle(text = "聊天") }
            item {
                SettingsCard {
                    InfoRow("ID", request.binding.targetId)
                    InsetDivider()
                    SwitchRow(enabled, "启用自动收款", "关闭后该聊天不会自动收款") { enabled = it }
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "模板") }
            item {
                SettingsCard {
                    OptionChoiceRow(
                        OptionItem("跟随默认规则", -1, "使用默认模板或全局设置"),
                        templateId.isBlank()
                    ) { templateId = "" }
                    templates.forEachIndexed { index, template ->
                        InsetDivider()
                        OptionChoiceRow(
                            OptionItem(template.name.ifBlank { "模板 ${index + 1}" }, index, describeTransferTemplate(template)),
                            templateId == template.id
                        ) { templateId = template.id }
                    }
                }
            }
            if (request.canDelete) {
                item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "操作") }
                item { SettingsCard { ActionRow("移除适用聊天", "移除后恢复默认规则", onDelete) } }
            }
        }
    }
}

@Composable
internal fun TransferBatchApplyPage(
    templates: List<TransferRuleTemplate>,
    bindings: List<TransferRuleBinding>,
    onBack: () -> Unit,
    onPickChats: (String) -> Unit
) {
    val scrollBehavior = MiuixScrollBehavior()
    PageScaffold(
        title = "批量套用模板",
        largeTitle = "批量套用模板",
        scrollBehavior = scrollBehavior,
        bottomBar = { BottomActionBar("返回", onBack) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 8.dp,
                bottom = padding.calculateBottomPadding() + 84.dp
            )
        ) {
            item { SmallTitle(text = "选择模板") }
            item {
                SettingsCard {
                    if (templates.isEmpty()) {
                        EmptyText("请先新增收款规则模板。")
                    } else {
                        templates.forEachIndexed { index, template ->
                            val count = bindings.count { it.templateId == template.id }
                            SelectRow(template.name, "$count 个聊天 · ${describeTransferTemplate(template)}") {
                                onPickChats(template.id)
                            }
                            if (index < templates.lastIndex) InsetDivider()
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun TransferTemplateEditorPage(
    context: Context,
    request: TransferTemplateEditorRequest,
    onBack: () -> Unit,
    onSave: (TransferRuleTemplate) -> Unit,
    onDelete: () -> Unit
) {
    var value by remember(request) { mutableStateOf(request.template) }
    var showReplySteps by remember { mutableStateOf(false) }
    var replyTarget by remember { mutableStateOf(TransferReplyTarget.PRIVATE) }
    var groupReplySteps by remember(request) {
        mutableStateOf(request.template.groupReplySteps ?: request.template.replySteps)
    }
    var picker by remember { mutableStateOf<ContactPickerRequest?>(null) }
    val accountOptions = remember { transferReceiveAccountOptions(context) }
    if (showReplySteps) {
        RedPacketReplyStepsPage(
            context = context,
            title = if (replyTarget == TransferReplyTarget.GROUP) "模板群聊收款回复" else "模板私聊收款回复",
            initialSteps = if (replyTarget == TransferReplyTarget.GROUP) groupReplySteps else value.replySteps,
            templateVariables = transferTemplateVariables,
            onBack = { showReplySteps = false },
            onSave = {
                if (replyTarget == TransferReplyTarget.GROUP) groupReplySteps = it else value = value.copy(replySteps = it)
                showReplySteps = false
            }
        )
        return
    }
    picker?.let { requestPicker ->
        ContactPickerPage(
            context = context,
            request = requestPicker,
            onBack = { picker = null },
            onConfirm = {
                requestPicker.onValue(formatIds(it.map { option -> option.id }))
                picker = null
            }
        )
        return
    }
    val scrollBehavior = MiuixScrollBehavior()
    PageScaffold(
        title = value.name.ifBlank { "收款模板" },
        largeTitle = value.name.ifBlank { "收款模板" },
        scrollBehavior = scrollBehavior,
        bottomBar = {
            BottomActionBar(
                "保存模板",
                {
                    onSave(value.copy(
                        name = value.name.ifBlank { "收款模板" },
                        replySteps = cleanRedPacketReplySteps(value.replySteps),
                        groupReplySteps = cleanRedPacketReplySteps(groupReplySteps)
                    ))
                    Toast.makeText(context, "收款模板已保存", Toast.LENGTH_SHORT).show()
                },
                "返回",
                onBack
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 8.dp,
                bottom = padding.calculateBottomPadding() + 84.dp
            )
        ) {
            item { SmallTitle(text = "模板") }
            item {
                SettingsCard {
                    InputRow("模板名称", "用于默认规则和聊天绑定", value.name) { value = value.copy(name = it) }
                    InsetDivider()
                    SwitchRow(value.enabled, "启用模板", "关闭后使用该模板的聊天不会自动收款") { value = value.copy(enabled = it) }
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "收款") }
            item {
                SettingsCard {
                    PopupChoiceRow("收款位置", "选择转账到账账户", accountOptions, value.receiveAccount, onValueChanged = { value = value.copy(receiveAccount = it) })
                    InsetDivider()
                    SwitchRow(value.refundRejected, "拒收时退回", "规则不通过时原路退回") { value = value.copy(refundRejected = it) }
                    InsetDivider()
                    PopupOptionRow("收款延迟", transferDelayModeLabel(value.delayMode), redPacketDelayModeOptions(true), value.delayMode, onValueChanged = { value = value.copy(delayMode = it) })
                    if (value.delayMode == TransferRuleConfig.DELAY_CUSTOM) {
                        InsetDivider(); NumberInputRow("自定义延迟", "单位 ms", value.delayMs.toString()) { value = value.copy(delayMs = it.toLongOrNull()?.coerceIn(0L, 600000L) ?: 0L) }
                    } else if (value.delayMode == TransferRuleConfig.DELAY_RANDOM) {
                        InsetDivider(); NumberInputRow("最小延迟", "单位 ms", value.randomMinMs.toString()) { value = value.copy(randomMinMs = it.toLongOrNull()?.coerceIn(0L, 600000L) ?: 0L) }
                        InsetDivider(); NumberInputRow("最大延迟", "单位 ms", value.randomMaxMs.toString()) { value = value.copy(randomMaxMs = it.toLongOrNull()?.coerceIn(value.randomMinMs, 600000L) ?: value.randomMinMs) }
                    }
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "过滤") }
            item {
                SettingsCard {
                    PopupOptionRow("收款范围", transferListModeLabel(value.listMode), optionItems("全部接收" to 0, "只接收白名单" to 1, "拒收黑名单" to 2), value.listMode, onValueChanged = { value = value.copy(listMode = it) })
                    if (value.listMode == 1 || value.listMode == 2) {
                        InsetDivider()
                        val listValue = if (value.listMode == 1) value.whitelist else value.blacklist
                        ActionRow(if (value.listMode == 1) "白名单" else "黑名单", autoReplySelectedIdSummary(listValue)) {
                            picker = ContactPickerRequest(
                                if (value.listMode == 1) "选择白名单" else "选择黑名单",
                                ContactPickerMode.BOTH,
                                true,
                                listValue,
                                { selected -> value = if (value.listMode == 1) value.copy(whitelist = selected) else value.copy(blacklist = selected) },
                                true
                            )
                        }
                    }
                    InsetDivider()
                    SwitchRow(value.amountEnabled, "启用金额规则", "按转账金额决定接收或拒收") { value = value.copy(amountEnabled = it) }
                    if (value.amountEnabled) {
                        InsetDivider(); PopupOptionRow("金额条件", transferAmountConditionLabel(value.amountCondition), optionItems("大于" to 0, "小于" to 1, "等于" to 2), value.amountCondition, onValueChanged = { value = value.copy(amountCondition = it) })
                        InsetDivider(); InputRow("金额数值", "单位元，例如 10.5", value.amountValue) { text -> value = value.copy(amountValue = text.filter { it.isDigit() || it == '.' }) }
                        InsetDivider(); PopupOptionRow("命中后动作", transferAmountActionLabel(value.amountAction), optionItems("拒收/忽略" to 0, "仅接收满足条件" to 1), value.amountAction, onValueChanged = { value = value.copy(amountAction = it) })
                    }
                    InsetDivider()
                    PopupOptionRow("关键词规则", transferKeywordModeLabel(value.keywordMode), optionItems("不启用" to 0, "必须包含关键词" to 1, "包含则拒收" to 2), value.keywordMode, onValueChanged = { value = value.copy(keywordMode = it) })
                    if (value.keywordMode != 0) {
                        InsetDivider(); InputRow("关键词", "多个关键词用 |、逗号或换行分隔", value.keywords, minLines = 2) { value = value.copy(keywords = it) }
                    }
                    InsetDivider()
                    SwitchRow(value.quietEnabled, "禁收时段", "指定时段内不自动收款") { value = value.copy(quietEnabled = it) }
                    if (value.quietEnabled) {
                        InsetDivider(); TimeOfDayPickerRow("开始时间", formatRedPacketSecond(value.quietStartSecond)) { value = value.copy(quietStartSecond = parseRedPacketSecond(it, value.quietStartSecond)) }
                        InsetDivider(); TimeOfDayPickerRow("结束时间", formatRedPacketSecond(value.quietEndSecond)) { value = value.copy(quietEndSecond = parseRedPacketSecond(it, value.quietEndSecond)) }
                    }
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "收款后回复") }
            item {
                SettingsCard {
                    SelectRow("私聊收款回复", describeRedPacketReplySteps(value.replySteps)) {
                        replyTarget = TransferReplyTarget.PRIVATE
                        showReplySteps = true
                    }
                    InsetDivider()
                    SelectRow("群聊收款回复", describeRedPacketReplySteps(groupReplySteps)) {
                        replyTarget = TransferReplyTarget.GROUP
                        showReplySteps = true
                    }
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "成功提醒") }
            item {
                TransferNotificationSettingsCard(
                    context,
                    value.notifySystemEnabled, { value = value.copy(notifySystemEnabled = it) },
                    value.notifyToastEnabled, { value = value.copy(notifyToastEnabled = it) },
                    value.notifySoundEnabled, { value = value.copy(notifySoundEnabled = it) },
                    value.notifySoundMode, { value = value.copy(notifySoundMode = it, notifySoundUri = "") },
                    value.notifyVibrateEnabled, { value = value.copy(notifyVibrateEnabled = it) },
                    value.notifySoundUri, { value = value.copy(notifySoundUri = it) },
                    value.notifyText, { value = value.copy(notifyText = it) },
                    value.notifyToastText, { value = value.copy(notifyToastText = it) },
                    value.announceEnabled, { value = value.copy(announceEnabled = it) },
                    value.announceText, { value = value.copy(announceText = it) }
                )
            }
            if (request.canDelete) {
                item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "操作") }
                item { SettingsCard { ActionRow("删除模板", "删除后相关聊天恢复默认规则", onDelete) } }
            }
        }
    }
}
