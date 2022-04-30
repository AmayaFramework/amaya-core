package io.github.amayaframework.core.inject;

import io.github.amayaframework.core.util.DuplicateException;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class SourceClass {
    final Class<?> clazz;
    final Map<Class<?>, SourceMethod> methods;

    private SourceClass(Class<?> clazz, Map<Class<?>, SourceMethod> methods) {
        this.clazz = clazz;
        this.methods = methods;
    }

    static SourceClass create(Class<?> clazz) {
        Map<Class<?>, SourceMethod> ret = new HashMap<>();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            Provider provider = method.getAnnotation(Provider.class);
            if (provider == null) {
                continue;
            }
            if (!Modifier.isPublic(method.getModifiers())) {
                throw new IllegalStateException("Provider can't be non-public");
            }
            Class<?> value = provider.value();
            if (ret.containsKey(value)) {
                throw new DuplicateException("The provider's annotation is duplicated");
            }
            SourceMethod toPut = packMethod(method, value);
            ret.put(value, toPut);
        }
        if (ret.isEmpty()) {
            throw new IllegalStateException("The source class does not contain any provider");
        }
        return new SourceClass(clazz, ret);
    }

    private static SourceMethod packMethod(Method method, Class<?> annotation) {
        Class<?>[] parameters = method.getParameterTypes();
        Method[] fields = annotation.getDeclaredMethods();
        if (parameters.length != fields.length) {
            throw new IllegalStateException("The number of annotation fields != the number of method parameters");
        }
        String[] ret = new String[fields.length];
        for (Method field : fields) {
            Position position = field.getAnnotation(Position.class);
            if (position == null) {
                throw new IllegalStateException("The position of the field is not specified");
            }
            int index = position.value();
            if (field.getReturnType() != parameters[index]) {
                throw new IllegalStateException("The field and parameter types do not match");
            }
            ret[index] = field.getName();
        }
        return new SourceMethod(method, ret);
    }

    @Override
    public String toString() {
        return methods.toString();
    }
}

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
