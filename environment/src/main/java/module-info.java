/**
 * Provides core abstractions for working with mounted or virtual environments.
 * <p>
 * This module defines the {@link io.github.amayaframework.environment.Environment} interface and related
 * factory interfaces and options to support file-based or virtual environments with pluggable configuration.
 * </p>
 *
 * <p>Dependencies:
 * <ul>
 *     <li>{@link io.github.amayaframework.options.OptionSet} for environment configuration</li>
 * </ul>
 * </p>
 */
module amayaframework.environment {
    // Imports
    requires amayaframework.options;
    // Exports
    exports io.github.amayaframework.environment;
}
