package io.github.amayaframework.core;

import com.github.romanqed.jfunc.Runnable1;

public interface ManagerBuilderConsumer extends Runnable1<ServiceManagerBuilder> {

    @Override
    void run(ServiceManagerBuilder builder) throws Throwable;
}
