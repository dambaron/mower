package org.dbaron.mower.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.dbaron.mower.model.Move;
import org.dbaron.mower.model.Orientation;
import org.dbaron.mower.model.Point;
import org.dbaron.mower.model.Position;
import org.dbaron.mower.model.reference.CartesianRotation;
import org.dbaron.mower.model.reference.CartesianTranslation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by dbaron on 27/01/15.
 */
public class PointProviderServiceImpl implements PointProviderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PointProviderServiceImpl.class);

    @Override
    public Point getNextPoint(Point startingPoint, Move move) {
        Validate.notNull(startingPoint, "startingPoint is required");
        Validate.notNull(move, "move is required");

        String code = move.getCode();
        Point nextPoint = null;
        // Rotations
        if (StringUtils.equals(code, CartesianRotation.G.getCode())) {

            Orientation currentOrientation = startingPoint.getOrientation();
        }

        if (nextPoint == null
                && StringUtils.equals(code, CartesianRotation.D.getCode())) {

            Orientation currentOrientation = startingPoint.getOrientation();
        }

        // Translations
        if (nextPoint == null
                && StringUtils.equals(code, CartesianTranslation.A.getCode())) {

            Position currentPosition = startingPoint.getPosition();
        }

        if (nextPoint == null
                && StringUtils.equals(code, CartesianTranslation.R.getCode())) {

            Position currentPosition = startingPoint.getPosition();

        }

        return nextPoint;
    }

//    @Override
//    public List<RoutePoint> getRoutePoints(RoutePoint startingPoint, List<Move> moves) {
//        Validate.notNull(startingPoint, "startingPoint is required");
//        Validate.notNull(moves, "moves are required");
//
//        RoutePoint currentPoint = startingPoint;
//        RoutePoint nextPoint = null;
//        List<RoutePoint> routePoints = new LinkedList<>();
//        routePoints.add(currentPoint);
//
//        for (Move move : moves) {
//            nextPoint = getNextRoutePoint(currentPoint, move);
//            routePoints.add(nextPoint);
//            currentPoint = nextPoint;
//        }
//
//        return routePoints;
//    }
}
