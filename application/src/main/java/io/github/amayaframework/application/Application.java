package io.github.amayaframework.application;

import com.github.romanqed.jfunc.Runnable1;
import com.github.romanqed.jfunc.Runnable2;
import io.github.amayaframework.di.ServiceProvider;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.service.ServiceManager;

public interface Application<T> extends Resettable {

    GroupOptionSet getOptions();

    Environment getEnvironment();

    ServiceManager getManager();

    ServiceProvider getProvider();

    void addHandler(Runnable2<T, Runnable1<T>> handler);

    @Override
    void reset();

    void start(Runnable1<T> handler) throws Throwable;

    void start() throws Throwable;

    void stop() throws Throwable;

    void run(Runnable1<T> handler) throws Throwable;

    void run() throws Throwable;

    void shutdown() throws Throwable;
}
