package io.github.amayaframework.core.configuration;

public interface ConfigurationSerializer<S> {

    S serialize(Configuration configuration);
}
