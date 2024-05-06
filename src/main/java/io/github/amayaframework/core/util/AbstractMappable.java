package io.github.amayaframework.core.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

public abstract class AbstractMappable<K, V> implements Mappable<K, V> {
    protected final Map<K, V> body;

    protected AbstractMappable(Map<K, V> body) {
        this.body = body;
    }

    @Override
    public Stream<Map.Entry<K, V>> stream() {
        return body.entrySet().stream();
    }

    @Override
    public Stream<Map.Entry<K, V>> parallelStream() {
        return body.entrySet().parallelStream();
    }

    @Override
    public void forEach(Consumer<? super Map.Entry<K, V>> action) {
        body.entrySet().forEach(action);
    }

    @Override
    public Spliterator<Map.Entry<K, V>> spliterator() {
        return body.entrySet().spliterator();
    }

    @Override
    public Map<K, V> map() {
        return body;
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> consumer) {
        body.forEach(consumer);
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return body.entrySet().iterator();
    }
}
