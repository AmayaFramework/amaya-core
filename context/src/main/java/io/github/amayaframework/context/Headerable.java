package io.github.amayaframework.context;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * An interface describing an abstract object holding headers.
 */
public interface Headerable {

    /**
     * Gets a {@link Map} containing all object headers, where each key corresponds to
     * a header name and the value is the <b>first</b> header value associated with it.
     * <p>
     * If a header has multiple values, only the first one will be returned.
     * For complete access to all values, use {@link #multiHeaders()}.
     *
     * @return a {@link Map} of header names to their first value
     */
    Map<String, String> headers();

    /**
     * Gets a {@link Map} containing all object headers, where each key corresponds to
     * a header name and the value is a list of <b>all</b> values associated with it.
     * <p>
     * This method preserves the full multi-value semantics of HTTP headers
     * (e.g. {@code Accept}, {@code Set-Cookie}).
     *
     * @return a {@link Map} of header names to lists of all their values
     */
    Map<String, List<String>> multiHeaders();

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
    String header(String name);

    /**
     * Returns an enumeration of all values of the header with the specified name.
     *
     * @param name the specified header name
     * @return an {@link Enumeration} of all header values, or an empty enumeration if none exist
     */
    Enumeration<String> headersEnum(String name);

    /**
     * Returns an iterable collection of all values of the header with the specified name.
     *
     * @param name the specified header name
     * @return an {@link Iterable} over all header values, or an empty iterable if none exist
     */
    Iterable<String> headers(String name);
}
