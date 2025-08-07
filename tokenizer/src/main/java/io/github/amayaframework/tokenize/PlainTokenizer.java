package io.github.amayaframework.tokenize;

import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * A {@link Tokenizer} implementation using {@link StringTokenizer}.
 * <p>
 * Performs tokenization based on character-based delimiters.
 * This implementation does not use regular expressions and is generally faster
 * for simple use cases.
 */
public final class PlainTokenizer implements Tokenizer {

    @Override
    public Iterable<String> tokenize(String target, String delim) {
        return () -> new TokenIterator(new StringTokenizer(target, delim));
    }

    private static final class TokenIterator implements Iterator<String> {
        private final StringTokenizer tokenizer;

        private TokenIterator(StringTokenizer tokenizer) {
            this.tokenizer = tokenizer;
        }

        @Override
        public boolean hasNext() {
            return tokenizer.hasMoreTokens();
        }

        @Override
        public String next() {
            return tokenizer.nextToken();
        }
    }
}
