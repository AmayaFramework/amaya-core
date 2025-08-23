package io.github.amayaframework.context;

import java.util.Iterator;
import java.util.function.Consumer;

public final class UnmodifiableIterator<T> implements Iterator<T> {
    private final Iterator<T> iterator;

    public UnmodifiableIterator(Iterator<T> iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public T next() {
        return iterator.next();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove: TODO MSG");
    }

    @Override
    public void forEachRemaining(Consumer<? super T> action) {
        iterator.forEachRemaining(action);
    }
}
