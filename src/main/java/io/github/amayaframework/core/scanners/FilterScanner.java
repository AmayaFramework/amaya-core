package io.github.amayaframework.core.scanners;

import io.github.amayaframework.core.config.ConfigProvider;
import io.github.amayaframework.core.util.ReflectionUtil;
import io.github.amayaframework.filters.Filter;
import io.github.amayaframework.filters.NamedFilter;
import io.github.amayaframework.filters.NamedFilters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;

public class FilterScanner<T extends Filter> implements Scanner<Map<String, T>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilterScanner.class);
    private final Class<T> clazz;

    public FilterScanner(Class<T> clazz) {
        this.clazz = Objects.requireNonNull(clazz);
    }

    @Override
    public Map<String, T> find() throws Exception {
        Map<NamedFilter[], T> multiple = ReflectionUtil.
                findAnnotatedWithValue(NamedFilters.class, clazz, NamedFilter[].class);
        Map<String, T> single = ReflectionUtil.findAnnotatedWithValue(NamedFilter.class, clazz, String.class);
        multiple.forEach((key, value) -> {
            for (NamedFilter filter : key) {
                single.put(filter.value(), value);
            }
        });
        if (ConfigProvider.getAmayaConfig().isDebug()) {
            LOGGER.debug("The scanner with the base class " +
                    clazz.getSimpleName() +
                    " found filters: \n" +
                    single
            );
        }
        return single;
    }
}
