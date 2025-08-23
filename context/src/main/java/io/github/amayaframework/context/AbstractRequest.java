package io.github.amayaframework.context;

import io.github.amayaframework.http.MimeData;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Skeletal implementation of {@link Request}. Built over underlying {@link ServletRequest} instance.
 *
 * @param <T> the type of underlying request
 */
public abstract class AbstractRequest<T extends ServletRequest> implements Request {
    protected final T request;
    protected InetSocketAddress local;
    protected InetSocketAddress remote;
    protected Charset charset;
    protected MimeData data;

    /**
     * Constructs {@link AbstractRequest} instance with given {@link ServletRequest} instance.
     *
     * @param request the underlying {@link ServletRequest} instance, must be non-null
     */
    protected AbstractRequest(T request) {
        this.request = request;
    }

    @Override
    public ServletInputStream inputStream() throws IOException {
        return request.getInputStream();
    }

    @Override
    public BufferedReader reader() throws IOException {
        return request.getReader();
    }

    @Override
    public InetSocketAddress localAddress() {
        if (local == null) {
            local = new InetSocketAddress(request.getLocalAddr(), request.getLocalPort());
        }
        return local;
    }

    @Override
    public String localHost() {
        return request.getLocalName();
    }

    @Override
    public InetSocketAddress remoteAddress() {
        if (remote == null) {
            remote = new InetSocketAddress(request.getRemoteAddr(), request.getRemotePort());
        }
        return remote;
    }

    @Override
    public String remoteHost() {
        return request.getRemoteHost();
    }

    @Override
    public Map<String, String[]> params() {
        return request.getParameterMap();
    }

    @Override
    public boolean containsParam(String name) {
        return request.getParameter(name) != null;
    }

    @Override
    public String param(String name) {
        return request.getParameter(name);
    }

    @Override
    public String[] params(String name) {
        return request.getParameterValues(name);
    }

    @Override
    public Iterable<Locale> locales() {
        return () -> request.getLocales().asIterator();
    }

    @Override
    public Charset charset() {
        if (charset == null || !charset.name().equals(request.getCharacterEncoding())) {
            var encoding = request.getCharacterEncoding();
            charset = encoding == null ? null : Charset.forName(encoding);
        }
        return charset;
    }

    @Override
    public void charset(Charset charset) {
        Objects.requireNonNull(charset);
        try {
            request.setCharacterEncoding(charset.name());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
        this.charset = charset;
    }

    @Override
    public long contentLength() {
        return request.getContentLengthLong();
    }

    /**
     * Parses {@link MimeData} from given qualifier.
     *
     * @param data the specified string containing mime data qualifier
     * @return {@link MimeData} instance
     */
    protected abstract MimeData parseMimeData(String data);

    @Override
    public MimeData mimeData() {
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
    public String protocol() {
        return request.getProtocol();
    }

    @Override
    public String scheme() {
        return request.getScheme();
    }

    @Override
    public Locale locale() {
        return request.getLocale();
    }
}
