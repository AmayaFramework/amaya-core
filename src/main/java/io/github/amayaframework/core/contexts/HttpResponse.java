package io.github.amayaframework.core.contexts;

import com.github.romanqed.util.Handler;
import com.github.romanqed.util.http.HeaderMap;
import com.github.romanqed.util.http.HttpCode;

import java.util.List;

/**
 * An interface representing a http response. Inherited from {@link HttpTransaction}.
 */
public interface HttpResponse extends HttpTransaction {
    /**
     * Returns response code
     *
     * @return {@link HttpCode}
     */
    HttpCode getCode();

    /**
     * Set response code, if null is passed instead of the code, it throws {@link NullPointerException}
     *
     * @param code {@link HttpCode} will be set
     */
    void setCode(HttpCode code);

    /**
     * Set header in internal header map, if null is passed instead of the value, it throws {@link NullPointerException}
     *
     * @param key   {@link String} header name
     * @param value {@link List} header values
     */
    void setHeader(String key, List<String> value);

    /**
     * Set header in internal header map, if null is passed instead of the value, it throws {@link NullPointerException}
     *
     * @param key   {@link String} header name
     * @param value {@link String} header value
     */
    void setHeader(String key, String value);

    /**
     * Remove header from internal header map
     *
     * @param key {@link String} header name
     * @return {@link List} removed header value
     */
    List<String> removeHeader(String key);

    /**
     * Returns map of all headers of response
     *
     * @return {@link HeaderMap}
     */
    HeaderMap getHeaderMap();

    /**
     * Returns contained stream handler
     *
     * @return {@link Handler} instance
     */
    Handler<FixedOutputStream> getOutputStreamHandler();

    /**
     * Sets output stream handler
     *
     * @param handler {@link Handler} handler to be set
     */
    void setOutputStreamHandler(Handler<FixedOutputStream> handler);
}
