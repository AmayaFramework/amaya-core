package io.github.amayaframework.core.filters;

import org.atteo.classindex.IndexAnnotated;

import java.lang.annotation.*;


/**
 * This annotation is created as a universal marker for any filter
 * that can be found in the future using any annotation scanner.
 * Annotated with {@link IndexAnnotated} for compatibility with org.atteo.classindex
 * As the default value has an empty string.
 */
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(NamedFilters.class)
@Target(ElementType.TYPE)
@IndexAnnotated
public @interface NamedFilter {
    String value() default "";
}
