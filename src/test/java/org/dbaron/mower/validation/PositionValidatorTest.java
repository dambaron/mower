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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.LinkedList;
import java.util.List;

import static junit.framework.Assert.fail;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PositionValidatorTest {

    private static final Position DEFAULT_POSITION = new Position(1, 1);
    private static final Orientation DEFAULT_ORIENTATION = new Orientation(
            CardinalOrientation.N.getCode());

    private static final WayPoint WAY_POINT_0_0_N = new WayPoint(
            new Position(0, 0),
            DEFAULT_ORIENTATION);

    private static final WayPoint WAY_POINT_1_1_N = new WayPoint(
            new Position(1, 1),
            DEFAULT_ORIENTATION);

    private static final Position DEFAULT_LOWER_LEFT_HAND_CORNER = new Position(0, 0);
    private static final Position DEFAULT_UPPER_RIGHT_HAND_CORNER = new Position(1, 1);

    private static final Field DEFAULT_FIELD = new Field(DEFAULT_LOWER_LEFT_HAND_CORNER,
            DEFAULT_UPPER_RIGHT_HAND_CORNER);

    private final PositionValidator positionValidator = new PositionValidator();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testValidateIsInsideFieldThrowsNullPointerExceptionWhenPositionIsNull() {

        exception.expect(NullPointerException.class);
        exception.expectMessage("position is required");
        positionValidator.validateIsInsideField(null, DEFAULT_FIELD);
    }

    @Test
    public void testValidateIsInsideFieldThrowsNullPointerExceptionWhenFieldIsNull() {

        exception.expect(NullPointerException.class);
        exception.expectMessage("field is required");
        positionValidator.validateIsInsideField(DEFAULT_POSITION, null);
    }

    @Test
    public void testValidateIsInsideFieldThrowsOutOfFieldException() {

        ImmutableList<Position> outOfFieldPositions = ImmutableList.of(
                new Position(0, 2),
                new Position(1, 2),
                new Position(2, 2),
                new Position(2, 1),
                new Position(2, 0));

        for (Position outOfFieldPosition : outOfFieldPositions.asList()) {

            try {
                positionValidator.validateIsInsideField(outOfFieldPosition, DEFAULT_FIELD);
                fail();
            } catch (OutOfFieldException oofe) {
                //DO NOTHING
            }
        }
    }

    @Test
    public void testValidateIsInsideField() {

        ImmutableList<Position> inFieldPositions = ImmutableList.of(
                new Position(0, 0),
                new Position(0, 1),
                new Position(1, 0),
                new Position(1, 1));

        for (Position inFieldPosition : inFieldPositions.asList()) {
            positionValidator.validateIsInsideField(inFieldPosition, DEFAULT_FIELD);
        }
    }

    @Test
    public void testValidateIsFreePositionThrowsNullPointerExceptionWhenPositionIsNull() {

        exception.expect(NullPointerException.class);
        exception.expectMessage("position is required");
        positionValidator.validateIsFreePosition(null,
                new Mower(),
                new LinkedList<Mower>());
    }

    @Test
    public void testValidateIsFreePositionThrowsNullPointerExceptionWhenRunningMowerIsNull() {

        exception.expect(NullPointerException.class);
        exception.expectMessage("runningMower is required");
        positionValidator.validateIsFreePosition(new Position(),
                null,
                new LinkedList<Mower>());
    }

    @Test
    public void testValidateIsFreePositionThrowsNullPointerExceptionWhenMowersIsNull() {

        exception.expect(NullPointerException.class);
        exception.expectMessage("mowers is required");
        positionValidator.validateIsFreePosition(new Position(),
                new Mower(),
                null);
    }

    @Test
    public void testValidateIsFreePositionThrowsOccupiedPositionException() {

        Mower runningMower = new Mower();

        List<Mower> mowersOnTheDiagonal = new LinkedList<>();
        mowersOnTheDiagonal.add(new Mower(WAY_POINT_0_0_N));
        mowersOnTheDiagonal.add(new Mower(WAY_POINT_1_1_N));

        List<Position> positions = new LinkedList<>();
        positions.add(new Position(0, 0));
        positions.add(new Position(1, 1));

        for (Position position : positions) {

            try {
                positionValidator.validateIsFreePosition(position,
                        runningMower,
                        mowersOnTheDiagonal);
                fail();
            } catch (MowingException me) {
                assertThat(me, is(instanceOf(OccupiedPositionException.class)));
            }
        }
    }
}