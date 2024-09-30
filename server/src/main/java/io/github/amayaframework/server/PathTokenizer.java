package io.github.amayaframework.server;

/**
 * An interface describing an abstract http path tokenizer.
 */
public interface PathTokenizer {

    /**
     * Splits given path by '/'.
     *
     * @param path the specified path to be split
     * @return a string array containing path segments
     */
    String[] tokenize(String path);
}
