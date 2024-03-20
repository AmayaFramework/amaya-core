package io.github.amayaframework.core.configuration;

import java.util.Map;

public interface Configuration {

    <T> T get(Entry<T> entry);

    <T> void set(Entry<T> entry, T value);

    boolean contains(Entry<?> entry);

    <T> T remove(Entry<T> entry);

    Map<Entry<?>, Object> asMap();
}
