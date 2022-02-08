package io.github.amayaframework.core.scanners;

import io.github.amayaframework.core.util.InvalidFormatException;
import io.github.amayaframework.core.wrapping.NamedAnnotation;
import org.atteo.classindex.ClassIndex;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

public class AnnotationScanner implements Scanner<Map<String, Class<? extends Annotation>>> {
    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Class<? extends Annotation>> find() {
        Iterable<Class<?>> classes = ClassIndex.getAnnotated(NamedAnnotation.class);
        Map<String, Class<? extends Annotation>> ret = new HashMap<>();
        for (Class<?> clazz : classes) {
            if (!clazz.isAnnotation()) {
                throw new InvalidFormatException("An annotated entity is not an annotation");
            }
            NamedAnnotation annotation = clazz.getAnnotation(NamedAnnotation.class);
            ret.put(annotation.value(), (Class<? extends Annotation>) clazz);
        }
        return ret;
    }
}
