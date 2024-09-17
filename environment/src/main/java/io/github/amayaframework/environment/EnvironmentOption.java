package io.github.amayaframework.environment;

public interface EnvironmentOption {

    String getName();

    boolean is(String option);

    <T> T getValue();
}
