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

internal fun redPacketReplyModeOptions(): List<OptionItem> {
    return listOf(
        OptionItem("不回复", RedPacketRuleConfig.REPLY_OFF, "只抢红包，不发送回复"),
        OptionItem("发送文字", RedPacketRuleConfig.REPLY_TEXT, "抢到后发送文字"),
        OptionItem("发送图片", RedPacketRuleConfig.REPLY_IMAGE, "从系统文件管理器选择图片"),
        OptionItem("发送语音", RedPacketRuleConfig.REPLY_VOICE, "从系统文件管理器选择语音文件"),
        OptionItem("发送视频", RedPacketRuleConfig.REPLY_VIDEO, "从系统文件管理器选择视频"),
        OptionItem("发送表情", RedPacketRuleConfig.REPLY_EMOJI, "从系统文件管理器选择表情文件"),
        OptionItem("发送文件", RedPacketRuleConfig.REPLY_FILE, "从系统文件管理器选择任意文件"),
        OptionItem("发送收藏", RedPacketRuleConfig.REPLY_FAVORITE, "从最近收藏选择"),
        OptionItem("发送 XML", RedPacketRuleConfig.REPLY_XML, "发送 AppMsg/XML 内容")
    )
}

internal fun redPacketReplyStepModeOptions(): List<OptionItem> {
    return redPacketReplyModeOptions().filter { it.value != RedPacketRuleConfig.REPLY_OFF }
}

internal fun redPacketReplyUsesText(replyMode: Int): Boolean {
    return replyMode == RedPacketRuleConfig.REPLY_TEXT ||
        replyMode == RedPacketRuleConfig.REPLY_AT_SENDER
}

internal fun normalizeRedPacketReplyModeForUi(replyMode: Int): Int {
    return if (replyMode == RedPacketRuleConfig.REPLY_AT_SENDER) {
        RedPacketRuleConfig.REPLY_TEXT
    } else {
        replyMode
    }
}

internal fun normalizeRedPacketReplyTextForUi(replyMode: Int, text: String): String {
    if (replyMode != RedPacketRuleConfig.REPLY_AT_SENDER) return text
    if (redPacketReplyContainsAtSenderVariable(text)) return text
    return REDPACKET_AT_SENDER_VARIABLE + text
}

internal fun redPacketReplyContainsAtSenderVariable(text: String): Boolean {
    return REDPACKET_AT_SENDER_VARIABLES.any { text.contains(it) }
}

internal fun normalizeRedPacketReplyStepsForUi(
    steps: List<RedPacketReplyStep>,
    legacyMode: Int,
    legacyText: String,
    legacyDelayMs: Long,
    legacyRandom: Boolean
): List<RedPacketReplyStep> {
    val source = if (steps.isNotEmpty()) {
        steps
    } else {
        RedPacketRuleConfig.legacyReplySteps(legacyMode, legacyText, legacyDelayMs, legacyRandom)
    }
    return source.map { normalizeRedPacketReplyStepForUi(it) }
}

internal fun normalizeRedPacketReplyStepForUi(step: RedPacketReplyStep): RedPacketReplyStep {
    val mode = normalizeRedPacketReplyModeForUi(step.mode)
    val content = normalizeRedPacketReplyTextForUi(step.mode, step.content)
    return step.copy(
        id = step.id.ifBlank { System.currentTimeMillis().toString() },
        mode = mode,
        content = content,
        delayMs = step.delayMs.coerceIn(0L, 600000L)
    )
}

internal fun cleanRedPacketReplySteps(steps: List<RedPacketReplyStep>): List<RedPacketReplyStep> {
    return steps.map { normalizeRedPacketReplyStepForUi(it) }
        .filter { it.mode != RedPacketRuleConfig.REPLY_OFF && it.content.isNotBlank() }
}

internal fun newRedPacketReplyStep(index: Int): RedPacketReplyStep {
    return RedPacketReplyStep(
        id = System.currentTimeMillis().toString() + "_$index",
        mode = RedPacketRuleConfig.REPLY_TEXT,
        content = "谢谢老板",
        delayMs = 0L,
        random = false
    )
}

internal fun defaultRedPacketReplyContent(replyMode: Int): String {
    return when {
        redPacketReplyUsesText(replyMode) -> "谢谢老板"
        replyMode == RedPacketRuleConfig.REPLY_XML -> "<msg><appmsg appid=\"\" sdkver=\"0\"><title>谢谢老板</title></appmsg></msg>"
        else -> ""
    }
}

internal fun redPacketReplyModeLabel(replyMode: Int): String {
    return when (normalizeRedPacketReplyModeForUi(replyMode)) {
        RedPacketRuleConfig.REPLY_TEXT -> "发送文字"
        RedPacketRuleConfig.REPLY_IMAGE -> "发送图片"
        RedPacketRuleConfig.REPLY_VOICE -> "发送语音"
        RedPacketRuleConfig.REPLY_VIDEO -> "发送视频"
        RedPacketRuleConfig.REPLY_EMOJI -> "发送表情"
        RedPacketRuleConfig.REPLY_FILE -> "发送文件"
        RedPacketRuleConfig.REPLY_FAVORITE -> "发送收藏"
        RedPacketRuleConfig.REPLY_XML -> "发送 XML"
        else -> "不回复"
    }
}

