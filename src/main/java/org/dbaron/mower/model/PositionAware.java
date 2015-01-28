package org.dbaron.mower.model;

/**
 * Created by dbaron on 27/01/15.
 */
public interface PositionAware {

    Position getPosition();

    Point updatePosition(Point point, Position position);
}
