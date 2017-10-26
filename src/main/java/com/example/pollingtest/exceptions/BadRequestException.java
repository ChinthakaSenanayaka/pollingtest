package com.example.pollingtest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class for HTTP 400 (bad request).
 *
 * @author ftibbitts
 * $Id: BadRequestException.java 550 2013-03-23 16:21:48Z fred.tibbitts $
 */
@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class BadRequestException extends Exception {

    private static final long serialVersionUID = 1L;

    public BadRequestException(String reason) {
        super(reason);
    }

    public BadRequestException() {
        super();
    }

    public BadRequestException(String reason, Throwable cause) {
        super(reason, cause);
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }
}
