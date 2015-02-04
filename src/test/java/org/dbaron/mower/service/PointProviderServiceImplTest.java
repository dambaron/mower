package org.dbaron.mower.service;

import org.dbaron.mower.model.Move;
import org.dbaron.mower.model.Orientation;
import org.dbaron.mower.model.Point;
import org.dbaron.mower.model.Position;
import org.dbaron.mower.model.Rotation;
import org.dbaron.mower.model.Translation;
import org.dbaron.mower.model.reference.CardinalOrientation;
import org.dbaron.mower.model.reference.CartesianRotation;
import org.dbaron.mower.model.reference.CartesianTranslation;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PointProviderServiceImplTest {

    private static final Orientation ORIENTATION_N = new Orientation(CardinalOrientation.N.getCode());
    private static final Orientation ORIENTATION_S = new Orientation(CardinalOrientation.S.getCode());
    private static final Orientation ORIENTATION_W = new Orientation(CardinalOrientation.W.getCode());
    private static final Orientation ORIENTATION_E = new Orientation(CardinalOrientation.E.getCode());

    private static final Position POSITION_0_1 = new Position(0, 1);
    private static final Position POSITION_1_0 = new Position(1, 0);
    private static final Position POSITION_1_1 = new Position(1, 1);
    private static final Position POSITION_1_2 = new Position(1, 2);
    private static final Position POSITION_2_1 = new Position(2, 1);

    private static final Point POINT_1_1_N = new Point(POSITION_1_1, ORIENTATION_N);
    private static final Point POINT_1_1_S = new Point(POSITION_1_1, ORIENTATION_S);
    private static final Point POINT_1_1_W = new Point(POSITION_1_1, ORIENTATION_W);
    private static final Point POINT_1_1_E = new Point(POSITION_1_1, ORIENTATION_E);

    private static final Move MOVE_A = new Move(CartesianTranslation.A.getCode());
    private static final String UNKNOWN_MOVE_CODE = "X";
    private static final Move UNKNOWN_MOVE = new Move(UNKNOWN_MOVE_CODE);

    private final PointProviderServiceImpl pointProviderServiceImpl = new PointProviderServiceImpl();

    @Test(expected = NullPointerException.class)
    public void testApplyMoveThrowsNullPointerExceptionWhenStartingPointIsNull() {
        pointProviderServiceImpl.applyMove(MOVE_A, null);
    }

    @Test(expected = NullPointerException.class)
    public void testApplyMoveThrowsNullPointerExceptionWhenMoveIsNull() {
        pointProviderServiceImpl.applyMove(null, POINT_1_1_N);
    }

    @Test
    public void testApplyMoveForUnknownMove() {

        Point nextPoint = pointProviderServiceImpl.applyMove(UNKNOWN_MOVE, POINT_1_1_N);
        assertThat(nextPoint, is(POINT_1_1_N));
    }

    @Test
    public void testApplyMove() {

        final Move forwardMove = new Move(CartesianTranslation.A.getCode());
        final Move backWardMove = new Move(CartesianTranslation.R.getCode());
        final Move leftMove = new Move(CartesianRotation.G.getCode());
        final Move rightMove = new Move(CartesianRotation.D.getCode());

        // 1,1,N -> A -> 1,2,N
        Point nextPoint = pointProviderServiceImpl.applyMove(forwardMove, POINT_1_1_N);
        assertThat(nextPoint, is(new Point(POSITION_1_2, ORIENTATION_N)));

        // 1,1,N -> R -> 1,0,N
        nextPoint = pointProviderServiceImpl.applyMove(backWardMove, POINT_1_1_N);
        assertThat(nextPoint, is(new Point(POSITION_1_0, ORIENTATION_N)));

        // 1,1,N -> G -> 1,1,W
        nextPoint = pointProviderServiceImpl.applyMove(leftMove, POINT_1_1_N);
        assertThat(nextPoint, is(POINT_1_1_W));

        // 1,1,N -> D -> 1,1,E
        nextPoint = pointProviderServiceImpl.applyMove(rightMove, POINT_1_1_N);
        assertThat(nextPoint, is(POINT_1_1_E));

        // 1,1,W -> A -> 0,1,W
        nextPoint = pointProviderServiceImpl.applyMove(forwardMove, POINT_1_1_W);
        assertThat(nextPoint, is(new Point(POSITION_0_1, ORIENTATION_W)));

        // 1,1,W -> R -> 2,1,W
        nextPoint = pointProviderServiceImpl.applyMove(backWardMove, POINT_1_1_W);
        assertThat(nextPoint, is(new Point(POSITION_2_1, ORIENTATION_W)));

        // 1,1,W -> G -> 1,1,S
        nextPoint = pointProviderServiceImpl.applyMove(leftMove, POINT_1_1_W);
        assertThat(nextPoint, is(POINT_1_1_S));

        // 1,1,W -> D -> 1,1,N
        nextPoint = pointProviderServiceImpl.applyMove(rightMove, POINT_1_1_W);
        assertThat(nextPoint, is(POINT_1_1_N));

        // 1,1,S -> A -> 1,0,S
        nextPoint = pointProviderServiceImpl.applyMove(forwardMove, POINT_1_1_S);
        assertThat(nextPoint, is(new Point(POSITION_1_0, ORIENTATION_S)));

        // 1,1,S -> R -> 1,2,S
        nextPoint = pointProviderServiceImpl.applyMove(backWardMove, POINT_1_1_S);
        assertThat(nextPoint, is(new Point(POSITION_1_2, ORIENTATION_S)));

        // 1,1,S -> G -> 1,1,E
        nextPoint = pointProviderServiceImpl.applyMove(leftMove, POINT_1_1_S);
        assertThat(nextPoint, is(POINT_1_1_E));

        // 1,1,S -> D -> 1,1,W
        nextPoint = pointProviderServiceImpl.applyMove(rightMove, POINT_1_1_S);
        assertThat(nextPoint, is(POINT_1_1_W));

        // 1,1,E -> A -> 2,1,E
        nextPoint = pointProviderServiceImpl.applyMove(forwardMove, POINT_1_1_E);
        assertThat(nextPoint, is(new Point(POSITION_2_1, ORIENTATION_E)));

        // 1,1,E -> R -> 0,1,E
        nextPoint = pointProviderServiceImpl.applyMove(backWardMove, POINT_1_1_E);
        assertThat(nextPoint, is(new Point(POSITION_0_1, ORIENTATION_E)));

        // 1,1,E -> G -> 1,1,N
        nextPoint = pointProviderServiceImpl.applyMove(leftMove, POINT_1_1_E);
        assertThat(nextPoint, is(POINT_1_1_N));

        // 1,1,E -> D -> 1,1,S
        nextPoint = pointProviderServiceImpl.applyMove(rightMove, POINT_1_1_E);
        assertThat(nextPoint, is(POINT_1_1_S));
    }
    
    @Test
    public void testApplyRotation() {

        final Rotation leftRotation = new Rotation(CartesianRotation.G.getCode());
        final Rotation rightRotation = new Rotation(CartesianRotation.D.getCode());

        // 1,1,N -> G -> 1,1,W
        Point nextPoint = pointProviderServiceImpl.applyRotation(leftRotation, POINT_1_1_N);
        assertThat(nextPoint, is(POINT_1_1_W));

        // 1,1,N -> D -> 1,1,E
        nextPoint = pointProviderServiceImpl.applyRotation(rightRotation, POINT_1_1_N);
        assertThat(nextPoint, is(POINT_1_1_E));

        // 1,1,W -> G -> 1,1,S
        nextPoint = pointProviderServiceImpl.applyRotation(leftRotation, POINT_1_1_W);
        assertThat(nextPoint, is(POINT_1_1_S));

        // 1,1,W -> D -> 1,1,N
        nextPoint = pointProviderServiceImpl.applyRotation(rightRotation, POINT_1_1_W);
        assertThat(nextPoint, is(POINT_1_1_N));

        // 1,1,S -> G -> 1,1,E
        nextPoint = pointProviderServiceImpl.applyRotation(leftRotation, POINT_1_1_S);
        assertThat(nextPoint, is(POINT_1_1_E));

        // 1,1,S -> D -> 1,1,W
        nextPoint = pointProviderServiceImpl.applyRotation(rightRotation, POINT_1_1_S);
        assertThat(nextPoint, is(POINT_1_1_W));

        // 1,1,E -> G -> 1,1,N
        nextPoint = pointProviderServiceImpl.applyRotation(leftRotation, POINT_1_1_E);
        assertThat(nextPoint, is(POINT_1_1_N));

        // 1,1,E -> D -> 1,1,S
        nextPoint = pointProviderServiceImpl.applyRotation(rightRotation, POINT_1_1_E);
        assertThat(nextPoint, is(POINT_1_1_S));
    }

    @Test
    public void testApplyTranslation() {

        final Translation forwardTranslation = new Translation(CartesianTranslation.A.getCode());
        final Translation backWardTranslation = new Translation(CartesianTranslation.R.getCode());

        // 1,1,N -> A -> 1,2,N
        Point nextPoint = pointProviderServiceImpl.applyTranslation(forwardTranslation, POINT_1_1_N);
        assertThat(nextPoint, is(new Point(POSITION_1_2, ORIENTATION_N)));

        // 1,1,N -> R -> 1,0,N
        nextPoint = pointProviderServiceImpl.applyTranslation(backWardTranslation, POINT_1_1_N);
        assertThat(nextPoint, is(new Point(POSITION_1_0, ORIENTATION_N)));

        // 1,1,W -> A -> 0,1,W
        nextPoint = pointProviderServiceImpl.applyTranslation(forwardTranslation, POINT_1_1_W);
        assertThat(nextPoint, is(new Point(POSITION_0_1, ORIENTATION_W)));

        // 1,1,W -> R -> 2,1,W
        nextPoint = pointProviderServiceImpl.applyTranslation(backWardTranslation, POINT_1_1_W);
        assertThat(nextPoint, is(new Point(POSITION_2_1, ORIENTATION_W)));

        // 1,1,S -> A -> 1,0,S
        nextPoint = pointProviderServiceImpl.applyTranslation(forwardTranslation, POINT_1_1_S);
        assertThat(nextPoint, is(new Point(POSITION_1_0, ORIENTATION_S)));

        // 1,1,S -> R -> 1,2,S
        nextPoint = pointProviderServiceImpl.applyTranslation(backWardTranslation, POINT_1_1_S);
        assertThat(nextPoint, is(new Point(POSITION_1_2, ORIENTATION_S)));

        // 1,1,E -> A -> 2,1,E
        nextPoint = pointProviderServiceImpl.applyTranslation(forwardTranslation, POINT_1_1_E);
        assertThat(nextPoint, is(new Point(POSITION_2_1, ORIENTATION_E)));

        // 1,1,E -> R -> 0,1,E
        nextPoint = pointProviderServiceImpl.applyTranslation(backWardTranslation, POINT_1_1_E);
        assertThat(nextPoint, is(new Point(POSITION_0_1, ORIENTATION_E)));
    }
}