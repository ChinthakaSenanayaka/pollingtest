package com.example.pollingtest.model;

import java.util.List;

public class CallerConfiguration {
	
	private String callerId;
	
	private Integer pollingFrequency;
	
	private Integer nextPoll;
	
	private List<String> notifyEmail;
	
	private Integer graceTime;
	
	private Integer graceTimeExpiration;
	
	public CallerConfiguration() {}

	public CallerConfiguration(String callerId, Integer pollingFrequency, Integer nextPoll, List<String> notifyEmail,
			Integer graceTime, Integer graceTimeExpiration) {
		this.callerId = callerId;
		this.pollingFrequency = pollingFrequency;
		this.nextPoll = nextPoll;
		this.notifyEmail = notifyEmail;
		this.graceTime = graceTime;
		this.graceTimeExpiration = graceTimeExpiration;
	}

	public String getCallerId() {
		return callerId;
	}

	public void setCallerId(String callerId) {
		this.callerId = callerId;
	}

	public Integer getPollingFrequency() {
		return pollingFrequency;
	}

	public void setPollingFrequency(Integer pollingFrequency) {
		this.pollingFrequency = pollingFrequency;
	}

	public Integer getNextPoll() {
		return nextPoll;
	}

	public void setNextPoll(Integer nextPoll) {
		this.nextPoll = nextPoll;
	}

	public List<String> getNotifyEmail() {
		return notifyEmail;
	}

	public void setNotifyEmail(List<String> notifyEmail) {
		this.notifyEmail = notifyEmail;
	}

	public Integer getGraceTime() {
		return graceTime;
	}

	public void setGraceTime(Integer graceTime) {
		this.graceTime = graceTime;
	}

	public Integer getGraceTimeExpiration() {
		return graceTimeExpiration;
	}

	public void setGraceTimeExpiration(Integer graceTimeExpiration) {
		this.graceTimeExpiration = graceTimeExpiration;
	}
	
}
