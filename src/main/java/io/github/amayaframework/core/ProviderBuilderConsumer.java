package io.github.amayaframework.core;

import com.github.romanqed.jfunc.Runnable1;
import io.github.amayaframework.di.ServiceProviderBuilder;

public interface ProviderBuilderConsumer extends Runnable1<ServiceProviderBuilder> {

    @Override
    void run(ServiceProviderBuilder builder) throws Throwable;
}
