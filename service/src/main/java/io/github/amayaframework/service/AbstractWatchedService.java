package io.github.amayaframework.service;

/**
 * Skeletal implementation of {@link WatchedService}. Provides the correct basic logic for changing states.
 */
public abstract class AbstractWatchedService implements WatchedService {
    /**
     * Watched {@link Service} instance.
     */
    protected final Service service;
    /**
     * Current service state.
     */
    protected volatile ServiceState state;

    /**
     * Constructs {@link WatchedService} instance with given service to be watched.
     * Sets service state to {@link ServiceState#NEW}.
     *
     * @param service the specified {@link Service} instance to be watched
     */
    protected AbstractWatchedService(Service service) {
        this.service = service;
        this.state = ServiceState.NEW;
    }

    /**
     * Checks if current state is not {@link ServiceState#UNWATCHED}.
     * Otherwise, throws {@link IllegalStateException}.
     */
    protected void checkUnwatched() {
        if (state == ServiceState.UNWATCHED) {
            throw new IllegalStateException("Unwatched service cannot be managed");
        }
    }

    @Override
    public ServiceState getState() {
        return state;
    }

    @Override
    public void start() throws Throwable {
        if (state == ServiceState.STARTED) {
            return;
        }
        checkUnwatched();
        try {
            this.service.start();
            this.state = ServiceState.STARTED;
        } catch (Throwable e) {
            this.state = ServiceState.FAILED;
            throw e;
        }
    }

    @Override
    public void stop() throws Throwable {
        if (state == ServiceState.STOPPED) {
            return;
        }
        checkUnwatched();
        try {
            this.service.stop();
            this.state = ServiceState.STOPPED;
        } catch (Throwable e) {
            this.state = ServiceState.FAILED;
            throw e;
        }
    }
}
