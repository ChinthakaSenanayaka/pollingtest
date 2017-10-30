package com.example.pollingtest.service;

import com.example.pollingtest.dto.CallerConfigDTO;
import com.example.pollingtest.exceptions.BadRequestException;
import com.example.pollingtest.exceptions.NotFoundException;
import com.example.pollingtest.model.Caller;
import com.example.pollingtest.model.CallerConfiguration;
import com.example.pollingtest.model.ClientService;
import com.example.pollingtest.model.Outage;

public interface PollingService {
	
    ClientService saveClientService(final ClientService clientService) throws BadRequestException;

    void deleteClientService(final String host, final Integer port) throws NotFoundException;

    Caller saveCaller(final Caller caller);

    void deleteCaller(final Caller caller) throws NotFoundException;

    Outage maintainOutage(final String host, final Integer port, final Outage outage) throws BadRequestException;
    
    CallerConfiguration setupCallerService(final String host, final Integer port, final CallerConfigDTO callerConfigDTO, boolean append)
    		throws NotFoundException, BadRequestException;
    
    void removeCallerService(final String host, final Integer port, final Caller caller) throws NotFoundException;
	
}
