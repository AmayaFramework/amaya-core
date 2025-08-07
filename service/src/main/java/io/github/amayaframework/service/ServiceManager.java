package io.github.amayaframework.service;

import com.github.romanqed.jfunc.Runnable1;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * A composite {@link Service} that manages a collection of other {@link Service} instances.
 * <p>
 * Provides centralized lifecycle control and dynamic registration/removal of services.
 * </p>
 *
 * <p><strong>Thread-safety:</strong> All operations are thread-safe.
 * </p>
 *
 * <p><strong>Lifecycle rules:</strong>
 * <ul>
 *   <li>Single services can be added or removed at any time, including while the manager is running.</li>
 *   <li>Batch operations (e.g., {@link #add(Iterable)} or {@link #remove(Iterable)}) are allowed only when the manager
 *   is <em>not running</em>.</li>
 *   <li>Services must be in a stopped or unmanaged state before being added.</li>
 *   <li>Disposed services cannot be added.</li>
 *   <li>After removal, service callbacks are reset to defaults.</li>
 * </ul>
 * </p>
 */
public interface ServiceManager extends Service {

    /**
     * Adds a single {@link Service} to this manager.
     * <p>
     * If the manager is running, the service will be immediately started using the manager's current callback.
     * <p>
     * If the service is already present, this call has no effect.
     *
     * @param service the service to add, may be {@code null}
     * @throws IllegalStateException    if the manager is disposed
     * @throws IllegalArgumentException if the service is not stopped
     */
    void add(Service service);

    /**
     * Adds multiple {@link Service} instances to this manager.
     * <p>
     * This operation is only allowed when the manager is in a fully stopped state.
     * <p>
     * All services must be stopped or unmanaged before being added.
     * Disposed services are rejected with {@link IllegalArgumentException}.
     * Already present services are silently skipped.
     *
     * @param iterable iterable collection of services to add, may be {@code null}
     * @throws IllegalStateException    if the manager is running or disposed
     * @throws IllegalArgumentException if any service is not stopped
     */
    void add(Iterable<Service> iterable);

    /**
     * Removes a single {@link Service} from this manager.
     * <p>
     * If the service was running, it will be stopped before removal.
     * If the service is not present, this call has no effect.
     *
     * @param service the service to remove, may be {@code null}
     * @throws IllegalStateException if the manager is disposed or if stopping the service fails
     */
    void remove(Service service);

    /**
     * Removes multiple {@link Service} instances from this manager.
     * <p>
     * This operation is only allowed when the manager is fully stopped.
     * Present services will be removed and disconnected from the manager.
     *
     * @param iterable iterable collection of services to remove, may be {@code null}
     * @throws IllegalStateException if the manager is running or disposed
     */
    void remove(Iterable<Service> iterable);

    /**
     * Removes all managed services from this manager and returns them.
     * <p>
     * This operation is only allowed when the manager is fully stopped.
     *
     * @return unmodifiable collection of removed services (maybe empty)
     * @throws IllegalStateException if the manager is running or disposed
     */
    Collection<Service> removeAll();

    /**
     * Returns a snapshot of currently managed services.
     * <p>
     * The returned collection is a read-only copy and safe for concurrent iteration.
     *
     * @return unmodifiable snapshot collection of services; never {@code null}
     */
    Collection<Service> services();

    /**
     * Returns the current failure handler callback, or {@code null} if not set.
     * <p>
     * This handler is invoked when a managed service fails with an exception.
     * <p>
     * Exceptions thrown by this handler will propagate to the manager's error handling logic.
     *
     * @return the failure handler callback
     */
    Runnable1<Throwable> onFailure();

    /**
     * Sets the failure handler callback to be invoked when a managed service fails.
     * <p>
     * Exceptions thrown from the handler will not be suppressed.
     *
     * @param action the callback to set, may be {@code null}
     */
    void onFailure(Runnable1<Throwable> action);

    /**
     * Returns the current halt handler, or {@code null} if not set.
     * <p>
     * This handler is invoked when a managed service signals a halt condition.
     * <p>
     * This handler must <strong>not</strong> throw exceptions; any thrown exceptions will be ignored.
     *
     * @return the halt handler callback
     */
    Consumer<Throwable> onHalt();

    /**
     * Sets the halt handler to be invoked when a managed service halts execution.
     * <p>
     * This handler must not throw exceptions; any thrown exceptions will be ignored.
     *
     * @param action the callback to set, may be {@code null}
     */
    void onHalt(Consumer<Throwable> action);

    @Override
    default void start(ServiceCallback callback) throws Throwable {
        start(null, callback);
    }

    @Override
    default void start() throws Throwable {
        start(null, EmptyServiceCallback.CALLBACK);
    }

    @Override
    default void stop() throws Throwable {
        stop(null);
    }
}
