package com.example.pollingtest.service;

import com.example.pollingtest.model.Caller;
import com.example.pollingtest.model.ClientService;
import com.example.pollingtest.model.Outage;

public interface PollingService {
	
    ClientService saveClientService(ClientService clientService);

    void deleteClientService(ClientService clientService);

    Caller saveCaller(Caller caller);

    void deleteCaller(Caller caller);

    Outage saveOutage(Outage outage);

    void deleteOutage(Outage outage);
	
}
