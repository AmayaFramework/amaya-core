/**
 * Amaya Server Module.
 *
 * @author Roman Bakaldin
 */
open module io.github.amayaframework.server {
    // Imports
    requires com.github.romanqed.jfunc;
    requires io.github.amayaframework.options;
    requires io.github.amayaframework.context;
    requires io.github.amayaframework.service;
    requires io.github.amayaframework.http;
    // Optional imports
    requires static io.github.amayaframework.environment;
    // Exports
    exports io.github.amayaframework.server;
}
