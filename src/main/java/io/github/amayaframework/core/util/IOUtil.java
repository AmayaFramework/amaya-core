package io.github.amayaframework.core.util;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;

public final class IOUtil {

    public static String cookieToHeader(Cookie cookie) {
        StringBuilder ret = new StringBuilder();
        ret.append(cookie.getName());
        ret.append('=');
        ret.append(cookie.getValue());
        if (cookie.getMaxAge() != -1) {
            ret.append("; Max-Age=");
            ret.append(cookie.getMaxAge());
        }
        if (cookie.getDomain() != null) {
            ret.append("; Domain=");
            ret.append(cookie.getDomain());
        }
        if (cookie.getPath() != null) {
            ret.append("; Path=");
            ret.append(cookie.getPath());
        }
        if (cookie.getSecure()) {
            ret.append("; Secure");
        }
        if (cookie.isHttpOnly()) {
            ret.append("; HttpOnly");
        }
        return ret.toString();
    }

    public static Map<String, Cookie> readCookieHeader(String header) {
        String[] split = header.split("; ");
        Map<String, Cookie> ret = new HashMap<>();
        for (String rawCookie : split) {
            int delimIndex = rawCookie.indexOf('=');
            if (delimIndex < 0) {
                return ret;
            }
            String name = rawCookie.substring(0, delimIndex);
            String value = rawCookie.substring(delimIndex + 1);
            ret.put(name, new Cookie(name, value));
        }
        return ret;
    }
}
