package io.github.amayaframework.core.controllers;

import io.github.amayaframework.core.routers.MethodRouter;
import io.github.amayaframework.core.routers.Router;

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
     * Returns the path to which the controller is bound
     *
     * @return {@link String}
     */
    String getPath();

    /**
     * Set the path to which the controller will bind
     *
     * @param route {@link String} route which must be not null
     */
    void setPath(String route);
}
