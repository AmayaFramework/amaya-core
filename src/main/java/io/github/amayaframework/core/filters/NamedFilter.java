package io.github.amayaframework.core.filters;

import org.atteo.classindex.IndexAnnotated;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * This annotation is created as a universal marker for any filter
 * that can be found in the future using any annotation scanner.
 * Annotated with {@link IndexAnnotated} for compatibility with org.atteo.classindex
 * As the default value has an empty string.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@IndexAnnotated
public @interface NamedFilter {
    String value() default "";
}
