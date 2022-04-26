package io.github.amayaframework.core.routers;

import io.github.amayaframework.core.methods.HttpMethod;
import io.github.amayaframework.core.routes.MethodRoute;
import io.github.amayaframework.core.util.DuplicateException;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public abstract class MethodRouter implements Router<MethodRoute> {
    protected Map<HttpMethod, Map<String, MethodRoute>> routes;

    public MethodRouter() {
        routes = new ConcurrentHashMap<>();
    }

    @Override
    public void addRoute(HttpMethod method, MethodRoute route) {
        Objects.requireNonNull(method);
        Objects.requireNonNull(route);
        String key = route.getRoute();
        Map<String, MethodRoute> methodRoutes = routes.get(method);
        if (methodRoutes == null) {
            methodRoutes = new ConcurrentHashMap<>();
            routes.put(method, methodRoutes);
        } else if (methodRoutes.containsKey(key)) {
            throw new DuplicateException("\"" + key + "\"");
        }
        methodRoutes.put(key, route);
    }

    @Override
    public MethodRoute getRoute(HttpMethod method, String pattern) {
        Objects.requireNonNull(method);
        Objects.requireNonNull(pattern);
        Map<String, MethodRoute> methodRoutes = routes.get(method);
        if (methodRoutes == null) {
            return null;
        }
        return methodRoutes.get(pattern);
    }

    @Override
    public MethodRoute removeRoute(HttpMethod method, String pattern) {
        Objects.requireNonNull(method);
        Objects.requireNonNull(pattern);
        Map<String, MethodRoute> methodRoutes = routes.get(method);
        if (methodRoutes == null) {
            return null;
        }
        return methodRoutes.remove(pattern);
    }
}
