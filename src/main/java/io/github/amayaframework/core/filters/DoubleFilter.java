package io.github.amayaframework.core.filters;

public class DoubleFilter implements StringFilter {
    @Override
    public Object transform(String source) {
        return Double.parseDouble(source);
    }
}
