package com.example.pollingtest.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import com.example.pollingtest.exceptions.BadRequestException;
import com.example.pollingtest.exceptions.DefaultExceptionAttributes;
import com.example.pollingtest.exceptions.ExceptionAttributes;

@ControllerAdvice
public abstract class AbstractController {
	
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  
  /**
   * Handles all Exceptions not addressed by more specific
   * <code>@ExceptionHandler</code> methods. Creates a response with the
   * Exception Attributes in the response body as JSON and a HTTP status code of
   * 500, internal server error.
   * 
   * @param exception An Exception instance.
   * @param request The HttpServletRequest in which the Exception was raised.
   * @return A ResponseEntity containing the Exception Attributes in the body
   *         and a HTTP status code 500.
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleException(Exception exception, HttpServletRequest request) {
    LOGGER.error("Exception: ", exception);
    ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();
    Map<String, Object> responseBody = exceptionAttributes.getExceptionAttributes(exception, request, HttpStatus.INTERNAL_SERVER_ERROR);
    return new ResponseEntity<Map<String, Object>>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, Object>> handleException(final IllegalArgumentException exception, final HttpServletRequest request) {
    LOGGER.error("Exception: ", exception);
    ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();
    Map<String, Object> responseBody = exceptionAttributes.getExceptionAttributes(exception, request, HttpStatus.BAD_REQUEST);
    return new ResponseEntity<Map<String, Object>>(responseBody, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NullPointerException.class)
  public ResponseEntity<Map<String, Object>> handleException(final NullPointerException exception, final HttpServletRequest request) {
    LOGGER.error("Exception: ", exception);
    ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();
    Map<String, Object> responseBody = exceptionAttributes.getExceptionAttributes(exception, request, HttpStatus.BAD_REQUEST);
    return new ResponseEntity<Map<String, Object>>(responseBody, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<Map<String, Object>> handleException(final BadRequestException exception, final HttpServletRequest request) {
    LOGGER.error("Exception: ", exception);
    ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();
    Map<String, Object> responseBody = exceptionAttributes.getExceptionAttributes(exception, request, HttpStatus.BAD_REQUEST);
    return new ResponseEntity<Map<String, Object>>(responseBody, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Map<String, Object>> handleException(final HttpMessageNotReadableException exception, final HttpServletRequest request) {
    LOGGER.error("Exception: ", exception);
    ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();
    Map<String, Object> responseBody = exceptionAttributes.getExceptionAttributes(exception, request, HttpStatus.BAD_REQUEST);
    return new ResponseEntity<Map<String, Object>>(responseBody, HttpStatus.BAD_REQUEST);
  }
  
  /**
   * Handle Spring related exceptions for HTTP 4xx scale.
   *
   * @param exception the exception
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler({ HttpClientErrorException.class })
  public ResponseEntity<Map<String, Object>> handleException(final HttpClientErrorException exception, final HttpServletRequest request) {
    LOGGER.error("Exception: ", exception);
    ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();
    Map<String, Object> responseBody = exceptionAttributes.getExceptionAttributes(exception, request, exception.getStatusCode());
    return new ResponseEntity<Map<String, Object>>(responseBody, exception.getStatusCode());
  }
	
}
