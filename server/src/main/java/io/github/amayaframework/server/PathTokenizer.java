package io.github.amayaframework.server;

import java.util.List;

/**
 * An interface describing an abstract http path tokenizer.
 */
public interface PathTokenizer {

    /**
     * Splits given path by '/'.
     *
     * @param path the specified path to be split
     * @return a {@link List} containing path segments
     */
    List<String> tokenize(String path);
}
