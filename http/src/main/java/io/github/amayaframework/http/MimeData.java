package io.github.amayaframework.http;

public final class MimeData {
    private MimeType type;
    private String parameter;
    private String value;

    public MimeData(MimeType type, String parameter, String value) {
        this.type = type;
        this.parameter = parameter;
        this.value = value;
    }

    public MimeData(MimeType type) {
        this.type = type;
    }

    public MimeType getType() {
        return type;
    }

    public void setType(MimeType type) {
        this.type = type;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
