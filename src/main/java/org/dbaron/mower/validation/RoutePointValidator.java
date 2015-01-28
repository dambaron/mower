package org.dbaron.mower.validation;

import org.dbaron.mower.exception.OccupiedPositionException;
import org.dbaron.mower.exception.OutOfFieldException;
import org.dbaron.mower.model.Field;
import org.dbaron.mower.model.Mower;
import org.dbaron.mower.model.RoutePoint;

import java.util.List;

/**
 * Created by dbaron on 28/01/15.
 */
public class RoutePointValidator {

    public void validateIsInsideField(RoutePoint routePoint, Field field) throws OutOfFieldException {

    }

    public void validateIsFreePosition(RoutePoint routePoint, List<Mower> mowers) throws OccupiedPositionException {

    }
}
