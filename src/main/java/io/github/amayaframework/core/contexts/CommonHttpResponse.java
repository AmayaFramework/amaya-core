package io.github.amayaframework.core.contexts;

import com.github.romanqed.util.Handler;
import io.github.amayaframework.http.HeaderMap;
import io.github.amayaframework.http.HttpCode;
import io.github.amayaframework.http.HttpHeaderMap;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class CommonHttpResponse extends AbstractHttpTransaction implements HttpResponse {
    private final HeaderMap headers;
    private HttpCode code;
    private Handler<FixedOutputStream> handler;

    protected CommonHttpResponse(HttpCode code, HeaderMap headers) {
        this.code = Objects.requireNonNull(code);
        this.headers = Objects.requireNonNull(headers);
        this.cookies = new HashMap<>();
    }

    public CommonHttpResponse(HttpCode code) {
        this(code, new HttpHeaderMap());
    }

    public CommonHttpResponse() {
        this(HttpCode.OK, new HttpHeaderMap());
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
    public Handler<FixedOutputStream> getOutputStreamHandler() {
        return handler;
    }

    @Override
    public void setOutputStreamHandler(Handler<FixedOutputStream> handler) {
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
}
