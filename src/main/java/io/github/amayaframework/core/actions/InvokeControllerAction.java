package io.github.amayaframework.core.actions;

import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.pipeline.InputAction;
import io.github.amayaframework.core.pipeline.RequestData;

/**
 * <p>Input action which calls found controller getRoute</p>
 * <p>Receives: {@link RequestData}</p>
 * <p>Returns: {@link HttpResponse}</p>
 */
public final class InvokeControllerAction extends InputAction<RequestData, HttpResponse> {
    @Override
    public HttpResponse execute(RequestData data) throws Throwable {
        return data.getRoute().execute(data.getRequest());
    }
}
