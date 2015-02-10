package org.dbaron.mower.service;

import org.apache.commons.lang3.Validate;
import org.dbaron.mower.model.Rotation;
import org.dbaron.mower.model.Translation;
import org.dbaron.mower.model.reference.CartesianRotation;
import org.dbaron.mower.model.reference.CartesianTranslation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dbaron on 01/02/15.
 */
public class MoveProviderServiceImpl implements MoveProviderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoveProviderServiceImpl.class);

    @Override
    public Translation getTranslation(String moveCode) {
        Validate.notBlank(moveCode, "moveCode is blank");

        try {
            CartesianTranslation ct = CartesianTranslation.valueOf(moveCode);
            return new Translation(ct.getCode());
        } catch (IllegalArgumentException iae) {
            LOGGER.error("Code {} was not found in the translations referential. Returning null",
                    moveCode);
            return null;
        }
    }

    @Override
    public Rotation getRotation(String moveCode) {
        Validate.notBlank(moveCode, "moveCode is blank");

        try {
            CartesianRotation cartesianRotation = CartesianRotation.valueOf(moveCode);
            return new Rotation(cartesianRotation.getCode());
        } catch (IllegalArgumentException iae) {
            LOGGER.error("Code {} was not found in the rotations referential. Returning null",
                    moveCode);
            return null;
        }
    }
}