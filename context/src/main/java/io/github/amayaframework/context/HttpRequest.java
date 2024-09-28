package io.github.amayaframework.context;

import io.github.amayaframework.http.HttpMethod;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 */
public interface HttpRequest extends Request, HttpTransaction {

    /**
     * @return
     */
    HttpMethod getMethod();

    /**
     * @return
     */
    URI getURI();

    /**
     * @return
     */
    String getPath();

    /**
     * @return
     */
    String[] getPathSegments();

    /**
     * @return
     */
    String getQueryString();

    // Headers

    /**
     * @param name
     * @return
     */
    Date getDateHeader(String name);

    /**
     * @param name
     * @return
     */
    int getIntHeader(String name);

    // Path parameters

    /**
     * @return
     */
    Map<String, Object> getPathParameters();

    /**
     * @param name
     * @param <T>
     * @return
     */
    <T> T getPathParameter(String name);

    // Query parameters

    /**
     * @return
     */
    Map<String, List<Object>> getQueryParameters();

    /**
     * @param name
     * @param <T>
     * @return
     */
    <T> List<T> getQueryParameters(String name);

    /**
     * @param name
     * @param <T>
     * @return
     */
    <T> T getQueryParameter(String name);

    // Session parameters

    /**
     * @return
     */
    Map<String, Object> getSessionParameters();

    /**
     * @param name
     * @param <T>
     * @return
     */
    <T> T getSessionParameter(String name);

    /**
     * @param name
     * @param value
     */
    void setSessionParameter(String name, Object value);

    // Trailer fields

    /**
     * @return
     */
    Map<String, String> getTrailerFields();

    /**
     * @return
     */
    boolean isTrailerFieldsReady();
}
