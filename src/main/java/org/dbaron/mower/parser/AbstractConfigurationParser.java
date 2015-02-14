package org.dbaron.mower.parser;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

/**
 * Created by dbaron on 29/01/15.
 */
public abstract class AbstractConfigurationParser {

    private ImmutableSet<String> orientationsDictionnary;
    private ImmutableSet<String> movesDictionnary;

    public AbstractConfigurationParser() {
        //DO NOTHING
    }

    /**
     * Builds a configuration parser with a dictionnary for allowed orientations
     * and another dictionnary for allowed moves
     * @param orientationsDictionnary - a dictionnary for allowed orientations
     * @param movesDictionnary - a dictionnary for allowed moves
     */
    public AbstractConfigurationParser(Set<String> orientationsDictionnary,
                                       Set<String> movesDictionnary) {

        this.orientationsDictionnary = ImmutableSet.<String>builder()
                .addAll(orientationsDictionnary)
                .build();

        this.movesDictionnary = ImmutableSet.<String>builder()
                .addAll(movesDictionnary)
                .build();
    }

    public Set<String> getOrientationsDictionnary() {
        return orientationsDictionnary;
    }

    public Set<String> getMovesDictionnary() {
        return movesDictionnary;
    }
}