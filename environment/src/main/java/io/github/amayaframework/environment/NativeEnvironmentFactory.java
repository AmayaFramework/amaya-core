package io.github.amayaframework.environment;

import io.github.amayaframework.options.OptionSet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Implementation of {@link EnvironmentFactory} that creates environment in native filesystem.
 * <br>
 * Uses the name of the environment to create the root directory.
 * <br>
 * The default mount point is the current directory ('.').
 */
public final class NativeEnvironmentFactory implements EnvironmentFactory {

    private static Environment create(String name, Path base, boolean init) throws IOException {
        var root = base.toAbsolutePath().normalize().resolve(name);
        if (init && !Files.isDirectory(root)) {
            Files.createDirectory(root);
        }
        return new NativeEnvironment(root, root.getFileSystem(), name);
    }

    @Override
    public Environment create(String name, OptionSet options) throws IOException {
        if (options == null || options.isEmpty()) {
            return create(name, Path.of("."), true);
        }
        var base = Path.of(options.get(EnvOptions.ROOT));
        return create(name, base, options.asKey(EnvOptions.INIT));
    }

    @Override
    public Environment create(String name) throws IOException {
        return create(name, Path.of("."), true);
    }
}
