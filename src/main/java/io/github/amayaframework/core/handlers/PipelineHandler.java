package io.github.amayaframework.core.handlers;

import com.github.romanqed.jutils.pipeline.ArrayPipeline;
import com.github.romanqed.jutils.pipeline.Pipeline;
import io.github.amayaframework.core.config.ConfigProvider;
import io.github.amayaframework.core.configurators.PipelineConfigurator;
import io.github.amayaframework.core.controllers.Controller;

import java.util.Collection;
import java.util.Objects;

public class PipelineHandler extends AbstractIOHandler {
    public PipelineHandler(Controller controller) {
        super(new ArrayPipeline<>(), new ArrayPipeline<>(), controller);
    }

    @Override
    public Pipeline<String> getInput() {
        return (Pipeline<String>) input;
    }

    @Override
    public Pipeline<String> getOutput() {
        return (Pipeline<String>) output;
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
