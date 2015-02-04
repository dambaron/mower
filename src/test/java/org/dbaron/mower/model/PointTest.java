package org.dbaron.mower.model;

import org.dbaron.mower.model.reference.CardinalOrientation;
import org.dbaron.mower.model.reference.CartesianRotation;
import org.dbaron.mower.model.reference.CartesianTranslation;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PointTest {

    private static final Position POSITION_0_1 = new Position(0, 1);
    private static final Position POSITION_2_1 = new Position(2, 1);

    private static final Position POSITION_1_1 = new Position(1, 1);

    private static final Position POSITION_1_0 = new Position(1, 0);
    private static final Position POSITION_1_2 = new Position(1, 2);

    private static final Orientation ORIENTATION_N = new Orientation(CardinalOrientation.N.getCode());
    private static final Orientation ORIENTATION_S = new Orientation(CardinalOrientation.S.getCode());
    private static final Orientation ORIENTATION_W = new Orientation(CardinalOrientation.W.getCode());
    private static final Orientation ORIENTATION_E = new Orientation(CardinalOrientation.E.getCode());

    private static final Point POINT_1_1_N = new Point(POSITION_1_1, ORIENTATION_N);
    private static final Point POINT_1_1_S = new Point(POSITION_1_1, ORIENTATION_S);
    private static final Point POINT_1_1_W = new Point(POSITION_1_1, ORIENTATION_W);
    private static final Point POINT_1_1_E = new Point(POSITION_1_1, ORIENTATION_E);

    @Test
    public void testApplyMove() {

        final String forwardMove = CartesianTranslation.A.getCode();
        final String backWardMove = CartesianTranslation.R.getCode();
        final String leftMove = CartesianRotation.G.getCode();
        final String rightMove = CartesianRotation.D.getCode();

        // 1,1,N -> A -> 1,2,N
        assertThat(POINT_1_1_N.applyMove(forwardMove), is(new Point(POSITION_1_2, ORIENTATION_N)));

        // 1,1,N -> R -> 1,0,N
        assertThat(POINT_1_1_N.applyMove(backWardMove), is(new Point(POSITION_1_0, ORIENTATION_N)));

        // 1,1,N -> G -> 1,1,W
        assertThat(POINT_1_1_N.applyMove(leftMove), is(POINT_1_1_W));

        // 1,1,N -> D -> 1,1,E
        assertThat(POINT_1_1_N.applyMove(rightMove), is(POINT_1_1_E));

        // 1,1,W -> A -> 0,1,W
        assertThat(POINT_1_1_W.applyMove(forwardMove), is(new Point(POSITION_0_1, ORIENTATION_W)));

        // 1,1,W -> R -> 2,1,W
        assertThat(POINT_1_1_W.applyMove(backWardMove), is(new Point(POSITION_2_1, ORIENTATION_W)));

        // 1,1,W -> G -> 1,1,S
        assertThat(POINT_1_1_W.applyMove(leftMove), is(POINT_1_1_S));

        // 1,1,W -> D -> 1,1,N
        assertThat(POINT_1_1_W.applyMove(rightMove), is(POINT_1_1_N));

        // 1,1,S -> A -> 1,0,S
        assertThat(POINT_1_1_S.applyMove(forwardMove), is(new Point(POSITION_1_0, ORIENTATION_S)));

        // 1,1,S -> R -> 1,2,S
        assertThat(POINT_1_1_S.applyMove(backWardMove), is(new Point(POSITION_1_2, ORIENTATION_S)));

        // 1,1,S -> G -> 1,1,E
        assertThat(POINT_1_1_S.applyMove(leftMove), is(POINT_1_1_E));

        // 1,1,S -> D -> 1,1,W
        assertThat(POINT_1_1_S.applyMove(rightMove), is(POINT_1_1_W));

        // 1,1,E -> A -> 2,1,E
        assertThat(POINT_1_1_E.applyMove(forwardMove), is(new Point(POSITION_2_1, ORIENTATION_E)));

        // 1,1,E -> R -> 0,1,E
        assertThat(POINT_1_1_E.applyMove(backWardMove), is(new Point(POSITION_0_1, ORIENTATION_E)));

        // 1,1,E -> G -> 1,1,N
        assertThat(POINT_1_1_E.applyMove(leftMove), is(POINT_1_1_N));

        // 1,1,E -> D -> 1,1,S
        assertThat(POINT_1_1_E.applyMove(rightMove), is(POINT_1_1_S));
    }
}