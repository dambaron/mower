package org.dbaron.mower.model;

/**
 * A move followed by a mower
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Move{");
        sb.append("code='").append(code).append('\'');
        sb.append('}');
        return sb.toString();
    }
}