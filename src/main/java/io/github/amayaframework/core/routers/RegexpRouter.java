package io.github.amayaframework.core.routers;

import io.github.amayaframework.core.methods.HttpMethod;
import io.github.amayaframework.core.routes.MethodRoute;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>A class describing the implementation of the router that supports the processing of paths with getParameters.</p>
 * <p>Implemented using regular expressions.</p>
 */
public class RegexpRouter extends MethodRouter {
    private final Map<HttpMethod, Set<MethodRoute>> regexpRoutes;

    public RegexpRouter() {
        regexpRoutes = new ConcurrentHashMap<>();
    }

    @Override
    public MethodRoute follow(HttpMethod method, String route) {
        Map<String, MethodRoute> methodRoutes = routes.get(method);
        if (methodRoutes == null) {
            return null;
        }
        MethodRoute found = methodRoutes.get(route);
        if (found != null) {
            return found;
        }
        Set<MethodRoute> regexps = regexpRoutes.get(method);
        if (regexps == null) {
            return null;
        }
        for (MethodRoute checked : regexps) {
            if (checked.matches(route)) {
                return checked;
            }
        }
        return null;
    }

    @Override
    public void addRoute(HttpMethod method, MethodRoute route) {
        super.addRoute(method, route);
        if (route.isRegexp()) {
            Set<MethodRoute> methodRoutes = regexpRoutes.computeIfAbsent(method, k -> new HashSet<>());
            methodRoutes.add(route);
        }
    }

    @Override
    public MethodRoute removeRoute(HttpMethod method, String pattern) {
        MethodRoute ret = super.removeRoute(method, pattern);
        if (ret.isRegexp()) {
            regexpRoutes.get(method).remove(ret);
        }
        return ret;
    }
}
