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
    protected final HttpVersion version;
    protected HttpCode status;
    protected Map<String, String> headers;
    protected Map<String, List<String>> multiHeaders;
    protected Map<String, Cookie> cookies;
    protected Map<String, Cookie> finalCookies;

    /**
     * TODO
     * @param response
     * @param version
     * @param protocol
     * @param scheme
     */
    protected AbstractHttpResponse(HttpServletResponse response, HttpVersion version, String protocol, String scheme) {
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
    public String header(String name) {
        return response.getHeader(name);
    }

    @Override
    public Enumeration<String> headersEnum(String name) {
        return new IteratorEnumeration<>(response.getHeaders(name).iterator());
    }

    @Override
    public Iterable<String> headers(String name) {
        return response.getHeaders(name);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Cookie> cookies() {
        if (finalCookies == null) {
            return Collections.EMPTY_MAP;
        }
        return finalCookies;
    }

    @Override
    public Cookie cookie(String name) {
        if (cookies == null) {
            return null;
        }
        return cookies.get(name);
    }

    @Override
    public void cookie(Cookie cookie) {
        Objects.requireNonNull(cookie);
        response.addCookie(cookie);
        if (cookies == null) {
            cookies = new HashMap<>();
            finalCookies = Collections.unmodifiableMap(cookies);
        }
        cookies.put(cookie.getName(), cookie);
    }

    @Override
    public void header(String name, String value) {
        response.setHeader(name, value);
    }

    @Override
    public void header(String name, int value) {
        response.setIntHeader(name, value);
    }

    @Override
    public void dateHeader(String name, long date) {
        response.setDateHeader(name, date);
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
    public void addDateHeader(String name, long date) {
        response.addDateHeader(name, date);
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

    /**
     * TODO
     * @param code
     * @return
     */
    protected abstract HttpCode parseHttpCode(int code);

    @Override
    public HttpCode status() {
        if (status == null || status.getCode() != response.getStatus()) {
            status = parseHttpCode(response.getStatus());
        }
        return status;
    }

    @Override
    public void status(HttpCode code) {
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
    public Supplier<Map<String, String>> trailerFields() {
        return response.getTrailerFields();
    }

    @Override
    public void trailerFields(Supplier<Map<String, String>> supplier) {
        response.setTrailerFields(supplier);
    }

    @Override
    public HttpVersion httpVersion() {
        return version;
    }
}
