package io.github.amayaframework.core.wrapping;

import org.atteo.classindex.IndexAnnotated;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@IndexAnnotated
public @interface NamedAnnotation {
    String value() default "";
}
