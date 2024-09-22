package io.github.amayaframework.options;

import com.github.romanqed.jfunc.Runnable1;
import com.github.romanqed.jfunc.Runnable2;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public final class EmptyOptionSet implements OptionSet {

    @Override
    public <T> T get(String key) {
        return null;
    }

    @Override
    public Object set(String key, Object value) {
        return null;
    }

    @Override
    public Object remove(String key) {
        return null;
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
}
