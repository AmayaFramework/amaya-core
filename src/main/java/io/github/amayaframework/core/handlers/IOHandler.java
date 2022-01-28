package io.github.amayaframework.core.handlers;

import com.github.romanqed.jutils.structs.pipeline.Pipeline;
import com.github.romanqed.jutils.structs.pipeline.PipelineResult;
import io.github.amayaframework.core.configurators.Configurator;
import io.github.amayaframework.core.controllers.Controller;

import java.util.Collection;

public interface IOHandler {
    PipelineResult process(Object data);

    /**
     * Returns pipeline handles input
     *
     * @return {@link Pipeline}
     */
    Pipeline getInput();

    /**
     * Returns pipeline handles output
     *
     * @return {@link Pipeline}
     */
    Pipeline getOutput();

    /**
     * Returns the controller bound to the handler.
     *
     * @return {@link Controller}
     */
    Controller getController();

    void configure(Collection<Configurator> configurators);
}
