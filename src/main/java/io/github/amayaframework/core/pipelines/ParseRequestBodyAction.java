package io.github.amayaframework.core.pipelines;

import io.github.amayaframework.core.contexts.ContentType;
import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.util.IOUtil;

import java.io.InputStream;

/**
 * <p>An input action which outputs information about the input pipeline result.</p>
 * <p>Receives: {@link RequestData}</p>
 * <p>Returns: {@link RequestData}</p>
 */
public class ParseRequestBodyAction extends PipelineAction<RequestData, RequestData> {

    @Override
    public RequestData apply(RequestData requestData) {
        HttpRequest request = requestData.getRequest();
        InputStream bodyStream = requestData.getInputStream();
        String rawType = requestData.getContentType();
        if (rawType == null) {
            request.setBody(bodyStream);
            return requestData;
        }
        ContentType type = ContentType.fromHeader(rawType);
        if (type == null) {
            request.setBody(bodyStream);
            return requestData;
        } else if (!type.isString()) {
            request.setBody(bodyStream);
            request.setContentType(type);
            return requestData;
        }
        request.setContentType(type);
        request.setBody(IOUtil.readInputStream(bodyStream, requestData.getCharset()));
        return requestData;
    }
}
