package io.github.amayaframework.server;

import java.net.InetSocketAddress;
import java.util.Set;

/**
 * An interface describing the server configuration.
 * Allows managing the set of addresses the server will listen on.
 * Changes to the addresses are reflected dynamically on the running server if supported.
 */
public interface ServerConfig {

    /**
     * Returns a mutable {@link Set} containing all currently listened addresses.
     * Modifications to this set (adding or removing addresses) will dynamically
     * update the server's listening state for those addresses.
     *
     * @return a mutable set of all addresses the server listens on
     */
    Set<InetSocketAddress> addresses();

    /**
     * Adds the specified address to the set of addresses to listen on.
     * If the server is running, it should begin listening on this address immediately if supported.
     *
     * @param address the address to add; must not be {@code null}
     * @throws IllegalArgumentException if {@code address} is {@code null}
     */
    void addAddress(InetSocketAddress address);

    /**
     * Removes the specified address from the set of addresses the server listens on.
     * If the server is listening on this address, it should stop listening immediately if supported.
     *
     * @param address the address to remove; must not be {@code null}
     * @throws IllegalArgumentException if {@code address} is {@code null}
     */
    void removeAddress(InetSocketAddress address);
}
