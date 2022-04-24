package io.github.amayaframework.core.handlers;

import com.github.romanqed.util.pipeline.ArrayPipeline;
import com.github.romanqed.util.pipeline.Pipeline;

public class PipelineHandler extends AbstractHandler {
    public PipelineHandler(boolean isAsync, EventManager manager) {
        this(isAsync, manager, new ArrayPipeline<>(), new ArrayPipeline<>());
    }

    public PipelineHandler(boolean isAsync, EventManager manager, Pipeline<String> input, Pipeline<String> output) {
        super(manager);
        if (isAsync) {
            this.input = e -> input.async(e).get();
            this.output = e -> output.async(e).get();
        } else {
            this.input = input;
            this.output = output;
        }
    }

    @Override
    public Pipeline<String> getInput() {
        return (Pipeline<String>) input;
    }

    @Override
    public Pipeline<String> getOutput() {
        return (Pipeline<String>) output;
    }
}
