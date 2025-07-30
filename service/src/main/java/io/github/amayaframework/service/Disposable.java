package io.github.amayaframework.service;

/**
 * Represents a resource or component that requires explicit release of resources.
 * Implementations should free all held resources when {@link #dispose()} is called.
 *
 * <p>The {@code dispose()} method must not throw any exceptions and should
 * complete as quickly as possible to avoid resource leaks or deadlocks.</p>
 */
public interface Disposable {

    /**
     * Releases any resources held by this instance.
     * After calling, the instance should be considered unusable.
     *
     * <p>This method must not throw exceptions and should execute promptly.</p>
     */
    void dispose();
}
