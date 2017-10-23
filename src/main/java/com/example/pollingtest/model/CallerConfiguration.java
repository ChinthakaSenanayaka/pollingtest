package com.example.pollingtest.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;

public class CallerConfiguration {
	
	@Field(value="callerId")
	private Caller caller;
	
	private Integer pollingFrequency;
	
	private Integer nextPoll;
	
	private List<String> notifyEmail;
	
	private Integer graceTime;
	
	private Integer graceTimeExpiration;
	
	private Outage outage;

	public CallerConfiguration() {}

	public CallerConfiguration(Caller caller, Integer pollingFrequency, Integer nextPoll, List<String> notifyEmail,
			Integer graceTime, Integer graceTimeExpiration, Outage outage) {
		this.caller = caller;
		this.pollingFrequency = pollingFrequency;
		this.nextPoll = nextPoll;
		this.notifyEmail = notifyEmail;
		this.graceTime = graceTime;
		this.graceTimeExpiration = graceTimeExpiration;
		this.outage = outage;
	}

	public Caller getCaller() {
		return caller;
	}

	public void setCaller(Caller caller) {
		this.caller = caller;
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

	public Outage getOutage() {
		return outage;
	}

	public void setOutage(Outage outage) {
		this.outage = outage;
	}
	
}
