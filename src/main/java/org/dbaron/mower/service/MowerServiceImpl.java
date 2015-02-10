package org.dbaron.mower.service;

import org.apache.commons.lang3.Validate;
import org.dbaron.mower.exception.OccupiedPositionException;
import org.dbaron.mower.exception.OutOfFieldException;
import org.dbaron.mower.model.Field;
import org.dbaron.mower.model.Move;
import org.dbaron.mower.model.Mower;
import org.dbaron.mower.model.Orientation;
import org.dbaron.mower.model.Point;
import org.dbaron.mower.model.Position;
import org.dbaron.mower.model.WayPoint;
import org.dbaron.mower.validation.PositionValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by dbaron on 28/01/15.
 */
public class MowerServiceImpl implements MowerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MowerServiceImpl.class);

    @Autowired
    private PositionValidator positionValidator;

    @Autowired
    private PointProviderService pointProviderService;

    private Map<Field, Set<Mower>> mowersByField = new HashMap<>();

    public PositionValidator getPositionValidator() {
        return positionValidator;
    }

    public void setPositionValidator(PositionValidator positionValidator) {
        this.positionValidator = positionValidator;
    }

    public PointProviderService getPointProviderService() {
        return pointProviderService;
    }

    public void setPointProviderService(PointProviderService pointProviderService) {
        this.pointProviderService = pointProviderService;
    }

    @Override
    public void registerField(Field field) {

        if (!mowersByField.containsKey(field)) {
            mowersByField.put(field, new LinkedHashSet<Mower>());
        }
    }

    @Override
    public Set<Field> getRegisteredFields() {
        return (mowersByField != null ? mowersByField.keySet() : null);
    }

    @Override
    public void registerMower(Mower mower, Field field) {

        if (!mowersByField.containsKey(field)) {
            registerField(field);
        }

        Set<Mower> registeredMowers = mowersByField.get(field);
        if (registeredMowers == null) {
            registeredMowers = new LinkedHashSet<>();
        }
        registeredMowers.add(mower);
        mowersByField.put(field, registeredMowers);
    }

    @Override
    public Set<Mower> getRegisteredMowers(Field Field) {
        return (mowersByField != null ? mowersByField.get(Field) : null);
    }

    @Override
    public void mow(Field field) {

        Set<Mower> mowers = mowersByField.get(field);
        if (mowers != null) {
            for (Mower mower : mowers) {
                mow(field, mower);
            }
        }
    }

    @Override
    public void mow(Field field, Mower mower) {
        Validate.notNull(field, "field is required");
        Validate.notNull(mower, "mower is required");

        Set<Mower> mowersInField = mowersByField.get(field);
        for (Move move : mower.getMoveSequence()) {

            // First validate starting point
            Point currentPoint = new Point(mower.getPosition(), mower.getOrientation());
            Position currentPosition = currentPoint.getPosition();
            try {
                positionValidator.validateIsInsideField(currentPosition, field);
                positionValidator.validateIsFreePosition(currentPosition, mower, mowersInField);

            } catch (OutOfFieldException oofe) {

                mower.setSkippedMoves(mower.getMoveSequence().size());
                LOGGER.error("Mower can't start. {} is outside the field",
                        currentPoint,
                        oofe);
            } catch (OccupiedPositionException ope) {

                mower.setSkippedMoves(mower.getMoveSequence().size());
                LOGGER.error("Mower can't start. {} is already occupied",
                        currentPoint,
                        ope);
            }

            // Second retrieve next point move after move
            Point nextPoint = pointProviderService.applyMove(move, currentPoint);
            Position nextPosition = nextPoint.getPosition();
            Orientation nextOrientation = nextPoint.getOrientation();
            try {
                positionValidator.validateIsInsideField(nextPosition, field);
                positionValidator.validateIsFreePosition(nextPosition, mower, mowersInField);

                WayPoint wayPoint = new WayPoint(nextPosition, nextOrientation);
                mower.getWayPoints().add(wayPoint);

            } catch (OutOfFieldException oofe) {

                mower.setSkippedMoves(mower.getSkippedMoves() + 1);
                LOGGER.error("{} is not mowable. It is out of field. Waiting for the next valid move",
                        nextPosition,
                        oofe);
            } catch (OccupiedPositionException ope) {

                mower.setSkippedMoves(mower.getSkippedMoves() + 1);
                LOGGER.error("{} is not mowable. It is already occupied. Waiting for the next valid move",
                        nextPosition,
                        ope);
            }
        }
    }
}