internal fun describeRedPacketReplySteps(steps: List<RedPacketReplyStep>): String {
    if (steps.isEmpty()) return "不回复"
    val types = steps.take(3).joinToString("/") { redPacketReplyModeShortLabel(it.mode) }
    val suffix = if (steps.size > 3) "等" else ""
    return "${steps.size} 条回复 · $types$suffix · 顺序发送"
}

internal fun redPacketReplyStepSummary(step: RedPacketReplyStep): String {
    val content = if (redPacketReplyUsesText(step.mode) || step.mode == RedPacketRuleConfig.REPLY_XML) {
        step.content.trim().replace('\n', ' ').ifBlank { "未填写内容" }.take(24)
    } else {
        redPacketReplyContentSummary(step.mode, step.content, withHint = false).removePrefix("已选择：")
    }
    val delay = step.delayMs.coerceAtLeast(0L)
    val delayText = if (delay > 0L) "延迟 ${delay}ms" else "无延迟"
    val randomText = if (step.random) "随机追加" else "固定"
    return "$content · $delayText · $randomText"
}

internal fun redPacketReplyModeShortLabel(replyMode: Int): String {
    return when (normalizeRedPacketReplyModeForUi(replyMode)) {
        RedPacketRuleConfig.REPLY_TEXT -> "文字"
        RedPacketRuleConfig.REPLY_IMAGE -> "图片"
        RedPacketRuleConfig.REPLY_VOICE -> "语音"
        RedPacketRuleConfig.REPLY_VIDEO -> "视频"
        RedPacketRuleConfig.REPLY_EMOJI -> "表情"
        RedPacketRuleConfig.REPLY_FILE -> "文件"
        RedPacketRuleConfig.REPLY_FAVORITE -> "收藏"
        RedPacketRuleConfig.REPLY_XML -> "XML"
        else -> "关闭"
    }
}

internal fun redPacketReplyPickerTitle(replyMode: Int): String {
    return when (replyMode) {
        RedPacketRuleConfig.REPLY_IMAGE -> "选择图片"
        RedPacketRuleConfig.REPLY_VOICE -> "选择语音文件"
        RedPacketRuleConfig.REPLY_VIDEO -> "选择视频"
        RedPacketRuleConfig.REPLY_EMOJI -> "选择表情文件"
        RedPacketRuleConfig.REPLY_FILE -> "选择文件"
        RedPacketRuleConfig.REPLY_FAVORITE -> "选择收藏"
        else -> "选择文件"
    }
}

internal fun redPacketReplyContentSummary(replyMode: Int, value: String, withHint: Boolean = true): String {
    return if (replyMode == RedPacketRuleConfig.REPLY_FAVORITE) {
        favoriteSummary(value)
    } else {
        redPacketReplyFileSummary(value, withHint)
    }
}

internal fun redPacketReplyFileSummary(value: String, withHint: Boolean = true): String {
    val names = RedPacketRuleConfig.splitTokens(value)
        .map { redPacketReplyFileDisplayName(it) }
        .filter { it.isNotBlank() }
    if (names.isEmpty()) return "未选择文件"
    val preview = names.take(3).joinToString("、")
    val suffix = when {
        names.size == 1 -> ""
        names.size > 3 -> " 等 ${names.size} 个文件"
        else -> "，共 ${names.size} 个"
    }
    val summary = "已选择：$preview$suffix"
    return if (withHint) "$summary，点击重新选择" else summary
}

internal fun redPacketReplyFileDisplayName(value: String): String {
    val raw = value.trim()
    if (raw.isBlank()) return ""
    val name = if (raw.startsWith("content://", ignoreCase = true) || raw.startsWith("file://", ignoreCase = true)) {
        runCatching { Uri.parse(raw) }.getOrNull()
            ?.lastPathSegment
            ?.let { Uri.decode(it) }
            ?.substringAfterLast('/')
            ?.substringAfterLast(':')
            .orEmpty()
            .ifBlank { raw.substringAfterLast('/') }
    } else {
        File(raw).name.ifBlank { raw.substringAfterLast('/') }
    }
    val cacheNameMatch = Regex("""^\d{10,}_(.+)$""").matchEntire(name)
    return (cacheNameMatch?.groupValues?.getOrNull(1) ?: name).trim()
}

internal fun describeRedPacketBinding(
    binding: RedPacketRuleBinding,
    templates: List<RedPacketRuleTemplate>
): String {
    val state = if (binding.enabled) "启用" else "关闭"
    val template = templates.firstOrNull { it.id == binding.templateId }
        ?.name
        ?.ifBlank { binding.templateId }
        ?: "跟随默认规则"
    return "$state · $template · ${binding.targetId}"
}

