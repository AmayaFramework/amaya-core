package io.github.amayaframework.server;

import io.github.amayaframework.http.HttpMethod;

/**
 * Lookup interface for resolving {@link HttpMethod} instances
 * by their string representation.
 * <p>
 * Implementations are typically lightweight buffers or maps
 * pre-initialized at framework startup, allowing fast
 * translation of servlet method names into framework-level
 * {@link HttpMethod} objects during request processing.
 */
public interface HttpMethodBuffer {

    /**
     * Resolve an {@link HttpMethod} instance for the given name.
     *
     * @param method the HTTP method name (e.g. {@code "GET"}, {@code "POST"})
     * @return the corresponding {@link HttpMethod}, or {@code null}
     *         if the method is not recognized
     */
    HttpMethod get(String method);
}
