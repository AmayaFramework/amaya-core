package io.github.amayaframework.core.configuration;

import io.github.amayaframework.core.Mappable;

public interface Configuration extends Mappable<Key, Object> {

    <T> T get(Key key);

    void set(Key key, Object value);

    boolean contains(Key key);

    Object remove(Key key);
}
