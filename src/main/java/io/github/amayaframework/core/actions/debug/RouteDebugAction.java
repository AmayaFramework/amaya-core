package io.github.amayaframework.core.actions.debug;

import io.github.amayaframework.core.pipeline.PipelineAction;
import io.github.amayaframework.core.pipeline.RequestData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>An input action which outputs information about the found route.</p>
 * <p>Receives: {@link RequestData}</p>
 * <p>Returns: {@link RequestData}</p>
 */
public final class RouteDebugAction extends PipelineAction<RequestData, RequestData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteDebugAction.class);

    @Override
    public RequestData execute(RequestData data) {
        String message = "Route found successfully\n" +
                "Method: " + data.getMethod() + '\n' +
                "Route: " + data.getRoute().route() + '\n' +
                "Path: " + data.getPath() + '\n';
        LOGGER.debug(message);
        return data;
    }
}
