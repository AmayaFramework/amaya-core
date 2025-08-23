package io.github.amayaframework.context;

import jakarta.servlet.http.HttpServletResponse;

import java.util.ListIterator;
import java.util.function.Consumer;

public final class MultiHeaderListIterator implements ListIterator<String> {
    private final ListIterator<String> iterator;
    private final String header;
    private final HttpServletResponse response;

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
        throw new UnsupportedOperationException("remove: TODO MSG");
    }

    @Override
    public void set(String t) {
        throw new UnsupportedOperationException("remove: TODO MSG");
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
