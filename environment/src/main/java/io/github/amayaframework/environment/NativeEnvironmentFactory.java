package io.github.amayaframework.environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

public class NativeEnvironmentFactory implements EnvironmentFactory {

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
    public Environment create(String name, EnvironmentOption... options) throws IOException {
//        var base = Path.of(".");
//        if (options.length == 0) {
//            return create(name, base, true);
//        }
//        var init = true;
//        for (var option : options) {
//            if (option.is(StandardEnvironmentOption.ROOT_LOCATION)) {
//                base = option.getValue();
//                continue;
//            }
//            if (option.is(StandardEnvironmentOption.INIT_ROOT)) {
//                init = option.getValue();
//            }
//        }
//        return create(name, base, init);
        return null;
    }

    @Override
    public Environment create(String name) throws IOException {
        return create(name, Path.of("."), true);
    }
}
