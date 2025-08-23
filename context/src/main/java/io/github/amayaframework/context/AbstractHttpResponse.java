package io.github.amayaframework.context;

import io.github.amayaframework.http.HttpCode;
import io.github.amayaframework.http.HttpVersion;
import io.github.amayaframework.http.MimeData;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;
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
     * Multi-value headers of this response.
     */
    protected Map<String, List<String>> multiHeaders;

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
    public Map<String, String> headers() {
        if (headers == null) {
            headers = new ResponseHeaderMap(response);
        }
        return headers;
    }

    /**
     * Collects all headers and their values from the underlying {@link HttpServletResponse}.
     * Each header name maps to a list of all its values.
     *
     * @return a map of header names to lists of values
     */
    protected Map<String, List<String>> collectMultiHeaders() {
        var map = new HashMap<String, List<String>>();
        for (var header : response.getHeaderNames()) {
            var values = new ArrayList<>(response.getHeaders(header));
            map.put(header, new MultiHeaderList(values, header, response));
        }
        return new ResponseMultiHeaderMap(map, response);
    }

    @Override
    public Map<String, List<String>> multiHeaders() {
        if (multiHeaders == null) {
            multiHeaders = collectMultiHeaders();
        }
        return multiHeaders;
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
    public Enumeration<String> getHeadersEnum(String name) {
        return new IteratorEnumeration<>(response.getHeaders(name).iterator());
    }

    @Override
    public Iterable<String> getHeaders(String name) {
        return response.getHeaders(name);
    }

    @Override
    public Map<String, Cookie> cookies() {
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
        Objects.requireNonNull(cookie);
        response.addCookie(cookie);
        if (cookies == null) {
            cookies = new HashMap<>();
        }
        cookies.put(cookie.getName(), cookie);
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
    public void setHeader(String name, int value) {
        response.setIntHeader(name, value);
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
    public void addHeader(String name, Object value) {
        response.addHeader(name, value.toString());
    }

    @Override
    public void addHeader(String name, String value) {
        response.addHeader(name, value);
    }

    @Override
    public void addHeader(String name, int value) {
        response.addIntHeader(name, value);
    }

    @Override
    public void addHeader(String name, Date date) {
        response.addDateHeader(name, date.getTime());
    }

    @Override
    public void addHeader(String name, long date) {
        response.addDateHeader(name, date);
    }

    @Override
    public void extendHeader(String name, Object value) {
        extendHeader(name, value.toString());
    }

    @Override
    public void extendHeader(String name, String value) {
        var oldValue = response.getHeader(name);
        if (oldValue == null || oldValue.isBlank()) {
            response.setHeader(name, value);
        } else {
            response.setHeader(name, oldValue + "," + value);
        }
    }

    @Override
    public HttpCode getStatus() {
        return status;
    }

    @Override
    public void setStatus(HttpCode code) {
        if (!code.isSupported(version)) {
            throw new UnsupportedHttpDefinition(version, code);
        }
        response.setStatus(code.getCode());
        this.status = code;
    }

    @Override
    public void sendError(HttpCode code, String message) throws IOException {
        if (!code.isSupported(version)) {
            throw new UnsupportedHttpDefinition(version, code);
        }
        response.sendError(code.getCode(), message);
        this.status = code;
    }

    @Override
    public void sendError(HttpCode code) throws IOException {
        if (!code.isSupported(version)) {
            throw new UnsupportedHttpDefinition(version, code);
        }
        response.sendError(code.getCode());
        this.status = code;
    }

    @Override
    public void sendRedirect(String location, boolean encode) throws IOException {
        Objects.requireNonNull(location);
        if (encode) {
            location = response.encodeRedirectURL(location);
        }
        response.sendRedirect(location);
        this.status = HttpCode.FOUND;
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
