package com.backend.exception;

/**
 * Class Invalid Card Exception
 * @author Baptiste Gellato
 * @version 0.0.1
 */
public class InvalidCardException extends RuntimeException  {
    /**
     * Invalid Card Exception if card isn't valid (Card number, date or CVC)
     *
     * @param errorMessage error message
     */
    public InvalidCardException(String errorMessage) {
        super(errorMessage);
    }
}
