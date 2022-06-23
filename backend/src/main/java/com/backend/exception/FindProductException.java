package com.backend.exception;

/**
 * Class Find Product Exception
 * @author Baptiste Gellato
 * @version 0.0.1
 */
public class FindProductException extends RuntimeException {
    /**
     * Find Product Exception if product Id is invalid
     *
     * @param errorMessage error message
     */
    public FindProductException(String errorMessage) {
        super(errorMessage);
    }
}
