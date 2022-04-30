package io.github.amayaframework.core.filters;

public class BooleanFilter implements Filter {
    @Override
    public Object transform(String source) {
        return Boolean.parseBoolean(source);
    }
}
