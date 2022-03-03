package io.github.amayaframework.core.configurators;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation that specializes insert policy
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Insert {
    InsertPolicy value();
}
