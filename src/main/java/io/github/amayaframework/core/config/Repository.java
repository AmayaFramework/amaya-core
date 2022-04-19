package io.github.amayaframework.core.config;

public interface Repository<T, K> {
    T get(K key);

    T put(K key, T t);

    T remove(K key);

    Runnable lock(K key);
}
