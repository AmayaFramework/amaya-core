package io.github.amayaframework.core.configuration;

import com.github.romanqed.jfunc.Function1;

@FunctionalInterface
public interface KeyProvider extends Function1<Key<?>, Object> {

    @Override
    Object invoke(Key<?> key) throws Throwable;
}
