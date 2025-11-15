package io.github.amayaframework.server;

import io.github.amayaframework.http.HttpVersion;

/**
 * TODO
 */
public interface HttpConnector extends Connector {

    /**
     * TODO
     * @return
     */
    HttpVersion httpVersion();
}
