package io.github.amayaframework.environment;

import java.nio.file.Path;
import java.util.Objects;

public final class StandardEnvironmentOption implements EnvironmentOption {
    public static final String ROOT_LOCATION = "env_root";
    public static final String INIT_ROOT = "init_env_root";

    private final String name;
    private final Class<?> type;
    private final Object value;

    private StandardEnvironmentOption(String name, Object value) {
        this.name = name;
        this.type = value.getClass();
        this.value = value;
    }

    public static StandardEnvironmentOption rootLocation(Path location) {
        Objects.requireNonNull(location);
        return new StandardEnvironmentOption(ROOT_LOCATION, location);
    }

    public static StandardEnvironmentOption initRoot(boolean init) {
        return new StandardEnvironmentOption(INIT_ROOT, init);
    }

    public static final StandardEnvironmentOption ENABLE_INIT = initRoot(true);
    public static final StandardEnvironmentOption DISABLE_INIT = initRoot(false);
    public static final StandardEnvironmentOption LOCAL_ROOT = rootLocation(Path.of("."));

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean is(String option) {
        return name.equals(option);
    }

    @Override
    public Class<?> getType() {
        return type;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getValue() {
        return (T) value;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || object.getClass() != StandardEnvironmentOption.class) return false;
        var that = (StandardEnvironmentOption) object;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name + "{value=" + value + "}";
    }
}
