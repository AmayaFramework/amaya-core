package io.github.amayaframework.http;

/**
 * An interface describing the abstract part of the HTTP protocol.
 */
public interface HttpDefinition {

    /**
     * Gets the first version with which this definition is supported.
     *
     * @return {@link HttpVersion} instance
     */
    HttpVersion since();

    /**
     * Checks whether this definition is supported by the specified protocol version.
     *
     * @param version the specified protocol version, must be non-null
     * @return true if supported, false otherwise
     */
    boolean isSupported(HttpVersion version);
}
