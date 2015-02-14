package org.dbaron.mower.application;

import com.google.common.collect.ImmutableSet;
import org.dbaron.mower.model.Configuration;
import org.dbaron.mower.model.Field;
import org.dbaron.mower.model.Move;
import org.dbaron.mower.model.Mower;
import org.dbaron.mower.model.Orientation;
import org.dbaron.mower.model.Point;
import org.dbaron.mower.model.Position;
import org.dbaron.mower.model.WayPoint;
import org.dbaron.mower.parser.BasicConfigurationParser;
import org.dbaron.mower.service.MoveProviderServiceImpl;
import org.dbaron.mower.service.MowerServiceImpl;
import org.dbaron.mower.service.PointProviderServiceImpl;
import org.dbaron.mower.validation.PositionValidator;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by dbaron on 06/02/15.
 */
public class MowerAppAcceptanceTest {

    private static final ImmutableSet<String> ALLOWED_ORIENTATIONS = ImmutableSet.of("N", "S", "W", "E");
    private static final ImmutableSet<String> ALLOWED_MOVES = ImmutableSet.of("G", "D", "A");

    private final BasicConfigurationParser basicConfigurationParser =
            new BasicConfigurationParser(ALLOWED_ORIENTATIONS, ALLOWED_MOVES);

    private final PositionValidator positionValidator = new PositionValidator();
    private final PointProviderServiceImpl pointProviderServiceImpl = new PointProviderServiceImpl();
    private final MoveProviderServiceImpl moveProviderServiceImpl = new MoveProviderServiceImpl();
    private final MowerServiceImpl mowerServiceImpl = new MowerServiceImpl();
    private final MowerApp mowerApp = new MowerApp();

    @Before
    public void setUp() {

        basicConfigurationParser.setMoveProviderService(moveProviderServiceImpl);

        mowerServiceImpl.setPositionValidator(positionValidator);
        mowerServiceImpl.setPointProviderService(pointProviderServiceImpl);

        mowerApp.setConfigurationParser(basicConfigurationParser);
        mowerApp.setMowerService(mowerServiceImpl);
    }

