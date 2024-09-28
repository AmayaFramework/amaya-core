/**
 * Amaya Context Module
 *
 * @author Roman Bakaldin
 */
open module io.github.amayaframework.context {
    // Imports
    requires static jetty.servlet.api;
    requires io.github.amayaframework.http;
    // Exports
    exports io.github.amayaframework.context;
}
