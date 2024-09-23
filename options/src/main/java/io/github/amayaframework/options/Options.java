package io.github.amayaframework.options;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class contains some useful methods that allow you to create an {@link OptionSet} implementation.
 */
public final class Options {
    public static final OptionSet EMPTY_OPTION_SET = new EmptyOptionSet();
    public static final String DEFAULT_GROUP_DELIMITER = ".";
    public static final String DEFAULT_GROUP = "";

    private Options() {
    }

    /**
     * Returns empty {@link OptionSet} singleton implementation.
     *
     * @return empty {@link OptionSet} implementation
     */
    public static OptionSet empty() {
        return EMPTY_OPTION_SET;
    }

    /**
     * Creates an empty modifiable instance of {@link OptionSet}.
     *
     * @return instance of {@link OptionSet}.
     */
    public static OptionSet create() {
        return new OpenOptionSet();
    }

    /**
     * Creates an empty instance of {@link OptionSet} that accepts keys only from given set.
     *
     * @param accepted the specified key set containing allowed keys
     * @return instance of {@link OptionSet}
     */
    public static OptionSet create(Set<String> accepted) {
        return new ClosedOptionSet(accepted);
    }

    /**
     * Creates an empty modifiable instance of {@link GroupOptionSet}
     * with the specified key qualifier delimiter and default group name.
     *
     * @param delimiter the specified delimiter
     * @param def       the specified default group name
     * @return instance of {@link GroupOptionSet}
     */
    public static GroupOptionSet createGrouped(String delimiter, String def) {
        return new ProvidedGroupSet(delimiter, def, OpenOptionSet::new);
    }

    /**
     * Creates an empty modifiable instance of {@link GroupOptionSet}
     * with {@link Options#DEFAULT_GROUP_DELIMITER} and {@link Options#DEFAULT_GROUP}.
     *
     * @return instance of {@link GroupOptionSet}
     */
    public static GroupOptionSet createGrouped() {
        return new ProvidedGroupSet(DEFAULT_GROUP_DELIMITER, DEFAULT_GROUP, OpenOptionSet::new);
    }

    /**
     * Creates an unmodifiable instance of {@link OptionSet} containing given key-value entry.
     *
     * @param k the specified key
     * @param v the specified value
     * @return instance of {@link OptionSet}
     */
    public static OptionSet of(String k, Object v) {
        return new PairOptionSet(k, v);
    }

    /**
     * Creates an unmodifiable instance of {@link OptionSet} containing given key-value entries.
     *
     * @param k1 the first key
     * @param v1 the first value
     * @param k2 the second key
     * @param v2 the second value
     * @return instance of {@link OptionSet}
     */
    public static OptionSet of(String k1, Object v1, String k2, Object v2) {
        return new UnmodifiableOptionSet(Map.of(k1, v1, k2, v2));
    }

    /**
     * Creates an unmodifiable instance of {@link OptionSet} containing given key-value entries.
     *
     * @param k1 the first key
     * @param v1 the first value
     * @param k2 the second key
     * @param v2 the second value
     * @param k3 the third key
     * @param v3 the third value
     * @return instance of {@link OptionSet}
     */
    public static OptionSet of(String k1, Object v1, String k2, Object v2, String k3, Object v3) {
        return new UnmodifiableOptionSet(Map.of(k1, v1, k2, v2, k3, v3));
    }

    /**
     * Creates an unmodifiable instance of {@link OptionSet} containing given key-value entries.
     *
     * @param k1 the first key
     * @param v1 the first value
     * @param k2 the second key
     * @param v2 the second value
     * @param k3 the third key
     * @param v3 the third value
     * @param k4 the fourth key
     * @param v4 the fourth value
     * @return instance of {@link OptionSet}
     */
    public static OptionSet of(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4) {
        return new UnmodifiableOptionSet(Map.of(k1, v1, k2, v2, k3, v3, k4, v4));
    }

    /**
     * Creates a modifiable instance of {@link OptionSet} containing entries from given map.
     *
     * @param map the specified map
     * @return instance of {@link OptionSet}
     */
    public static OptionSet of(Map<String, Object> map) {
        var body = new HashMap<>(map);
        body.remove(null);
        return new OpenOptionSet(body);
    }

    /**
     * Creates a modifiable instance of {@link OptionSet} containing keys from given set. All values set to null.
     *
     * @param keys the specified key set
     * @return instance of {@link OptionSet}
     */
    public static OptionSet of(Set<String> keys) {
        var map = new HashMap<String, Object>();
        for (var key : keys) {
            map.put(key, null);
        }
        return new OpenOptionSet(map);
    }

    /**
     * Creates a modifiable instance of {@link OptionSet} containing keys from given iterable object.
     * All values set to null.
     *
     * @param keys the specified iterable object
     * @return instance of {@link OptionSet}
     */
    public static OptionSet of(Iterable<String> keys) {
        var map = new HashMap<String, Object>();
        for (var key : keys) {
            map.put(key, null);
        }
        return new OpenOptionSet(map);
    }

    /**
     * Creates a modifiable instance of {@link OptionSet} containing keys from given array. All values set to null.
     *
     * @param keys the specified key array
     * @return instance of {@link OptionSet}
     */
    public static OptionSet of(String... keys) {
        var map = new HashMap<String, Object>();
        for (var key : keys) {
            map.put(key, null);
        }
        return new OpenOptionSet(map);
    }
}
