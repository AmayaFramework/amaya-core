package io.github.amayaframework.core;

import org.slf4j.ILoggerFactory;

import java.util.function.Supplier;

public interface LoggerFactorySupplier extends Supplier<ILoggerFactory> {

    @Override
    ILoggerFactory get();
}
