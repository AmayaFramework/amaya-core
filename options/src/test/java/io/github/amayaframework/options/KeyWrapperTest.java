package io.github.amayaframework.options;

import com.github.romanqed.jtype.JType;
import com.github.romanqed.jtype.Types;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class KeyWrapperTest {

    @Test
    public void testIntKey() {
        var key = Key.of("ik", Integer.class);
        assertEquals("ik", key.getKey());
        assertEquals(Integer.class, key.getType().getRawType());
        assertEquals(Integer.class, key.getType().getType());
        var set = Options.create();
        set.set(key, 4542);
        assertEquals(4542, set.get(key));
    }

    @Test
    public void testStringKey() {
        var key = Key.of("sk", String.class);
        assertEquals("sk", key.getKey());
        assertEquals(String.class, key.getType().getRawType());
        assertEquals(String.class, key.getType().getType());
        var set = Options.create();
        set.set(key, "testVal");
        assertEquals("testVal", set.get(key));
    }

    @Test
    public void testListKey() {
        var key = Key.of("ilk", new JType<List<Integer>>(){});
        assertEquals("ilk", key.getKey());
        assertEquals(List.class, key.getType().getRawType());
        assertEquals(Types.of(List.class, Integer.class), key.getType().getType());
        var set = Options.create();
        set.set(key, List.of(1, 2, 3));
        var list = set.get(key);
        assertEquals(list, set.get(key));
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
    }
}
