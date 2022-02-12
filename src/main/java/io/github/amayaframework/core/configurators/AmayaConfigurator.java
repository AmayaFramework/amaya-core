package io.github.amayaframework.core.configurators;

import com.github.romanqed.jutils.pipeline.Pipeline;
import io.github.amayaframework.core.config.ConfigProvider;
import io.github.amayaframework.core.handlers.PipelineHandler;
import io.github.amayaframework.core.pipelines.InputStage;
import io.github.amayaframework.core.pipelines.OutputStage;
import io.github.amayaframework.core.pipelines.ParseRequestBodyAction;
import io.github.amayaframework.core.pipelines.debug.DebugStage;
import io.github.amayaframework.core.pipelines.debug.RequestDebugAction;
import io.github.amayaframework.core.pipelines.debug.ResponseDebugAction;
import io.github.amayaframework.core.pipelines.debug.RouteDebugAction;

public class AmayaConfigurator implements PipelineConfigurator {
    private final ActionFabric fabric;

    public AmayaConfigurator(String prefix) {
        this.fabric = new ActionFabric(prefix);
    }

    public AmayaConfigurator() {
        this.fabric = new ActionFabric();
    }

    @Override
    public void configure(PipelineHandler handler)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Pipeline input = handler.getInput();
        input.put(InputStage.PARSE_REQUEST, fabric.makeAction(InputStage.PARSE_REQUEST));
        input.put(InputStage.PARSE_REQUEST_BODY, new ParseRequestBodyAction());
        input.put(InputStage.PARSE_REQUEST_COOKIES, fabric.makeAction(InputStage.PARSE_REQUEST_COOKIES));
        Pipeline output = handler.getOutput();
        output.put(OutputStage.PROCESS_HEADERS, fabric.makeAction(OutputStage.PROCESS_HEADERS));
        output.put(OutputStage.PROCESS_BODY, fabric.makeAction(OutputStage.PROCESS_BODY));
        if (ConfigProvider.getConfig().isDebug()) {
            addInputDebugActions(input);
            addOutputDebugActions(output);
        }
    }

    protected void addInputDebugActions(Pipeline input) {
        input.insertBefore(InputStage.PARSE_REQUEST, DebugStage.ROUTE_DEBUG, new RouteDebugAction());
        input.insertAfter(InputStage.PARSE_REQUEST, DebugStage.REQUEST_DEBUG, new RequestDebugAction());
    }

    private void addOutputDebugActions(Pipeline output) {
        output.insertBefore(OutputStage.PROCESS_HEADERS, DebugStage.RESPONSE_DEBUG, new ResponseDebugAction());
    }
}
