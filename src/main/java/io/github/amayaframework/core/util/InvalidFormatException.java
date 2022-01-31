package io.github.amayaframework.core.util;

public class InvalidFormatException extends IllegalArgumentException {
    public InvalidFormatException() {
        super();
    }

    public InvalidFormatException(String s) {
        super(s);
    }

    public InvalidFormatException(String s, Exception cause) {
        super(s, cause);
    }
}
