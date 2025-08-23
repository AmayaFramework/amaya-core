package io.github.amayaframework.context;

import io.github.amayaframework.http.HttpMethod;
import io.github.amayaframework.http.HttpVersion;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.*;

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
     * {@link URL} containing full request path.
     */
    protected URL url;

    /**
     * Parsed segments of request path.
     */
    protected List<String> segments;

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
     * Multi-value headers of this request.
     */
    protected Map<String, List<String>> multiHeaders;

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
    public String getHeader(String name) {
        return request.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeadersEnum(String name) {
        return request.getHeaders(name);
    }

    @Override
    public Iterable<String> getHeaders(String name) {
        return () -> request.getHeaders(name).asIterator();
    }

    @Override
    public Date getDateHeader(String name) {
        var ret = request.getDateHeader(name);
        if (ret < 0) {
            return null;
        }
        return new Date(ret);
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
        if (method == null) {
            method = parseHttpMethod(request.getMethod());
        }
        return method;
    }

    private URL createURL() {
        var ret = new StringBuilder();
        var scheme = request.getScheme();
        var port = request.getServerPort();
        ret.append(scheme);
        ret.append("://");
        ret.append(request.getServerName());
        if ((scheme.equals("http") && port != 80) || (scheme.equals("https") && port != 443)) {
            ret.append(':');
            ret.append(port);
        }
        ret.append(request.getRequestURI());
        var query = request.getQueryString();
        if (query != null) {
            ret.append('?').append(query);
        }
        try {
            return new URL(ret.toString());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public URL getURL() {
        if (url == null) {
            url = createURL();
        }
        return url;
    }

    private URI createURI() {
        var path = request.getRequestURI();
        var query = request.getQueryString();
        if (query == null) {
            return URI.create(path);
        }
        return URI.create(path + '?' + query);
    }

    @Override
    public URI getRequestURI() {
        if (uri == null) {
            uri = createURI();
        }
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
     * @return {@link List} containing path segments
     */
    protected abstract List<String> splitPath(String path);

    @Override
    public List<String> getPathSegments() {
        if (segments == null) {
            segments = splitPath(request.getRequestURI());
        }
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
        if (queries == null) {
            queries = collectQueries();
        }
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
        if (sessionAttributes == null) {
            sessionAttributes = new SessionAttributeMap(request.getSession(true));
        }
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
    public Map<String, Cookie> cookies() {
        if (cookies == null) {
            cookies = collectCookies();
        }
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
