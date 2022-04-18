package io.github.amayaframework.core.config;

import java.util.Objects;

public class Field<T> {
    private final String name;
    private final Class<?> type;

    public Field(String name, Class<?> type) {
        this.name = Objects.requireNonNull(name);
        this.type = Objects.requireNonNull(type);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode() * 13 + type.hashCode();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Field)) {
            return false;
        }
        Field<T> field;
        try {
            field = (Field<T>) obj;
        } catch (Exception e) {
            return false;
        }
        return name.equals(field.name) && type == field.type;
    }
}
