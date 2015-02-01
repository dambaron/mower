package org.dbaron.mower.service;

import org.apache.commons.lang3.Validate;
import org.dbaron.mower.model.Rotation;
import org.dbaron.mower.model.Translation;
import org.dbaron.mower.model.reference.CartesianRotation;
import org.dbaron.mower.model.reference.CartesianTranslation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.datatransfer.Transferable;

/**
 * Created by dbaron on 01/02/15.
 */
public class MoveProviderServiceImpl implements MoveProviderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoveProviderServiceImpl.class);

    @Override
    public Translation getTranslation(String moveCode) {
        Validate.notBlank(moveCode, "moveCode is blank");

        Translation translation = null;
        try {
            CartesianTranslation ct = CartesianTranslation.valueOf(moveCode);
            translation = new Translation(ct.getCode());
        } catch (IllegalArgumentException iae) {
            LOGGER.debug("Code {} was not found in the translations referential. Returning null",
                    moveCode);
        }
        return translation;
    }

    @Override
    public Rotation getRotation(String moveCode) {
        Validate.notBlank(moveCode, "moveCode is blank");

        Rotation rotation = null;
        try {
            CartesianRotation cartesianRotation = CartesianRotation.valueOf(moveCode);
            rotation = new Rotation(cartesianRotation.getCode());
        } catch (IllegalArgumentException iae) {
            LOGGER.debug("Code {} was not found in the rotations referential. Returning null",
                    moveCode);
        }
        return rotation;
    }
}
