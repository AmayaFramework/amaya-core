package io.github.amayaframework.application;

import com.github.romanqed.jfunc.Runnable1;
import com.github.romanqed.jfunc.Runnable2;
import io.github.amayaframework.di.ServiceProvider;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.service.ServiceManager;

/**
 *
 * @param <T>
 */
public interface Application<T> extends Resettable {

    /**
     *
     * @return
     */
    GroupOptionSet getOptions();

    /**
     *
     * @return
     */
    Environment getEnvironment();

    /**
     *
     * @return
     */
    ServiceManager getManager();

    /**
     *
     * @return
     */
    ServiceProvider getProvider();

    /**
     *
     * @param handler
     */
    void addHandler(Runnable2<T, Runnable1<T>> handler);

    /**
     *
     */
    @Override
    void reset();

    /**
     *
     * @param handler
     * @throws Throwable
     */
    void start(Runnable1<T> handler) throws Throwable;

    /**
     *
     * @throws Throwable
     */
    void start() throws Throwable;

    /**
     *
     * @throws Throwable
     */
    void stop() throws Throwable;

    /**
     *
     * @param handler
     * @throws Throwable
     */
    void run(Runnable1<T> handler) throws Throwable;

    /**
     *
     * @throws Throwable
     */
    void run() throws Throwable;

    /**
     *
     * @throws Throwable
     */
    void shutdown() throws Throwable;
}
