package io.github.amayaframework.http;

import java.util.Locale;
import java.util.Map;

/**
 * A class that implements the information holder about the http protocol version.
 * <br>
 * Supports version comparison. Implements {@link Comparable} interface.
 */
public final class HttpVersion implements Comparable<HttpVersion> {
    public static final HttpVersion HTTP_1_0 = new HttpVersion("HTTP/1.0", 10);
    public static final HttpVersion HTTP_1_1 = new HttpVersion("HTTP/1.1", 11);
    public static final HttpVersion HTTP_2_0 = new HttpVersion("HTTP/2.0", 20);

    private static final Map<String, HttpVersion> VERSIONS = Map.of(
            "HTTP/1.0", HTTP_1_0,
            "HTTP/1", HTTP_1_0,
            "HTTP/1.1", HTTP_1_1,
            "HTTP/2.0", HTTP_2_0,
            "HTTP/2", HTTP_2_0
    );
    final String tag;
    final int number;

    /**
     * Constructs {@link HttpVersion} instance with given version tag and number.
     *
     * @param tag    the specified version tag, for example, 'HTTP/1.1'
     * @param number the specified version number in format 'xy', where x is major and y is minor
     */
    public HttpVersion(String tag, int number) {
        this.tag = tag;
        this.number = number;
    }

    /**
     * Searches among predefined versions for the version with the specified tag.
     * If the version is unknown, it returns null.
     *
     * @param tag the specified version tag
     * @return {@link HttpVersion} instance if found, null otherwise
     */
    public static HttpVersion of(String tag) {
        return VERSIONS.get(tag.toUpperCase(Locale.ENGLISH));
    }

    /**
     * Searches among predefined versions for the version with the specified number.
     * If the version is unknown, it returns null.
     * <br>
     * It supports shortcuts, for example, '1' instead of '10'.
     *
     * @param number the specified version number
     * @return {@link HttpVersion} instance if found, null otherwise
     */
    public static HttpVersion of(int number) {
        switch (number) {
            case 1:
            case 10:
                return HTTP_1_0;
            case 11:
                return HTTP_1_1;
            case 2:
            case 20:
                return HTTP_2_0;
        }
        return null;
    }

    /**
     * Returns {@link Map} instance containing all predefined http versions.
     *
     * @return an unmodifiable {@link Map} instance
     */
    public static Map<String, HttpVersion> all() {
        return VERSIONS;
    }

    /**
     * Gets the number of this http protocol version.
     *
     * @return the number of protocol version
     */
    public int getNumber() {
        return number;
    }

    /**
     * Gets the tag containing full protocol version qualifier.
     * <br>
     * For example, 'HTTP/1.1'.
     *
     * @return the tag of protocol version
     */
    public String getTag() {
        return tag;
    }

    /**
     * Checks if given version newer than this one.
     *
     * @param version the specified version to be checked
     * @return true if this version older, false otherwise
     */
    public boolean before(HttpVersion version) {
        return number < version.number;
    }

    /**
     * Checks if given version older than this one.
     *
     * @param version the specified version to be checked
     * @return true if this version newer, false otherwise
     */
    public boolean after(HttpVersion version) {
        return number > version.number;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        var that = (HttpVersion) object;
        return number == that.number;
    }

    @Override
    public int hashCode() {
        return number;
    }

    @Override
    public String toString() {
        return tag;
    }

    @Override
    public int compareTo(HttpVersion o) {
        return Integer.compare(number, o.number);
    }
}
