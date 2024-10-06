package io.github.amayaframework.core;

import io.github.amayaframework.environment.EnvironmentFactory;
import io.github.amayaframework.environment.NativeEnvironmentFactory;
import io.github.amayaframework.service.HandledManagerFactory;
import io.github.amayaframework.service.ServiceHandler;
import io.github.amayaframework.service.ServiceManagerFactory;

final class Defaults {
    static final EnvironmentFactory DEFAULT_ENVIRONMENT_FACTORY = new NativeEnvironmentFactory();
    static final ServiceHandler DEFAULT_HANDLER = new PlainServiceHandler();
    static final ServiceManagerFactory DEFAULT_MANAGER_FACTORY = new HandledManagerFactory();

    private Defaults() {
    }
}