    @Test
    public void testLaunchConfiguration() {

        List<String> unparsedInputs = Arrays.asList("5 5",
                "1 2 N",
                "GAGAGAGAA",
                "3 3 E",
                "AADAADADDA");

        Configuration configuration = basicConfigurationParser.parseConfiguration(unparsedInputs);
        mowerApp.launch(configuration);
        mowerApp.displayJourneys();

        //Check configuration

        //Check field
        Field fieldFromConfiguration = configuration.getField();
        assertThat(fieldFromConfiguration, is(notNullValue()));

        //Lower left hand corner with x=0, y=0
        assertThat(fieldFromConfiguration.getLowerLeftHandCorner(), is(notNullValue()));
        assertThat(fieldFromConfiguration.getLowerLeftHandCorner().getX(), is(0));
        assertThat(fieldFromConfiguration.getLowerLeftHandCorner().getY(), is(0));

        //Lower left hand corner with x=5, y=5
        assertThat(fieldFromConfiguration.getUpperRightHandCorner(), is(notNullValue()));
        assertThat(fieldFromConfiguration.getUpperRightHandCorner().getX(), is(5));
        assertThat(fieldFromConfiguration.getUpperRightHandCorner().getY(), is(5));

        //Check starting points
        List<Point> startingPointsFromConfiguration = configuration.getStartingPoints();
        assertThat(startingPointsFromConfiguration, is(notNullValue()));
        assertThat(startingPointsFromConfiguration.isEmpty(), is(false));

        //Two starting points should have been found
        assertThat(startingPointsFromConfiguration.size(), is(2));
        for (Point startingPoint : startingPointsFromConfiguration) {
            assertThat(startingPoint, is(notNullValue()));
            assertThat(startingPoint.getPosition(), is(notNullValue()));
            assertThat(startingPoint.getOrientation(), is(notNullValue()));
        }

        //First starting point at x=1, y=2, pointing north
        assertThat(startingPointsFromConfiguration.get(0).getPosition().getX(), is(1));
        assertThat(startingPointsFromConfiguration.get(0).getPosition().getY(), is(2));
        assertThat(startingPointsFromConfiguration.get(0).getOrientation().getCode(), is("N"));

        //Second starting point at x=3, y=3, pointing east
        assertThat(startingPointsFromConfiguration.get(1).getPosition().getX(), is(3));
        assertThat(startingPointsFromConfiguration.get(1).getPosition().getY(), is(3));
        assertThat(startingPointsFromConfiguration.get(1).getOrientation().getCode(), is("E"));

        //Check move sequences
        List<List<Move>> moveSequencesFromConfiguration = configuration.getMoveSequences();
        assertThat(moveSequencesFromConfiguration, is(notNullValue()));

        //Two sequences should be found
        assertThat(moveSequencesFromConfiguration.size(), is(2));
        for (List<Move> moves : moveSequencesFromConfiguration) {
            assertThat(moves, is(notNullValue()));
            assertThat(moves.isEmpty(), is(false));
        }

        //First sequence should contain 9 commands
        assertThat(moveSequencesFromConfiguration.get(0).size(), is(9));

        //Second sequence should contain 10 commands
        assertThat(moveSequencesFromConfiguration.get(1).size(), is(10));

        //Check mower service

        //Check registered fields
        Set<Field> registeredFields = mowerApp.getMowerService()
                .getRegisteredFields();

        assertThat(registeredFields, is(notNullValue()));

        //One field only should be registered
        assertThat(registeredFields.isEmpty(), is(false));
        assertThat(registeredFields.size(), is(1));

        //Registered field within service should be the same as the one found in configuration
        assertThat(registeredFields.contains(fieldFromConfiguration), is(true));

        //Check registered mowers
        List<Mower> registeredMowers = mowerApp.getMowerService()
                .getRegisteredMowers(fieldFromConfiguration);

        assertThat(registeredMowers, is(notNullValue()));

        //Two mowers should be registered
        assertThat(registeredMowers.size(), is(2));

        for (Mower registeredMower : registeredMowers) {
            assertThat(registeredMower, is(notNullValue()));

            //No moves should have been skipped during mowning
            assertThat(registeredMower.getSkippedMoves(), is(0));

            final WayPoint initialWayPoint = registeredMower.getInitialWayPoint();

            assertThat(initialWayPoint, is(notNullValue()));
            assertThat(initialWayPoint.getPosition(), is(notNullValue()));
            assertThat(initialWayPoint.getOrientation(), is(notNullValue()));

            final Position position = registeredMower.getPosition();
            final Orientation orientation = registeredMower.getOrientation();

            assertThat(position, is(notNullValue()));
            assertThat(orientation, is(notNullValue()));

            final List<WayPoint> wayPoints = registeredMower.getWayPoints();
            assertThat(wayPoints, is(notNullValue()));
            assertThat(wayPoints.isEmpty(), is(false));

            //During mowing each Move turns into a WayPoint
            //The initial WayPoint is also added to the WayPoint list
            //So size(WayPoint List) = size(MoveSequence) + 1
            final List<Move> moveSequence = registeredMower.getMoveSequence();
            assertThat(moveSequence, is(notNullValue()));
            assertThat(moveSequence.size(), is(wayPoints.size() - 1));
        }

        //First mower should have stopped at x=1, y=3, pointing North
        assertThat(registeredMowers.get(0).getPosition().getX(), is(1));
        assertThat(registeredMowers.get(0).getPosition().getY(), is(3));
        assertThat(registeredMowers.get(0).getOrientation().getCode(), is("N"));

        //Second mower should have stopped at x=5, y=1, pointing East
        assertThat(registeredMowers.get(1).getPosition().getX(), is(5));
        assertThat(registeredMowers.get(1).getPosition().getY(), is(1));
        assertThat(registeredMowers.get(1).getOrientation().getCode(), is("E"));
    }
}
