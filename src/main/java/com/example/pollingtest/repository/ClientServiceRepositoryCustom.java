package com.example.pollingtest.repository;

import com.example.pollingtest.exceptions.NotFoundException;
import com.example.pollingtest.model.CallerConfiguration;
import com.example.pollingtest.model.ClientService;
import com.example.pollingtest.model.Outage;

public interface ClientServiceRepositoryCustom {
	
	Outage maintainOutage(final String host, final Integer port, final Outage outage);
	
	CallerConfiguration setupCallerService(final ClientService clientService, final CallerConfiguration callerConfiguration, boolean append);
	
	void deleteClientService(final String host, final Integer port) throws NotFoundException;
	
	void removeCallerRefs(final String callerId);
	
	void removeCallerService(final ClientService clientService, final String callerId);
	
}
