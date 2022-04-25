package io.github.amayaframework.core.controllers;

import io.github.amayaframework.core.routers.MethodRouter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface UseRouter {
    Class<? extends MethodRouter> value();
}
