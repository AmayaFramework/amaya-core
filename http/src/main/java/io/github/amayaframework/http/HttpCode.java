package io.github.amayaframework.http;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A class that implements the information holder about the http code.
 * Implements {@link HttpDefinition}.
 */
public final class HttpCode implements HttpDefinition {
    // 1xx
    public static final HttpCode CONTINUE = of11(100, "Continue");
    public static final HttpCode SWITCHING_PROTOCOLS = of11(101, "Switching Protocols");
    public static final HttpCode PROCESSING = of11(102, "Processing");
    public static final HttpCode EARLY_HINTS = of11(103, "Early Hints");
    // 2xx
    public static final HttpCode OK = of1(200, "OK");
    public static final HttpCode CREATED = of1(201, "Created");
    public static final HttpCode ACCEPTED = of1(202, "Accepted");
    public static final HttpCode NON_AUTHORITATIVE_INFORMATION =
            of11(203, "Non-Authoritative Information");
    public static final HttpCode NO_CONTENT = of1(204, "No Content");
    public static final HttpCode RESET_CONTENT = of11(205, "Reset Content");
    public static final HttpCode PARTIAL_CONTENT = of11(206, "Partial Content");
    public static final HttpCode MULTI_STATUS = of11(207, "Multi-Status");
    public static final HttpCode ALREADY_REPORTED = of11(208, "Already Reported");
    public static final HttpCode IM_USED = of11(226, "IM Used");
    // 3xx
    public static final HttpCode MULTIPLE_CHOICES = of1(300, "Multiple Choices");
    public static final HttpCode MOVED_PERMANENTLY = of1(301, "Moved Permanently");
    public static final HttpCode FOUND = of1(302, "Found");
    public static final HttpCode SEE_OTHER = of11(303, "See Other");
    public static final HttpCode NOT_MODIFIED = of1(304, "Not Modified");
    public static final HttpCode USE_PROXY = of11(305, "Use Proxy");
    public static final HttpCode SWITCH_PROXY = of11(306, "Switch Proxy");
    public static final HttpCode TEMPORARY_REDIRECT = of11(307, "Temporary Redirect");
    public static final HttpCode PERMANENT_REDIRECT = of11(308, "Permanent Redirect");
    // 4xx
    public static final HttpCode BAD_REQUEST = of1(400, "Bad Request");
    public static final HttpCode UNAUTHORIZED = of1(401, "Unauthorized");
    public static final HttpCode PAYMENT_REQUIRED = of11(402, "Payment Required");
    public static final HttpCode FORBIDDEN = of1(403, "Forbidden");
    public static final HttpCode NOT_FOUND = of1(404, "Not Found");
    public static final HttpCode METHOD_NOT_ALLOWED = of11(405, "Method Not Allowed");
    public static final HttpCode NOT_ACCEPTABLE = of11(406, "Not Acceptable");
    public static final HttpCode PROXY_AUTHENTICATION_REQUIRED =
            of11(407, "Proxy Authentication Required");
    public static final HttpCode REQUEST_TIMEOUT = of11(408, "Request Timeout");
    public static final HttpCode CONFLICT = of11(409, "Conflict");
    public static final HttpCode GONE = of11(410, "Gone");
    public static final HttpCode LENGTH_REQUIRED = of11(411, "Length Required");
    public static final HttpCode PRECONDITION_FAILED = of11(412, "Precondition Failed");
    public static final HttpCode PAYLOAD_TOO_LARGE = of11(413, "Payload Too Large");
    public static final HttpCode URI_TOO_LONG = of11(414, "URI Too Long");
    public static final HttpCode UNSUPPORTED_MEDIA_TYPE = of11(415, "Unsupported Media Type");
    public static final HttpCode RANGE_NOT_SATISFIABLE = of11(416, "Range Not Satisfiable");
    public static final HttpCode EXPECTATION_FAILED = of11(417, "Expectation Failed");
    public static final HttpCode I_AM_A_TEAPOT = of11(418, "I'm a teapot");
    public static final HttpCode MISDIRECTED_REQUEST = of11(421, "Misdirected Request");
    public static final HttpCode UNPROCESSABLE_ENTITY = of11(422, "Unprocessable Entity");
    public static final HttpCode LOCKED = of11(423, "Locked");
    public static final HttpCode FAILED_DEPENDENCY = of11(424, "Failed Dependency");
    public static final HttpCode TOO_EARLY = of11(425, "Too Early");
    public static final HttpCode UPGRADE_REQUIRED = of11(426, "Upgrade Required");
    public static final HttpCode PRECONDITION_REQUIRED = of11(428, "Precondition Required");
    public static final HttpCode TOO_MANY_REQUESTS = of11(429, "Too Many Requests");
    public static final HttpCode REQUEST_HEADER_FIELDS_TOO_LARGE =
            of11(431, "Request Header Fields Too Large");
    public static final HttpCode UNAVAILABLE_FOR_LEGAL_REASONS =
            of11(451, "Unavailable For Legal Reasons");
    // 5xx
    public static final HttpCode INTERNAL_SERVER_ERROR = of1(500, "Internal Server Error");
    public static final HttpCode NOT_IMPLEMENTED = of1(501, "Not Implemented");
    public static final HttpCode BAD_GATEWAY = of1(502, "Bad Gateway");
    public static final HttpCode SERVICE_UNAVAILABLE = of1(503, "Service Unavailable");
    public static final HttpCode GATEWAY_TIMEOUT = of11(504, "Gateway Timeout");
    public static final HttpCode HTTP_VERSION_NOT_SUPPORTED = of11(505, "HTTP Version Not Supported");
    public static final HttpCode VARIANT_ALSO_NEGOTIATES = of11(506, "Variant Also Negotiates");
    public static final HttpCode INSUFFICIENT_STORAGE = of11(507, "Insufficient Storage");
    public static final HttpCode LOOP_DETECTED = of11(508, "Loop Detected");
    public static final HttpCode NOT_EXTENDED = of11(510, "Not Extended");
    public static final HttpCode NETWORK_AUTHENTICATION_REQUIRED =
            of11(511, "Network Authentication Required");

