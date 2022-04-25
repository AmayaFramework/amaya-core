package io.github.amayaframework.core.pipeline;

import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.util.AbstractCompletableData;

import java.util.Objects;

public abstract class AbstractResponseData extends AbstractCompletableData implements ResponseData {
    private HttpResponse response;

    protected AbstractResponseData(HttpResponse response) {
        this.response = response;
    }

    @Override
    public void complete() {
        this.response = new UnmodifiableResponse(response);
        this.completed = true;
    }

    @Override
    public HttpResponse getResponse() {
        return response;
    }

    @Override
    public void setResponse(HttpResponse response) {
        checkCompleted();
        this.response = Objects.requireNonNull(response);
    }
}
