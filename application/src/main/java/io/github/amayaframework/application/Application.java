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

public interface Application<T> extends Service, Resettable {

    GroupOptionSet options();

    Environment environment();

    TaskConfigurer<T> configurer();

    ServiceManager manager();

    ServiceProvider provider();

    @Override
    void reset();

    void run(Task<T> task) throws Throwable;

    default void run(SyncTask<T> task) throws Throwable {
        run((Task<T>) task);
    }

    default void run(AsyncTask<T> task) throws Throwable {
        run((Task<T>) task);
    }

    void run() throws Throwable;
}
