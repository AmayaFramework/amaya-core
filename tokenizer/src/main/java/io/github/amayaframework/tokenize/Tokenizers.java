package io.github.amayaframework.tokenize;

/**
 * A class containing singleton instances of the main tokenizers.
 */
public final class Tokenizers {
    /**
     * Singleton instance of {@link PlainTokenizer}.
     */
    public static final Tokenizer PLAIN_TOKENIZER = new PlainTokenizer();

    /**
     * Singleton instance of {@link RegexTokenizer}.
     */
    public static final Tokenizer REGEX_TOKENIZER = new RegexTokenizer();

    private Tokenizers() {
    }

    /**
     * Returns singleton instance of {@link PlainTokenizer}.
     *
     * @return {@link Tokenizer} instance
     */
    public static Tokenizer plain() {
        return PLAIN_TOKENIZER;
    }

    /**
     * Returns singleton instance of {@link RegexTokenizer}.
     *
     * @return {@link Tokenizer} instance
     */
    public static Tokenizer regex() {
        return REGEX_TOKENIZER;
    }

    /**
     * Splits given string by {@link PlainTokenizer}.
     *
     * @param target the specified string to be split
     * @param delim  the specified delimiter
     * @return {@link Iterable} instance containing tokens
     */
    public static Iterable<String> split(String target, String delim) {
        return PLAIN_TOKENIZER.tokenize(target, delim);
    }

    /**
     * Splits given string by {@link RegexTokenizer}.
     *
     * @param target the specified string to be split
     * @param delim  the specified regex
     * @return {@link Iterable} instance containing tokens
     */
    public static Iterable<String> splitByRegex(String target, String delim) {
        return REGEX_TOKENIZER.tokenize(target, delim);
    }
}
