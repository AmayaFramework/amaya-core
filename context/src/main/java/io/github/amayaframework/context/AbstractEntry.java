package io.github.amayaframework.context;

import java.util.Map;

/**
 * Skeletal implementation of {@link Map.Entry}. Provides key field, key getter and
 * {@link Map.Entry#equals(Object)} and {@link Map.Entry#hashCode()} implementations
 * using key as unique value.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public abstract class AbstractEntry<K, V> implements Map.Entry<K, V> {
    /**
     * Field holding entry key
     */
    protected final K key;

    /**
     * Constructs {@link AbstractEntry} instance with given key.
     *
     * @param key the entry key
     */
    protected AbstractEntry(K key) {
        this.key = key;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof AbstractEntry)) return false;
        var that = (AbstractEntry<?, ?>) object;
        return key.equals(that.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }
}
