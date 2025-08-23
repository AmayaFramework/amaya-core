package io.github.amayaframework.context;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

/**
 * An interface that describes the abstract context of request processing.
 * Contains both a servlet request and response, and {@link Request} and {@link Response}.
 * {@link Request} and {@link Response} act as higher-level wrappers over servlet context.
 */
public interface Context extends Attributable<String> {

    /**
     * Gets the {@link Request} instance representing the current request.
     *
     * @return the {@link Request} instance
     */
    Request request();

    /**
     * Returns the original, unwrapped {@link ServletRequest} instance as provided by the servlet container.
     * This is the raw request object, without any modifications or wrappers applied by the framework.
     * Use this method only if low-level access to the servlet API is required.
     *
     * @return the original {@link ServletRequest} instance
     */
    ServletRequest servletRequest();

    /**
     * Gets the {@link Response} instance representing the current response.
     *
     * @return the {@link Response} instance
     */
    Response response();

    /**
     * Returns the original, unwrapped {@link ServletResponse} instance as provided by the servlet container.
     * This is the raw response object, without any modifications or wrappers applied by the framework.
     * Use this method only if low-level access to the servlet API is required.
     *
     * @return the original {@link ServletResponse} instance
     */
    ServletResponse servletResponse();
}
