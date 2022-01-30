package io.github.amayaframework.core.util;

import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.routers.InvalidFormatException;
import io.github.amayaframework.core.routers.Route;
import io.github.amayaframework.core.scanners.FilterScanner;
import io.github.amayaframework.filters.ContentFilter;
import io.github.amayaframework.filters.StringFilter;

import javax.servlet.http.Cookie;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseUtil {
    public static final String CONTENT_HEADER = "Content-Type";
    public static final String COOKIE_HEADER = "Cookie";
    public static final String CONTENT_CHARSET = "charset=";
    public static final Map<String, StringFilter> STRING_FILTERS;
    public static final Map<String, ContentFilter> CONTENT_FILTERS;
    public static final Pattern ROUTE = Pattern.compile("(?:/[^\\s/]+)+");
    private static final String PARAM_DELIMITER = ":";
    private static final String URL_ENCODING = AmayaConfig.INSTANCE.getCharset().name();
    private static final Pattern QUERY_VALIDATOR = Pattern.compile("^(?:[^&]+=[^&]+(?:&|$))+$");
    private static final Pattern QUERY = Pattern.compile("([^&]+)=([^&]+)");

    static {
        try {
            STRING_FILTERS = Collections.unmodifiableMap(new FilterScanner<>(StringFilter.class).find());
        } catch (Exception e) {
            throw new IllegalStateException("Exception when scanning parameter filters!", e);
        }
        try {
            CONTENT_FILTERS = Collections.unmodifiableMap(new FilterScanner<>(ContentFilter.class).find());
        } catch (Exception e) {
            throw new IllegalStateException("Exception when scanning content filters!", e);
        }
    }

    public static Variable<String, StringFilter> parseRouteParameter(String source) {
        Objects.requireNonNull(source);
        String[] split = source.split(PARAM_DELIMITER);
        if (split.length < 1 || split.length > 2) {
            throw new InvalidFormatException("Invalid parameter \"" + source + "\"");
        }
        if (split.length == 1) {
            return new Variable<>(split[0], null);
        }
        return new Variable<>(split[0], STRING_FILTERS.get(split[1]));
    }

    public static Map<String, Object> extractRouteParameters(Route route, String source) {
        Map<String, Object> ret = new HashMap<>();
        if (!route.isRegexp()) {
            return ret;
        }
        Matcher finder = route.pattern().matcher(source);
        Iterator<Variable<String, StringFilter>> parameters = route.parameters().iterator();
        if (!finder.find()) {
            return null;
        }
        Variable<String, StringFilter> next;
        for (int i = 1; i <= finder.groupCount(); ++i) {
            next = parameters.next();
            if (next.getValue() != null) {
                ret.put(next.getKey(), next.getValue().transform(finder.group(i)));
            } else {
                ret.put(next.getKey(), finder.group(i));
            }
        }
        return ret;
    }

    public static Map<String, List<String>> parseQueryString(String source) throws UnsupportedEncodingException {
        Map<String, List<String>> ret = new HashMap<>();
        if (source == null || source.isEmpty()) {
            return ret;
        }
        if (!QUERY_VALIDATOR.matcher(source).matches()) {
            return ret;
        }
        Matcher matcher = QUERY.matcher(source);
        while (matcher.find()) {
            String value = URLDecoder.decode(matcher.group(2), URL_ENCODING);
            ret.computeIfAbsent(matcher.group(1), key -> new ArrayList<>()).add(value);
        }
        return ret;
    }

    public static Map<String, Cookie> parseCookieHeader(String header) {
        Objects.requireNonNull(header);
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

    public static Charset parseCharsetHeader(String header, Charset defaultCharset) {
        if (header == null) {
            return defaultCharset;
        }
        header = header.trim();
        if (!header.startsWith("charset")) {
            return defaultCharset;
        }
        int position = header.indexOf('=');
        if (position < 0) {
            return defaultCharset;
        }
        try {
            return Charset.forName(header.substring(position + 1));
        } catch (Exception e) {
            return defaultCharset;
        }
    }

    public static String getResponseData(HttpResponse response) {
        return "Code: " + response.getCode() + "\n" +
                "Body: " + response.getBody() + "\n" +
                "Attachments: " + response.getAttachments() + "\n" +
                "Headers: " + response.getHeaderMap() + "\n" +
                "Cookies: " + response.getCookies() + "\n";
    }

    public static String normalizePath(String path) {
        if (path.equals("/")) {
            path = "";
        }
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }
}
