package io.github.amayaframework.server;

import io.github.amayaframework.context.AbstractHttpResponse;
import io.github.amayaframework.http.HttpCode;
import io.github.amayaframework.http.HttpVersion;
import io.github.amayaframework.http.MimeData;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet-backed implementation of {@link AbstractHttpResponse}.
 * <p>
 * Wraps a {@link HttpServletResponse} and provides efficient formatting
 * and parsing of MIME data and HTTP status codes using pluggable buffer,
 * parser, and formatter components.
 * <p>
 * This class is designed for high-load scenarios: it minimizes allocations
 * and leverages pre-initialized lookup buffers for fast status resolution.
 */
public class ServerHttpResponse extends AbstractHttpResponse {
    protected final HttpErrorHandler errorHandler;
    protected final MimeParser parser;
    protected final MimeFormatter formatter;
    protected final HttpCodeBuffer codeBuffer;

    /**
     * Creates a new {@code ServerHttpResponse}.
     *
     * @param response     the underlying servlet response, never {@code null}
     * @param errorHandler the error handler to delegate to when sending errors,
     *                     must not be {@code null}
     * @param parser       the parser for {@link MimeData}, used to interpret MIME strings,
     *                     must not be {@code null}
     * @param formatter    the formatter for {@link MimeData}, used to serialize MIME values,
     *                     must not be {@code null}
     * @param codeBuffer   the buffer for resolving {@link HttpCode} instances from raw codes,
     *                     must not be {@code null}
     * @param version      the HTTP version of the response, must not be {@code null}
     * @param protocol     the raw protocol string (e.g. {@code "HTTP/1.1"}), must not be {@code null}
     * @param scheme       the request scheme (e.g. {@code "http"} or {@code "https"}), must not be {@code null}
     */
    public ServerHttpResponse(HttpServletResponse response,
                              HttpErrorHandler errorHandler,
                              MimeParser parser,
                              MimeFormatter formatter,
                              HttpCodeBuffer codeBuffer,
                              HttpVersion version,
                              String protocol,
                              String scheme) {
        super(response, version, protocol, scheme);
        this.errorHandler = errorHandler;
        this.parser = parser;
        this.formatter = formatter;
        this.codeBuffer = codeBuffer;
    }

    @Override
    protected HttpCode parseHttpCode(int code) {
        return codeBuffer.get(code);
    }

    @Override
    protected void handleError(HttpCode code, String message) throws IOException {
        errorHandler.handle(response, code, message);
    }

    @Override
    protected MimeData parseMimeData(String data) {
        return parser.read(data);
    }

    @Override
    protected String formatMimeData(MimeData data) {
        return formatter.format(data);
    }
}
