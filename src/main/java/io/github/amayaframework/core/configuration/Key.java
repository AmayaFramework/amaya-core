package io.github.amayaframework.core.configuration;

import java.lang.reflect.Type;
import java.util.Objects;

public final class Key {
    private final String name;
    private final Type type;

    public Key(String name, Type type) {
        this.name = Objects.requireNonNull(name);
        this.type = Objects.requireNonNull(type);
    }

    public static Key of(String name, Type type) {
        return new Key(name, type);
    }

    public static Key ofBool(String name) {
        return new Key(name, Boolean.class);
    }

    public static Key ofChar(String name) {
        return new Key(name, Character.class);
    }

    public static Key ofByte(String name) {
        return new Key(name, Byte.class);
    }

    public static Key ofShort(String name) {
        return new Key(name, Short.class);
    }

    public static Key ofInt(String name) {
        return new Key(name, Integer.class);
    }

    public static Key ofLong(String name) {
        return new Key(name, Long.class);
    }

    public static Key ofFloat(String name) {
        return new Key(name, Float.class);
    }

    public static Key ofDouble(String name) {
        return new Key(name, Double.class);
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        var key = (Key) object;
        return name.equals(key.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Key {" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
