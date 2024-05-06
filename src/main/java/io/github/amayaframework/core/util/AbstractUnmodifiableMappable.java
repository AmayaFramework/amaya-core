package io.github.amayaframework.core.util;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;

public abstract class AbstractUnmodifiableMappable<K, V> extends AbstractMappable<K, V> {
    protected final Map<K, V> unmodifiable;

    protected AbstractUnmodifiableMappable(Map<K, V> body) {
        super(body);
        this.unmodifiable = Collections.unmodifiableMap(body);
    }

    @Override
    public Spliterator<Map.Entry<K, V>> spliterator() {
        return unmodifiable.entrySet().spliterator();
    }

    @Override
    public Map<K, V> map() {
        return unmodifiable;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return unmodifiable.entrySet().iterator();
    }
}
