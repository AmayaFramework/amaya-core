package io.github.amayaframework.core.actions;

/**
 * Class with constants describing the list of default input processing stages.
 */
public final class InputStage {
    public static final String PARSE_REQUEST = "ParseRequestAction";
    public static final String PARSE_REQUEST_BODY = "ParseRequestBodyAction";
    public static final String PARSE_REQUEST_COOKIES = "ParseRequestCookiesAction";
    public static final String INVOKE_CONTROLLER = "InvokeControllerAction";
}
