package io.github.amayaframework.server;

import io.github.amayaframework.context.AbstractHttpResponse;
import io.github.amayaframework.http.HttpCode;
import io.github.amayaframework.http.HttpVersion;
import io.github.amayaframework.http.MimeData;
import jakarta.servlet.http.HttpServletResponse;

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
    protected final MimeParser parser;
    protected final MimeFormatter formatter;
    protected final HttpCodeBuffer codeBuffer;

    /**
     * Create a new {@code ServerHttpResponse}.
     *
     * @param response   the underlying servlet response
     * @param parser     the parser for {@link MimeData}
     * @param formatter  the formatter for {@link MimeData}
     * @param codeBuffer the buffer for resolving {@link HttpCode}
     * @param version    the HTTP version of the response
     * @param protocol   the raw protocol string (e.g. {@code "HTTP/1.1"})
     * @param scheme     the request scheme (e.g. {@code "http"} or {@code "https"})
     */
    public ServerHttpResponse(HttpServletResponse response,
                              MimeParser parser,
                              MimeFormatter formatter,
                              HttpCodeBuffer codeBuffer,
                              HttpVersion version,
                              String protocol,
                              String scheme) {
        super(response, version, protocol, scheme);
        this.parser = parser;
        this.formatter = formatter;
        this.codeBuffer = codeBuffer;
    }

    @Override
    protected HttpCode parseHttpCode(int code) {
        return codeBuffer.get(code);
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
