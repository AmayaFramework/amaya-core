package io.github.amayaframework.environment;

import java.nio.file.FileSystem;
import java.nio.file.Path;

public interface Environment extends AutoCloseable {

    Path getRoot();

    FileSystem getFileSystem();

    String getName();
}
