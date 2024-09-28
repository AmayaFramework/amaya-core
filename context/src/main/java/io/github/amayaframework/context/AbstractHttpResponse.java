package io.github.amayaframework.context;

import io.github.amayaframework.http.HttpCode;
import io.github.amayaframework.http.HttpVersion;
import io.github.amayaframework.http.MimeData;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Skeletal implementation of {@link HttpRequest}. Provides implementations for all {@link HttpRequest} methods.
 * Requires to implement {@link AbstractResponse#formatMimeData(MimeData)}.
 */
public abstract class AbstractHttpResponse extends AbstractResponse<HttpServletResponse> implements HttpResponse {
    /**
     * Http version of this response.
     */
    protected final HttpVersion version;
    /**
     * Http status code of this response.
     */
    protected HttpCode status;
    /**
     * Headers of this response.
     */
    protected Map<String, String> headers;
    /**
     * Cookies of this response.
     */
    protected Map<String, Cookie> cookies;

    /**
     * Constructs {@link AbstractHttpResponse} instance with given {@link HttpServletResponse},
     * protocol, scheme and {@link HttpVersion}.
     *
     * @param response the underlying {@link HttpServletResponse} instance, must be non-null
     * @param protocol the specified protocol string
     * @param scheme   the specified scheme string
     * @param version  the specified http protocol version, must be non-null
     */
    protected AbstractHttpResponse(HttpServletResponse response, String protocol, String scheme, HttpVersion version) {
        super(response, protocol, scheme);
        this.version = version;
        this.status = HttpCode.OK;
    }

    @Override
    public Map<String, String> getHeaders() {
        if (headers != null) {
            return headers;
        }
        headers = new ResponseHeaderMap(response);
        return headers;
    }

    @Override
    public boolean containsHeader(String name) {
        return response.containsHeader(name);
    }

    @Override
    public String getHeader(String name) {
        return response.getHeader(name);
    }

    @Override
    public Map<String, Cookie> getCookies() {
        if (cookies == null) {
            cookies = new HashMap<>();
        }
        return cookies;
    }

    @Override
    public Cookie getCookie(String name) {
        if (cookies == null) {
            cookies = new HashMap<>();
            return null;
        }
        return cookies.get(name);
    }

    @Override
    public void setCookie(Cookie cookie) {
        if (cookies == null) {
            cookies = new HashMap<>();
        }
        cookies.put(cookie.getName(), cookie);
        response.addCookie(cookie);
    }

    @Override
    public void setHeader(String name, Object value) {
        response.setHeader(name, value.toString());
    }

    @Override
    public void setHeader(String name, String value) {
        response.setHeader(name, value);
    }

    @Override
    public void setHeader(String name, Date date) {
        response.setDateHeader(name, date.getTime());
    }

    @Override
    public void setHeader(String name, long date) {
        response.setDateHeader(name, date);
    }

    @Override
    public HttpCode getStatus() {
        return status;
    }

    @Override
    public void setStatus(HttpCode code) {
        this.status = code;
        response.setStatus(code.getCode());
    }

    @Override
    public void sendError(HttpCode code, String message) throws IOException {
        response.sendError(code.getCode(), message);
    }

    @Override
    public void sendError(HttpCode code) throws IOException {
        response.sendError(code.getCode());
    }

    @Override
    public void sendRedirect(String location) throws IOException {
        response.sendRedirect(location);
    }

    @Override
    public Supplier<Map<String, String>> getTrailerFields() {
        return response.getTrailerFields();
    }

    @Override
    public void setTrailerFields(Supplier<Map<String, String>> supplier) {
        response.setTrailerFields(supplier);
    }

    @Override
    public HttpVersion getHttpVersion() {
        return version;
    }
}
