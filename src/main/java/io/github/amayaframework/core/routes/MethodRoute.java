package io.github.amayaframework.core.routes;

import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.util.Attachable;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class MethodRoute extends Route implements Attachable {
    private final Method method;
    private final Function<HttpRequest, HttpResponse> body;
    private final Map<String, Object> attachments;

    public MethodRoute(String route, Method method, Function<HttpRequest, HttpResponse> body) {
        super(route);
        this.method = Objects.requireNonNull(method);
        this.body = Objects.requireNonNull(body);
        this.attachments = new ConcurrentHashMap<>();
    }

    public Method getMethod() {
        return method;
    }

    public Function<HttpRequest, HttpResponse> getBody() {
        return body;
    }

    @Override
    public Map<String, Object> getAttachments() {
        return attachments;
    }

    @Override
    public Object getAttachment(String key) {
        return attachments.get(key);
    }

    @Override
    public void setAttachment(String key, Object value) {
        attachments.put(key, value);
    }
}
