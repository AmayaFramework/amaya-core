package io.github.amayaframework.core.inject;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target(ElementType.METHOD)
public @interface Provider {
    Class<? extends Annotation> value();
}
