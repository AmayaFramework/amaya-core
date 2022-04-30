package io.github.amayaframework.core.inject;

import java.lang.annotation.*;

/**
 * An annotation intended to indicate that a class method can be used
 * as a data source when injecting values into method arguments.
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target(ElementType.METHOD)
@Documented
public @interface Provider {
    Class<? extends Annotation> value();
}