internal fun redPacketBindingMatchesQuery(
    binding: RedPacketRuleBinding,
    templates: List<RedPacketRuleTemplate>,
    lowerQuery: String
): Boolean {
    if (binding.label.lowercase(Locale.US).contains(lowerQuery)) return true
    if (binding.targetId.lowercase(Locale.US).contains(lowerQuery)) return true
    val template = templates.firstOrNull { it.id == binding.templateId }
    return template != null && (
        template.name.lowercase(Locale.US).contains(lowerQuery) ||
            describeRedPacketTemplate(template).lowercase(Locale.US).contains(lowerQuery)
        )
}

internal fun redPacketBindingFromContact(
    row: MessageBlockContactOption,
    templates: List<RedPacketRuleTemplate>,
    existing: RedPacketRuleBinding?
): RedPacketRuleBinding {
    val option = row.contact
    val targetId = option.id.trim()
    return RedPacketRuleBinding(
        id = existing?.id ?: RedPacketRuleConfig.bindingKey(targetId),
        targetId = targetId,
        label = existing?.label?.takeIf { it.isNotBlank() } ?: option.label.ifBlank { targetId },
        enabled = existing?.enabled ?: false,
        templateId = existing?.templateId ?: if (templates.size == 1) templates.first().id else "",
        customRules = false,
        overrideRule = null
    )
}

internal fun findRedPacketBinding(
    bindings: List<RedPacketRuleBinding>,
    targetId: String
): RedPacketRuleBinding? {
    val key = RedPacketRuleConfig.bindingKey(targetId)
    return bindings.firstOrNull { RedPacketRuleConfig.bindingKey(it.targetId) == key }
}

internal fun normalizedRedPacketBinding(binding: RedPacketRuleBinding): RedPacketRuleBinding {
    val targetId = binding.targetId.trim()
    return binding.copy(
        id = RedPacketRuleConfig.bindingKey(targetId),
        targetId = targetId,
        label = binding.label.ifBlank { targetId },
        templateId = binding.templateId.trim(),
        customRules = false,
        overrideRule = null
    )
}

internal fun upsertRedPacketBindings(
    current: List<RedPacketRuleBinding>,
    additions: List<RedPacketRuleBinding>
): List<RedPacketRuleBinding> {
    val result = linkedMapOf<String, RedPacketRuleBinding>()
    current.forEach { binding ->
        val normalized = normalizedRedPacketBinding(binding)
        if (normalized.targetId.isNotBlank()) {
            result[RedPacketRuleConfig.bindingKey(normalized.targetId)] = normalized
        }
    }
    additions.forEach { binding ->
        val normalized = normalizedRedPacketBinding(binding)
        if (normalized.targetId.isBlank()) return@forEach
        val key = RedPacketRuleConfig.bindingKey(normalized.targetId)
        val old = result[key]
        result[key] = if (old == null) {
            normalized
        } else {
            normalized.copy(
                id = old.id,
                enabled = old.enabled,
                label = old.label.ifBlank { normalized.label },
                templateId = old.templateId.ifBlank { normalized.templateId }
            )
        }
    }
    return result.values.toList()
}

internal fun formatRedPacketSecond(value: Int): String {
    val second = value.coerceIn(0, 86399)
    return String.format(
        Locale.US,
        "%02d:%02d:%02d",
        second / 3600,
        second / 60 % 60,
        second % 60
    )
}

internal fun parseRedPacketSecond(value: String, fallback: Int): Int {
    return parseTimeOfDaySeconds(value).takeIf { it >= 0 } ?: fallback.coerceIn(0, 86399)
}

internal fun initialMessageBlockState(sp: SharedPreferences): MessageBlockInitialState {
    val rawTemplates = sp.getString(MessageBlockSettings.KEY_TEMPLATES, "") ?: ""
    val rawBindings = sp.getString(MessageBlockSettings.KEY_BINDINGS, "") ?: ""
    val parsedTemplates = MessageBlockSettings.parseTemplates(rawTemplates)
    val cleanedTemplates = parsedTemplates.map { MessageBlockSettings.clearTemplateTargets(it) }
    val existingBindings = MessageBlockSettings.parseBindings(rawBindings)
    val legacyBindings = if (rawBindings.isBlank()) {
        MessageBlockSettings.legacyBindingsFromTemplates(parsedTemplates)
    } else {
        emptyList()
    }
    val bindings = if (existingBindings.isNotEmpty()) existingBindings else legacyBindings
    val shouldPersist = rawBindings.isBlank() && legacyBindings.isNotEmpty()
    return MessageBlockInitialState(cleanedTemplates, bindings, shouldPersist)
}

