package org.dbaron.mower.model.reference;

/**
 * Created by dbaron on 27/01/15.
 */
public enum CardinalOrientation {

    N("N", "NORTH"),
    S("S", "SOUTH"),
    W("W", "WEST"),
    E("E", "EAST");

    private String code;
    private String label;

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    CardinalOrientation(String code, String label) {
        this.code = code;
        this.label = label;
    }
}