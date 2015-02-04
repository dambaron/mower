package org.dbaron.mower.model;

import org.apache.commons.lang3.Validate;
import org.dbaron.mower.model.reference.CardinalOrientation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
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

    public Orientation applyRotation(String rotationCode) {
        Validate.notNull(rotationCode, "rotationCode is required");

        Orientation nextOrientation = null;
        String orientationCode = getCode();
        try {

            CardinalOrientation cardinalOrientation = CardinalOrientation.valueOf(orientationCode);
            CardinalOrientation nextCardinalOrientation = cardinalOrientation.getNextCardinalOrientation(rotationCode);
            if (nextCardinalOrientation != null) {
                nextOrientation = new Orientation(nextCardinalOrientation.getCode());
            }
        } catch(IllegalArgumentException iae) {
            LOGGER.error("No cardinal orientation found for code {}", rotationCode, iae);
        }

        return nextOrientation;
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
}