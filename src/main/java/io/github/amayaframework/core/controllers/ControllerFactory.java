package io.github.amayaframework.core.controllers;

/**
 * An interface describing a universal controller factory.
 */
public interface ControllerFactory {
    /**
     * Creates a controller based on the passed route and a class object containing annotated methods inside.
     *
     * @param route  route to be set
     * @param source object to pack
     * @return {@link Controller} instance
     * @throws Exception if there was any exception during the packaging process.
     */
    Controller create(String route, Object source) throws Exception;
}
