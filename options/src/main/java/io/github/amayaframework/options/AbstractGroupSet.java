package io.github.amayaframework.options;

import com.github.romanqed.jfunc.Exceptions;
import com.github.romanqed.jfunc.Runnable1;
import com.github.romanqed.jfunc.Runnable2;
import com.github.romanqed.jfunc.Runnable3;

import java.util.*;
import java.util.function.Consumer;

/**
 * Skeletal implementation of {@link GroupOptionSet}.
 * <br>
 * Contains the implementation of all basic methods except {@link GroupOptionSet#set(String, Object)}.
 */
public abstract class AbstractGroupSet implements GroupOptionSet {
    /**
     * Default group name. For example, value from this variable will be used for 'key' qualifier.
     */
    protected final String defName;
    /**
     * {@link Map} instance containing {@link OptionSet} instances associated with group names.
     */
    protected final Map<String, OptionSet> groups;
    /**
     * Default group set.
     */
    protected OptionSet defGroup;

    /**
     * Constructs instance of {@link GroupOptionSet} with the specified delimiter, default group name and
     * group map.
     *
     * @param defName the specified group name, used by default
     * @param groups    the specified group map instance
     */
    protected AbstractGroupSet(String defName, Map<String, OptionSet> groups) {
        this.defName = defName;
        this.groups = groups;
    }

    /**
     * Creates a new {@link OptionSet} instance for the given group name.
     *
     * @param name the group name
     * @return newly created {@link OptionSet}
     */
    protected abstract OptionSet createGroup(String name);

    /**
     * Ensures the {@link OptionSet} associated with the given group exists,
     * creating it if necessary.
     *
     * @param group the group name
     * @return the existing or newly created {@link OptionSet} instance
     */
    protected OptionSet ensure(String group) {
        if (group == null || defName.equals(group)) {
            if (defGroup != null) {
                return defGroup;
            }
            defGroup = createGroup(defName);
            groups.put(defName, defGroup);
            return defGroup;
        }
        var ret = groups.get(group);
        if (ret != null) {
            return ret;
        }
        ret = createGroup(group);
        groups.put(group, ret);
        return ret;
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
    public OptionSet ensureGroup(String group) {
        return ensure(group);
    }

    @Override
    public OptionSet setGroup(String group, OptionSet set) {
        if (group == null || defName.equals(group)) {
            defGroup = set;
        }
        return groups.put(group, set);
    }

    @Override
    public OptionSet removeGroup(String group) {
        if (group == null || defName.equals(group)) {
            defGroup = null;
        }
        return groups.remove(group);
    }

    @Override
    public <T> T get(String key) {
        if (defGroup == null) {
            return null;
        }
        return defGroup.get(key);
    }

    @Override
    public boolean asKey(String key) {
        if (defGroup == null) {
            return false;
        }
        return defGroup.asKey(key);
    }

    @Override
    public boolean asBool(String key) {
        if (defGroup == null) {
            return false;
        }
        return defGroup.asBool(key);
    }

    @Override
    public boolean contains(String key) {
        if (defGroup == null) {
            return false;
        }
        return defGroup.contains(key);
    }

    @Override
    public Object set(String key, Object value) {
        if (defGroup == null) {
            defGroup = createGroup(defName);
            groups.put(defName, defGroup);
        }
        return defGroup.set(key, value);
    }

    @Override
    public Object remove(String key) {
        if (defGroup == null) {
            return null;
        }
        return defGroup.remove(key);
    }

    @Override
    public <T> T get(String group, String key) {
        var set = group == null || defName.equals(group) ? defGroup : groups.get(group);
        if (set == null) {
            return null;
        }
        return set.get(key);
    }

    @Override
    public <T> T get(String group, String key, T def) {
        var set = group == null || defName.equals(group) ? defGroup : groups.get(group);
        if (set == null) {
            return def;
        }
        return set.get(key, def);
    }

    @Override
    public boolean asKey(String group, String key) {
        var set = group == null || defName.equals(group) ? defGroup : groups.get(group);
        if (set == null) {
            return false;
        }
        return set.asKey(key);
    }

    @Override
    public boolean asBool(String group, String key) {
        var set = group == null || defName.equals(group) ? defGroup : groups.get(group);
        if (set == null) {
            return false;
        }
        return set.asBool(key);
    }

    @Override
    public boolean contains(String group, String key) {
        var set = group == null || defName.equals(group) ? defGroup : groups.get(group);
        if (set == null) {
            return false;
        }
        return set.contains(key);
    }

    @Override
    public Object set(String group, String key, Object value) {
        var set = ensure(group);
        return set.set(key, value);
    }

    @Override
    public Object remove(String group, String key) {
        var set = group == null || defName.equals(group) ? defGroup : groups.get(group);
        if (set == null) {
            return null;
        }
        return set.remove(key);
    }

    @Override
    public boolean isEmpty() {
        return groups.isEmpty();
    }

    @Override
    public Set<String> keys() {
        return Collections.unmodifiableSet(groups.keySet());
    }

    @Override
    public Map<String, Object> asMap() {
        return Collections.unmodifiableMap(groups);
    }

    @Override
    public void forEach(Runnable1<String> action) {
        if (defGroup != null) {
            defGroup.forEach(action);
        }
    }

    @Override
    public void forEach(Runnable2<String, Object> action) {
        if (defGroup != null) {
            defGroup.forEach(action);
        }
    }

    @Override
    public void forEachGroup(Runnable2<String, String> action) {
        try {
            for (var entry : groups.entrySet()) {
                var group = entry.getKey();
                var set = entry.getValue();
                for (var key : set) {
                    action.run(group, key);
                }
            }
        } catch (Throwable e) {
            Exceptions.throwAny(e);
        }
    }

    @Override
    public void forEachGroup(Runnable3<String, String, Object> action) {
        try {
            for (var entry : groups.entrySet()) {
                var group = entry.getKey();
                var set = entry.getValue();
                set.forEach((key, val) -> action.run(group, key, val));
            }
        } catch (Throwable e) {
            Exceptions.throwAny(e);
        }
    }

    @Override
    public void forEach(Consumer<? super String> action) {
        if (defGroup != null) {
            defGroup.forEach(action);
        }
    }

    @Override
    public Iterator<String> iterator() {
        return new GroupedIterator(groups.values().iterator());
    }

    @Override
    public String toString() {
        return "Grouped Options " + groups;
    }

    /**
     * Iterator that traverses all keys across all {@link OptionSet}s in all groups.
     */
    protected static final class GroupedIterator implements Iterator<String> {
        private final Iterator<OptionSet> groupIterator;
        private Iterator<String> current;

        /**
         * Constructs iterator over provided group sets.
         *
         * @param groupIterator the iterator over {@link OptionSet} instances
         */
        public GroupedIterator(Iterator<OptionSet> groupIterator) {
            this.groupIterator = groupIterator;
        }

        @Override
        public boolean hasNext() {
            while (true) {
                if (current != null && current.hasNext()) {
                    return true;
                }
                if (!groupIterator.hasNext()) {
                    return false;
                }
                current = groupIterator.next().iterator();
            }
        }

        @Override
        public String next() {
            while (true) {
                if (current != null && current.hasNext()) {
                    return current.next();
                }
                if (!groupIterator.hasNext()) {
                    throw new NoSuchElementException();
                }
                current = groupIterator.next().iterator();
            }
        }

        @Override
        public void remove() {
            if (current == null) {
                throw new IllegalStateException();
            }
            current.remove();
        }
    }
}
