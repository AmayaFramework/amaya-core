package io.github.amayaframework.core.routes;

import com.github.romanqed.util.Record;
import io.github.amayaframework.core.filters.Filter;
import io.github.amayaframework.core.util.ParseUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRoute implements Route {
    private static final Pattern BRACKETS = Pattern.compile("\\{([^{}]+)}");
    private static final String PARAMETER = "([^/]+)";
    private final String route;
    private final List<Record<String, Filter>> parameters;
    private final boolean regexp;
    private final Pattern pattern;

    private HttpRoute(String route, boolean regexp, Pattern pattern, List<Record<String, Filter>> parameters) {
        this.route = route;
        this.regexp = regexp;
        this.pattern = pattern;
        this.parameters = parameters;
    }

    public static HttpRoute compile(String route) {
        // Validate and normalize route
        Objects.requireNonNull(route);
        ParseUtil.validateRoute(route);
        route = ParseUtil.normalizeRoute(route);
        // Parse route
        String pattern = route;
        Matcher brackets = BRACKETS.matcher(pattern);
        boolean found = brackets.find();
        boolean isRegexp = found;
        List<Record<String, Filter>> parameters = new ArrayList<>();
        while (found) {
            pattern = pattern.replace(brackets.group(), PARAMETER);
            Record<String, Filter> parameter = ParseUtil.parseRouteParameter(brackets.group(1));
            if (parameters.contains(parameter)) {
                throw new DuplicateParameterException(parameter);
            }
            parameters.add(parameter);
            found = brackets.find();
        }
        return new HttpRoute(route, isRegexp, Pattern.compile(pattern), parameters);
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public String getRoute() {
        return route;
    }

    @Override
    public List<Record<String, Filter>> getParameters() {
        return parameters;
    }

    @Override
    public boolean matches(String path) {
        return this.route.equals(path) || (regexp && pattern.matcher(path).matches());
    }

    @Override
    public boolean isRegexp() {
        return regexp;
    }

    @Override
    public int hashCode() {
        return route.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof HttpRoute) {
            return route.hashCode() == obj.hashCode();
        }
        return false;
    }

    @Override
    public String toString() {
        return route;
    }
}
