package io.github.amayaframework.core.filters;

import io.github.amayaframework.core.wrapping.Content;
import io.github.amayaframework.filters.ContentFilter;
import io.github.amayaframework.filters.NamedFilter;

import java.util.Map;

@NamedFilter(Content.PATH)
public class PathFilter implements ContentFilter {
    @Override
    @SuppressWarnings("unchecked")
    public Object transform(Object source, String name) {
        try {
            return ((Map<String, Object>) source).get(name);
        } catch (Exception e) {
            return null;
        }
    }
}
