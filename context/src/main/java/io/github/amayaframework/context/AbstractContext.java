package io.github.amayaframework.context;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.util.Map;

/**
 * A skeletal implementation of the {@link Context} interface, providing base functionality
 * for working with request and response attributes.
 *
 * @param <RQ> the type of the servlet request
 * @param <RP> the type of the servlet response
 */
public abstract class AbstractContext<RQ extends ServletRequest, RP extends ServletResponse> implements Context {

    /**
     * The original, unwrapped servlet request instance provided by the servlet container.
     */
    protected final RQ originalRequest;

    /**
     * The original, unwrapped servlet response instance provided by the servlet container.
     */
    protected final RP originalResponse;

    /**
     * The attribute map bound to the original servlet request.
     */
    protected final RequestAttributeMap attributes;

    /**
     * Constructs a new instance of {@code AbstractContext} with the given original request and response.
     *
     * @param originalRequest  the original servlet request provided by the servlet container
     * @param originalResponse the original servlet response provided by the servlet container
     */
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
