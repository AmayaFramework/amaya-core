package io.github.amayaframework.context;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Skeletal implementation for {@link Map} based on key sequence stored as {@link Iterable}.
 * @param <K> the key type
 * @param <V> the value type
 */
public abstract class AbstractIteratedMap<K, V> implements Map<K, V> {
    /**
     * {@link Iterable} instance containing map keys
     */
    protected final Iterable<K> keys;

    /**
     * Constructs {@link AbstractIteratedMap} with given key sequence.
     * @param keys the {@link Iterable} instance containing key sequence, must be non-null
     */
    protected AbstractIteratedMap(Iterable<K> keys) {
        this.keys = keys;
    }

    /**
     * Creates stream over {@link AbstractIteratedMap#keys}.
     * @return {@link Stream} instance
     */
    protected Stream<K> getKeyStream() {
        var spliterator = Spliterators.spliteratorUnknownSize(keys.iterator(), Spliterator.ORDERED);
        return StreamSupport.stream(spliterator, false);
    }

    @Override
    public int size() {
        var count = getKeyStream().count();
        if (count > Integer.MAX_VALUE) {
            throw new IllegalStateException("Length of key sequence more than integer max value");
        }
        return (int) count;
    }

    @Override
    public boolean isEmpty() {
        return keys.iterator().hasNext();
    }

    @Override
    public Set<K> keySet() {
        var set = new HashSet<K>();
        for (var key : keys) {
            set.add(key);
        }
        return set;
    }
}
