package com.example.pollingtest.exceptions;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;

/**
 * The Interface ExceptionAttributes.
 */
public interface ExceptionAttributes {

  /**
   * Gets the exception attributes.
   *
   * @param exception the exception
   * @param httpRequest the HTTP request
   * @param httpStatus the HTTP status
   * @return the exception attributes
   */
  Map<String, Object> getExceptionAttributes(Exception exception, HttpServletRequest httpRequest, HttpStatus httpStatus);

}