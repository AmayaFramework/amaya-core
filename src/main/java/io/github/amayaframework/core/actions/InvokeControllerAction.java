package io.github.amayaframework.core.actions;

import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.pipeline.InputAction;
import io.github.amayaframework.core.pipeline.RequestData;

/**
 * <p>Input action which calls found controller route</p>
 * <p>Receives: {@link RequestData}</p>
 * <p>Returns: {@link HttpResponse}</p>
 */
public class InvokeControllerAction extends InputAction<RequestData, HttpResponse> {
    @Override
    public HttpResponse execute(RequestData requestData) throws Throwable {
        return requestData.getRoute().getBody().execute(requestData.getRequest());
    }
}
