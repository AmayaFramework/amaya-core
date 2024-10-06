package io.github.amayaframework.environment;

import io.github.amayaframework.options.OptionSet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

/**
 * Implementation of {@link EnvironmentFactory} that creates environment in native filesystem.
 * <br>
 * Uses the name of the environment to create the root directory.
 * <br>
 * The default mount point is the current directory ('.').
 */
public final class NativeEnvironmentFactory implements EnvironmentFactory {

    private static void initRoot(Path root) throws IOException {
        if (Files.isDirectory(root, LinkOption.NOFOLLOW_LINKS)) {
            return;
        }
        Files.createDirectory(root);
    }

    private static Environment create(String name, Path base, boolean init) throws IOException {
        var root = base.toAbsolutePath().normalize().resolve(name);
        if (init) {
            initRoot(root);
        }
        return new NativeEnvironment(root, root.getFileSystem(), name);
    }

    @Override
    public Environment create(String name, OptionSet options) throws IOException {
        if (options == null || options.isEmpty()) {
            return create(name, Path.of("."), true);
        }
        var base = Path.of(options.<String>get(Environment.ROOT));
        return create(name, base, options.asKey(Environment.INIT));
    }

    @Override
    public Environment create(String name) throws IOException {
        return create(name, Path.of("."), true);
    }
}
