package io.github.amayaframework.core.contexts;

import io.github.amayaframework.core.methods.HttpMethod;
import io.github.amayaframework.core.wrapping.Content;
import io.github.amayaframework.core.wrapping.Viewable;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A class representing a http request. Inherited from {@link HttpTransaction} and service interface {@link Viewable}.
 */
public abstract class HttpRequest extends AbstractHttpTransaction implements Viewable {
    private final Map<String, Object> fields;
    private HttpMethod method;
    private Map<String, List<String>> queryParameters;
    private Map<String, Object> pathParameters;

    public HttpRequest() {
        fields = new HashMap<>();
    }

    /**
     * Returns the used http method.
     *
     * @return {@link HttpMethod}
     */
    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = Objects.requireNonNull(method);
    }

    @Override
    public Object get(String name) {
        return fields.get(name);
    }

    protected void put(String name, Object value) {
        this.fields.put(name, value);
    }

    /**
     * Returns all queried parameters extracted from the request URI.
     *
     * @return {@link Map}
     */
    public Map<String, List<String>> getQueryParameters() {
        return queryParameters;
    }

    public void setQueryParameters(Map<String, List<String>> queryParameters) {
        this.queryParameters = Objects.requireNonNull(queryParameters);
        put(Content.QUERY, queryParameters);
    }

    /**
     * Returns a specific query parameter.
     *
     * @param name of specific query parameter
     * @return {@link List} of query parameter values
     */
    public List<String> getQueryParameter(String name) {
        return queryParameters.get(name);
    }

    /**
     * Returns first value of a specific query parameter.
     *
     * @param name of specific query parameter
     * @return {@link String}
     */
    public String getFirstQueryParameter(String name) {
        List<String> parameter = queryParameters.get(name);
        if (parameter == null || parameter.size() == 0) {
            return null;
        }
        return parameter.get(0);
    }

    /**
     * Returns a map of path parameters that were extracted
     * from the request URI in accordance with the specification
     * described in the controller.
     *
     * @return {@link Map}
     */
    public Map<String, Object> getPathParameters() {
        return pathParameters;
    }

    public void setPathParameters(Map<String, Object> pathParameters) {
        this.pathParameters = Objects.requireNonNull(pathParameters);
        put(Content.PATH, pathParameters);
    }

    /**
     * Returns the specified pass parameter, cast to {@link T}, in case of absence of
     * the parameter or an unsuccessful cast, returns null.
     *
     * @param name of the path parameter
     * @param <T>  the type to which the path parameter will be cast
     * @return parameter, cast to {@link T}
     */
    @SuppressWarnings("unchecked")
    public <T> T getPathParameter(String name) {
        try {
            return (T) pathParameters.get(name);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void setBody(Object body) {
        super.setBody(body);
        put(Content.BODY, this.body);
    }

    public void setCookies(Map<String, Cookie> cookies) {
        this.cookies = Objects.requireNonNull(cookies);
        put(Content.COOKIE, cookies);
    }
}
