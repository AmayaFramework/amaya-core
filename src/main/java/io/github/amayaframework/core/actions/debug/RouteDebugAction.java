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
public class RouteDebugAction extends PipelineAction<RequestData, RequestData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteDebugAction.class);

    @Override
    public RequestData execute(RequestData requestData) {
        String message = "Route found successfully\n" +
                "Method: " + requestData.getMethod() + '\n' +
                "Route: " + requestData.getRoute().route() + '\n' +
                "Path: " + requestData.getPath() + '\n';
        LOGGER.debug(message);
        return requestData;
    }
}
