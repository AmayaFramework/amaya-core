package io.github.amayaframework.core.configurators;

import io.github.amayaframework.core.controllers.Controller;
import io.github.amayaframework.core.pipeline.NamedPipeline;

/**
 * Basic interface for framework configurators.
 */
public interface Configurator {
    /**
     * Configures each controller in current builder
     *
     * @param controller controller to be configured
     * @throws Throwable any inner exceptions, which stops building
     */
    void configureController(Controller controller) throws Throwable;

    /**
     * Configures each input pipeline in current builder
     *
     * @param input input pipeline to be configured
     * @throws Throwable any inner exceptions, which stops building
     */
    void configureInput(NamedPipeline input) throws Throwable;

    /**
     * Configures each output pipeline in current builder
     *
     * @param output output pipeline to be configured
     * @throws Throwable any inner exceptions, which stops building
     */
    void configureOutput(NamedPipeline output) throws Throwable;
}
