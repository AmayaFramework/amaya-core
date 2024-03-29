package io.github.amayaframework.core.environment;

import io.github.amayaframework.core.configuration.Configuration;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

public interface Environment extends AutoCloseable {

    ILoggerFactory getLoggerFactory();

    Logger getLogger();

    Path getWorkingDirectory();

    InputStream openInputStream(Path path);

    InputStream openInputStream(String name);

    OutputStream openOutputStream(Path path);

    OutputStream openOutputStream(String name);

    Configuration getConfiguration();
}
