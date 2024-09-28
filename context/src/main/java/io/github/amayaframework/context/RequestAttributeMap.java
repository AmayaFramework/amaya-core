package io.github.amayaframework.context;

import jakarta.servlet.ServletRequest;

import java.util.*;

/**
 *
 */
public final class RequestAttributeMap extends AbstractIteratedMap<String, Object> {
    private final ServletRequest request;

    /**
     * @param request
     */
    public RequestAttributeMap(ServletRequest request) {
        super(() -> request.getAttributeNames().asIterator());
        this.request = request;
    }

    @Override
    public boolean containsKey(Object key) {
        return request.getAttribute((String) key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        for (var key : keys) {
            var object = request.getAttribute(key);
            if (object.equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object get(Object key) {
        return request.getAttribute((String) key);
    }

    @Override
    public Object put(String key, Object value) {
        var ret = request.getAttribute(key);
        request.setAttribute(key, value);
        return ret;
    }

    @Override
    public Object remove(Object key) {
        var string = (String) key;
        var ret = request.getAttribute(string);
        request.removeAttribute(string);
        return ret;
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        Objects.requireNonNull(m);
        for (var entry : m.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        for (var key : keys) {
            request.removeAttribute(key);
        }
    }

    @Override
    public Collection<Object> values() {
        var ret = new LinkedList<>();
        for (var key : keys) {
            ret.add(request.getAttribute(key));
        }
        return ret;
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        var ret = new HashSet<Entry<String, Object>>();
        for (var key : keys) {
            ret.add(new EntryImpl(key));
        }
        return ret;
    }

    private final class EntryImpl extends AbstractEntry<String, Object> {

        private EntryImpl(String key) {
            super(key);
        }

        @Override
        public Object getValue() {
            return request.getAttribute(key);
        }

        @Override
        public Object setValue(Object value) {
            var ret = request.getAttribute(key);
            request.setAttribute(key, value);
            return ret;
        }

        @Override
        public String toString() {
            return key + "=" + request.getAttribute(key);
        }
    }
}
