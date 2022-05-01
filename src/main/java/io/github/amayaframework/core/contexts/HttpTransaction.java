package io.github.amayaframework.core.contexts;

import io.github.amayaframework.http.ContentType;

import javax.servlet.http.Cookie;
import java.util.Collection;
import java.util.List;

/**
 * <p>An interface describing the general case of a http transaction.</p>
 * <p>It is basic for the {@link HttpRequest} and {@link HttpResponse} classes</p>
 */
public interface HttpTransaction {
    /**
     * Returns the transaction body unchanged
     *
     * @return {@link Object}
     */
    Object getBody();

    /**
     * A body setter that allows you to change the transaction body during its processing.
     * It can also be used by the end user of the transaction.
     *
     * @param body body object
     */
    void setBody(Object body);

    /**
     * Returns values of specified header
     *
     * @param key {@link String} header key
     * @return {@link List} header values
     */
    List<String> getHeaders(String key);

    /**
     * Returns first value of specified header
     *
     * @param key header key
     * @return first value of header
     */
    String getHeader(String key);

    /**
     * Returns a list of cookies that belong to this transaction.
     *
     * @return {@link Collection} of {@link Cookie}
     */
    Collection<Cookie> getCookies();

    /**
     * Sets the cookie for this transaction.
     *
     * @param cookie {@link Cookie} value to be set. Must be not null.
     */
    void setCookie(Cookie cookie);

    /**
     * Returns a cookie (if one exists) by name. If the cookie is not found, returns null.
     *
     * @param name the name that will be searched for. Must be not null.
     * @return found {@link Cookie} or null
     */
    Cookie getCookie(String name);

    /**
     * Returns content type of transaction
     *
     * @return {@link ContentType} enum
     */
    ContentType getContentType();

    /**
     * Sets content type of transaction
     *
     * @param type {@link ContentType} enum
     */
    void setContentType(ContentType type);
}
