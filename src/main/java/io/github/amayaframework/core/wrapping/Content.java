package io.github.amayaframework.core.wrapping;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class Content {
    public static final String QUERY = "query";
    public static final String PATH = "path";
    public static final String BODY = "body";
    public static final String COOKIE = "cookie";

    private static final Map<Class<Annotation>, Content> children = new ConcurrentHashMap<>();

    static {
        addContent(Query.class, QUERY);
        addContent(Path.class, PATH);
        addContent(Body.class, BODY);
        addContent(Cookie.class, COOKIE);
    }

    private final Class<Annotation> annotationClass;
    private final String filter;

    @SuppressWarnings("unchecked")
    private Content(Class<? extends Annotation> annotationClass, String filter) {
        Objects.requireNonNull(annotationClass);
        if (!annotationClass.isAnnotation()) {
            throw new IllegalArgumentException("The provided class is not an annotation");
        }
        this.annotationClass = (Class<Annotation>) annotationClass;
        this.filter = Objects.requireNonNull(filter);
    }

    public static void addContent(Class<? extends Annotation> annotationClass, String filter) {
        Content toAdd = new Content(annotationClass, filter);
        children.put(toAdd.annotationClass, toAdd);
    }

    public static Content fromAnnotation(Annotation annotation) {
        if (annotation == null) {
            return null;
        }
        return children.get(annotation.annotationType());
    }

    public String getFilter() {
        return filter;
    }
}
