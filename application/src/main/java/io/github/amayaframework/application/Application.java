package io.github.amayaframework.application;

import com.github.romanqed.jfunc.Runnable1;
import com.github.romanqed.jfunc.Runnable2;
import io.github.amayaframework.di.ServiceProvider;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.service.ServiceManager;

/**
 * An interface describing an abstract application that manages services.
 *
 * @param <T> the type of application context
 */
public interface Application<T> extends Resettable {

    /**
     * Gets the application option set.
     *
     * @return the {@link GroupOptionSet} instance
     */
    GroupOptionSet getOptions();

    /**
     * Gets the application environment.
     *
     * @return the {@link Environment} instance
     */
    Environment getEnvironment();

    /**
     * Gets the {@link ServiceManager} instance managed by this application.
     *
     * @return the {@link ServiceManager}
     */
    ServiceManager getManager();

    /**
     * Gets the {@link ServiceProvider} instance.
     *
     * @return the {@link ServiceProvider} instance if amaya di module loaded, null otherwise
     */
    ServiceProvider getProvider();

    /**
     * Adds middlewared handler to chain of handlers.
     *
     * @param handler the context handler instance, must be non-null
     */
    void addHandler(Runnable2<T, Runnable1<T>> handler);

    /**
     * Removes all added middleware handlers. Sets an empty handler instead.
     */
    @Override
    void reset();

    /**
     * Starts application with given context handler and removes all added middlewares.
     *
     * @param handler the context handler, may be null
     * @throws Throwable if any problems during application start occurred
     */
    void start(Runnable1<T> handler) throws Throwable;

    /**
     * Starts application with added middleware handlers.
     *
     * @throws Throwable if any problems during application start occurred
     */
    void start() throws Throwable;

    /**
     * Stops application and removes jvm shutdown callback if it registered.
     *
     * @throws Throwable if any problems during application stop occurred
     */
    void stop() throws Throwable;

    /**
     * Starts application with given context handler and removes all added middlewares.
     * Also registers jvm shutdown callback, so after calling this method you do not need stops application manually.
     *
     * @param handler the context handler, may be null
     * @throws Throwable if any problems during application start occurred
     */
    void run(Runnable1<T> handler) throws Throwable;

    /**
     * Starts application with added middleware handlers.
     * Also registers jvm shutdown callback, so after calling this method you do not need stops application manually.
     *
     * @throws Throwable if any problems during application start occurred
     */
    void run() throws Throwable;

    /**
     * Irreversible stops application. Closes environment. Removes jvm shutdown callback if it registered.
     *
     * @throws Throwable if any problems during application stop occurred
     */
    void shutdown() throws Throwable;
}
