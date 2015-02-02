package org.dbaron.mower.application;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dbaron on 28/01/15.
 */
public class MowerApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(MowerApp.class);

    private static CommandLineParser parser = new PosixParser();
    private static HelpFormatter formatter = new HelpFormatter();
    private static Options appOptions = new Options();

    public static void main(String[] args) {

        // init available options
        initOptions();

        // parse the command line arguments group by group

        try {
            // create the parser
            CommandLine line = parser.parse(appOptions, args);

            if (line.hasOption("c")) {

                LOGGER.info("Launching mower(s)");
                LOGGER.info("Mower(s) stopped");
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
     * @return
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

    protected static void usage() {

        // Generate help statement
        LOGGER.info("usage: java MowerApp -c,--config <file>");
    }
}
