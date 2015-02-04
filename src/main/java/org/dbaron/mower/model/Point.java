package org.dbaron.mower.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.dbaron.mower.model.reference.CartesianRotation;
import org.dbaron.mower.model.reference.CartesianTranslation;

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

    public Point() {
        this.position = null;
        this.orientation = null;
    }

    public Point(Position position, Orientation orientation) {
        this.position = position;
        this.orientation = orientation;
    }

    public Point applyMove(String moveCode) {
        Validate.notNull(moveCode, "moveCode is required");

        boolean isRotation, isTranslation;

        Position currentPosition = this.getPosition();
        Orientation currentOrientation = this.getOrientation();

        Position nextPosition = null;
        Orientation nextOrientation = null;

        isRotation = (StringUtils.equals(moveCode, CartesianRotation.G.getCode())
                || StringUtils.equals(moveCode, CartesianRotation.D.getCode()));

        isTranslation = (StringUtils.equals(moveCode, CartesianTranslation.A.getCode())
                || StringUtils.equals(moveCode, CartesianTranslation.R.getCode()));


        //Rotations
        if (isRotation
                && currentOrientation != null) {
            nextOrientation = currentOrientation.applyRotation(moveCode);
        }

        //Translations
        if (isTranslation
                && currentPosition != null
                && currentOrientation != null) {
            nextPosition = currentPosition.applyTranslation(moveCode, currentOrientation);
        }

        nextPosition = (nextPosition != null ? nextPosition : currentPosition);
        nextOrientation = (nextOrientation != null ? nextOrientation : currentOrientation);

        return new Point(nextPosition, nextOrientation);
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
}