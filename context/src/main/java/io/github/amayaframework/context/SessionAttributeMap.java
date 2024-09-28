package io.github.amayaframework.context;

import jakarta.servlet.http.HttpSession;

import java.util.*;

/**
 *
 */
public final class SessionAttributeMap extends AbstractIteratedMap<String, Object> {
    private final HttpSession session;

    /**
     * @param session
     */
    public SessionAttributeMap(HttpSession session) {
        super(() -> session.getAttributeNames().asIterator());
        this.session = session;
    }

    @Override
    public boolean containsKey(Object key) {
        return session.getAttribute((String) key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        for (var key : keys) {
            var object = session.getAttribute(key);
            if (object.equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object get(Object key) {
        return session.getAttribute((String) key);
    }

    @Override
    public Object put(String key, Object value) {
        var ret = session.getAttribute(key);
        session.setAttribute(key, value);
        return ret;
    }

    @Override
    public Object remove(Object key) {
        var string = (String) key;
        var ret = session.getAttribute(string);
        session.removeAttribute(string);
        return ret;
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        Objects.requireNonNull(m);
        for (var entry : m.entrySet()) {
            session.setAttribute(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        for (var key : keys) {
            session.removeAttribute(key);
        }
    }

    @Override
    public Collection<Object> values() {
        var ret = new LinkedList<>();
        for (var key : keys) {
            ret.add(session.getAttribute(key));
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
            return session.getAttribute(key);
        }

        @Override
        public Object setValue(Object value) {
            var ret = session.getAttribute(key);
            session.setAttribute(key, value);
            return ret;
        }

        @Override
        public String toString() {
            return key + "=" + session.getAttribute(key);
        }
    }
}