internal fun newMessageBlockTemplate(index: Int): MessageBlockTemplate {
    return MessageBlockTemplate(
        id = System.currentTimeMillis().toString() + "_" + index,
        name = "模板 $index",
        enabled = true,
        mode = MessageBlockSettings.MODE_TARGETS,
        targets = "",
        targetGroupMembers = "",
        excludes = "",
        excludeGroupMembers = "",
        typeAll = false,
        types = emptySet(),
        textKeywords = ""
    )
}

internal fun describeMessageBlockTemplate(template: MessageBlockTemplate): String {
    val enabled = if (template.enabled) "启用" else "关闭"
    val type = describeMessageBlockRuleState(
        MessageBlockRuleState(template.typeAll, template.types, template.textKeywords)
    )
    return "$enabled · $type"
}

internal fun describeMessageBlockRuleState(rules: MessageBlockRuleState): String {
    val type = if (rules.typeAll) "所有消息" else {
        val names = messageBlockTypeOptions()
            .filter { rules.types.contains(it.key) }
            .joinToString("、") { it.title }
        names.ifBlank { "未选类型" }
    }
    val keyword = if (rules.textKeywords.isBlank()) "" else "，关键词"
    return "$type$keyword"
}

internal fun messageBlockRuleStateFromTemplates(
    templateIds: Set<String>,
    templates: List<MessageBlockTemplate>
): MessageBlockRuleState {
    val selected = templates.filter { templateIds.contains(it.id) }
    if (selected.isEmpty()) return MessageBlockRuleState(false, emptySet(), "")
    val types = LinkedHashSet<String>()
    selected.forEach { template -> types += template.types }
    val textKeywords = selected.map { it.textKeywords.trim() }
        .filter { it.isNotEmpty() }
        .distinct()
        .joinToString("\n")
    return MessageBlockRuleState(
        typeAll = selected.any { it.typeAll },
        types = types,
        textKeywords = textKeywords
    )
}

internal fun describeMessageBlockBinding(binding: MessageBlockBinding, templates: List<MessageBlockTemplate>): String {
    if (binding.quickBlockAll) return "聊天分组快捷屏蔽 · 所有消息"
    val state = if (binding.enabled) "启用" else "关闭"
    val action = if (binding.action == MessageBlockSettings.ACTION_EXCLUDE) "排除" else "屏蔽"
    val names = templates
        .filter { binding.templateIds.contains(it.id) }
        .joinToString("、") { it.name.ifBlank { it.id } }
        .ifBlank { "未绑定模板" }
    val rules = if (binding.customRules) {
        " · 专属规则：${describeMessageBlockRuleState(MessageBlockRuleState(binding.typeAll, binding.types, binding.textKeywords))}"
    } else {
        ""
    }
    return "$state · $action · $names$rules"
}

internal fun describeMessageBlockDefaultRule(rule: MessageBlockDefaultRule, templates: List<MessageBlockTemplate>): String {
    if (!rule.enabled) return "未启用"
    val names = templates
        .filter { rule.templateIds.contains(it.id) }
        .joinToString("、") { it.name.ifBlank { it.id } }
    val rules = if (rule.customRules) {
        "专属规则：${describeMessageBlockRuleState(MessageBlockRuleState(rule.typeAll, rule.types, rule.textKeywords))}"
    } else {
        names.ifBlank { "未绑定模板" }
    }
    return "启用 · $rules"
}

internal fun messageBlockBindingMatchesQuery(
    binding: MessageBlockBinding,
    templates: List<MessageBlockTemplate>,
    lowerQuery: String
): Boolean {
    if (binding.label.lowercase(Locale.US).contains(lowerQuery)) return true
    if (binding.targetId.lowercase(Locale.US).contains(lowerQuery)) return true
    if (binding.customRules) {
        val rules = describeMessageBlockRuleState(
            MessageBlockRuleState(binding.typeAll, binding.types, binding.textKeywords)
        ).lowercase(Locale.US)
        if (rules.contains(lowerQuery) || binding.textKeywords.lowercase(Locale.US).contains(lowerQuery)) return true
    }
    return templates.any { template ->
        binding.templateIds.contains(template.id) &&
            (
                template.name.lowercase(Locale.US).contains(lowerQuery) ||
                    template.id.lowercase(Locale.US).contains(lowerQuery)
                )
    }
}

internal fun messageBlockBindingTargetSummary(binding: MessageBlockBinding): String {
    val type = if (binding.targetType == MessageBlockSettings.TARGET_GROUP_MEMBER) "群成员" else "联系人 / 群聊 / 公众号"
    return "$type · ${binding.targetId}"
}

internal fun messageBlockBindingFromContact(
    option: ContactOption,
    templates: List<MessageBlockTemplate>,
    existing: MessageBlockBinding?
): MessageBlockBinding {
    val label = option.label.ifBlank { option.id }
    if (existing != null) return existing.copy(label = label)
    return MessageBlockBinding(
        id = MessageBlockSettings.bindingKey(MessageBlockSettings.TARGET_CONTACT, option.id),
        targetType = MessageBlockSettings.TARGET_CONTACT,
        targetId = option.id,
        label = label,
        enabled = true,
        action = MessageBlockSettings.ACTION_BLOCK,
        templateIds = defaultMessageBlockTemplateIds(templates)
    )
}

