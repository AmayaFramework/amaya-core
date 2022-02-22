package io.github.amayaframework.core.handlers;

import com.github.romanqed.jutils.pipeline.ArrayPipeline;
import com.github.romanqed.jutils.pipeline.Pipeline;

public class PipelineHandler extends AbstractIOHandler {
    private final Pipeline<String> input;
    private final Pipeline<String> output;

    public PipelineHandler() {
        this(new ArrayPipeline<>(), new ArrayPipeline<>());
    }

    public PipelineHandler(Pipeline<String> input, Pipeline<String> output) {
        super(input, output);
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
