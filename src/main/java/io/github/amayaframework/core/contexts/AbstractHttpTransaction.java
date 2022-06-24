package io.github.amayaframework.core.contexts;

import io.github.amayaframework.http.ContentType;

import javax.servlet.http.Cookie;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractHttpTransaction implements HttpTransaction {
    protected Map<String, Cookie> cookies;
    protected Object body;
    protected ContentType type;
    protected Charset charset;

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
        if (header != null && !header.isEmpty()) {
            return header.get(0);
        }
        return null;
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
    public Charset getCharset() {
        return charset;
    }

    @Override
    public void setCharset(Charset charset) {
        this.charset = charset;
    }
}
