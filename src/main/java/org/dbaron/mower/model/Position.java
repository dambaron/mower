package org.dbaron.mower.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.dbaron.mower.model.reference.CardinalOrientation;
import org.dbaron.mower.model.reference.CartesianTranslation;

/**
 * Created by dbaron on 27/01/15.
 */
public class Position {

    private int x;
    private int y;

    public Position() {
        x = 0;
        y = 0;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Position applyTranslation(String moveCode, Orientation orientation) {
        Validate.notNull(moveCode, "moveCode is required");
        Validate.notNull(orientation, "orientation is required");

        int currentX = this.getX();
        int currentY = this.getY();

        int nextX = currentX;
        int nextY = currentY;

        CardinalOrientation cardinalOrientation = CardinalOrientation.valueOf(orientation.getCode());

        //Translations
        if (StringUtils.equals(moveCode, CartesianTranslation.A.getCode())) {

            switch (cardinalOrientation) {
                case N:
                    nextY = nextY + 1;
                    break;
                case S:
                    nextY = nextY - 1;
                    break;
                case W:
                    nextX = nextX - 1;
                    break;
                case E:
                    nextX = nextX + 1;
                    break;
                default:
                    break;
            }
        }

        if (StringUtils.equals(moveCode, CartesianTranslation.R.getCode())) {

            switch (cardinalOrientation) {
                case N:
                    nextY = nextY - 1;
                    break;
                case S:
                    nextY = nextY + 1;
                    break;
                case W:
                    nextX = nextX + 1;
                    break;
                case E:
                    nextX = nextX - 1;
                    break;
                default:
                    break;
            }
        }

        return new Position(nextX, nextY);
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        if (y != position.y) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}