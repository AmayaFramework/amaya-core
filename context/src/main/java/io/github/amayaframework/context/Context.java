package io.github.amayaframework.context;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

/**
 *
 */
public interface Context {

    /**
     * @return
     */
    Request getRequest();

    /**
     * @return
     */
    ServletRequest getServletRequest();

    /**
     * @return
     */
    Response getResponse();

    /**
     * @return
     */
    ServletResponse getServletResponse();
}
