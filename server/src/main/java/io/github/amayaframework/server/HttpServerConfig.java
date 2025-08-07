package io.github.amayaframework.server;

import io.github.amayaframework.http.HttpVersion;
import jakarta.servlet.ServletContext;

import java.net.InetSocketAddress;

/**
 * An interface describing the configuration options for an HTTP server.
 * <p>
 * Extends {@link ServerConfig} by adding HTTP-specific configuration properties such as
 * HTTP protocol version, MIME handling, and path tokenization.
 * <p>
 * Implementations are expected to allow configuration changes only when the server is stopped.
 * Attempts to modify configuration during runtime should throw {@link IllegalStateException}.
 * <p>
 * Configuration changes are reflected on the running server if allowed.
 * <p>
 * Provides access to the underlying {@link jakarta.servlet.ServletContext} if available.
 */
public interface HttpServerConfig extends ServerConfig {

    /**
     * Returns the {@link ServletContext} associated with this server.
     * <p>
     * If the implementation does not support a {@code ServletContext}, this method returns {@code null}.
     * Otherwise, the returned context is guaranteed to be fully initialized and ready for use.
     *
     * @return the {@code ServletContext} if supported, or {@code null} otherwise
     */
    ServletContext servletContext();

    /**
     * Gets the HTTP protocol version used by the server.
     * <p>
     * The default value depends on the implementation.
     *
     * @return the current {@link HttpVersion} in use
     */
    HttpVersion httpVersion();

    /**
     * Sets the HTTP protocol version for the server.
     * <p>
     * This operation can be performed at any time, but the change
     * will only take effect after the server is restarted.
     *
     * @param version the {@link HttpVersion} to set; must be non-null
     */
    void httpVersion(HttpVersion version);

    /**
     * Adds the specified address to the set of addresses the server listens to,
     * using the provided HTTP protocol version.
     *
     * @param address the {@link InetSocketAddress} to listen on; must be non-null
     * @param version the {@link HttpVersion} to use for this address; must be non-null
     */
    void addAddress(InetSocketAddress address, HttpVersion version);

    /**
     * Gets the {@link MimeFormatter} instance used by the server for formatting MIME types.
     * <p>
     * The default formatter depends on the implementation.
     *
     * @return the current {@link MimeFormatter} instance
     */
    MimeFormatter mimeFormatter();

    /**
     * Sets the {@link MimeFormatter} instance used by the server.
     * <p>
     * This operation can be performed at any time, but the change
     * will only take effect after the server is restarted.
     *
     * @param formatter the {@link MimeFormatter} to use; must be non-null
     */
    void mimeFormatter(MimeFormatter formatter);

    /**
     * Gets the {@link MimeParser} instance used by server. The default value depends on the implementation.
     *
     * @return the {@link MimeParser} instance
     */
    MimeParser mimeParser();

    /**
     * Sets the {@link MimeParser} instance used by the server.
     * <p>
     * This operation can be performed at any time, but the change
     * will only take effect after the server is restarted.
     *
     * @param parser the {@link MimeParser} to use; must be non-null
     */
    void mimeParser(MimeParser parser);

    /**
     * Gets the {@link PathTokenizer} instance used by the server for tokenizing request paths.
     * <p>
     * The default tokenizer depends on the implementation.
     *
     * @return the current {@link PathTokenizer} instance
     */
    PathTokenizer pathTokenizer();

    /**
     * Sets the {@link PathTokenizer} instance used by the server.
     * <p>
     * This operation can be performed at any time, but the change
     * will only take effect after the server is restarted.
     *
     * @param tokenizer the {@link PathTokenizer} to use; must be non-null
     */
    void pathTokenizer(PathTokenizer tokenizer);
}
