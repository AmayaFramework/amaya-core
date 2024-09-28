package io.github.amayaframework.context;

import io.github.amayaframework.http.HttpMethod;
import io.github.amayaframework.http.HttpVersion;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Skeletal implementation of {@link HttpRequest}. Provides implementations for all methods except
 * {@link HttpRequest#getPathParameters()} and {@link HttpRequest#getPathParameter(String)}.
 */
public abstract class AbstractHttpRequest extends AbstractRequest<HttpServletRequest> implements HttpRequest {
    /**
     * Http version of this request.
     */
    protected final HttpVersion version;
    /**
     * Http method of this request.
     */
    protected HttpMethod method;
    /**
     * {@link URI} containing path from http request line.
     */
    protected URI uri;
    /**
     * Parsed segments of request path.
     */
    protected String[] segments;
    /**
     * Parsed query parameters of this request.
     */
    protected Map<String, List<Object>> queries;
    /**
     * Cookies of this request.
     */
    protected Map<String, Cookie> cookies;
    /**
     * Headers of this request.
     */
    protected Map<String, String> headers;
    /**
     * Attributes of session associated with this request.
     */
    protected Map<String, Object> sessionAttributes;

    /**
     * Constructs {@link AbstractHttpRequest} instance with given {@link HttpServletRequest} and {@link HttpVersion}.
     *
     * @param request the underlying {@link HttpServletRequest} instance, must be non-null
     * @param version the specified http protocol version, must be non-null
     */
    protected AbstractHttpRequest(HttpServletRequest request, HttpVersion version) {
        super(request);
        this.version = version;
    }

    @Override
    public Map<String, String> getHeaders() {
        if (headers != null) {
            return headers;
        }
        headers = new RequestHeaderMap(request);
        return headers;
    }

    @Override
    public boolean containsHeader(String name) {
        return request.getHeader(name) != null;
    }

    @Override
    public String getHeader(String name) {
        return request.getHeader(name);
    }

    @Override
    public Date getDateHeader(String name) {
        return new Date(request.getDateHeader(name));
    }

    @Override
    public int getIntHeader(String name) {
        return request.getIntHeader(name);
    }

    /**
     * Parses {@link HttpMethod} from raw string, containing method name.
     *
     * @param method the string with http method name
     * @return {@link HttpMethod} instance
     */
    protected abstract HttpMethod parseHttpMethod(String method);

    @Override
    public HttpMethod getMethod() {
        if (method != null) {
            return method;
        }
        method = parseHttpMethod(request.getMethod());
        return method;
    }

    @Override
    public URI getURI() {
        if (uri != null) {
            return uri;
        }
        uri = URI.create(request.getRequestURI() + "?" + request.getQueryString());
        return uri;
    }

    @Override
    public String getPath() {
        return request.getRequestURI();
    }

    /**
     * Splits given path by segments.
     *
     * @param path the specified path to be split
     * @return {@link String[]} containing path segments
     */
    protected abstract String[] splitPath(String path);

    @Override
    public String[] getPathSegments() {
        if (segments != null) {
            return segments;
        }
        segments = splitPath(request.getRequestURI());
        return segments;
    }

    @Override
    public String getQueryString() {
        return request.getQueryString();
    }

    /**
     * Collects query parameters from this request.
     *
     * @return {@link Map} instance containing all request query parameters
     */
    protected abstract Map<String, List<Object>> collectQueries();

    @Override
    public Map<String, List<Object>> getQueryParameters() {
        if (queries != null) {
            return queries;
        }
        queries = collectQueries();
        return queries;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> getQueryParameters(String name) {
        if (queries == null) {
            queries = collectQueries();
        }
        return (List<T>) queries.get(name);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getQueryParameter(String name) {
        if (queries == null) {
            queries = collectQueries();
        }
        var found = queries.get(name);
        if (found == null || found.isEmpty()) {
            return null;
        }
        return (T) found.get(0);
    }

    @Override
    public Map<String, Object> getSessionParameters() {
        if (sessionAttributes != null) {
            return sessionAttributes;
        }
        sessionAttributes = new SessionAttributeMap(request.getSession(true));
        return sessionAttributes;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getSessionParameter(String name) {
        return (T) request.getSession(true).getAttribute(name);
    }

    @Override
    public void setSessionParameter(String name, Object value) {
        request.getSession(true).setAttribute(name, value);
    }

    /**
     * Collects cookies from this request.
     *
     * @return {@link Map} instance containing all request cookies
     */
    protected Map<String, Cookie> collectCookies() {
        var ret = new HashMap<String, Cookie>();
        var cookies = request.getCookies();
        if (cookies == null) {
            return ret;
        }
        for (var cookie : cookies) {
            ret.put(cookie.getName(), cookie);
        }
        return ret;
    }

    @Override
    public Map<String, Cookie> getCookies() {
        if (cookies != null) {
            return cookies;
        }
        cookies = collectCookies();
        return cookies;
    }

    @Override
    public Cookie getCookie(String name) {
        if (cookies == null) {
            cookies = collectCookies();
        }
        return cookies.get(name);
    }

    @Override
    public Map<String, String> getTrailerFields() {
        return request.getTrailerFields();
    }

    @Override
    public boolean isTrailerFieldsReady() {
        return request.isTrailerFieldsReady();
    }

    @Override
    public HttpVersion getHttpVersion() {
        return version;
    }
}
