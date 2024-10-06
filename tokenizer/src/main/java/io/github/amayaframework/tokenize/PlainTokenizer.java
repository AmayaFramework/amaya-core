package io.github.amayaframework.tokenize;

import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * {@link Tokenizer} implementation using {@link StringTokenizer}.
 */
public final class PlainTokenizer implements Tokenizer {

    @Override
    public Iterable<String> tokenize(String target, String delim) {
        return () -> {
            var tokenizer = new StringTokenizer(target, delim);
            return new TokenIterator(tokenizer);
        };
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
