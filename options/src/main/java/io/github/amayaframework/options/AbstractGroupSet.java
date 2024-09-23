package io.github.amayaframework.options;

import com.github.romanqed.jfunc.Runnable1;
import com.github.romanqed.jfunc.Runnable2;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Skeletal implementation of {@link GroupOptionSet}.
 * <br>
 * Contains the implementation of all basic methods except {@link GroupOptionSet#set(String, Object)}.
 */
public abstract class AbstractGroupSet implements GroupOptionSet {
    /**
     * Key qualifier delimiter. For example, 'group.key' -> '.' is delimiter.
     */
    protected final String delimiter;
    /**
     * Delimiter length.
     */
    protected final int length;
    /**
     * Default group name. For example, value from this variable will be used for 'key' qualifier.
     */
    protected final String defGroup;
    /**
     * {@link Map} instance containing {@link OptionSet} instances associated with group names.
     */
    protected final Map<String, OptionSet> groups;

    /**
     * Constructs instance of {@link GroupOptionSet} with the specified delimiter, default group name and
     * group map.
     *
     * @param delimiter the specified delimiter, used in key qualifiers.
     *                  For example, if delim will be '.', qualifiers will be 'org.group.key'
     * @param defGroup  the specified group name, used by default
     * @param groups    the specified group map instance
     */
    protected AbstractGroupSet(String delimiter, String defGroup, Map<String, OptionSet> groups) {
        this.delimiter = delimiter;
        this.length = delimiter.length();
        this.defGroup = defGroup;
        this.groups = groups;
    }

    /**
     * Gets group identifier from full key qualifier.
     *
     * @param index     the index of first delimiter symbol
     * @param qualifier the specified key qualifier
     * @return default group name, if index &lt; 0, found identifier otherwise
     */
    protected String extractGroup(int index, String qualifier) {
        if (index < 0) {
            return defGroup;
        }
        return qualifier.substring(0, index);
    }

    /**
     * Gets key identifier from full key qualifier.
     *
     * @param index     the index of first delimiter symbol
     * @param qualifier the specified key qualifier
     * @return full string, if index &lt; 0, found identifier otherwise
     */
    protected String extractName(int index, String qualifier) {
        if (index < 0) {
            return qualifier;
        }
        return qualifier.substring(index + length);
    }

    @Override
    public OptionSet getGroup(String group) {
        return groups.get(group);
    }

    @Override
    public boolean containsGroup(String group) {
        return groups.containsKey(group);
    }

    @Override
    public <T> T get(String key) {
        var index = key.lastIndexOf(delimiter);
        var group = extractGroup(index, key);
        var found = groups.get(group);
        if (found == null) {
            return null;
        }
        return found.get(extractName(index, key));
    }

    @Override
    public boolean asKey(String key) {
        var index = key.lastIndexOf(delimiter);
        var group = extractGroup(index, key);
        var found = groups.get(group);
        if (found == null) {
            return false;
        }
        return found.asKey(extractName(index, key));
    }

    @Override
    public boolean asBool(String key) {
        var index = key.lastIndexOf(delimiter);
        var group = extractGroup(index, key);
        var found = groups.get(group);
        if (found == null) {
            return false;
        }
        return found.asBool(extractName(index, key));
    }

    @Override
    public boolean contains(String key) {
        var index = key.lastIndexOf(delimiter);
        var group = extractGroup(index, key);
        var found = groups.get(group);
        if (found == null) {
            return false;
        }
        return found.contains(extractName(index, key));
    }

    @Override
    public Object remove(String key) {
        var index = key.lastIndexOf(delimiter);
        var group = extractGroup(index, key);
        var found = groups.get(group);
        if (found == null) {
            return null;
        }
        return found.remove(extractName(index, key));
    }

    @Override
    public Set<String> getKeys() {
        return Collections.unmodifiableSet(groups.keySet());
    }

    @Override
    public Map<String, Object> asMap() {
        return Collections.unmodifiableMap(groups);
    }

    @Override
    public void forEach(Runnable1<String> action) {
        Objects.requireNonNull(action);
        for (var entry : groups.entrySet()) {
            var group = entry.getKey();
            if (group.equals(defGroup)) {
                entry.getValue().forEach(action);
            } else {
                var prefix = group + delimiter;
                entry.getValue().forEach(key -> action.run(prefix + key));
            }
        }
    }

    @Override
    public void forEach(Runnable2<String, Object> action) {
        Objects.requireNonNull(action);
        for (var entry : groups.entrySet()) {
            var group = entry.getKey();
            if (group.equals(defGroup)) {
                entry.getValue().forEach(action);
            } else {
                var prefix = entry.getKey() + delimiter;
                entry.getValue().forEach((key, value) -> action.run(prefix + key, value));
            }
        }
    }

    @Override
    public String toString() {
        return "Grouped Options " + groups;
    }
}
