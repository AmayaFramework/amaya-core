package io.github.amayaframework.core.inject;

import java.lang.reflect.Method;
import java.util.Arrays;

class SourceMethod {
    final Method method;
    final String[] parameters;

    SourceMethod(Method method, String[] parameters) {
        this.method = method;
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "SourceMethod{" +
                "method=" + method +
                ", parameters=" + Arrays.toString(parameters) +
                '}';
    }
}
