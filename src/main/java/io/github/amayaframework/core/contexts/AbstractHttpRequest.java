package io.github.amayaframework.core.contexts;

import io.github.amayaframework.core.inject.*;

import javax.servlet.http.Cookie;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@SourceRequest(HttpRequest.class)
public abstract class AbstractHttpRequest extends AbstractHttpTransaction implements HttpRequest {
    private Map<String, List<String>> queryParameters;
    private Map<String, Object> pathParameters;

    @Override
    public Map<String, List<String>> getQuery() {
        return queryParameters;
    }

    @Override
    public void setQuery(Map<String, List<String>> queryParameters) {
        this.queryParameters = Objects.requireNonNull(queryParameters);
    }

    @Override
    public List<String> getQueries(String name) {
        return queryParameters.get(name);
    }

    @Override
    @Provider(Query.class)
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
    }

    @Override
    @SuppressWarnings("unchecked")
    @Provider(Path.class)
    public <T> T getPathParameter(String name) {
        try {
            return (T) pathParameters.get(name);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void setCookies(Map<String, Cookie> cookies) {
        this.cookies = Objects.requireNonNull(cookies);
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
