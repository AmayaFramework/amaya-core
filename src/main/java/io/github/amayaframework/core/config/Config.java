package io.github.amayaframework.core.config;

import io.github.amayaframework.core.util.AbstractCompletableData;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class Config extends AbstractCompletableData implements Configurable {
    private final Map<String, Object> fields;

    public Config(Supplier<Map<String, Object>> supplier) {
        this.fields = supplier.get();
    }

    public Config() {
        this(ConcurrentHashMap::new);
    }

    @Override
    public <T> void setField(Field<T> field, T value) {
        checkCompleted();
        Objects.requireNonNull(field);
        Objects.requireNonNull(value);
        fields.put(field.getName(), value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getField(Field<T> field) {
        Objects.requireNonNull(field);
        return (T) fields.get(field.getName());
    }

    @Override
    public void complete() {
        this.completed = true;
    }

    @Override
    public String toString() {
        return fields.toString();
    }
}