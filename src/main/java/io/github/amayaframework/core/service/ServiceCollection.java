package io.github.amayaframework.core.service;

import io.github.amayaframework.core.Streamable;

public interface ServiceCollection extends Streamable<Service> {

    <T extends Service> T get(Class<T> type);

    boolean contains(Class<?> type);

    <T extends Service> T add(T service);

    <T extends Service> T remove(Class<T> type);

    void clear();
}
