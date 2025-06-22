package io.github.amayaframework.options;

import static org.junit.jupiter.api.Assertions.*;

class CommonSetTest {

    void testGet(OptionSet set) {
        set.set("k1", true);
        set.set("k2", 1);
        set.set("k3", 1L);
        set.set("k4", 1F);
        set.set("k5", 1D);
        set.set("k6", "string");
        assertTrue(set.<Boolean>get("k1"));
        assertEquals(1, set.<Integer>get("k2"));
        assertEquals(1L, set.<Long>get("k3"));
        assertEquals(1F, set.<Float>get("k4"));
        assertEquals(1D, set.<Double>get("k5"));
        assertEquals("string", set.get("k6"));
    }

    void testDefault(OptionSet set) {
        var kd1Key = Key.of("kd1Keyed", Integer.class);
        var kd2Key = Key.of("kd2Keyed", Integer.class);
        var kd3Key = Key.of("kd3Keyed", Integer.class);
        set.set("kd1", null);
        set.set("kd2", 1);
        set.set(kd1Key, null);
        set.set(kd2Key, 2);
        assertNull(set.get("kd1", 1));
        assertEquals(1, set.get("kd2", 2));
        assertEquals(10, set.get("kd3", 10));
        assertNull(set.get(kd1Key, 1));
        assertEquals(2, set.get(kd2Key, 1));
        assertEquals(10, set.get(kd3Key, 10));
    }

    void testKey(OptionSet set) {
        // Pure true value
        set.set("kt", true);
        // Pure false value
        set.set("kf", false);
        // Some object ref
        set.set("ko", new Object());
        // Null ref
        set.set("kn", null);
        assertTrue(set.asKey("kt"));
        assertFalse(set.asKey("kf"));
        assertTrue(set.asKey("ko"));
        assertTrue(set.asKey("kn"));
    }

    void testBool(OptionSet set) {
        // Pure true value
        set.set("kt", true);
        // Pure false value
        set.set("kf", false);
        // Some object ref
        set.set("ko", new Object());
        // Null ref
        set.set("kn", null);
        assertTrue(set.asBool("kt"));
        assertFalse(set.asBool("kf"));
        assertTrue(set.asBool("ko"));
        assertFalse(set.asBool("kn"));
    }
}
