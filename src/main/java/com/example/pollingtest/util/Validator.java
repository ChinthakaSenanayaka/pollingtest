package com.example.pollingtest.util;

import com.example.pollingtest.exceptions.BadRequestException;
import com.example.pollingtest.model.Outage;

public class Validator {

	public static void validateOutage(final Outage outage) throws BadRequestException {

		if (outage.getStartTime() != null && outage.getEndTime() != null) {
			if (outage.getStartTime().after(outage.getEndTime())) {
				throw new BadRequestException("Outage start time should be earlier than outage end time!");
			}
		} else {
			throw new BadRequestException("Outage start time and end time should be specified!");
		}
	}

}
