package io.github.amayaframework.core.configuration;

import io.github.amayaframework.core.util.Mappable;

public interface Configuration extends Mappable<Key<?>, Object> {

    <T> T get(Key<T> key);

    <T> void set(Key<T> key, T value);

    boolean contains(Key<?> key);

    <T> T remove(Key<T> key);
}
