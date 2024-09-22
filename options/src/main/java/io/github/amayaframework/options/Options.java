package io.github.amayaframework.options;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public final class Options {
    public static final OptionSet EMPTY_OPTION_SET = new EmptyOptionSet();
    public static final String DEFAULT_GROUP_DELIMITER = ".";
    public static final String DEFAULT_GROUP = "";

    private Options() {
    }

    /**
     * @return
     */
    public static OptionSet empty() {
        return EMPTY_OPTION_SET;
    }

    /**
     * @return
     */
    public static OptionSet create() {
        return new OpenOptionSet();
    }

    /**
     * @param accepted
     * @return
     */
    public static OptionSet create(Set<String> accepted) {
        return new ClosedOptionSet(accepted);
    }

    /**
     * @param delimiter
     * @param def
     * @return
     */
    public static GroupOptionSet createGrouped(String delimiter, String def) {
        return new ProvidedGroupSet(delimiter, def, OpenOptionSet::new);
    }

    /**
     * @return
     */
    public static GroupOptionSet createGrouped() {
        return new ProvidedGroupSet(DEFAULT_GROUP_DELIMITER, DEFAULT_GROUP, OpenOptionSet::new);
    }

    /**
     * @param k
     * @param v
     * @return
     */
    public static OptionSet of(String k, Object v) {
        return new PairOptionSet(k, v);
    }

    /**
     * @param k1
     * @param v1
     * @param k2
     * @param v2
     * @return
     */
    public static OptionSet of(String k1, Object v1, String k2, Object v2) {
        return new UnmodifiableOptionSet(Map.of(k1, v1, k2, v2));
    }

    /**
     * @param k1
     * @param v1
     * @param k2
     * @param v2
     * @param k3
     * @param v3
     * @return
     */
    public static OptionSet of(String k1, Object v1, String k2, Object v2, String k3, Object v3) {
        return new UnmodifiableOptionSet(Map.of(k1, v1, k2, v2, k3, v3));
    }

    /**
     * @param k1
     * @param v1
     * @param k2
     * @param v2
     * @param k3
     * @param v3
     * @param k4
     * @param v4
     * @return
     */
    public static OptionSet of(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4) {
        return new UnmodifiableOptionSet(Map.of(k1, v1, k2, v2, k3, v3, k4, v4));
    }

    /**
     * @param body
     * @return
     */
    public static OptionSet of(Map<String, Object> body) {
        return new UnmodifiableOptionSet(body);
    }

    /**
     * @param keys
     * @return
     */
    public static OptionSet of(Set<String> keys) {
        if (keys == null || keys.isEmpty()) {
            return EMPTY_OPTION_SET;
        }
        if (keys.size() == 1) {
            return new PairOptionSet(keys.iterator().next(), true);
        }
        var map = new HashMap<String, Object>();
        for (var key : keys) {
            map.put(key, true);
        }
        return new UnmodifiableOptionSet(map);
    }

    /**
     * @param keys
     * @return
     */
    public static OptionSet of(String... keys) {
        if (keys == null || keys.length == 0) {
            return EMPTY_OPTION_SET;
        }
        if (keys.length == 1) {
            return new PairOptionSet(keys[0], true);
        }
        var map = new HashMap<String, Object>();
        for (var key : keys) {
            map.put(key, true);
        }
        return new UnmodifiableOptionSet(map);
    }
}
