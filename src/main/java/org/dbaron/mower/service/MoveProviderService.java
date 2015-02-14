package org.dbaron.mower.service;

import org.dbaron.mower.model.Rotation;
import org.dbaron.mower.model.Translation;

/**
 * Created by dbaron on 01/02/15.
 */
public interface MoveProviderService {

    /**
     * Builds a translation object from string
     * @param moveCode
     * @return
     */
    Translation getTranslation(String moveCode);

    /**
     * Builds a rotation object from string
     * @param moveCode
     * @return
     */
    Rotation getRotation(String moveCode);
}