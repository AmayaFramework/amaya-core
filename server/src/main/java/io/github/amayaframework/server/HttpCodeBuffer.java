package io.github.amayaframework.server;

import io.github.amayaframework.http.HttpCode;

/**
 * Lookup interface for resolving {@link HttpCode} instances
 * by their numeric status code.
 * <p>
 * Implementations are typically lightweight buffers or maps
 * pre-initialized at framework startup, allowing fast
 * translation of servlet integer codes into framework-level
 * {@link HttpCode} objects during request/response processing.
 */
public interface HttpCodeBuffer {

    /**
     * Resolve an {@link HttpCode} instance for the given numeric code.
     *
     * @param code the numeric HTTP status code (e.g. 200, 404, 500)
     * @return the corresponding {@link HttpCode}, or {@code null}
     *         if the code is not recognized
     */
    HttpCode get(int code);
}
