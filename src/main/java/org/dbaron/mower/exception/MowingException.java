package org.dbaron.mower.exception;

/**
 * Created by dbaron on 28/01/15.
 */
public class MowingException extends Exception {

    /**
     * Constructs a <code>MowingException</code> with no detail message.
     */
    public MowingException() {
        super();
    }

    /**
     * Constructs a <code>MowingException</code> with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public MowingException(String s) {
        super(s);
    }
}