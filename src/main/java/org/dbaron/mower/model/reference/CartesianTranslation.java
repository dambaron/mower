package org.dbaron.mower.model.reference;

/**
 * Created by dbaron on 29/01/15.
 */
public enum CartesianTranslation {

    A("A", "FORWARD"),
    R("R", "BACKWARD");

    private final String code;
    private final String label;

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    CartesianTranslation(String code, String label) {
        this.code = code;
        this.label = label;
    }
}