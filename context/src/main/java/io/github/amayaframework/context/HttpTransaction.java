package io.github.amayaframework.context;

import io.github.amayaframework.http.HttpVersion;
import jakarta.servlet.http.Cookie;

import java.util.Map;

/**
 *
 */
public interface HttpTransaction extends Transaction, Headerable {

    /**
     * @return
     */
    HttpVersion getHttpVersion();

    /**
     * @return
     */
    Map<String, Cookie> getCookies();

    /**
     * @param name
     * @return
     */
    Cookie getCookie(String name);
}
