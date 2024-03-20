package io.github.amayaframework.core.context;

import java.io.InputStream;
import java.net.InetSocketAddress;

public interface Request extends Transaction {

    InputStream getInputStream();

    InetSocketAddress getLocalAddress();

    String getLocalHost();

    InetSocketAddress getRemoteAddress();

    String getRemoteHost();
}
