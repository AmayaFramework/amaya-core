package io.github.amayaframework.core.util;

import com.github.romanqed.jutils.structs.Pair;
import io.github.amayaframework.core.methods.HttpMethod;
import org.atteo.classindex.ClassIndex;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class ReflectUtils {
    public static <T> T extractAnnotationValue(Annotation annotation, String value, Class<T> type)
            throws InvocationTargetException, IllegalAccessException {
        Objects.requireNonNull(annotation);
        Objects.requireNonNull(value);
        Objects.requireNonNull(type);
        Class<? extends Annotation> annotationType = annotation.annotationType();
        Method found = Arrays.
                stream(annotationType.getDeclaredMethods()).
                filter(method -> method.getName().equals(value)).
                findFirst().
                orElse(null);
        if (found == null) {
            throw new NoSuchElementException();
        }
        return type.cast(found.invoke(annotation));
    }

    public static <T> T extractAnnotationValue(Annotation annotation, Class<T> type)
            throws InvocationTargetException, IllegalAccessException {
        return extractAnnotationValue(annotation, "value", type);
    }

    public static List<Pair<HttpMethod, String>> extractMethodRoutes(Method method)
            throws InvocationTargetException, IllegalAccessException {
        Objects.requireNonNull(method);
        List<Pair<HttpMethod, String>> ret = new LinkedList<>();
        for (Annotation annotation : method.getDeclaredAnnotations()) {
            HttpMethod httpMethod = HttpMethod.fromAnnotation(annotation);
            if (httpMethod != null) {
                ret.add(new Pair<>(httpMethod, extractAnnotationValue(annotation, String.class)));
            }
        }
        return ret;
    }

    public static <V, T> Map<V, T> foundAnnotatedWithValue
            (Class<? extends Annotation> annotation, Class<T> castType, Class<V> valueType, String value)
            throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Iterable<Class<?>> classes = ClassIndex.getAnnotated(annotation);
        Map<V, T> ret = new HashMap<>();
        for (Class<?> clazz : classes) {
            if (!castType.isAssignableFrom(clazz)) {
                continue;
            }
            V key = extractAnnotationValue(clazz.getAnnotation(annotation), value, valueType);
            T instance = castType.cast(clazz.newInstance());
            ret.put(key, instance);
        }
        return ret;
    }

    public static <V, T> Map<V, T>
    foundAnnotatedWithValue(Class<? extends Annotation> annotation, Class<T> castType, Class<V> valueType)
            throws InvocationTargetException, InstantiationException, IllegalAccessException {
        return foundAnnotatedWithValue(annotation, castType, valueType, "value");
    }
}
