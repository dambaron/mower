package org.dbaron.mower.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An orientation
 * Created by dbaron on 27/01/15.
 */
public class Orientation {

    private static final Logger LOGGER = LoggerFactory.getLogger(Orientation.class);

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Orientation() {
        this.code = null;
    }

    public Orientation(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Orientation that = (Orientation) o;

        if (code != null ? !code.equals(that.code) : that.code != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Orientation{");
        sb.append("code='").append(code).append('\'');
        sb.append('}');
        return sb.toString();
    }
}