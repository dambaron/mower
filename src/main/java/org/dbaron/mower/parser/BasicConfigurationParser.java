package org.dbaron.mower.parser;

import org.dbaron.mower.model.Configuration;

import java.util.List;

/**
 * Created by dbaron on 29/01/15.
 */
public class BasicConfigurationParser extends AbstractConfigurationParser {

    public BasicConfigurationParser(List<String> orientationsDictionnary,
                                    List<String> movesDictionnary) {
        super(orientationsDictionnary, movesDictionnary);
    }
}
