package io.github.amayaframework.core.inject;

import io.github.amayaframework.core.contexts.HttpRequest;
import org.atteo.classindex.IndexAnnotated;

import java.lang.annotation.*;

/**
 * An annotation that allows you to label another annotation, method, or type
 * with the specified HttpRequest class, which will be used for subsequent injections.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@IndexAnnotated
@Documented
public @interface SpecifyRequest {
    Class<? extends HttpRequest> value();
}
