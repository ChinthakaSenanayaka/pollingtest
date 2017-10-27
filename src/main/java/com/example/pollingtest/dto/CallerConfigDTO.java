package com.example.pollingtest.dto;

import com.example.pollingtest.model.Caller;
import com.example.pollingtest.model.CallerConfiguration;

public class CallerConfigDTO {
	
	private Caller caller;
	
	private CallerConfiguration callerConfiguration;

	public CallerConfigDTO() {}

	public CallerConfigDTO(Caller caller, CallerConfiguration callerConfiguration) {
		this.caller = caller;
		this.callerConfiguration = callerConfiguration;
	}

	public Caller getCaller() {
		return caller;
	}

	public void setCaller(Caller caller) {
		this.caller = caller;
	}

	public CallerConfiguration getCallerConfiguration() {
		return callerConfiguration;
	}

	public void setCallerConfiguration(CallerConfiguration callerConfiguration) {
		this.callerConfiguration = callerConfiguration;
	}
	
}
