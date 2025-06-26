package io.github.amayaframework.context;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

/**
 * An interface that describes the abstract context of request processing.
 * Contains both a servlet request and response, and {@link Request} and {@link Response}.
 * {@link Request} and {@link Response} act as higher-level wrappers over servlet context.
 */
public interface Context {

    /**
     * Gets the {@link Request} instance representing the current request.
     * Changes made to {@link Request} reflects on {@link ServletRequest} and vice versa.
     *
     * @return the {@link Request} instance
     */
    Request getRequest();

    /**
     * Gets the {@link ServletRequest} instance representing the current request at the servlet level.
     * Changes made to {@link ServletRequest} reflects on {@link Request} and vice versa.
     *
     * @return the {@link ServletRequest} instance
     */
    ServletRequest getServletRequest();

    /**
     * Returns the original, unwrapped {@link ServletRequest} instance as provided by the servlet container.
     * This is the raw request object, without any modifications or wrappers applied by the framework.
     * Use this method only if low-level access to the servlet API is required.
     *
     * @return the original {@link ServletRequest} instance
     */
    ServletRequest getOriginalRequest();

    /**
     * Gets the {@link Response} instance representing the current response.
     * Changes made to {@link Response} reflects on {@link ServletResponse} and vice versa.
     *
     * @return the {@link Response} instance
     */
    Response getResponse();

    /**
     * Gets the {@link ServletResponse} instance representing the current response at the servlet level.
     * Changes made to {@link ServletResponse} reflects on {@link Response} and vice versa.
     *
     * @return the {@link ServletResponse} instance
     */
    ServletResponse getServletResponse();

    /**
     * Returns the original, unwrapped {@link ServletResponse} instance as provided by the servlet container.
     * This is the raw response object, without any modifications or wrappers applied by the framework.
     * Use this method only if low-level access to the servlet API is required.
     *
     * @return the original {@link ServletResponse} instance
     */
    ServletResponse getOriginalResponse();
}
