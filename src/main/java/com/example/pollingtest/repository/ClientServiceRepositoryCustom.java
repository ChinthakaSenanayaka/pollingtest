package com.example.pollingtest.repository;

import com.example.pollingtest.model.CallerConfiguration;
import com.example.pollingtest.model.Outage;

public interface ClientServiceRepositoryCustom {
	
	Outage setupOutage(String host, Integer port, Outage outage);
	
	void deleteOutage(String host, Integer port);
	
	CallerConfiguration setupCallerService(String host, Integer port, CallerConfiguration callerConfiguration, boolean append);
	
}
