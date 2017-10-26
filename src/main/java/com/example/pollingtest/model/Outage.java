package com.example.pollingtest.model;

import java.util.Date;

public class Outage {
	
	private Date startTime;
	
	private Date endTime;

	public Outage() {}
	
	public Outage(Date startTime, Date endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}

    public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}
