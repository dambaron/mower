package org.dbaron.mower.parser;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

/**
 * Abstract class for all configuration parsers
 * Created by dbaron on 29/01/15.
 */
abstract class AbstractConfigurationParser {

    private ImmutableSet<String> orientationsDictionary;
    private ImmutableSet<String> movesDictionary;

    public AbstractConfigurationParser() {
        //DO NOTHING
    }

    /**
     * Builds a configuration parser with a dictionary for allowed orientations
     * and another dictionary for allowed moves
     * @param orientationsDictionary - a dictionary for allowed orientations
     * @param movesDictionary - a dictionary for allowed moves
     */
    public AbstractConfigurationParser(Set<String> orientationsDictionary,
                                       Set<String> movesDictionary) {

        this.orientationsDictionary = ImmutableSet.<String>builder()
                .addAll(orientationsDictionary)
                .build();

        this.movesDictionary = ImmutableSet.<String>builder()
                .addAll(movesDictionary)
                .build();
    }

    public Set<String> getOrientationsDictionary() {
        return orientationsDictionary;
    }

    public Set<String> getMovesDictionary() {
        return movesDictionary;
    }
}