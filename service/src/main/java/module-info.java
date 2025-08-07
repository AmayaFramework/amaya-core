/**
 * <p>This module provides interfaces and base implementations for managing
 * the lifecycle of services within applications.</p>
 *
 * <h2>Key Features</h2>
 * <ul>
 *   <li><b>ServiceManager</b> interface for managing collections of services.</li>
 *   <li><b>AbstractServiceManager</b> base class supporting start, stop,
 *       removal, and error handling of managed services.</li>
 * </ul>
 *
 * <h2>Dependencies</h2>
 * <ul>
 *   <li><code>com.github.romanqed.jfunc</code> — functional interfaces and utilities.</li>
 *   <li><code>com.github.romanqed.jct</code> — cancellation management and cancel tokens.</li>
 * </ul>
 */
module amayaframework.service {
    // Imports
    requires com.github.romanqed.jfunc;
    requires com.github.romanqed.jct;
    // Exports
    exports io.github.amayaframework.service;
}
