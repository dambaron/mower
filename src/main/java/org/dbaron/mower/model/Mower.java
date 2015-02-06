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
    private int skippedMoves = 0;

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
        Validate.notNull(moveSequence, "moveSequence is required");

        this.moveSequence = new LinkedList<>();
        this.moveSequence.addAll(moveSequence);
    }

    public int getSkippedMoves() {
        return skippedMoves;
    }

    public void setSkippedMoves(int skippedMoves) {
        this.skippedMoves = skippedMoves;
    }

    public String getDisplayablePath() {

        if (getWayPoints() == null
                || getWayPoints().isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (WayPoint wayPoint : getWayPoints()) {
            sb.append("(")
                    .append(wayPoint.getPosition().getX())
                    .append(",")
                    .append(wayPoint.getPosition().getY())
                    .append(",")
                    .append(wayPoint.getOrientation().getCode())
                    .append(") -> ");
        }
        sb.append("(STOP)");
        return sb.toString();
    }

    @Override
    public Orientation getOrientation() {

        if (wayPoints == null
                || wayPoints.isEmpty()) {

            return (initialWayPoint != null ? initialWayPoint.getOrientation() : null);
        }

        return wayPoints.getLast().getOrientation();
    }

    @Override
    public Position getPosition() {

        if (wayPoints == null
                || wayPoints.isEmpty()) {
            return initialWayPoint != null ? initialWayPoint.getPosition() : null;
        }

        return wayPoints.getLast().getPosition();
    }
}