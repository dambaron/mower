package org.dbaron.mower.model.reference;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A cardinal orientation in a cartesian coordinate system
 * Created by dbaron on 27/01/15.
 */
public enum CardinalOrientation {

    N("N", "NORTH"),
    S("S", "SOUTH"),
    W("W", "WEST"),
    E("E", "EAST");

    private static final Logger LOGGER = LoggerFactory.getLogger(CardinalOrientation.class);

    private static final ImmutableList<CardinalOrientation> LEFT_SHIFTS = ImmutableList.of(
            CardinalOrientation.N,
            CardinalOrientation.W,
            CardinalOrientation.S,
            CardinalOrientation.E);

    private static final ImmutableList<CardinalOrientation> RIGHT_SHIFTS = ImmutableList.of(
            CardinalOrientation.N,
            CardinalOrientation.E,
            CardinalOrientation.S,
            CardinalOrientation.W);

    private final String code;
    private final String label;

    CardinalOrientation(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    /**
     * Defines the next cardinal orientation after a cardinal rotation is applied
     * to the current cardinal orientation
     * @param cartesianRotation the cartesian rotation to apply
     * @return the next cardinal orientation
     */
    public CardinalOrientation getNextCardinalOrientation(String cartesianRotation) {
        Validate.notNull(cartesianRotation, "cartesianRotation is required");

        CardinalOrientation nextCardinalOrientation = null;
        if (StringUtils.equals(cartesianRotation, CartesianRotation.G.getCode())) {
            nextCardinalOrientation = getNextCardinalOrientation(LEFT_SHIFTS);
        }

        if (StringUtils.equals(cartesianRotation, CartesianRotation.D.getCode())) {
            nextCardinalOrientation = getNextCardinalOrientation(RIGHT_SHIFTS);
        }

        if (nextCardinalOrientation == null) {
            LOGGER.error("Unable to determine next orientation for code {}", cartesianRotation);
            throw new IllegalArgumentException("Unable to determine next orientation for code " + cartesianRotation);
        } else {
            return nextCardinalOrientation;
        }
    }

    /**
     * Defines the next cardinal orientation based upon current orientation and a list of transitions
     * @param cardinalOrientations the list of allowed orientations
     * @return the next cardinal orientation
     */
    private CardinalOrientation getNextCardinalOrientation(ImmutableList<CardinalOrientation> cardinalOrientations) {
        Validate.notNull(cardinalOrientations, "cardinalOrientations is required");

        CardinalOrientation nextCardinalOrientation = null;

        int size = cardinalOrientations.size();
        int index = cardinalOrientations.indexOf(this);
        if (size > 0 && index > -1) {
            int nextIndex = (index + 1) % cardinalOrientations.size();
            nextCardinalOrientation = cardinalOrientations.get(nextIndex);
        }

        return nextCardinalOrientation;
    }
}