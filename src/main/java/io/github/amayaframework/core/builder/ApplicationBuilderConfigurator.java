package io.github.amayaframework.core.builder;

import com.github.romanqed.jfunc.Runnable1;

public interface ApplicationBuilderConfigurator extends Runnable1<ApplicationBuilder> {

    @Override
    void run(ApplicationBuilder builder) throws Throwable;
}
