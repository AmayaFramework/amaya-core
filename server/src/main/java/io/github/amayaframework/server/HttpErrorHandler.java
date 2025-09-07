package io.github.amayaframework.server;

import io.github.amayaframework.http.HttpCode;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * A strategy interface for handling HTTP errors.
 * <p>
 * Implementations define how to respond to error conditions, such as sending
 * a plain-text error, rendering an HTML error page, or logging diagnostic
 * information. This decouples error generation from the response object,
 * making it configurable and reusable across different server implementations.
 */
public interface HttpErrorHandler {

    /**
     * Handles an HTTP error by writing an appropriate response.
     *
     * @param response the underlying {@link HttpServletResponse} to write to, never {@code null}
     * @param code     the {@link HttpCode} representing the error status, never {@code null}
     * @param message  an optional error message, may be {@code null}
     * @throws IOException if an I/O error occurs while writing the error response
     */
    void handle(HttpServletResponse response, HttpCode code, String message) throws IOException;
}
