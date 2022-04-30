package io.github.amayaframework.core.filters;

import java.math.BigInteger;

public class BigIntegerFilter implements Filter {
    @Override
    public Object transform(String source) {
        return new BigInteger(source);
    }
}
