/**
 * The {@code amayaframework.application} module provides core abstractions and implementations
 * for application lifecycle management, including task execution, service coordination,
 * environment integration, and configuration facilities.
 * <p>
 * It depends on core functional utilities, cancellation tokens, middleware constructs,
 * and other foundational Amaya modules such as options, environment, and service management.
 * The module also optionally integrates with the {@code amayaframework.di} module for dependency injection.
 * <p>
 * This module exports the {@code io.github.amayaframework.application} package
 * which contains the main interfaces and base classes for building and configuring applications.
 */
module amayaframework.application {
    // Imports
    // Base dependencies
    requires com.github.romanqed.jfunc;
    requires com.github.romanqed.jct;
    requires com.github.romanqed.jconv;
    // Amaya modules
    requires amayaframework.options;
    requires amayaframework.environment;
    requires amayaframework.service;
    // Optional modules
    requires static amayaframework.di;
    // Exports
    exports io.github.amayaframework.application;
}
