package io.github.amayaframework.core.routers;

public class DuplicateParameterException extends DuplicateException {
    public DuplicateParameterException() {
        super();
    }

    public DuplicateParameterException(Object parameter) {
        super("Parameter " + parameter.toString());
    }
}
