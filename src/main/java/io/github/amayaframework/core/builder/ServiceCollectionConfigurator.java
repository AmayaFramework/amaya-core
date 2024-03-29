package io.github.amayaframework.core.builder;

import com.github.romanqed.jfunc.Runnable2;
import io.github.amayaframework.core.environment.Environment;
import io.github.amayaframework.core.service.ServiceCollection;

public interface ServiceCollectionConfigurator extends Runnable2<Environment, ServiceCollection> {

    @Override
    void run(Environment environment, ServiceCollection collection) throws Throwable;
}
