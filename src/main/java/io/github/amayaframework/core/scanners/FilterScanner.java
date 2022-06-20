package io.github.amayaframework.core.scanners;

import io.github.amayaframework.core.filters.*;
import io.github.amayaframework.core.util.ReflectUtil;

import java.util.Map;

public class FilterScanner implements Scanner<String, Filter> {
    private static final Class<Filter> FILTER_CLASS = Filter.class;
    private static final Filter BIG_INTEGER_FILTER = new BigIntegerFilter();
    private static final Filter BOOLEAN_FILTER = new BooleanFilter();
    private static final Filter DOUBLE_FILTER = new DoubleFilter();
    private static final Filter INTEGER_FILTER = new IntegerFilter();

    @Override
    public Map<String, Filter> find() throws Exception {
        Map<NamedFilter[], Filter> multiple = ReflectUtil.findAnnotatedWithValue(NamedFilters.class, FILTER_CLASS);
        Map<String, Filter> single = ReflectUtil.findAnnotatedWithValue(NamedFilter.class, FILTER_CLASS);
        multiple.forEach((key, value) -> {
            for (NamedFilter filter : key) {
                single.put(filter.value(), value);
            }
        });
        single.put("bigint", BIG_INTEGER_FILTER);
        single.put("bool", BOOLEAN_FILTER);
        single.put("double", DOUBLE_FILTER);
        single.put("int", INTEGER_FILTER);
        return single;
    }
}
