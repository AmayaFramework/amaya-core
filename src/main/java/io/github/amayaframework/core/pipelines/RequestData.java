package io.github.amayaframework.core.pipelines;

import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.methods.HttpMethod;
import io.github.amayaframework.core.routes.MethodRoute;

import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * A simple container created to transfer data between pipeline actions.
 * Common form.
 */
public interface RequestData {
    MethodRoute getRoute();

    String getPath();

    HttpMethod getMethod();

    HttpRequest getRequest();

    void setRequest(HttpRequest request);

    InputStream getInputStream();

    String getContentType();

    Charset getCharset();
}
