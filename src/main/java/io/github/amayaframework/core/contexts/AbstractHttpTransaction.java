package io.github.amayaframework.core.contexts;

import javax.servlet.http.Cookie;
import java.io.InputStream;
import java.util.*;

public abstract class AbstractHttpTransaction implements HttpTransaction {
    private final Map<String, Object> attachments;
    protected Map<String, Cookie> cookies;
    protected Object body;
    protected ContentType type;

    protected AbstractHttpTransaction() {
        attachments = new HashMap<>();
    }

    @Override
    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    @Override
    public String getHeader(String key) {
        List<String> header = getHeaders(key);
        if (header != null) {
            return header.get(0);
        }
        return null;
    }

    @Override
    public Map<String, Object> getAttachments() {
        return attachments;
    }

    @Override
    public Object getAttachment(String key) {
        return attachments.get(key);
    }

    @Override
    public void setAttachment(String key, Object value) {
        attachments.put(key, value);
    }

    @Override
    public Collection<Cookie> getCookies() {
        return cookies.values();
    }

    @Override
    public void setCookie(Cookie cookie) {
        Objects.requireNonNull(cookie);
        cookies.put(cookie.getName(), cookie);
    }

    @Override
    public Cookie getCookie(String name) {
        return cookies.get(name);
    }

    @Override
    public ContentType getContentType() {
        return type;
    }

    @Override
    public void setContentType(ContentType type) {
        this.type = Objects.requireNonNull(type);
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
