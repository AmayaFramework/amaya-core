package io.github.amayaframework.core.handlers;

import com.github.romanqed.jutils.structs.pipeline.Pipeline;
import com.github.romanqed.jutils.structs.pipeline.PipelineResult;
import com.github.romanqed.jutils.util.Checks;
import io.github.amayaframework.core.configurators.Configurator;
import io.github.amayaframework.core.controllers.Controller;
import io.github.amayaframework.core.util.AmayaConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class BaseIOHandler implements IOHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Pipeline input;
    private final Pipeline output;
    private final Controller controller;
    private final Collection<Configurator> defaultConfigurators;

    public BaseIOHandler(Controller controller, Collection<Configurator> defaultConfigurators) {
        input = new Pipeline();
        output = new Pipeline();
        this.controller = Objects.requireNonNull(controller);
        Objects.requireNonNull(defaultConfigurators);
        defaultConfigurators.forEach(Objects::requireNonNull);
        this.defaultConfigurators = Collections.unmodifiableCollection(defaultConfigurators);
    }

    @Override
    public Pipeline getInput() {
        return input;
    }

    @Override
    public Pipeline getOutput() {
        return output;
    }

    @Override
    public void configure(Collection<Configurator> configurators) {
        configurators = Checks.requireNonNullElse(configurators, defaultConfigurators);
        configurators.forEach(e -> e.configure(this));
        if (AmayaConfig.INSTANCE.getDebug()) {
            String message = "Handler pipelines have been successfully configured\n" +
                    "Input: " + input + "\n" +
                    "Output: " + output + "\n";
            logger.debug(message);
        }
    }

    @Override
    public Controller getController() {
        return controller;
    }

    @Override
    public PipelineResult process(Object data) {
        return output.process(input.process(data));
    }
}
