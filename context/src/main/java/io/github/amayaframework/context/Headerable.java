package io.github.amayaframework.context;

import java.util.Enumeration;
import java.util.Map;

/**
 * An interface describing an abstract object holding headers.
 */
public interface Headerable {

    /**
     * Gets {@link Map} containing all object headers.
     *
     * @return the {@link Map} instance
     */
    Map<String, String> headers();

    /**
     * Checks if object containing header with given name.
     *
     * @param name the specified header name
     * @return true if it exists, false otherwise
     */
    boolean containsHeader(String name);

    /**
     * Gets value of header with given name.
     *
     * @param name the specified header name
     * @return string containing header value if it exists, null otherwise
     */
    String getHeader(String name);

    /**
     * Returns an enumeration of all values of the header with the specified name.
     *
     * @param name the specified header name
     * @return an {@link Enumeration} of all header values, or an empty enumeration if none exist
     */
    Enumeration<String> getHeadersEnum(String name);

    /**
     * Returns an iterable collection of all values of the header with the specified name.
     *
     * @param name the specified header name
     * @return an {@link Iterable} over all header values, or an empty iterable if none exist
     */
    Iterable<String> getHeaders(String name);
}
