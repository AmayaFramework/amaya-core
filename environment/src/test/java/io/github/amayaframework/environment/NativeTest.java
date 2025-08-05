package io.github.amayaframework.environment;

import io.github.amayaframework.options.Options;
import org.junit.jupiter.api.Test;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public final class NativeTest {

    @Test
    public void testNative() throws Exception {
        var factory = new NativeEnvironmentFactory();
        var env = factory.create("test");
        assertEquals(FileSystems.getDefault(), env.fileSystem());
        assertEquals(Path.of("./test").toAbsolutePath().normalize(), env.root());
        assertTrue(Files.isDirectory(env.root()));
        Files.delete(env.root());
        env.close();
    }

    @Test
    public void testNativeWithOptions() throws Exception {
        var options = Options.of(EnvOptions.INIT, false, EnvOptions.ROOT.getKey(), "test-root");
        var factory = new NativeEnvironmentFactory();
        var env = factory.create("test", options);
        assertEquals(FileSystems.getDefault(), env.fileSystem());
        assertEquals(Path.of("./test-root/test").toAbsolutePath().normalize(), env.root());
        assertFalse(Files.isDirectory(env.root()));
        env.close();
    }
}
