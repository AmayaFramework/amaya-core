package io.github.amayaframework.core.wrapping;

public interface Viewable {
    Object view(String name);

    void set(String name, Object value);
}
