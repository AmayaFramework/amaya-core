package io.github.amayaframework.core.inject;

import io.github.amayaframework.core.contexts.HttpRequest;
import org.atteo.classindex.IndexAnnotated;

import java.lang.annotation.*;

/**
 * Annotation for specifying HttpRequest implementations that are available for injection.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@IndexAnnotated
@Documented
public @interface SourceRequest {
    Class<? extends HttpRequest> value();
}
