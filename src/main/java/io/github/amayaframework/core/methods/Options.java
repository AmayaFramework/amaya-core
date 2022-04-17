package io.github.amayaframework.core.methods;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>An annotation that is a marker for the http method OPTIONS. It has an empty string as the default value.</p>
 * <p>Enum reference {@link HttpMethod#OPTIONS}</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Options {
    String value() default "";
}
