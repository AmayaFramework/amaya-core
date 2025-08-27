package io.github.amayaframework.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A holder for parsed MIME type data consisting of a {@link MimeType}
 * and an optional map of parameters. Typically corresponds to values
 * of HTTP headers such as {@code Content-Type} or {@code Accept}.
 * <p>
 * Parameters are represented as a simple {@code Map<String,String>}.
 * Keys are compared case-sensitively by default, although most HTTP
 * parameters are defined to be case-insensitive. Values may be {@code null}
 * if the parameter is present without an explicit value (e.g. {@code secure}).
 * </p>
 */
public final class MimeData {
    MimeType type;
    Map<String, String> params;

    /**
     * Constructs {@link MimeData} instance with given {@link MimeType}, parameter and value.
     *
     * @param type   the specified {@link MimeType}, must be non-null
     * @param params the specified parameter map, may be null
     */
    public MimeData(MimeType type, Map<String, String> params) {
        this.type = type;
        this.params = params;
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
     * Creates a new {@link MimeData} instance with the given type
     * and parameter map.
     *
     * @param type   the MIME type, must not be {@code null}
     * @param params the parameter map, may be {@code null}
     * @return a new {@link MimeData} instance
     */
    public static MimeData of(MimeType type, Map<String, String> params) {
        return new MimeData(Objects.requireNonNull(type), params);
    }

    /**
     * Creates a new {@link MimeData} instance with the given type
     * and a single parameter.
     *
     * @param type  the MIME type, must not be {@code null}
     * @param param the parameter name, must not be {@code null}
     * @param value the parameter value, may be {@code null}
     * @return a new {@link MimeData} instance
     */
    public static MimeData of(MimeType type, String param, String value) {
        var params = new HashMap<String, String>();
        params.put(param, value);
        return new MimeData(Objects.requireNonNull(type), params);
    }

    /**
     * Creates a new {@link MimeData} instance with the given type
     * and a single parameter without a value.
     *
     * @param type  the MIME type, must not be {@code null}
     * @param param the parameter name, must not be {@code null}
     * @return a new {@link MimeData} instance
     */
    public static MimeData of(MimeType type, String param) {
        var params = new HashMap<String, String>();
        params.put(param, null);
        return new MimeData(Objects.requireNonNull(type), params);
    }

    /**
     * Creates a new {@link MimeData} instance with the given type
     * and no parameters.
     *
     * @param type the MIME type, must not be {@code null}
     * @return a new {@link MimeData} instance
     */
    public static MimeData of(MimeType type) {
        return new MimeData(Objects.requireNonNull(type));
    }

    /**
     * Returns the {@link MimeType} of this data.
     *
     * @return the MIME type, never {@code null}
     */
    public MimeType getType() {
        return type;
    }

    /**
     * Sets the {@link MimeType} of this data.
     *
     * @param type the MIME type, must not be {@code null}
     */
    public void setType(MimeType type) {
        this.type = Objects.requireNonNull(type);
    }

    /**
     * Returns the map of parameters associated with this MIME data.
     *
     * @return the parameters, or {@code null} if none were defined
     */
    public Map<String, String> getParams() {
        return params;
    }

    /**
     * Replaces the map of parameters associated with this MIME data.
     *
     * @param params the new parameter map, may be {@code null}
     */
    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    /**
     * Checks whether this MIME data has a parameter with the given name.
     *
     * @param param the parameter name
     * @return {@code true} if the parameter exists, {@code false} otherwise
     */
    public boolean hasParam(String param) {
        return params != null && params.containsKey(param);
    }

    /**
     * Returns the value of the given parameter.
     *
     * @param param the parameter name
     * @return the value of the parameter, or {@code null} if the parameter
     * is not present or explicitly defined without a value
     */
    public String getParam(String param) {
        return params == null ? null : params.get(param);
    }

    /**
     * Sets or replaces the value of the given parameter.
     * <p>
     * If the parameter map is {@code null}, it will be lazily created.
     * </p>
     *
     * @param param the parameter name, must not be {@code null}
     * @param value the parameter value, may be {@code null}
     */
    public void setParam(String param, String value) {
        if (params == null) {
            params = new HashMap<>();
        }
        params.put(param, value);
    }

    /**
     * Builds a canonical qualifier string for this MIME data,
     * consisting of the type and subtype followed by formatted
     * parameters, e.g. {@code "text/html; charset=UTF-8"}.
     *
     * @return the qualifier string
     */
    private String getQualifier() {
        if (params == null || params.isEmpty()) {
            return type.qualifier;
        }
        var builder = new StringBuilder(type.qualifier);
        for (var entry : params.entrySet()) {
            var key = entry.getKey();
            if (key == null) {
                continue;
            }
            builder.append(';').append(key);
            var value = entry.getValue();
            if (value == null) {
                continue;
            }
            builder.append('=').append(value);
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        var data = (MimeData) object;
        return type.equals(data.type) && Objects.equals(params, data.params);
    }

    @Override
    public int hashCode() {
        return getQualifier().hashCode();
    }

    @Override
    public String toString() {
        return getQualifier();
    }
}
