/**
 * The Web Application module of Amaya Framework.
 * <p>
 * This module provides interfaces and base implementations for building
 * web applications, including HTTP context handling, web application lifecycle,
 * server integration, and configuration facilities.
 * <p>
 * It depends on core Amaya modules such as application, server, environment,
 * options, and service, and integrates with servlet APIs and core utility
 * libraries for functional programming and concurrency control.
 * <p>
 * The module supports optional integration with dependency injection via
 * amayaframework.di, allowing extensible and modular web application setups.
 */
module amayaframework.web {
    // Imports
    // Basic
    requires transitive com.github.romanqed.jfunc;
    requires transitive com.github.romanqed.jconv;
    requires transitive com.github.romanqed.jct;
    // Amaya modules
    requires amayaframework.options;
    requires amayaframework.environment;
    requires amayaframework.service;
    requires transitive amayaframework.server;
    requires transitive amayaframework.application;
    // Optional modules
    requires static amayaframework.di;
    // Exports
    exports io.github.amayaframework.web;
}
