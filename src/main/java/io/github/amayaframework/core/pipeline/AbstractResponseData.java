package io.github.amayaframework.core.pipeline;

import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.util.CompletableAttachable;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractResponseData extends CompletableAttachable implements ResponseData {
    private HttpResponse response;

    protected AbstractResponseData(HttpResponse response) {
        super(new ConcurrentHashMap<>());
        this.response = response;
    }

    @Override
    public void complete() {
        super.complete();
        this.response = new UnmodifiableResponse(response);
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
