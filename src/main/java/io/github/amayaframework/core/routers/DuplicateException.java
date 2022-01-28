package io.github.amayaframework.core.routers;

public class DuplicateException extends IllegalArgumentException {
    public DuplicateException() {
        super();
    }

    public DuplicateException(String s) {
        super(s + " is duplicated");
    }
}
