package io.github.amayaframework.core.util;

import io.github.amayaframework.core.filters.*;
import io.github.amayaframework.core.scanners.FilterScanner;
import io.github.amayaframework.core.wrapping.Content;

import java.util.Collections;
import java.util.Map;

public final class FilterUtil {
    public static final Map<String, StringFilter> STRING_FILTERS = getStringFilters();
    public static final Map<String, ContentFilter> CONTENT_FILTERS = getContentFilters();

    private static Map<String, StringFilter> getStringFilters() {
        FilterScanner<StringFilter> scanner = new FilterScanner<>(StringFilter.class);
        Map<String, StringFilter> ret = scanner.safetyFind();
        ret.put("bigint", new BigIntegerFilter());
        ret.put("bool", new BooleanFilter());
        ret.put("double", new DoubleFilter());
        ret.put("int", new IntegerFilter());
        return Collections.unmodifiableMap(ret);
    }

    private static Map<String, ContentFilter> getContentFilters() {
        FilterScanner<ContentFilter> scanner = new FilterScanner<>(ContentFilter.class);
        Map<String, ContentFilter> ret = scanner.safetyFind();
        ret.put(Content.PATH, new PathFilter());
        ret.put(Content.QUERY, new MapListFilter());
        ret.put(Content.COOKIE, new CookieFilter());
        ret.put(Content.HEADER, new HeaderFilter());
        return Collections.unmodifiableMap(ret);
    }
}
