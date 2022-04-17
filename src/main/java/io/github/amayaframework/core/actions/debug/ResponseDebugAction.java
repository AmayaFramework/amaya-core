package io.github.amayaframework.core.actions.debug;

import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.pipeline.PipelineAction;
import io.github.amayaframework.core.pipeline.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>An output action which outputs information about the response received.</p>
 * <p>Receives: {@link HttpResponse}</p>
 * <p>Returns: {@link HttpResponse}</p>
 */
public class ResponseDebugAction extends PipelineAction<ResponseData, ResponseData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseDebugAction.class);

    @Override
    public ResponseData execute(ResponseData responseData) {
        HttpResponse response = responseData.getResponse();
        String message = "HttpResponse was received successfully\n" +
                "Implementation used: " + (response != null ? response.getClass().getSimpleName() : null) + "\n";
        if (response != null) {
            message += "Code: " + response.getCode() + "\n" +
                    "Body: " + response.getBody() + "\n" +
                    "Headers: " + response.getHeaderMap() + "\n" +
                    "Cookies: " + response.getCookies() + "\n";
        }
        LOGGER.debug(message);
        return responseData;
    }
}
