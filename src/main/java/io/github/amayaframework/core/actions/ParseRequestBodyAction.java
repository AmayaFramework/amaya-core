package io.github.amayaframework.core.actions;

import com.github.romanqed.util.IOUtil;
import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.pipeline.PipelineAction;
import io.github.amayaframework.core.pipeline.RequestData;
import io.github.amayaframework.http.ContentType;

import java.io.InputStream;

/**
 * <p>Input action which parse request body action.</p>
 * <p>Receives: {@link RequestData}</p>
 * <p>Returns: {@link RequestData}</p>
 */
public final class ParseRequestBodyAction extends PipelineAction<RequestData, RequestData> {

    @Override
    public RequestData execute(RequestData data) {
        HttpRequest request = data.getRequest();
        InputStream bodyStream = data.getInputStream();
        // Skipping body processing if the request doesn't have one
        if (!data.getMethod().isHasBody()) {
            request.setBody(bodyStream);
            return data;
        }
        // Process request body
        String rawType = data.getContentType();
        // If there is no content type header, skip body processing
        if (rawType == null) {
            request.setBody(bodyStream);
            return data;
        }
        ContentType type = ContentType.fromHeader(rawType);
        // If the content-header is unknown, skip body processing
        if (type == null) {
            request.setBody(bodyStream);
            return data;
        }
        if (!type.isString()) {
            request.setBody(bodyStream);
            request.setContentType(type);
            return data;
        }
        request.setContentType(type);
        request.setBody(IOUtil.readInputStream(bodyStream, data.getCharset()));
        return data;
    }
}
