package io.github.amayaframework.core.routes;

import com.github.romanqed.util.Action;
import com.github.romanqed.util.Record;
import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.filters.Filter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public final class MethodRoute extends AbstractAttachable implements Route, Action<HttpRequest, HttpResponse> {
    private final HttpRoute route;
    private final Method method;
    private final Action<HttpRequest, HttpResponse> body;

    public MethodRoute(String route, Method method, Action<HttpRequest, HttpResponse> body) {
        super(new HashMap<>());
        this.route = HttpRoute.compile(route);
        this.method = Objects.requireNonNull(method);
        this.body = Objects.requireNonNull(body);
    }

    public Method getMethod() {
        return method;
    }

    @Override
    public HttpResponse execute(HttpRequest request) throws Throwable {
        return body.execute(request);
    }

    @Override
    public int hashCode() {
        return route.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MethodRoute)) {
            return false;
        }
        MethodRoute methodRoute = (MethodRoute) obj;
        return route.equals(methodRoute.route);
    }

    @Override
    public Pattern getPattern() {
        return route.getPattern();
    }

    @Override
    public String getRoute() {
        return route.getRoute();
    }

    @Override
    public boolean isRegexp() {
        return route.isRegexp();
    }

    @Override
    public boolean matches(String path) {
        return this.route.matches(path);
    }

    @Override
    public List<Record<String, Filter>> getParameters() {
        return route.getParameters();
    }
}
