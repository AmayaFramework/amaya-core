package io.github.amayaframework.server;

import io.github.amayaframework.http.HttpVersion;
import org.junit.jupiter.api.Test;

import jakarta.servlet.http.HttpServletRequest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public final class ServerHttpRequestTest {
    private ServerHttpRequest newRequestWithQuery(String query) {
        var servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getQueryString()).thenReturn(query);
        return new ServerHttpRequest(
                servletRequest,
                HttpVersion.HTTP_1_1,
                m -> null,
                new SplitPathTokenizer(),
                data -> null
        );
    }

    @Test
    public void testNullQuery() {
        var req = newRequestWithQuery(null);
        assertTrue(req.queryParams().isEmpty());
    }

    @Test
    public void testKeyOnlyParam() {
        var req = newRequestWithQuery("debug");
        assertTrue(req.queryParams().containsKey("debug"));
        assertTrue(req.queryParams().get("debug").isEmpty());
    }

    @Test
    public void testKeyValueParam() {
        var req = newRequestWithQuery("id=123");
        assertEquals(List.of("123"), req.queryParams().get("id"));
    }

    @Test
    public void testMultipleParams() {
        var req = newRequestWithQuery("id=1&name=Alice&debug");
        var queries = req.queryParams();

        assertEquals(List.of("1"), queries.get("id"));
        assertEquals(List.of("Alice"), queries.get("name"));
        assertTrue(queries.containsKey("debug"));
    }

    @Test
    public void testDuplicateKeys() {
        var req = newRequestWithQuery("id=1&id=2&id=3");
        assertEquals(List.of("1", "2", "3"), req.queryParams().get("id"));
    }

    @Test
    public void testTrimmedKey() {
        var req = newRequestWithQuery(" name =Bob");
        assertEquals(List.of("Bob"), req.queryParams().get("name"));
    }

    @Test
    public void testEmptyKey() {
        var req = newRequestWithQuery(" =value");
        assertEquals(List.of("value"), req.queryParams().get(""));
    }
}
