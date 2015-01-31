package org.dbaron.mower.validation;

import com.google.common.collect.ImmutableList;
import org.dbaron.mower.exception.MowingException;
import org.dbaron.mower.exception.OccupiedPositionException;
import org.dbaron.mower.exception.OutOfFieldException;
import org.dbaron.mower.model.Field;
import org.dbaron.mower.model.Mower;
import org.dbaron.mower.model.Orientation;
import org.dbaron.mower.model.Position;
import org.dbaron.mower.model.WayPoint;
import org.dbaron.mower.model.reference.CardinalOrientation;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PositionValidatorTest {

    private static final Position DEFAULT_POSITION = new Position(1, 1);
    private static final Orientation DEFAULT_ORIENTATION = new Orientation(CardinalOrientation.NORTH.getCode());

    private static final WayPoint WAY_POINT_0_0_N = new WayPoint(
            new Position(0, 0),
            DEFAULT_ORIENTATION);

    private static final WayPoint WAY_POINT_1_1_N = new WayPoint(
            new Position(1, 1),
            DEFAULT_ORIENTATION);

    private static final Position DEFAUT_LOWER_LEFT_HAND_CORNER = new Position(0, 0);
    private static final Position DEFAUT_UPPER_RIGHT_HAND_CORNER = new Position(2, 2);

    private static final Field DEFAULT_FIELD = new Field(DEFAUT_LOWER_LEFT_HAND_CORNER,
            DEFAUT_UPPER_RIGHT_HAND_CORNER);

    private PositionValidator positionValidator = new PositionValidator();

    @Test(expected = NullPointerException.class)
    public void testValidateIsInsideFieldThrowsNullPointerExceptionWhenPositionIsNull() {
        positionValidator.validateIsInsideField(null, DEFAULT_FIELD);
    }

    @Test(expected = NullPointerException.class)
    public void testValidateIsInsideFieldThrowsNullPointerExceptionWhenFieldIsNull() {
        positionValidator.validateIsInsideField(DEFAULT_POSITION, null);
    }

    @Test
    public void testValidateIsInsideFieldThrowsOutOfFieldException() {

        ImmutableList<Position> outOfFieldPositions = ImmutableList.of(
                new Position(0, 2),
                new Position(1, 2),
                new Position(2, 2),
                new Position(2, 1),
                new Position(2, 0)
        );

        for (Position outOfFieldPosition : outOfFieldPositions.asList()) {

            try {
                positionValidator.validateIsInsideField(outOfFieldPosition, DEFAULT_FIELD);
            } catch (MowingException me) {
                assertThat(me, is(instanceOf(OutOfFieldException.class)));
            }
        }
    }

    @Test
    public void testValidateIsInsideField() {
        positionValidator.validateIsInsideField(DEFAULT_POSITION, DEFAULT_FIELD);
    }

    @Test(expected = NullPointerException.class)
    public void testValidateIsFreePositionThrowsNullPointerExceptionWhenPositionIsNull() {
        positionValidator.validateIsFreePosition(null, new LinkedList<Mower>());
    }

    @Test(expected = NullPointerException.class)
    public void testValidateIsFreePositionThrowsNullPointerExceptionWhenMowersIsNull() {
        positionValidator.validateIsFreePosition(new Position(), null);
    }

    @Test
    public void testValidateIsFreePositionThrowsOccupiedPositionException() {

        List<Mower> mowersOnTheDiagonal = new LinkedList<>();
        mowersOnTheDiagonal.add(new Mower(WAY_POINT_0_0_N));
        mowersOnTheDiagonal.add(new Mower(WAY_POINT_1_1_N));

        List<Position> positions = new LinkedList<>();
        positions.add(new Position(0, 0));
        positions.add(new Position(1, 1));

        for (Position position : positions) {

            try {
                positionValidator.validateIsFreePosition(position, mowersOnTheDiagonal);
            } catch (MowingException me) {
                assertThat(me, is(instanceOf(OccupiedPositionException.class)));
            }
        }
    }
}