package io.github.amayaframework.core.filters;

import javax.servlet.http.Cookie;
import java.util.Map;

public class CookieFilter implements ContentFilter {
    @Override
    @SuppressWarnings("unchecked")
    public Object transform(Object source, String name) {
        try {
            return ((Map<String, Cookie>) source).get(name);
        } catch (Exception e) {
            return null;
        }
    }
}
