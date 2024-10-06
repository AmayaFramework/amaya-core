package io.github.amayaframework.context;

import io.github.amayaframework.http.HttpVersion;
import jakarta.servlet.http.Cookie;

import java.util.Map;

/**
 * An interface describing the abstract http protocol transaction.
 */
public interface HttpTransaction extends Transaction, Headerable {

    /**
     * Gets http protocol version of this transaction.
     *
     * @return the {@link HttpVersion} instance
     */
    HttpVersion getHttpVersion();

    /**
     * Gets {@link Map} containing cookies of this transaction.
     *
     * @return the {@link Map} instance
     */
    Map<String, Cookie> getCookies();

    /**
     * Gets {@link Cookie} of this transaction with given name.
     *
     * @param name the specified cookie name
     * @return the {@link Cookie} instance if it exists, null otherwise
     */
    Cookie getCookie(String name);
}
