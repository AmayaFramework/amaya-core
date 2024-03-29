package io.github.amayaframework.core.environment;

import java.io.File;

public interface EnvironmentFactory {

    Environment create(File directory);
}
