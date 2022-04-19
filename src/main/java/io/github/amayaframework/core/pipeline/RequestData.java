package io.github.amayaframework.core.pipeline;

import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.methods.HttpMethod;
import io.github.amayaframework.core.routes.MethodRoute;
import io.github.amayaframework.core.util.CompletableData;

import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * A container for transferring data between the actions of the input pipeline.
 */
public interface RequestData extends CompletableData {
    /**
     * @return the current route containing information about the bound method and path template.
     */
    MethodRoute getRoute();

    /**
     * @return the actual path that the client sent.
     */
    String getPath();

    /**
     * @return http request method
     */
    HttpMethod getMethod();

    /**
     * @return the request processed in the previous actions,
     * or null if the request processing has not yet been completed/has not occurred.
     */
    HttpRequest getRequest();

    /**
     * Sets a new http request instance.
     *
     * @param request which will be set, must not be null
     */
    void setRequest(HttpRequest request);

    /**
     * @return an input stream containing unread data from the client.
     */
    InputStream getInputStream();

    /**
     * <h3>Important!</h3>
     * <p>If the http request has already been processed,
     * it is strongly recommended to get this information from it.</p>
     *
     * @return raw content of the content type header
     */
    String getContentType();

    /**
     * <h3>Important!</h3>
     * <h3>Each time it will parse the raw content of the header!</h3>
     * <p>If the http request has already been processed,
     * it is strongly recommended to get this information from it.</p>
     *
     * @return the encoding passed in the request or used by default
     */
    Charset getCharset();
}
