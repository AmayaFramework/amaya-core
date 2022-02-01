package io.github.amayaframework.core.configurators;

import com.github.romanqed.jutils.pipeline.Pipeline;
import io.github.amayaframework.core.handlers.IOHandler;
import io.github.amayaframework.core.pipelines.Stage;
import io.github.amayaframework.core.pipelines.debug.DebugStage;
import io.github.amayaframework.core.pipelines.debug.RequestDebugAction;
import io.github.amayaframework.core.pipelines.debug.ResponseDebugAction;
import io.github.amayaframework.core.pipelines.debug.RouteDebugAction;
import io.github.amayaframework.core.util.AmayaConfig;

import java.util.Objects;

public abstract class AbstractConfigurator implements Configurator {
    @Override
    public void configure(IOHandler configurable) {
        Objects.requireNonNull(configurable);
        accept(configurable);
        if (AmayaConfig.INSTANCE.getDebug()) {
            addDebugActions(configurable.getPipeline());
        }
    }

    protected void addDebugActions(Pipeline pipeline) {
        pipeline.insertAfter(Stage.FIND_ROUTE.name(), DebugStage.ROUTE_DEBUG.name(), new RouteDebugAction());
        pipeline.insertAfter(Stage.PARSE_REQUEST.name(), DebugStage.REQUEST_DEBUG.name(), new RequestDebugAction());
        pipeline.insertAfter(Stage.INVOKE_CONTROLLER.name(), DebugStage.RESPONSE_DEBUG.name(),
                new ResponseDebugAction());
    }
}
