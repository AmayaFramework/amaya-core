package io.github.amayaframework.context;

import io.github.amayaframework.http.HttpMethod;
import io.github.amayaframework.http.HttpVersion;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.*;

/**
 * Skeletal implementation of {@link HttpRequest}. Provides implementations for most methods
 * common to HTTP requests, built on top of the underlying {@link HttpServletRequest}.
 * <p>
 * Subclasses must implement:
 * <ul>
 *   <li>{@link #pathParams()} and {@link #pathParam(String)} — path parameter parsing,</li>
 *   <li>{@link #parseHttpMethod(String)} — conversion from raw HTTP method string to {@link HttpMethod},</li>
 *   <li>{@link #splitPath(String)} — splitting a path string into segments,</li>
 *   <li>{@link #collectQueries()} — query string parsing.</li>
 * </ul>
 */
public abstract class AbstractHttpRequest extends AbstractRequest<HttpServletRequest> implements HttpRequest {
    protected final HttpVersion version;
    protected HttpMethod method;
    protected URI uri;
    protected URL url;
    protected List<String> segments;
    protected Map<String, List<Object>> queries;
    protected Map<String, Cookie> cookies;
    protected Map<String, String> headers;
    protected Map<String, List<String>> multiHeaders;
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
    public Map<String, String> headers() {
        if (headers == null) {
            headers = new RequestHeaderMap(request);
        }
        return headers;
    }

    /**
     * Collects all headers and their values from the underlying {@link HttpServletRequest}.
     * Each header name maps to a list of all its values.
     *
     * @return a map of header names to lists of values
     */
    protected Map<String, List<String>> collectMultiHeaders() {
        var ret = new HashMap<String, List<String>>();
        var names = request.getHeaderNames();
        while (names.hasMoreElements()) {
            var header = names.nextElement();
            var headers = request.getHeaders(header);
            var values = new ArrayList<String>();
            while (headers.hasMoreElements()) {
                values.add(headers.nextElement());
            }
            ret.put(header, Collections.unmodifiableList(values));
        }
        return Collections.unmodifiableMap(ret);
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
        return request.getHeader(name) != null;
    }

    @Override
    public String header(String name) {
        return request.getHeader(name);
    }

    @Override
    public Enumeration<String> headersEnum(String name) {
        return request.getHeaders(name);
    }

    @Override
    public Iterable<String> headers(String name) {
        return () -> request.getHeaders(name).asIterator();
    }

    @Override
    public Date dateHeader(String name) {
        var ret = request.getDateHeader(name);
        if (ret < 0) {
            return null;
        }
        return new Date(ret);
    }

    @Override
    public int intHeader(String name) {
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
    public HttpMethod method() {
        if (method == null) {
            method = parseHttpMethod(request.getMethod());
        }
        return method;
    }

    /**
     * Builds a full {@link URL} representing this request, including scheme,
     * host, port (if non-default), path, and query string.
     * <p>
     * Uses the underlying {@link HttpServletRequest#getRequestURL()} and appends
     * the query string if present.
     *
     * @return the constructed {@link URL}
     * @throws IllegalStateException if the URL is malformed (should not normally happen)
     */
    protected URL createUrl() {
        var buffer = request.getRequestURL();
        var query = request.getQueryString();
        if (query != null) {
            buffer.append('?').append(query);
        }
        try {
            return new URL(buffer.toString());
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public URL url() {
        if (url == null) {
            url = createUrl();
        }
        return url;
    }

    private URI createUri() {
        var path = request.getRequestURI();
        var query = request.getQueryString();
        if (query == null) {
            return URI.create(path);
        }
        return URI.create(path + '?' + query);
    }

    @Override
    public URI requestUri() {
        if (uri == null) {
            uri = createUri();
        }
        return uri;
    }

    @Override
    public String path() {
        return request.getRequestURI();
    }

    /**
     * Splits given path by segments.
     *
     * @param path the specified path to be split
     * @return {@link List} containing path segments
     */
    protected abstract List<String> splitPath(String path);

    @Override
    public List<String> pathSegments() {
        if (segments == null) {
            segments = splitPath(request.getRequestURI());
        }
        return segments;
    }

    @Override
    public String queryString() {
        return request.getQueryString();
    }

    /**
     * Collects query parameters from this request.
     *
     * @return {@link Map} instance containing all request query parameters
     */
    protected abstract Map<String, List<Object>> collectQueries();

    @Override
    public Map<String, List<Object>> queryParams() {
        if (queries == null) {
            queries = collectQueries();
        }
        return queries;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> queryParams(String name) {
        if (queries == null) {
            queries = collectQueries();
        }
        return (List<T>) queries.get(name);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T queryParam(String name) {
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
    public boolean hasSession() {
        return request.getSession(false) != null;
    }

    @Override
    public HttpSession session(boolean create) {
        return request.getSession(create);
    }

    @Override
    public HttpSession session() {
        return request.getSession();
    }

    @Override
    public String changeSessionId() {
        return request.changeSessionId();
    }

    @Override
    public String requestedSessionId() {
        return request.getRequestedSessionId();
    }

    @Override
    public boolean requestedSessionIdValid() {
        return request.isRequestedSessionIdValid();
    }

    @Override
    public boolean requestedSessionIdFromUrl() {
        return request.isRequestedSessionIdFromURL();
    }

    @Override
    public boolean requestedSessionIdFromCookie() {
        return request.isRequestedSessionIdFromCookie();
    }

    @Override
    public Map<String, Object> sessionParams() {
        if (sessionAttributes == null) {
            sessionAttributes = new SessionAttributeMap(request.getSession(true));
        }
        return sessionAttributes;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T sessionParam(String name) {
        var session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (T) session.getAttribute(name);
    }

    @Override
    public void sessionParam(String name, Object value) {
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
        return Collections.unmodifiableMap(ret);
    }

    @Override
    public Map<String, Cookie> cookies() {
        if (cookies == null) {
            cookies = collectCookies();
        }
        return cookies;
    }

    @Override
    public Cookie cookie(String name) {
        if (cookies == null) {
            cookies = collectCookies();
        }
        return cookies.get(name);
    }

    @Override
    public Map<String, String> trailerFields() {
        return request.getTrailerFields();
    }

    @Override
    public boolean trailerFieldsReady() {
        return request.isTrailerFieldsReady();
    }

    @Override
    public HttpVersion httpVersion() {
        return version;
    }
}
