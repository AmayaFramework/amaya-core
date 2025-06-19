package io.github.amayaframework.options;

import com.github.romanqed.jfunc.Runnable1;
import com.github.romanqed.jfunc.Runnable2;

import java.util.Map;
import java.util.Set;

/**
 * An interface extending {@link OptionSet} interface with basic option group operations.
 * <br>
 * It includes the basic group operations: getGroup, setGroup, containsGroup, removeGroup.
 * <br>
 * Every single key operation splits key by 2 parts: group identifier and option key identifier.
 * After that use group identifier to try access {@link OptionSet} instance related to requested group.
 * If set can be accessed, interacts with value from it by option key.
 */
public interface GroupOptionSet extends OptionSet {

    /**
     * Gets the {@link OptionSet} instance associated with given group name.
     *
     * @param group the specified group name
     * @return the {@link OptionSet} instance associated with group name or null
     */
    OptionSet getGroup(String group);

    /**
     * Checks if set contains group with the specified name.
     *
     * @param group the specified group name
     * @return true, if group exists, false otherwise
     */
    boolean containsGroup(String group);

    /**
     * Sets given {@link OptionSet} instance to the specified group name.
     *
     * @param group the specified group name, must be non-null
     * @param set   the specified {@link OptionSet} instance, may be null
     * @return previous associated instance, if it exists, null otherwise
     */
    OptionSet setGroup(String group, OptionSet set);

    /**
     * Removes the specified group from set.
     *
     * @param group the specified group name
     * @return associated {@link OptionSet} instance, if it exists, null otherwise
     */
    OptionSet removeGroup(String group);

    /**
     * Gets set contains all group names from this {@link OptionSet} instance.
     *
     * @return {@link Set} instance
     */
    @Override
    Set<String> getKeys();

    /**
     * Gets map contains all group entries from this {@link OptionSet} instance.
     *
     * @return {@link Map} instance
     */
    @Override
    Map<String, Object> asMap();

    /**
     * Performs given action for each key in each group in this {@link GroupOptionSet} instance.
     *
     * @param action the action to be performed, must be non-null
     */
    @Override
    void forEach(Runnable1<String> action);

    /**
     * Performs given action for each key-value pair in each group in this {@link OptionSet} instance.
     *
     * @param action the action to be performed, must be non-null
     */
    @Override
    void forEach(Runnable2<String, Object> action);
}
