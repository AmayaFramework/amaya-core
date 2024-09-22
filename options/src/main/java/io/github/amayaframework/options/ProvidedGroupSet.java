package io.github.amayaframework.options;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 *
 */
public class ProvidedGroupSet extends AbstractGroupSet {
    private final Function<String, OptionSet> provider;

    /**
     * @param delimiter
     * @param defGroup
     * @param supplier
     * @param provider
     */
    public ProvidedGroupSet(String delimiter,
                            String defGroup,
                            Supplier<Map<String, OptionSet>> supplier,
                            Function<String, OptionSet> provider) {
        super(delimiter, defGroup, supplier);
        this.provider = Objects.requireNonNull(provider);
    }

    /**
     * @param delimiter
     * @param defGroup
     * @param supplier
     * @param provider
     */
    public ProvidedGroupSet(String delimiter,
                            String defGroup,
                            Supplier<Map<String, OptionSet>> supplier,
                            Supplier<OptionSet> provider) {
        super(delimiter, defGroup, supplier);
        Objects.requireNonNull(provider);
        this.provider = k -> provider.get();
    }

    /**
     * @param delimiter
     * @param defGroup
     * @param provider
     */
    public ProvidedGroupSet(String delimiter, String defGroup, Supplier<OptionSet> provider) {
        super(delimiter, defGroup, HashMap::new);
        Objects.requireNonNull(provider);
        this.provider = k -> provider.get();
    }

    /**
     * @param delimiter
     * @param provider
     */
    public ProvidedGroupSet(String delimiter, Supplier<OptionSet> provider) {
        super(delimiter, "", HashMap::new);
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
