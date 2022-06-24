package io.github.amayaframework.core.util;

import java.util.Collections;
import java.util.Map;

public class CompletableAttachable extends AbstractCompletableData implements Attachable {
    private Map<String, Object> body;

    protected CompletableAttachable(Map<String, Object> body) {
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
        checkCompleted();
        body.put(key, value);
    }

    @Override
    public Object remove(String key) {
        checkCompleted();
        return body.remove(key);
    }

    @Override
    public void complete() {
        this.completed = true;
        this.body = Collections.unmodifiableMap(body);
    }
}
