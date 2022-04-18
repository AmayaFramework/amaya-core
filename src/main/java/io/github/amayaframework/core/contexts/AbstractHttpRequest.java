package io.github.amayaframework.core.contexts;

import io.github.amayaframework.core.wrapping.Content;

import javax.servlet.http.Cookie;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractHttpRequest extends AbstractHttpTransaction implements HttpRequest {
    private final Map<String, Object> fields;
    private Map<String, List<String>> queryParameters;
    private Map<String, Object> pathParameters;

    public AbstractHttpRequest() {
        fields = new HashMap<>();
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

    @Override
    public String getBodyAsString() {
        if (body == null || type == null || !type.isString()) {
            return null;
        }
        return body.toString();
    }

    @Override
    public InputStream getBodyAsInputStream() {
        if (body == null || (type != null && type.isString())) {
            return null;
        }
        try {
            return (InputStream) body;
        } catch (Exception e) {
            return null;
        }
    }
}
