package org.dbaron.mower.model;

/**
 * A point in a cartesian coordinate system.
 * Described by a position and an orientation.
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

    public Point() {
        this.position = null;
        this.orientation = null;
    }

    public Point(Position position, Orientation orientation) {
        this.position = position;
        this.orientation = orientation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (orientation != null ? !orientation.equals(point.orientation) : point.orientation != null) return false;
        if (position != null ? !position.equals(point.position) : point.position != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = position != null ? position.hashCode() : 0;
        result = 31 * result + (orientation != null ? orientation.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Point{");
        sb.append("position=").append(position);
        sb.append(", orientation=").append(orientation);
        sb.append('}');
        return sb.toString();
    }
}