package io.github.amayaframework.core.handlers;

import com.github.romanqed.jutils.pipeline.Pipeline;
import io.github.amayaframework.core.config.ConfigProvider;
import io.github.amayaframework.core.configurators.PipelineConfigurator;
import io.github.amayaframework.core.controllers.Controller;

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
        if (ConfigProvider.getConfig().isDebug()) {
            String message = "Handler pipelines have been successfully configured\n" +
                    "Input: " + input + "\n" +
                    "Output: " + output + "\n";
            logger.debug(message);
        }
    }
}