internal fun messageBlockBindingFromGroupMember(
    entry: String,
    templates: List<MessageBlockTemplate>,
    existing: MessageBlockBinding?
): MessageBlockBinding {
    val normalized = normalizeGroupMemberEntry(entry) ?: entry.trim()
    if (existing != null) return existing.copy(
        targetId = normalized,
        label = existing.label.ifBlank { normalized }
    )
    return MessageBlockBinding(
        id = MessageBlockSettings.bindingKey(MessageBlockSettings.TARGET_GROUP_MEMBER, normalized),
        targetType = MessageBlockSettings.TARGET_GROUP_MEMBER,
        targetId = normalized,
        label = normalized,
        enabled = true,
        action = MessageBlockSettings.ACTION_BLOCK,
        templateIds = defaultMessageBlockTemplateIds(templates)
    )
}

internal fun defaultMessageBlockTemplateIds(templates: List<MessageBlockTemplate>): Set<String> {
    return if (templates.size == 1) setOf(templates.first().id) else emptySet()
}

internal fun normalizedMessageBlockBinding(binding: MessageBlockBinding): MessageBlockBinding {
    val targetType = if (binding.targetType == MessageBlockSettings.TARGET_GROUP_MEMBER) {
        MessageBlockSettings.TARGET_GROUP_MEMBER
    } else {
        MessageBlockSettings.TARGET_CONTACT
    }
    val normalizedTarget = if (targetType == MessageBlockSettings.TARGET_GROUP_MEMBER) {
        normalizeGroupMemberEntry(binding.targetId) ?: binding.targetId.trim()
    } else {
        binding.targetId.trim()
    }
    val action = if (binding.action == MessageBlockSettings.ACTION_EXCLUDE) {
        MessageBlockSettings.ACTION_EXCLUDE
    } else {
        MessageBlockSettings.ACTION_BLOCK
    }
    return binding.copy(
        id = MessageBlockSettings.bindingKey(targetType, normalizedTarget),
        targetType = targetType,
        targetId = normalizedTarget,
        action = action,
        templateIds = binding.templateIds.filter { it.isNotBlank() }.toSet(),
        types = binding.types.filter { it.isNotBlank() }.toSet(),
        textKeywords = binding.textKeywords.trim()
    )
}

internal fun findMessageBlockBinding(
    bindings: List<MessageBlockBinding>,
    targetType: String,
    targetId: String
): MessageBlockBinding? {
    val normalizedType = if (targetType == MessageBlockSettings.TARGET_GROUP_MEMBER) {
        MessageBlockSettings.TARGET_GROUP_MEMBER
    } else {
        MessageBlockSettings.TARGET_CONTACT
    }
    val normalizedTarget = if (normalizedType == MessageBlockSettings.TARGET_GROUP_MEMBER) {
        normalizeGroupMemberEntry(targetId) ?: targetId.trim()
    } else {
        targetId.trim()
    }
    return bindings.firstOrNull {
        it.targetType == normalizedType && it.targetId == normalizedTarget
    }
}

internal fun upsertMessageBlockBindings(
    current: List<MessageBlockBinding>,
    additions: List<MessageBlockBinding>
): List<MessageBlockBinding> {
    val result = linkedMapOf<String, MessageBlockBinding>()
    current.forEach { binding ->
        val normalized = normalizedMessageBlockBinding(binding)
        result[MessageBlockSettings.bindingKey(normalized.targetType, normalized.targetId)] = normalized
    }
    additions.forEach { binding ->
        val normalized = normalizedMessageBlockBinding(binding)
        val key = MessageBlockSettings.bindingKey(normalized.targetType, normalized.targetId)
        val old = result[key]
        result[key] = if (old == null) {
            normalized
        } else {
            normalized.copy(
                id = old.id,
                enabled = old.enabled,
                action = old.action,
                templateIds = old.templateIds + normalized.templateIds,
                customRules = old.customRules,
                typeAll = old.typeAll,
                types = old.types,
                textKeywords = old.textKeywords
            )
        }
    }
    return result.values.toList()
}

