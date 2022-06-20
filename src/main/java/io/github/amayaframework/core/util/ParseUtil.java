package io.github.amayaframework.core.util;

import com.github.romanqed.util.Record;
import io.github.amayaframework.core.filters.Filter;
import io.github.amayaframework.core.routes.Route;
import io.github.amayaframework.core.scanners.Scanner;
import io.github.amayaframework.core.spi.ServiceRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ParseUtil {
    public static final Map<String, Filter> STRING_FILTERS = getStringFilters();
    private static final Pattern ROUTE = Pattern.compile("(?:/[^\\s/]+)+");
    private static final String PARAM_DELIMITER = ":";

    private static Map<String, Filter> getStringFilters() {
        Scanner<String, Filter> scanner = ServiceRepository.getFilterScanner();
        return Collections.unmodifiableMap(scanner.safetyFind());
    }

    public static void validateRoute(String route) {
        if (!route.isEmpty() && !ParseUtil.ROUTE.matcher(route).matches()) {
            throw new InvalidRouteFormatException(route);
        }
    }

    public static String normalizeRoute(String route) {
        if (route.equals("/")) {
            route = "";
        }
        if (route.endsWith("/")) {
            route = route.substring(0, route.length() - 1);
        }
        return route;
    }

    public static Record<String, Filter> parseRouteParameter(String source) {
        String[] split = source.split(PARAM_DELIMITER);
        if (split.length < 1 || split.length > 2) {
            throw new InvalidFormatException("Invalid parameter \"" + source + "\"");
        }
        if (split.length == 1) {
            return new Record<>(split[0], null);
        }
        return new Record<>(split[0], STRING_FILTERS.get(split[1]));
    }

    public static Map<String, Object> extractRouteParameters(Route route, String source) {
        Map<String, Object> ret = new HashMap<>();
        if (!route.isRegexp()) {
            return ret;
        }
        Matcher finder = route.getPattern().matcher(source);
        if (!finder.find()) {
            return ret;
        }
        Iterator<Record<String, Filter>> parameters = route.getParameters().iterator();
        Record<String, Filter> next;
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
}
