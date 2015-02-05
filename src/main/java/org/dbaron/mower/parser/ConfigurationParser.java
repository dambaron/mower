package org.dbaron.mower.parser;

import org.dbaron.mower.model.Configuration;
import org.dbaron.mower.model.Field;
import org.dbaron.mower.model.Move;
import org.dbaron.mower.model.Point;

import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * Created by dbaron on 29/01/15.
 */
public interface ConfigurationParser {

    Set<String> getOrientationsDictionnary();

    Set<String> getMovesDictionnary();

    Configuration parseConfiguration(List<String> configurationElements);
    Configuration parseConfiguration(File file);

    List<String> parseField(String fieldDefinition);
    List<String> parsePoint(String pointDefinition);
    List<String> parseMoves(String moveSequence);

    void validateField(List<String> fieldElements);
    void validatePoint(List<String> pointElements);
    void validateMoves(List<String> moveElements);

    Field buildField(String fieldDefinition);
    Point buildPoint(String pointDefinition);
    List<Move> buildMoves(String moveSequence);
}