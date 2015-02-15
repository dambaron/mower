package org.dbaron.mower.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.dbaron.mower.model.Move;
import org.dbaron.mower.model.Orientation;
import org.dbaron.mower.model.Point;
import org.dbaron.mower.model.Position;
import org.dbaron.mower.model.Rotation;
import org.dbaron.mower.model.Translation;
import org.dbaron.mower.model.reference.CardinalOrientation;
import org.dbaron.mower.model.reference.CartesianRotation;
import org.dbaron.mower.model.reference.CartesianTranslation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * An implementation of the PointProviderService
 * Created by dbaron on 27/01/15.
 */
public class PointProviderServiceImpl implements PointProviderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PointProviderServiceImpl.class);

    @Override
    public Point applyMove(Move move, Point startingPoint) {
        Validate.notNull(startingPoint, "startingPoint is required");
        Validate.notNull(move, "move is required");

        String moveCode = move.getCode();
        Point nextPoint = null;

        boolean isRotation = (StringUtils.equals(moveCode, CartesianRotation.G.getCode())
                || StringUtils.equals(moveCode, CartesianRotation.D.getCode()));

        boolean isTranslation = (StringUtils.equals(moveCode, CartesianTranslation.A.getCode())
                || StringUtils.equals(moveCode, CartesianTranslation.R.getCode()));

        //Rotations
        if (isRotation) {
            Rotation rotation = new Rotation(move.getCode());
            nextPoint = applyRotation(rotation, startingPoint);
        }

        //Translations
        if (isTranslation) {
            Translation translation = new Translation(move.getCode());
            nextPoint = applyTranslation(translation, startingPoint);
        }

        if (nextPoint == null
                || nextPoint.getPosition() == null
                || nextPoint.getOrientation() == null) {
            LOGGER.error("Unable to determine next point. Returning current point instead");
            nextPoint = startingPoint;
        }
        return nextPoint;
    }

    @Override
    public Point applyTranslation(Translation translation, Point startingPoint) {
        Validate.notNull(startingPoint, "startingPoint is required");
        Validate.notNull(translation, "translation is required");

        String translationCode = translation.getCode();
        if (StringUtils.isBlank(translationCode)) {
            LOGGER.error("Translation can't be applied. Translation code is blank");
            return null;
        }

        Position currentPosition = startingPoint.getPosition();
        if (currentPosition == null) {
            LOGGER.error("Translation can't be applied. Current position is null");
            return null;
        }

        Orientation currentOrientation = startingPoint.getOrientation();
        if (currentOrientation == null) {
            LOGGER.error("Translation can't be applied. Current orientation is null");
            return null;
        }

        int currentX = currentPosition.getX();
        int currentY = currentPosition.getY();

        int nextX = currentX;
        int nextY = currentY;

        CardinalOrientation cardinalOrientation;
        try {
            cardinalOrientation = CardinalOrientation.valueOf(currentOrientation.getCode());
        } catch (IllegalArgumentException iae) {
            LOGGER.error("Translation can't be applied. Unknown orientation code {}",
                    currentOrientation.getCode(),
                    iae);
            return null;
        }

        // Translations
        // Forward translation
        if (StringUtils.equals(translationCode, CartesianTranslation.A.getCode())) {

            switch (cardinalOrientation) {
                case N:
                    nextY = nextY + 1;
                    break;
                case S:
                    nextY = nextY - 1;
                    break;
                case W:
                    nextX = nextX - 1;
                    break;
                case E:
                    nextX = nextX + 1;
                    break;
                default:
                    break;
            }
        }

        // Backward translation
        if (StringUtils.equals(translationCode, CartesianTranslation.R.getCode())) {

            switch (cardinalOrientation) {
                case N:
                    nextY = nextY - 1;
                    break;
                case S:
                    nextY = nextY + 1;
                    break;
                case W:
                    nextX = nextX + 1;
                    break;
                case E:
                    nextX = nextX - 1;
                    break;
                default:
                    break;
            }
        }

        return new Point(new Position(nextX, nextY), currentOrientation);
    }

    @Override
    public Point applyRotation(Rotation rotation, Point startingPoint) {
        Validate.notNull(startingPoint, "startingPoint is required");
        Validate.notNull(rotation, "rotation is required");

        String rotationCode = rotation.getCode();
        if (StringUtils.isBlank(rotationCode)) {
            LOGGER.error("Rotation can't be applied. Translation code is blank");
            return null;
        }

        Position currentPosition = startingPoint.getPosition();
        if (currentPosition == null) {
            LOGGER.error("Rotation can't be applied. Current position is null");
            return null;
        }

        Orientation currentOrientation = startingPoint.getOrientation();
        if (currentOrientation == null) {
            LOGGER.error("Rotation can't be applied. Current orientation is null");
            return null;
        }

        CardinalOrientation cardinalOrientation = null;
        try {
            cardinalOrientation = CardinalOrientation.valueOf(currentOrientation.getCode());
        } catch(IllegalArgumentException iae) {
            LOGGER.error("Rotation can't be applied. Unknown orientation code {}",
                    currentOrientation.getCode(),
                    iae);
        }

        CardinalOrientation nextCardinalOrientation = cardinalOrientation.getNextCardinalOrientation(rotationCode);
        if (nextCardinalOrientation == null) {
            LOGGER.error("Rotation can't be applied. No orientation found for code {}", rotationCode);
            return null;
        }

        return new Point(currentPosition, new Orientation(nextCardinalOrientation.getCode()));
    }
}