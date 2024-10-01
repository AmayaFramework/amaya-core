package io.github.amayaframework.core;

import com.github.romanqed.jfunc.Runnable1;
import io.github.amayaframework.di.ServiceProvider;

public interface ProviderConsumer extends Runnable1<ServiceProvider> {

    @Override
    void run(ServiceProvider provider) throws Throwable;
}
