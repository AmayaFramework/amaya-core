package io.github.amayaframework.core.pipelines;

import com.github.romanqed.jutils.http.HttpCode;
import io.github.amayaframework.core.contexts.HttpResponse;

/**
 * <p>Action that transfers control to the controller.</p>
 * <p>Receives: {@link RequestData}</p>
 * <p>Returns: {@link HttpResponse}</p>
 */
public class InvokeControllerAction extends PipelineAction<RequestData, HttpResponse> {
    @Override
    public HttpResponse execute(RequestData requestData) throws Exception {
        HttpResponse ret = requestData.getRoute().getBody().execute(requestData.getRequest());
        if (ret == null) {
            reject(HttpCode.INTERNAL_SERVER_ERROR);
        }
        return ret;
    }
}
