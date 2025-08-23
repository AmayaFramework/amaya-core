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
    protected final RQ servletRequest;

    /**
     * The original, unwrapped servlet response instance provided by the servlet container.
     */
    protected final RP servletResponse;

    /**
     * The attribute map bound to the original servlet request.
     */
    protected RequestAttributeMap attributes;

    /**
     * Constructs a new instance of {@code AbstractContext} with the given original request and response.
     *
     * @param servletRequest  the original servlet request provided by the servlet container
     * @param servletResponse the original servlet response provided by the servlet container
     */
    protected AbstractContext(RQ servletRequest, RP servletResponse) {
        this.servletRequest = servletRequest;
        this.servletResponse = servletResponse;
    }

    @Override
    public RQ servletRequest() {
        return servletRequest;
    }

    @Override
    public RP servletResponse() {
        return servletResponse;
    }

    @Override
    public Map<String, Object> attributes() {
        if (attributes == null) {
            attributes = new RequestAttributeMap(servletRequest);
        }
        return attributes;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <V> V get(String key) {
        return (V) servletRequest.getAttribute(key);
    }

    @Override
    public void set(String key, Object value) {
        servletRequest.setAttribute(key, value);
    }

    @Override
    public Object remove(String key) {
        var ret = servletRequest.getAttribute(key);
        servletRequest.removeAttribute(key);
        return ret;
    }

    @Override
    public boolean contains(String key) {
        return servletRequest.getAttribute(key) != null;
    }
}
