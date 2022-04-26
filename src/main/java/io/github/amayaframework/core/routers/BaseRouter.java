package io.github.amayaframework.core.routers;

import io.github.amayaframework.core.methods.HttpMethod;
import io.github.amayaframework.core.routes.MethodRoute;

import java.util.Map;

/**
 * A class describing the implementation of the router that does not support the processing of paths with getParameters.
 */
public class BaseRouter extends MethodRouter {
    @Override
    public MethodRoute follow(HttpMethod method, String route) {
        Map<String, MethodRoute> methodRoutes = routes.get(method);
        if (methodRoutes == null) {
            return null;
        }
        return methodRoutes.get(route);
    }
}
