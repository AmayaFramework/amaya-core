package io.github.amayaframework.core.contexts;

import com.github.romanqed.jutils.http.HeaderMap;
import com.github.romanqed.jutils.http.HttpCode;
import io.github.amayaframework.core.util.AmayaConfig;
import io.github.amayaframework.core.util.ParseUtil;

import java.nio.charset.Charset;
import java.util.*;

/**
 * A class representing a http response. Inherited from {@link HttpTransaction}.
 */
public class HttpResponse extends AbstractHttpTransaction {
    private final Charset charset = AmayaConfig.INSTANCE.getCharset();
    private final HeaderMap headers;
    private HttpCode code;
    private StreamHandler handler;

    /**
     * Creates HttpResponse with code and header map
     *
     * @param code    {@link HttpCode} will be set
     * @param headers {@link HeaderMap} map will be set as internal
     */
    public HttpResponse(HttpCode code, HeaderMap headers) {
        this.code = Objects.requireNonNull(code);
        this.headers = Objects.requireNonNull(headers);
        this.cookies = new HashMap<>();
    }

    /**
     * Creates HttpResponse with code and empty header map
     *
     * @param code {@link HttpCode} will be set
     */
    public HttpResponse(HttpCode code) {
        this(code, new HeaderMap());
    }

    /**
     * Creates HttpResponse with {@link HttpCode#OK} code and empty header map
     */
    public HttpResponse() {
        this(HttpCode.OK, new HeaderMap());
    }

    /**
     * Returns response code
     *
     * @return {@link HttpCode}
     */
    public HttpCode getCode() {
        return code;
    }

    /**
     * Set response code, if null is passed instead of the code, it throws {@link NullPointerException}
     *
     * @param code {@link HttpCode} will be set
     */
    public void setCode(HttpCode code) {
        this.code = Objects.requireNonNull(code);
    }

    /**
     * Set header in internal header map, if null is passed instead of the value, it throws {@link NullPointerException}
     *
     * @param key   {@link String} header name
     * @param value {@link List} header values
     */
    public void setHeader(String key, List<String> value) {
        Objects.requireNonNull(value);
        headers.put(key, value);
    }

    /**
     * Set header in internal header map, if null is passed instead of the value, it throws {@link NullPointerException}
     *
     * @param key   {@link String} header name
     * @param value {@link String} header value
     */
    public void setHeader(String key, String value) {
        Objects.requireNonNull(value);
        setHeader(key, Collections.singletonList(value));
    }

    /**
     * Remove header from internal header map
     *
     * @param key {@link String} header name
     * @return {@link List} removed header value
     */
    public List<String> removeHeader(String key) {
        return headers.remove(key);
    }

    /**
     * Returns map of all headers of response
     *
     * @return {@link HeaderMap}
     */
    public HeaderMap getHeaderMap() {
        return headers;
    }

    @Override
    public List<String> getHeaders(String key) {
        return headers.get(key);
    }

    @Override
    public void setBody(Object body) {
        if (type == null || !type.isString()) {
            String message = "Illegal content type, use stream handler or configure content type correctly";
            throw new IllegalStateException(message);
        }
        super.setBody(body);
    }

    /**
     * Returns contained stream handler
     *
     * @return {@link StreamHandler} instance
     */
    public StreamHandler getOutputStreamHandler() {
        return handler;
    }

    /**
     * Sets output stream handler
     *
     * @param handler {@link StreamHandler} handler to be set
     */
    public void setOutputStreamHandler(StreamHandler handler) {
        this.handler = Objects.requireNonNull(handler);
    }

    @Override
    public void setContentType(ContentType type) {
        super.setContentType(type);
        String headerValue = type.getHeader();
        if (type.isString()) {
            headerValue += "; " + ParseUtil.CONTENT_CHARSET + charset.name().toLowerCase(Locale.ROOT);
        }
        headers.set(ParseUtil.CONTENT_HEADER, headerValue);
    }
}
