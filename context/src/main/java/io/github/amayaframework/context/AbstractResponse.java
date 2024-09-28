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
 * @param <T>
 */
public abstract class AbstractResponse<T extends ServletResponse> implements Response {
    /**
     *
     */
    protected final T response;
    /**
     *
     */
    protected final String protocol;
    /**
     *
     */
    protected final String scheme;
    /**
     *
     */
    protected Charset charset;
    /**
     *
     */
    protected long length;
    /**
     *
     */
    protected MimeData data;

    /**
     * @param response
     * @param protocol
     * @param scheme
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
        this.charset = charset;
        response.setCharacterEncoding(charset.name());
    }

    @Override
    public long getContentLength() {
        return length;
    }

    @Override
    public void setContentLength(long length) {
        this.length = length;
        response.setContentLengthLong(length);
    }

    @Override
    public MimeData getMimeData() {
        return data;
    }

    @Override
    public void setMimeData(MimeData data) {
        this.data = data;
        response.setContentType(formatMimeData(data));
    }

    /**
     * @param data
     * @return
     */
    protected abstract String formatMimeData(MimeData data);

    @Override
    public void setMimeType(MimeType type) {
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
