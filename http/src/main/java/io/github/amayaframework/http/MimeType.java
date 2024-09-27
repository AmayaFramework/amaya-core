package io.github.amayaframework.http;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class MimeType {

    // Application
    public static final MimeType ATOM_XML = new MimeType("application", "atom+xml");
    public static final MimeType EDI_X12 = new MimeType("application", "EDI-X12");
    public static final MimeType EDIFACT = new MimeType("application", "EDIFACT");
    public static final MimeType JSON = new MimeType("application", "json", true);
    public static final MimeType APPLICATION_JAVASCRIPT
            = new MimeType("application", "javascript", true);
    public static final MimeType OCTET_STREAM = new MimeType("application", "octet-stream");
    public static final MimeType APPLICATION_OGG = new MimeType("application", "ogg");
    public static final MimeType PDF = new MimeType("application", "pdf");
    public static final MimeType POSTSCRIPT = new MimeType("application", "postscript", true);
    public static final MimeType SOAP_XML = new MimeType("application", "soap+xml", true);
    public static final MimeType FONT_WOFF = new MimeType("application", "font-woff");
    public static final MimeType XHTML_XML = new MimeType("application", "xhtml+xml", true);
    public static final MimeType XML_DTD = new MimeType("application", "xml-dtd", true);
    public static final MimeType XOP_XML = new MimeType("application", "xop+xml", true);
    public static final MimeType ZIP = new MimeType("application", "zip");
    public static final MimeType GZIP = new MimeType("application", "gzip");
    public static final MimeType X_BITTORRENT = new MimeType("application", "x-bittorrent");
    public static final MimeType X_TEX = new MimeType("application", "x-tex");
    public static final MimeType APPLICATION_XML = new MimeType("application", "xml", true);
    public static final MimeType DOC = new MimeType("application", "msword");
    public static final MimeType FORM_URLENCODED
            = new MimeType("application", "x-www-form-urlencoded", true);

    // Audio
    public static final MimeType BASIC = new MimeType("audio", "basic");
    public static final MimeType L24 = new MimeType("audio", "L24");
    public static final MimeType AUDIO_MP4 = new MimeType("audio", "mp4");
    public static final MimeType AAC = new MimeType("audio", "aac");
    public static final MimeType MP3_MPEG = new MimeType("audio", "mpeg");
    public static final MimeType AUDIO_OGG = new MimeType("audio", "ogg");
    public static final MimeType VORBIS = new MimeType("audio", "vorbis");
    public static final MimeType X_MS_WMA = new MimeType("audio", "x-ms-wma");
    public static final MimeType X_MS_WAX = new MimeType("audio", "x-ms-wax");
    public static final MimeType REAL_AUDIO = new MimeType("audio", "vnd.rn-realaudio");
    public static final MimeType WAV = new MimeType("audio", "vnd.wave");
    public static final MimeType AUDIO_WEBM = new MimeType("audio", "webm");

    // Image
    public static final MimeType GIF = new MimeType("image", "gif");
    public static final MimeType JPEG = new MimeType("image", "jpeg");
    public static final MimeType PJPEG = new MimeType("image", "pjpeg");
    public static final MimeType PNG = new MimeType("image", "png");
    public static final MimeType SVG_XML = new MimeType("image", "svg+xml", true);
    public static final MimeType TIFF = new MimeType("image", "tiff");
    public static final MimeType ICO = new MimeType("image", "vnd.microsoft.icon");
    public static final MimeType WBMP = new MimeType("image", "vnd.wap.wbmp");
    public static final MimeType WEBP = new MimeType("image", "webp");

    // Message
    public static final MimeType HTTP = new MimeType("message", "http", true);
    public static final MimeType IMDN_XML = new MimeType("message", "imdn+xml", true);
    public static final MimeType PARTIAL = new MimeType("message", "partial");
    public static final MimeType E_MAIL_RFC_822 = new MimeType("message", "rfc822", true);

    // Multipart
    public static final MimeType MIXED = new MimeType("multipart", "mixed");
    public static final MimeType ALTERNATIVE = new MimeType("multipart", "alternative");
    public static final MimeType RELATED = new MimeType("multipart", "related");
    public static final MimeType FORM_DATA = new MimeType("multipart", "form-data");
    public static final MimeType SIGNED = new MimeType("multipart", "signed");
    public static final MimeType ENCRYPTED = new MimeType("multipart", "encrypted");

    // Text
    public static final MimeType CMD = new MimeType("text", "cmd", true);
    public static final MimeType CSS = new MimeType("text", "css", true);
    public static final MimeType CSV = new MimeType("text", "csv", true);
    public static final MimeType HTML = new MimeType("text", "html", true);
    public static final MimeType JAVASCRIPT = new MimeType("text", "javascript", true);
    public static final MimeType PLAIN = new MimeType("text", "plain", true);
    public static final MimeType PHP = new MimeType("text", "php", true);
    public static final MimeType XML = new MimeType("text", "xml", true);
    public static final MimeType MARKDOWN = new MimeType("text", "markdown", true);
    public static final MimeType CACHE_MANIFEST = new MimeType("text", "cache-manifest", true);

    // Video
    public static final MimeType MPEG = new MimeType("video", "mpeg");
    public static final MimeType MP4 = new MimeType("video", "mp4");
    public static final MimeType OGG = new MimeType("video", "ogg");
    public static final MimeType QUICKTIME = new MimeType("video", "quicktime");
    public static final MimeType WEBM = new MimeType("video", "webm");
    public static final MimeType X_MS_WMV = new MimeType("video", "x-ms-wmv");
    public static final MimeType X_FLV = new MimeType("video", "x-flv");
    public static final MimeType X_MS_VIDEO = new MimeType("video", "x-msvideo");
    public static final MimeType THREE_GPP = new MimeType("video", "3gpp");
    public static final MimeType THREE_GPP_2 = new MimeType("video", "3gpp2");
    private static final Map<String, MimeType> TYPES = getTypes();
    final String qualifier;
    final String group;
    final String name;
    final boolean text;

    public MimeType(String group, String name, boolean text) {
        this.qualifier = (group + "/" + name).toLowerCase(Locale.ENGLISH);
        this.group = group;
        this.name = name;
        this.text = text;
    }

    public MimeType(String group, String name) {
        this(group, name, false);
    }

    private static Map<String, MimeType> getTypes() {
        var ret = new HashMap<String, MimeType>();
        ret.put(ATOM_XML.qualifier, ATOM_XML);
        ret.put(EDI_X12.qualifier, EDI_X12);
        ret.put(EDIFACT.qualifier, EDIFACT);
        ret.put(JSON.qualifier, JSON);
        ret.put(APPLICATION_JAVASCRIPT.qualifier, APPLICATION_JAVASCRIPT);
        ret.put(OCTET_STREAM.qualifier, OCTET_STREAM);
        ret.put(APPLICATION_OGG.qualifier, APPLICATION_OGG);
        ret.put(PDF.qualifier, PDF);
        ret.put(POSTSCRIPT.qualifier, POSTSCRIPT);
        ret.put(SOAP_XML.qualifier, SOAP_XML);
        ret.put(FONT_WOFF.qualifier, FONT_WOFF);
        ret.put(XHTML_XML.qualifier, XHTML_XML);
        ret.put(XML_DTD.qualifier, XML_DTD);
        ret.put(XOP_XML.qualifier, XOP_XML);
        ret.put(ZIP.qualifier, ZIP);
        ret.put(GZIP.qualifier, GZIP);
        ret.put(X_BITTORRENT.qualifier, X_BITTORRENT);
        ret.put(X_TEX.qualifier, X_TEX);
        ret.put(APPLICATION_XML.qualifier, APPLICATION_XML);
        ret.put(DOC.qualifier, DOC);
        ret.put(FORM_URLENCODED.qualifier, FORM_URLENCODED);
        ret.put(BASIC.qualifier, BASIC);
        ret.put(L24.qualifier, L24);
        ret.put(AUDIO_MP4.qualifier, AUDIO_MP4);
        ret.put(AAC.qualifier, AAC);
        ret.put(MP3_MPEG.qualifier, MP3_MPEG);
        ret.put(AUDIO_OGG.qualifier, AUDIO_OGG);
        ret.put(VORBIS.qualifier, VORBIS);
        ret.put(X_MS_WMA.qualifier, X_MS_WMA);
        ret.put(X_MS_WAX.qualifier, X_MS_WAX);
        ret.put(REAL_AUDIO.qualifier, REAL_AUDIO);
        ret.put(WAV.qualifier, WAV);
        ret.put(AUDIO_WEBM.qualifier, AUDIO_WEBM);
        ret.put(GIF.qualifier, GIF);
        ret.put(JPEG.qualifier, JPEG);
        ret.put(PJPEG.qualifier, PJPEG);
        ret.put(PNG.qualifier, PNG);
        ret.put(SVG_XML.qualifier, SVG_XML);
        ret.put(TIFF.qualifier, TIFF);
        ret.put(ICO.qualifier, ICO);
        ret.put(WBMP.qualifier, WBMP);
        ret.put(WEBP.qualifier, WEBP);
        ret.put(HTTP.qualifier, HTTP);
        ret.put(IMDN_XML.qualifier, IMDN_XML);
        ret.put(PARTIAL.qualifier, PARTIAL);
        ret.put(E_MAIL_RFC_822.qualifier, E_MAIL_RFC_822);
        ret.put(MIXED.qualifier, MIXED);
        ret.put(ALTERNATIVE.qualifier, ALTERNATIVE);
        ret.put(RELATED.qualifier, RELATED);
        ret.put(FORM_DATA.qualifier, FORM_DATA);
        ret.put(SIGNED.qualifier, SIGNED);
        ret.put(ENCRYPTED.qualifier, ENCRYPTED);
        ret.put(CMD.qualifier, CMD);
        ret.put(CSS.qualifier, CSS);
        ret.put(CSV.qualifier, CSV);
        ret.put(HTML.qualifier, HTML);
        ret.put(JAVASCRIPT.qualifier, JAVASCRIPT);
        ret.put(PLAIN.qualifier, PLAIN);
        ret.put(PHP.qualifier, PHP);
        ret.put(XML.qualifier, XML);
        ret.put(MARKDOWN.qualifier, MARKDOWN);
        ret.put(CACHE_MANIFEST.qualifier, CACHE_MANIFEST);
        ret.put(MPEG.qualifier, MPEG);
        ret.put(MP4.qualifier, MP4);
        ret.put(OGG.qualifier, OGG);
        ret.put(QUICKTIME.qualifier, QUICKTIME);
        ret.put(WEBM.qualifier, WEBM);
        ret.put(X_MS_WMV.qualifier, X_MS_WMV);
        ret.put(X_FLV.qualifier, X_FLV);
        ret.put(X_MS_VIDEO.qualifier, X_MS_VIDEO);
        ret.put(THREE_GPP.qualifier, THREE_GPP);
        ret.put(THREE_GPP_2.qualifier, THREE_GPP_2);
        return Collections.unmodifiableMap(ret);
    }

    public static Iterable<MimeType> all() {
        return TYPES.values();
    }

    public static MimeType of(String qualifier) {
        return TYPES.get(qualifier.toLowerCase(Locale.ENGLISH));
    }

    public static MimeType of(String group, String name) {
        var qualifier = group + "/" + name;
        return TYPES.get(qualifier.toLowerCase(Locale.ENGLISH));
    }

    public String getQualifier() {
        return qualifier;
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
        var type = (MimeType) object;
        return qualifier.equals(type.qualifier);
    }

    @Override
    public int hashCode() {
        return qualifier.hashCode();
    }

    @Override
    public String toString() {
        return qualifier;
    }
}
