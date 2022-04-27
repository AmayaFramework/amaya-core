package io.github.amayaframework.core.wrapping;

import io.github.amayaframework.core.scanners.AnnotationScanner;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Content {
    public static final String QUERY = "query";
    public static final String PATH = "path";
    public static final String BODY = "body";
    public static final String COOKIE = "cookie";
    public static final String HEADER = "header";

    private static final Map<Class<? extends Annotation>, String> children = getContentMap();

    private static Map<Class<? extends Annotation>, String> getContentMap() {
        Map<Class<? extends Annotation>, String> ret = new HashMap<>();
        ret.put(Query.class, QUERY);
        ret.put(Path.class, PATH);
        ret.put(Header.class, HEADER);
        ret.put(Body.class, BODY);
        ret.put(HttpCookie.class, COOKIE);
        AnnotationScanner scanner = new AnnotationScanner();
        Map<String, Class<? extends Annotation>> found = scanner.safetyFind();
        found.forEach((key, value) -> ret.put(value, key));
        return Collections.unmodifiableMap(ret);
    }

    public static String fromAnnotation(Annotation annotation) {
        if (annotation == null) {
            return null;
        }
        return children.get(annotation.annotationType());
    }
}
