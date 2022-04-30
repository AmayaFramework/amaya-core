package io.github.amayaframework.core.routes;

import io.github.amayaframework.core.filters.Filter;
import io.github.amayaframework.core.util.DuplicateParameterException;
import io.github.amayaframework.core.util.ParseUtil;
import io.github.amayaframework.core.util.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRoute implements Route {
    private static final Pattern BRACKETS = Pattern.compile("\\{([^{}]+)}");
    private static final String PARAMETER = "([^/]+)";
    private final String route;
    private final List<Variable<String, Filter>> parameters;
    private final boolean regexp;
    private final Pattern pattern;

    private HttpRoute(String route, boolean regexp, Pattern pattern, List<Variable<String, Filter>> parameters) {
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
        List<Variable<String, Filter>> parameters = new ArrayList<>();
        while (found) {
            pattern = pattern.replace(brackets.group(), PARAMETER);
            Variable<String, Filter> parameter = ParseUtil.parseRouteParameter(brackets.group(1));
            if (parameters.contains(parameter)) {
                throw new DuplicateParameterException(parameter);
            }
            parameters.add(parameter);
            found = brackets.find();
        }
        return new HttpRoute(route, isRegexp, Pattern.compile(pattern), parameters);
    }

    public Pattern getPattern() {
        return pattern;
    }

    public String getRoute() {
        return route;
    }

    public List<Variable<String, Filter>> getParameters() {
        return parameters;
    }

    public boolean matches(String route) {
        return this.route.equals(route) || pattern.matcher(route).matches();
    }

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
