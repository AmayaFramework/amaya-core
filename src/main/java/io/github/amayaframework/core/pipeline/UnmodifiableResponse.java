package io.github.amayaframework.core.pipeline;

import com.github.romanqed.jutils.http.HeaderMap;
import com.github.romanqed.jutils.http.HttpCode;
import com.github.romanqed.jutils.lambdas.Handler;
import io.github.amayaframework.core.contexts.ContentType;
import io.github.amayaframework.core.contexts.FixedOutputStream;
import io.github.amayaframework.core.contexts.HttpResponse;

import javax.servlet.http.Cookie;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

final class UnmodifiableResponse implements HttpResponse {
    private final HttpResponse body;

    UnmodifiableResponse(HttpResponse body) {
        this.body = body;
    }

    @Override
    public HttpCode getCode() {
        return body.getCode();
    }

    @Override
    public void setCode(HttpCode code) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setHeader(String key, List<String> value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setHeader(String key, String value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> removeHeader(String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public HeaderMap getHeaderMap() {
        return HeaderMap.unmodifiableHeaderMap(body.getHeaderMap());
    }

    @Override
    public Handler<FixedOutputStream> getOutputStreamHandler() {
        return body.getOutputStreamHandler();
    }

    @Override
    public void setOutputStreamHandler(Handler<FixedOutputStream> handler) {
        throw new UnsupportedOperationException();
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
}
