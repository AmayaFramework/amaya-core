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
public abstract class AbstractHttpRequest extends AbstractHttpTransaction implements HttpRequest {
    private final Map<String, Object> fields;
    private HttpMethod method;
    private Map<String, List<String>> queryParameters;
    private Map<String, Object> pathParameters;

    public AbstractHttpRequest() {
        fields = new HashMap<>();
    }

    @Override
    public HttpMethod getMethod() {
        return method;
    }

    @Override
    public void setMethod(HttpMethod method) {
        this.method = Objects.requireNonNull(method);
    }

    @Override
    public Object view(String name) {
        return fields.get(name);
    }

    @Override
    public void set(String name, Object value) {
        Objects.requireNonNull(name);
        this.fields.put(name, value);
    }

    @Override
    public Map<String, List<String>> getQuery() {
        return queryParameters;
    }

    @Override
    public void setQuery(Map<String, List<String>> queryParameters) {
        this.queryParameters = Objects.requireNonNull(queryParameters);
        set(Content.QUERY, queryParameters);
    }

    @Override
    public List<String> getQueries(String name) {
        return queryParameters.get(name);
    }

    @Override
    public String getQuery(String name) {
        List<String> parameter = queryParameters.get(name);
        if (parameter == null || parameter.isEmpty()) {
            return null;
        }
        return parameter.get(0);
    }

    @Override
    public Map<String, Object> getPathParameters() {
        return pathParameters;
    }

    @Override
    public void setPathParameters(Map<String, Object> pathParameters) {
        this.pathParameters = Objects.requireNonNull(pathParameters);
        set(Content.PATH, pathParameters);
    }

    @Override
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
        set(Content.BODY, this.body);
    }

    @Override
    public void setCookies(Map<String, Cookie> cookies) {
        this.cookies = Objects.requireNonNull(cookies);
        set(Content.COOKIE, cookies);
    }
}
