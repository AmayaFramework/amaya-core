package io.github.amayaframework.core.pipeline;

import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.methods.HttpMethod;
import io.github.amayaframework.core.routes.MethodRoute;

import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * A simple container created to transfer data between pipeline actions.
 * Common form.
 */
public interface RequestData extends CompletableData {
    /**
     * @return
     */
    MethodRoute getRoute();

    /**
     * @return
     */
    String getPath();

    /**
     * @return
     */
    HttpMethod getMethod();

    /**
     * @return
     */
    HttpRequest getRequest();

    /**
     * @param request
     */
    void setRequest(HttpRequest request);

    /**
     * @return
     */
    InputStream getInputStream();

    /**
     * @return
     */
    String getContentType();

    /**
     * @return
     */
    Charset getCharset();
}
