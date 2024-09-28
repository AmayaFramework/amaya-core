package io.github.amayaframework.context;

import io.github.amayaframework.http.HttpCode;
import jakarta.servlet.http.Cookie;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.function.Supplier;

/**
 *
 */
public interface HttpResponse extends Response, HttpTransaction {

    /**
     * @param cookie
     */
    void setCookie(Cookie cookie);

    // Any header type

    /**
     * @param name
     * @param value
     */
    void setHeader(String name, Object value);

    /**
     * @param name
     * @param value
     */
    void addHeader(String name, Object value);

    // String headers

    /**
     * @param name
     * @param value
     */
    void setHeader(String name, String value);

    /**
     * @param name
     * @param value
     */
    void addHeader(String name, String value);

    // Date headers

    /**
     * @param name
     * @param date
     */
    void setHeader(String name, Date date);

    /**
     * @param name
     * @param date
     */
    void addHeader(String name, Date date);

    /**
     * @param name
     * @param date
     */
    void setHeader(String name, long date);

    /**
     * @param name
     * @param date
     */
    void addHeader(String name, long date);

    /**
     * @return
     */
    HttpCode getStatus();

    /**
     * @param code
     */
    void setStatus(HttpCode code);

    /**
     * @param code
     * @param message
     * @throws IOException
     */
    void sendError(HttpCode code, String message) throws IOException;

    /**
     * @param code
     * @throws IOException
     */
    void sendError(HttpCode code) throws IOException;

    /**
     * @param location
     * @throws IOException
     */
    void sendRedirect(String location) throws IOException;

    /**
     * @return
     */
    Supplier<Map<String, String>> getTrailerFields();

    /**
     * @param supplier
     */
    void setTrailerFields(Supplier<Map<String, String>> supplier);
}
