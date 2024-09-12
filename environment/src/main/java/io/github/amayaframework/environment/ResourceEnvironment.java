package io.github.amayaframework.environment;

import java.nio.file.FileSystem;
import java.nio.file.Path;

class ResourceEnvironment implements Environment {

    @Override
    public Path getRoot() {
        return null;
    }

    @Override
    public FileSystem getFileSystem() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void close() throws Exception {

    }
}
