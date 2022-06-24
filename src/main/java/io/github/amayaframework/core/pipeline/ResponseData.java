package io.github.amayaframework.core.pipeline;

import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.util.Attachable;
import io.github.amayaframework.core.util.CompletableData;

/**
 * A container for transferring data between the actions of the output pipeline.
 */
public interface ResponseData extends Attachable, CompletableData {
    /**
     * @return the http response instance sent by the user from the controller.
     */
    HttpResponse getResponse();

    /**
     * Sets a new http response instance.
     *
     * @param response to be set, must be not null
     */
    void setResponse(HttpResponse response);
}
