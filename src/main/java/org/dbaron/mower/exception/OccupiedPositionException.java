package org.dbaron.mower.exception;

/**
 * Created by dbaron on 28/01/15.
 */
public class OccupiedPositionException extends MowingException {

    /**
     * Constructs an <code>OccupiedPositionException</code> with no detail message.
     */
    public OccupiedPositionException() {
        super();
    }

    /**
     * Constructs an <code>OccupiedPositionException</code> with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public OccupiedPositionException(String s) {
        super(s);
    }
}
