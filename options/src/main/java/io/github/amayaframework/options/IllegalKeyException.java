package io.github.amayaframework.options;

import java.util.Set;

/**
 * Thrown to indicate that given key is unacceptable in option set.
 */
public class IllegalKeyException extends IllegalArgumentException {
    private final String key;
    private final Set<String> acceptable;

    /**
     * Constructs an {@link IllegalKeyException} with the specified illegal key and set of acceptable keys.
     *
     * @param key        the specified illegal key
     * @param acceptable the specified acceptable set
     */
    public IllegalKeyException(String key, Set<String> acceptable) {
        super("Illegal key: " + key + ". Acceptable keys: " + acceptable);
        this.key = key;
        this.acceptable = acceptable;
    }

    /**
     * Constructs an {@link IllegalKeyException} with the specified illegal key.
     *
     * @param key the specified illegal key
     */
    public IllegalKeyException(String key) {
        super("Illegal key: " + key);
        this.key = key;
        this.acceptable = null;
    }

    /**
     * Gets the illegal key.
     *
     * @return the illegal key
     */
    public String getKey() {
        return key;
    }

    /**
     * Gets the set containing acceptable keys.
     *
     * @return the set containing acceptable keys
     */
    public Set<String> getAcceptable() {
        return acceptable;
    }
}
