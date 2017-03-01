package de.scisertec.core.infrastructure.exception;

public class Violation {

    String key;
    String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Violation{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
