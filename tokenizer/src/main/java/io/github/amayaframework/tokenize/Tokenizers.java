package io.github.amayaframework.tokenize;

/**
 * A utility class providing singleton instances of commonly used {@link Tokenizer} implementations.
 * <p>
 * Also includes static helper methods for quick tokenization.
 */
public final class Tokenizers {
    private Tokenizers() {
    }

    /**
     * Singleton instance of {@link PlainTokenizer}.
     */
    public static final Tokenizer PLAIN_TOKENIZER = new PlainTokenizer();

    /**
     * Singleton instance of {@link RegexTokenizer}.
     */
    public static final Tokenizer REGEX_TOKENIZER = new RegexTokenizer();

    /**
     * Returns the singleton instance of {@link PlainTokenizer}.
     *
     * @return a {@link Tokenizer} for simple character-based tokenization
     */
    public static Tokenizer plain() {
        return PLAIN_TOKENIZER;
    }

    /**
     * Returns the singleton instance of {@link RegexTokenizer}.
     *
     * @return a {@link Tokenizer} using regular expressions
     */
    public static Tokenizer regex() {
        return REGEX_TOKENIZER;
    }

    /**
     * Splits the given string using the {@link PlainTokenizer}.
     *
     * @param target the string to tokenize
     * @param delim  the delimiter characters
     * @return an {@link Iterable} of tokens
     */
    public static Iterable<String> split(String target, String delim) {
        return PLAIN_TOKENIZER.tokenize(target, delim);
    }

    /**
     * Splits the given string using the {@link RegexTokenizer}.
     *
     * @param target the string to tokenize
     * @param delim  the regular expression used as delimiter
     * @return an {@link Iterable} of tokens
     */
    public static Iterable<String> splitByRegex(String target, String delim) {
        return REGEX_TOKENIZER.tokenize(target, delim);
    }
}
