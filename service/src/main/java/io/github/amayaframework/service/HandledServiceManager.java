package io.github.amayaframework.service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * A thread-safe implementation of a service manager that uses {@link ServiceHandler}
 * to perform start and stop operations on managed services.
 */
public final class HandledServiceManager extends AbstractServiceManager {
    private final ServiceHandler handler;

    /**
     * Constructs {@link HandledServiceManager} instance with given supplier providing {@link Set}
     * instance to store managed services and {@link ServiceHandler} instance.
     *
     * @param supplier the specified supplier providing service {@link Set} instance
     * @param handler  the specified {@link ServiceHandler} instance
     */
    public HandledServiceManager(Supplier<Set<WatchedService>> supplier, ServiceHandler handler) {
        super(Objects.requireNonNull(supplier.get()));
        this.handler = Objects.requireNonNull(handler);
    }

    /**
     * Constructs {@link HandledServiceManager} instance with given {@link ServiceHandler} instance
     *
     * @param handler the specified {@link ServiceHandler} instance
     */
    public HandledServiceManager(ServiceHandler handler) {
        super(new HashSet<>());
        this.handler = Objects.requireNonNull(handler);
    }

    @Override
    public void start() throws Throwable {
        synchronized (lock) {
            handler.start(services);
        }
    }

    @Override
    public void stop() throws Throwable {
        synchronized (lock) {
            handler.stop(services);
        }
    }
}
