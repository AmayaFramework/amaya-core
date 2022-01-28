package io.github.amayaframework.core.pipelines;

/**
 * Enum describing the list of default request and response processing stages.
 */
public enum Stage {
    FIND_ROUTE,
    PARSE_REQUEST,
    PARSE_REQUEST_BODY,
    PARSE_REQUEST_COOKIES,
    INVOKE_CONTROLLER,
    CHECK_RESPONSE,
    PARSE_RESPONSE_COOKIES
}
