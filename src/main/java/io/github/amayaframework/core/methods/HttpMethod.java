package io.github.amayaframework.core.methods;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Enum describing the list of http methods supported by the framework.
 */
public enum HttpMethod {
    GET(Get.class, false),
    HEAD(Head.class, false),
    POST(Post.class),
    PUT(Put.class),
    PATCH(Patch.class),
    DELETE(Delete.class),
    OPTIONS(Options.class, false);

    private static final Map<Class<Annotation>, HttpMethod> children = toMap();
    private static final Map<String, HttpMethod> stringChildren = toStringMap();
    private final Class<Annotation> annotationClass;
    private final boolean hasBody;

    @SuppressWarnings("unchecked")
    HttpMethod(Class<?> annotationClass, boolean hasBody) {
        Objects.requireNonNull(annotationClass);
        if (!annotationClass.isAnnotation()) {
            throw new IllegalArgumentException("The provided class is not an annotation");
        }
        this.annotationClass = (Class<Annotation>) annotationClass;
        this.hasBody = hasBody;
    }

    HttpMethod(Class<?> annotationClass) {
        this(annotationClass, true);
    }

    private static Map<Class<Annotation>, HttpMethod> toMap() {
        Map<Class<Annotation>, HttpMethod> ret = new HashMap<>();
        for (HttpMethod method : HttpMethod.values()) {
            ret.put(method.annotationClass, method);
        }
        return Collections.unmodifiableMap(ret);
    }

    private static Map<String, HttpMethod> toStringMap() {
        Map<String, HttpMethod> ret = new HashMap<>();
        for (HttpMethod method : HttpMethod.values()) {
            ret.put(method.name(), method);
        }
        return Collections.unmodifiableMap(ret);
    }

    public static HttpMethod fromAnnotation(Annotation annotation) {
        if (annotation == null) {
            return null;
        }
        return children.get(annotation.annotationType());
    }

    public static HttpMethod fromName(String name) {
        Objects.requireNonNull(name);
        return stringChildren.get(name);
    }

    public Class<Annotation> getAnnotationClass() {
        return annotationClass;
    }

    public boolean isHasBody() {
        return hasBody;
    }
}
