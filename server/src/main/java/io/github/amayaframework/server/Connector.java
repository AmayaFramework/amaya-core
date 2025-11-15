package io.github.amayaframework.server;

import java.net.InetSocketAddress;

/**
 * TODO
 */
public interface Connector {

    // слушает?

    /**
     * TODO
     * @return
     */
    boolean listening();

    // жив или вообще удален из сервера?

    /**
     * TODO
     * @return
     */
    boolean alive();

    // хост

    /**
     * TODO
     * @return
     */
    String host();

    // порт

    /**
     * TODO
     * @return
     */
    int port();

    // локальный порт (либо рандомный, если был 0, либо == port)

    /**
     * TODO
     * @return
     */
    int localPort();

    // актуальный локальный адрес (host + localPort)

    /**
     * TODO
     * @return
     */
    InetSocketAddress address();
}
