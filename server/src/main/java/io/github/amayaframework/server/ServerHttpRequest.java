package io.github.amayaframework.server;

import io.github.amayaframework.context.AbstractHttpRequest;
import io.github.amayaframework.http.HttpVersion;
import io.github.amayaframework.http.MimeData;
import jakarta.servlet.http.HttpServletRequest;

import java.util.*;

/**
 * Servlet-backed implementation of {@link AbstractHttpRequest}.
 * <p>
 * Wraps a {@link HttpServletRequest} and provides efficient parsing of
 * path segments, query parameters, and MIME data using pluggable
 * buffer and parser components.
 * <p>
 * This class is designed for high-load scenarios: it avoids regular
 * expressions, minimizes allocations, and relies on tokenizer-based
 * parsing for request paths and query strings.
 */
public abstract class ServerHttpRequest extends AbstractHttpRequest {
    protected final PathTokenizer tokenizer;
    protected final MimeParser parser;
    protected Map<String, Object> pathParams;

    /**
     * Create a new {@code ServerHttpRequest}.
     *
     * @param request   the underlying servlet request
     * @param version   the resolved HTTP version
     * @param tokenizer the tokenizer for splitting request paths
     * @param parser    the parser for {@link MimeData}
     */
    public ServerHttpRequest(HttpServletRequest request,
                             HttpVersion version,
                             PathTokenizer tokenizer,
                             MimeParser parser) {
        super(request, version);
        this.tokenizer = tokenizer;
        this.parser = parser;
    }

    @Override
    protected List<String> splitPath(String path) {
        return Collections.unmodifiableList(tokenizer.tokenize(path));
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Map<String, List<Object>> collectQueries() {
        var query = request.getQueryString();
        if (query == null) {
            return Collections.EMPTY_MAP;
        }
        var ret = new HashMap<String, List<Object>>();
        var tokenizer = new StringTokenizer(query, "&");
        while (tokenizer.hasMoreTokens()) {
            var token = tokenizer.nextToken();
            // Try to locate '=' index
            var index = token.indexOf('=');
            // If not found, then save it as key-only parameter
            if (index < 0) {
                ret.computeIfAbsent(token.trim(), k -> new ArrayList<>());
                continue;
            }
            // If found, then save it as key-value parameter
            var key = StringUtil.trim(0, index, token);
            var value = token.substring(index + 1);
            ret.computeIfAbsent(key == null ? "" : key, k -> new ArrayList<>()).add(value);
        }
        return ret;
    }

    @Override
    protected MimeData parseMimeData(String data) {
        return parser.read(data);
    }

    @Override
    public Map<String, Object> pathParams() {
        if (pathParams == null) {
            pathParams = new HashMap<>();
        }
        return pathParams;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T pathParam(String name) {
        if (pathParams == null) {
            return null;
        }
        return (T) pathParams.get(name);
    }
}
