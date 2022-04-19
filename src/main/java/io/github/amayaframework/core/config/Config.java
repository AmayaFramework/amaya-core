package io.github.amayaframework.core.config;

import io.github.amayaframework.core.util.AbstractCompletableData;
import org.slf4j.Logger;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class Config extends AbstractCompletableData implements Configurable {
    private final Logger logger;
    private final Map<String, Object> fields;

    public Config(Supplier<Map<String, Object>> supplier, Logger logger) {
        this.fields = supplier.get();
        this.logger = logger;
    }

    public Config(Supplier<Map<String, Object>> supplier) {
        this(supplier, null);
    }

    public Config(Logger logger) {
        this(ConcurrentHashMap::new, logger);
    }

    public Config() {
        this(ConcurrentHashMap::new, null);
    }

    @Override
    public <T> void setField(Field<T> field, T value) {
        checkCompleted();
        Objects.requireNonNull(field);
        Objects.requireNonNull(value);
        fields.put(field.getName(), value);
        if (logger != null && logger.isDebugEnabled()) {
            logger.debug("Field " + field + " set with value " + value);
        }
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
