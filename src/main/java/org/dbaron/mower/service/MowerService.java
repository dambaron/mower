package org.dbaron.mower.service;

import org.dbaron.mower.model.Field;
import org.dbaron.mower.model.Mower;

import java.util.Map;
import java.util.Set;

/**
 * Created by dbaron on 28/01/15.
 */
public interface MowerService {

    public void registerField(Field field);
    public void unregisterField(Field field);

    public void registerMower(Mower mower, Field field);
    public void unregisterMower(Mower mower, Field field);

    public void mow(Field field);
    public void mow(Field field, Mower mower);

    public Set<Field> getRegisteredFields();
    public Set<Mower> getRegisteredMowers(Field Field);
    public Map<Field, Set<Mower>> getRegisteredMowersByField();
}