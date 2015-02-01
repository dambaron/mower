package org.dbaron.mower.service;

import org.apache.commons.lang3.Validate;
import org.dbaron.mower.model.Move;
import org.dbaron.mower.model.RoutePoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by dbaron on 27/01/15.
 */
public class RoutePointProviderServiceImpl implements RoutePointProviderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoutePointProviderServiceImpl.class);

    @Override
    public RoutePoint getNextRoutePoint(RoutePoint startingPoint, Move move) {
        return null;
    }

    @Override
    public List<RoutePoint> getRoutePoints(RoutePoint startingPoint, List<Move> moves) {
        Validate.notNull(startingPoint, "startingPoint is required");
        Validate.notNull(moves, "moves are required");

        RoutePoint currentPoint = startingPoint;
        RoutePoint nextPoint = null;
        List<RoutePoint> routePoints = new LinkedList<>();
        routePoints.add(currentPoint);

        for (Move move : moves) {
            nextPoint = getNextRoutePoint(currentPoint, move);
            routePoints.add(nextPoint);
            currentPoint = nextPoint;
        }

        return routePoints;
    }
}
