package io.github.amayaframework.core.context;

import java.util.Objects;

public final class MIMEType {

    // Application
    public static final MIMEType ATOM_XML = of("application", "atom+xml");
    public static final MIMEType EDI_X12 = of("application", "EDI-X12");
    public static final MIMEType EDIFACT = of("application", "EDIFACT");
    public static final MIMEType JSON = of("application", "json", true);
    public static final MIMEType APPLICATION_JAVASCRIPT = of("application", "javascript", true);
    public static final MIMEType OCTET_STREAM = of("application", "octet-stream");
    public static final MIMEType APPLICATION_OGG = of("application", "ogg");
    public static final MIMEType PDF = of("application", "pdf");
    public static final MIMEType POSTSCRIPT = of("application", "postscript", true);
    public static final MIMEType SOAP_XML = of("application", "soap+xml", true);
    public static final MIMEType FONT_WOFF = of("application", "font-woff");
    public static final MIMEType XHTML_XML = of("application", "xhtml+xml", true);
    public static final MIMEType XML_DTD = of("application", "xml-dtd", true);
    public static final MIMEType XOP_XML = of("application", "xop+xml", true);
    public static final MIMEType ZIP = of("application", "zip");
    public static final MIMEType GZIP = of("application", "gzip");
    public static final MIMEType X_BITTORRENT = of("application", "x-bittorrent");
    public static final MIMEType X_TEX = of("application", "x-tex");
    public static final MIMEType APPLICATION_XML = of("application", "xml", true);
    public static final MIMEType DOC = of("application", "msword");
    public static final MIMEType FORM_URLENCODED = of("application", "x-www-form-urlencoded", true);

    // Audio
    public static final MIMEType BASIC = of("audio", "basic");
    public static final MIMEType L24 = of("audio", "L24");
    public static final MIMEType AUDIO_MP4 = of("audio", "mp4");
    public static final MIMEType AAC = of("audio", "aac");
    public static final MIMEType MP3_MPEG = of("audio", "mpeg");
    public static final MIMEType AUDIO_OGG = of("audio", "ogg");
    public static final MIMEType VORBIS = of("audio", "vorbis");
    public static final MIMEType X_MS_WMA = of("audio", "x-ms-wma");
    public static final MIMEType X_MS_WAX = of("audio", "x-ms-wax");
    public static final MIMEType REAL_AUDIO = of("audio", "vnd.rn-realaudio");
    public static final MIMEType WAV = of("audio", "vnd.wave");
    public static final MIMEType AUDIO_WEBM = of("audio", "webm");

    // Image
    public static final MIMEType GIF = of("image", "gif");
    public static final MIMEType JPEG = of("image", "jpeg");
    public static final MIMEType PJPEG = of("image", "pjpeg");
    public static final MIMEType PNG = of("image", "png");
    public static final MIMEType SVG_XML = of("image", "svg+xml", true);
    public static final MIMEType TIFF = of("image", "tiff");
    public static final MIMEType ICO = of("image", "vnd.microsoft.icon");
    public static final MIMEType WBMP = of("image", "vnd.wap.wbmp");
    public static final MIMEType WEBP = of("image", "webp");

    // Message
    public static final MIMEType HTTP = of("message", "http", true);
    public static final MIMEType IMDN_XML = of("message", "imdn+xml", true);
    public static final MIMEType PARTIAL = of("message", "partial");
    public static final MIMEType E_MAIL_RFC_822 = of("message", "rfc822", true);

    // Multipart
    public static final MIMEType MIXED = of("multipart", "mixed");
    public static final MIMEType ALTERNATIVE = of("multipart", "alternative");
    public static final MIMEType RELATED = of("multipart", "related");
    public static final MIMEType FORM_DATA = of("multipart", "form-data");
    public static final MIMEType SIGNED = of("multipart", "signed");
    public static final MIMEType ENCRYPTED = of("multipart", "encrypted");

    // Text
    public static final MIMEType CMD = of("text", "cmd", true);
    public static final MIMEType CSS = of("text", "css", true);
    public static final MIMEType CSV = of("text", "csv", true);
    public static final MIMEType HTML = of("text", "html", true);
    public static final MIMEType JAVASCRIPT = of("text", "javascript", true);
    public static final MIMEType PLAIN = of("text", "plain", true);
    public static final MIMEType PHP = of("text", "php", true);
    public static final MIMEType XML = of("text", "xml", true);
    public static final MIMEType MARKDOWN = of("text", "markdown", true);
    public static final MIMEType CACHE_MANIFEST = of("text", "cache-manifest", true);

    // Video
    public static final MIMEType MPEG = of("video", "mpeg");
    public static final MIMEType MP4 = of("video", "mp4");
    public static final MIMEType OGG = of("video", "ogg");
    public static final MIMEType QUICKTIME = of("video", "quicktime");
    public static final MIMEType WEBM = of("video", "webm");
    public static final MIMEType X_MS_WMV = of("video", "x-ms-wmv");
    public static final MIMEType X_FLV = of("video", "x-flv");
    public static final MIMEType X_MS_VIDEO = of("video", "x-msvideo");
    public static final MIMEType THREE_GPP = of("video", "3gpp");
    public static final MIMEType THREE_GPP_2 = of("video", "3gpp2");

    private final String group;
    private final String name;
    private final boolean text;

    public MIMEType(String group, String name, boolean text) {
        this.group = Objects.requireNonNull(group);
        this.name = Objects.requireNonNull(name);
        this.text = text;
    }

    public MIMEType(String group, String name) {
        this(group, name, false);
    }

    public static MIMEType of(String group, String name, boolean text) {
        return new MIMEType(group, name, text);
    }

    public static MIMEType of(String group, String name) {
        return new MIMEType(group, name, false);
    }

    public String getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }

    public boolean isText() {
        return text;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        var type = (MIMEType) object;
        return group.equals(type.group) && name.equals(type.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, name);
    }

    @Override
    public String toString() {
        return "MIMEType{" +
                "group='" + group + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
