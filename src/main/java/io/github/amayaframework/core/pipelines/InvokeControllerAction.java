package io.github.amayaframework.core.pipelines;

import io.github.amayaframework.core.contexts.HttpResponse;

/**
 * <p>Input action which calls found controller route</p>
 * <p>Receives: {@link RequestData}</p>
 * <p>Returns: {@link HttpResponse}</p>
 */
public class InvokeControllerAction extends InputAction<RequestData, HttpResponse> {
    @Override
    public HttpResponse execute(RequestData requestData) throws Exception {
        return requestData.getRoute().getBody().execute(requestData.getRequest());
    }
}