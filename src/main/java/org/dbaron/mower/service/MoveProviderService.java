package org.dbaron.mower.service;

import org.dbaron.mower.model.Rotation;
import org.dbaron.mower.model.Translation;

/**
 * A service for computing moves
 * Created by dbaron on 01/02/15.
 */
public interface MoveProviderService {

    /**
     * Builds a translation object from string
     * @param moveCode - a move code
     * @return a translation
     */
    Translation getTranslation(String moveCode);

    /**
     * Builds a rotation object from string
     * @param moveCode - a move code
     * @return a rotation
     */
    Rotation getRotation(String moveCode);
}