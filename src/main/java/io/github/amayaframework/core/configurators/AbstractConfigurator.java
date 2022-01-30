package io.github.amayaframework.core.configurators;

import com.github.romanqed.jutils.structs.pipeline.Pipeline;
import io.github.amayaframework.core.handlers.IOHandler;
import io.github.amayaframework.core.pipelines.Stage;
import io.github.amayaframework.core.pipelines.debug.*;
import io.github.amayaframework.core.util.AmayaConfig;

import java.util.Objects;

public abstract class AbstractConfigurator implements Configurator {
    @Override
    public void configure(IOHandler configurable) {
        Objects.requireNonNull(configurable);
        accept(configurable);
        if (AmayaConfig.INSTANCE.getDebug()) {
            addDebugActions(configurable.getInput(), configurable.getOutput());
        }
    }

    protected void addDebugActions(Pipeline input, Pipeline output) {
        input.insertAfter(Stage.FIND_ROUTE.name(), DebugStage.ROUTE_DEBUG.name(), new RouteDebugAction());
        input.insertAfter(Stage.PARSE_REQUEST.name(), DebugStage.REQUEST_DEBUG.name(), new RequestDebugAction());
        input.insertAfter(Stage.INVOKE_CONTROLLER.name(), DebugStage.RESPONSE_DEBUG.name(), new ResponseDebugAction());
        output.insertBefore(Stage.CHECK_RESPONSE.name(), DebugStage.INPUT_RESULT_DEBUG.name(),
                new InputResultDebugAction());
    }
}
