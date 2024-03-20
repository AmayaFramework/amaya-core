package io.github.amayaframework.core.configuration;

import java.util.Objects;

public final class Entry<T> {
    private final String name;
    private final Class<T> type;

    public Entry(String name, Class<T> type) {
        this.name = Objects.requireNonNull(name);
        this.type = Objects.requireNonNull(type);
    }

    public static <T> Entry<T> of(String name, Class<T> type) {
        return new Entry<>(name, type);
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
        var entry = (Entry<?>) object;
        return name.equals(entry.name) && type.equals(entry.type);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Entry {" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
