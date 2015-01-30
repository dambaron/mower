package org.dbaron.mower.parser;

import com.google.common.base.CharMatcher;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.dbaron.mower.model.Configuration;
import org.dbaron.mower.model.Field;
import org.dbaron.mower.model.Move;
import org.dbaron.mower.model.Orientation;
import org.dbaron.mower.model.Point;
import org.dbaron.mower.model.Position;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dbaron on 29/01/15.
 */
public class FileConfigurationParser extends BasicConfigurationParser {

    private File file;

    public Field getField(String line) throws IllegalArgumentException {

        Validate.notNull(line, "line is required");
        String[] split = StringUtils.split(line);

        if (split.length != 2) {
            throw new IllegalArgumentException();
        }


        Field field = new Field();
        return field;
    }

    public Point getStartingPoint(String line) {

        Validate.notNull(line, "line is required");
        String[] split = StringUtils.split(line);

        if (split.length != 3
                || !StringUtils.isNumeric(split[0])
                || !StringUtils.isNumeric(split[1])
                || !getOrientationsDictionnary().contains(split[2])){
            throw new IllegalArgumentException();
        }

        Position position = new Position(Integer.valueOf(split[0]),
                Integer.valueOf(split[1]));

        Orientation orientation = new Orientation(split[2]);

        return new Point(position, orientation);
    }

    public List<Move> getMoves(String line) {

        Validate.notNull(line, "line is required");

        List<Move> moves = new LinkedList<>();
        return moves;
    }

    public FileConfigurationParser(List<String> orientationsDictionnary,
                                   List<String> movesDictionnary,
                                   File file) {
        super(orientationsDictionnary, movesDictionnary);
        this.file = file;
    }

    public void validateField(List<String> field) {

        Validate.notNull(field, "field is required");
        String errorMessage;
        for (String fieldElement : field) {

            String remainder = CharMatcher.DIGIT.retainFrom(fieldElement);
            if (StringUtils.isNotEmpty(remainder)) {
                errorMessage = "Error : " + fieldElement + " is not a valid element";
                throw new IllegalArgumentException(errorMessage);
            }
        }
    }

    public void validateStartingPoint(List<String> startingPoint) {
        Validate.notNull(startingPoint, "startingPoint is required");
    }

    public void validateMoves(List<String> moves) {
        Validate.notNull(moves, "moves is required");
    }

    public void parse(File file) {

    }
}
