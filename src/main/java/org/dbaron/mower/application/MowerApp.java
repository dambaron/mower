package org.dbaron.mower.application;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.dbaron.mower.model.Configuration;
import org.dbaron.mower.parser.FileConfigurationParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;

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
    private FileConfigurationParser fileConfigurationParser;

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

    public MowerApp() {
        //DO NOTHING
    }

    public void launch(String configurationFile) {

        File mowersFile = new File(configurationFile);
        if (!mowersFile.exists()) {
            LOGGER.error("Configuration file {} doesn't exist", mowersFile);
        } else {
            Configuration configuration = fileConfigurationParser.parseConfiguration(mowersFile);
        }
    }
}