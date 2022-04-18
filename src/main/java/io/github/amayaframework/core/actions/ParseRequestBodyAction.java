package io.github.amayaframework.core.actions;

import io.github.amayaframework.core.contexts.ContentType;
import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.pipeline.PipelineAction;
import io.github.amayaframework.core.pipeline.RequestData;
import io.github.amayaframework.core.util.IOUtil;

import java.io.InputStream;

/**
 * <p>Input action which parse request body action.</p>
 * <p>Receives: {@link RequestData}</p>
 * <p>Returns: {@link RequestData}</p>
 */
public final class ParseRequestBodyAction extends PipelineAction<RequestData, RequestData> {

    @Override
    public RequestData execute(RequestData requestData) {
        HttpRequest request = requestData.getRequest();
        InputStream bodyStream = requestData.getInputStream();
        // Skipping body processing if the request doesn't have one
        if (!requestData.getMethod().isHasBody()) {
            request.setBody(bodyStream);
            return requestData;
        }
        // Process request body
        String rawType = requestData.getContentType();
        // If there is no content type header, skip body processing
        if (rawType == null) {
            request.setBody(bodyStream);
            return requestData;
        }
        ContentType type = ContentType.fromHeader(rawType);
        // If the content-header is unknown, skip body processing
        if (type == null) {
            request.setBody(bodyStream);
            return requestData;
        }
        if (!type.isString()) {
            request.setBody(bodyStream);
            request.setContentType(type);
            return requestData;
        }
        request.setContentType(type);
        request.setBody(IOUtil.readInputStream(bodyStream, requestData.getCharset()));
        return requestData;
    }
}
