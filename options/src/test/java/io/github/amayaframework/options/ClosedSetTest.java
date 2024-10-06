package io.github.amayaframework.options;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public final class ClosedSetTest extends CommonSetTest {

    @Test
    public void testGet() {
        testGet(new ClosedOptionSet(Set.of("k1", "k2", "k3", "k4", "k5", "k6")));
    }

    @Test
    public void testKey() {
        testKey(new ClosedOptionSet(Set.of("kt", "kf", "ko", "kn")));
    }

    @Test
    public void testBool() {
        testBool(new ClosedOptionSet(Set.of("kt", "kf", "ko", "kn")));
    }

    @Test
    public void testContains() {
        var set = new ClosedOptionSet(Set.of("kn", "kv"));
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
        var set = new ClosedOptionSet(Set.of("k"));
        // New key
        assertNull(set.set("k", "first"));
        // Existing value
        assertEquals("first", set.get("k"));
        // Illegal key
        assertThrows(IllegalKeyException.class, () -> set.set("key", "1"));
    }

    @Test
    public void testRemove() {
        var set = new ClosedOptionSet(Set.of("k"));
        // Unknown key
        assertNull(set.remove("nk"));
        // Existing key
        set.set("k", "v");
        assertEquals("v", set.remove("k"));
        assertNull(set.get("k"));
    }
}
