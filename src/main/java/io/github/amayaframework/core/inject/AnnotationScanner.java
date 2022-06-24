package io.github.amayaframework.core.inject;

import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.scanners.Scanner;
import org.atteo.classindex.ClassIndex;

import java.util.HashMap;
import java.util.Map;

final class AnnotationScanner implements Scanner<Class<?>, Class<? extends HttpRequest>> {
    @Override
    public Map<Class<?>, Class<? extends HttpRequest>> find() {
        Iterable<Class<?>> found = ClassIndex.getAnnotated(SpecifyRequest.class);
        Map<Class<?>, Class<? extends HttpRequest>> ret = new HashMap<>();
        for (Class<?> clazz : found) {
            ret.put(clazz, clazz.getAnnotation(SpecifyRequest.class).value());
        }
        return ret;
    }
}
