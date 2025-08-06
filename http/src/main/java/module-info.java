/**
 * Provides core HTTP constants and value types used throughout the framework.
 * <p>
 * This module defines minimal, reusable representations of common HTTP concepts,
 * including request methods, protocol versions, status codes, and MIME types.
 * </p>
 *
 * <p>
 * Included types:
 * <ul>
 *     <li>{@link io.github.amayaframework.http.HttpMethod} – standard HTTP request methods</li>
 *     <li>{@link io.github.amayaframework.http.HttpVersion} – supported HTTP protocol versions</li>
 *     <li>{@link io.github.amayaframework.http.HttpCode} – predefined HTTP status codes</li>
 *     <li>{@link io.github.amayaframework.http.MimeType} – commonly used MIME types</li>
 * </ul>
 *
 * <p>
 * This module is implementation-agnostic and forms the foundation for
 * more advanced HTTP handling layers in the framework.
 * </p>
 */
module amayaframework.http {
    // Exports
    exports io.github.amayaframework.http;
}