internal fun messageBlockTypeOptions(): List<MessageBlockTypeOption> {
    return listOf(
        MessageBlockTypeOption(MessageBlockSettings.TYPE_TEXT, "文字", "普通文本消息"),
        MessageBlockTypeOption(MessageBlockSettings.TYPE_QUOTE, "引用消息", "引用文字、图片和其它消息"),
        MessageBlockTypeOption(MessageBlockSettings.TYPE_IMAGE, "图片", "图片消息"),
        MessageBlockTypeOption(MessageBlockSettings.TYPE_VIDEO, "小视频", "视频和短视频消息"),
        MessageBlockTypeOption(MessageBlockSettings.TYPE_VOICE, "语音", "语音消息"),
        MessageBlockTypeOption(MessageBlockSettings.TYPE_LINK, "文章、链接", "文章、链接、文件和接龙类 AppMsg"),
        MessageBlockTypeOption(MessageBlockSettings.TYPE_MUSIC, "音乐", "音乐类 AppMsg"),
        MessageBlockTypeOption(MessageBlockSettings.TYPE_MINI_PROGRAM, "小程序", "小程序卡片"),
        MessageBlockTypeOption(MessageBlockSettings.TYPE_CARD, "名片", "联系人名片"),
        MessageBlockTypeOption(MessageBlockSettings.TYPE_EMOJI, "动画表情", "表情消息"),
        MessageBlockTypeOption(MessageBlockSettings.TYPE_RED_PACKET, "红包", "红包消息"),
        MessageBlockTypeOption(MessageBlockSettings.TYPE_TRANSFER, "转账", "转账消息"),
        MessageBlockTypeOption(MessageBlockSettings.TYPE_VOIP, "视频/语音聊天", "通话邀请和记录"),
        MessageBlockTypeOption(MessageBlockSettings.TYPE_LOCATION, "地图位置", "位置分享消息"),
        MessageBlockTypeOption(MessageBlockSettings.TYPE_SYSTEM, "系统消息", "普通系统提示"),
        MessageBlockTypeOption(MessageBlockSettings.TYPE_PAT, "拍一拍", "拍一拍系统消息"),
        MessageBlockTypeOption(MessageBlockSettings.TYPE_VIDEO_NUMBER, "视频号链接", "视频号分享卡片"),
        MessageBlockTypeOption(MessageBlockSettings.TYPE_UNKNOWN, "未知类型", "微信新增或暂未识别的消息类型")
    )
}

internal fun loadMessageBlockContacts(
    context: Context,
    callback: (MessageBlockContactData, Throwable?) -> Unit
) {
    val api = WeChatApis.contact().contacts()
    if (api == null || !api.isAvailable) {
        callback(MessageBlockContactData(), IllegalStateException("联系人列表不可用"))
        return
    }
    val main = Handler(Looper.getMainLooper())
    Thread({
        try {
            val labelsByUser = linkedMapOf<String, MutableList<String>>()
            val labels = runCatching { api.getContactLabelList() }
                .getOrDefault(emptyList())
                .mapNotNull { label ->
                    val labelId = label.labelId.trim()
                    val labelName = label.labelName.trim().ifBlank { labelId }
                    if (labelId.isBlank() && labelName.isBlank()) return@mapNotNull null
                    val ids = label.userNameList.ifEmpty {
                        if (labelId.isBlank()) emptyList() else runCatching { api.getContactByLabelId(labelId) }.getOrDefault(emptyList())
                    }.map { it.trim() }
                        .filter { it.isNotEmpty() }
                        .distinct()
                    ids.forEach { wxId ->
                        labelsByUser.getOrPut(wxId) { arrayListOf() }.add(labelName)
                    }
                    MessageBlockLabelOption(
                        id = labelId.ifBlank { labelName },
                        name = labelName,
                        contactIds = ids
                    )
                }
                .sortedBy { it.name.lowercase(Locale.US) }

            val byId = linkedMapOf<String, MessageBlockContactOption>()
            fun putContact(contact: WeChatContact?, kind: MessageBlockContactKind, group: Boolean) {
                val option = contact.toOption(group) ?: return
                byId[option.id] = MessageBlockContactOption(
                    contact = option,
                    kind = kind,
                    labelNames = labelsByUser[option.id].orEmpty().distinct()
                )
            }

            api.getPickerContacts().forEach { putContact(it, MessageBlockContactKind.FRIEND, false) }
            api.getPickerGroups().forEach { putContact(it, MessageBlockContactKind.GROUP, true) }
            api.getPickerOfficialAccounts().forEach { putContact(it, MessageBlockContactKind.OFFICIAL, false) }

            val missingLabelIds = labels.flatMap { it.contactIds }
                .distinct()
                .filterNot { byId.containsKey(it) }
            if (missingLabelIds.isNotEmpty()) {
                api.getContactsByIds(missingLabelIds).forEach { contact ->
                    val kind = when {
                        contact.isGroup() -> MessageBlockContactKind.GROUP
                        contact.isOfficialAccount() -> MessageBlockContactKind.OFFICIAL
                        else -> MessageBlockContactKind.FRIEND
                    }
                    putContact(contact, kind, contact.isGroup())
                }
            }

            val contacts = byId.values.sortedWith(
                compareBy<MessageBlockContactOption> { it.kind.ordinal }
                    .thenBy { it.contact.label.lowercase(Locale.US) }
            )
            main.post { callback(MessageBlockContactData(contacts, labels), null) }
        } catch (throwable: Throwable) {
            main.post { callback(MessageBlockContactData(), throwable) }
        }
    }, "HchatMessageBlockContacts").start()
}

