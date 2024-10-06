package io.github.amayaframework.http;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public final class HttpMethodTest {

    @Test
    public void testPredefined() {
        assertEquals(HttpMethod.GET, HttpMethod.of("get"));
        assertEquals(HttpMethod.HEAD, HttpMethod.of("head"));
        assertEquals(HttpMethod.POST, HttpMethod.of("post"));
        assertEquals(HttpMethod.PUT, HttpMethod.of("put"));
        assertEquals(HttpMethod.DELETE, HttpMethod.of("delete"));
        assertEquals(HttpMethod.CONNECT, HttpMethod.of("connect"));
        assertEquals(HttpMethod.OPTIONS, HttpMethod.of("options"));
        assertEquals(HttpMethod.TRACE, HttpMethod.of("trace"));
        assertEquals(HttpMethod.PATCH, HttpMethod.of("patch"));
    }

    @Test
    public void testHasBody() {
        // Methods disallowing body
        assertFalse(HttpMethod.GET.allowBody);
        assertFalse(HttpMethod.HEAD.allowBody);
        assertFalse(HttpMethod.CONNECT.allowBody);
        assertFalse(HttpMethod.OPTIONS.allowBody);
        assertFalse(HttpMethod.TRACE.allowBody);
        // Methods allowing body
        assertTrue(HttpMethod.POST.allowBody);
        assertTrue(HttpMethod.PUT.allowBody);
        assertTrue(HttpMethod.DELETE.allowBody);
        assertTrue(HttpMethod.PATCH.allowBody);
    }

    @Test
    public void testHttpVersion() {
        // Since 1.0
        assertEquals(HttpVersion.HTTP_1_0, HttpMethod.GET.since);
        assertEquals(HttpVersion.HTTP_1_0, HttpMethod.HEAD.since);
        assertEquals(HttpVersion.HTTP_1_0, HttpMethod.POST.since);
        // Since 1.1
        assertEquals(HttpVersion.HTTP_1_1, HttpMethod.PUT.since);
        assertEquals(HttpVersion.HTTP_1_1, HttpMethod.DELETE.since);
        assertEquals(HttpVersion.HTTP_1_1, HttpMethod.CONNECT.since);
        assertEquals(HttpVersion.HTTP_1_1, HttpMethod.OPTIONS.since);
        assertEquals(HttpVersion.HTTP_1_1, HttpMethod.TRACE.since);
        assertEquals(HttpVersion.HTTP_1_1, HttpMethod.PATCH.since);
    }
}
