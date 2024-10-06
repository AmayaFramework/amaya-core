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
        assertEquals(FileSystems.getDefault(), env.getFileSystem());
        assertEquals(Path.of("./test").toAbsolutePath().normalize(), env.getRoot());
        assertTrue(Files.isDirectory(env.getRoot()));
        Files.delete(env.getRoot());
        env.close();
    }

    @Test
    public void testNativeWithOptions() throws Exception {
        var options = Options.of(Environment.INIT, false, Environment.ROOT, "test-root");
        var factory = new NativeEnvironmentFactory();
        var env = factory.create("test", options);
        assertEquals(FileSystems.getDefault(), env.getFileSystem());
        assertEquals(Path.of("./test-root/test").toAbsolutePath().normalize(), env.getRoot());
        assertFalse(Files.isDirectory(env.getRoot()));
        env.close();
    }
}
