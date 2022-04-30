package io.github.amayaframework.core.filters;

public class IntegerFilter implements Filter {
    @Override
    public Object transform(String source) {
        return Integer.parseInt(source);
    }
}
