package io.github.amayaframework.context;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * An adapter class that wraps a standard {@link Iterator} and exposes it
 * as an {@link Enumeration}.
 *
 * <p>
 * This is useful in contexts where APIs require an {@link Enumeration}
 * but data is available only through an {@link Iterator}.
 * </p>
 *
 * @param <E> the type of elements returned by this enumeration
 */
public final class IteratorEnumeration<E> implements Enumeration<E> {
    private final Iterator<E> iterator;

    /**
     * Creates a new {@code IteratorEnumeration} backed by the given {@link Iterator}.
     *
     * @param iterator the iterator to be wrapped, must not be {@code null}
     */
    public IteratorEnumeration(Iterator<E> iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean hasMoreElements() {
        return iterator.hasNext();
    }

    @Override
    public E nextElement() {
        return iterator.next();
    }

    @Override
    public Iterator<E> asIterator() {
        return iterator;
    }
}
