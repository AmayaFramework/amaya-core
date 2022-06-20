package io.github.amayaframework.core.util;

import com.github.romanqed.jeflect.meta.LambdaType;
import com.github.romanqed.util.Action;
import io.github.amayaframework.core.controllers.BasePacker;
import io.github.amayaframework.core.controllers.Packer;
import io.github.amayaframework.core.controllers.UsePacker;
import io.github.amayaframework.core.inject.InjectPacker;
import io.github.amayaframework.core.routers.BaseRouter;
import io.github.amayaframework.core.routers.MethodRouter;
import io.github.amayaframework.core.routers.RegexpRouter;
import org.atteo.classindex.ClassIndex;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import static com.github.romanqed.jeflect.ReflectUtil.extractAnnotationValue;
import static com.github.romanqed.jeflect.ReflectUtil.packLambdaMethod;

public final class ReflectUtil {
    private static final String DEFAULT_VALUE_NAME = "value";
    private static final Map<Class<?>, Callable<? extends Packer>> DEFAULT_PACKERS = getDefaultPackers();
    private static final Map<Class<?>, Callable<? extends MethodRouter>> DEFAULT_ROUTERS = getDefaultRouters();
    @SuppressWarnings("rawtypes")
    private static final LambdaType<Action> ACTION = LambdaType.fromClass(Action.class);

    private static Map<Class<?>, Callable<? extends Packer>> getDefaultPackers() {
        Map<Class<?>, Callable<? extends Packer>> ret = new HashMap<>();
        ret.put(BasePacker.class, BasePacker::new);
        ret.put(InjectPacker.class, InjectPacker::new);
        return Collections.unmodifiableMap(ret);
    }

    private static Map<Class<?>, Callable<? extends MethodRouter>> getDefaultRouters() {
        Map<Class<?>, Callable<? extends MethodRouter>> ret = new HashMap<>();
        ret.put(BaseRouter.class, BaseRouter::new);
        ret.put(RegexpRouter.class, RegexpRouter::new);
        return Collections.unmodifiableMap(ret);
    }

    public static <V, T> Map<V, T> findAnnotatedWithValue(Class<? extends Annotation> annotation, Class<T> castType,
                                                          String value) throws
            InstantiationException, IllegalAccessException, NoSuchMethodException {
        Iterable<Class<?>> classes = ClassIndex.getAnnotated(annotation);
        Map<V, T> ret = new HashMap<>();
        for (Class<?> clazz : classes) {
            if (!castType.isAssignableFrom(clazz)) {
                continue;
            }
            V key = extractAnnotationValue(clazz.getAnnotation(annotation), value);
            T instance = castType.cast(clazz.newInstance());
            ret.put(key, instance);
        }
        return ret;
    }

    public static <V, T> Map<V, T> findAnnotatedWithValue(Class<? extends Annotation> annotation,
                                                          Class<T> castType) throws
            InstantiationException, IllegalAccessException, NoSuchMethodException {
        return findAnnotatedWithValue(annotation, castType, DEFAULT_VALUE_NAME);
    }

    @SuppressWarnings("unchecked")
    public static <T, R> Action<T, R> packAction(Method method, Object bind) throws Throwable {
        return packLambdaMethod(ACTION, method, bind);
    }

    public static Packer extractPacker(AnnotatedElement annotated) {
        UsePacker packer = annotated.getAnnotation(UsePacker.class);
        if (packer == null) {
            return null;
        }
        Class<? extends Packer> type = packer.value();
        Callable<? extends Packer> ret = DEFAULT_PACKERS.getOrDefault(type, type::newInstance);
        try {
            return ret.call();
        } catch (Exception e) {
            return null;
        }
    }

    public static Callable<? extends MethodRouter> findMethodRouter(Class<? extends MethodRouter> clazz) {
        return DEFAULT_ROUTERS.getOrDefault(clazz, clazz::newInstance);
    }
}
