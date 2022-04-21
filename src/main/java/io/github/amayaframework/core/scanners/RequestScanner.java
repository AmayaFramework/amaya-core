package io.github.amayaframework.core.scanners;

import io.github.amayaframework.core.contexts.CommonHttpRequest;
import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.contexts.Request;
import org.atteo.classindex.ClassIndex;

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class RequestScanner implements Scanner<Class<? extends HttpRequest>> {
    private static final Class<CommonHttpRequest> HTTP_REQUEST_CLASS = CommonHttpRequest.class;

    @Override
    @SuppressWarnings("unchecked")
    public Class<HttpRequest> find() {
        Iterable<Class<?>> found = ClassIndex.getAnnotated(Request.class);
        Set<Class<?>> classes = new HashSet<>();
        for (Class<?> clazz : found) {
            if (!HTTP_REQUEST_CLASS.isAssignableFrom(clazz)) {
                throw new IllegalStateException("A class inherited from CommonHttpRequest was expected");
            }
            int modifiers = clazz.getModifiers();
            if (Modifier.isInterface(modifiers) || Modifier.isAbstract(modifiers)) {
                throw new IllegalStateException("Class expected, interface or abstract class found");
            }
            classes.add(clazz);
        }
        List<Class<?>> toRemove = new LinkedList<>();
        for (Class<?> clazz : classes) {
            toRemove.add(clazz.getSuperclass());
        }
        toRemove.forEach(classes::remove);
        if (classes.size() > 1) {
            throw new IllegalStateException("Several different HttpRequests were found, unrelated to each other");
        }
        return (Class<HttpRequest>) classes.stream().findFirst().orElse(null);
    }
}
