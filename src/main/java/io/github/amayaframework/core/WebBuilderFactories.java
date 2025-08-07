package io.github.amayaframework.core;

import com.github.romanqed.jfunc.Exceptions;
import io.github.amayaframework.di.BuilderChecks;
import io.github.amayaframework.di.ProviderBuilders;
import io.github.amayaframework.di.ScopedProviderBuilder;
import io.github.amayaframework.di.stub.StubFactory;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

/**
 * Utility class providing factory methods for creating instances
 * of {@link WebBuilderFactory} with various configurations and dependencies.
 */
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

    /**
     * Creates a plain {@link WebBuilderFactory} using the provided {@link LoggerFactorySupplier}.
     * No dependency injection will be applied.
     *
     * @param supplier the logger factory supplier, must not be null
     * @return a plain {@link WebBuilderFactory} instance
     */
    public static WebBuilderFactory createPlain(LoggerFactorySupplier supplier) {
        return new PlainWebBuilderFactory(supplier.get());
    }

    /**
     * Creates a plain {@link WebBuilderFactory} with default logger factory
     * if SLF4J is detected, otherwise with null logger.
     *
     * @return a plain {@link WebBuilderFactory} instance
     */
    public static WebBuilderFactory createPlain() {
        if (LOG_LOADED) {
            return new PlainWebBuilderFactory(LoggerFactory.getILoggerFactory());
        }
        return new PlainWebBuilderFactory(null);
    }

    /**
     * Creates a provided {@link WebBuilderFactory} with explicit scoped provider builder supplier
     * and logger factory supplier, enabling dependency injection support.
     *
     * @param supplier              the scoped provider builder supplier, must not be null
     * @param loggerFactorySupplier the logger factory supplier, must not be null
     * @return a provided {@link WebBuilderFactory} instance
     */
    public static WebBuilderFactory createProvided(Supplier<ScopedProviderBuilder> supplier,
                                                   LoggerFactorySupplier loggerFactorySupplier) {
        return new ProvidedWebBuilderFactory(supplier, loggerFactorySupplier.get());
    }

    /**
     * Creates a provided {@link WebBuilderFactory} with scoped provider builder supplier
     * and default logger factory if SLF4J is detected, otherwise with null logger.
     *
     * @param supplier the scoped provider builder supplier, must not be null
     * @return a provided {@link WebBuilderFactory} instance
     */
    public static WebBuilderFactory createProvided(Supplier<ScopedProviderBuilder> supplier) {
        if (LOG_LOADED) {
            return new ProvidedWebBuilderFactory(supplier, LoggerFactory.getILoggerFactory());
        }
        return new ProvidedWebBuilderFactory(supplier, null);
    }

    /**
     * Creates a provided {@link WebBuilderFactory} optionally enabling builder checks,
     * with explicit logger factory supplier.
     *
     * @param enableChecks          whether to enable full builder validation
     * @param loggerFactorySupplier the logger factory supplier, must not be null
     * @return a provided {@link WebBuilderFactory} instance
     */
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

    /**
     * Creates a provided {@link WebBuilderFactory} with full builder validation enabled
     * and the specified logger factory supplier.
     *
     * @param loggerFactorySupplier the logger factory supplier, must not be null
     * @return a provided {@link WebBuilderFactory} instance with validation enabled
     */
    public static WebBuilderFactory createProvided(LoggerFactorySupplier loggerFactorySupplier) {
        return createProvided(true, loggerFactorySupplier);
    }

    /**
     * Creates a provided {@link WebBuilderFactory} optionally enabling builder checks,
     * using default logger factory if available.
     *
     * @param enableChecks whether to enable full builder validation
     * @return a provided {@link WebBuilderFactory} instance
     */
    public static WebBuilderFactory createProvided(boolean enableChecks) {
        var stubFactory = loadStubFactory();
        if (enableChecks) {
            return createProvided(() -> ProviderBuilders.createScoped(stubFactory, BuilderChecks.VALIDATE_ALL));
        }
        return createProvided(() -> ProviderBuilders.createScoped(stubFactory));
    }

    /**
     * Creates a {@link WebBuilderFactory} based on presence of DI support.
     * If DI is detected, creates a provided factory with builder checks enabled;
     * otherwise, creates a plain factory.
     *
     * @param supplier the logger factory supplier, must not be null
     * @return a {@link WebBuilderFactory} instance
     */
    public static WebBuilderFactory create(LoggerFactorySupplier supplier) {
        if (DI_LOADED) {
            return createProvided(true, supplier);
        }
        return createPlain(supplier);
    }

    /**
     * Creates a {@link WebBuilderFactory} based on presence of DI support.
     * If DI is detected, creates a provided factory with builder checks enabled;
     * otherwise, creates a plain factory.
     *
     * @return a {@link WebBuilderFactory} instance
     */
    public static WebBuilderFactory create() {
        if (DI_LOADED) {
            return createProvided(true);
        }
        return createPlain();
    }
}
