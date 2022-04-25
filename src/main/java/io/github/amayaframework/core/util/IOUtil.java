package io.github.amayaframework.core.util;

import javax.servlet.http.Cookie;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public final class IOUtil {
    public static String readResourceFile(String name) throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream stream = classLoader.getResourceAsStream(name);
        if (stream == null) {
            return "";
        }
        String ret = readInputStream(stream, StandardCharsets.UTF_8);
        stream.close();
        return ret;
    }

    public static String readInputStream(InputStream stream, Charset charset) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset));
        return reader.lines().reduce("", (left, right) -> left + right + "\n");
    }

    public static String throwableToString(Throwable throwable) {
        StringWriter ret = new StringWriter();
        throwable.printStackTrace(new PrintWriter(ret));
        return ret.toString();
    }

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
}
