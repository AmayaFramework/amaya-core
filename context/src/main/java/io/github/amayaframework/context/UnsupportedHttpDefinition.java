package io.github.amayaframework.context;

import io.github.amayaframework.http.HttpDefinition;
import io.github.amayaframework.http.HttpVersion;

/**
 * Thrown to indicate that given http definition is not supported by current http version.
 */
public class UnsupportedHttpDefinition extends RuntimeException {
    /**
     * Current http version.
     */
    private final HttpVersion version;
    /**
     * Unsupported http definition.
     */
    private final HttpDefinition definition;

    /**
     * Constructs an {@link UnsupportedHttpDefinition} with the specified {@link HttpVersion} and
     * unsupported {@link HttpDefinition}.
     *
     * @param version    the current {@link HttpVersion}
     * @param definition the unsupported {@link HttpDefinition}
     */
    public UnsupportedHttpDefinition(HttpVersion version, HttpDefinition definition) {
        super(getMessage(version, definition));
        this.version = version;
        this.definition = definition;
    }

    private static String getMessage(HttpVersion version, HttpDefinition definition) {
        return definition +
                " is not supported by " +
                version +
                ", required version is " +
                definition.since();
    }

    /**
     * Gets current {@link HttpVersion}.
     *
     * @return the {@link HttpVersion} instance
     */
    public HttpVersion getVersion() {
        return version;
    }

    /**
     * Gets unsupported {@link HttpDefinition}.
     *
     * @return the {@link HttpDefinition} instance
     */
    public HttpDefinition getDefinition() {
        return definition;
    }
}
