package io.github.amayaframework.core.configurators;

import io.github.amayaframework.core.controllers.Controller;
import io.github.amayaframework.core.handlers.PipelineHandler;

import java.util.Objects;

public class ConfigurableWrapper {
    private final Configurable body;

    public ConfigurableWrapper(Configurable body) {
        this.body = Objects.requireNonNull(body);
    }

    public void configure(PipelineHandler handler, Controller controller) {

    }
}
