package io.github.amayaframework.http;

import java.util.Locale;
import java.util.Map;

/**
 * A class that implements the information holder about the http method.
 * Implements {@link HttpDefinition}.
 */
public final class HttpMethod implements HttpDefinition {
    // Since 1.0
    public static final HttpMethod GET = new HttpMethod("GET", HttpVersion.HTTP_1_0, false);
    public static final HttpMethod HEAD = new HttpMethod("HEAD", HttpVersion.HTTP_1_0, false);
    public static final HttpMethod POST = new HttpMethod("POST", HttpVersion.HTTP_1_0, true);
    // Since 1.1
    public static final HttpMethod PUT = new HttpMethod("PUT", HttpVersion.HTTP_1_1, true);
    public static final HttpMethod DELETE = new HttpMethod("DELETE", HttpVersion.HTTP_1_1, true);
    public static final HttpMethod CONNECT = new HttpMethod("CONNECT", HttpVersion.HTTP_1_1, false);
    public static final HttpMethod OPTIONS = new HttpMethod("OPTIONS", HttpVersion.HTTP_1_1, false);
    public static final HttpMethod TRACE = new HttpMethod("TRACE", HttpVersion.HTTP_1_1, false);
    public static final HttpMethod PATCH = new HttpMethod("PATCH", HttpVersion.HTTP_1_1, true);
    private static final Map<String, HttpMethod> METHODS = Map.of(
            "GET", GET,
            "HEAD", HEAD,
            "POST", POST,
            "PUT", PUT,
            "DELETE", DELETE,
            "CONNECT", CONNECT,
            "OPTIONS", OPTIONS,
            "TRACE", TRACE,
            "PATCH", PATCH
    );
    final String name;
    final HttpVersion since;
    final boolean allowBody;

    /**
     * Constructs {@link HttpMethod} instance with given method name, http version defining this method
     * and request body flag.
     *
     * @param name      the http method name
     * @param since     the http version defining this method
     * @param allowBody request body flag
     */
    public HttpMethod(String name, HttpVersion since, boolean allowBody) {
        this.name = name;
        this.since = since;
        this.allowBody = allowBody;
    }

    /**
     * Searches among predefined methods for the method with the specified name.
     *
     * @param name the specified http method name
     * @return {@link HttpMethod} instance if found, null otherwise
     */
    public static HttpMethod of(String name) {
        return METHODS.get(name.toUpperCase(Locale.ENGLISH));
    }

    /**
     * Returns {@link Map} instance containing all predefined http methods.
     *
     * @return an unmodifiable {@link Map} instance
     */
    public static Map<String, HttpMethod> all() {
        return METHODS;
    }

    /**
     * Gets name of this http method.
     *
     * @return http method name
     */
    public String getName() {
        return name;
    }

    @Override
    public HttpVersion since() {
        return since;
    }

    /**
     * Checks whether a request with this method can have a body.
     *
     * @return true if body allowed, false otherwise
     */
    public boolean isAllowBody() {
        return allowBody;
    }

    @Override
    public boolean isSupported(HttpVersion version) {
        return version.number >= since.number;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        var that = (HttpMethod) object;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
