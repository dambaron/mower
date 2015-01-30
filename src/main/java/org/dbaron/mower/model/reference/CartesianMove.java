package org.dbaron.mower.model.reference;

/**
 * Created by dbaron on 29/01/15.
 */
public enum CartesianMove {

    LEFT("G"),
    RIGTH("D"),
    FORWARD("A"),
    BACKWARD("R");

    private String code;

    public String getCode() {
        return code;
    }

    CartesianMove(String code) {
        this.code = code;
    }
}