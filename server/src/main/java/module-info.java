/**
 * The amayaframework.server module provides abstractions and implementations
 * for server-side components, including HTTP servers and generic servers.
 * <p>
 * This module depends on servlet APIs, core Amaya modules, and several utility
 * libraries for async programming and cancellation support.
 * <p>
 * The {@code jakarta.servlet} API and {@code amayaframework.context} module are
 * exported transitively to allow downstream users to interact with servlet
 * types and context abstractions without requiring explicit dependencies.
 */
module amayaframework.server {
    // Imports
    // Base dependencies
    requires com.github.romanqed.jfunc;
    requires com.github.romanqed.juni;
    requires com.github.romanqed.jct;
    // Servlets (transitive)
    requires transitive jakarta.servlet;
    // Amaya modules
    requires amayaframework.options;
    requires amayaframework.service;
    requires transitive amayaframework.context;
    // Optional amaya modules
    requires static amayaframework.environment;
    // Exports
    exports io.github.amayaframework.server;
}
