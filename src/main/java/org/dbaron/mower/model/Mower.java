package org.dbaron.mower.model;

import org.apache.commons.lang3.Validate;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by dbaron on 28/01/15.
 */
public class Mower implements OrientationAware, PositionAware {

    private WayPoint initialWayPoint;
    private LinkedList<WayPoint> wayPoints;
    private LinkedList<RoutePoint> routePoints;

    public Mower() {
        this.initialWayPoint = new WayPoint();
        this.wayPoints = new LinkedList<>();
        this.routePoints = new LinkedList<>();
    }

    public Mower(WayPoint initialWayPoint) {

        Validate.notNull(initialWayPoint, "initialWayPoint is required");

        this.initialWayPoint = initialWayPoint;

        this.wayPoints = new LinkedList<>();
        this.wayPoints.add(initialWayPoint);

        this.routePoints = new LinkedList<>();
    }

    public Mower(WayPoint initialWayPoint, List<RoutePoint> routePoints) {

        Validate.notNull(initialWayPoint, "initialWayPoint is required");
        Validate.notNull(routePoints, "routePoints is required");

        this.initialWayPoint = initialWayPoint;

        this.wayPoints = new LinkedList<>();
        this.wayPoints.add(initialWayPoint);

        this.routePoints = new LinkedList<>();
        this.routePoints.addAll(routePoints);
    }

    public WayPoint getInitialWayPoint() {
        return initialWayPoint;
    }

    public List<WayPoint> getWayPoints() {
        return wayPoints;
    }

    public List<RoutePoint> getRoutePoints() {
        return routePoints;
    }

    public void setRoutePoints(List<RoutePoint> routePoints) {
        this.routePoints = new LinkedList<>();
        this.routePoints.addAll(routePoints);
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

        if (wayPoints.isEmpty()) {
            return initialWayPoint.getPosition();
        }

        return this.wayPoints.getLast().getPosition();
    }

    @Override
    public Point updatePosition(Point point, Position position) {
        return null;
    }
}