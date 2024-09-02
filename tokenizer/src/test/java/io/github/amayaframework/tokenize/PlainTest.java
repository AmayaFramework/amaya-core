package io.github.amayaframework.tokenize;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public final class PlainTest {

    @Test
    public void testWhitespaces() {
        var tok = Tokenizers.plain();
        var tokens = tok.tokenize(" a         b    c          d      ", " ");
        assertIterableEquals(List.of("a", "b", "c", "d"), tokens);
    }

    @Test
    public void testCharacters() {
        var tok = Tokenizers.plain();
        var tokens = tok.tokenize("a::b:c:::d:::", ":");
        assertIterableEquals(List.of("a", "b", "c", "d"), tokens);
    }

    @Test
    public void testManyDelimiters() {
        var tok = Tokenizers.plain();
        var tokens = tok.tokenize("jjja| |b.. c000d'", "j| .0'");
        assertIterableEquals(List.of("a", "b", "c", "d"), tokens);
    }
}
