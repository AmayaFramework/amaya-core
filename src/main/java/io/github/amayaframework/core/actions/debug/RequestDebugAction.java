package io.github.amayaframework.core.actions.debug;

import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.pipeline.PipelineAction;
import io.github.amayaframework.core.pipeline.RequestData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>An input action which outputs information about the parsed request.</p>
 * <p>Receives: {@link RequestData}</p>
 * <p>Returns: {@link RequestData}</p>
 */
public final class RequestDebugAction extends PipelineAction<RequestData, RequestData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestDebugAction.class);

    @Override
    public RequestData execute(RequestData requestData) {
        HttpRequest request = requestData.getRequest();
        String message = "The request was parsed successfully\n" +
                "Implementation used: " + request.getClass().getSimpleName() + "\n" +
                "Parsed path parameters: " + request.getPathParameters() + "\n" +
                "Parsed query parameters: " + request.getQuery() + "\n";
        LOGGER.debug(message);
        return requestData;
    }
}
