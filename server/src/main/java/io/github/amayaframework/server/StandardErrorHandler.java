package io.github.amayaframework.server;

import io.github.amayaframework.http.HttpCode;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Default implementation of {@link HttpErrorHandler}.
 * <p>
 * This handler delegates error generation directly to
 * {@link HttpServletResponse#sendError(int, String)}. It produces
 * container-specific error pages or default error messages.
 * <p>
 * Suitable as a simple fallback handler, though custom implementations
 * may be provided to support JSON errors, HTML templates, or logging.
 */
public final class StandardErrorHandler implements HttpErrorHandler {

    /**
     * Sends an error response using the standard servlet mechanism.
     *
     * @param response the servlet response, must not be {@code null}
     * @param code     the HTTP status code, never {@code null}
     * @param message  the optional error message, may be {@code null}
     * @throws IOException if writing the error response fails
     */
    @Override
    public void handle(HttpServletResponse response, HttpCode code, String message) throws IOException {
        if (message == null) {
            response.sendError(code.getCode());
        } else {
            response.sendError(code.getCode(), message);
        }
    }
}
