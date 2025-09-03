package io.github.amayaframework.server;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Default {@link PathTokenizer} implementation based on {@link StringTokenizer}.
 * <p>
 * Splits a request path into segments using {@code '/'} as a delimiter.
 * Consecutive slashes are treated as a single delimiter, and empty segments
 * are skipped.
 * <p>
 * Special case: if the path is exactly {@code "/"}, this implementation
 * returns a constant singleton list containing {@code "/"}.
 * For all other paths, each non-empty segment is returned as-is without
 * additional decoding or normalization.
 * <p>
 * This implementation is optimized for high-load scenarios:
 * it avoids regular expressions and minimizes allocations by using
 * {@link StringTokenizer}.
 */
public final class SplitPathTokenizer implements PathTokenizer {
    private static final List<String> SINGLE = List.of("/");

    @Override
    public List<String> tokenize(String path) {
        if (path.equals("/")) {
            return SINGLE;
        }
        var ret = new ArrayList<String>();
        var tokenizer = new StringTokenizer(path, "/");
        while (tokenizer.hasMoreTokens()) {
            ret.add(tokenizer.nextToken());
        }
        return ret;
    }
}
