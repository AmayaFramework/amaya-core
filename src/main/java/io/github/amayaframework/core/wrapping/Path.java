package io.github.amayaframework.core.wrapping;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * An annotation that is a marker for injecting the path parameter value into the marked argument.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Path {
    String value() default "";
}
