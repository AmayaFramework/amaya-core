package io.github.amayaframework.context;

import jakarta.servlet.http.HttpServletResponse;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * A {@link List} implementation that represents multi-value headers
 * bound to a {@link HttpServletResponse}.
 * <p>
 * Add operations append values to the servlet response header.
 * Removal and modification operations are not supported.
 */
public final class MultiHeaderList implements List<String> {
    private final List<String> body;
    private final String header;
    private final HttpServletResponse response;

    /**
     * Constructs a new header list bound to a response.
     *
     * @param body     the backing list
     * @param header   the header name
     * @param response the backing servlet response
     */
    public MultiHeaderList(List<String> body, String header, HttpServletResponse response) {
        this.body = body;
        this.header = header;
        this.response = response;
    }

    @Override
    public int size() {
        return body.size();
    }

    @Override
    public boolean isEmpty() {
        return body.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return body.contains(o);
    }

    @Override
    public Iterator<String> iterator() {
        return new UnmodifiableIterator<>(body.iterator());
    }

    @Override
    public void forEach(Consumer<? super String> action) {
        body.forEach(action);
    }

    @Override
    public Object[] toArray() {
        return body.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return body.toArray(a);
    }

    @Override
    public <T> T[] toArray(IntFunction<T[]> generator) {
        return body.toArray(generator);
    }

    @Override
    public boolean add(String s) {
        response.addHeader(header, s);
        return body.add(s);
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("remove operation is not supported on MultiHeaderList");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        // This is absolutely legal for this case, so ignore possible ide warnings
        return body.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends String> c) {
        if (c == null || c.isEmpty()) {
            return false;
        }
        c.forEach(v -> response.addHeader(header, v));
        return body.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        if (c == null || c.isEmpty()) {
            return false;
        }
        c.forEach(v -> response.addHeader(header, v));
        return body.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("removeAll operation is not supported on MultiHeaderList");
    }

    @Override
    public boolean removeIf(Predicate<? super String> filter) {
        throw new UnsupportedOperationException("removeIf operation is not supported on MultiHeaderList");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("retainAll operation is not supported on MultiHeaderList");
    }

    @Override
    public void replaceAll(UnaryOperator<String> operator) {
        throw new UnsupportedOperationException("replaceAll operation is not supported on MultiHeaderList");
    }

    @Override
    public void sort(Comparator<? super String> c) {
        throw new UnsupportedOperationException("sort operation is not supported on MultiHeaderList");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("clear operation is not supported on MultiHeaderList");
    }

    @Override
    public String get(int index) {
        return body.get(index);
    }

    @Override
    public String set(int index, String element) {
        throw new UnsupportedOperationException("set operation is not supported on MultiHeaderList");
    }

    @Override
    public void add(int index, String element) {
        response.addHeader(header, element);
        body.add(index, element);
    }

    @Override
    public String remove(int index) {
        throw new UnsupportedOperationException("remove(index) operation is not supported on MultiHeaderList");
    }

    @Override
    public int indexOf(Object o) {
        return body.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return body.lastIndexOf(o);
    }

    @Override
    public ListIterator<String> listIterator() {
        return new MultiHeaderListIterator(body.listIterator(), header, response);
    }

    @Override
    public ListIterator<String> listIterator(int index) {
        return new MultiHeaderListIterator(body.listIterator(index), header, response);
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        return new MultiHeaderList(body.subList(fromIndex, toIndex), header, response);
    }

    @Override
    public Spliterator<String> spliterator() {
        return body.spliterator();
    }

    @Override
    public Stream<String> stream() {
        return body.stream();
    }

    @Override
    public Stream<String> parallelStream() {
        return body.parallelStream();
    }
}
