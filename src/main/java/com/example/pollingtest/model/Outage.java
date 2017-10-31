package com.example.pollingtest.model;

import java.util.Date;

/**
 * Outage config model class
 * 
 * @author Chinthaka Senanayaka
 * @since 31-Oct-2017
 *
 */
public class Outage {
	
	private Date startTime;
	
	private Date endTime;

	/**
	 * Default constructor
	 */
	public Outage() {}
	
	/**
	 * Field setting constructor
	 * 
	 * @param startTime outage start time
	 * @param endTime outage end time
	 */
	public Outage(Date startTime, Date endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * Getter for outage start time
	 * 
	 * @return outage start time
	 */
    public Date getStartTime() {
		return startTime;
	}

    /**
     * Setter for outage start time
     * 
     * @param startTime outage start time
     */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * Getter for outage end time
	 * 
	 * @return outage end time
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * Setter for outage end time
	 * 
	 * @param endTime outage end time
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}
