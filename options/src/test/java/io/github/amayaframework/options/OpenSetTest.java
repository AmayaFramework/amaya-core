package io.github.amayaframework.options;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public final class OpenSetTest extends CommonSetTest {

    @Test
    public void testGet() {
        testGet(new OpenOptionSet());
    }

    @Test
    public void testDefault() {
        testDefault(new OpenOptionSet());
    }

    @Test
    public void testKey() {
        testKey(new OpenOptionSet());
    }

    @Test
    public void testBool() {
        testBool(new OpenOptionSet());
    }

    @Test
    public void testContains() {
        var set = new OpenOptionSet();
        // Unknown key
        assertFalse(set.contains("uk"));
        // Known key with null value
        set.set("kn", null);
        assertTrue(set.contains("kn"));
        // Known key with non-null value
        set.set("kv", "");
        assertTrue(set.contains("kv"));
    }

    @Test
    public void testSet() {
        var set = new OpenOptionSet();
        // New key
        assertNull(set.set("k", "first"));
        // Existing value
        assertEquals("first", set.get("k"));
    }

    @Test
    public void testRemove() {
        var set = new OpenOptionSet();
        // Unknown key
        assertNull(set.remove("nk"));
        // Existing key
        set.set("k", "v");
        assertEquals("v", set.remove("k"));
        assertFalse(set.contains("k"));
        assertNull(set.get("k"));
    }
}
