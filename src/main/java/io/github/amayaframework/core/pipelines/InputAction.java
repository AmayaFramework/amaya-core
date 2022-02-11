package io.github.amayaframework.core.pipelines;

import com.github.romanqed.jutils.http.HttpCode;
import io.github.amayaframework.core.contexts.Responses;

public abstract class InputAction<T, R> extends PipelineAction<T, R> {
    /**
     * <p>A method that allows you to interrupt the pipeline by returning an HttpResponse with the specified code.</p>
     * <p>Note: Java does not recognize it as return or throw, so in some cases it is necessary to make an
     * additional return after the call, but this code will never be executed, since execution will
     * terminate immediately after calling this method.</p>
     *
     * @param code {@link HttpCode} code to be returned
     */
    protected void reject(HttpCode code) {
        interrupt(Responses.responseWithCode(code, code.getMessage()));
    }
}
