package io.github.amayaframework.core.methods;

import java.lang.annotation.*;

/**
 * <p>An annotation that is a marker for the http method GET. It has an empty string as the default value.</p>
 * <p>Enum reference {@link HttpMethod#GET}</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Get {
    String value() default "";
}
