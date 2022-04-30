package io.github.amayaframework.core.inject;

import java.lang.annotation.*;

/**
 * An annotation that is a marker for injecting the query parameter value into the marked argument.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface Query {
    @Position
    String value() default "";
}
