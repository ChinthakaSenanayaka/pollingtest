package com.example.pollingtest.service;

import com.example.pollingtest.dto.CallerConfigDTO;
import com.example.pollingtest.exceptions.BadRequestException;
import com.example.pollingtest.exceptions.NotFoundException;
import com.example.pollingtest.model.Caller;
import com.example.pollingtest.model.CallerConfiguration;
import com.example.pollingtest.model.ClientService;
import com.example.pollingtest.model.Outage;

public interface PollingService {
	
    ClientService saveClientService(ClientService clientService);

    void deleteClientService(ClientService clientService);

    Caller saveCaller(Caller caller);

    void deleteCaller(Caller caller);

    Outage setupOutage(String host, Integer port, Outage outage) throws BadRequestException;

    void deleteOutage(String host, Integer port);
    
    CallerConfiguration setupCallerService(String host, Integer port, CallerConfigDTO callerConfigDTO, boolean append)
    		throws NotFoundException, BadRequestException;
	
}
