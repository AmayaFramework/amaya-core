package io.github.amayaframework.core.contexts;

import io.github.amayaframework.core.inject.Path;
import io.github.amayaframework.core.inject.Provider;
import io.github.amayaframework.core.inject.Query;

import javax.servlet.http.Cookie;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * An interface representing a http request. Inherited from {@link HttpTransaction}.
 */
public interface HttpRequest extends HttpTransaction {
    /**
     * Returns all queried getParameters extracted from the request URI.
     *
     * @return {@link Map}
     */
    Map<String, List<String>> getQuery();

    void setQuery(Map<String, List<String>> queryParameters);

    /**
     * Returns a specific query parameter.
     *
     * @param name of specific query parameter
     * @return {@link List} of query parameter values
     */
    List<String> getQueries(String name);

    /**
     * Returns first value of a specific query parameter.
     *
     * @param name of specific query parameter
     * @return {@link String}
     */
    @Provider(Query.class)
    String getQuery(String name);

    /**
     * Returns a map of path getParameters that were extracted
     * from the request URI in accordance with the specification
     * described in the controller.
     *
     * @return {@link Map}
     */
    Map<String, Object> getPathParameters();

    void setPathParameters(Map<String, Object> pathParameters);

    /**
     * Returns the specified pass parameter, cast to {@link T}, in case of absence of
     * the parameter or an unsuccessful cast, returns null.
     *
     * @param name of the path parameter
     * @param <T>  the type to which the path parameter will be cast
     * @return parameter, cast to {@link T}
     */
    @Provider(Path.class)
    <T> T getPathParameter(String name);

    void setCookies(Map<String, Cookie> cookies);

    /**
     * Transform body into {@link String} and returns it.
     *
     * @return {@link String} body
     */
    String getBodyAsString();

    /**
     * Transform body into {@link InputStream} and returns it.
     *
     * @return {@link InputStream} body
     */
    InputStream getBodyAsInputStream();
}
