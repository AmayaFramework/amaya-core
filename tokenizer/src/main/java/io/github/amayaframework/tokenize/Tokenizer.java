package io.github.amayaframework.tokenize;

/**
 * An abstraction for a string tokenizer.
 * <p>
 * Implementations of this interface split an input string using
 * the specified delimiters and return the result as a lazily evaluated {@link Iterable}.
 * <p>
 * A recommended implementation strategy is to avoid creating intermediate collections
 * and instead return an {@link java.util.Iterator} that performs splitting on-the-fly.
 */
public interface Tokenizer {

    /**
     * Splits the given string using the specified delimiter.
     *
     * @param target the string to tokenize
     * @param delim  the delimiter(s) to use for splitting
     * @return an {@link Iterable} containing the tokens, lazily produced
     */
    Iterable<String> tokenize(String target, String delim);
}
