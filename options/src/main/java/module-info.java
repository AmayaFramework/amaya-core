/**
 * Provides abstractions and utilities for working with structured option sets.
 * <p>
 * This module defines the main interfaces for handling key-value pairs grouped by name,
 * including flat option sets ({@link io.github.amayaframework.options.OptionSet}) and grouped sets
 * ({@link io.github.amayaframework.options.GroupOptionSet}).
 * </p>
 *
 * <h2>Key Features</h2>
 * <ul>
 *     <li>Unified API for accessing and modifying configuration-like data structures</li>
 *     <li>Support for grouping options into named collections</li>
 *     <li>Utility classes for iteration and functional access patterns</li>
 *     <li>Compatibility with functional interfaces from <b>jfunc</b></li>
 * </ul>
 *
 * <h2>Use Cases</h2>
 * <ul>
 *     <li>Hierarchical configuration management</li>
 *     <li>Modular option injection in framework components</li>
 *     <li>Runtime option manipulation and analysis</li>
 * </ul>
 *
 * <h2>Exported Packages</h2>
 * <ul>
 *     <li>{@link io.github.amayaframework.options} — contains all public types for working with option sets</li>
 * </ul>
 */
module amayaframework.options {
    // Imports
    requires com.github.romanqed.jfunc;
    requires com.github.romanqed.jtype;
    // Exports
    exports io.github.amayaframework.options;
}
