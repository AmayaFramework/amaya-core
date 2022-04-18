package io.github.amayaframework.core.pipeline;

import io.github.amayaframework.core.contexts.HttpResponse;

/**
 * A simple container created to transfer data between pipeline actions.
 * Common form.
 */
public interface ResponseData {
    HttpResponse getResponse();

    void setResponse(HttpResponse response);
}
