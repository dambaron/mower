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
    private LinkedList<Move> moveSequence;

    public Mower() {
        this.initialWayPoint = new WayPoint();
        this.wayPoints = new LinkedList<>();
        this.moveSequence = new LinkedList<>();
    }

    public Mower(WayPoint initialWayPoint) {

        Validate.notNull(initialWayPoint, "initialWayPoint is required");

        this.initialWayPoint = initialWayPoint;

        this.wayPoints = new LinkedList<>();
        this.wayPoints.add(initialWayPoint);

        this.moveSequence = new LinkedList<>();
    }

    public Mower(WayPoint initialWayPoint, List<Move> moveSequence) {

        Validate.notNull(initialWayPoint, "initialWayPoint is required");
        Validate.notNull(moveSequence, "moveSequence is required");

        this.initialWayPoint = initialWayPoint;

        this.wayPoints = new LinkedList<>();
        this.wayPoints.add(initialWayPoint);

        this.moveSequence = new LinkedList<>();
        this.moveSequence.addAll(moveSequence);
    }

    public WayPoint getInitialWayPoint() {
        return initialWayPoint;
    }

    public List<WayPoint> getWayPoints() {
        return wayPoints;
    }

    public List<Move> getMoveSequence() {
        return moveSequence;
    }

    public void setMoveSequence(List<Move> moveSequence) {
        this.moveSequence = new LinkedList<>();
        this.moveSequence.addAll(moveSequence);
    }

    @Override
    public Orientation getOrientation() {
        return null;
    }

    @Override
    public Point updateOrientation(Orientation orientation) {
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
    public Point updatePosition(Position position) {
        return null;
    }
}