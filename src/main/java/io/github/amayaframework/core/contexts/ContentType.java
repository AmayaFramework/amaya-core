package io.github.amayaframework.core.contexts;

import java.util.*;

public enum ContentType {
    // Application
    ATOM_XML("application", "atom+xml"),
    EDI_X12("application", "EDI-X12"),
    EDIFACT("application", "EDIFACT"),
    JSON("application", "json", true),
    APPLICATION_JAVASCRIPT("application", "javascript", true),
    OCTET_STREAM("application", "octet-stream"),
    APPLICATION_OGG("application", "ogg"),
    PDF("application", "pdf"),
    POSTSCRIPT("application", "postscript", true),
    SOAP_XML("application", "soap+xml", true),
    FONT_WOFF("application", "font-woff"),
    XHTML_XML("application", "xhtml+xml", true),
    XML_DTD("application", "xml-dtd", true),
    XOP_XML("application", "xop+xml", true),
    ZIP("application", "zip"),
    GZIP("application", "gzip"),
    X_BITTORRENT("application", "x-bittorrent"),
    X_TEX("application", "x-tex"),
    APPLICATION_XML("application", "xml", true),
    DOC("application", "msword"),
    // Audio
    BASIC("audio", "basic"),
    L24("audio", "L24"),
    AUDIO_MP4("audio", "mp4"),
    AAC("audio", "aac"),
    MP3_MPEG("audio", "mpeg"),
    AUDIO_OGG("audio", "ogg"),
    VORBIS("audio", "vorbis"),
    X_MS_WMA("audio", "x-ms-wma"),
    X_MS_WAX("audio", "x-ms-wax"),
    REAL_AUDIO("audio", "vnd.rn-realaudio"),
    WAV("audio", "vnd.wave"),
    AUDIO_WEBM("audio", "webm"),
    // Image
    GIF("image", "gif"),
    JPEG("image", "jpeg"),
    P_JPEG("image", "pjpeg"),
    PNG("image", "png"),
    SVG_XML("image", "svg+xml", true),
    TIFF("image", "tiff"),
    ICO("image", "vnd.microsoft.icon"),
    WBMP("image", "vnd.wap.wbmp"),
    WEBP("image", "webp"),
    // Message
    HTTP("message", "http", true),
    IMDN_XML("message", "imdn+xml", true),
    PARTIAL("message", "partial"),
    E_MAIL_RFC_822("message", "rfc822", true),
    // Multipart
    MIXED("multipart", "mixed"),
    ALTERNATIVE("multipart", "alternative"),
    RELATED("multipart", "related"),
    FORM_DATA("multipart", "form-data"),
    SIGNED("multipart", "signed"),
    ENCRYPTED("multipart", "encrypted"),
    // Text
    CMD("text", "cmd", true),
    CSS("text", "css", true),
    CSV("text", "csv", true),
    HTML("text", "html", true),
    JAVASCRIPT("text", "javascript", true),
    PLAIN("text", "plain", true),
    PHP("text", "php", true),
    XML("text", "xml", true),
    MARKDOWN("text", "markdown", true),
    CACHE_MANIFEST("text", "cache-manifest", true),
    // Video
    MPEG("video", "mpeg"),
    MP4("video", "mp4"),
    OGG("video", "ogg"),
    QUICKTIME("video", "quicktime"),
    WEBM("video", "webm"),
    X_MS_WMV("video", "x-ms-wmv"),
    X_FLV("video", "x-flv"),
    X_MS_VIDEO("video", "x-msvideo"),
    THREE_GPP("video", "3gpp"),
    THREE_GPP_2("video", "3gpp2");


    private static final Map<String, ContentType> children = toMap();
    private final String group;
    private final String name;
    private final boolean string;

    ContentType(String group, String name, boolean string) {
        this.group = Objects.requireNonNull(group);
        this.name = Objects.requireNonNull(name);
        this.string = string;
    }

    ContentType(String group, String name) {
        this(group, name, false);
    }

    private static Map<String, ContentType> toMap() {
        Map<String, ContentType> ret = new HashMap<>();
        for (ContentType contentType : ContentType.values()) {
            ret.put(contentType.group + "/" + contentType.name, contentType);
        }
        return Collections.unmodifiableMap(ret);
    }

    public static ContentType fromHeader(String header) {
        Objects.requireNonNull(header);
        header = header.toLowerCase(Locale.ROOT);
        return children.get(header);
    }

    public String getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }

    public String getHeader() {
        return group + "/" + name;
    }

    public boolean isString() {
        return string;
    }
}
