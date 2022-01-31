package io.github.amayaframework.core.pipelines;

import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.methods.HttpMethod;
import io.github.amayaframework.core.routes.MethodRoute;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Objects;

/**
 * A simple container created to transfer data between pipeline actions.
 * Common form.
 */
public abstract class RequestData {
    private MethodRoute route;
    private String path;
    private HttpMethod method;
    private HttpRequest request;

    protected RequestData(MethodRoute route, String path, HttpMethod method) {
        this.route = route;
        this.path = path;
        this.method = method;
    }

    public MethodRoute getRoute() {
        return route;
    }

    public void setRoute(MethodRoute route) {
        this.route = Objects.requireNonNull(route);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = Objects.requireNonNull(path);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = Objects.requireNonNull(method);
    }

    public HttpRequest getRequest() {
        return request;
    }

    public void setRequest(HttpRequest request) {
        this.request = Objects.requireNonNull(request);
    }

    public abstract InputStream getInputStream();

    public abstract String getContentType();

    public abstract Charset getCharset();
}
