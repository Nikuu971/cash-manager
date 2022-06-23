package com.backend.exception;

/**
 * Class Invalid Amount Exception
 * @author Baptiste Gellato
 * @version 0.0.1
 */
public class InvalidAmount extends RuntimeException {
    /**
     * Invalid amount Exception if user don't have money for pay
     *
     * @param errorMessage error message
     */
    public InvalidAmount(String errorMessage) {
        super(errorMessage);
    }
}