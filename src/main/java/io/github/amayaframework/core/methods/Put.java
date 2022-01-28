package io.github.amayaframework.core.methods;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <p>An annotation that is a marker for the http method PUT. It has an empty string as the default value.</p>
 * <p>Enum reference {@link HttpMethod#PUT}</p>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Put {
    String value() default "";
}
