/**
 * The core module of the Amaya Framework.
 *
 * <p>This module provides foundational implementations and factories for
 * web applications, integrating components such as environment management,
 * service lifecycle handling, HTTP server configuration, and logging support.
 *
 * <p>It depends on several other Amaya modules, including:
 * <ul>
 *     <li>{@code amayaframework.options} — for configuration options handling</li>
 *     <li>{@code amayaframework.environment} — for environment abstractions</li>
 *     <li>{@code amayaframework.service} — for service lifecycle management</li>
 *     <li>{@code amayaframework.web} — for web application abstractions and implementations</li>
 * </ul>
 *
 * <p>This module also optionally integrates with SLF4J for logging and
 * optionally with Amaya's dependency injection modules {@code amayaframework.di} and
 * {@code amayaframework.di.stub} if available at runtime.
 *
 * <p>The module exports the {@code io.github.amayaframework.core} package,
 * containing the core implementations and utilities for building and running
 * web applications using the Amaya Framework.
 */
module amayaframework.core {
    // Imports
    // Basic
    requires transitive com.github.romanqed.juni;
    // Amaya modules
    requires amayaframework.options;
    requires amayaframework.environment;
    requires amayaframework.service;
    requires transitive amayaframework.web;
    // Logger
    requires static org.slf4j;
    // Optional modules
    requires static amayaframework.di;
    requires static amayaframework.di.stub;
    // Exports
    exports io.github.amayaframework.core;
}
