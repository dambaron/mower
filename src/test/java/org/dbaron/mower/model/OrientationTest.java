package org.dbaron.mower.model;

import org.dbaron.mower.model.reference.CardinalOrientation;
import org.dbaron.mower.model.reference.CartesianRotation;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class OrientationTest {

    private static final Orientation ORIENTATION_N = new Orientation(CardinalOrientation.N.getCode());
    private static final Orientation ORIENTATION_S = new Orientation(CardinalOrientation.S.getCode());
    private static final Orientation ORIENTATION_W = new Orientation(CardinalOrientation.W.getCode());
    private static final Orientation ORIENTATION_E = new Orientation(CardinalOrientation.E.getCode());

    @Test
    public void testApplyRotation() {

        final String leftRotation = CartesianRotation.G.getCode();

        assertThat(ORIENTATION_N.applyRotation(leftRotation),
                is(ORIENTATION_W));

        assertThat(ORIENTATION_W.applyRotation(leftRotation),
                is(ORIENTATION_S));

        assertThat(ORIENTATION_S.applyRotation(leftRotation),
                is(ORIENTATION_E));

        assertThat(ORIENTATION_E.applyRotation(leftRotation),
                is(ORIENTATION_N));

        final String rightRotation = CartesianRotation.D.getCode();

        assertThat(ORIENTATION_N.applyRotation(rightRotation),
                is(ORIENTATION_E));

        assertThat(ORIENTATION_E.applyRotation(rightRotation),
                is(ORIENTATION_S));

        assertThat(ORIENTATION_S.applyRotation(rightRotation),
                is(ORIENTATION_W));

        assertThat(ORIENTATION_W.applyRotation(rightRotation),
                is(ORIENTATION_N));
    }
}