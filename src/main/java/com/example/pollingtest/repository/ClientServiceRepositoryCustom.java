package com.example.pollingtest.repository;

import com.example.pollingtest.exceptions.NotFoundException;
import com.example.pollingtest.model.CallerConfiguration;
import com.example.pollingtest.model.ClientService;
import com.example.pollingtest.model.Outage;

public interface ClientServiceRepositoryCustom {
	
	Outage setupOutage(String host, Integer port, Outage outage);
	
	void deleteOutage(String host, Integer port);
	
	CallerConfiguration setupCallerService(ClientService clientService, CallerConfiguration callerConfiguration, boolean append);
	
	void deleteClientService(String host, Integer port) throws NotFoundException;
	
	void removeCallerRefs(String callerId);
	
	void removeCallerService(ClientService clientService, String callerId);
	
}
