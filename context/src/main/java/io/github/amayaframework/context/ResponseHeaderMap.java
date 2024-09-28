package io.github.amayaframework.context;

import jakarta.servlet.http.HttpServletResponse;

import java.util.*;

/**
 *
 */
public final class ResponseHeaderMap implements Map<String, String> {
    private final HttpServletResponse response;

    /**
     * @param response
     */
    public ResponseHeaderMap(HttpServletResponse response) {
        this.response = response;
    }

    private Collection<String> getHeaders() {
        return response.getHeaderNames();
    }

    @Override
    public int size() {
        return getHeaders().size();
    }

    @Override
    public boolean isEmpty() {
        return getHeaders().isEmpty();
    }

    @Override
    @SuppressWarnings("SuspiciousMethodCalls")
    public boolean containsKey(Object key) {
        return getHeaders().contains(key);
    }

    @Override
    public boolean containsValue(Object value) {
        var headers = getHeaders();
        for (var header : headers) {
            var toCheck = response.getHeader(header);
            if (toCheck.equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String get(Object key) {
        return response.getHeader((String) key);
    }

    @Override
    public String put(String key, String value) {
        var ret = response.getHeader(key);
        response.setHeader(key, value);
        return ret;
    }

    @Override
    public String remove(Object key) {
        var string = (String) key;
        var ret = response.getHeader(string);
        response.setHeader(string, null);
        return ret;
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        Objects.requireNonNull(m);
        for (var entry : m.entrySet()) {
            response.setHeader(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        var headers = getHeaders();
        for (var header : headers) {
            response.setHeader(header, null);
        }
    }

    @Override
    public Set<String> keySet() {
        return new HashSet<>(getHeaders());
    }

    @Override
    public Collection<String> values() {
        var ret = new LinkedList<String>();
        var headers = getHeaders();
        for (var header : headers) {
            ret.add(response.getHeader(header));
        }
        return ret;
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        var ret = new HashSet<Entry<String, String>>();
        var headers = getHeaders();
        for (var header : headers) {
            ret.add(new EntryImpl(header));
        }
        return ret;
    }

    private final class EntryImpl extends AbstractEntry<String, String> {

        private EntryImpl(String key) {
            super(key);
        }

        @Override
        public String getValue() {
            return response.getHeader(key);
        }

        @Override
        public String setValue(String value) {
            var ret = response.getHeader(key);
            response.setHeader(key, value);
            return ret;
        }

        @Override
        public String toString() {
            return key + "=" + response.getHeader(key);
        }
    }
}
