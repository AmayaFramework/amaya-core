package io.github.amayaframework.context;

import jakarta.servlet.http.HttpServletRequest;

import java.util.*;

/**
 *
 */
public final class RequestHeaderMap extends AbstractIteratedMap<String, String> {
    private final HttpServletRequest request;

    /**
     * @param request
     */
    public RequestHeaderMap(HttpServletRequest request) {
        super(() -> request.getHeaderNames().asIterator());
        this.request = request;
    }

    @Override
    public boolean containsKey(Object key) {
        return request.getHeader((String) key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        for (var key : keys) {
            var object = request.getHeader(key);
            if (object.equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String get(Object key) {
        return request.getHeader((String) key);
    }

    @Override
    public String put(String key, String value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<String> values() {
        var ret = new LinkedList<String>();
        for (var key : keys) {
            ret.add(request.getHeader(key));
        }
        return ret;
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        var ret = new HashSet<Entry<String, String>>();
        for (var key : keys) {
            ret.add(new EntryImpl(key));
        }
        return ret;
    }

    private final class EntryImpl extends AbstractEntry<String, String> {

        private EntryImpl(String key) {
            super(key);
        }

        @Override
        public String getValue() {
            return request.getHeader(key);
        }

        @Override
        public String setValue(String value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public String toString() {
            return key + "=" + request.getHeader(key);
        }
    }
}
