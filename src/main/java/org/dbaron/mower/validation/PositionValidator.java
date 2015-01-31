package org.dbaron.mower.validation;

import org.apache.commons.lang3.Validate;
import org.dbaron.mower.exception.OccupiedPositionException;
import org.dbaron.mower.exception.OutOfFieldException;
import org.dbaron.mower.model.Field;
import org.dbaron.mower.model.Mower;
import org.dbaron.mower.model.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by dbaron on 28/01/15.
 */
public class PositionValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(PositionValidator.class);

    public void validateIsInsideField(Position position, Field field) {
        Validate.notNull(position, "position is required");
        Validate.notNull(field, "field is required");

        Position lowerLeftHandCorner = field.getLowerLeftHandCorner();
        Position upperRightHandCorner = field.getUpperRightHandCorner();

        int minX = 0;
        int minY = 0;
        if (lowerLeftHandCorner != null) {
            minX = lowerLeftHandCorner.getX();
            minY = lowerLeftHandCorner.getY();
        }

        int maxX = 0;
        int maxY = 0;
        if (upperRightHandCorner != null) {
            maxX = upperRightHandCorner.getX();
            maxY = upperRightHandCorner.getY();
        }

        int currentX = position.getX();
        int currentY = position.getY();
        if (currentX < minX
                || currentX > maxX
                || currentY < minY
                || currentY > maxY) {

            LOGGER.error("{} is out of field", position);
            throw new OutOfFieldException(position + " is out of field");
        }

    }

    public void validateIsFreePosition(Position position, List<Mower> mowers) {
        Validate.notNull(position, "position is required");
        Validate.notNull(mowers, "mowers is required");

        for (Mower mower : mowers) {
            Position mowerPosition = mower.getPosition();

            if (position.equals(mowerPosition)) {
                LOGGER.error("Position {} is occupied", position);
                throw new OccupiedPositionException("Position " + position + " is occupied");
            }
        }
    }
}