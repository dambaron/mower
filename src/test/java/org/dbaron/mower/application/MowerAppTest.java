package org.dbaron.mower.application;

import com.google.common.collect.ImmutableSet;
import org.dbaron.mower.model.Configuration;
import org.dbaron.mower.parser.BasicConfigurationParser;
import org.dbaron.mower.service.MoveProviderServiceImpl;
import org.dbaron.mower.service.MowerServiceImpl;
import org.dbaron.mower.service.PointProviderServiceImpl;
import org.dbaron.mower.validation.PositionValidator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;


public class MowerAppTest {

    private static final ImmutableSet<String> ALLOWED_ORIENTATIONS = ImmutableSet.of("N", "S", "W", "E");
    private static final ImmutableSet<String> ALLOWED_MOVES = ImmutableSet.of("G", "D", "A");

    private final BasicConfigurationParser basicConfigurationParser =
            new BasicConfigurationParser(ALLOWED_ORIENTATIONS, ALLOWED_MOVES);

    private final PositionValidator positionValidator = new PositionValidator();
    private final PointProviderServiceImpl pointProviderServiceImpl = new PointProviderServiceImpl();
    private final MoveProviderServiceImpl moveProviderServiceImpl = new MoveProviderServiceImpl();
    private final MowerServiceImpl mowerServiceImpl = new MowerServiceImpl();
    private final MowerApp mowerApp = new MowerApp();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {

        basicConfigurationParser.setMoveProviderService(moveProviderServiceImpl);

        mowerServiceImpl.setPositionValidator(positionValidator);
        mowerServiceImpl.setPointProviderService(pointProviderServiceImpl);

        mowerApp.setConfigurationParser(basicConfigurationParser);
        mowerApp.setMowerService(mowerServiceImpl);
    }

    @Test
    public void testLaunchConfigurationThrowsNullPointerExceptionWhenConfigurationIsNull() {

        exception.expect(NullPointerException.class);
        exception.expectMessage("configuration is required");
        Configuration configuration = null;
        mowerApp.launch(configuration);
    }

    @Test
    public void testLaunchConfiguration() {

        List<String> unparsedInputs = Arrays.asList("1 1",
                "0 0 N",
                "AAGADDAAGADDAAGADDAAGADD");

        Configuration configuration = basicConfigurationParser.parseConfiguration(unparsedInputs);
        mowerApp.launch(configuration);
        mowerApp.displayJourneys();
    }
}