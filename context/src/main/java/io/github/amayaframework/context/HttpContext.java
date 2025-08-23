package io.github.amayaframework.context;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * An interface that describes the abstract context of http request processing.
 * Contains both a servlet request and response, and {@link HttpServletRequest} and {@link HttpServletResponse}.
 * {@link HttpRequest} and {@link HttpResponse} act as higher-level wrappers over servlet context.
 */
public interface HttpContext extends Context {

    /**
     * Gets the {@link HttpRequest} instance representing the current http request.
     *
     * @return the {@link HttpRequest} instance
     */
    @Override
    HttpRequest request();

    /**
     * Returns the original, unwrapped {@link HttpServletRequest} instance as provided by the servlet container.
     * This is the raw request object, without any modifications or wrappers applied by the framework.
     * Use this method only if low-level access to the servlet API is required.
     *
     * @return the original {@link HttpServletRequest} instance
     */
    @Override
    HttpServletRequest servletRequest();

    /**
     * Gets the {@link HttpResponse} instance representing the current http response.
     *
     * @return the {@link HttpResponse} instance
     */
    @Override
    HttpResponse response();

    /**
     * Returns the original, unwrapped {@link HttpServletResponse} instance as provided by the servlet container.
     * This is the raw response object, without any modifications or wrappers applied by the framework.
     * Use this method only if low-level access to the servlet API is required.
     *
     * @return the original {@link HttpServletResponse} instance
     */
    @Override
    HttpServletResponse servletResponse();
}
