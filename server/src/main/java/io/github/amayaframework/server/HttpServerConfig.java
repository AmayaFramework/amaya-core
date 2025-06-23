package io.github.amayaframework.server;

import io.github.amayaframework.http.HttpVersion;
import jakarta.servlet.ServletContext;

import java.net.InetSocketAddress;

/**
 * An interface describing the http server config.
 */
public interface HttpServerConfig extends ServerConfig {

    /**
     * Returns the {@link ServletContext} associated with this server.
     * <p>
     * If the implementation does not support a {@code ServletContext}, this method will return {@code null}.
     * Otherwise, the returned context is guaranteed to be fully initialized and ready for use.
     *
     * @return the {@code ServletContext} if supported, or {@code null} otherwise
     */
    ServletContext getServletContext();

    /**
     * Gets the http version used. The default value depends on the implementation.
     *
     * @return the {@link HttpVersion} instance
     */
    HttpVersion getHttpVersion();

    /**
     * Sets the http version used by server.
     *
     * @param version the specified {@link HttpVersion} instance to be set, must be non-null
     * @throws IllegalStateException if server started
     */
    void setHttpVersion(HttpVersion version);

    /**
     * Adds given address to listened set and starts listen it with specified http protocol.
     *
     * @param address the specified address to be listened, must be non-null
     * @param version the specified http version, must be non-null
     */
    void addAddress(InetSocketAddress address, HttpVersion version);

    /**
     * Gets the {@link MimeFormatter} instance used by server. The default value depends on the implementation.
     *
     * @return the {@link MimeFormatter} instance
     */
    MimeFormatter getMimeFormatter();

    /**
     * Sets the {@link MimeFormatter} instance used by server.
     *
     * @param formatter the {@link MimeFormatter} instance, must be non-null
     * @throws IllegalStateException if server started
     */
    void setMimeFormatter(MimeFormatter formatter);

    /**
     * Gets the {@link MimeParser} instance used by server. The default value depends on the implementation.
     *
     * @return the {@link MimeParser} instance
     */
    MimeParser getMimeParser();

    /**
     * Sets the {@link MimeParser} instance used by server.
     *
     * @param parser the {@link MimeParser} instance, must be non-null
     * @throws IllegalStateException if server started
     */
    void setMimeParser(MimeParser parser);

    /**
     * Gets the {@link PathTokenizer} instance used by server. The default value depends on the implementation.
     *
     * @return the {@link PathTokenizer} instance
     */
    PathTokenizer getPathTokenizer();

    /**
     * Sets the {@link PathTokenizer} instance used by server.
     *
     * @param tokenizer the {@link PathTokenizer} instance, must be non-null
     * @throws IllegalStateException if server started
     */
    void setPathTokenizer(PathTokenizer tokenizer);
}
