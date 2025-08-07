package io.github.amayaframework.application;

import com.github.romanqed.jconv.AsyncTask;
import com.github.romanqed.jconv.SyncTask;
import com.github.romanqed.jconv.Task;
import com.github.romanqed.jconv.TaskConfigurer;
import io.github.amayaframework.di.core.ServiceProvider;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.service.Service;
import io.github.amayaframework.service.ServiceManager;

/**
 * Represents the core application contract for managing lifecycle, configuration,
 * environment, and execution of tasks within the framework.
 * <p>
 * This interface extends {@link Service} to participate in lifecycle management
 * and {@link Resettable} to allow resetting internal state, enabling
 * flexible task reconfiguration and repeated application runs.
 * </p>
 *
 * <p>
 * The {@link #reset()} method clears the current task and configuration builder,
 * allowing subsequent runs to build or receive a new task for execution.
 * This supports scenarios where the application can start with an explicitly
 * provided task via {@link #run(Task)}, or after reset, automatically
 * construct a task using the configured {@link #configurer()}.
 * </p>
 *
 * @param <T> the type of result produced by tasks executed by this application
 */
public interface Application<T> extends Service, Resettable {

    /**
     * Returns the grouped set of configuration options available to this application.
     *
     * @return the {@link GroupOptionSet} containing application options
     */
    GroupOptionSet options();

    /**
     * Returns the current {@link Environment} associated with this application,
     * providing access to environment-specific data and context.
     *
     * @return the application {@link Environment}
     */
    Environment environment();

    /**
     * Returns the {@link TaskConfigurer} responsible for configuring the task
     * that this application will run.
     * <p>
     * The configurer allows building or modifying the task configuration dynamically,
     * which is used when no explicit task is provided to {@link #run(Task)}.
     * After {@link #reset()}, the application will use this configurer
     * to build the next task to execute.
     * </p>
     *
     * @return the task configurer instance
     */
    TaskConfigurer<T> configurer();

    /**
     * Returns the {@link ServiceManager} responsible for managing
     * the lifecycle of internal services within this application.
     *
     * @return the internal {@link ServiceManager}
     */
    ServiceManager manager();

    /**
     * Returns the {@link ServiceProvider} for resolving dependencies
     * and services within the application context.
     *
     * @return the application {@link ServiceProvider}
     */
    ServiceProvider provider();

    /**
     * Resets the application state by clearing the currently set task and
     * resetting the internal task configuration builder.
     * <p>
     * This allows the application to discard any previously configured or running task,
     * enabling later runs to build a new task using {@link #configurer()},
     * or to run with a newly provided explicit task.
     * <p>
     * Typical usage is to call {@code reset()} after stopping the application,
     * then modify the task configuration, and finally call {@link #run()} or
     * {@link #start()} to run the new or rebuilt task.
     * </p>
     */
    @Override
    void reset();

    /**
     * Executes the specified {@link Task} within the application context.
     *
     * @param task the {@link Task} to execute
     * @throws Throwable if execution fails
     */
    void run(Task<T> task) throws Throwable;

    /**
     * Executes the given synchronous {@link SyncTask} within the application context.
     * <p>
     * This is a convenience method that delegates to {@link #run(Task)}.
     * </p>
     *
     * @param task the synchronous task to run
     * @throws Throwable if task execution or lifecycle management fails
     */
    default void run(SyncTask<T> task) throws Throwable {
        run((Task<T>) task);
    }

    /**
     * Executes the given asynchronous {@link AsyncTask} within the application context.
     * <p>
     * This is a convenience method that delegates to {@link #run(Task)}.
     * </p>
     *
     * @param task the asynchronous task to run
     * @throws Throwable if task execution or lifecycle management fails
     */
    default void run(AsyncTask<T> task) throws Throwable {
        run((Task<T>) task);
    }

    /**
     * Executes the application using the currently configured task.
     * <p>
     * If no explicit task was provided previously (via {@link #run(Task)}), this method
     * will build a task using the {@link #configurer()} and then run it.
     * <p>
     * This enables running the application multiple times with the same or updated
     * configuration without explicitly passing a task each time.
     * </p>
     *
     * @throws Throwable if task execution or lifecycle management fails
     */
    void run() throws Throwable;
}
