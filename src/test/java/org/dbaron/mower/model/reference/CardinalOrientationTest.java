package org.dbaron.mower.model.reference;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CardinalOrientationTest {

    @Test
    public void testGetNextCardinalOrientationByCode() {

        final String leftRotation = CartesianRotation.G.getCode();

        assertThat(CardinalOrientation.N.getNextCardinalOrientation(leftRotation),
                is(CardinalOrientation.W));

        assertThat(CardinalOrientation.W.getNextCardinalOrientation(leftRotation),
                is(CardinalOrientation.S));

        assertThat(CardinalOrientation.S.getNextCardinalOrientation(leftRotation),
                is(CardinalOrientation.E));

        assertThat(CardinalOrientation.E.getNextCardinalOrientation(leftRotation),
                is(CardinalOrientation.N));

        final String rightRotation = CartesianRotation.D.getCode();

        assertThat(CardinalOrientation.N.getNextCardinalOrientation(rightRotation),
                is(CardinalOrientation.E));

        assertThat(CardinalOrientation.E.getNextCardinalOrientation(rightRotation),
                is(CardinalOrientation.S));

        assertThat(CardinalOrientation.S.getNextCardinalOrientation(rightRotation),
                is(CardinalOrientation.W));

        assertThat(CardinalOrientation.W.getNextCardinalOrientation(rightRotation),
                is(CardinalOrientation.N));

    }
}