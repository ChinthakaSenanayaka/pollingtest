package com.example.pollingtest.dto;

import com.example.pollingtest.model.Caller;
import com.example.pollingtest.model.CallerConfiguration;


/**
 * RESTful Json class to get user details and its service monitoring config.
 * 
 * @author Chinthaka Senanayaka
 * @since 31-Oct-2017
 *
 */
public class CallerConfigDTO {
	
	private Caller caller;
	
	private CallerConfiguration callerConfiguration;

	/**
	 * Default constructor.
	 */
	public CallerConfigDTO() {}

	/**
	 * Field setting constructor
	 * 
	 * @param caller user details
	 * @param callerConfiguration user service monitoring config details
	 */
	public CallerConfigDTO(Caller caller, CallerConfiguration callerConfiguration) {
		this.caller = caller;
		this.callerConfiguration = callerConfiguration;
	}

	/**
	 * Getter for user details.
	 * 
	 * @return user details object
	 */
	public Caller getCaller() {
		return caller;
	}

	/**
	 * Setter for user details.
	 * 
	 * @param caller user details object
	 */
	public void setCaller(Caller caller) {
		this.caller = caller;
	}

	/**
	 * Getter for user service monitoring config details
	 * 
	 * @return user service monitoring config details
	 */
	public CallerConfiguration getCallerConfiguration() {
		return callerConfiguration;
	}

	/**
	 * Setter for user service monitoring config details
	 * 
	 * @param callerConfiguration user service monitoring config details
	 */
	public void setCallerConfiguration(CallerConfiguration callerConfiguration) {
		this.callerConfiguration = callerConfiguration;
	}
	
}
