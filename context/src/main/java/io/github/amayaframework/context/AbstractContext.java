package io.github.amayaframework.context;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.util.Map;

public abstract class AbstractContext<RQ extends ServletRequest, RP extends ServletResponse> implements Context {
    protected final RQ originalRequest;
    protected final RP originalResponse;
    protected final RequestAttributeMap attributes;

    protected AbstractContext(RQ originalRequest, RP originalResponse) {
        this.originalRequest = originalRequest;
        this.originalResponse = originalResponse;
        this.attributes = new RequestAttributeMap(originalRequest);
    }

    @Override
    public RQ originalRequest() {
        return originalRequest;
    }

    @Override
    public RP originalResponse() {
        return originalResponse;
    }

    @Override
    public Map<String, Object> attributes() {
        return attributes;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <V> V get(String key) {
        return (V) originalRequest.getAttribute(key);
    }

    @Override
    public void set(String key, Object value) {
        originalRequest.setAttribute(key, value);
    }

    @Override
    public Object remove(String key) {
        var ret = originalRequest.getAttribute(key);
        originalRequest.removeAttribute(key);
        return ret;
    }

    @Override
    public boolean contains(String key) {
        return originalRequest.getAttribute(key) != null;
    }
}
