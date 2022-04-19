package io.github.amayaframework.core.config;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigRepository implements Repository<Config, Class<?>> {
    private final Map<Class<?>, Config> body;
    private final Set<Class<?>> locks;

    public ConfigRepository() {
        this.body = new ConcurrentHashMap<>();
        this.locks = Collections.synchronizedSet(new HashSet<>());
    }

    @Override
    public Config get(Class<?> key) {
        Objects.requireNonNull(key);
        return body.get(key);
    }

    @Override
    public Config put(Class<?> key, Config config) {
        Objects.requireNonNull(key);
        if (locks.contains(key)) {
            throw new IllegalStateException("Can't set locked config");
        }
        Objects.requireNonNull(config);
        return body.put(key, config);
    }

    @Override
    public Config remove(Class<?> key) {
        Objects.requireNonNull(key);
        if (locks.contains(key)) {
            throw new IllegalStateException("Can't remove locked config");
        }
        return body.remove(key);
    }

    @Override
    public Runnable lock(Class<?> key) {
        Objects.requireNonNull(key);
        if (!body.containsKey(key) || locks.contains(key)) {
            throw new IllegalArgumentException("Can't create lock for this key: " + key);
        }
        locks.add(key);
        return () -> locks.remove(key);
    }
}
