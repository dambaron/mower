package org.dbaron.mower.service;

import org.dbaron.mower.model.Move;
import org.dbaron.mower.model.Point;
import org.dbaron.mower.model.Rotation;
import org.dbaron.mower.model.Translation;

/**
 * Created by dbaron on 27/01/15.
 */
public interface PointProviderService {

    Point applyMove(Move move, Point startingPoint);

    Point applyTranslation(Translation translation, Point startingPoint);

    Point applyRotation(Rotation rotation, Point startingPoint);
}
