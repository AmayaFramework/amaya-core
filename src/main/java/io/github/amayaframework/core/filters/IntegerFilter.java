package io.github.amayaframework.core.filters;

public class IntegerFilter implements StringFilter {
    @Override
    public Object transform(String source) {
        return Integer.parseInt(source);
    }
}
