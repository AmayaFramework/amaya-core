package io.github.amayaframework.core;

import com.github.romanqed.jconv.LinkedPipelineBuilder;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.di.Builders;
import io.github.amayaframework.di.ServiceProviderBuilder;
import io.github.amayaframework.environment.EnvironmentFactory;
import io.github.amayaframework.environment.NativeEnvironmentFactory;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.service.HandledManagerFactory;
import io.github.amayaframework.service.ServiceHandler;
import io.github.amayaframework.service.ServiceManagerFactory;

import java.util.Objects;
import java.util.function.Supplier;

public final class CoreBuilderFactory implements ApplicationBuilderFactory {
    private static final String AMAYA_DI = "io.github.amayaframework.di";
    private static final EnvironmentFactory DEFAULT_ENVIRONMENT_FACTORY = new NativeEnvironmentFactory();
    private static final ServiceHandler DEFAULT_HANDLER = new PlainServiceHandler();
    private static final ServiceManagerFactory DEFAULT_MANAGER_FACTORY = new HandledManagerFactory();

    private final Supplier<ServiceProviderBuilder> supplier;

    public CoreBuilderFactory(Supplier<ServiceProviderBuilder> supplier) {
        // DI module loaded
        this.supplier = Objects.requireNonNull(supplier);
    }

    public CoreBuilderFactory() {
        if (isDILoaded()) {
            this.supplier = Builders::createChecked;
        } else {
            this.supplier = null;
        }
    }

    private static boolean isDILoaded() {
        var layer = ModuleLayer.boot();
        return layer.findModule(AMAYA_DI).isPresent();
    }

    @Override
    public ApplicationBuilder create(GroupOptionSet options) {
        var ret = create();
        ret.setOptions(options);
        return ret;
    }

    private ApplicationBuilder createPlain() {
        var managerBuilder = new PlainManagerBuilder(DEFAULT_MANAGER_FACTORY, DEFAULT_HANDLER);
        var handlerBuilder = new LinkedPipelineBuilder<HttpContext>();
        return new PlainApplicationBuilder(managerBuilder, handlerBuilder, DEFAULT_ENVIRONMENT_FACTORY);
    }

    private ApplicationBuilder createProvided() {
        var managerBuilder = new ProvidedManagerBuilder(DEFAULT_MANAGER_FACTORY, DEFAULT_HANDLER);
        var handlerBuilder = new LinkedPipelineBuilder<HttpContext>();
        return new ProvidedApplicationBuilder(managerBuilder, handlerBuilder, supplier, DEFAULT_ENVIRONMENT_FACTORY);
    }

    @Override
    public ApplicationBuilder create() {
        if (isDILoaded()) {
            return createProvided();
        }
        return createPlain();
    }
}
