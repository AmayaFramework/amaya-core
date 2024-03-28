package io.github.amayaframework.core.configuration;

import java.lang.reflect.Type;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class MapScheme implements ConfigurationScheme {
    private final Map<String, Type> body;
    private final Map<String, Type> unmodifiable;

    public MapScheme(Supplier<Map<String, Type>> supplier) {
        this.body = Objects.requireNonNull(supplier.get());
        this.unmodifiable = Collections.unmodifiableMap(this.body);
    }

    @Override
    public Map<String, Type> map() {
        return unmodifiable;
    }

    @Override
    public void forEach(BiConsumer<? super String, ? super Type> consumer) {
        body.forEach(consumer);
    }

    @Override
    public Stream<Map.Entry<String, Type>> stream() {
        return unmodifiable.entrySet().stream();
    }

    @Override
    public Stream<Map.Entry<String, Type>> parallelStream() {
        return unmodifiable.entrySet().parallelStream();
    }

    @Override
    public void add(Key key) {
        Objects.requireNonNull(key);
        body.put(key.getName(), key.getType());
    }

    @Override
    public Type add(String name, Type type) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(type);
        return body.put(name, type);
    }

    @Override
    public Type get(String name) {
        return body.get(name);
    }

    @Override
    public boolean remove(Key key) {
        Objects.requireNonNull(key);
        return body.remove(key.getName()) != null;
    }

    @Override
    public Type remove(String name) {
        return body.remove(name);
    }

    @Override
    public Iterator<Map.Entry<String, Type>> iterator() {
        return unmodifiable.entrySet().iterator();
    }

    @Override
    public void forEach(Consumer<? super Map.Entry<String, Type>> action) {
        body.entrySet().forEach(action);
    }

    @Override
    public Spliterator<Map.Entry<String, Type>> spliterator() {
        return body.entrySet().spliterator();
    }
}
