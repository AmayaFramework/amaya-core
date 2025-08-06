package io.github.amayaframework.service;

import com.github.romanqed.jfunc.Runnable1;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * Manages a collection of {@link Service} instances,
 * providing methods to add, remove, and control lifecycle of multiple services as one unit.
 * Extends {@link Service} to participate in lifecycle management.
 */
public interface ServiceManager extends Service {

    /**
     * Adds a single {@link Service} to be managed.
     *
     * @param service the service to add
     */
    void add(Service service);

    /**
     * Adds multiple {@link Service} instances to be managed.
     *
     * @param services iterable collection of services to add
     */
    void add(Iterable<Service> services);

    /**
     * Removes a single {@link Service} from management.
     *
     * @param service the service to remove
     */
    void remove(Service service);

    /**
     * Removes multiple {@link Service} instances from management.
     *
     * @param services iterable collection of services to remove
     */
    void remove(Iterable<Service> services);

    /**
     * Removes all managed services, returning the removed services as a collection.
     *
     * @return unmodifiable collection of removed services
     */
    Collection<Service> removeAll();

    /**
     * Returns an unmodifiable collection of currently managed services.
     *
     * @return unmodifiable collection of managed services
     */
    Collection<Service> services();

    /**
     * Gets the current {@link Runnable1} action invoked on service failure.
     *
     * @return the failure handler consumer, or {@code null} if none set
     */
    Runnable1<Throwable> onFailure();

    /**
     * Sets a {@link Runnable1} to be invoked on service failure.
     *
     * @param action the failure handler consumer
     */
    void onFailure(Runnable1<Throwable> action);

    /**
     * Gets the current {@link Consumer} action invoked on service halt.
     *
     * @return the halt handler consumer, or {@code null} if none set
     */
    Consumer<Throwable> onHalt();

    /**
     * Sets a {@link Consumer} to be invoked on service halt.
     *
     * @param action the halt handler consumer
     */
    void onHalt(Consumer<Throwable> action);
}
