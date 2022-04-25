package io.github.amayaframework.core.handlers;

import com.github.romanqed.util.pipeline.ArrayPipeline;
import com.github.romanqed.util.pipeline.Pipeline;

public class PipelineHandler extends AbstractHandler {
    private final Pipeline<String> input;
    private final Pipeline<String> output;

    public PipelineHandler(boolean isAsync, EventManager manager) {
        super(manager);
        // Set input
        this.input = new ArrayPipeline<>();
        this.inputAction = isAsync ? e -> input.async(e).get() : input;
        // Set output
        this.output = new ArrayPipeline<>();
        this.outputAction = isAsync ? e -> output.async(e).get() : output;
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
