package org.dbaron.mower.model;

/**
 * Created by dbaron on 01/02/15.
 */
public class Translation extends Move {

    public Translation() {
        super();
    }

    public Translation(String code) {
        super(code);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Translation{");
        sb.append("code='").append(getCode()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}