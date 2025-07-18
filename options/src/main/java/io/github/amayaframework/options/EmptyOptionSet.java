package io.github.amayaframework.options;

import com.github.romanqed.jfunc.Runnable1;
import com.github.romanqed.jfunc.Runnable2;

import java.util.*;
import java.util.function.Consumer;

/**
 * Unmodifiable implementation of {@link OptionSet} that contains no values.
 */
public final class EmptyOptionSet implements OptionSet {
    private static final EmptyIterator EMPTY_ITERATOR = new EmptyIterator();

    @Override
    public <T> T get(String key) {
        return null;
    }

    @Override
    public Object set(String key, Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object remove(String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean asKey(String key) {
        return false;
    }

    @Override
    public boolean asBool(String key) {
        return false;
    }

    @Override
    public boolean contains(String key) {
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<String> getKeys() {
        return Collections.EMPTY_SET;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> asMap() {
        return Collections.EMPTY_MAP;
    }

    @Override
    public void forEach(Runnable1<String> action) {
        // Do nothing
    }

    @Override
    public void forEach(Runnable2<String, Object> action) {
        // Do nothing
    }

    @Override
    public String toString() {
        return "Options {}";
    }

    @Override
    public Iterator<String> iterator() {
        return EMPTY_ITERATOR;
    }

    @Override
    public void forEach(Consumer<? super String> action) {
        // Do nothing
    }

    @Override
    public Spliterator<String> spliterator() {
        return Spliterators.emptySpliterator();
    }

    private static final class EmptyIterator implements Iterator<String> {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public String next() {
            throw new NoSuchElementException();
        }
    }
}
