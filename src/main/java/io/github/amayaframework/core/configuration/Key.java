package io.github.amayaframework.core.configuration;

import com.github.romanqed.jtype.JType;

import java.lang.reflect.Type;
import java.util.Objects;

public final class Key<T> {
    private final String name;
    private final JType<T> type;

    public Key(String name, JType<T> type) {
        this.name = Objects.requireNonNull(name);
        this.type = Objects.requireNonNull(type);
    }

    public static <T> Key<T> of(String name, JType<T> type) {
        return new Key<>(name, type);
    }

    public static <T> Key<T> of(String name, Class<T> type) {
        return new Key<>(name, JType.of(type));
    }

    public static <T> Key<T> of(String name, Type type) {
        return new Key<>(name, JType.of(type));
    }

    public static Key<Boolean> ofBool(String name) {
        return new Key<>(name, JType.BOOLEAN);
    }

    public static Key<Character> ofChar(String name) {
        return new Key<>(name, JType.CHARACTER);
    }

    public static Key<Byte> ofByte(String name) {
        return new Key<>(name, JType.BYTE);
    }

    public static Key<Short> ofShort(String name) {
        return new Key<>(name, JType.SHORT);
    }

    public static Key<Integer> ofInt(String name) {
        return new Key<>(name, JType.INTEGER);
    }

    public static Key<Long> ofLong(String name) {
        return new Key<>(name, JType.LONG);
    }

    public static Key<Float> ofFloat(String name) {
        return new Key<>(name, JType.FLOAT);
    }

    public static Key<Double> ofDouble(String name) {
        return new Key<>(name, JType.DOUBLE);
    }

    public String getName() {
        return name;
    }

    public JType<T> getType() {
        return type;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        var key = (Key<?>) object;
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