internal fun loadContacts(
    context: Context,
    mode: ContactPickerMode,
    includeLabels: Boolean = false,
    callback: (List<ContactOption>, Throwable?) -> Unit
) {
    val api = WeChatApis.contact().contacts()
    if (api == null || !api.isAvailable) {
        callback(emptyList(), IllegalStateException("联系人列表不可用"))
        return
    }
    val main = Handler(Looper.getMainLooper())
    Thread({
        try {
            val rows = mutableListOf<ContactOption>()
            val labelsByUser = linkedMapOf<String, MutableList<String>>()
            if (includeLabels) {
                runCatching { api.getContactLabelList() }.getOrDefault(emptyList()).forEach { label ->
                    val labelName = label.labelName.ifBlank { label.labelId }
                    if (labelName.isBlank()) return@forEach
                    label.userNameList.forEach { wxId ->
                        if (wxId.isNotBlank()) {
                            labelsByUser.getOrPut(wxId) { arrayListOf() }.add(labelName)
                        }
                    }
                }
            }
            if (mode == ContactPickerMode.FRIENDS || mode == ContactPickerMode.BOTH || mode == ContactPickerMode.ALL_CHATS) {
                rows += api.getPickerContacts().mapNotNull { it.toOption(false, labelsByUser[it.wxId].orEmpty()) }
            }
            if (mode == ContactPickerMode.GROUPS || mode == ContactPickerMode.BOTH || mode == ContactPickerMode.ALL_CHATS) {
                rows += api.getPickerGroups().mapNotNull { it.toOption(true) }
            }
            if (mode == ContactPickerMode.ALL_CHATS) {
                rows += api.getPickerOfficialAccounts().mapNotNull { it.toOption(false, official = true) }
            }
            val conversationOrder = WeChatApis.conversations()
                ?.getRecentConversationUsernames(10000)
                .orEmpty()
                .mapIndexed { index, username -> username to index }
                .toMap()
            val sorted = rows.distinctBy { it.id }.sortedWith(
                compareBy<ContactOption> { conversationOrder[it.id] ?: Int.MAX_VALUE }
                    .thenBy { it.group }
                    .thenBy { it.official }
                    .thenBy { it.label.lowercase(Locale.US) }
            )
            main.post { callback(sorted, null) }
        } catch (throwable: Throwable) {
            main.post { callback(emptyList(), throwable) }
        }
    }, "HchatContactPicker").start()
}

internal fun loadGroupMembers(
    context: Context,
    group: ContactOption,
    callback: (List<ContactOption>, Throwable?) -> Unit
) {
    val api = WeChatApis.contact().contacts()
    if (api == null || !api.isAvailable) {
        callback(emptyList(), IllegalStateException("群成员列表不可用"))
        return
    }
    val main = Handler(Looper.getMainLooper())
    Thread({
        try {
            val roomNames = api.getGroupMemberRoomDisplayNames(group.id)
            val rows = api.getGroupMembers(group.id).mapNotNull { contact ->
                val id = contact.wxId.takeIf { it.isNotBlank() } ?: return@mapNotNull null
                ContactOption(
                    id = id,
                    label = roomNames[id]?.takeIf { it.isNotBlank() }
                        ?: contact.displayName().ifEmpty { id },
                    group = false,
                    avatarUrl = contact.avatarUrl,
                    avatarBackupUrl = contact.avatarBackupUrl,
                    searchAliases = listOf(contact.remarkName, contact.nickname, contact.customWxId)
                        .filter { it.isNotBlank() }
                        .distinct()
                )
            }.sortedBy { it.label.lowercase(Locale.US) }
            main.post { callback(rows, null) }
        } catch (throwable: Throwable) {
            main.post { callback(emptyList(), throwable) }
        }
    }, "HchatGroupMemberPicker").start()
}

internal fun WeChatContact?.toOption(
    group: Boolean,
    labels: List<String> = emptyList(),
    official: Boolean = false
): ContactOption? {
    if (this == null || wxId.isNullOrEmpty()) return null
    return ContactOption(
        id = wxId,
        label = pickerDisplayName(group),
        group = group,
        avatarUrl = avatarUrl,
        avatarBackupUrl = avatarBackupUrl,
        labels = labels.distinct(),
        official = official,
        searchAliases = listOf(remarkName, nickname, customWxId)
            .filter { it.isNotBlank() }
            .distinct()
    )
}

internal fun parseIds(value: String): Set<String> {
    return value.split('|').map { it.trim() }.filter { it.isNotEmpty() }.toSet()
}

internal fun formatIds(ids: Iterable<String>): String {
    return ids
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .distinct()
        .joinToString("|")
}

internal fun parseFavoriteIds(value: String): Set<String> {
    return value.replace(";;;", "|")
        .split("|", ",", "，", "\n", "\r")
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .toSet()
}

