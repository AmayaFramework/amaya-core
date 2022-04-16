package io.github.amayaframework.core;

public interface Amaya<T> extends AutoCloseable {
    String getAddress();

    int getPort();

    T getServer();

    void start() throws Throwable;
}
