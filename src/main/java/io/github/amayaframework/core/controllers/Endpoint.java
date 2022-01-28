package io.github.amayaframework.core.controllers;

import org.atteo.classindex.IndexAnnotated;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <p>An annotation that is a marker for user controllers by default.</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@IndexAnnotated
public @interface Endpoint {
    String value() default "";
}
