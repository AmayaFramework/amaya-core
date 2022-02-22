package io.github.amayaframework.core.configurators;

import io.github.amayaframework.core.handlers.PipelineHandler;

import java.util.function.Consumer;

public interface PipelineConfigurator extends Consumer<PipelineHandler> {
    void configure(PipelineHandler handler) throws Exception;

    @Override
    default void accept(PipelineHandler handler) {
        try {
            configure(handler);
        } catch (Exception e) {
            throw new IllegalStateException("Exception when configure", e);
        }
    }
}
