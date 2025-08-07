/**
 * Provides abstraction over servlet request/response handling and contextual data access.
 * <p>
 * This module defines general-purpose {@link io.github.amayaframework.context.Context}
 * and HTTP-specific {@link io.github.amayaframework.context.HttpContext} interfaces,
 * which act as unified access points for servlet-layer requests and responses,
 * and provide attribute management facilities.
 * </p>
 * <p>
 * Includes base support for high-level {@code Request} and {@code Response} wrappers
 * over the servlet API, enabling consistent and framework-integrated context access
 * for middleware, routing, and handler layers.
 * </p>
 * <p>
 * Depends on:
 * <ul>
 *     <li>{@code jakarta.servlet} — for low-level request/response types</li>
 *     <li>{@code amayaframework.http} — for basic HTTP definitions</li>
 * </ul>
 */
module amayaframework.context {
    // Imports
    // Servlets
    requires transitive jakarta.servlet;
    // Amaya modules
    requires transitive amayaframework.http;
    // Exports
    exports io.github.amayaframework.context;
}
