package io.github.amayaframework.context;

import jakarta.servlet.ServletInputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Locale;
import java.util.Map;

/**
 *
 */
public interface Request extends Transaction, Attributable<String> {

    /**
     * @return
     * @throws IOException
     */
    ServletInputStream getInputStream() throws IOException;

    /**
     * @return
     * @throws IOException
     */
    BufferedReader getReader() throws IOException;

    /**
     * @return
     */
    InetSocketAddress getLocalAddress();

    /**
     * @return
     */
    String getLocalHost();

    /**
     * @return
     */
    InetSocketAddress getRemoteAddress();

    /**
     * @return
     */
    String getRemoteHost();

    /**
     * @return
     */
    Map<String, String[]> getParameterValues();

    /**
     * @param name
     * @return
     */
    boolean containsParameter(String name);

    /**
     * @param name
     * @return
     */
    String getParameter(String name);

    /**
     * @param name
     * @return
     */
    String[] getParameterValues(String name);

    /**
     * @return
     */
    Iterable<Locale> getLocales();
}
