package io.github.amayaframework.core.configuration;

import io.github.amayaframework.core.Mappable;

import java.lang.reflect.Type;

public interface ConfigurationScheme extends Mappable<String, Type> {

    void add(Key key);

    Type add(String name, Type type);

    Type get(String name);

    boolean remove(Key key);

    Type remove(String name);
}
