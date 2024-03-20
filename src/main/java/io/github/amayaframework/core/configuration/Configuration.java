package io.github.amayaframework.core.configuration;

import io.github.amayaframework.core.Mappable;

public interface Configuration extends Mappable<Entry<?>, Object> {

    <T> T get(Entry<T> entry);

    <T> void set(Entry<T> entry, T value);

    boolean contains(Entry<?> entry);

    <T> T remove(Entry<T> entry);
}
