package io.github.amayaframework.core.routers;

import io.github.amayaframework.core.methods.HttpMethod;
import io.github.amayaframework.core.routes.Route;

/**
 * An interface describing a standard router used in the framework.
 */
public interface Router<T extends Route> {
    T follow(HttpMethod method, String route);

    void addRoute(HttpMethod method, T route);

    T getRoute(HttpMethod method, String pattern);

    T removeRoute(HttpMethod method, String pattern);
}
