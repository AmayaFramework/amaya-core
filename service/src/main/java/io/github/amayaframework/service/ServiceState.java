package io.github.amayaframework.service;

/**
 * An enumeration containing the possible states of the {@link WatchedService}.
 */
public enum ServiceState {

    /**
     * The state of the service, for which no {@link Service#start()} or {@link Service#stop()} has ever been called.
     */
    NEW,

    /**
     * The status of the service for which the {@link Service#start()} call was successfully completed.
     */
    STARTED,

    /**
     * The status of the service for which the {@link Service#stop()} call was successfully completed.
     */
    STOPPED,

    /**
     * The state of the service for which the {@link Service#start()} or {@link Service#stop()} call
     * ended with an exception being thrown.
     */
    FAILED,

    /**
     * The state of the service for which {@link WatchedService#unwatch()} was called.
     */
    UNWATCHED
}
