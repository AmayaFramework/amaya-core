package io.github.amayaframework.core.pipeline;

import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.methods.HttpMethod;
import io.github.amayaframework.core.routes.MethodRoute;
import io.github.amayaframework.core.util.CompletableAttachable;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractRequestData extends CompletableAttachable implements RequestData {
    private final RouteData data;
    private HttpRequest request;

    protected AbstractRequestData(RouteData data) {
        super(new ConcurrentHashMap<>());
        this.data = data;
    }

    @Override
    public MethodRoute getRoute() {
        return data.route;
    }

    @Override
    public String getPath() {
        return data.path;
    }

    @Override
    public HttpMethod getMethod() {
        return data.method;
    }

    public HttpRequest getRequest() {
        return request;
    }

    public void setRequest(HttpRequest request) {
        this.request = Objects.requireNonNull(request);
    }
}
