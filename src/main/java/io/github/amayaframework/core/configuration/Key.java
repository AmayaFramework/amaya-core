package io.github.amayaframework.core.configuration;

import java.util.Objects;

public final class Key<T> {
    private final String name;
    private final Class<T> type;

    public Key(String name, Class<T> type) {
        this.name = Objects.requireNonNull(name);
        this.type = Objects.requireNonNull(type);
    }

    public static <T> Key<T> of(String name, Class<T> type) {
        return new Key<>(name, type);
    }

    public String getName() {
        return name;
    }

    public Class<T> getType() {
        return type;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        var key = (Key<?>) object;
        return name.equals(key.name) && type.equals(key.type);
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
