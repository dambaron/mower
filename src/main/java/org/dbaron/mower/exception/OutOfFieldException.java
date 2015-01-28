package org.dbaron.mower.exception;

/**
 * Created by dbaron on 28/01/15.
 */
public class OutOfFieldException extends MowingException {

    /**
     * Constructs an <code>OutOfFieldException</code> with no detail message.
     */
    public OutOfFieldException() {
        super();
    }

    /**
     * Constructs an <code>OutOfFieldException</code> with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public OutOfFieldException(String s) {
        super(s);
    }
}
