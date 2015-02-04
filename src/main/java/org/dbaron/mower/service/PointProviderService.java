package org.dbaron.mower.service;

import org.dbaron.mower.model.Move;
import org.dbaron.mower.model.Point;

/**
 * Created by dbaron on 27/01/15.
 */
public interface PointProviderService {

    Point getNextPoint(Point startingPoint, Move move);
}
