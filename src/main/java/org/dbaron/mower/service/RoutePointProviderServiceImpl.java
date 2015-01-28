package org.dbaron.mower.service;

import org.dbaron.mower.model.Move;
import org.dbaron.mower.model.RoutePoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        return null;
    }
}
