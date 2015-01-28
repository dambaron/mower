package org.dbaron.mower.model;

/**
 * Created by dbaron on 28/01/15.
 */
public class Point {

    private Position position;
    private Orientation orientation;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }
}
