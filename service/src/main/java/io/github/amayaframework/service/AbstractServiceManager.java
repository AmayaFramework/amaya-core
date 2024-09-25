package io.github.amayaframework.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Skeletal implementation of {@link ServiceManager}.
 * Provides logic for adding and removing services, as well as synchronization.
 */
public abstract class AbstractServiceManager implements ServiceManager {
    /**
     * {@link Set} containing all managed services.
     */
    protected final Set<WatchedService> services;
    /**
     * Lock-object for synchronisation.
     */
    protected final Object lock;
    private final Set<WatchedService> wrapped;

    /**
     * Constructs {@link ServiceManager} instance with given service set.
     *
     * @param services the specified {@link Set} instance
     */
    protected AbstractServiceManager(Set<WatchedService> services) {
        this.services = services;
        this.wrapped = new HashSet<>();
        this.lock = new Object();
    }

    @Override
    public WatchedService add(Service service) {
        Objects.requireNonNull(service);
        synchronized (lock) {
            var managed = new ManagedService(service);
            services.add(managed);
            var wrap = new SynchronizedService(managed);
            wrapped.add(wrap);
            return wrap;
        }
    }

    @Override
    public Iterable<WatchedService> getServices() {
        return Collections.unmodifiableCollection(wrapped);
    }

    private final class SynchronizedService implements WatchedService {
        private final ManagedService service;

        private SynchronizedService(ManagedService service) {
            this.service = service;
        }

        @Override
        public void start() throws Throwable {
            synchronized (lock) {
                service.start();
            }
        }

        @Override
        public void stop() throws Throwable {
            synchronized (lock) {
                service.stop();
            }
        }

        @Override
        public ServiceState getState() {
            return service.state;
        }

        @Override
        public Service unwatch() {
            synchronized (lock) {
                return service.unwatch();
            }
        }
    }

    private final class ManagedService extends AbstractWatchedService {

        ManagedService(Service service) {
            super(service);
        }

        @Override
        public Service unwatch() {
            state = ServiceState.UNWATCHED;
            services.remove(this);
            return service;
        }
    }
}
