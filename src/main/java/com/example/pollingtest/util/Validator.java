package com.example.pollingtest.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.pollingtest.exceptions.BadRequestException;
import com.example.pollingtest.exceptions.NotFoundException;
import com.example.pollingtest.model.Caller;
import com.example.pollingtest.model.ClientService;
import com.example.pollingtest.model.Outage;
import com.example.pollingtest.repository.CallerRepository;
import com.example.pollingtest.repository.ClientServiceRepository;

@Component
public class Validator {
	
	@Autowired
	private CallerRepository callerRepository;
	
	@Autowired
    private ClientServiceRepository clientServiceRepository;
	
	public Caller validateCaller(Caller caller) throws NotFoundException {
		Caller dbCaller = callerRepository.findByUsernameAndPassword(caller.getUsername(), caller.getPassword());
		if(dbCaller == null) {
			throw new NotFoundException("Caller with username and password not found!");
		}
		
		return dbCaller;
	}
	
	public ClientService validateClientService(String host, Integer port) throws NotFoundException {
		ClientService dbClientService = clientServiceRepository.findByHostAndPort(host, port);
		if (dbClientService == null) {
			throw new NotFoundException("Service monitoring is not set up!");
		}
		
		return dbClientService;
	}
	
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
