package io.github.amayaframework.core.configurators;

/**
 * An enumeration containing the available access policies
 */
public enum AccessPolicy {
    /**
     * A policy that provides direct and full access to the contents of the controller pipeline.
     */
    DIRECT,

    /**
     * A policy that provides a clean pipeline for filling and subsequent controlled insertion
     * into the controller pipeline
     */
    INDIRECT
}
