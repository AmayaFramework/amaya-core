package io.github.amayaframework.core.builder;

import com.github.romanqed.jfunc.Runnable2;
import io.github.amayaframework.core.environment.Environment;
import io.github.amayaframework.di.ManualProviderBuilder;

public interface ServiceProviderConfigurator extends Runnable2<Environment, ManualProviderBuilder> {

    @Override
    void run(Environment environment, ManualProviderBuilder builder) throws Throwable;
}
