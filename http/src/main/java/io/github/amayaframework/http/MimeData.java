package io.github.amayaframework.http;

import java.util.Objects;

/**
 * A class that implements the information holder about the mime data.
 * Usually, mime data is represented in the Content-Type header.
 */
public final class MimeData {
    MimeType type;
    String parameter;
    String value;

    /**
     * Constructs {@link MimeData} instance with given {@link MimeType}, parameter and value.
     *
     * @param type      the specified {@link MimeType}, must be non-null
     * @param parameter the specified parameter, may be null
     * @param value     the specified parameter value, may be null
     */
    public MimeData(MimeType type, String parameter, String value) {
        this.type = type;
        this.parameter = parameter;
        this.value = value;
    }

    /**
     * Constructs {@link MimeData} instance with given {@link MimeType}.
     *
     * @param type the specified {@link MimeType}, must be non-null
     */
    public MimeData(MimeType type) {
        this.type = type;
    }

    /**
     * Creates {@link MimeData} instance with given {@link MimeType}, parameter and value.
     *
     * @param type      the specified {@link MimeType}, must be non-null
     * @param parameter the specified parameter, may be null
     * @param value     the specified parameter value, may be null
     * @return {@link MimeData} instance
     */
    public static MimeData of(MimeType type, String parameter, String value) {
        return new MimeData(Objects.requireNonNull(type), parameter, value);
    }

    /**
     * Creates {@link MimeData} instance with given {@link MimeType} and parameter.
     *
     * @param type      the specified {@link MimeType}, must be non-null
     * @param parameter the specified parameter, may be null
     * @return {@link MimeData} instance
     */
    public static MimeData of(MimeType type, String parameter) {
        return new MimeData(Objects.requireNonNull(type), parameter, null);
    }

    /**
     * Creates {@link MimeData} instance with given {@link MimeType}.
     *
     * @param type the specified {@link MimeType}, must be non-null
     * @return {@link MimeData} instance
     */
    public static MimeData of(MimeType type) {
        return new MimeData(Objects.requireNonNull(type));
    }

    private static String getQualifier(MimeType type, String parameter, String value) {
        var ret = type.qualifier;
        if (parameter == null) {
            return ret;
        }
        if (value == null) {
            return ret + ";" + parameter;
        }
        return ret + ";" + parameter + "=" + value;
    }

    /**
     * Gets {@link MimeType} for this mime data.
     *
     * @return {@link MimeType} instance
     */
    public MimeType getType() {
        return type;
    }

    /**
     * Sets {@link MimeType} for this mime data.
     *
     * @param type the specified {@link MimeType}, must be non-null
     */
    public void setType(MimeType type) {
        this.type = Objects.requireNonNull(type);
    }

    /**
     * Gets parameter for this mime data.
     *
     * @return parameter
     */
    public String getParameter() {
        return parameter;
    }

    /**
     * Sets parameter for this mime data.
     *
     * @param parameter the specified parameter, may be null
     */
    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    /**
     * Gets parameter value for this mime data.
     *
     * @return parameter value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets parameter value for this mime data.
     *
     * @param value the specified parameter value, may be null
     */
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        var data = (MimeData) object;
        return type.equals(data.type) && parameter.equals(data.parameter) && value.equals(data.value);
    }

    @Override
    public int hashCode() {
        return getQualifier(type, parameter, value).hashCode();
    }

    @Override
    public String toString() {
        return getQualifier(type, parameter, value);
    }
}
