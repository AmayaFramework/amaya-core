package io.github.amayaframework.core.configuration;

import io.github.amayaframework.core.util.Streamable;

public interface Scheme extends Streamable<Key<?>> {

    void add(Key<?> key);

    Key<?> get(String name);

    boolean remove(Key<?> key);
}
