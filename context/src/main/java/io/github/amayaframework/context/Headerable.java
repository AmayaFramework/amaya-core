package io.github.amayaframework.context;

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
    Map<String, String> getHeaders();

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
}
