package io.github.amayaframework.core.context;

public final class MIMEData {
    private MIMEType type;
    private String parameter;
    private String value;

    public MIMEData(MIMEType type, String parameter, String value) {
        this.type = type;
        this.parameter = parameter;
        this.value = value;
    }

    public MIMEData(MIMEType type) {
        this.type = type;
    }

    public MIMEType getType() {
        return type;
    }

    public void setType(MIMEType type) {
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
