package io.github.amayaframework.core;

import org.slf4j.ILoggerFactory;

import java.util.function.Supplier;

/**
 * A functional interface extending {@link Supplier} to provide
 * an SLF4J {@link ILoggerFactory} instance.
 */
public interface LoggerFactorySupplier extends Supplier<ILoggerFactory> {

    /**
     * Gets an instance of {@link ILoggerFactory}.
     *
     * @return the {@link ILoggerFactory} instance
     */
    @Override
    ILoggerFactory get();
}
