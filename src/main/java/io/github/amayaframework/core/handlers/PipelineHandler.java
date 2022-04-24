package io.github.amayaframework.core.handlers;

import com.github.romanqed.util.pipeline.ArrayPipeline;
import com.github.romanqed.util.pipeline.Pipeline;

public class PipelineHandler extends AbstractHandler {
    private final Pipeline<String> input;
    private final Pipeline<String> output;

    public PipelineHandler(EventManager manager) {
        this(manager, new ArrayPipeline<>(), new ArrayPipeline<>());
    }

    public PipelineHandler(EventManager manager, Pipeline<String> input, Pipeline<String> output) {
        super(manager, input, output);
        this.input = input;
        this.output = output;
    }

    @Override
    public Pipeline<String> getInput() {
        return input;
    }

    @Override
    public Pipeline<String> getOutput() {
        return output;
    }
}
