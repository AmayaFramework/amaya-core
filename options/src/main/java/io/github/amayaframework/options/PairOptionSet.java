package io.github.amayaframework.options;

import com.github.romanqed.jfunc.Runnable1;
import com.github.romanqed.jfunc.Runnable2;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

final class PairOptionSet implements OptionSet {
    private final String key;
    private final Object value;

    public PairOptionSet(String key, Object value) {
        this.key = Objects.requireNonNull(key);
        this.value = value;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        if (!this.key.equals(key)) {
            return null;
        }
        return (T) value;
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
        return false;
    }

    @Override
    public boolean asKey(String key) {
        if (!this.key.equals(key)) {
            return false;
        }
        return value != Boolean.FALSE;
    }

    @Override
    public boolean asBool(String key) {
        if (!this.key.equals(key)) {
            return false;
        }
        if (value == null) {
            return false;
        }
        return value != Boolean.FALSE;
    }

    @Override
    public boolean contains(String key) {
        return this.key.equals(key);
    }

    @Override
    public Set<String> getKeys() {
        return Set.of(key);
    }

    @Override
    public Map<String, Object> asMap() {
        return Map.of(key, value);
    }

    @Override
    public void forEach(Runnable1<String> action) {
        try {
            action.run(key);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void forEach(Runnable2<String, Object> action) {
        try {
            action.run(key, value);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Options {" + key + "=" + value + "}";
    }
}
