package com.example.pollingtest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class for HTTP 404 (NOT FOUND).
 * 
 * @author Chinthaka Senanayaka
 * @since 31-Oct-2017
 *
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class NotFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;

	/**
     * Field setting constructor
     * 
     * @param reason exception reason
     */
    public NotFoundException(String reason) {
        super(reason);
    }

    /**
     * Default constructor
     */
    public NotFoundException() {}

    /**
     * Field setting constructor
     * 
     * @param reason exception reason
     * @param cause causing exception
     */
    public NotFoundException(String reason, Throwable cause) {
        super(reason, cause);
    }

    /**
     * Field setting constructor
     * 
     * @param cause causing exception
     */
    public NotFoundException(Throwable cause) {
        super(cause);
    }
	
}
