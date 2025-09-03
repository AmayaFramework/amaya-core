package io.github.amayaframework.server;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public final class SplitPathTokenizerTest {
    private final PathTokenizer tokenizer = new SplitPathTokenizer();

    @Test
    public void testRootPath() {
        assertEquals(List.of("/"), tokenizer.tokenize("/"));
    }

    @Test
    public void testSingleSegment() {
        assertEquals(List.of("users"), tokenizer.tokenize("/users"));
    }

    @Test
    public void testMultipleSegments() {
        assertEquals(List.of("users", "123", "profile"), tokenizer.tokenize("/users/123/profile"));
    }

    @Test
    public void testTrailingSlash() {
        assertEquals(List.of("users", "123"), tokenizer.tokenize("/users/123/"));
    }

    @Test
    public void testConsecutiveSlashes() {
        assertEquals(List.of("a", "b", "c"), tokenizer.tokenize("/a//b///c/"));
    }
}
