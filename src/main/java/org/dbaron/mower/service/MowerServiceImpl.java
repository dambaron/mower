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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by dbaron on 28/01/15.
 */
public class MowerServiceImpl implements MowerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MowerServiceImpl.class);

    private PositionValidator positionValidator = new PositionValidator();

    @Autowired
    private PointProviderService pointProviderService;

    private Map<Field, List<Mower>> mowersByField = new HashMap<>();

    @Override
    public void registerField(Field field) {

        if (!mowersByField.containsKey(field)) {
            mowersByField.put(field, new LinkedList<Mower>());
        }
    }

    @Override
    public void unregisterField(Field field) {

        if (mowersByField.containsKey(field)) {
            mowersByField.remove(field);
        }
    }

    @Override
    public void registerMower(Mower mower, Field field) {

        if (!mowersByField.containsKey(field)) {
            registerField(field);
        }

        List<Mower> registeredMowers = mowersByField.get(field);
        if (registeredMowers == null) {
            registeredMowers = new LinkedList<>();
        }
        registeredMowers.add(mower);
        mowersByField.put(field, registeredMowers);
    }

    @Override
    public void unregisterMower(Mower mower, Field field) {

        if (mowersByField.containsKey(field)) {

            List<Mower> registeredMowers = mowersByField.get(field);
            if (registeredMowers.contains(mower)) {
                registeredMowers.remove(mower);
                mowersByField.put(field, registeredMowers);
            }
        }
    }

    @Override
    public void mow(Field field) {

        List<Mower> mowers = mowersByField.get(field);
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

        List<Mower> mowersInField = mowersByField.get(field);
        for (Move move : mower.getMoveSequence()) {

            Point currentPoint = new Point(mower.getPosition(), mower.getOrientation());
            Point nextPoint = pointProviderService.applyMove(move, currentPoint);
            Position nextPosition = nextPoint.getPosition();
            Orientation nextOrientation = nextPoint.getOrientation();
            try {
                positionValidator.validateIsInsideField(nextPosition, field);
                positionValidator.validateIsFreePosition(nextPosition, mower, mowersInField);

                WayPoint wayPoint = new WayPoint(nextPosition, nextOrientation);
                mower.getWayPoints().add(wayPoint);
                mower.updatePosition(nextPosition);
                mower.updateOrientation(nextOrientation);

            } catch (OutOfFieldException | OccupiedPositionException e) {
                LOGGER.error("{} is not mowable. Waiting for the next valid move", nextPosition, e);
            }
        }
    }
}
