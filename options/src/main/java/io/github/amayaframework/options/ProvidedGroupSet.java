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
     * @param defGroup  the specified default group name
     * @param supplier  supplier providing map instance
     * @param provider  function providing group instance
     */
    public ProvidedGroupSet(String defGroup,
                            Supplier<Map<String, OptionSet>> supplier,
                            Function<String, OptionSet> provider) {
        super(Objects.requireNonNull(defGroup), Objects.requireNonNull(supplier.get()));
        this.provider = Objects.requireNonNull(provider);
    }

    /**
     * Constructs {@link ProvidedGroupSet} instance with given delimiter, default group name, map provided by supplier
     * and group instance provider.
     *
     * @param defGroup  the specified default group name
     * @param supplier  supplier providing map instance
     * @param provider  supplier providing group instance
     */
    public ProvidedGroupSet(String defGroup,
                            Supplier<Map<String, OptionSet>> supplier,
                            Supplier<OptionSet> provider) {
        super(Objects.requireNonNull(defGroup), Objects.requireNonNull(supplier.get()));
        Objects.requireNonNull(provider);
        this.provider = k -> provider.get();
    }

    /**
     * Constructs {@link ProvidedGroupSet} instance with given delimiter, default group name
     * and group instance provider.
     *
     * @param defGroup  the specified default group name
     * @param provider  supplier providing group instance
     */
    public ProvidedGroupSet(String defGroup, Supplier<OptionSet> provider) {
        super(Objects.requireNonNull(defGroup), new HashMap<>());
        Objects.requireNonNull(provider);
        this.provider = k -> provider.get();
    }

    /**
     * Constructs {@link ProvidedGroupSet} instance with given delimiter, '' as default group name
     * and group instance provider.
     *
     * @param provider  supplier providing group instance
     */
    public ProvidedGroupSet(Supplier<OptionSet> provider) {
        super("", new HashMap<>());
        Objects.requireNonNull(provider);
        this.provider = k -> provider.get();
    }

    @Override
    protected OptionSet createGroup(String name) {
        return provider.apply(name);
    }
}
