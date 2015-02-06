package org.dbaron.mower.model;

import org.dbaron.mower.model.reference.CardinalOrientation;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MowerTest {

    private static final Position POSITION_0_0 = new Position(0, 0);
    private static final Position POSITION_1_1 = new Position(1, 1);
    private static final Position POSITION_2_2 = new Position(2, 2);
    private static final Position POSITION_3_3 = new Position(3, 3);

    private static final Orientation ORIENTATION_N = new Orientation(CardinalOrientation.N.getCode());
    private static final Orientation ORIENTATION_S = new Orientation(CardinalOrientation.S.getCode());
    private static final Orientation ORIENTATION_W = new Orientation(CardinalOrientation.W.getCode());
    private static final Orientation ORIENTATION_E = new Orientation(CardinalOrientation.E.getCode());

    private static final WayPoint DEFAULT_WAY_POINT = new WayPoint(POSITION_0_0, ORIENTATION_N);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testOneParameterConstructorThrowsNullPointerExceptionWhenInitialWayPointIsNull() {

        exception.expect(NullPointerException.class);
        exception.expectMessage("initialWayPoint is required");
        new Mower(null);
    }

    @Test
    public void testTwoParametersConstructorThrowsNullPointerExceptionWhenInitialWayPointIsNull() {

        exception.expect(NullPointerException.class);
        exception.expectMessage("initialWayPoint is required");
        new Mower(null, Collections.<Move>emptyList());
    }

    @Test
    public void testTwoParametersConstructorThrowsNullPointerExceptionWhenMoveSequenceIsNull() {

        exception.expect(NullPointerException.class);
        exception.expectMessage("moveSequence is required");
        new Mower(new WayPoint(), null);
    }

    @Test
    public void testSetMoveSequenceThrowsNullPointerExceptionWhenMoveSequenceIsNull() {

        exception.expect(NullPointerException.class);
        exception.expectMessage("moveSequence is required");
        Mower mower = new Mower(DEFAULT_WAY_POINT);
        mower.setMoveSequence(null);
    }

    @Test
    public void testGetPositionWhenWayPointsIsEmpty() {

        Mower mower = new Mower(DEFAULT_WAY_POINT);
        mower.getWayPoints().clear();
        mower.getWayPoints().addAll(Collections.<WayPoint>emptyList());
        assertThat(mower.getPosition(), is(DEFAULT_WAY_POINT.getPosition()));
    }

    @Test
    public void testGetPosition() {

        Mower mower = new Mower(DEFAULT_WAY_POINT);
        mower.getWayPoints().add(new WayPoint(POSITION_0_0, ORIENTATION_S));
        mower.getWayPoints().add(new WayPoint(POSITION_1_1, ORIENTATION_W));
        mower.getWayPoints().add(new WayPoint(POSITION_2_2, ORIENTATION_E));
        mower.getWayPoints().add(new WayPoint(POSITION_3_3, ORIENTATION_S));

        assertThat(mower.getPosition(), is(POSITION_3_3));
    }

    @Test
    public void testGetOrientationWhenWayPointsIsEmpty() {

        Mower mower = new Mower(DEFAULT_WAY_POINT);
        mower.getWayPoints().clear();
        mower.getWayPoints().addAll(Collections.<WayPoint>emptyList());

        assertThat(mower.getOrientation(), is(DEFAULT_WAY_POINT.getOrientation()));
    }

    @Test
    public void testGetOrientation() {

        Mower mower = new Mower(DEFAULT_WAY_POINT);
        mower.getWayPoints().add(new WayPoint(POSITION_0_0, ORIENTATION_S));
        mower.getWayPoints().add(new WayPoint(POSITION_1_1, ORIENTATION_W));
        mower.getWayPoints().add(new WayPoint(POSITION_2_2, ORIENTATION_E));
        mower.getWayPoints().add(new WayPoint(POSITION_3_3, ORIENTATION_S));

        assertThat(mower.getOrientation(), is(ORIENTATION_S));
    }
}