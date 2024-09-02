package io.github.amayaframework.tokenize;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public final class RegexTest {

    @Test
    public void testWhitespaces() {
        var tok = Tokenizers.regex();
        var tokens = tok.tokenize("a     b    c        d", "\\s+");
        assertIterableEquals(List.of("a", "b", "c", "d"), tokens);
    }

    @Test
    public void testRegex() {
        var tok = Tokenizers.regex();
        var tokens = tok.tokenize("a=-=b=c=-=d", "=-=");
        assertIterableEquals(List.of("a", "b=c", "d"), tokens);
    }
}
