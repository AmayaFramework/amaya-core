package io.github.amayaframework.context;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 */
public interface HttpContext extends Context {

    /**
     * @return
     */
    @Override
    HttpRequest getRequest();

    /**
     * @return
     */
    @Override
    HttpServletRequest getServletRequest();

    /**
     * @return
     */
    @Override
    HttpResponse getResponse();

    /**
     * @return
     */
    @Override
    HttpServletResponse getServletResponse();
}
