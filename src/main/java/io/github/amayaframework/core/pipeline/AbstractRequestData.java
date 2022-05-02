package io.github.amayaframework.core.pipeline;

import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.methods.HttpMethod;
import io.github.amayaframework.core.routes.MethodRoute;

import java.util.Objects;

public abstract class AbstractRequestData implements RequestData {
    private final RouteData data;
    private HttpRequest request;

    protected AbstractRequestData(RouteData data) {
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
