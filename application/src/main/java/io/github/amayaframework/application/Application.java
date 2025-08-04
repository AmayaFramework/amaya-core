package io.github.amayaframework.application;

import com.github.romanqed.jconv.Task;
import com.github.romanqed.jconv.TaskConfigurer;
import io.github.amayaframework.di.core.ServiceProvider;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.service.Service;
import io.github.amayaframework.service.ServiceManager;

public interface Application<T> extends Service, Resettable {

    GroupOptionSet getOptions();

    Environment getEnvironment();

    TaskConfigurer<T> getConfigurer();

    ServiceManager getServiceManager();

    ServiceProvider getServiceProvider();

    @Override
    void reset();

    void run(Task<T> task) throws Throwable;

    void run() throws Throwable;
}
