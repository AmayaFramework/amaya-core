package io.github.amayaframework.core.configurators;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation that specializes access policy
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Access {
    AccessPolicy value();
}
