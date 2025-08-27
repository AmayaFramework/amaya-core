package io.github.amayaframework.server;

final class StringUtil {
    private StringUtil() {
    }

    static String trim(int start, int end, String string) {
        // noinspection DuplicatedCode
        while (start < end && string.charAt(start) <= ' ') {
            start++;
        }
        while (end > start && string.charAt(end - 1) <= ' ') {
            end--;
        }
        if (start >= end) {
            return null;
        }
        return string.substring(start, end);
    }

    static String trimQuote(int start, int end, String string) {
        // noinspection DuplicatedCode
        while (start < end && string.charAt(start) <= ' ') {
            start++;
        }
        while (end > start && string.charAt(end - 1) <= ' ') {
            end--;
        }
        if (start >= end) {
            return null;
        }
        if (string.charAt(start) == '"') {
            ++start;
        }
        if (string.charAt(end - 1) == '"') {
            --end;
        }
        if (start >= end) {
            return null;
        }
        return string.substring(start, end);
    }
}
