package io.github.amayaframework.core.controllers;

import org.atteo.classindex.IndexAnnotated;

import java.lang.annotation.*;

/**
 * <p>An annotation that is a marker for user controllers by default.</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@IndexAnnotated
public @interface Endpoint {
    String value() default "";
}
