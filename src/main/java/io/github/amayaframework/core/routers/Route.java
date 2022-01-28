package io.github.amayaframework.core.routers;

import com.github.romanqed.jutils.util.Checks;
import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.util.ParseUtil;
import io.github.amayaframework.core.util.Variable;
import io.github.amayaframework.filters.StringFilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Route {
    private static final Pattern BRACKETS = Pattern.compile("\\{([^{}]+)}");
    private static final String PARAMETER = "([^/]+)";

    private Pattern pattern;
    private String route;
    private List<Variable<String, StringFilter>> parameters;
    private Function<HttpRequest, HttpResponse> body;
    private boolean regexp;

    private Route() {
    }

    public static Route compile(String route, Function<HttpRequest, HttpResponse> body) {
        Objects.requireNonNull(route);
        if (route.equals("/")) {
            route = "";
        }
        if (!route.isEmpty() && !ParseUtil.ROUTE.matcher(route).matches()) {
            throw new InvalidRouteFormatException(route);
        }
        Route ret = new Route();
        Matcher brackets = BRACKETS.matcher(route);
        boolean found = brackets.find();
        ret.regexp = found;
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
        ret.parameters = Collections.unmodifiableList(parameters);
        if (ret.regexp) {
            ret.pattern = Pattern.compile(route);
        }
        ret.route = route;
        ret.body = Checks.requireNonNullElse(body, request -> null);
        return ret;
    }

    public static Route compile(String route) {
        return compile(route, null);
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

    public HttpResponse apply(HttpRequest request) {
        return body.apply(request);
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
