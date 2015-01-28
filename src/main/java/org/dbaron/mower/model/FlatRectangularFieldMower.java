package org.dbaron.mower.model;

/**
 * Created by dbaron on 27/01/15.
 */
public class FlatRectangularFieldMower extends Mower implements OrientationAware, PositionAware {

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
