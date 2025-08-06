package io.github.amayaframework.core;

import com.github.romanqed.jfunc.Exceptions;
import io.github.amayaframework.di.BuilderChecks;
import io.github.amayaframework.di.ProviderBuilders;
import io.github.amayaframework.di.ScopedProviderBuilder;
import io.github.amayaframework.di.stub.StubFactory;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.function.Supplier;

public final class WebBuilderFactories {
    private static final boolean LOG_LOADED = LookupUtil.isSlf4jLoaded();
    private static final boolean DI_LOADED = LookupUtil.isDiLoaded();

    private WebBuilderFactories() {
    }

    private static StubFactory loadStubFactory() {
        if (!DI_LOADED) {
            return null;
        }
        var clazz = LookupUtil.lookupStubFactory();
        if (clazz == null) {
            return null;
        }
        try {
            return (StubFactory) clazz.getConstructor().newInstance((Object[]) null);
        } catch (Throwable e) {
            Exceptions.throwAny(e);
            // Unreachable code to suppress javac error
            return null;
        }
    }

    public static WebBuilderFactory createPlain(LoggerFactorySupplier supplier) {
        return new PlainWebBuilderFactory(HashMap::new, supplier.get());
    }

    public static WebBuilderFactory createPlain() {
        if (LOG_LOADED) {
            return new PlainWebBuilderFactory(HashMap::new, LoggerFactory.getILoggerFactory());
        }
        return new PlainWebBuilderFactory(HashMap::new, null);
    }

    public static WebBuilderFactory createProvided(Supplier<ScopedProviderBuilder> supplier,
                                                   LoggerFactorySupplier loggerFactorySupplier) {
        return new ProvidedWebBuilderFactory(HashMap::new, supplier, loggerFactorySupplier.get());
    }

    public static WebBuilderFactory createProvided(Supplier<ScopedProviderBuilder> supplier) {
        if (LOG_LOADED) {
            return new ProvidedWebBuilderFactory(HashMap::new, supplier, LoggerFactory.getILoggerFactory());
        }
        return new ProvidedWebBuilderFactory(HashMap::new, supplier, null);
    }

    public static WebBuilderFactory createProvided(boolean enableChecks, LoggerFactorySupplier loggerFactorySupplier) {
        var stubFactory = loadStubFactory();
        if (enableChecks) {
            return createProvided(
                    () -> ProviderBuilders.createScoped(stubFactory, BuilderChecks.VALIDATE_ALL),
                    loggerFactorySupplier
            );
        }
        return createProvided(() -> ProviderBuilders.createScoped(stubFactory), loggerFactorySupplier);
    }

    public static WebBuilderFactory createProvided(boolean enableChecks) {
        var stubFactory = loadStubFactory();
        if (enableChecks) {
            return createProvided(() -> ProviderBuilders.createScoped(stubFactory, BuilderChecks.VALIDATE_ALL));
        }
        return createProvided(() -> ProviderBuilders.createScoped(stubFactory));
    }

    public static WebBuilderFactory create(LoggerFactorySupplier supplier) {
        if (DI_LOADED) {
            return createProvided(true, supplier);
        }
        return createPlain(supplier);
    }

    public static WebBuilderFactory create() {
        if (DI_LOADED) {
            return createProvided(true);
        }
        return createPlain();
    }
}
