package com.example.pollingtest.model;

import java.util.List;

/**
 * User details and service monitoring details for the user DTO class.
 * 
 * @author Chinthaka Senanayaka
 * @since 31-Oct-2017
 *
 */
public class CallerConfiguration {
	
	private String callerId;
	
	private Integer pollingFrequency;
	
	private Integer nextPoll;
	
	private List<String> notifyEmail;
	
	private Integer graceTime;
	
	private Integer graceTimeExpiration;
	
	/**
	 * Default constructor
	 */
	public CallerConfiguration() {}

	/**
	 * Field setting constructor
	 * 
	 * @param callerId user id
	 * @param pollingFrequency frequency of service polling
	 * @param nextPoll time to next poll the service
	 * @param notifyEmail list of emails to be notified
	 * @param graceTime grace time to notify when service goes down
	 * @param graceTimeExpiration when service is down time to fire the notification email
	 */
	public CallerConfiguration(String callerId, Integer pollingFrequency, Integer nextPoll, List<String> notifyEmail,
			Integer graceTime, Integer graceTimeExpiration) {
		this.callerId = callerId;
		this.pollingFrequency = pollingFrequency;
		this.nextPoll = nextPoll;
		this.notifyEmail = notifyEmail;
		this.graceTime = graceTime;
		this.graceTimeExpiration = graceTimeExpiration;
	}

	/**
	 * Getter for user id
	 * 
	 * @return user id field
	 */
	public String getCallerId() {
		return callerId;
	}

	/**
	 * Setter for user id
	 * 
	 * @param callerId user id field
	 */
	public void setCallerId(String callerId) {
		this.callerId = callerId;
	}

	/**
	 * Getter for frequency of service polling
	 * 
	 * @return frequency of service polling field
	 */
	public Integer getPollingFrequency() {
		return pollingFrequency;
	}

	/**
	 * Setter for frequency of service polling
	 * 
	 * @param pollingFrequency frequency of service polling field
	 */
	public void setPollingFrequency(Integer pollingFrequency) {
		this.pollingFrequency = pollingFrequency;
	}

	/**
	 * Getter for time to next poll the service
	 * 
	 * @return time to next poll the service
	 */
	public Integer getNextPoll() {
		return nextPoll;
	}

	/**
	 * Setter for time to next poll the service
	 * 
	 * @param nextPoll time to next poll the service
	 */
	public void setNextPoll(Integer nextPoll) {
		this.nextPoll = nextPoll;
	}

	/**
	 * Getter for list of emails to be notified
	 * 
	 * @return list of emails to be notified
	 */
	public List<String> getNotifyEmail() {
		return notifyEmail;
	}

	/**
	 * Setter for list of emails to be notified
	 * 
	 * @param notifyEmail list of emails to be notified
	 */
	public void setNotifyEmail(List<String> notifyEmail) {
		this.notifyEmail = notifyEmail;
	}

	/**
	 * Getter for grace time to notify when service goes down
	 * 
	 * @return race time to notify when service goes down
	 */
	public Integer getGraceTime() {
		return graceTime;
	}

	/**
	 * Setter for grace time to notify when service goes down
	 * 
	 * @param graceTime grace time to notify when service goes down
	 */
	public void setGraceTime(Integer graceTime) {
		this.graceTime = graceTime;
	}

	/**
	 * Getter for when service is down time to fire the notification email
	 * 
	 * @return when service is down time to fire the notification email
	 */
	public Integer getGraceTimeExpiration() {
		return graceTimeExpiration;
	}

	/**
	 * Setter for when service is down time to fire the notification email
	 * 
	 * @param graceTimeExpiration when service is down time to fire the notification email
	 */
	public void setGraceTimeExpiration(Integer graceTimeExpiration) {
		this.graceTimeExpiration = graceTimeExpiration;
	}
	
}
