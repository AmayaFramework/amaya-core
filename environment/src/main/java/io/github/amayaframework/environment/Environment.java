package io.github.amayaframework.environment;

import java.io.Closeable;
import java.nio.file.FileSystem;
import java.nio.file.Path;

/**
 * An interface describing an abstract environment mounted on a specific file system.
 * It can be both virtual and real, or combined.
 */
public interface Environment extends Closeable {

    /**
     * Returns {@link Path} instance containing real or virtual path to environment root.
     *
     * @return the {@link Path} instance
     */
    Path root();

    /**
     * Returns {@link FileSystem} instance in which the environment is mounted.
     *
     * @return the {@link FileSystem} instance
     */
    FileSystem fileSystem();

    /**
     * Returns environment name. Maybe used for some purposes as name of root dir, etc.
     *
     * @return the environment name
     */
    String name();

    boolean opened();
}
