package io.github.amayaframework.core.inject;

import java.lang.annotation.*;

/**
 * An annotation that is a marker for injecting the http header value into the marked argument.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface Header {
    @Position
    String value() default "";
}
