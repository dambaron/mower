package org.dbaron.mower.parser;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.dbaron.mower.model.Configuration;
import org.dbaron.mower.model.Field;
import org.dbaron.mower.model.Move;
import org.dbaron.mower.model.Orientation;
import org.dbaron.mower.model.Point;
import org.dbaron.mower.model.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by dbaron on 29/01/15.
 */
public class BasicConfigurationParser extends AbstractConfigurationParser implements ConfigurationParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicConfigurationParser.class);

    public BasicConfigurationParser(Set<String> orientationsDictionnary,
                                    Set<String> movesDictionnary) {
        super(orientationsDictionnary, movesDictionnary);
    }

    @Override
    public Set<String> getOrientationsDictionnary() {
        return super.getOrientationsDictionnary();
    }

    @Override
    public Set<String> getMovesDictionnary() {
        return super.getMovesDictionnary();
    }

    @Override
    public Configuration parseConfiguration() {
        return null;
    }

    private List<String> parseInput(String input) {
        Validate.notBlank(input, "input was blank");

        return Arrays.asList(StringUtils.split(input));
    }

    @Override
    public List<String> parseField(String fieldDefinition) {
        return parseInput(fieldDefinition);
    }

    @Override
    public List<String> parsePoint(String pointDefinition) {
        return parseInput(pointDefinition);
    }

    @Override
    public List<String> parseMoves(String moveSequence) {
        return parseMoves(moveSequence);
    }

    private void validateNumericValue(String value) {
        if (!StringUtils.isNumeric(value)) {
            LOGGER.error("Numeric value expected. Found {}", value);
            throw new IllegalArgumentException("Numeric value expected. Found " + value);
        }
    }

    private void validateDictionnaryValue(String value, Set<String> dictionnary) {
        Validate.notNull(value);
        Validate.notNull(dictionnary);

        if (!dictionnary.contains(value)) {
            LOGGER.error("Unknown value in dictionnary : {}", value);
            throw new IllegalArgumentException("Unknown value in dictionnary : " + value);
        }
    }

    private void validateDictionnaryValues(List<String> values, Set<String> dictionnary) {
        Validate.notNull(values);
        Validate.notNull(dictionnary);

        Set<String> valueSet = new HashSet<>();
        valueSet.addAll(values);
        if (!dictionnary.containsAll(valueSet)) {

            Sets.SetView<String> difference = Sets.difference(valueSet, dictionnary);
            LOGGER.error("Unknown values in dictionnary : {}", difference);
            throw new IllegalArgumentException("Unknown values in dictionnary : " + difference);
        }
    }

    @Override
    public void validateField(List<String> fieldElements) {
        Validate.notEmpty(fieldElements, "fieldElements was empty");

        if (fieldElements.size() != 2) {
            LOGGER.error("Wrong number of elements in field definition");
            throw new IllegalArgumentException("Wrong number of elements in field definition");
        }

        validateNumericValue(fieldElements.get(0));
        validateNumericValue(fieldElements.get(1));
    }

    @Override
    public void validatePoint(List<String> pointElements) {
        Validate.notEmpty(pointElements, "pointElements was empty");

        if (pointElements.size() != 3) {
            LOGGER.error("Wrong number of elements in point definition");
            throw new IllegalArgumentException("Wrong number of elements in point definition");
        }

        validateNumericValue(pointElements.get(0));
        validateNumericValue(pointElements.get(1));
        validateDictionnaryValue(pointElements.get(2), getOrientationsDictionnary());
    }

    @Override
    public void validateMoves(List<String> moveElements) {
        Validate.notEmpty(moveElements, "moveElements was empty");

        validateDictionnaryValues(moveElements, getMovesDictionnary());
    }

    @Override
    public Field buildField(String fieldDefinition) {
        Validate.notBlank(fieldDefinition, "fieldDefinition was blank");

        List<String> fieldElements = parseField(fieldDefinition);
        validateField(fieldElements);

        Position lowerLeftHandCorner = new Position(0, 0);
        Position upperRightHandCorner = new Position();
        upperRightHandCorner.setX(Integer.valueOf(fieldElements.get(0)));
        upperRightHandCorner.setY(Integer.valueOf(fieldElements.get(1)));

        return new Field(lowerLeftHandCorner, upperRightHandCorner);
    }

    @Override
    public Point buildPoint(String pointDefinition) {
        Validate.notBlank(pointDefinition, "pointDefinition was blank");

        List<String> pointElements = parsePoint(pointDefinition);
        validatePoint(pointElements);

        Position position = new Position();
        position.setX(Integer.valueOf(pointElements.get(0)));
        position.setY(Integer.valueOf(pointElements.get(1)));

        Orientation orientation = new Orientation();
        orientation.setCode(pointElements.get(2));

        return new Point(position, orientation);
    }

    @Override
    public List<Move> buildMoves(String moveSequence) {
        Validate.notBlank(moveSequence, "moveSequence was blank");

        List<String> moveElements = parseMoves(moveSequence);
        validateMoves(moveElements);

        List<Move> moves = new LinkedList<>();
        for (String moveElement : moveElements) {
            moves.add(new Move(moveElement));
        }

        return moves;
    }
}