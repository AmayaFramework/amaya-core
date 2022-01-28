package io.github.amayaframework.core.methods;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <p>An annotation that is a marker for the http method DELETE. It has an empty string as the default value.</p>
 * <p>Enum reference {@link HttpMethod#DELETE}</p>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Delete {
    String value() default "";
}
