package io.github.amayaframework.options;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Implementation of {@link GroupOptionSet} that automatically creates
 * requested group instance on {@link GroupOptionSet#set(String, Object)} operation.
 */
public class ProvidedGroupSet extends AbstractGroupSet {
    private final Function<String, OptionSet> provider;

    /**
     * Constructs {@link ProvidedGroupSet} instance with given delimiter, default group name, map provided by supplier
     * and group instance provider.
     *
     * @param delimiter the specified delimiter
     * @param defGroup  the specified default group name
     * @param supplier  supplier providing map instance
     * @param provider  function providing group instance
     */
    public ProvidedGroupSet(String delimiter,
                            String defGroup,
                            Supplier<Map<String, OptionSet>> supplier,
                            Function<String, OptionSet> provider) {
        super(
                Objects.requireNonNull(delimiter),
                Objects.requireNonNull(defGroup),
                Objects.requireNonNull(supplier.get())
        );
        this.provider = Objects.requireNonNull(provider);
    }

    /**
     * Constructs {@link ProvidedGroupSet} instance with given delimiter, default group name, map provided by supplier
     * and group instance provider.
     *
     * @param delimiter the specified delimiter
     * @param defGroup  the specified default group name
     * @param supplier  supplier providing map instance
     * @param provider  supplier providing group instance
     */
    public ProvidedGroupSet(String delimiter,
                            String defGroup,
                            Supplier<Map<String, OptionSet>> supplier,
                            Supplier<OptionSet> provider) {
        super(
                Objects.requireNonNull(delimiter),
                Objects.requireNonNull(defGroup),
                Objects.requireNonNull(supplier.get())
        );
        Objects.requireNonNull(provider);
        this.provider = k -> provider.get();
    }

    /**
     * Constructs {@link ProvidedGroupSet} instance with given delimiter, default group name
     * and group instance provider.
     *
     * @param delimiter the specified delimiter
     * @param defGroup  the specified default group name
     * @param provider  supplier providing group instance
     */
    public ProvidedGroupSet(String delimiter, String defGroup, Supplier<OptionSet> provider) {
        super(Objects.requireNonNull(delimiter), Objects.requireNonNull(defGroup), new HashMap<>());
        Objects.requireNonNull(provider);
        this.provider = k -> provider.get();
    }

    /**
     * Constructs {@link ProvidedGroupSet} instance with given delimiter, '' as default group name
     * and group instance provider.
     *
     * @param delimiter the specified delimiter
     * @param provider  supplier providing group instance
     */
    public ProvidedGroupSet(String delimiter, Supplier<OptionSet> provider) {
        super(Objects.requireNonNull(delimiter), "", new HashMap<>());
        Objects.requireNonNull(provider);
        this.provider = k -> provider.get();
    }

    @Override
    public OptionSet setGroup(String group, OptionSet set) {
        Objects.requireNonNull(group);
        return groups.put(group, set);
    }

    @Override
    public OptionSet removeGroup(String group) {
        return groups.remove(group);
    }

    @Override
    public Object set(String key, Object value) {
        var index = key.lastIndexOf(delimiter);
        var group = extractGroup(index, key);
        var found = groups.computeIfAbsent(group, provider);
        return found.set(extractName(index, key), value);
    }
}
