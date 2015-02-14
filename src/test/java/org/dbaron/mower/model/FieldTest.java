package org.dbaron.mower.model;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

public class FieldTest {

    private static final Position DEFAUT_LOWER_LEFT_HAND_CORNER = new Position(0, 0);
    private static final Position DEFAUT_UPPER_RIGHT_HAND_CORNER = new Position(2, 2);

    private final Table<Integer, Integer, Boolean> defaultMowingIndex = HashBasedTable.create();
    private final Table<Integer, Integer, Boolean> allPositionsMowedIndex = HashBasedTable.create();
    private final Table<Integer, Integer, Boolean> noPositionMowedIndex = HashBasedTable.create();
    private final Table<Integer, Integer, Boolean> allButOnePositionMowedIndex = HashBasedTable.create();

    @Spy
    private Field spyField = new Field(DEFAUT_LOWER_LEFT_HAND_CORNER, DEFAUT_UPPER_RIGHT_HAND_CORNER);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        //Creating diagonal mowing index
        defaultMowingIndex.put(0, 0, Boolean.TRUE);
        defaultMowingIndex.put(0, 1, Boolean.FALSE);
        defaultMowingIndex.put(0, 2, Boolean.FALSE);
        defaultMowingIndex.put(1, 0, Boolean.FALSE);
        defaultMowingIndex.put(1, 1, Boolean.TRUE);
        defaultMowingIndex.put(1, 2, Boolean.FALSE);
        defaultMowingIndex.put(2, 0, Boolean.FALSE);
        defaultMowingIndex.put(2, 1, Boolean.FALSE);
        defaultMowingIndex.put(2, 2, Boolean.TRUE);

        //Creating mowing index where all positions are mowned
        allPositionsMowedIndex.put(0, 0, Boolean.TRUE);
        allPositionsMowedIndex.put(0, 1, Boolean.TRUE);
        allPositionsMowedIndex.put(1, 0, Boolean.TRUE);
        allPositionsMowedIndex.put(1, 1, Boolean.TRUE);

        //Creating mowing index where all positions are mowned but one
        allButOnePositionMowedIndex.put(0, 0, Boolean.TRUE);
        allButOnePositionMowedIndex.put(0, 1, Boolean.TRUE);
        allButOnePositionMowedIndex.put(1, 0, Boolean.FALSE);
        allButOnePositionMowedIndex.put(1, 1, Boolean.TRUE);

        //Creating mowing index where no position is mowned
        noPositionMowedIndex.put(0, 0, Boolean.FALSE);
        noPositionMowedIndex.put(0, 1, Boolean.FALSE);
        noPositionMowedIndex.put(1, 0, Boolean.FALSE);
        noPositionMowedIndex.put(1, 1, Boolean.FALSE);
    }

    @Test
    public void testConstructorThrowsNullPointerExceptionWhenLowerLeftHandCornerIsNull() {

        exception.expect(NullPointerException.class);
        exception.expectMessage("lowerLeftHandCorner is required");
        new Field(null, new Position());
    }

    @Test
    public void testConstructorThrowsIllegalArgumentExceptionWhenLowerLeftHandCornerHasNegativeX() {

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("X coordinate for lower left hand corner is negative");
        new Field(new Position(-1, 0), new Position(1, 1));
    }

    @Test
    public void testConstructorThrowsIllegalArgumentExceptionWhenLowerLeftHandCornerHasNegativeY() {

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Y coordinate for lower left hand corner is negative");
        new Field(new Position(0, -1), new Position(1, 1));
    }

    @Test
    public void testConstructorThrowsNullPointerExceptionWhenUpperRightHandCornerIsNull() {

        exception.expect(NullPointerException.class);
        exception.expectMessage("upperRightHandCorner is required");
        new Field(new Position(), null);
    }

    @Test
    public void testConstructorThrowsIllegalArgumentExceptionWhenUpperRightHandCornerHasNegativeX() {

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("X coordinate for upper right hand corner is negative");
        new Field(new Position(0, 0), new Position(-1, 1));
    }

    @Test
    public void testConstructorThrowsIllegalArgumentExceptionWhenUpperRightHandCornerHasNegativeY() {

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Y coordinate for upper right hand corner is negative");
        new Field(new Position(0, 0), new Position(1, -1));
    }

    @Test
    public void testConstructorThrowsIllegalArgumentExceptionWhenCornersHaveSameX() {

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("X coordinate for upper right hand corner must be greater than X coordinate for lower left hand corner");
        new Field(new Position(0, 0), new Position(0, 1));
    }

    @Test
    public void testConstructorThrowsIllegalArgumentExceptionWhenCornersHaveSameY() {

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Y coordinate for upper right hand corner must be greater than Y coordinate for lower left hand corner");
        new Field(new Position(0, 0), new Position(1, 0));
    }

    @Test
    public void testConstructor() {

        Field field = new Field(DEFAUT_LOWER_LEFT_HAND_CORNER, DEFAUT_UPPER_RIGHT_HAND_CORNER);

        assertThat(field, is(notNullValue()));

        assertThat(field.getLowerLeftHandCorner(), is(DEFAUT_LOWER_LEFT_HAND_CORNER));
        assertThat(field.getUpperRightHandCorner(), is(DEFAUT_UPPER_RIGHT_HAND_CORNER));

        int expectedMowingIndexSize = (
                (DEFAUT_UPPER_RIGHT_HAND_CORNER.getX() + 1)
                        * (DEFAUT_UPPER_RIGHT_HAND_CORNER.getY() + 1)
        );

        assertThat(field.getMowingIndex(), is(notNullValue()));

        int mowingIndexSize = field.getMowingIndex().size();
        assertThat(mowingIndexSize, is(expectedMowingIndexSize));
    }

    @Test
    public void testIsMowedForNullMowingIndex() {

        when(spyField.getMowingIndex()).thenReturn(null);
        assertThat(spyField.isMowed(), is(Boolean.FALSE));
    }

    @Test
    public void testIsMowedForAllPositionMowedIndex() {

        when(spyField.getMowingIndex()).thenReturn(allPositionsMowedIndex);
        assertThat(spyField.isMowed(), is(Boolean.TRUE));
    }

    @Test
    public void testIsMowedForNoPositionMowedIndex() {
        when(spyField.getMowingIndex()).thenReturn(noPositionMowedIndex);
        assertThat(spyField.isMowed(), is(Boolean.FALSE));
    }

    @Test
    public void testIsMowedForAllButOnPositionMowedIndex() {
        when(spyField.getMowingIndex()).thenReturn(allButOnePositionMowedIndex);
        assertThat(spyField.isMowed(), is(Boolean.FALSE));
    }

    @Test
    public void testIsMowedByPosition() {

        when(spyField.getMowingIndex()).thenReturn(defaultMowingIndex);

        for (int x = DEFAUT_LOWER_LEFT_HAND_CORNER.getX(); x <= DEFAUT_UPPER_RIGHT_HAND_CORNER.getX(); x++) {

            for (int y = DEFAUT_LOWER_LEFT_HAND_CORNER.getY(); y <= DEFAUT_UPPER_RIGHT_HAND_CORNER.getY(); y++) {

                assertThat(spyField.isMowed(x, y), is(defaultMowingIndex.get(x, y)));

            }
        }
    }
}