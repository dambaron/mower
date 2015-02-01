package org.dbaron.mower.model;

import org.dbaron.mower.model.reference.CardinalOrientation;

/**
 * Created by dbaron on 27/01/15.
 */
public class Move {

    private String code;

    public Move() {
    }

    public Move(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}