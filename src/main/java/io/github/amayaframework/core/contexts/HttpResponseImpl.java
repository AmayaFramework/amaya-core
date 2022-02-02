package io.github.amayaframework.core.contexts;

import com.github.romanqed.jutils.http.HeaderMap;
import com.github.romanqed.jutils.http.HttpCode;
import io.github.amayaframework.core.util.AmayaConfig;
import io.github.amayaframework.core.util.ParseUtil;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

class HttpResponseImpl extends AbstractHttpTransaction implements HttpResponse {
    private final Charset charset = AmayaConfig.INSTANCE.getCharset();
    private final HeaderMap headers;
    private HttpCode code;
    private StreamHandler handler;

    public HttpResponseImpl(HttpCode code, HeaderMap headers) {
        this.code = Objects.requireNonNull(code);
        this.headers = Objects.requireNonNull(headers);
        this.cookies = new HashMap<>();
    }

    public HttpResponseImpl(HttpCode code) {
        this(code, new HeaderMap());
    }

    public HttpResponseImpl() {
        this(HttpCode.OK, new HeaderMap());
    }

    @Override
    public HttpCode getCode() {
        return code;
    }

    @Override
    public void setCode(HttpCode code) {
        this.code = Objects.requireNonNull(code);
    }

    @Override
    public void setHeader(String key, List<String> value) {
        Objects.requireNonNull(value);
        headers.put(key, value);
    }

    @Override
    public void setHeader(String key, String value) {
        Objects.requireNonNull(value);
        setHeader(key, Collections.singletonList(value));
    }

    @Override
    public List<String> removeHeader(String key) {
        return headers.remove(key);
    }

    @Override
    public HeaderMap getHeaderMap() {
        return headers;
    }

    @Override
    public StreamHandler getOutputStreamHandler() {
        return handler;
    }

    @Override
    public void setOutputStreamHandler(StreamHandler handler) {
        this.handler = Objects.requireNonNull(handler);
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

    @Override
    public void setContentType(ContentType type) {
        super.setContentType(type);
        headers.set(ParseUtil.CONTENT_HEADER, ParseUtil.generateContentHeader(type, charset));
    }
}
