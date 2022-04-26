package io.github.amayaframework.core.config;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class Config implements Configurable {
    private final Map<Field<?>, Object> fields;

    public Config(Supplier<Map<Field<?>, Object>> supplier) {
        Objects.requireNonNull(supplier);
        this.fields = Objects.requireNonNull(supplier.get());
    }

    public Config() {
        this(ConcurrentHashMap::new);
    }

    @Override
    public <T> void setField(Field<T> field, T value) {
        Objects.requireNonNull(field);
        Objects.requireNonNull(value);
        fields.put(field, value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getField(Field<T> field) {
        Objects.requireNonNull(field);
        return (T) fields.get(field);
    }

    @Override
    public String toString() {
        return fields.toString();
    }
}
