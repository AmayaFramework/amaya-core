package io.github.amayaframework.options;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public final class GroupSetTest extends CommonSetTest {

    @Test
    public void testGet() {
        var gs = new ProvidedGroupSet(".", OpenOptionSet::new);
        testGet(gs);
        // Test some groups
        gs.set("g1.k1", "v1");
        gs.set("g2.k1", "v2");
        gs.set("g3.k1", "v3");
        assertEquals("v1", gs.get("g1.k1"));
        assertEquals("v1", gs.getGroup("g1").get("k1"));
        assertEquals("v2", gs.get("g2.k1"));
        assertEquals("v2", gs.getGroup("g2").get("k1"));
        assertEquals("v3", gs.get("g3.k1"));
        assertEquals("v3", gs.getGroup("g3").get("k1"));
    }

    @Test
    public void testDefault() {
        var gs = new ProvidedGroupSet(".", OpenOptionSet::new);
        testDefault(gs);
        // Test some groups
        var k1Key = Key.of("k1", Integer.class);
        var g1k1Key = k1Key.withGroup("kg1");
        var g2k1Key = k1Key.withGroup("kg2");
        var g3k1Key = k1Key.withGroup("kg3");
        gs.set("g1.k1", null);
        gs.set("g2.k1", 1);
        gs.set(g1k1Key, null);
        gs.set(g2k1Key, 2);
        assertNull(gs.get("g1.k1", 1));
        assertEquals(1, gs.get("g2.k1", 2));
        assertEquals(10, gs.get("g3.k1", 10));
        assertNull(gs.get(g1k1Key, 1));
        assertEquals(2, gs.get(g2k1Key, 1));
        assertEquals(10, gs.get(g3k1Key, 10));
    }

    @Test
    public void testKey() {
        testKey(new ProvidedGroupSet(".", OpenOptionSet::new));
    }

    @Test
    public void testBool() {
        testBool(new ProvidedGroupSet(".", OpenOptionSet::new));
    }

    @Test
    public void testContains() {
        var set = new ProvidedGroupSet(".", OpenOptionSet::new);
        // Unknown key
        assertFalse(set.contains("uk"));
        // Known key with null value
        set.set("kn", null);
        assertTrue(set.contains("kn"));
        // Known key with non-null value
        set.set("kv", "");
        assertTrue(set.contains("kv"));
        // Default group
        assertTrue(set.containsGroup(""));
        // Unknown group
        assertFalse(set.containsGroup("ug"));
        // Known group
        set.set("g1.k", "v");
        assertTrue(set.containsGroup("g1"));
    }

    @Test
    public void testSet() {
        var set = new ProvidedGroupSet(".", OpenOptionSet::new);
        // New key
        assertNull(set.set("k", "first"));
        // Existing value
        assertEquals("first", set.set("k", "v"));
        // New group
        assertNull(set.setGroup("g1", null));
        // Default group
        var dg = set.setGroup("", null);
        assertNotNull(dg);
        assertEquals("v", dg.get("k"));
    }

    @Test
    public void testRemove() {
        var set = new ProvidedGroupSet(".", OpenOptionSet::new);
        // Unknown key
        assertNull(set.remove("nk"));
        // Existing key
        set.set("k", "v");
        assertEquals("v", set.remove("k"));
        assertFalse(set.contains("k"));
        assertNull(set.get("k"));
        // Unknown group
        assertNull(set.removeGroup("ug"));
        // Default group
        assertNotNull(set.removeGroup(""));
        assertFalse(set.containsGroup(""));
        assertNull(set.getGroup(""));
    }
}
