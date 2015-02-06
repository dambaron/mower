package org.dbaron.mower.application;

import com.google.common.collect.ImmutableSet;
import org.dbaron.mower.model.Configuration;
import org.dbaron.mower.parser.BasicConfigurationParser;
import org.dbaron.mower.service.MoveProviderServiceImpl;
import org.dbaron.mower.service.MowerServiceImpl;
import org.dbaron.mower.service.PointProviderServiceImpl;
import org.dbaron.mower.validation.PositionValidator;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

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
    }
}
