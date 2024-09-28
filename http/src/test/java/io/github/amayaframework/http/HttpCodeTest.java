package io.github.amayaframework.http;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class HttpCodeTest {

    private static void checkRange(int start, int end) {
        for (var i = start; i <= end; ++i) {
            assertEquals(i, HttpCode.of(i).code);
        }
    }

    @Test
    public void testPredefined() {
        // 1xx
        checkRange(100, 103);
        // 2xx
        checkRange(200, 208);
        assertEquals(226, HttpCode.of(226).code);
        // 3xx
        checkRange(300, 308);
        // 4xx
        checkRange(400, 418);
        checkRange(421, 426);
        assertEquals(428, HttpCode.of(428).code);
        assertEquals(429, HttpCode.of(429).code);
        assertEquals(431, HttpCode.of(431).code);
        assertEquals(451, HttpCode.of(451).code);
        // 5xx
        checkRange(500, 508);
        assertEquals(510, HttpCode.of(510).code);
        assertEquals(511, HttpCode.of(511).code);
    }

    @Test
    public void testHttpVersion() {
        var all = new HashMap<>(HttpCode.all());
        var http1 = Set.of(200, 201, 202, 204, 300, 301, 302, 304, 400, 401, 403, 404, 500, 501, 502, 503);
        for (var number : http1) {
            var code = all.remove(number);
            assertEquals(HttpVersion.HTTP_1_0, code.since);
        }
        for (var code : all.values()) {
            assertEquals(HttpVersion.HTTP_1_1, code.since);
        }
    }
}
