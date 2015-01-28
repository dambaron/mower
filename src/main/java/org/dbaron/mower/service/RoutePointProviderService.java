package org.dbaron.mower.service;

import org.dbaron.mower.model.Move;
import org.dbaron.mower.model.RoutePoint;

import java.util.List;

/**
 * Created by dbaron on 27/01/15.
 */
public interface RoutePointProviderService {

    RoutePoint getNextRoutePoint(RoutePoint startingPoint, Move move);
    
    List<RoutePoint> getRoutePoints(RoutePoint startingPoint, List<Move> moves);
}
