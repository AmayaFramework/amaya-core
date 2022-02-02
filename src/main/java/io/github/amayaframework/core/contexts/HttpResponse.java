package io.github.amayaframework.core.contexts;

import com.github.romanqed.jutils.http.HeaderMap;
import com.github.romanqed.jutils.http.HttpCode;

import java.util.List;

/**
 * A class representing a http response. Inherited from {@link HttpTransaction}.
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
     * @return {@link StreamHandler} instance
     */
    StreamHandler getOutputStreamHandler();

    /**
     * Sets output stream handler
     *
     * @param handler {@link StreamHandler} handler to be set
     */
    void setOutputStreamHandler(StreamHandler handler);
}
