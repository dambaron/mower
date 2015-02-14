package org.dbaron.mower.parser;

import com.google.common.io.Files;
import org.apache.commons.lang3.Validate;
import org.dbaron.mower.model.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;

/**
 * Created by dbaron on 29/01/15.
 */
public class FileConfigurationParser extends BasicConfigurationParser implements ConfigurationParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileConfigurationParser.class);

    private File file;

    public FileConfigurationParser() {
        super();
    }

    public FileConfigurationParser(Set<String> orientationsDictionnary,
                                   Set<String> movesDictionnary) {
        super(orientationsDictionnary, movesDictionnary);
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public Configuration parseConfiguration(File file) {
        Validate.notNull(file, "file is required");

        List<String> lines;
        try {
            lines = Files.readLines(file, Charset.defaultCharset());
        } catch (IOException ioe) {
            LOGGER.error("Error while reading file", ioe);
            return null;
        }

        return super.parseConfiguration(lines);
    }

    @Override
    public Set<String> getOrientationsDictionnary() {
        return super.getOrientationsDictionnary();
    }

    @Override
    public Set<String> getMovesDictionnary() {
        return super.getMovesDictionnary();
    }
}