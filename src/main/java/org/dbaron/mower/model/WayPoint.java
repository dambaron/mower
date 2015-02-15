package org.dbaron.mower.model;

/**
 * A point in a cartesian coordinate system.
 * Described by a position and an orientation.
 *
 * Created by dbaron on 27/01/15.
 */
public class WayPoint extends Point {

    public WayPoint() {
        super();
    }

    public WayPoint(Position position, Orientation orientation) {
        super(position, orientation);
    }
}