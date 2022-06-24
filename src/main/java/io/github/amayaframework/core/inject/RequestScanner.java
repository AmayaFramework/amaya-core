package io.github.amayaframework.core.inject;

import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.scanners.Scanner;
import org.atteo.classindex.ClassIndex;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static io.github.amayaframework.core.inject.Util.REQUEST;

final class RequestScanner implements Scanner<Class<?>, SourceClass> {

    @Override
    public Map<Class<?>, SourceClass> find() {
        Iterable<Class<?>> found = ClassIndex.getAnnotated(SourceRequest.class);
        Map<Class<?>, SourceClass> ret = new HashMap<>();
        ret.put(HttpRequest.class, SourceClass.create(HttpRequest.class));
        for (Class<?> clazz : found) {
            if (!REQUEST.isAssignableFrom(clazz)) {
                throw new IllegalStateException("The found class is not an HttpRequest implementation");
            }
            SourceRequest annotation = clazz.getAnnotation(SourceRequest.class);
            ret.put(annotation.value(), SourceClass.create(clazz));
        }
        return Collections.unmodifiableMap(ret);
    }
}
