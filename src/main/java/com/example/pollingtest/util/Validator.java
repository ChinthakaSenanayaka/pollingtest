package com.example.pollingtest.util;

import java.util.Date;

import com.example.pollingtest.exceptions.BadRequestException;

public class Validator {
	
	public static void validateOutage(Date startTime, Date endTime) throws BadRequestException {
		if(startTime != null && endTime != null) {
		if(startTime.after(endTime)) {
			throw new BadRequestException("Outage start time should be earlier than outage end time!");
		}
		} else {
			throw new BadRequestException("Outage start time and end time should be specified!");
		}
	}
	
}
