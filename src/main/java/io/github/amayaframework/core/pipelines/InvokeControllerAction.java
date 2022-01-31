package io.github.amayaframework.core.pipelines;

import io.github.amayaframework.core.contexts.HttpResponse;

/**
 * <p>Input action that transfers control to the controller.</p>
 * <p>Receives: {@link RequestData}</p>
 * <p>Returns: {@link HttpResponse}</p>
 */
public class InvokeControllerAction extends PipelineAction<RequestData, HttpResponse> {
    @Override
    public HttpResponse apply(RequestData requestData) {
        return requestData.getRoute().getBody().apply(requestData.getRequest());
    }
}
