package com.example.pollingtest.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "outage")
public class Outage {
	
    @Id
    private String id;
    
	private Date startTime;
	
	private Date endTime;

	public Outage() {}
	
	public Outage(Date startTime, Date endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public String getId() {
      return id;
    }
	
    public void setId(String id) {
      this.id = id;
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
