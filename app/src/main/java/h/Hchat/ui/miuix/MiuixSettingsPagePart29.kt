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
internal fun ContactPickerPage(
    context: Context,
    request: ContactPickerRequest,
    onBack: () -> Unit,
    onConfirm: (List<ContactOption>) -> Unit
) {
    var loading by remember(request) { mutableStateOf(true) }
    var error by remember(request) { mutableStateOf("") }
    var contacts by remember(request) { mutableStateOf(emptyList<ContactOption>()) }
    var query by remember(request) { mutableStateOf("") }
    val initialSelectedIds = remember(request) { parseIds(request.existingValue) }
    var selectedIds by remember(request) { mutableStateOf(initialSelectedIds) }
    val scopeSelectionEnabled = request.enableScopeSelection &&
        request.multiSelect &&
        request.mode == ContactPickerMode.FRIENDS
    var scopeTab by remember(request) { mutableStateOf(ContactPickerScopeTab.FRIENDS) }
    var selectedConversationGroupId by remember(request) { mutableStateOf("") }
    var contactFilter by remember(request) {
        mutableStateOf(
            if (request.mode == ContactPickerMode.FRIENDS && request.enableLabels) {
                ContactPickerFilter.FRIENDS
            } else {
                ContactPickerFilter.ALL
            }
        )
    }
    var selectedLabel by remember(request) { mutableStateOf("") }
    val groupLabels = remember(request) {
        if (request.multiSelect && request.enableGroupLabels && request.mode.supportsGroups()) {
            GroupChatLabelStore.load(context)
        } else {
            emptyList()
        }
    }
    val listState = rememberLazyListState()
    val scrollBehavior = MiuixScrollBehavior()

    LaunchedEffect(request) {
        loadContacts(context, request.mode, request.enableLabels) { result, throwable ->
            loading = false
            if (throwable != null) {
                error = throwable.message ?: "读取联系人失败"
            } else {
                contacts = result
            }
        }
    }
    val conversationGroupFilters = remember(contacts) {
        ConversationGroupPickerSupport.filters(context, contacts.map { it.id })
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
    val scopedContacts = remember(contacts, selectedConversationIds) {
        selectedConversationIds?.let { ids -> contacts.filter { it.id in ids } } ?: contacts
    }
    val labels = remember(scopedContacts) {
        scopedContacts.flatMap { it.labels }
            .distinct()
            .sortedWith(compareBy { it.lowercase(Locale.US) })
    }
    val contactLabelScopes = remember(contacts) {
        contacts.asSequence()
            .filter { !it.group && !it.official }
            .flatMap { contact -> contact.labels.asSequence().map { it to contact.id } }
            .groupBy(keySelector = { it.first }, valueTransform = { it.second })
            .map { (name, ids) -> ContactPickerScope(name, ids.toSet()) }
            .sortedBy { it.name.lowercase(Locale.US) }
    }
    val availableGroupIds = remember(scopedContacts) {
        scopedContacts.filter { it.group }.map { it.id }.toSet()
    }
    val selectableGroupLabels = remember(groupLabels, availableGroupIds) {
        groupLabels.mapNotNull { label ->
            val ids = label.groupIds.intersect(availableGroupIds)
            label.copy(groupIds = ids).takeIf { ids.isNotEmpty() }
        }
    }
    val showGroupLabels = request.mode == ContactPickerMode.GROUPS ||
        contactFilter == ContactPickerFilter.GROUPS
    LaunchedEffect(contactFilter, labels) {
        if (contactFilter == ContactPickerFilter.LABELS) {
            if (selectedLabel.isBlank() || !labels.contains(selectedLabel)) {
                selectedLabel = labels.firstOrNull().orEmpty()
            }
        } else if (selectedLabel.isNotBlank()) {
            selectedLabel = ""
        }
    }
    val lower = query.trim().lowercase(Locale.US)
    val visibleContacts = remember(
        scopedContacts,
        contactFilter,
        selectedLabel,
        request.enableLabels,
        lower,
        initialSelectedIds
    ) {
        scopedContacts.asSequence().filter {
            when (contactFilter) {
                ContactPickerFilter.FRIENDS -> !it.group && !it.official
                ContactPickerFilter.GROUPS -> it.group
                ContactPickerFilter.OFFICIALS -> it.official
                ContactPickerFilter.LABELS -> request.enableLabels &&
                    selectedLabel.isNotBlank() &&
                    !it.group &&
                    !it.official &&
                    it.labels.contains(selectedLabel)
                ContactPickerFilter.ALL -> true
            }
        }.filter { it.matchesSearch(lower) }
            .toList()
            .selectedFirst { initialSelectedIds.contains(it.id) }
    }
    val visibleLabelScopes = remember(contactLabelScopes, lower, initialSelectedIds) {
        contactLabelScopes.filter {
            lower.isEmpty() || it.name.lowercase(Locale.US).contains(lower)
        }.selectedFirst { scope -> scope.memberIds.any(initialSelectedIds::contains) }
    }
    val visibleConversationGroupScopes = remember(conversationGroupFilters, lower, initialSelectedIds) {
        conversationGroupFilters.filter {
            lower.isEmpty() || it.name.lowercase(Locale.US).contains(lower)
        }.selectedFirst { scope -> scope.conversationIds.any(initialSelectedIds::contains) }
    }
    val visibleContactIds = remember(visibleContacts) {
        visibleContacts.mapTo(linkedSetOf()) { it.id }
    }
    val bottomVisibleIds = remember(
        scopeSelectionEnabled,
        scopeTab,
        visibleContactIds,
        visibleLabelScopes,
        visibleConversationGroupScopes
    ) {
        when {
            !scopeSelectionEnabled || scopeTab == ContactPickerScopeTab.FRIENDS -> visibleContactIds
            scopeTab == ContactPickerScopeTab.LABELS -> {
                visibleLabelScopes.flatMapTo(linkedSetOf()) { it.memberIds }
            }
            else -> {
                visibleConversationGroupScopes.flatMapTo(linkedSetOf()) { it.conversationIds }
            }
        }
    }
    val bottomVisibleAllSelected = bottomVisibleIds.isNotEmpty() && bottomVisibleIds.all { selectedIds.contains(it) }

    PageScaffold(
        title = request.title,
        largeTitle = request.title,
        scrollBehavior = scrollBehavior,
        bottomBar = {
            BottomActionBar(
                primaryText = if (request.multiSelect) "保存选择" else request.singleConfirmText,
                onPrimaryClick = {
                    val picked = contacts.filter { selectedIds.contains(it.id) }
                    if (picked.isEmpty() && !request.multiSelect) {
                        Toast.makeText(context, "请选择联系人", Toast.LENGTH_SHORT).show()
                    } else {
                        onConfirm(picked)
                    }
                },
                secondaryText = "返回",
                onSecondaryClick = onBack,
                middleText = if (request.multiSelect && bottomVisibleIds.isNotEmpty()) {
                    if (bottomVisibleAllSelected) "取消全选" else "全选"
                } else {
                    null
                },
                onMiddleClick = if (request.multiSelect && bottomVisibleIds.isNotEmpty()) {
                    {
                        selectedIds = if (bottomVisibleAllSelected) {
                            selectedIds - bottomVisibleIds
                        } else {
                            selectedIds + bottomVisibleIds
                        }
                    }
                } else {
                    null
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
            if (scopeSelectionEnabled) {
                item {
                    SettingsCard {
                        ContactPickerScopeFilterRow(
                            selected = scopeTab,
                            onSelected = {
                                scopeTab = it
                                contactFilter = ContactPickerFilter.FRIENDS
                                query = ""
                            }
                        )
                    }
                }
            } else if (request.mode == ContactPickerMode.FRIENDS && request.enableLabels) {
                item {
                    SettingsCard {
                        FriendContactFilterRow(
                            selected = contactFilter,
                            onSelected = {
                                contactFilter = it
                                query = ""
                            }
                        )
                    }
                }
            } else if (request.mode == ContactPickerMode.BOTH || request.mode == ContactPickerMode.ALL_CHATS) {
                item {
                    SettingsCard {
                        ContactFilterRow(
                            selected = contactFilter,
                            enableLabels = request.enableLabels,
                            enableOfficials = request.mode == ContactPickerMode.ALL_CHATS,
                            onSelected = {
                                contactFilter = it
                                query = ""
                            }
                        )
                    }
                }
            }
            if (!scopeSelectionEnabled && request.enableLabels && contactFilter == ContactPickerFilter.LABELS) {
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
            if (!scopeSelectionEnabled && conversationGroupChoices.size > 1) {
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
            if (showGroupLabels && selectableGroupLabels.isNotEmpty()) {
                item { PickerSectionHeader(text = "群聊标签") }
                item {
                    SettingsCard {
                        selectableGroupLabels.forEachIndexed { index, label ->
                            val selectedCount = label.groupIds.count { selectedIds.contains(it) }
                            GroupChatLabelPickerRow(
                                label = label,
                                selectedCount = selectedCount,
                                onClick = {
                                    selectedIds = if (selectedCount == label.groupIds.size) {
                                        selectedIds - label.groupIds
                                    } else {
                                        selectedIds + label.groupIds
                                    }
                                }
                            )
                            if (index < selectableGroupLabels.lastIndex) InsetDivider()
                        }
                    }
                }
            }
            item {
                SettingsCard {
                    InputRow(
                        title = "搜索",
                        summary = when {
                            scopeSelectionEnabled && scopeTab == ContactPickerScopeTab.LABELS -> "好友标签名称"
                            scopeSelectionEnabled && scopeTab == ContactPickerScopeTab.CONVERSATION_GROUPS -> "聊天分组名称"
                            else -> "昵称 / 群聊备注 / wxid"
                        },
                        value = query,
                        onValueChange = { query = it }
                    )
                }
            }
            item {
                PickerSectionHeader(
                    text = when {
                        scopeSelectionEnabled && scopeTab == ContactPickerScopeTab.LABELS -> {
                            if (visibleLabelScopes.isEmpty()) "好友标签" else "好友标签 · ${visibleLabelScopes.size} 项"
                        }
                        scopeSelectionEnabled && scopeTab == ContactPickerScopeTab.CONVERSATION_GROUPS -> {
                            if (visibleConversationGroupScopes.isEmpty()) "聊天分组" else "聊天分组 · ${visibleConversationGroupScopes.size} 项"
                        }
                        visibleContacts.isNotEmpty() -> "${contactFilter.title} · ${visibleContacts.size} 项"
                        else -> contactFilter.title
                    }
                )
            }
            when {
                loading -> item { SettingsCard { EmptyText("正在载入列表...") } }
                error.isNotEmpty() -> item { SettingsCard { EmptyText(error) } }
                scopeSelectionEnabled && scopeTab == ContactPickerScopeTab.LABELS -> {
                    if (visibleLabelScopes.isEmpty()) {
                        item { SettingsCard { EmptyText("没有匹配的好友标签") } }
                    } else {
                        item {
                            SettingsCard {
                                visibleLabelScopes.forEachIndexed { index, scope ->
                                    val selectedCount = scope.memberIds.count(selectedIds::contains)
                                    ContactPickerScopeRow(
                                        title = scope.name,
                                        memberCount = scope.memberIds.size,
                                        selectedCount = selectedCount,
                                        memberUnit = "位好友",
                                        onClick = {
                                            selectedIds = if (selectedCount == scope.memberIds.size) {
                                                selectedIds - scope.memberIds
                                            } else {
                                                selectedIds + scope.memberIds
                                            }
                                        }
                                    )
                                    if (index < visibleLabelScopes.lastIndex) InsetDivider()
                                }
                            }
                        }
                    }
                }
                scopeSelectionEnabled && scopeTab == ContactPickerScopeTab.CONVERSATION_GROUPS -> {
                    if (visibleConversationGroupScopes.isEmpty()) {
                        item { SettingsCard { EmptyText("没有匹配的聊天分组") } }
                    } else {
                        item {
                            SettingsCard {
                                visibleConversationGroupScopes.forEachIndexed { index, scope ->
                                    val selectedCount = scope.conversationIds.count(selectedIds::contains)
                                    ContactPickerScopeRow(
                                        title = scope.name,
                                        memberCount = scope.conversationIds.size,
                                        selectedCount = selectedCount,
                                        memberUnit = "位好友",
                                        onClick = {
                                            selectedIds = if (selectedCount == scope.conversationIds.size) {
                                                selectedIds - scope.conversationIds
                                            } else {
                                                selectedIds + scope.conversationIds
                                            }
                                        }
                                    )
                                    if (index < visibleConversationGroupScopes.lastIndex) InsetDivider()
                                }
                            }
                        }
                    }
                }
                visibleContacts.isEmpty() -> item { SettingsCard { EmptyText("没有匹配结果") } }
                else -> items(items = visibleContacts, key = { it.id }) { option ->
                    ContactListCard(
                        option = option,
                        selected = selectedIds.contains(option.id),
                        multiSelect = request.multiSelect,
                        onClick = {
                            selectedIds = if (request.multiSelect) {
                                if (selectedIds.contains(option.id)) {
                                    selectedIds - option.id
                                } else {
                                    selectedIds + option.id
                                }
                            } else {
                                setOf(option.id)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
internal fun FavoritePickerPage(
    request: FavoritePickerRequest,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val favoriteApi = WeChatApis.media()?.favorites()
    val initialCache = remember(request) { favoriteApi?.cachedList() }
    var loading by remember(request) { mutableStateOf(initialCache?.items.isNullOrEmpty()) }
    var error by remember(request) { mutableStateOf("") }
    var favorites by remember(request) { mutableStateOf(initialCache?.items.orEmpty()) }
    var backgroundLoading by remember(request) { mutableStateOf(false) }
    var query by remember(request) { mutableStateOf("") }
    var filter by remember(request) { mutableStateOf(FavoritePickerFilter.ALL) }
    var preview by remember(request) { mutableStateOf<FavoriteMediaPreview?>(null) }
    val initialSelectedIds = remember(request) { parseFavoriteIds(request.existingValue) }
    var selectedIds by remember(request) { mutableStateOf(initialSelectedIds) }
    val listState = rememberLazyListState()
    val scrollBehavior = MiuixScrollBehavior()

    LaunchedEffect(request) {
        loading = favorites.isEmpty()
        error = ""
        val result = withContext(Dispatchers.IO) {
            runCatching {
                val api = favoriteApi
                    ?: throw IllegalStateException("收藏 API 未就绪")
                if (!api.canList()) {
                    throw IllegalStateException("收藏列表不可用")
                }
                var page = h.Hchat.hooks.api.media.WeChatFavoriteApi.FavoritePage(emptyList(), false)
                for (attempt in 0..2) {
                    page = api.openCachedList()
                    if (page.items.isNotEmpty() || attempt == 2) break
                    delay(350L)
                }
                page
            }
        }
        loading = false
        result.onSuccess {
            favorites = it.items
            if (it.hasMore) {
                backgroundLoading = true
                runCatching {
                    while (true) {
                        val page = withContext(Dispatchers.IO) {
                            favoriteApi?.loadNextPage()
                                ?: throw IllegalStateException("收藏 API 未就绪")
                        }
                        favorites = page.items
                        if (!page.hasMore) break
                        delay(FAVORITE_BACKGROUND_BATCH_DELAY_MS)
                    }
                }.onFailure {
                    error = it.message ?: "后台读取收藏失败"
                }
                backgroundLoading = false
            }
        }
            .onFailure {
                error = it.message ?: "读取收藏失败"
                if (favorites.isEmpty()) favorites = emptyList()
                backgroundLoading = false
            }
    }

    val lower = query.trim().lowercase(Locale.US)
    val visible = favorites.filter { item ->
        filter.matches(item.type) && (
            lower.isEmpty() ||
                item.displayTitle().lowercase(Locale.US).contains(lower) ||
                item.displaySummary().lowercase(Locale.US).contains(lower) ||
                item.tags.any { it.lowercase(Locale.US).contains(lower) }
            )
    }.selectedFirst { initialSelectedIds.contains(it.localId.toString()) }
    val visibleIds = visible.map { it.localId.toString() }.toSet()
    val visibleAllSelected = visibleIds.isNotEmpty() && visibleIds.all { selectedIds.contains(it) }
    PageScaffold(
        title = request.title,
        largeTitle = request.title,
        scrollBehavior = scrollBehavior,
        bottomBar = {
            BottomActionBar(
                primaryText = "保存选择",
                onPrimaryClick = {
                    if (!request.multiSelect && selectedIds.isEmpty()) {
                        Toast.makeText(context, "请选择收藏", Toast.LENGTH_SHORT).show()
                    } else {
                        request.onValue(formatFavoriteIds(selectedIds, request.delimiter, request.multiSelect))
                        onBack()
                    }
                },
                secondaryText = "返回",
                onSecondaryClick = onBack,
                middleText = if (request.multiSelect && visibleIds.isNotEmpty()) {
                    if (visibleAllSelected) "取消全选" else "全选"
                } else {
                    null
                },
                onMiddleClick = if (request.multiSelect && visibleIds.isNotEmpty()) {
                    {
                        selectedIds = if (visibleAllSelected) {
                            selectedIds - visibleIds
                        } else {
                            selectedIds + visibleIds
                        }
                    }
                } else {
                    null
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
                    InputRow("搜索", "标题 / 来源 / 标签", query) { query = it }
                    FavoritePickerFilterRow(selected = filter, onSelected = { filter = it })
                }
            }
            item {
                PickerSectionHeader(
                    text = if (backgroundLoading) {
                        "${filter.label}收藏 · ${visible.size} 项 · 后台加载中（${favorites.size}）"
                    } else {
                        "${filter.label}收藏 · ${visible.size} / ${favorites.size} 项"
                    }
                )
            }
            when {
                loading -> item { SettingsCard { EmptyText("正在载入收藏...") } }
                error.isNotEmpty() -> item { SettingsCard { EmptyText(error) } }
                visible.isEmpty() && !backgroundLoading -> item {
                    SettingsCard {
                        EmptyText(if (query.isBlank()) "没有可选收藏" else "没有匹配收藏")
                    }
                }
                visible.isEmpty() -> item { SettingsCard { EmptyText("正在后台查找收藏...") } }
                else -> visible.forEach { favorite ->
                    item {
                        val id = favorite.localId.toString()
                        FavoriteListCard(
                            item = favorite,
                            selected = selectedIds.contains(id),
                            multiSelect = request.multiSelect,
                            onPreview = { path -> preview = FavoriteMediaPreview(favorite, path) },
                            onClick = {
                                selectedIds = if (request.multiSelect) {
                                    if (selectedIds.contains(id)) selectedIds - id else selectedIds + id
                                } else {
                                    setOf(id)
                                }
                            }
                        )
                    }
                }
            }
            if (backgroundLoading && visible.isNotEmpty()) {
                item { SettingsCard { EmptyText("正在后台加载更多收藏...") } }
            }
        }
    }
    preview?.let { media ->
        FavoriteMediaPreviewDialog(media = media, onClose = { preview = null })
    }
}

@Composable
internal fun GroupMemberPickerPage(
    context: Context,
    request: GroupMemberPickerRequest,
    onBack: () -> Unit,
    onConfirm: (List<String>) -> Unit
) {
    var groupLoading by remember(request) { mutableStateOf(true) }
    var groupError by remember(request) { mutableStateOf("") }
    var groups by remember(request) { mutableStateOf(emptyList<ContactOption>()) }
    var selectedGroup by remember(request) { mutableStateOf<ContactOption?>(null) }
    var memberLoading by remember(request) { mutableStateOf(false) }
    var memberError by remember(request) { mutableStateOf("") }
    var members by remember(request) { mutableStateOf(emptyList<ContactOption>()) }
    var query by remember(request) { mutableStateOf("") }
    val initialSelectedEntries = remember(request) {
        parseGroupMemberEntries(request.existingValue).filterTo(linkedSetOf()) { entry ->
            request.allowedGroupIds?.let { entry.substringBefore('/') in it } != false
        }
    }
    var selectedEntries by remember(request) { mutableStateOf<Set<String>>(initialSelectedEntries) }
    var selectionEdited by remember(request) { mutableStateOf(initialSelectedEntries.isNotEmpty()) }
    val groupListState = rememberLazyListState()
    val memberListState = rememberLazyListState()
    val scrollBehavior = MiuixScrollBehavior()

    LaunchedEffect(request) {
        loadContacts(context, ContactPickerMode.GROUPS) { result, throwable ->
            groupLoading = false
            if (throwable != null) {
                groupError = throwable.message ?: "读取群聊失败"
            } else {
                groups = request.allowedGroupIds?.let { allowed ->
                    result.filter { it.id in allowed }
                } ?: result
            }
        }
    }
    LaunchedEffect(selectedGroup?.id) {
        val group = selectedGroup
        if (group == null) {
            members = emptyList()
            memberError = ""
            memberLoading = false
            return@LaunchedEffect
        }
        memberLoading = true
        memberError = ""
        members = emptyList()
        query = ""
        memberListState.scrollToItem(0)
        loadGroupMembers(context, group) { result, throwable ->
            memberLoading = false
            if (throwable != null) {
                memberError = throwable.message ?: "读取群成员失败"
            } else {
                members = result
            }
        }
    }

    val group = selectedGroup
    PageScaffold(
        title = group?.label ?: request.title,
        largeTitle = group?.label ?: request.title,
        scrollBehavior = scrollBehavior,
        bottomBar = {
            BottomActionBar(
                primaryText = if (group == null && selectedEntries.isEmpty() && !selectionEdited) "选择群聊" else "保存选择",
                onPrimaryClick = {
                    if (selectedGroup == null && selectedEntries.isEmpty() && !selectionEdited) {
                        Toast.makeText(context, "请先选择群聊", Toast.LENGTH_SHORT).show()
                    } else {
                        onConfirm(selectedEntries.toList())
                    }
                },
                secondaryText = if (group == null) "返回" else "重新选群",
                onSecondaryClick = {
                    if (selectedGroup == null) {
                        onBack()
                    } else {
                        selectedGroup = null
                        query = ""
                    }
                },
                middleText = if (selectedEntries.isEmpty()) null else "清空选择",
                onMiddleClick = if (selectedEntries.isEmpty()) null else {
                    {
                        selectedEntries = emptySet()
                        selectionEdited = true
                    }
                }
            )
        }
    ) { padding ->
        val listRoute = group?.let { GroupMemberPickerRoute.Members(it) } ?: GroupMemberPickerRoute.Groups
        SettingsRouteTransition(
            targetState = listRoute,
            label = "GroupMemberPickerTransition",
            depthOf = { it.depth() }
        ) { route ->
            val routeGroup = (route as? GroupMemberPickerRoute.Members)?.group
            val lower = query.trim().lowercase(Locale.US)
            val rows = if (routeGroup == null) groups else members
            val visible = rows.filter {
                it.matchesSearch(lower)
            }.selectedFirst { option ->
                if (routeGroup == null) {
                    initialSelectedEntries.any { it.substringBefore('/') == option.id }
                } else {
                    initialSelectedEntries.contains(groupMemberEntry(routeGroup.id, option.id))
                }
            }
            val routeListState = if (routeGroup == null) groupListState else memberListState
            LazyColumn(
                modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
                state = routeListState,
                contentPadding = PaddingValues(
                    top = padding.calculateTopPadding() + 8.dp,
                    bottom = padding.calculateBottomPadding() + 84.dp
                )
            ) {
                item {
                    SettingsCard {
                        InputRow(
                            title = "搜索",
                            summary = if (routeGroup == null) "群名称 / 群聊备注 / 群号" else "群昵称 / wxid",
                            value = query,
                            onValueChange = { query = it }
                        )
                    }
                }
                item {
                    SmallTitle(
                        modifier = Modifier.padding(top = 10.dp),
                        text = if (routeGroup == null) "群聊" else "群成员"
                    )
                }
                when {
                    routeGroup == null && groupLoading -> item { SettingsCard { EmptyText("正在载入群聊...") } }
                    routeGroup == null && groupError.isNotEmpty() -> item { SettingsCard { EmptyText(groupError) } }
                    routeGroup != null && memberLoading -> item { SettingsCard { EmptyText("正在载入群成员...") } }
                    routeGroup != null && memberError.isNotEmpty() -> item { SettingsCard { EmptyText(memberError) } }
                    visible.isEmpty() -> item { SettingsCard { EmptyText("没有匹配结果") } }
                    else -> visible.forEach { option ->
                        item {
                            val entry = routeGroup?.let { groupMemberEntry(it.id, option.id) }.orEmpty()
                            ContactListCard(
                                option = option,
                                selected = if (routeGroup == null) false else selectedEntries.contains(entry),
                                multiSelect = routeGroup != null,
                                onClick = {
                                    if (routeGroup == null) {
                                        selectedGroup = option
                                    } else if (selectedEntries.contains(entry)) {
                                        selectedEntries = selectedEntries - entry
                                        selectionEdited = true
                                    } else {
                                        selectedEntries = selectedEntries + entry
                                        selectionEdited = true
                                    }
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
internal fun OptionPickerPage(
    request: OptionPickerRequest,
    onBack: () -> Unit,
    onSelected: (OptionItem) -> Unit
) {
    val scrollBehavior = MiuixScrollBehavior()
    val listState = rememberLazyListState()
    var selectedValue by remember(request) { mutableStateOf(request.currentValue) }
    PageScaffold(
        title = request.title,
        largeTitle = request.title,
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
            item { SmallTitle(text = "选项") }
            item {
                SettingsCard {
                    request.options.forEachIndexed { index, item ->
                        OptionChoiceRow(
                            item = item,
                            selected = item.value == selectedValue,
                            onClick = {
                                selectedValue = item.value
                                onSelected(item)
                            }
                        )
                        if (index < request.options.lastIndex) InsetDivider()
                    }
                }
            }
        }
    }
}

@Composable
internal fun UnsupportedFeaturePage(provider: FeatureSettingsProvider, onBack: () -> Unit) {
    val scrollBehavior = MiuixScrollBehavior()
    val listState = rememberLazyListState()
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
            item {
                SettingsCard {
                    EmptyText("这个模块暂时没有可配置项")
                }
            }
        }
    }
}

@Composable
internal fun PageScaffold(
    title: String,
    largeTitle: String,
    scrollBehavior: ScrollBehavior,
    onBack: (() -> Unit)? = null,
    topBarActions: @Composable RowScope.() -> Unit = {},
    bottomBar: @Composable ((Backdrop) -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    RegisterSettingsBackHandler(onBack)
    val graphicsLayer = rememberGraphicsLayer()
    val backdrop = rememberLayerBackdrop(graphicsLayer)
    val navigationInset = navigationButtonBottomInset()
    val navigationGap = if (navigationInset > 0.dp) NAVIGATION_BUTTON_EXTRA_GAP else 0.dp
    val bottomAvoidance = navigationInset + navigationGap
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MiuixTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .layerBackdrop(backdrop)
        ) {
            top.yukonga.miuix.kmp.basic.Scaffold(
                topBar = {
                    TopAppBar(
                        title = title,
                        largeTitle = largeTitle,
                        scrollBehavior = scrollBehavior,
                        navigationIcon = {
                            onBack?.let { back ->
                                Box(
                                    modifier = Modifier.size(40.dp).responsiveTap(onClick = back),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        imageVector = NavIcons.Back,
                                        contentDescription = "返回",
                                        colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.onSurface),
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        },
                        actions = topBarActions,
                        defaultWindowInsetsPadding = true
                    )
                },
                bottomBar = {},
                contentWindowInsets = WindowInsets(0, 0, 0, 0),
                content = { padding ->
                    content(
                        PaddingValues(
                            top = padding.calculateTopPadding(),
                            bottom = padding.calculateBottomPadding() + bottomAvoidance
                        )
                    )
                }
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = bottomAvoidance)
                .align(Alignment.BottomCenter)
        ) {
            bottomBar?.invoke(backdrop)
        }
    }
}

@Composable
internal fun SettingsCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth().padding(horizontal = 12.dp),
        cornerRadius = 18.dp
    ) {
        content()
    }
}

@Composable
internal fun SwitchRow(
    sp: SharedPreferences,
    key: String,
    title: String,
    summary: String,
    defaultValue: Boolean
) {
    var checked by remember { mutableStateOf(sp.getBoolean(key, defaultValue)) }
    Row(
        modifier = Modifier.fillMaxWidth().clickable {
            checked = !checked
            sp.edit().putBoolean(key, checked).apply()
        }.padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, color = MiuixTheme.colorScheme.onSurface, fontWeight = FontWeight.Medium)
            if (summary.isNotBlank()) {
                Text(text = summary, color = MiuixTheme.colorScheme.onSurfaceVariantSummary, fontSize = 12.sp)
            }
        }
        Switch(
            checked = checked,
            onCheckedChange = {
                checked = it
                sp.edit().putBoolean(key, it).apply()
            }
        )
    }
}

@Composable
internal fun SwitchRow(
    checked: Boolean,
    title: String,
    summary: String,
    enabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().then(
            if (enabled) {
                Modifier.clickable { onCheckedChange(!checked) }
            } else {
                Modifier
            }
        ).padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, color = MiuixTheme.colorScheme.onSurface, fontWeight = FontWeight.Medium)
            Text(text = summary, color = MiuixTheme.colorScheme.onSurfaceVariantSummary, fontSize = 12.sp)
        }
        Switch(
            checked = checked,
            onCheckedChange = { if (enabled) onCheckedChange(it) }
        )
    }
}

@Composable
internal fun OptionRow(
    sp: SharedPreferences,
    key: String,
    title: String,
    options: List<OptionItem>,
    defaultValue: Int,
    onValueChanged: (Int) -> Unit = {}
) {
    var currentValue by remember {
        mutableStateOf(sp.getInt(key, defaultValue))
    }
    PopupOptionRow(
        title = title,
        summary = options.firstOrNull { it.value == currentValue }?.label
            ?: options.firstOrNull { it.value == defaultValue }?.label
            ?: "",
        options = options,
        currentValue = currentValue,
        onValueChanged = {
            currentValue = it
            onValueChanged(it)
            sp.edit().putInt(key, it).apply()
        }
    )
}
