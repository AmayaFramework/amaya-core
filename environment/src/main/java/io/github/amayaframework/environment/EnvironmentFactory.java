package io.github.amayaframework.environment;

import java.io.IOException;

public interface EnvironmentFactory {

    Environment create(String name, EnvironmentOption... options) throws IOException;

    Environment create(String name) throws IOException;
}
