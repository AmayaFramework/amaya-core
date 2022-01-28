package io.github.amayaframework.core.pipelines;

import com.github.romanqed.jutils.http.HttpCode;
import com.github.romanqed.jutils.structs.pipeline.PipelineResult;
import io.github.amayaframework.core.contexts.HttpResponse;

/**
 * <p>The output action, during which the result of the input pipeline is checked
 * for the exceptions and the correctness of the type.</p>
 * <p>Receives: {@link PipelineResult}</p>
 * <p>Returns: {@link HttpResponse}</p>
 */
public class CheckResponseAction extends PipelineAction<PipelineResult, HttpResponse> {
    @Override
    public HttpResponse apply(PipelineResult result) {
        if (result.getException() != null) {
            reject(HttpCode.INTERNAL_SERVER_ERROR);
        }
        try {
            return (HttpResponse) result.getResult();
        } catch (ClassCastException e) {
            reject(HttpCode.INTERNAL_SERVER_ERROR);
            return null;
        }
    }
}
