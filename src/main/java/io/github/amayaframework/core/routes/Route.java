package io.github.amayaframework.core.routes;

import io.github.amayaframework.core.util.DuplicateParameterException;
import io.github.amayaframework.core.util.InvalidRouteFormatException;
import io.github.amayaframework.core.util.ParseUtil;
import io.github.amayaframework.core.util.Variable;
import io.github.amayaframework.filters.StringFilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Route {
    private static final Pattern BRACKETS = Pattern.compile("\\{([^{}]+)}");
    private static final String PARAMETER = "([^/]+)";
    private final String route;
    private final List<Variable<String, StringFilter>> parameters;
    private final boolean regexp;
    private Pattern pattern;

    public Route(String route) {
        Objects.requireNonNull(route);
        if (route.equals("/")) {
            route = "";
        }
        if (!route.isEmpty() && !ParseUtil.ROUTE.matcher(route).matches()) {
            throw new InvalidRouteFormatException(route);
        }
        Matcher brackets = BRACKETS.matcher(route);
        boolean found = brackets.find();
        regexp = found;
        List<Variable<String, StringFilter>> parameters = new ArrayList<>();
        while (found) {
            route = route.replace(brackets.group(), PARAMETER);
            Variable<String, StringFilter> parameter = ParseUtil.parseRouteParameter(brackets.group(1));
            if (parameters.contains(parameter)) {
                throw new DuplicateParameterException(parameter);
            }
            parameters.add(parameter);
            found = brackets.find();
        }
        this.parameters = Collections.unmodifiableList(parameters);
        if (regexp) {
            pattern = Pattern.compile(route);
        }
        this.route = route;
    }

    public Pattern pattern() {
        return pattern;
    }

    public String route() {
        return route;
    }

    public List<Variable<String, StringFilter>> parameters() {
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
        if (obj instanceof Route) {
            return hashCode() == obj.hashCode();
        }
        return false;
    }
}