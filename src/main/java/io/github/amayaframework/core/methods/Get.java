package io.github.amayaframework.core.methods;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <p>An annotation that is a marker for the http method GET. It has an empty string as the default value.</p>
 * <p>Enum reference {@link HttpMethod#GET}</p>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Get {
    String value() default "";
}
