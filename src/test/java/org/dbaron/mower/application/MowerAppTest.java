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


public class MowerAppTest {

    private static final ImmutableSet<String> ALLOWED_ORIENTATIONS = ImmutableSet.of("N", "S", "W", "E");
    private static final ImmutableSet<String> ALLOWED_MOVES = ImmutableSet.of("G", "D", "A");

    private BasicConfigurationParser basicConfigurationParser =
            new BasicConfigurationParser(ALLOWED_ORIENTATIONS, ALLOWED_MOVES);

    private PositionValidator positionValidator = new PositionValidator();
    private PointProviderServiceImpl pointProviderServiceImpl = new PointProviderServiceImpl();
    private MoveProviderServiceImpl moveProviderServiceImpl = new MoveProviderServiceImpl();
    private MowerServiceImpl mowerServiceImpl = new MowerServiceImpl();
    private MowerApp mowerApp = new MowerApp();

    @Before
    public void setUp() {

        basicConfigurationParser.setMoveProviderService(moveProviderServiceImpl);

        mowerServiceImpl.setPositionValidator(positionValidator);
        mowerServiceImpl.setPointProviderService(pointProviderServiceImpl);

        mowerApp.setConfigurationParser(basicConfigurationParser);
        mowerApp.setMowerService(mowerServiceImpl);
    }

    @Test
    public void testLaunchConfigurationFile() throws Exception {

    }

    @Test
    public void testLaunchConfiguration() throws Exception {

        List<String> unparsedInputs = Arrays.asList("5 5",
                "1 2 N",
                "GAGAGAGAA",
                "3 3 E",
                "AADAADADDA");

        Configuration configuration = basicConfigurationParser.parseConfiguration(unparsedInputs);
        mowerApp.launch(configuration);
        mowerApp.displayJourneys();
    }

    @Test
    public void testLaunchConfiguration2() throws Exception {

        List<String> unparsedInputs = Arrays.asList("1 1",
                "0 0 N",
                "AAGADDAAGADDAAGADDAAGADD");

        Configuration configuration = basicConfigurationParser.parseConfiguration(unparsedInputs);
        mowerApp.launch(configuration);
        mowerApp.displayJourneys();
    }
}