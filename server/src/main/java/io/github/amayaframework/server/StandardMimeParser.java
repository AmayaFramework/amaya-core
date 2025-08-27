package io.github.amayaframework.server;

import io.github.amayaframework.http.MimeData;
import io.github.amayaframework.http.MimeType;
import io.github.amayaframework.tokenize.Tokenizer;
import io.github.amayaframework.tokenize.Tokenizers;

import java.util.HashMap;
import java.util.Map;

/**
 * Default implementation of {@link MimeParser}.
 * <p>
 * Parses raw MIME type strings such as
 * {@code "text/html; charset=UTF-8"} into {@link MimeData}
 * objects. The parser is <em>tolerant</em>: it trims optional
 * whitespace, accepts quoted parameter values, and unescapes
 * simple {@code \"} sequences. However, it does not enforce
 * strict RFC token validation and ignores malformed or empty
 * parameters instead of failing.
 * </p>
 * <p>
 * This makes it efficient and suitable for high-load scenarios
 * where valid input is expected, while still being robust against
 * common deviations from the standard.
 * </p>
 */
public final class StandardMimeParser implements MimeParser {
    private final Tokenizer tokenizer;

    /**
     * Creates a new parser using the given tokenizer to split
     * parameters.
     *
     * @param tokenizer the tokenizer to use, must not be {@code null}
     */
    public StandardMimeParser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    /**
     * Creates a new parser using the default
     * {@link Tokenizers#PLAIN_TOKENIZER}.
     */
    public StandardMimeParser() {
        this.tokenizer = Tokenizers.PLAIN_TOKENIZER;
    }

    private static MimeType parseType(String type) {
        var prefound = MimeType.lookup(type);
        if (prefound != null) {
            return prefound;
        }
        var slash = type.indexOf('/');
        var length = type.length();
        if (slash < 0 || slash == length - 1) {
            throw new IllegalMimeTypeException(type);
        }
        var group = StringUtil.trim(0, slash, type);
        var subtype = StringUtil.trim(slash + 1, length, type);
        if (group == null || subtype == null) {
            throw new IllegalMimeTypeException(type);
        }
        return new MimeType(group, subtype, null);
    }

    private static void parseParam(String param, Map<String, String> params) {
        var eq = param.indexOf('=');
        if (eq < 0) {
            params.put(param.trim(), null);
            return;
        }
        var key = StringUtil.trim(0, eq, param);
        if (key == null) {
            return;
        }
        var value = StringUtil.trimQuote(eq + 1, param.length(), param);
        if (value == null) {
            params.put(key, null);
        } else {
            params.put(key, value.replace("\\\"", "\""));
        }
    }

    @Override
    public MimeData read(String data) {
        var split = tokenizer.tokenize(data, ";").iterator();
        var type = parseType(split.next());
        if (!split.hasNext()) {
            return new MimeData(type);
        }
        var params = new HashMap<String, String>(2);
        while (split.hasNext()) {
            var param = split.next();
            if (param.isEmpty()) {
                continue;
            }
            parseParam(param, params);
        }
        return new MimeData(type, params);
    }
}
