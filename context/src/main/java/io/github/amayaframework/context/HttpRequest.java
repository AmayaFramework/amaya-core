package io.github.amayaframework.context;

import io.github.amayaframework.http.HttpMethod;

import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * An interface describing the abstract http protocol request.
 */
public interface HttpRequest extends Request, HttpTransaction {

    /**
     * Gets http method of this request.
     *
     * @return the {@link HttpMethod} instance
     */
    HttpMethod getMethod();

    /**
     * Gets full request URL, including scheme, hostname, port, request uri and query string.
     *
     * @return the {@link URL} instance containing request URL
     */
    URL getURL();

    /**
     * Returns the part of this request's URL from the protocol name up to the query string in the first line of the
     * HTTP request. The web container does not decode this String.
     *
     * @return the {@link URI} instance containing URI from http request line.
     */
    URI getRequestURI();

    /**
     * Gets request path from http request line.
     *
     * @return a string containing request path
     */
    String getPath();

    /**
     * Gets segments of request path.
     * <br>
     * For example, for path /a/b/c method returns [a, b, c].
     *
     * @return the {@link String} array containing path segments
     */
    String[] getPathSegments();

    /**
     * Gets the query string that is contained in the request URL after the path.
     * This method returns <code>null</code> if the URL does not have a query string.
     *
     * @return a <code>String</code> containing the query string or <code>null</code> if the URL
     * contains no query string. The value is not decoded by the container.
     */
    String getQueryString();

    // Headers

    /**
     * Gets the value of the specified request header as a {@link Date} instance.
     * Use this method with headers that contain dates, such as <code>If-Modified-Since</code>.
     * <p>
     * If the request did not have a header of the specified name, this method returns null.
     * If the header can't be converted to a date, the method throws an <code>IllegalArgumentException</code>.
     *
     * @param name a <code>String</code> specifying the name of the header
     * @return a <code>long</code> value representing the date specified in the header expressed as the number of
     * milliseconds since January 1, 1970 GMT, or -1 if the named header was not included with the request
     * @throws IllegalArgumentException If the header value can't be converted to a date
     */
    Date getDateHeader(String name);

    /**
     * Gets the value of the specified request header as an <code>int</code>. If the request does not have a header of
     * the specified name, this method returns -1. If the header cannot be converted to an integer, this method throws a
     * <code>NumberFormatException</code>.
     *
     * <p>
     * The header name is case-insensitive.
     *
     * @param name the specified header name
     * @return an integer expressing the value of the request header or -1
     * if the request doesn't have a header of this name
     * @throws NumberFormatException If the header value can't be converted to an <code>int</code>
     */
    int getIntHeader(String name);

    // Path parameters

    /**
     * Gets all path parameters as a map
     *
     * @return a map containing path parameters and their values
     */
    Map<String, Object> getPathParameters();

    /**
     * Gets a specific path parameter by its name.
     *
     * @param name the name of the path parameter to retrieve
     * @param <T>  the type of the parameter value
     * @return the value of the path parameter, or null if not found
     */
    <T> T getPathParameter(String name);

    // Query parameters

    /**
     * Gets all query parameters as a map.
     *
     * @return a map containing query parameters and their values
     */
    Map<String, List<Object>> getQueryParameters();

    /**
     * Gets a list of values for a specific query parameter by its name.
     *
     * @param name the name of the query parameter to retrieve
     * @param <T>  the type of the parameter values
     * @return a list of values for the query parameter, or null value if not found
     */
    <T> List<T> getQueryParameters(String name);

    /**
     * Gets a value for a specific query parameter by its name.
     *
     * @param name the name of the query parameter to retrieve
     * @param <T>  the type of the parameter value
     * @return the first value of the query parameter, or null if not found
     */
    <T> T getQueryParameter(String name);

    // Session parameters

    /**
     * Gets all session parameters as a map.
     *
     * @return a map containing session parameters and their values
     */
    Map<String, Object> getSessionParameters();

    /**
     * Gets a specific session parameter by its name.
     *
     * @param name the name of the session parameter to retrieve
     * @param <T>  the type of the parameter value
     * @return the value of the session parameter, or null if not found
     */
    <T> T getSessionParameter(String name);

    /**
     * Sets a session parameter with a specified name and value.
     *
     * @param name  the name of the session parameter to set
     * @param value the value to associate with the session parameter
     */
    void setSessionParameter(String name, Object value);

    // Trailer fields

    /**
     * Get the request trailer fields.
     * <p>
     * {@link #isTrailerFieldsReady()} should be called first to determine if it is safe to call this method without
     * causing an exception.
     * </p>
     *
     * @return A map of trailer fields in which all the keys are in lowercase, regardless of the case they had at the
     * protocol level. If there are no trailer fields, yet {@link #isTrailerFieldsReady} is returning true, the empty
     * map is returned.
     * @throws IllegalStateException if {@link #isTrailerFieldsReady()} is false
     */
    Map<String, String> getTrailerFields();

    /**
     * Checks if trailer fields are ready to read using {@link #getTrailerFields}.
     * These methods returns true immediately if it is known that there is no trailer in the request, for instance, the
     * underlying protocol (such as HTTP 1.0) does not support the trailer fields, or the request is not in chunked
     * encoding in HTTP 1.1. And the method also returns true if both of the following conditions are satisfied:
     * <ol type="a">
     * <li>the application has read all the request data and an EOF indication has been returned from the
     * {@link #getReader} or {@link #getInputStream}.
     * <li>all the trailer fields sent by the client have been received. Note that it is possible that the client
     * has sent no trailer fields.
     * </ol>
     *
     * @return a boolean whether trailer fields are ready to read
     */
    boolean isTrailerFieldsReady();
}
