package io.github.amayaframework.core.configuration;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class MapScheme implements Scheme {
    private final Map<String, Key<?>> keys;
    private final Collection<Key<?>> values;

    public MapScheme(Supplier<Map<String, Key<?>>> supplier) {
        this.keys = Objects.requireNonNull(supplier.get());
        this.values = Collections.unmodifiableCollection(keys.values());
    }

    public MapScheme() {
        this.keys = new HashMap<>();
        this.values = Collections.unmodifiableCollection(keys.values());
    }

    @Override
    public void add(Key<?> key) {
        Objects.requireNonNull(key);
        keys.put(key.getName(), key);
    }

    @Override
    public Key<?> get(String name) {
        return keys.get(name);
    }

    @Override
    public boolean remove(Key<?> key) {
        Objects.requireNonNull(key);
        return keys.remove(key.getName()) != null;
    }

    @Override
    public Iterator<Key<?>> iterator() {
        return values.iterator();
    }

    @Override
    public void forEach(Consumer<? super Key<?>> action) {
        values.forEach(action);
    }

    @Override
    public Spliterator<Key<?>> spliterator() {
        return values.spliterator();
    }

    @Override
    public Stream<Key<?>> stream() {
        return values.stream();
    }

    @Override
    public Stream<Key<?>> parallelStream() {
        return values.parallelStream();
    }
}
