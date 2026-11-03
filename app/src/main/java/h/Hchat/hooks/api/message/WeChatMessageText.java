package h.Hchat.hooks.api.message;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 把消息正文从字符串或字节还原成文本。无法识别的字节不当成正文。
 */
final class WeChatMessageText {
    private WeChatMessageText() {
    }

    static String fromValue(Object value) {
        if (value == null) return "";
        if (value instanceof byte[]) return fromBytes((byte[]) value);
        String text = String.valueOf(value).trim();
        if (text.startsWith("[B@")) return "";
        return text;
    }

    static String fromBytes(byte[] bytes) {
        if (bytes == null || bytes.length == 0) return "";
        String utf8 = decode(bytes, StandardCharsets.UTF_8);
        if (looksLikeText(utf8)) return utf8;
        String utf16 = decode(bytes, StandardCharsets.UTF_16LE);
        if (looksLikeText(utf16)) return utf16;
        return "";
    }

    private static String decode(byte[] bytes, Charset charset) {
        try {
            return new String(bytes, charset).trim();
        } catch (Throwable ignored) {
            return "";
        }
    }

    private static boolean looksLikeText(String value) {
        if (value == null || value.isEmpty()) return false;
        int sample = Math.min(value.length(), 64);
        int printable = 0;
        for (int i = 0; i < sample; i++) {
            char c = value.charAt(i);
            if (c == '\uFFFD') return false;
            if (c >= 0x20 || c == '\n' || c == '\r' || c == '\t') printable++;
        }
        return printable * 2 >= sample;
    }
}
