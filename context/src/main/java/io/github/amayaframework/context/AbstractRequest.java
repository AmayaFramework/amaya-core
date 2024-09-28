package io.github.amayaframework.context;

import io.github.amayaframework.http.MimeData;
import io.github.amayaframework.http.MimeType;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Map;

/**
 * Skeletal implementation of {@link Request}. Built over underlying {@link ServletRequest} instance.
 * @param <T> the type of underlying request
 */
public abstract class AbstractRequest<T extends ServletRequest> implements Request {
    /**
     *
     */
    protected final T request;
    /**
     *
     */
    protected InetSocketAddress local;
    /**
     *
     */
    protected InetSocketAddress remote;
    /**
     *
     */
    protected Charset charset;
    /**
     *
     */
    protected MimeData data;
    /**
     *
     */
    protected Map<String, Object> attributes;

    /**
     * @param request
     */
    protected AbstractRequest(T request) {
        this.request = request;
    }

    @Override
    public Map<String, Object> getAttributes() {
        if (attributes != null) {
            return attributes;
        }
        attributes = new RequestAttributeMap(request);
        return attributes;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <V> V get(String key) {
        return (V) request.getAttribute(key);
    }

    @Override
    public void set(String key, Object value) {
        request.setAttribute(key, value);
    }

    @Override
    public Object remove(String key) {
        var ret = request.getAttribute(key);
        request.removeAttribute(key);
        return ret;
    }

    @Override
    public boolean contains(String key) {
        return request.getAttribute(key) != null;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return request.getInputStream();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return request.getReader();
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        if (local != null) {
            return local;
        }
        local = new InetSocketAddress(request.getLocalName(), request.getLocalPort());
        return local;
    }

    @Override
    public String getLocalHost() {
        return request.getLocalName();
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        if (remote != null) {
            return remote;
        }
        remote = new InetSocketAddress(request.getRemoteHost(), request.getRemotePort());
        return remote;
    }

    @Override
    public String getRemoteHost() {
        return request.getRemoteHost();
    }

    @Override
    public Map<String, String[]> getParameterValues() {
        return request.getParameterMap();
    }

    @Override
    public boolean containsParameter(String name) {
        return request.getParameter(name) != null;
    }

    @Override
    public String getParameter(String name) {
        return request.getParameter(name);
    }

    @Override
    public String[] getParameterValues(String name) {
        return request.getParameterValues(name);
    }

    @Override
    public Iterable<Locale> getLocales() {
        return () -> request.getLocales().asIterator();
    }

    @Override
    public Charset getCharset() {
        if (charset != null) {
            return charset;
        }
        charset = Charset.forName(request.getCharacterEncoding());
        return charset;
    }

    @Override
    public void setCharset(Charset charset) {
        this.charset = charset;
        try {
            request.setCharacterEncoding(charset.name());
        } catch (UnsupportedEncodingException e) {
            // Unreachable code
        }
    }

    @Override
    public long getContentLength() {
        return request.getContentLengthLong();
    }

    /**
     * @param data
     * @return
     */
    protected abstract MimeData parseMimeData(String data);

    @Override
    public MimeData getMimeData() {
        if (data != null) {
            return data;
        }
        var contentType = request.getContentType();
        if (contentType == null) {
            return null;
        }
        data = parseMimeData(contentType);
        return data;
    }

    @Override
    public void setMimeData(MimeData data) {
        this.data = data;
    }

    @Override
    public void setMimeType(MimeType type) {
        this.data = new MimeData(type);
    }

    @Override
    public String getProtocol() {
        return request.getProtocol();
    }

    @Override
    public String getScheme() {
        return request.getScheme();
    }

    @Override
    public Locale getLocale() {
        return request.getLocale();
    }
}
