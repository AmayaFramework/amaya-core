package io.github.amayaframework.server;

import io.github.amayaframework.http.HttpVersion;

/**
 * An interface describing the http server config.
 */
public interface HttpServerConfig extends ServerConfig {

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
