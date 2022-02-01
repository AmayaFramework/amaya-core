package io.github.amayaframework.core.handlers;

import com.github.romanqed.jutils.pipeline.Pipeline;
import com.github.romanqed.jutils.pipeline.PipelineResult;
import io.github.amayaframework.core.configurators.Configurator;
import io.github.amayaframework.core.controllers.Controller;

import java.util.Collection;

public interface IOHandler {
    PipelineResult process(Object data);

    /**
     * Returns pipeline handles controller
     *
     * @return {@link Pipeline}
     */
    Pipeline getPipeline();

    /**
     * Returns the controller bound to the handler.
     *
     * @return {@link Controller}
     */
    Controller getController();

    void configure(Collection<Configurator> configurators);
}
