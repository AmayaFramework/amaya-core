package io.github.amayaframework.core.configurators;

import com.github.romanqed.jutils.pipeline.Pipeline;
import com.github.romanqed.jutils.util.Handler;
import io.github.amayaframework.core.ConfigProvider;
import io.github.amayaframework.core.actions.InputStage;
import io.github.amayaframework.core.actions.InvokeControllerAction;
import io.github.amayaframework.core.actions.OutputStage;
import io.github.amayaframework.core.actions.ParseRequestBodyAction;
import io.github.amayaframework.core.actions.debug.DebugStage;
import io.github.amayaframework.core.actions.debug.RequestDebugAction;
import io.github.amayaframework.core.actions.debug.ResponseDebugAction;
import io.github.amayaframework.core.actions.debug.RouteDebugAction;
import io.github.amayaframework.core.handlers.PipelineHandler;

public final class AmayaConfigurator implements Handler<PipelineHandler> {
    private final ActionFabric fabric;

    public AmayaConfigurator(String prefix) {
        this.fabric = new ActionFabric(prefix);
    }

    @Override
    public void handle(PipelineHandler handler)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Pipeline<String> input = handler.getInput();
        input.put(InputStage.PARSE_REQUEST, fabric.makeAction(InputStage.PARSE_REQUEST));
        input.put(InputStage.PARSE_REQUEST_COOKIES, fabric.makeAction(InputStage.PARSE_REQUEST_COOKIES));
        input.put(InputStage.PARSE_REQUEST_BODY, new ParseRequestBodyAction());
        input.put(InputStage.INVOKE_CONTROLLER, new InvokeControllerAction());
        Pipeline<String> output = handler.getOutput();
        output.put(OutputStage.PROCESS_HEADERS, fabric.makeAction(OutputStage.PROCESS_HEADERS));
        output.put(OutputStage.PROCESS_BODY, fabric.makeAction(OutputStage.PROCESS_BODY));
        if (ConfigProvider.getAmayaConfig().isDebug()) {
            addInputDebugActions(input);
            addOutputDebugActions(output);
        }
    }

    private void addInputDebugActions(Pipeline<String> input) {
        input.insertBefore(InputStage.PARSE_REQUEST, DebugStage.ROUTE_DEBUG, new RouteDebugAction());
        input.insertAfter(InputStage.PARSE_REQUEST, DebugStage.REQUEST_DEBUG, new RequestDebugAction());
    }

    private void addOutputDebugActions(Pipeline<String> output) {
        output.insertBefore(OutputStage.PROCESS_HEADERS, DebugStage.RESPONSE_DEBUG, new ResponseDebugAction());
    }
}
