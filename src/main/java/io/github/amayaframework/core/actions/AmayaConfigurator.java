package io.github.amayaframework.core.actions;

import com.github.romanqed.util.Handler;
import com.github.romanqed.util.pipeline.Pipeline;
import io.github.amayaframework.core.actions.debug.DebugStage;
import io.github.amayaframework.core.actions.debug.RequestDebugAction;
import io.github.amayaframework.core.actions.debug.ResponseDebugAction;
import io.github.amayaframework.core.actions.debug.RouteDebugAction;
import io.github.amayaframework.core.config.AmayaConfig;
import io.github.amayaframework.core.handlers.PipelineHandler;

public final class AmayaConfigurator implements Handler<PipelineHandler> {
    private final ActionFactory fabric;
    private final boolean isDebug;

    public AmayaConfigurator(String prefix, AmayaConfig config) {
        this.fabric = new ActionFactory(prefix, config);
        this.isDebug = config.isDebug();
    }

    @Override
    public void handle(PipelineHandler handler) throws Throwable {
        Pipeline<String> input = handler.getInput();
        input.put(InputStage.PARSE_REQUEST, fabric.makeAction(InputStage.PARSE_REQUEST));
        input.put(InputStage.PARSE_REQUEST_COOKIES, fabric.makeAction(InputStage.PARSE_REQUEST_COOKIES));
        input.put(InputStage.PARSE_REQUEST_BODY, new ParseRequestBodyAction());
        input.put(InputStage.INVOKE_CONTROLLER, new InvokeControllerAction());
        Pipeline<String> output = handler.getOutput();
        output.put(OutputStage.PROCESS_HEADERS, fabric.makeAction(OutputStage.PROCESS_HEADERS));
        output.put(OutputStage.PROCESS_BODY, fabric.makeAction(OutputStage.PROCESS_BODY));
        if (isDebug) {
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
