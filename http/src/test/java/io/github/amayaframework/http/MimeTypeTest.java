package io.github.amayaframework.http;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public final class MimeTypeTest {

    @Test
    public void testPredefined() {
        var text = MimeType.PLAIN;
        var json = MimeType.JSON;
        assertEquals(text, MimeType.of("text/plain"));
        assertEquals(text, MimeType.of("TEXT/PLAIN"));
        assertEquals(text, MimeType.of("text", "plain"));
        assertEquals(text, MimeType.of("TEXT", "PLAIN"));
        assertEquals(json, MimeType.of("application/json"));
        assertEquals(json, MimeType.of("APPLICATION/JSON"));
        assertEquals(json, MimeType.of("application", "json"));
        assertEquals(json, MimeType.of("APPLICATION", "JSON"));
    }

    @Test
    public void testIsText() {
        var texted = List.of(
                MimeType.JSON,
                MimeType.APPLICATION_JAVASCRIPT,
                MimeType.POSTSCRIPT,
                MimeType.SOAP_XML,
                MimeType.XHTML_XML,
                MimeType.XML_DTD,
                MimeType.XOP_XML,
                MimeType.APPLICATION_XML,
                MimeType.FORM_URLENCODED,
                MimeType.SVG_XML,
                MimeType.HTTP,
                MimeType.IMDN_XML,
                MimeType.E_MAIL_RFC_822
        );
        var all = new HashMap<>(MimeType.all());
        for (var type : texted) {
            assertTrue(type.text);
            all.remove(type.qualifier);
        }
        for (var type : all.values()) {
            if (type.qualifier.startsWith("text/")) {
                assertTrue(type.text);
            } else {
                assertFalse(type.text);
            }
        }
    }
}
