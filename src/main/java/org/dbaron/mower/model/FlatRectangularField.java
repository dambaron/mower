package org.dbaron.mower.model;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

/**
 * Created by dbaron on 27/01/15.
 */
public class FlatRectangularField extends Field {

    private CartesianPosition lowerLeftHandCorner;
    private CartesianPosition upperRightHandCorner;

    private Table<Integer, Integer, Boolean> mowingIndex = HashBasedTable.create();

    public FlatRectangularField() {
        this.lowerLeftHandCorner = null;
        this.upperRightHandCorner = null;
    }

    public FlatRectangularField(CartesianPosition lowerLeftHandCorner, CartesianPosition upperRightHandCorner) {
        this.lowerLeftHandCorner = lowerLeftHandCorner;
        this.upperRightHandCorner = upperRightHandCorner;
    }

    public CartesianPosition getLowerLeftHandCorner() {
        return lowerLeftHandCorner;
    }

    public void setLowerLeftHandCorner(CartesianPosition lowerLeftHandCorner) {
        this.lowerLeftHandCorner = lowerLeftHandCorner;
    }

    public CartesianPosition getUpperRightHandCorner() {
        return upperRightHandCorner;
    }

    public void setUpperRightHandCorner(CartesianPosition upperRightHandCorner) {
        this.upperRightHandCorner = upperRightHandCorner;
    }

    @Override
    public boolean isMowed() {
        return false;
    }

    @Override
    public boolean isMowed(Position position) {
        return false;
    }
}
