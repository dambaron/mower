package org.dbaron.mower.model.reference;

/**
 * Created by dbaron on 27/01/15.
 */
public enum CardinalOrientation {

    NORTH("N"),
    SOUTH("S"),
    WEST("W"),
    EAST("E");

    private String code;

    public String getCode() {
        return code;
    }

    CardinalOrientation(String code) {
        this.code = code;
    }
}