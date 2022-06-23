package com.backend.exception;

/**
 * Class User Exception
 * @author Baptiste Gellato
 * @version 0.0.1
 */
public class UserException extends RuntimeException  {
    /**
     * User Exception if user Id is invalid
     *
     * @param errorMessage error message
     */
    public UserException(String errorMessage) {
        super(errorMessage);
    }
}
