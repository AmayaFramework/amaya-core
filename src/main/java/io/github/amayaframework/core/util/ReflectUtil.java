package io.github.amayaframework.core.util;

import com.github.romanqed.jeflect.lambdas.LambdaClass;
import com.github.romanqed.util.Action;
import com.github.romanqed.util.Pair;
import io.github.amayaframework.core.methods.HttpMethod;
import org.atteo.classindex.ClassIndex;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

import static com.github.romanqed.jeflect.ReflectUtil.extractAnnotationValue;
import static com.github.romanqed.jeflect.ReflectUtil.packLambdaMethod;

public final class ReflectUtil {
    @SuppressWarnings("rawtypes")
    private static final LambdaClass<Action> ACTION = LambdaClass.fromClass(Action.class);

    public static List<Pair<HttpMethod, String>> extractMethodRoutes(Method method) throws NoSuchMethodException {
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

    public static <V, T> Map<V, T> findAnnotatedWithValue(Class<? extends Annotation> annotation, Class<T> castType,
                                                          Class<V> valueType, String value) throws
            InstantiationException, IllegalAccessException, NoSuchMethodException {
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

    public static <V, T> Map<V, T> findAnnotatedWithValue(Class<? extends Annotation> annotation,
                                                          Class<T> castType, Class<V> valueType) throws
            InstantiationException, IllegalAccessException, NoSuchMethodException {
        return findAnnotatedWithValue(annotation, castType, valueType, "value");
    }

    @SuppressWarnings("unchecked")
    public static <T, R> Action<T, R> packAction(Method method, Object bind) throws Throwable {
        return packLambdaMethod(ACTION, method, bind);
    }
}
