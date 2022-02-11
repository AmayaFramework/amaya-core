package io.github.amayaframework.core.configurators;

import com.github.romanqed.jutils.pipeline.Pipeline;
import io.github.amayaframework.core.handlers.PipelineHandler;
import io.github.amayaframework.core.pipelines.InputStage;
import io.github.amayaframework.core.pipelines.OutputStage;
import io.github.amayaframework.core.pipelines.ParseRequestBodyAction;

public class AmayaConfigurator implements PipelineConfigurator {
    private final ActionFabric fabric;

    public AmayaConfigurator(String prefix) {
        this.fabric = new ActionFabric(prefix);
    }

    public AmayaConfigurator() {
        this.fabric = new ActionFabric();
    }

    @Override
    // TODO Add debug actions
    public void configure(PipelineHandler handler)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Pipeline input = handler.getInput();
        input.put(InputStage.PARSE_REQUEST, fabric.makeAction(InputStage.PARSE_REQUEST));
        input.put(InputStage.PARSE_REQUEST_BODY, new ParseRequestBodyAction());
        input.put(InputStage.PARSE_REQUEST_COOKIES, fabric.makeAction(InputStage.PARSE_REQUEST_COOKIES));
        Pipeline output = handler.getOutput();
        output.put(OutputStage.PROCESS_HEADERS, fabric.makeAction(OutputStage.PROCESS_HEADERS));
        output.put(OutputStage.PROCESS_BODY, fabric.makeAction(OutputStage.PROCESS_BODY));
    }
}
