package io.github.amayaframework.core.filters;

public class DoubleFilter implements Filter {
    @Override
    public Object transform(String source) {
        return Double.parseDouble(source);
    }
}
