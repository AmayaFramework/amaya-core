package io.github.amayaframework.core.config;

/**
 * Interface describing a configurable entity.
 */
public interface Configurable {
    /**
     * Sets the field according to the transmitted data.
     *
     * @param field field name and type to set
     * @param value value to set
     * @param <T>   type of value
     */
    <T> void setField(Field<T> field, T value);

    /**
     * Gets the field content.
     *
     * @param field field name and type to get
     * @param <T>   type of value
     * @return entity that field contains
     */
    <T> T getField(Field<T> field);
}
