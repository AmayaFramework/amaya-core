package io.github.amayaframework.core.contexts;

import javax.servlet.http.Cookie;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CommonHttpRequest implements HttpRequest {
    private final HttpRequest body;

    public CommonHttpRequest(HttpRequest body) {
        this.body = body;
    }

    @Override
    public Map<String, List<String>> getQuery() {
        return body.getQuery();
    }

    @Override
    public void setQuery(Map<String, List<String>> queryParameters) {
        body.setQuery(queryParameters);
    }

    @Override
    public List<String> getQueries(String name) {
        return body.getQueries(name);
    }

    @Override
    public String getQuery(String name) {
        return body.getQuery(name);
    }

    @Override
    public Map<String, Object> getPathParameters() {
        return body.getPathParameters();
    }

    @Override
    public void setPathParameters(Map<String, Object> pathParameters) {
        body.setPathParameters(pathParameters);
    }

    @Override
    public <T> T getPathParameter(String name) {
        return body.getPathParameter(name);
    }

    @Override
    public void setCookies(Map<String, Cookie> cookies) {
        body.setCookies(cookies);
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
    public Object getBody() {
        return body.getBody();
    }

    @Override
    public void setBody(Object body) {
        this.body.setBody(body);
    }

    @Override
    public List<String> getHeaders(String key) {
        return body.getHeaders(key);
    }

    @Override
    public String getHeader(String key) {
        return body.getHeader(key);
    }

    @Override
    public Collection<Cookie> getCookies() {
        return body.getCookies();
    }

    @Override
    public void setCookie(Cookie cookie) {
        body.setCookie(cookie);
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
        body.setContentType(type);
    }

    @Override
    public Map<String, Object> getAttachments() {
        return body.getAttachments();
    }

    @Override
    public Object getAttachment(String key) {
        return body.getAttachment(key);
    }

    @Override
    public void setAttachment(String key, Object value) {
        body.setAttachment(key, value);
    }

    @Override
    public Object removeAttachment(String key) {
        return body.removeAttachment(key);
    }

    @Override
    public Object view(String name) {
        return body.view(name);
    }

    @Override
    public void set(String name, Object value) {
        body.set(name, value);
    }
}
