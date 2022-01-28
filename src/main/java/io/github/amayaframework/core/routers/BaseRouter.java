package io.github.amayaframework.core.routers;

import io.github.amayaframework.core.methods.HttpMethod;

import java.util.Map;

/**
 * A class describing the implementation of the router that does not support the processing of paths with parameters.
 */
public class BaseRouter extends AbstractRouter {
    @Override
    public Route follow(HttpMethod method, String route) {
        Map<String, Route> methodRoutes = routes.get(method);
        if (methodRoutes == null) {
            return null;
        }
        return methodRoutes.get(route);
    }
}
