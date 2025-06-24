/**
 * Amaya Server Module
 *
 * @author Roman Bakaldin
 */
module io.github.amayaframework.server {
    // Imports
    requires com.github.romanqed.jfunc;
    // Servlets
    requires jakarta.servlet;
    // Amaya modules
    requires io.github.amayaframework.http;
    requires io.github.amayaframework.options;
    requires io.github.amayaframework.service;
    requires io.github.amayaframework.context;
    requires static io.github.amayaframework.environment;
    // Exports
    exports io.github.amayaframework.server;
}
