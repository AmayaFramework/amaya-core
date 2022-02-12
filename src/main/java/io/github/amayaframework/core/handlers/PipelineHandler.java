package io.github.amayaframework.core.handlers;

import com.github.romanqed.jutils.pipeline.Pipeline;
import io.github.amayaframework.core.configurators.PipelineConfigurator;
import io.github.amayaframework.core.controllers.Controller;
import io.github.amayaframework.core.util.AmayaConfig;

import java.util.Collection;
import java.util.Objects;

public class PipelineHandler extends AbstractIOHandler {
    public PipelineHandler(Controller controller) {
        super(new Pipeline(), new Pipeline(), controller);
    }

    @Override
    public Pipeline getInput() {
        return (Pipeline) input;
    }

    @Override
    public Pipeline getOutput() {
        return (Pipeline) output;
    }

    public void configure(Collection<PipelineConfigurator> configurators) {
        Objects.requireNonNull(configurators);
        configurators.forEach(e -> e.accept(this));
        if (AmayaConfig.INSTANCE.isDebug()) {
            String message = "Handler pipelines have been successfully configured\n" +
                    "Pipeline: " + input + "\n";
            logger.debug(message);
        }
    }
}
