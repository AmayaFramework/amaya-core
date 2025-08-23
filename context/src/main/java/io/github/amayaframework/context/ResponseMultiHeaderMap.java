package io.github.amayaframework.context;

import jakarta.servlet.http.HttpServletResponse;

import java.util.*;
import java.util.function.BiConsumer;

public final class ResponseMultiHeaderMap implements Map<String, List<String>> {
    private final Map<String, List<String>> body;
    private final HttpServletResponse response;
    private Map<String, List<String>> finalBody;

    public ResponseMultiHeaderMap(Map<String, List<String>> body, HttpServletResponse response) {
        this.body = body;
        this.response = response;
    }

    @Override
    public int size() {
        return body.size();
    }

    @Override
    public boolean isEmpty() {
        return body.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return body.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return body.containsValue(value);
    }

    @Override
    public List<String> get(Object key) {
        return body.get(key);
    }

    @Override
    public List<String> put(String key, List<String> value) {
        var ret = body.put(key, new MultiHeaderList(value, key, response));
        if (ret != null) {
            response.setHeader(key, null);
        }
        value.forEach(v -> response.addHeader(key, v));
        return ret;
    }

    @Override
    public List<String> remove(Object key) {
        var ret = body.remove(key);
        if (ret != null) {
            response.setHeader((String) key, null);
        }
        return ret;
    }

    @Override
    public void putAll(Map<? extends String, ? extends List<String>> m) {
        m.forEach(this::put);
    }

    @Override
    public void clear() {
        body.forEach((k, v) -> response.setHeader(k, null));
        body.clear();
    }

    @Override
    public Set<String> keySet() {
        if (finalBody == null) {
            finalBody = Collections.unmodifiableMap(body);
        }
        return finalBody.keySet();
    }

    @Override
    public Collection<List<String>> values() {
        if (finalBody == null) {
            finalBody = Collections.unmodifiableMap(body);
        }
        return finalBody.values();
    }

    @Override
    public Set<Entry<String, List<String>>> entrySet() {
        if (finalBody == null) {
            finalBody = Collections.unmodifiableMap(body);
        }
        return finalBody.entrySet();
    }

    @Override
    public void forEach(BiConsumer<? super String, ? super List<String>> action) {
        body.forEach(action);
    }
}
