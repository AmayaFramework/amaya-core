package io.github.amayaframework.http;

import java.util.List;
import java.util.Locale;
import java.util.Map;

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

    public HttpVersion(String tag, int number) {
        this.tag = tag;
        this.number = number;
    }

    public static HttpVersion of(String tag) {
        return VERSIONS.get(tag.toUpperCase(Locale.ENGLISH));
    }

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

    public static Iterable<HttpVersion> all() {
        return List.of(HTTP_1_0, HTTP_1_1, HTTP_2_0);
    }

    public int getNumber() {
        return number;
    }

    public String getTag() {
        return tag;
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
