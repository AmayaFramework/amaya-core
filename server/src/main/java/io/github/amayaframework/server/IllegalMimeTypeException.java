package io.github.amayaframework.server;

/**
 * Thrown to indicate that given mime type violates rfc2045 rules.
 */
public class IllegalMimeTypeException extends IllegalArgumentException {
    /**
     * Illegal mime type.
     */
    private final String type;

    /**
     * Constructs an {@link IllegalMimeTypeException} instance with the specified illegal mime type.
     *
     * @param type the string containing illegal mime type definition
     */
    public IllegalMimeTypeException(String type) {
        super("Illegal mime type: " + type);
        this.type = type;
    }

    /**
     * Gets illegal mime type definition violates rfc2045.
     *
     * @return the string containing illegal mime type definition
     */
    public String getMimeType() {
        return type;
    }
}
