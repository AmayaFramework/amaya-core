package io.github.amayaframework.options;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class OptionsTest {

    @Test
    public void testEmpty() {
        var empty = Options.empty();
        assertEquals(0, empty.getKeys().size());
        assertTrue(empty.asMap().isEmpty());
    }

    @Test
    public void testPair() {
        var pair = Options.of("k", "v");
        assertEquals("v", pair.get("k"));
        assertTrue(pair.asKey("k"));
        assertTrue(pair.contains("k"));
    }

    @Test
    public void testUnmodifiable() {
        var map = Map.<String, Object>of(
                "k1", "v1", "k2", "v2", "k3", "v3", "k4", "v4", "k5", "v5"
        );
        var set = Options.of(map);
        for (var key : map.keySet()) {
            assertEquals(map.get(key), set.get(key));
        }
    }

    @Test
    public void testFromSet() {
        var set = Options.of(Set.of("k1", "k2", "k3", "k4", "k5"));
        assertTrue(set.<Boolean>get("k1"));
        assertTrue(set.<Boolean>get("k2"));
        assertTrue(set.<Boolean>get("k3"));
        assertTrue(set.<Boolean>get("k4"));
        assertTrue(set.<Boolean>get("k5"));
    }

    @Test
    public void testFromKeys() {
        var set = Options.of("k1", "k2", "k3", "k4", "k5");
        assertTrue(set.<Boolean>get("k1"));
        assertTrue(set.<Boolean>get("k2"));
        assertTrue(set.<Boolean>get("k3"));
        assertTrue(set.<Boolean>get("k4"));
        assertTrue(set.<Boolean>get("k5"));
    }
}
