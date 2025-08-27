package io.github.amayaframework.server;

import io.github.amayaframework.http.MimeType;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public final class StandardMimeParserTest {
    private final StandardMimeParser parser = new StandardMimeParser();

    @Test
    public void testSimpleType() {
        var data = parser.read("text/html");
        assertEquals("text", data.getType().getGroup());
        assertEquals("html", data.getType().getName());
        assertNull(data.getParams());
    }

    @Test
    public void testTypeWithSpaces() {
        var data = parser.read(" text / html ");
        assertEquals("text", data.getType().getGroup());
        assertEquals("html", data.getType().getName());
    }

    @Test
    public void testKnownLookupType() {
        var data = parser.read("application/json");
        assertNotNull(data.getType());
        assertSame(MimeType.JSON, data.getType());
    }

    @Test
    public void testTypeWithSimpleParam() {
        var data = parser.read("text/html; charset=UTF-8");
        assertEquals(Map.of("charset", "UTF-8"), data.getParams());
    }

    @Test
    public void testTypeWithQuotedParam() {
        var data = parser.read("text/html; charset=\"utf-8\"");
        assertEquals("utf-8", data.getParam("charset"));
    }

    @Test
    public void testTypeWithEscapedQuoteParam() {
        var data = parser.read("text/html; note=\"hello \\\"world\\\"\"");
        assertEquals("hello \"world\"", data.getParam("note"));
    }

    @Test
    public void testTypeWithParamWithoutValue() {
        var data = parser.read("text/html; secure");
        assertNull(data.getParam("secure"));
        assertTrue(data.hasParam("secure"));
    }

    @Test
    public void testTypeWithMultipleParams() {
        var data = parser.read("multipart/form-data; boundary=something; charset=UTF-8");
        assertEquals("something", data.getParam("boundary"));
        assertEquals("UTF-8", data.getParam("charset"));
    }

    @Test
    public void testParamWithSpacesAround() {
        var data = parser.read("text/html ;  charset = UTF-8 ");
        assertEquals("UTF-8", data.getParam("charset"));
    }

    @Test
    public void testEmptyParamIgnored() {
        var data = parser.read("text/html;;charset=UTF-8;");
        assertEquals("UTF-8", data.getParam("charset"));
        assertEquals(1, data.getParams().size());
    }

    @Test
    public void testParamWithExtraQuotesInside() {
        var data = parser.read("text/html; weird=\"val\"ue\"");
        assertEquals("val\"ue", data.getParam("weird"));
    }

    @Test
    public void testNoSlashThrows() {
        assertThrows(IllegalMimeTypeException.class, () -> parser.read("text"));
    }

    @Test
    public void testSubtypeMissingThrows() {
        assertThrows(IllegalMimeTypeException.class, () -> parser.read("text/"));
    }

    @Test
    public void testNullGroupOrSubtypeThrows() {
        assertThrows(IllegalMimeTypeException.class, () -> parser.read("/html"));
    }

    @Test
    public void testParamWithoutKeyIgnored() {
        var data = parser.read("text/html; =utf-8; charset=UTF-8");
        assertEquals("UTF-8", data.getParam("charset"));
        assertFalse(data.hasParam(""));
    }

    @Test
    public void testOnlySpacesGroupOrSubtypeThrows() {
        assertThrows(IllegalMimeTypeException.class, () -> parser.read("   /   "));
    }

    @Test
    public void testParamWithEmptyValueBecomesNull() {
        var data = parser.read("text/html; charset=");
        assertTrue(data.hasParam("charset"));
        assertNull(data.getParam("charset"));
    }
}
