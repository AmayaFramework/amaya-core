package io.github.amayaframework.environment;

import java.nio.file.FileSystem;
import java.nio.file.Path;

/**
 * An interface describing an abstract environment mounted on a specific file system.
 * It can be both virtual and real, or combined.
 */
public interface Environment extends AutoCloseable {
    /**
     * The string key for {@link io.github.amayaframework.options.OptionSet}
     * that allows you to set the mount point of the environment. By default, '.'.
     */
    String ROOT = "root";
    /**
     * The boolean key for {@link io.github.amayaframework.options.OptionSet}
     * that allows you to set the initialization of the environment during creation. By default, 'true'.
     */
    String INIT = "init";

    /**
     * Returns {@link Path} instance containing real or virtual path to environment root.
     *
     * @return the {@link Path} instance
     */
    Path getRoot();

    /**
     * Returns {@link FileSystem} instance in which the environment is mounted.
     *
     * @return the {@link FileSystem} instance
     */
    FileSystem getFileSystem();

    /**
     * Returns environment name. Maybe used for some purposes as name of root dir, etc.
     *
     * @return the environment name
     */
    String getName();
}
