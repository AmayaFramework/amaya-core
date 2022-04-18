package io.github.amayaframework.core.pipeline;

import io.github.amayaframework.core.contexts.ContentType;
import io.github.amayaframework.core.contexts.HttpRequest;

import javax.servlet.http.Cookie;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

class UnmodifiableRequest implements HttpRequest {
    private final HttpRequest body;

    protected UnmodifiableRequest(HttpRequest body) {
        this.body = body;
    }

    @Override
    public Map<String, List<String>> getQuery() {
        return Collections.unmodifiableMap(body.getQuery());
    }

    @Override
    public void setQuery(Map<String, List<String>> queryParameters) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> getQueries(String name) {
        return Collections.unmodifiableList(body.getQueries(name));
    }

    @Override
    public String getQuery(String name) {
        return body.getQuery(name);
    }

    @Override
    public Map<String, Object> getPathParameters() {
        return Collections.unmodifiableMap(body.getPathParameters());
    }

    @Override
    public void setPathParameters(Map<String, Object> pathParameters) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T getPathParameter(String name) {
        return body.getPathParameter(name);
    }

    @Override
    public Object getBody() {
        return body.getBody();
    }

    @Override
    public void setBody(Object body) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getBodyAsString() {
        return body.getBodyAsString();
    }

    @Override
    public InputStream getBodyAsInputStream() {
        return body.getBodyAsInputStream();
    }

    @Override
    public List<String> getHeaders(String key) {
        return Collections.unmodifiableList(body.getHeaders(key));
    }

    @Override
    public String getHeader(String key) {
        return body.getHeader(key);
    }

    @Override
    public Collection<Cookie> getCookies() {
        return Collections.unmodifiableCollection(body.getCookies());
    }

    @Override
    public void setCookies(Map<String, Cookie> cookies) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setCookie(Cookie cookie) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Cookie getCookie(String name) {
        return body.getCookie(name);
    }

    @Override
    public ContentType getContentType() {
        return body.getContentType();
    }

    @Override
    public void setContentType(ContentType type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<String, Object> getAttachments() {
        return Collections.unmodifiableMap(body.getAttachments());
    }

    @Override
    public Object getAttachment(String key) {
        return body.getAttachment(key);
    }

    @Override
    public void setAttachment(String key, Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object removeAttachment(String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object view(String name) {
        return body.view(name);
    }

    @Override
    public void set(String name, Object value) {
        throw new UnsupportedOperationException();
    }
}
