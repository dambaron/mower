package org.dbaron.mower.model;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.apache.commons.lang3.Validate;

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

        Validate.notNull(lowerLeftHandCorner, "lowerLeftHandCorner is required");
        Validate.notNull(upperRightHandCorner, "upperRightHandCorner is required");

        Validate.isTrue(lowerLeftHandCorner.getX() >= 0, "X coordinate for lower left hand corner is negative");
        Validate.isTrue(lowerLeftHandCorner.getY() >= 0, "Y coordinate for lower left hand corner is negative");

        Validate.isTrue(upperRightHandCorner.getX() >= 0, "X coordinate for upper right hand corner is negative");
        Validate.isTrue(upperRightHandCorner.getY() >= 0, "Y coordinate for upper right hand corner is negative");

        Validate.isTrue(upperRightHandCorner.getX() > lowerLeftHandCorner.getX(),
                "X coordinate for upper right hand corner must be greater than X coordinate for lower left hand corner");

        Validate.isTrue(upperRightHandCorner.getY() > lowerLeftHandCorner.getY(),
                "Y coordinate for upper right hand corner must be greater than Y coordinate for lower left hand corner");

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

    public Table<Integer, Integer, Boolean> getMowingIndex() {
        return mowingIndex;
    }

    public boolean isMowed() {
        return getMowingIndex() != null && !getMowingIndex().containsValue(Boolean.FALSE);
    }

    public boolean isMowed(int x, int y) {
        return getMowingIndex() != null && getMowingIndex().get(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Field field = (Field) o;

        if (lowerLeftHandCorner != null ? !lowerLeftHandCorner.equals(field.lowerLeftHandCorner) : field.lowerLeftHandCorner != null)
            return false;
        if (upperRightHandCorner != null ? !upperRightHandCorner.equals(field.upperRightHandCorner) : field.upperRightHandCorner != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = lowerLeftHandCorner != null ? lowerLeftHandCorner.hashCode() : 0;
        result = 31 * result + (upperRightHandCorner != null ? upperRightHandCorner.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Field{");
        sb.append("lowerLeftHandCorner=").append(lowerLeftHandCorner);
        sb.append(", upperRightHandCorner=").append(upperRightHandCorner);
        sb.append('}');
        return sb.toString();
    }
}