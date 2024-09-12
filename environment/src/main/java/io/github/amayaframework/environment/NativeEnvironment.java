package io.github.amayaframework.environment;

import java.nio.file.FileSystem;
import java.nio.file.Path;

final class NativeEnvironment implements Environment {
    private final Path root;
    private final FileSystem fileSystem;
    private final String name;

    NativeEnvironment(Path root, FileSystem fileSystem, String name) {
        this.root = root;
        this.fileSystem = fileSystem;
        this.name = name;
    }

    @Override
    public Path getRoot() {
        return root;
    }

    @Override
    public FileSystem getFileSystem() {
        return fileSystem;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void close() {
        // Do nothing, because we cannot close native filesystem
    }
}
