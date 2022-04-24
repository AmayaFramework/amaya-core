package io.github.amayaframework.core.util;

import com.github.romanqed.jeflect.lambdas.LambdaClass;
import com.github.romanqed.util.Action;
import com.github.romanqed.util.Pair;
import io.github.amayaframework.core.controllers.Pack;
import io.github.amayaframework.core.methods.HttpMethod;
import io.github.amayaframework.core.wrapping.BasePacker;
import io.github.amayaframework.core.wrapping.InjectPacker;
import io.github.amayaframework.core.wrapping.Packer;
import org.atteo.classindex.ClassIndex;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.Callable;

import static com.github.romanqed.jeflect.ReflectUtil.extractAnnotationValue;
import static com.github.romanqed.jeflect.ReflectUtil.packLambdaMethod;

public final class ReflectUtil {
    private static final Map<Class<?>, Callable<? extends Packer>> DEFAULTS;
    @SuppressWarnings("rawtypes")
    private static final LambdaClass<Action> ACTION = LambdaClass.fromClass(Action.class);

    static {
        try {
            DEFAULTS = packDefaults();
        } catch (Throwable e) {
            throw new IllegalStateException("Can't cache constructors for default packers", e);
        }
    }

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

    private static Map<Class<?>, Callable<? extends Packer>> packDefaults() throws Throwable {
        Map<Class<?>, Callable<? extends Packer>> ret = new HashMap<>();
        ret.put(BasePacker.class, com.github.romanqed.jeflect.ReflectUtil.packConstructor(BasePacker.class));
        ret.put(InjectPacker.class, com.github.romanqed.jeflect.ReflectUtil.packConstructor(InjectPacker.class));
        return Collections.unmodifiableMap(ret);
    }

    public static Packer extractPacker(AnnotatedElement annotated) {
        Pack pack = annotated.getAnnotation(Pack.class);
        if (pack == null) {
            return null;
        }
        Class<? extends Packer> type = pack.value();
        Callable<? extends Packer> ret = DEFAULTS.getOrDefault(type, type::newInstance);
        try {
            return ret.call();
        } catch (Exception e) {
            return null;
        }
    }
}
