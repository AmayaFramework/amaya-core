package io.github.amayaframework.environment;

public interface EnvironmentOption {
    EnvironmentOption[] EMPTY = new EnvironmentOption[0];

    String getName();

    boolean is(String option);

    Class<?> getType();

    <T> T getValue();
}
