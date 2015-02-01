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
import org.dbaron.mower.model.Rotation;
import org.dbaron.mower.model.Translation;
import org.dbaron.mower.service.MoveProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
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

    private MoveProviderService moveProviderService;

    public MoveProviderService getMoveProviderService() {
        return moveProviderService;
    }

    public void setMoveProviderService(MoveProviderService moveProviderService) {
        this.moveProviderService = moveProviderService;
    }

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
    public Configuration parseConfiguration(List<String> configurationElements) {
        Validate.notNull(configurationElements);

        int nbElements = configurationElements.size();
        if (nbElements < 3) {
            LOGGER.error("Configuration should contain 3 elements at least");
            throw new IllegalArgumentException("Configuration should contain 3 elements at least");
        }

        if (nbElements % 2 == 0) {
            LOGGER.error("Configuration should contain an odd number of elements");
            throw new IllegalArgumentException("Configuration should contain an odd number of elements");
        }

        Field field = buildField(configurationElements.get(0));

        List<Point> startingPoints = new LinkedList<>();
        List<List<Move>> moveSequences = new LinkedList<>();

        for (int i = 1; i < configurationElements.size(); i = i + 2) {

            Point startingPoint = buildPoint(configurationElements.get(i));
            List<Move> moves = buildMoves(configurationElements.get(i + 1));

            startingPoints.add(startingPoint);
            moveSequences.add(moves);
        }

        return new Configuration(field, startingPoints, moveSequences);
    }

    @Override
    public Configuration parseConfiguration(File file) {
        throw new UnsupportedOperationException();
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
        Validate.notBlank(moveSequence, "moveSequence was blank");

        List<String> moves = new LinkedList<>();
        char[] chars = moveSequence.toCharArray();
        for (char aChar : chars) {
            moves.add(String.valueOf(aChar));
        }
        return moves;
    }

    private void validateNumericValue(String value) {
        if (!StringUtils.isNumeric(value)) {
            LOGGER.error("Numeric value expected. Found {}", value);
            throw new IllegalArgumentException("Numeric value expected. Found " + value);
        }
    }

    private void validateDictionnaryValue(String value, Set<String> dictionnary) {

        validateDictionnaryValues(Arrays.asList(value), dictionnary);
    }

    private void validateDictionnaryValues(List<String> values, Set<String> dictionnary) {
        Validate.notNull(values);
        Validate.notNull(dictionnary);

        Set<String> valueSet = new HashSet<>();
        valueSet.addAll(values);
        if (!dictionnary.containsAll(valueSet)) {

            Sets.SetView<String> difference = Sets.difference(valueSet, dictionnary);
            LOGGER.error("Unknown values found : {}", difference);
            throw new IllegalArgumentException("Unknown values found : " + difference);
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

            Translation translation = moveProviderService.getTranslation(moveElement);
            Rotation rotation = moveProviderService.getRotation(moveElement);

            if (translation == null && rotation == null) {
                LOGGER.error("No suitable move found for code {}", moveElement);
                throw new IllegalArgumentException("No suitable move found for " + moveElement);
            }

            if (translation != null && rotation != null) {
                LOGGER.error("Code {} applies to both translation and rotation", moveElement);
                throw new IllegalArgumentException("Code " + moveElement + " applies to both translation and rotation");
            }

            if (translation != null && rotation == null) {
                LOGGER.debug("Adding translation to moves");
                moves.add(translation);
            }

            if (translation == null && rotation != null) {
                LOGGER.debug("Adding rotation to moves");
                moves.add(rotation);
            }
        }

        return moves;
    }
}