package io.github.amayaframework.core.config;

public interface Configurable {
    <T> void setField(Field<T> field, T value);

    <T> T getField(Field<T> field);
}
