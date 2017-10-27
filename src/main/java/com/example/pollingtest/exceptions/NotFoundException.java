package com.example.pollingtest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class NotFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;

    public NotFoundException(String reason) {
        super(reason);
    }

    public NotFoundException() {
        super();
    }

    public NotFoundException(String reason, Throwable cause) {
        super(reason, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }
	
}
