package com.example.pollingtest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class for HTTP 400 (bad request).
 *
 * @author Chinthaka Senanayaka
 * @since 31-Oct-2017
 * 
 */
@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class BadRequestException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Field setting constructor
     * 
     * @param reason exception reason
     */
    public BadRequestException(String reason) {
        super(reason);
    }

    /**
     * Default constructor
     */
    public BadRequestException() {}

    /**
     * Field setting constructor
     * 
     * @param reason exception reason
     * @param cause causing exception
     */
    public BadRequestException(String reason, Throwable cause) {
        super(reason, cause);
    }

    /**
     * Field setting constructor
     * 
     * @param cause causing exception
     */
    public BadRequestException(Throwable cause) {
        super(cause);
    }
}
