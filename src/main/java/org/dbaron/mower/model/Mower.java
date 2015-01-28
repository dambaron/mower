package org.dbaron.mower.model;

import org.dbaron.mower.exception.MowingException;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by dbaron on 28/01/15.
 */
public class Mower implements OrientationAware, PositionAware {

    private WayPoint initialWayPoint;
    private List<WayPoint> wayPoints;
    private List<RoutePoint> routePoints;

    public Mower() {
        this.initialWayPoint = null;
        this.wayPoints = new LinkedList<>();
        this.routePoints = new LinkedList<>();
    }

    public WayPoint getInitialWayPoint() {
        return initialWayPoint;
    }

    public void setInitialWayPoint(WayPoint initialWayPoint) {
        this.initialWayPoint = initialWayPoint;
    }

    public List<WayPoint> getWayPoints() {
        return wayPoints;
    }

    public void setWayPoints(List<WayPoint> wayPoints) {
        this.wayPoints = wayPoints;
    }

    public List<RoutePoint> getRoutePoints() {
        return routePoints;
    }

    public void setRoutePoints(List<RoutePoint> routePoints) {
        this.routePoints = routePoints;
    }

    @Override
    public Orientation getOrientation() {
        return null;
    }

    @Override
    public Point updateOrientation(Point point, Orientation orientation) {
        return null;
    }


    @Override
    public Position getPosition() {
        return null;
    }

    @Override
    public Point updatePosition(Point point, Position position) {
        return null;
    }
}
