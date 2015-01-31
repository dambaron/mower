package org.dbaron.mower.model;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

public class FieldTest {

    private static final Position DEFAUT_LOWER_LEFT_HAND_CORNER = new Position(0, 0);
    private static final Position DEFAUT_UPPER_RIGHT_HAND_CORNER = new Position(2, 2);

    private Table<Integer, Integer, Boolean> defaultMowingIndex = HashBasedTable.create();
    private Table<Integer, Integer, Boolean> allPositionMowedIndex = HashBasedTable.create();
    private Table<Integer, Integer, Boolean> noPositionMowedIndex = HashBasedTable.create();
    private Table<Integer, Integer, Boolean> allButOnePositionMowedIndex = HashBasedTable.create();

    @Spy
    Field spyField = new Field(DEFAUT_LOWER_LEFT_HAND_CORNER, DEFAUT_UPPER_RIGHT_HAND_CORNER);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        defaultMowingIndex.put(0, 0, Boolean.TRUE);
        defaultMowingIndex.put(0, 1, Boolean.FALSE);
        defaultMowingIndex.put(0, 2, Boolean.FALSE);
        defaultMowingIndex.put(1, 0, Boolean.FALSE);
        defaultMowingIndex.put(1, 1, Boolean.TRUE);
        defaultMowingIndex.put(1, 2, Boolean.FALSE);
        defaultMowingIndex.put(2, 0, Boolean.FALSE);
        defaultMowingIndex.put(2, 1, Boolean.FALSE);
        defaultMowingIndex.put(2, 2, Boolean.TRUE);

        allPositionMowedIndex.put(0, 0, Boolean.TRUE);
        allPositionMowedIndex.put(0, 1, Boolean.TRUE);
        allPositionMowedIndex.put(1, 0, Boolean.TRUE);
        allPositionMowedIndex.put(1, 1, Boolean.TRUE);

        allButOnePositionMowedIndex.put(0, 0, Boolean.TRUE);
        allButOnePositionMowedIndex.put(0, 1, Boolean.TRUE);
        allButOnePositionMowedIndex.put(1, 0, Boolean.FALSE);
        allButOnePositionMowedIndex.put(1, 1, Boolean.TRUE);

        noPositionMowedIndex.put(0, 0, Boolean.FALSE);
        noPositionMowedIndex.put(0, 1, Boolean.FALSE);
        noPositionMowedIndex.put(1, 0, Boolean.FALSE);
        noPositionMowedIndex.put(1, 1, Boolean.FALSE);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorThrowsNullPointerExceptionWhenLowerLeftHandCornerIsNull() {
        new Field(null, new Position());
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorThrowsNullPointerExceptionWhenUpperRightHandCornerIsNull() {
        new Field(new Position(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorThrowsIllegalArgumentExceptionWhenCornersHaveSameX() {
        new Field(new Position(0, 0), new Position(0, 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorThrowsIllegalArgumentExceptionWhenCornersHaveSameY() {
        new Field(new Position(0, 0), new Position(1, 0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorThrowsIllegalArgumentExceptionWhenCornersHaveSameXY() {
        new Field(new Position(0, 0), new Position(0, 0));
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

        when(spyField.getMowingIndex()).thenReturn(allPositionMowedIndex);
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