internal fun formatFavoriteIds(ids: Iterable<String>, delimiter: String, multiSelect: Boolean): String {
    val values = ids
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .distinct()
    if (values.isEmpty()) return ""
    return if (multiSelect) {
        values.joinToString(delimiter)
    } else {
        values.first()
    }
}

internal fun favoriteSummary(value: String): String {
    val count = parseFavoriteIds(value).size
    return if (count == 0) "未选择收藏" else "已选择 $count 个收藏"
}

internal fun <T> List<T>.selectedFirst(isSelected: (T) -> Boolean): List<T> {
    if (size < 2) return this
    val selected = ArrayList<T>()
    val unselected = ArrayList<T>()
    for (value in this) {
        if (isSelected(value)) selected.add(value) else unselected.add(value)
    }
    if (selected.isEmpty() || unselected.isEmpty()) return this
    return selected.apply { addAll(unselected) }
}

internal fun groupMemberEntry(groupId: String, memberId: String): String {
    return "${groupId.trim()}/${memberId.trim()}"
}

internal fun parseGroupMemberEntries(value: String): Set<String> {
    return value.split("|", ",", "，", "\n", "\r")
        .mapNotNull { normalizeGroupMemberEntry(it) }
        .toSet()
}

internal fun formatGroupMemberEntries(entries: Iterable<String>): String {
    return entries
        .mapNotNull { normalizeGroupMemberEntry(it) }
        .distinct()
        .joinToString("|")
}

internal fun normalizeGroupMemberEntry(value: String): String? {
    val token = value.trim()
    if (token.isEmpty()) return null
    val separators = charArrayOf('/', '#', ':', '：')
    val index = separators.map { token.indexOf(it) }
        .filter { it > 0 }
        .minOrNull()
        ?: return null
    val groupId = token.substring(0, index).trim()
    val memberId = token.substring(index + 1).trim()
    if (groupId.isEmpty() || memberId.isEmpty()) return null
    return groupMemberEntry(groupId, memberId)
}

suspend fun loadAvatarBitmap(wxId: String, primary: String, backup: String): ImageBitmap? = withContext(Dispatchers.IO) {
    val sources = avatarSources(wxId, primary, backup)
    if (sources.isEmpty()) {
        null
    } else {
        val cacheKey = sources.joinToString("|")
        val cached = AvatarMemoryCache.cached(cacheKey)
        if (cached.first) {
            cached.second
        } else {
            var bitmap: ImageBitmap? = null
            for (source in sources) {
                bitmap = loadAvatarSource(source)?.asImageBitmap()
                if (bitmap != null) break
            }
            bitmap?.also { AvatarMemoryCache.put(cacheKey, it) }
        }
    }
}

internal fun avatarSources(wxId: String, primary: String, backup: String): List<String> {
    val result = LinkedHashSet<String>()
    if (primary.isNotBlank()) result.add(primary)
    if (backup.isNotBlank()) result.add(backup)
    val local = avatarLocalPath(wxId, hd = false)
    if (!local.isNullOrBlank()) result.add(local)
    val hdLocal = avatarLocalPath(wxId, hd = true)
    if (!hdLocal.isNullOrBlank()) result.add(hdLocal)
    return result.toList()
}

internal fun avatarLocalPath(wxId: String, hd: Boolean): String? {
    if (wxId.isBlank()) return null
    val root = WeChatAvatarPathCache.resolveAvatarRoot() ?: return null
    val hash = md5Lower(wxId) ?: return null
    return buildString(root.length + 64) {
        append(root.trimEnd('/'))
        append('/')
        append(hash.substring(0, 2))
        append('/')
        append(hash.substring(2, 4))
        append("/user_")
        if (hd) append("hd_")
        append(hash)
        append(".png")
    }
}

internal fun md5Lower(value: String): String? {
    return try {
        val digest = MessageDigest.getInstance("MD5").digest(value.toByteArray(Charsets.UTF_8))
        buildString(digest.size * 2) {
            for (b in digest) {
                append(((b.toInt() ushr 4) and 0xF).toString(16))
                append((b.toInt() and 0xF).toString(16))
            }
        }
    } catch (_: Throwable) {
        null
    }
}

internal fun loadAvatarSource(value: String): android.graphics.Bitmap? {
    return try {
        if (value.startsWith("http://") || value.startsWith("https://")) {
            val connection = URL(value).openConnection()
            connection.connectTimeout = 3000
            connection.readTimeout = 3000
            connection.getInputStream().use { BitmapFactory.decodeStream(it) }
        } else {
            val file = File(value)
            if (file.exists()) BitmapFactory.decodeFile(file.absolutePath) else null
        }
    } catch (_: Throwable) {
        null
    }
}

internal fun isDarkMode(context: Context): Boolean {
    val night = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    return night == Configuration.UI_MODE_NIGHT_YES
}
