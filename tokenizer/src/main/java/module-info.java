/**
 * A minimalistic and fast string tokenization module.
 * <p>
 * Provides a uniform abstraction (`Tokenizer`) for splitting strings by simple
 * delimiters or regular expressions without allocating intermediate collections.
 * All tokenization are performed on-the-fly using lazy {@link java.util.Iterator}
 * implementations.
 * <p>
 * The module is optimized for performance and minimal memory overhead.
 */
module amayaframework.tokenize {
    // Exports
    exports io.github.amayaframework.tokenize;
}
