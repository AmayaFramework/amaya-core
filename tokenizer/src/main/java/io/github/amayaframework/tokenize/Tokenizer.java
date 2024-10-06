package io.github.amayaframework.tokenize;

/**
 * An interface describing an abstract tokenizer.
 * Splits a string by delimiters and returns the result as an abstract {@link Iterable} instance.
 * <br>
 * A good way to implement it would be not to create any collection storing tokens,
 * but to implement an {@link java.util.Iterator} that performs splitting on the fly.
 */
public interface Tokenizer {

    /**
     * Splits string by given delimiter.
     *
     * @param target the specified string to be split
     * @param delim  the specified string containing delimiters
     * @return the {@link Iterable} instance, containing result of splitting
     */
    Iterable<String> tokenize(String target, String delim);
}
