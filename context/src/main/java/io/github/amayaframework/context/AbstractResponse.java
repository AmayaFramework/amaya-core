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
    protected final T response;
    protected final String protocol;
    protected final String scheme;
    protected Charset charset;
    protected long length;
    protected MimeData data;
    protected String contentType;

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
    public ServletOutputStream outputStream() throws IOException {
        return response.getOutputStream();
    }

    @Override
    public PrintWriter writer() throws IOException {
        return response.getWriter();
    }

    @Override
    public boolean sent() {
        return response.isCommitted();
    }

    @Override
    public void reset() {
        response.reset();
    }

    @Override
    public int bufferSize() {
        return response.getBufferSize();
    }

    @Override
    public void bufferSize(int size) {
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
    public Charset charset() {
        if (charset == null || !charset.name().equals(response.getCharacterEncoding())) {
            charset = Charset.forName(response.getCharacterEncoding());
        }
        return charset;
    }

    @Override
    public void charset(Charset charset) {
        response.setCharacterEncoding(charset.name());
        this.charset = charset;
    }

    @Override
    public long contentLength() {
        return length;
    }

    @Override
    public void contentLength(long length) {
        response.setContentLengthLong(length);
        this.length = length;
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
        var contentType = response.getContentType();
        if (contentType == null) {
            return null;
        }
        if (contentType.equals(this.contentType)) {
            return data;
        }
        data = parseMimeData(contentType);
        return data;
    }

    /**
     * Formats {@link MimeData} instance as mime string, e.g. 'group/type;param=value'.
     *
     * @param data the specified {@link MimeData} instance to be formatted
     * @return mime string
     */
    protected abstract String formatMimeData(MimeData data);

    @Override
    public void mimeData(MimeData data) {
        if (data == null) {
            response.setContentType(null);
            contentType = null;
        } else {
            var type = formatMimeData(data);
            response.setContentType(type);
            contentType = type;
        }
        this.data = data;
    }

    @Override
    public void mimeType(MimeType type) {
        if (type == null) {
            response.setContentType(null);
            contentType = null;
            data = null;
        } else {
            mimeData(new MimeData(type));
        }
    }

    @Override
    public String protocol() {
        return protocol;
    }

    @Override
    public String scheme() {
        return scheme;
    }

    @Override
    public Locale locale() {
        return response.getLocale();
    }

    @Override
    public void locale(Locale locale) {
        response.setLocale(locale);
    }
}
