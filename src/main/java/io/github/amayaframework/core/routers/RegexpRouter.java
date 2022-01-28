package io.github.amayaframework.core.routers;

import io.github.amayaframework.core.methods.HttpMethod;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>A class describing the implementation of the router that supports the processing of paths with parameters.</p>
 * <p>Implemented using regular expressions.</p>
 */
public class RegexpRouter extends AbstractRouter {
    private final Map<HttpMethod, Set<Route>> regexpRoutes;

    public RegexpRouter() {
        regexpRoutes = new ConcurrentHashMap<>();
    }

    @Override
    public Route follow(HttpMethod method, String route) {
        Map<String, Route> methodRoutes = routes.get(method);
        if (methodRoutes == null) {
            return null;
        }
        Route found = methodRoutes.get(route);
        if (found != null) {
            return found;
        }
        Set<Route> regexps = regexpRoutes.get(method);
        if (regexps == null) {
            return null;
        }
        for (Route checked : regexps) {
            if (checked.matches(route)) {
                return checked;
            }
        }
        return null;
    }

    @Override
    public void addRoute(HttpMethod method, Route route) {
        super.addRoute(method, route);
        if (route.isRegexp()) {
            Set<Route> methodRoutes = regexpRoutes.computeIfAbsent(method, k -> new HashSet<>());
            methodRoutes.add(route);
        }
    }

    @Override
    public Route removeRoute(HttpMethod method, String pattern) {
        Route ret = super.removeRoute(method, pattern);
        if (ret.isRegexp()) {
            regexpRoutes.get(method).remove(ret);
        }
        return ret;
    }
}
