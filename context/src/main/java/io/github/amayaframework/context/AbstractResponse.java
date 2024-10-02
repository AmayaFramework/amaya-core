package io.github.amayaframework.context;

import io.github.amayaframework.http.MimeData;
import io.github.amayaframework.http.MimeType;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.ServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Locale;

/**
 * Skeletal implementation of {@link Response}. Built over underlying {@link ServletResponse} instance.
 *
 * @param <T> the type of underlying response
 */
public abstract class AbstractResponse<T extends ServletResponse> implements Response {
    /**
     * The underlying {@link ServletResponse} instance.
     */
    protected final T response;
    /**
     * Response protocol.
     */
    protected final String protocol;
    /**
     * Response scheme.
     */
    protected final String scheme;
    /**
     * Response charset.
     */
    protected Charset charset;
    /**
     * Length of response content.
     */
    protected long length;
    /**
     * Response mime data.
     */
    protected MimeData data;

    /**
     * Constructs {@link AbstractResponse} instance with given {@link ServletResponse} instance, protocol and scheme.
     *
     * @param response the underlying {@link ServletResponse} instance, must be non-null
     * @param protocol the specified protocol string
     * @param scheme   the specified scheme string
     */
    protected AbstractResponse(T response, String protocol, String scheme) {
        this.response = response;
        this.protocol = protocol;
        this.scheme = scheme;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return response.getOutputStream();
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return response.getWriter();
    }

    @Override
    public boolean isSent() {
        return response.isCommitted();
    }

    @Override
    public void reset() {
        response.reset();
    }

    @Override
    public int getBufferSize() {
        return response.getBufferSize();
    }

    @Override
    public void setBufferSize(int size) {
        response.setBufferSize(size);
    }

    @Override
    public void flushBuffer() throws IOException {
        response.flushBuffer();
    }

    @Override
    public void resetBuffer() {
        response.resetBuffer();
    }

    @Override
    public Charset getCharset() {
        if (charset != null) {
            return charset;
        }
        charset = Charset.forName(response.getCharacterEncoding());
        return charset;
    }

    @Override
    public void setCharset(Charset charset) {
        response.setCharacterEncoding(charset.name());
        this.charset = charset;
    }

    @Override
    public long getContentLength() {
        return length;
    }

    @Override
    public void setContentLength(long length) {
        response.setContentLengthLong(length);
        this.length = length;
    }

    @Override
    public MimeData getMimeData() {
        return data;
    }

    @Override
    public void setMimeData(MimeData data) {
        if (data == null) {
            response.setContentType(null);
        } else {
            response.setContentType(formatMimeData(data));
        }
        this.data = data;
    }

    /**
     * Formats {@link MimeData} instance as mime string, e.g. 'group/type;param=value'.
     *
     * @param data the specified {@link MimeData} instance to be formatted
     * @return mime string
     */
    protected abstract String formatMimeData(MimeData data);

    @Override
    public void setMimeType(MimeType type) {
        if (type == null) {
            setMimeData(null);
            return;
        }
        setMimeData(new MimeData(type));
    }

    @Override
    public String getProtocol() {
        return protocol;
    }

    @Override
    public String getScheme() {
        return scheme;
    }

    @Override
    public Locale getLocale() {
        return response.getLocale();
    }

    @Override
    public void setLocale(Locale locale) {
        response.setLocale(locale);
    }
}
