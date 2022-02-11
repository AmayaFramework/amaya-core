package io.github.amayaframework.core.pipelines;

/**
 * Enum describing the list of default request and response processing stages.
 */
public class InputStage {
    public static final String PARSE_REQUEST = "ParseRequestAction";
    public static final String PARSE_REQUEST_BODY = "ParseRequestBodyAction";
    public static final String PARSE_REQUEST_COOKIES = "ParseRequestCookiesAction";
}
