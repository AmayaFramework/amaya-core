package io.github.amayaframework.core.util;

public class DuplicateException extends IllegalArgumentException {
    public DuplicateException() {
        super();
    }

    public DuplicateException(String s) {
        super(s + " is duplicated");
    }
}
