package org.dbaron.mower.service;

import org.dbaron.mower.model.Field;
import org.dbaron.mower.model.Mower;

import java.util.List;
import java.util.Set;

/**
 * Created by dbaron on 28/01/15.
 */
public interface MowerService {

    /**
     * Register a field in the service
     * @param field - the field to register
     */
    public void registerField(Field field);

    /**
     * Register a mower in the service.
     * The mower belongs to a field
     * @param mower - the mower to register
     * @param field - the field to attach the mower to
     */
    public void registerMower(Mower mower, Field field);

    /**
     * Mows a field with all mowers attached to it
     * @param field - the field to mow
     */
    public void mow(Field field);

    /**
     * Mows a field with a given mower
     * @param field - the field to mow
     * @param mower - the mower to mow the field with
     */
    public void mow(Field field, Mower mower);

    public Set<Field> getRegisteredFields();
    public List<Mower> getRegisteredMowers(Field Field);
}