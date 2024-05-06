package io.github.amayaframework.core.configuration;

public interface ConfigurationBuilder {

    ConfigurationBuilder setScheme(Scheme scheme);

    ConfigurationBuilder addProvider(KeyProvider provider);

    ConfigurationBuilder removeProvider(KeyProvider provider);

    Configuration build();
}
