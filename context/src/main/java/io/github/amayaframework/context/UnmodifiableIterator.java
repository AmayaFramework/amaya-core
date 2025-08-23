package io.github.amayaframework.context;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 * Unmodifiable {@link Iterator} wrapper that disables removal operations.
 *
 * @param <T> the element type
 */
public final class UnmodifiableIterator<T> implements Iterator<T> {
    private final Iterator<T> iterator;

    /**
     * Constructs a new unmodifiable iterator wrapping the given iterator.
     *
     * @param iterator the underlying iterator
     */
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
        throw new UnsupportedOperationException("remove operation is not supported on UnmodifiableIterator");
    }

    @Override
    public void forEachRemaining(Consumer<? super T> action) {
        iterator.forEachRemaining(action);
    }
}
