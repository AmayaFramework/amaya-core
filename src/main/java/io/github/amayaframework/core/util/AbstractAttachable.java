package io.github.amayaframework.core.util;

import java.util.Map;

public abstract class AbstractAttachable implements Attachable {
    private final Map<String, Object> body;

    protected AbstractAttachable(Map<String, Object> body) {
        this.body = body;
    }

    @Override
    public Map<String, Object> getAttachments() {
        return body;
    }

    @Override
    public Object get(String key) {
        return body.get(key);
    }

    @Override
    public void set(String key, Object value) {
        body.put(key, value);
    }

    @Override
    public Object remove(String key) {
        return body.remove(key);
    }
}
