package io.github.amayaframework.service;

/**
 * Represents the various lifecycle states of a {@link Service},
 * including allowed transitions between states.
 */
public enum ServiceState {

    /**
     * The service is not managed by any manager and is in an undefined state.
     * <p>
     * No valid transitions from this state, as it represents an unmanaged service.
     */
    UNMANAGED(false, true),

    /**
     * The service has been created but not yet started.
     * <p>
     * Allowed transitions:
     * <ul>
     *   <li>{@link #STARTING} - when the service begins starting process</li>
     *   <li>{@link #DISPOSED} - if the service is disposed before starting</li>
     * </ul>
     */
    NEW(true),

    /**
     * The service has been disposed and can no longer be used.
     * <p>
     * Terminal state with no allowed transitions.
     */
    DISPOSED(true, true),

    /**
     * The service has encountered a failure and is no longer operational.
     * <p>
     * Allowed transitions:
     * <ul>
     *   <li>{@link #STARTING} - supports restart attempts after failure</li>
     * </ul>
     */
    FAILED(true),

    /**
     * The service is in the process of starting.
     * <p>
     * Allowed transitions:
     * <ul>
     *   <li>{@link #STARTED} - successful start</li>
     *   <li>{@link #FAILED} - start failed</li>
     *   <li>{@link #DISPOSED} - optionally disposed during start</li>
     * </ul>
     */
    STARTING(false),

    /**
     * The service is fully started and operational.
     * <p>
     * Allowed transitions:
     * <ul>
     *   <li>{@link #STOPPING} - when the service is requested to stop</li>
     *   <li>{@link #FAILED} - if service failure occurs while running</li>
     * </ul>
     */
    STARTED(false),

    /**
     * The service is in the process of stopping.
     * <p>
     * Allowed transitions:
     * <ul>
     *   <li>{@link #STOPPED} - successful stop</li>
     *   <li>{@link #FAILED} - failure occurred during stopping</li>
     * </ul>
     */
    STOPPING(false),

    /**
     * The service has stopped and is no longer running.
     * <p>
     * Allowed transitions:
     * <ul>
     *   <li>{@link #STARTING} - supports restarting after stop</li>
     *   <li>{@link #DISPOSED} - service can be disposed after stop</li>
     * </ul>
     */
    STOPPED(true);

    private final boolean isStopped;
    private final boolean isTerminal;

    ServiceState(boolean isStopped, boolean isTerminal) {
        this.isStopped = isStopped;
        this.isTerminal = isTerminal;
    }

    ServiceState(boolean isStopped) {
        this(isStopped, false);
    }

    /**
     * Returns whether this state represents a stopped or non-running service.
     *
     * @return {@code true} if the service is stopped or inactive, {@code false} otherwise.
     */
    public boolean isStopped() {
        return isStopped;
    }

    /**
     * Returns whether this state is terminal
     *
     * @return {@code true} if the state is terminal, {@code false} otherwise
     */
    public boolean isTerminal() {
        return isTerminal;
    }
}
