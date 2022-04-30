package io.github.amayaframework.core.actions;

import java.lang.annotation.*;

/**
 * An annotation indicating that the action being created by default
 * requests a config that is accepted using the constructor parameter.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface WithConfig {
}
