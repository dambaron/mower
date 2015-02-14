package org.dbaron.mower.application;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.lang3.Validate;
import org.dbaron.mower.model.Configuration;
import org.dbaron.mower.model.Field;
import org.dbaron.mower.model.Move;
import org.dbaron.mower.model.Mower;
import org.dbaron.mower.model.Orientation;
import org.dbaron.mower.model.Point;
import org.dbaron.mower.model.Position;
import org.dbaron.mower.model.WayPoint;
import org.dbaron.mower.parser.ConfigurationParser;
import org.dbaron.mower.service.MowerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

/**
 * Created by dbaron on 28/01/15.
 */
public class MowerApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(MowerApp.class);

    // CLI related objects
    private static CommandLineParser parser = new PosixParser();
    private static Options appOptions = new Options();

    //Services
    @Autowired
    private ConfigurationParser configurationParser;

    @Autowired
    private MowerService mowerService;

    public MowerApp() {
        //DO NOTHING
    }

    public static void main(String[] args) {

        // init application context
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring/mower-spring-context.xml");

        // init available options
        initOptions();

        // parse the command line arguments group by group
        try {
            // create the parser
            CommandLine line = parser.parse(appOptions, args);

            if (line.hasOption("c")) {

                String configurationFile = line.getOptionValue("c");

                LOGGER.info("Launching mower(s)");
                MowerApp mowerApp = (MowerApp) context.getBean("mowerApp");
                mowerApp.launch(configurationFile);

                LOGGER.info("Mower(s) stopped");
                mowerApp.displayJourneys();

                System.exit(0);
            }
        } catch (ParseException pe) {

            // deal with parse error
            LOGGER.error("Parsing failed. Reason: " + pe.getMessage());
            usage();
            System.exit(-1);
        }
    }

    /**
     * Init CLI options
     */
    protected static void initOptions() {

        // create the Options
        Option config = OptionBuilder
                .isRequired()
                .hasArg()
                .withArgName("file")
                .withLongOpt("config")
                .withDescription("use given file as config")
                .create("c");

        // add options to all options
        appOptions.addOption(config);
    }

    /**
     * Display how to use the MowerApp
     */
    protected static void usage() {

        // Generate help statement
        LOGGER.info("usage: java MowerApp -c,--config <file>");
    }

    public ConfigurationParser getConfigurationParser() {
        return configurationParser;
    }

    public void setConfigurationParser(ConfigurationParser configurationParser) {
        this.configurationParser = configurationParser;
    }

    public MowerService getMowerService() {
        return mowerService;
    }

    public void setMowerService(MowerService mowerService) {
        this.mowerService = mowerService;
    }

    /**
     * Launch a mow based on a given gonfiguration file
     * @param pathToConfigurationFile - the path to the configuration file
     */
    public void launch(String pathToConfigurationFile) {
        Validate.notNull(pathToConfigurationFile, "pathToConfigurationFile is required");

        File configurationFile = new File(pathToConfigurationFile);
        if (!configurationFile.exists()) {

            LOGGER.error("Configuration file {} doesn't exist", configurationFile);
        } else {

            Configuration configuration = configurationParser.parseConfiguration(configurationFile);
            launch(configuration);
        }
    }

    /**
     * Launch a mow with the given configuration
     * @param configuration - the configuration the mowers will use
     */
    public void launch(Configuration configuration) {
        Validate.notNull(configuration, "configuration is required");

        Field field = configuration.getField();
        List<Point> startingPoints = configuration.getStartingPoints();
        ListIterator<Point> listIterator = startingPoints.listIterator();

        int index;
        while(listIterator.hasNext()) {

            index = listIterator.nextIndex();
            Point startingPoint = listIterator.next();
            List<Move> moveSequence = configuration.getMoveSequences().get(index);

            final Position startingPosition = startingPoint.getPosition();
            final Orientation startingOrientation = startingPoint.getOrientation();

            WayPoint startingWayPoint = new WayPoint(startingPosition,
                    startingOrientation);

            Mower mower = new Mower(startingWayPoint, moveSequence);

            mowerService.registerField(field);
            mowerService.registerMower(mower, field);
            mowerService.mow(field, mower);
        }
    }

    /**
     * Display the journeys of all mowers on all fields
     */
    public void displayJourneys() {

        Set<Field> fields = this.getMowerService().getRegisteredFields();
        for (Field field : fields) {

            List<Mower> mowers = this.getMowerService().getRegisteredMowers(field);

            LOGGER.info("Mowing results for {}", field);
            for (Mower mower : mowers) {

                LOGGER.info("One mower stopped at {}, {}",
                        mower.getPosition(),
                        mower.getOrientation());

                LOGGER.info("The path was : {}", mower.getDisplayablePath());

                LOGGER.info("Skipped moves : {}", mower.getSkippedMoves());
            }
        }
    }
}