package io.github.amayaframework.core.configuration;

import io.github.amayaframework.core.util.AbstractUnmodifiableMappable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class MapConfiguration extends AbstractUnmodifiableMappable<Key<?>, Object> implements Configuration {

    MapConfiguration(Map<Key<?>, Object> body) {
        super(body);
    }

    public MapConfiguration(Supplier<Map<Key<?>, Object>> supplier) {
        super(Objects.requireNonNull(supplier.get()));
    }

    public MapConfiguration() {
        super(new HashMap<>());
    }


    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(Key<T> key) {
        return (T) body.get(key);
    }

    @Override
    public <T> void set(Key<T> key, T value) {
        Objects.requireNonNull(key);
        if (value == null) {
            body.put(key, null);
            return;
        }
        var clazz = key.getType().getRawType();
        if (!clazz.isInstance(value)) {
            throw new IllegalArgumentException("Value must be instance of key type");
        }
        body.put(key, value);
    }

    @Override
    public boolean contains(Key<?> key) {
        return body.containsKey(key);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T remove(Key<T> key) {
        return (T) body.remove(key);
    }
}
