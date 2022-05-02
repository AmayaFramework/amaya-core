package io.github.amayaframework.core.pipeline;

import io.github.amayaframework.core.methods.HttpMethod;
import io.github.amayaframework.core.routes.MethodRoute;

public final class RouteData {
    final MethodRoute route;
    final String path;
    final HttpMethod method;

    public RouteData(HttpMethod method, String path, MethodRoute route) {
        this.route = route;
        this.path = path;
        this.method = method;
    }
}