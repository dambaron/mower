package org.dbaron.mower.model.reference;

/**
 * A rotation in a cartesian coordinate system
 * Created by dbaron on 29/01/15.
 */
public enum CartesianRotation {

    G("G", "LEFT"),
    D("D", "RIGHT");

    private final String code;
    private final String label;

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    CartesianRotation(String code, String label) {
        this.code = code;
        this.label = label;
    }
}