package io.github.amayaframework.context;

import jakarta.servlet.http.HttpServletResponse;

import java.util.ListIterator;
import java.util.function.Consumer;

/**
 * A {@link ListIterator} implementation for multi-value response headers.
 * <p>
 * Adding values appends them to both the underlying iterator and the {@link HttpServletResponse}.
 * Removal and modification operations are not supported.
 */
public final class MultiHeaderListIterator implements ListIterator<String> {
    private final ListIterator<String> iterator;
    private final String header;
    private final HttpServletResponse response;

    /**
     * Constructs a new list iterator for a specific header.
     *
     * @param iterator the underlying list iterator
     * @param header   the header name
     * @param response the backing servlet response
     */
    public MultiHeaderListIterator(ListIterator<String> iterator, String header, HttpServletResponse response) {
        this.iterator = iterator;
        this.header = header;
        this.response = response;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public String next() {
        return iterator.next();
    }

    @Override
    public boolean hasPrevious() {
        return iterator.hasPrevious();
    }

    @Override
    public String previous() {
        return iterator.previous();
    }

    @Override
    public int nextIndex() {
        return iterator.nextIndex();
    }

    @Override
    public int previousIndex() {
        return iterator.previousIndex();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove operation is not supported on MultiHeaderListIterator");
    }

    @Override
    public void set(String t) {
        throw new UnsupportedOperationException("set operation is not supported on MultiHeaderListIterator");
    }

    @Override
    public void add(String t) {
        response.addHeader(header, t);
        iterator.add(t);
    }

    @Override
    public void forEachRemaining(Consumer<? super String> action) {
        iterator.forEachRemaining(action);
    }
}
