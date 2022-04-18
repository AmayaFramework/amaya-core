package io.github.amayaframework.core.pipeline;

import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.methods.HttpMethod;
import io.github.amayaframework.core.routes.MethodRoute;

import java.util.Objects;

public abstract class AbstractRequestData extends Data implements RequestData {
    private final MethodRoute route;
    private final String path;
    private final HttpMethod method;
    private HttpRequest request;

    protected AbstractRequestData(HttpMethod method, String path, MethodRoute route) {
        this.route = Objects.requireNonNull(route);
        this.path = Objects.requireNonNull(path);
        this.method = Objects.requireNonNull(method);
    }

    @Override
    public void complete() {
        request = new UnmodifiableRequest(request);
        completed = true;
    }

    @Override
    public MethodRoute getRoute() {
        return route;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public HttpMethod getMethod() {
        return method;
    }

    public HttpRequest getRequest() {
        return request;
    }

    public void setRequest(HttpRequest request) {
        checkCompleted();
        this.request = Objects.requireNonNull(request);
    }
}
