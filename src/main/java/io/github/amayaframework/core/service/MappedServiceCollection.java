package io.github.amayaframework.core.service;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class MappedServiceCollection implements ServiceCollection {
    private final Map<Class<?>, Service> body;
    private final Collection<Service> values;

    public MappedServiceCollection(Supplier<Map<Class<?>, Service>> supplier) {
        this.body = Objects.requireNonNull(supplier.get());
        this.values = body.values();
    }

    public MappedServiceCollection() {
        this.body = new HashMap<>();
        this.values = body.values();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Service> T get(Class<T> type) {
        return (T) body.get(type);
    }

    @Override
    public boolean contains(Class<?> type) {
        return body.containsKey(type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Service> T add(T service) {
        Objects.requireNonNull(service);
        return (T) body.put(service.getClass(), service);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Service> T remove(Class<T> type) {
        return (T) body.remove(type);
    }

    @Override
    public void clear() {
        body.clear();
    }

    @Override
    public Iterator<Service> iterator() {
        return values.iterator();
    }

    @Override
    public Stream<Service> stream() {
        return values.stream();
    }

    @Override
    public Stream<Service> parallelStream() {
        return values.parallelStream();
    }

    @Override
    public void forEach(Consumer<? super Service> action) {
        values.forEach(action);
    }

    @Override
    public Spliterator<Service> spliterator() {
        return values.spliterator();
    }
}
