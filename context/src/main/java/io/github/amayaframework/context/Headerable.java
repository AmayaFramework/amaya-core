package io.github.amayaframework.context;

import java.util.Map;

/**
 *
 */
public interface Headerable {

    /**
     * @return
     */
    Map<String, String> getHeaders();

    /**
     * @param name
     * @return
     */
    boolean containsHeader(String name);

    /**
     * @param name
     * @return
     */
    String getHeader(String name);
}
