package io.github.amayaframework.core.configurators;

import com.github.romanqed.jutils.structs.pipeline.Pipeline;
import io.github.amayaframework.core.pipelines.Stage;
import io.github.amayaframework.core.pipelines.debug.*;

public abstract class AbstractConfigurator implements Configurator {
    protected void addDebugActions(Pipeline input, Pipeline output) {
        input.insertAfter(Stage.FIND_ROUTE.name(), DebugStage.ROUTE_DEBUG.name(), new RouteDebugAction());
        input.insertAfter(Stage.PARSE_REQUEST.name(), DebugStage.REQUEST_DEBUG.name(), new RequestDebugAction());
        input.insertAfter(Stage.INVOKE_CONTROLLER.name(), DebugStage.RESPONSE_DEBUG.name(), new ResponseDebugAction());
        output.insertBefore(Stage.CHECK_RESPONSE.name(), DebugStage.INPUT_RESULT_DEBUG.name(),
                new InputResultDebugAction());
    }
}
