/**
 * Amaya Server Module.
 *
 * @author Roman Bakaldin
 */
module io.github.amayaframework.server {
    // Imports
    requires com.github.romanqed.jconv;
    requires static io.github.amayaframework.options;
    requires io.github.amayaframework.context;
    requires io.github.amayaframework.service;
    requires io.github.amayaframework.http;
    // Exports
    exports io.github.amayaframework.server;
}
