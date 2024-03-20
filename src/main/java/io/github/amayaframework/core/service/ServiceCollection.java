package io.github.amayaframework.core.service;

public interface ServiceCollection extends Iterable<Service> {

    <T extends Service> T get(Class<T> type);

    boolean contains(Class<?> type);

    <T extends Service> T add(T service);

    <T extends Service> T remove(Class<T> type);
}
