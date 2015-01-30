package org.dbaron.mower.model;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

/**
 * Created by dbaron on 27/01/15.
 */
public class Field {

    private Position lowerLeftHandCorner;
    private Position upperRightHandCorner;

    private Table<Integer, Integer, Boolean> mowingIndex = HashBasedTable.create();

    public Field() {
        this.lowerLeftHandCorner = null;
        this.upperRightHandCorner = null;
    }

    public Field(Position lowerLeftHandCorner, Position upperRightHandCorner) {
        this.lowerLeftHandCorner = lowerLeftHandCorner;
        this.upperRightHandCorner = upperRightHandCorner;
        for (int x = lowerLeftHandCorner.getX(); x <= upperRightHandCorner.getX(); x++) {
            for (int y = lowerLeftHandCorner.getY(); y <= upperRightHandCorner.getY(); y++) {
                mowingIndex.put(x, y, Boolean.FALSE);
            }
        }
    }

    public Position getLowerLeftHandCorner() {
        return lowerLeftHandCorner;
    }

    public void setLowerLeftHandCorner(Position lowerLeftHandCorner) {
        this.lowerLeftHandCorner = lowerLeftHandCorner;
    }

    public Position getUpperRightHandCorner() {
        return upperRightHandCorner;
    }

    public void setUpperRightHandCorner(Position upperRightHandCorner) {
        this.upperRightHandCorner = upperRightHandCorner;
    }

    public boolean isMowed() {
        return !mowingIndex.containsValue(Boolean.FALSE);
    }

    public boolean isMowed(Position position) {

        Boolean isPositionMowed = mowingIndex.get(position.getX(), position.getX());
        return (isPositionMowed != null && isPositionMowed);
    }
}
