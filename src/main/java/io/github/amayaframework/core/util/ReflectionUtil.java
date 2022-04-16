package io.github.amayaframework.core.util;

import com.github.romanqed.jutils.util.Action;
import com.github.romanqed.jutils.util.Handler;
import com.github.romanqed.jutils.util.Pair;
import io.github.amayaframework.core.methods.HttpMethod;
import org.atteo.classindex.ClassIndex;

import java.lang.annotation.Annotation;
import java.lang.invoke.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class ReflectionUtil {
    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
    private static final String HANDLE = "handle";
    private static final String ACTION = "execute";
    private static final MethodType HANDLER_TYPE = MethodType.methodType(void.class, Object.class);
    private static final MethodType ACTION_TYPE = MethodType.methodType(Object.class, Object.class);

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

    public static <V, T> Map<V, T> findAnnotatedWithValue
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
    findAnnotatedWithValue(Class<? extends Annotation> annotation, Class<T> castType, Class<V> valueType)
            throws InvocationTargetException, InstantiationException, IllegalAccessException {
        return findAnnotatedWithValue(annotation, castType, valueType, "value");
    }

    public static MethodType extractType(Method method) throws IllegalAccessException {
        Objects.requireNonNull(method);
        method.setAccessible(true);
        MethodHandle handle = LOOKUP.unreflect(method);
        return handle.type();
    }

    public static MethodType extractDynamicType(Method method) throws IllegalAccessException {
        return extractType(method).dropParameterTypes(0, 1);
    }

    @SuppressWarnings("unchecked")
    public static <T> Handler<T> packVoidMethod(Object bind, Method method) throws Throwable {
        MethodHandle handle = LOOKUP.unreflect(method);
        CallSite callSite = LambdaMetafactory.metafactory(
                LOOKUP,
                HANDLE,
                // types to bind
                MethodType.methodType(Handler.class, bind.getClass()),
                // lambda type
                HANDLER_TYPE,
                // method handle to transform
                handle,
                // source method type
                handle.type().dropParameterTypes(0, 1));
        return (Handler<T>) callSite.getTarget().bindTo(bind).invoke();
    }

    @SuppressWarnings("unchecked")
    public static <T, R> Action<T, R> packReturnMethod(Object bind, Method method) throws Throwable {
        Objects.requireNonNull(bind);
        Objects.requireNonNull(method);
        MethodHandle handle = LOOKUP.unreflect(method);
        CallSite callSite = LambdaMetafactory.metafactory(
                LOOKUP,
                ACTION,
                MethodType.methodType(Action.class, bind.getClass()),
                ACTION_TYPE,
                handle,
                handle.type().dropParameterTypes(0, 1)
        );
        return (Action<T, R>) callSite.getTarget().bindTo(bind).invoke();
    }

    public static Action<Object[], Object> packAnyMethod(Object bind, Method method, int argumentNumber)
            throws Throwable {
        method.setAccessible(true);
        MethodHandle handle = LOOKUP.unreflect(method).bindTo(bind).asSpreader(Object[].class, argumentNumber);
        return handle::invoke;
    }
}
