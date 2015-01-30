package org.dbaron.mower.parser;

import com.google.common.collect.ImmutableList;
import org.dbaron.mower.model.Configuration;
import org.dbaron.mower.model.Field;
import org.dbaron.mower.model.Move;
import org.dbaron.mower.model.Point;

import java.util.List;

/**
 * Created by dbaron on 29/01/15.
 */
public abstract class AbstractConfigurationParser implements ConfigurationParser {

    private ImmutableList<String> orientationsDictionnary;
    private ImmutableList<String> movesDictionnary;

    public AbstractConfigurationParser(List<String> orientationsDictionnary,
                                       List<String> movesDictionnary) {

        this.orientationsDictionnary = ImmutableList.<String>builder()
                .addAll(orientationsDictionnary)
                .build();

        this.movesDictionnary = ImmutableList.<String>builder()
                .addAll(movesDictionnary)
                .build();
    }

    @Override
    public List<String> getOrientationsDictionnary() {
        return orientationsDictionnary.asList();
    }

    @Override
    public List<String> getMovesDictionnary() {
        return movesDictionnary.asList();
    }

    @Override
    public Configuration parse() {
        throw new UnsupportedOperationException("Method should be implemented in subclass");
    }

    @Override
    public Field parseField(String field) {
        throw new UnsupportedOperationException("Method should be implemented in subclass");
    }

    @Override
    public Point parserPoint(String pointDefinition) {
        throw new UnsupportedOperationException("Method should be implemented in subclass");
    }

    @Override
    public List<Move> parseMoveSequence(String moveSequence) {
        throw new UnsupportedOperationException("Method should be implemented in subclass");
    }

    @Override
    public void validateField(List<String> fieldElements) {
        throw new UnsupportedOperationException("Method should be implemented in subclass");
    }

    @Override
    public void validatePoint(List<String> pointElements) {
        throw new UnsupportedOperationException("Method should be implemented in subclass");
    }

    @Override
    public void validateMoves(List<String> moves) {
        throw new UnsupportedOperationException("Method should be implemented in subclass");
    }
}