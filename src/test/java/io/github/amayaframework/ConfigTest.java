package io.github.amayaframework;

import io.github.amayaframework.core.config.Config;
import io.github.amayaframework.core.config.Field;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConfigTest extends Assertions {
    @Test
    public void testSameNames() {
        Config config = new Config();
        Field<String> str = new Field<>("A", String.class);
        Field<Integer> integer = new Field<>("A", Integer.class);
        config.setField(str, "test");
        config.setField(integer, -11);
        assertAll(
                () -> assertEquals("test", config.getField(str)),
                () -> assertEquals(-11, config.getField(integer))
        );
    }

    @Test
    public void testGetValue() {
        Config config = new Config();
        config.setField(new Field<>("FIELD", String.class), "test");
        assertEquals("test", config.getField(new Field<>("FIELD", String.class)));
    }
}
