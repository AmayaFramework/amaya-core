package io.github.amayaframework.http;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public final class HttpVersionTest {

    @Test
    public void testPredefined() {
        // 1.0
        assertEquals(HttpVersion.HTTP_1_0, HttpVersion.of(10));
        assertEquals(HttpVersion.HTTP_1_0, HttpVersion.of("HTTP/1"));
        assertEquals(HttpVersion.HTTP_1_0, HttpVersion.of("HTTP/1.0"));
        // 1.1
        assertEquals(HttpVersion.HTTP_1_1, HttpVersion.of(11));
        assertEquals(HttpVersion.HTTP_1_1, HttpVersion.of("HTTP/1.1"));
        // 2.0
        assertEquals(HttpVersion.HTTP_2_0, HttpVersion.of(20));
        assertEquals(HttpVersion.HTTP_2_0, HttpVersion.of("HTTP/2"));
        assertEquals(HttpVersion.HTTP_2_0, HttpVersion.of("HTTP/2.0"));
        // 3.0
        assertEquals(HttpVersion.HTTP_3_0, HttpVersion.of(30));
        assertEquals(HttpVersion.HTTP_3_0, HttpVersion.of("HTTP/3"));
        assertEquals(HttpVersion.HTTP_3_0, HttpVersion.of("HTTP/3.0"));
    }

    @Test
    public void testComparisons() {
        var http1 = HttpVersion.HTTP_1_0;
        var http11 = HttpVersion.HTTP_1_1;
        var http2 = HttpVersion.HTTP_2_0;
        var http3 = HttpVersion.HTTP_3_0;
        // http/1
        assertTrue(http1.before(http11));
        assertFalse(http1.after(http11));
        assertTrue(http1.before(http2));
        assertFalse(http1.after(http2));
        // http/1.1
        assertTrue(http11.after(http1));
        assertFalse(http11.before(http1));
        assertTrue(http11.before(http2));
        assertFalse(http11.after(http2));
        // http/2
        assertTrue(http2.after(http1));
        assertFalse(http2.before(http1));
        assertTrue(http2.after(http11));
        assertFalse(http2.before(http11));
        // http/3
        assertTrue(http3.after(http11));
        assertFalse(http3.before(http11));
        assertTrue(http3.after(http1));
        assertFalse(http3.before(http1));
        assertTrue(http3.after(http2));
        assertTrue(http3.after(http2));
        assertFalse(http3.before(http2));
        assertEquals(-1, http1.compareTo(http11));
        assertEquals(1, http11.compareTo(http1));
    }
}
