package io.github.amayaframework.core.config;

import java.util.Objects;

public class Field<T> {
    private final String name;

    public Field(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public String getName() {
        return name;
    }
}
