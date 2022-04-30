package io.github.amayaframework.core.routes;

import io.github.amayaframework.core.util.DuplicateException;

public class DuplicateParameterException extends DuplicateException {
    public DuplicateParameterException() {
        super();
    }

    public DuplicateParameterException(Object parameter) {
        super("Parameter " + parameter.toString());
    }
}
