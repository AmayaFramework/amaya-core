package io.github.amayaframework.server;

import java.net.InetSocketAddress;
import java.util.Set;

/**
 * An interface describing the server config. Allows to set the addresses that the server will listen to.
 */
public interface ServerConfig {

    /**
     * Gets the {@link Set} instance containing all listened addresses.
     *
     * @return the {@link Set} instance containing all listened addresses
     */
    Set<InetSocketAddress> getAddresses();

    /**
     * Adds given address to listened set and starts listen it.
     *
     * @param address the specified address to be listened, must be non-null
     */
    void addAddress(InetSocketAddress address);

    /**
     * Removes given address from listened set and stops listen it.
     *
     * @param address the specified address to be removed, must be non-null
     */
    void removeAddress(InetSocketAddress address);
}
