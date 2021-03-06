package io.github.amayaframework.core.controllers;

import io.github.amayaframework.core.routers.MethodRouter;
import io.github.amayaframework.core.routes.MethodRoute;

import java.util.Collection;
import java.util.List;

/**
 * A class describing a standard http controller.
 */
final class HttpController implements Controller {
    private final Class<?> clazz;
    private final MethodRouter router;
    private final List<MethodRoute> routes;
    private final String route;

    HttpController(Class<?> clazz, String route, MethodRouter router, List<MethodRoute> routes) {
        this.clazz = clazz;
        this.route = route;
        this.router = router;
        this.routes = routes;
    }

    @Override
    public MethodRouter getRouter() {
        return router;
    }

    @Override
    public String getRoute() {
        return route;
    }

    @Override
    public Collection<MethodRoute> getRoutes() {
        return routes;
    }

    @Override
    public Class<?> getControllerClass() {
        return clazz;
    }
}
