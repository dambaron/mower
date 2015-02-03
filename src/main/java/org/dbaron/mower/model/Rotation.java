package org.dbaron.mower.model;

/**
 * Created by dbaron on 01/02/15.
 */
public class Rotation extends Move {

    public Rotation() {
        super();
    }

    public Rotation(String code) {
        super(code);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Rotation{");
        sb.append("code='").append(getCode()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}