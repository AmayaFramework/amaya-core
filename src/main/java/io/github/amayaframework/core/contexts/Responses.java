package io.github.amayaframework.core.contexts;

import com.github.romanqed.jutils.http.HeaderMap;
import com.github.romanqed.jutils.http.HttpCode;

import java.net.URL;
import java.util.Objects;

/**
 * <p>A class designed to make easier the creation of responses with various codes.</p>
 * <p>Methods that do not accept arguments will return a response with an empty body.</p>
 */
public final class Responses {
    private static final String LOCATION = "Location";

    public static HttpResponse response(HttpCode code, HeaderMap headers) {
        return new HttpResponseImpl(code, headers);
    }

    public static HttpResponse response(HttpCode code) {
        return new HttpResponseImpl(code);
    }

    public static HttpResponse response() {
        return new HttpResponseImpl();
    }

    public static HttpResponse responseWithCode(HttpCode code, Object body) {
        HttpResponse ret = new HttpResponseImpl(code);
        ret.setContentType(ContentType.PLAIN);
        ret.setBody(body);
        return ret;
    }

    public static HttpResponse ok(Object body) {
        return responseWithCode(HttpCode.OK, body);
    }

    public static HttpResponse ok() {
        return responseWithCode(HttpCode.OK, null);
    }

    public static HttpResponse created(Object body) {
        return responseWithCode(HttpCode.CREATED, body);
    }

    public static HttpResponse created() {
        return responseWithCode(HttpCode.CREATED, null);
    }

    public static HttpResponse accepted(Object body) {
        return responseWithCode(HttpCode.ACCEPTED, body);
    }

    public static HttpResponse accepted() {
        return responseWithCode(HttpCode.ACCEPTED, null);
    }

    public static HttpResponse badRequest(Object body) {
        return responseWithCode(HttpCode.BAD_REQUEST, body);
    }

    public static HttpResponse badRequest() {
        return responseWithCode(HttpCode.BAD_REQUEST, null);
    }

    public static HttpResponse unauthorized(Object body) {
        return responseWithCode(HttpCode.UNAUTHORIZED, body);
    }

    public static HttpResponse unauthorized() {
        return responseWithCode(HttpCode.UNAUTHORIZED, null);
    }

    public static HttpResponse forbidden(Object body) {
        return responseWithCode(HttpCode.FORBIDDEN, body);
    }

    public static HttpResponse forbidden() {
        return responseWithCode(HttpCode.FORBIDDEN, null);
    }

    public static HttpResponse notFound(Object body) {
        return responseWithCode(HttpCode.NOT_FOUND, body);
    }

    public static HttpResponse notFound() {
        return responseWithCode(HttpCode.NOT_FOUND, null);
    }

    public static HttpResponse serverError(Object body) {
        return responseWithCode(HttpCode.INTERNAL_SERVER_ERROR, body);
    }

    public static HttpResponse serverError() {
        return responseWithCode(HttpCode.INTERNAL_SERVER_ERROR, null);
    }

    public static HttpResponse badGateway(Object body) {
        return responseWithCode(HttpCode.BAD_GATEWAY, body);
    }

    public static HttpResponse badGateway() {
        return responseWithCode(HttpCode.BAD_GATEWAY, null);
    }

    private static HttpResponse redirect(HttpCode code, URL url) {
        Objects.requireNonNull(url);
        HttpResponse ret = responseWithCode(code, null);
        ret.setHeader(LOCATION, url.toString());
        return ret;
    }

    public static HttpResponse movedPermanently(URL url) {
        return redirect(HttpCode.MOVED_PERMANENTLY, url);
    }

    public static HttpResponse found(URL url) {
        return redirect(HttpCode.FOUND, url);
    }

    public static HttpResponse seeOther(URL url) {
        return redirect(HttpCode.SEE_OTHER, url);
    }

    public static HttpResponse useProxy(URL url) {
        return redirect(HttpCode.USE_PROXY, url);
    }

    public static HttpResponse temporaryRedirect(URL url) {
        return redirect(HttpCode.TEMPORARY_REDIRECT, url);
    }

    public static HttpResponse permanentRedirect(URL url) {
        return redirect(HttpCode.PERMANENT_REDIRECT, url);
    }
}
