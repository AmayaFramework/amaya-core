package io.github.amayaframework.core.pipelines.debug;

import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.pipelines.PipelineAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>An action which outputs information about the response received.</p>
 * <p>Receives: {@link HttpResponse}</p>
 * <p>Returns: {@link HttpResponse}</p>
 */
public class ResponseDebugAction extends PipelineAction<HttpResponse, HttpResponse> {
    private static final Logger logger = LoggerFactory.getLogger(ResponseDebugAction.class);

    @Override
    public HttpResponse execute(HttpResponse response) {
        String message = "HttpResponse was received successfully\n" +
                "Implementation used: " + (response != null ? response.getClass().getSimpleName() : null) + "\n";
        if (response != null) {
            message += "Code: " + response.getCode() + "\n" +
                    "Body: " + response.getBody() + "\n" +
                    "Headers: " + response.getHeaderMap() + "\n" +
                    "Cookies: " + response.getCookies() + "\n";
        }
        logger.debug(message);
        return response;
    }
}
