package org.dbaron.mower.service;

import org.dbaron.mower.model.Rotation;
import org.dbaron.mower.model.Translation;

/**
 * Created by dbaron on 01/02/15.
 */
public interface MoveProviderService {

    Translation getTranslation(String moveCode);

    Rotation getRotation(String moveCode);
}
