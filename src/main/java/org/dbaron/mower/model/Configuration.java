package org.dbaron.mower.model;

import org.apache.commons.lang3.Validate;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by dbaron on 29/01/15.
 */
public class Configuration {

    private Field field;

    private List<Point> startingPoints = new LinkedList<>();

    private List<List<Move>> moveSequences = new LinkedList<>();

    public Configuration() {
        this.field = null;
        this.startingPoints = null;
        this.moveSequences = null;
    }

    public Configuration(Field field, List<Point> startingPoints, List<List<Move>> moveSequences) {

        Validate.notNull(field, "field is required");
        Validate.notNull(field, "startingPoints is required");
        Validate.notNull(field, "moveSequences is required");

        this.field = field;
        this.startingPoints = startingPoints;
        this.moveSequences = moveSequences;
    }

    public Field getField() {
        return field;
    }

    public List<Point> getStartingPoints() {
        return startingPoints;
    }

    public List<List<Move>> getMoveSequences() {
        return moveSequences;
    }
}