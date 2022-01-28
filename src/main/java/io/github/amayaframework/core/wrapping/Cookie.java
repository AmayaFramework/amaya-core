package io.github.amayaframework.core.wrapping;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * An annotation that is a marker for injecting the cookie value into the marked argument.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Cookie {
    String value() default "";
}
