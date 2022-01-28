package io.github.amayaframework.core.routers;

import io.github.amayaframework.core.methods.HttpMethod;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractRouter implements Router {
    protected Map<HttpMethod, Map<String, Route>> routes;

    public AbstractRouter() {
        routes = new ConcurrentHashMap<>();
    }

    @Override
    public void addRoute(HttpMethod method, Route route) {
        Objects.requireNonNull(method);
        Objects.requireNonNull(route);
        String key = route.route();
        Map<String, Route> methodRoutes = routes.get(method);
        if (methodRoutes == null) {
            methodRoutes = new ConcurrentHashMap<>();
            routes.put(method, methodRoutes);
        } else if (methodRoutes.containsKey(key)) {
            throw new DuplicateException("\"" + key + "\"");
        }
        methodRoutes.put(key, route);
    }

    @Override
    public Route getRoute(HttpMethod method, String pattern) {
        Objects.requireNonNull(method);
        Objects.requireNonNull(pattern);
        Map<String, Route> methodRoutes = routes.get(method);
        if (methodRoutes == null) {
            return null;
        }
        return methodRoutes.get(pattern);
    }

    @Override
    public Route removeRoute(HttpMethod method, String pattern) {
        Objects.requireNonNull(method);
        Objects.requireNonNull(pattern);
        Map<String, Route> methodRoutes = routes.get(method);
        if (methodRoutes == null) {
            return null;
        }
        return methodRoutes.remove(pattern);
    }
}
