package io.github.amayaframework.core.controllers;

import io.github.amayaframework.core.routers.MethodRouter;
import io.github.amayaframework.core.routers.Router;
import io.github.amayaframework.core.routes.MethodRoute;

import java.util.Collection;

/**
 * <p>The interface representing the heart of the framework - controller.</p>
 * <p>Contains a router that performs internal navigation and its own path.</p>
 */
public interface Controller {
    /**
     * Returns a router that performs internal navigation
     *
     * @return {@link Router}
     */
    MethodRouter getRouter();

    /**
     * Returns a collection of controller routes
     *
     * @return {@link Collection} of {@link MethodRoute}
     */
    Collection<MethodRoute> getRoutes();

    /**
     * Returns the path to which the controller is bound
     *
     * @return {@link String}
     */
    String getRoute();

    /**
     * @return the controller class
     */
    Class<?> getControllerClass();
}
