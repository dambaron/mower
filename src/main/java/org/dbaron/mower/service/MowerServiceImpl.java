package org.dbaron.mower.service;

import org.dbaron.mower.exception.MowingException;
import org.dbaron.mower.model.Field;
import org.dbaron.mower.model.Mower;
import org.dbaron.mower.model.RoutePoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by dbaron on 28/01/15.
 */
public class MowerServiceImpl implements MowerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MowerServiceImpl.class);

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

        List<Mower> mowers = mowersByField.get(field);
        if (mowers == null) {
            mowers = new LinkedList<>();
        }
        mowers.add(mower);
        mowersByField.put(field, mowers);
    }

    @Override
    public void unregisterMower(Mower mower, Field field) {

        if (mowersByField.containsKey(field)) {

            List<Mower> mowers = mowersByField.get(field);
            if (mowers.contains(mower)) {
                mowers.remove(mower);
                mowersByField.put(field, mowers);
            }
        }
    }

    @Override
    public void mow(Field field) throws MowingException {

        List<Mower> mowers = mowersByField.get(field);
        if (mowers != null) {
            for (Mower mower : mowers) {
                mow(field, mower);
            }
        }
    }

    @Override
    public void mow(Field field, Mower mower) throws MowingException {

        List<RoutePoint> routePoints = mower.getRoutePoints();
        for (RoutePoint routePoint : routePoints) {

        }
    }
}
