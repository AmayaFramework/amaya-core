package io.github.amayaframework.core.filters;

import java.util.function.Function;

public class HeaderFilter implements ContentFilter {
    @Override
    @SuppressWarnings("unchecked")
    public Object transform(Object source, String name) {
        try {
            return ((Function<String, String>) source).apply(name);
        } catch (Exception e) {
            return null;
        }
    }
}
