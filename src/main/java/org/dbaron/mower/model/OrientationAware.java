package org.dbaron.mower.model;

/**
 * Created by dbaron on 27/01/15.
 */
public interface OrientationAware {

    Orientation getOrientation();

    Point updateOrientation(Orientation orientation);
}