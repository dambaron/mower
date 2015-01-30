package org.dbaron.mower.parser;

import org.dbaron.mower.model.Configuration;
import org.dbaron.mower.model.Field;
import org.dbaron.mower.model.Move;
import org.dbaron.mower.model.Point;

import java.util.List;

/**
 * Created by dbaron on 29/01/15.
 */
public interface ConfigurationParser {

    List<String> getOrientationsDictionnary();

    List<String> getMovesDictionnary();

    Configuration parse();

    Field parseField(String field);

    Point parserPoint(String pointDefinition);

    List<Move> parseMoveSequence(String moveSequence);

    void validateField(List<String> fieldElements);

    void validatePoint(List<String> pointElements);

    void validateMoves(List<String> moves);
}