    private static final Map<Integer, HttpCode> CODES = getCodes();

    final int code;
    final String description;
    final HttpVersion since;

    /**
     * Constructs {@link HttpCode} instance with given code number, description and http version defining this code.
     *
     * @param code        the specified http code number
     * @param description the specified http code description
     * @param since       the http version defining this code
     */
    public HttpCode(int code, String description, HttpVersion since) {
        this.code = code;
        this.description = description;
        this.since = since;
    }

    /**
     * Searches among predefined methods for the method with the specified number.
     *
     * @param code the specified number of http code
     * @return {@link HttpCode} instance if found, null otherwise
     */
    public static HttpCode of(int code) {
        return CODES.get(code);
    }

    /**
     * Returns {@link Map} instance containing all predefined http codes.
     *
     * @return an unmodifiable {@link Map} instance
     */
    public static Map<Integer, HttpCode> all() {
        return CODES;
    }

    private static HttpCode of1(int code, String description) {
        return new HttpCode(code, description, HttpVersion.HTTP_1_0);
    }

    private static HttpCode of11(int code, String description) {
        return new HttpCode(code, description, HttpVersion.HTTP_1_1);
    }

    private static Map<Integer, HttpCode> getCodes() {
        var ret = new HashMap<Integer, HttpCode>();
        ret.put(100, CONTINUE);
        ret.put(101, SWITCHING_PROTOCOLS);
        ret.put(102, PROCESSING);
        ret.put(103, EARLY_HINTS);
        ret.put(200, OK);
        ret.put(201, CREATED);
        ret.put(202, ACCEPTED);
        ret.put(203, NON_AUTHORITATIVE_INFORMATION);
        ret.put(204, NO_CONTENT);
        ret.put(205, RESET_CONTENT);
        ret.put(206, PARTIAL_CONTENT);
        ret.put(207, MULTI_STATUS);
        ret.put(208, ALREADY_REPORTED);
        ret.put(226, IM_USED);
        ret.put(300, MULTIPLE_CHOICES);
        ret.put(301, MOVED_PERMANENTLY);
        ret.put(302, FOUND);
        ret.put(303, SEE_OTHER);
        ret.put(304, NOT_MODIFIED);
        ret.put(305, USE_PROXY);
        ret.put(306, SWITCH_PROXY);
        ret.put(307, TEMPORARY_REDIRECT);
        ret.put(308, PERMANENT_REDIRECT);
        ret.put(400, BAD_REQUEST);
        ret.put(401, UNAUTHORIZED);
        ret.put(402, PAYMENT_REQUIRED);
        ret.put(403, FORBIDDEN);
        ret.put(404, NOT_FOUND);
        ret.put(405, METHOD_NOT_ALLOWED);
        ret.put(406, NOT_ACCEPTABLE);
        ret.put(407, PROXY_AUTHENTICATION_REQUIRED);
        ret.put(408, REQUEST_TIMEOUT);
        ret.put(409, CONFLICT);
        ret.put(410, GONE);
        ret.put(411, LENGTH_REQUIRED);
        ret.put(412, PRECONDITION_FAILED);
        ret.put(413, PAYLOAD_TOO_LARGE);
        ret.put(414, URI_TOO_LONG);
        ret.put(415, UNSUPPORTED_MEDIA_TYPE);
        ret.put(416, RANGE_NOT_SATISFIABLE);
        ret.put(417, EXPECTATION_FAILED);
        ret.put(418, I_AM_A_TEAPOT);
        ret.put(421, MISDIRECTED_REQUEST);
        ret.put(422, UNPROCESSABLE_ENTITY);
        ret.put(423, LOCKED);
        ret.put(424, FAILED_DEPENDENCY);
        ret.put(425, TOO_EARLY);
        ret.put(426, UPGRADE_REQUIRED);
        ret.put(428, PRECONDITION_REQUIRED);
        ret.put(429, TOO_MANY_REQUESTS);
        ret.put(431, REQUEST_HEADER_FIELDS_TOO_LARGE);
        ret.put(451, UNAVAILABLE_FOR_LEGAL_REASONS);
        ret.put(500, INTERNAL_SERVER_ERROR);
        ret.put(501, NOT_IMPLEMENTED);
        ret.put(502, BAD_GATEWAY);
        ret.put(503, SERVICE_UNAVAILABLE);
        ret.put(504, GATEWAY_TIMEOUT);
        ret.put(505, HTTP_VERSION_NOT_SUPPORTED);
        ret.put(506, VARIANT_ALSO_NEGOTIATES);
        ret.put(507, INSUFFICIENT_STORAGE);
        ret.put(508, LOOP_DETECTED);
        ret.put(510, NOT_EXTENDED);
        ret.put(511, NETWORK_AUTHENTICATION_REQUIRED);
        return Collections.unmodifiableMap(ret);
    }

    /**
     * Gets number of this http code.
     *
     * @return number of http code
     */
    public int getCode() {
        return code;
    }

    /**
     * Gets description of this http code.
     *
     * @return description of http code
     */
    public String getDescription() {
        return description;
    }

    @Override
    public HttpVersion since() {
        return since;
    }

    @Override
    public boolean isSupported(HttpVersion version) {
        return version.number >= since.number;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        var httpCode = (HttpCode) object;
        return code == httpCode.code;
    }

    @Override
    public int hashCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + " " + description;
    }
}
