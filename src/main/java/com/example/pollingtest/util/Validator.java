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

/**
 * Class for business logic validations
 * 
 * @author Chinthaka Senanayaka
 * @since 31-Oct-2017
 *
 */
@Component
public class Validator {
	
	@Autowired
	private CallerRepository callerRepository;
	
	@Autowired
    private ClientServiceRepository clientServiceRepository;
	
	/**
	 * Validate whether user is a registered user in the system
	 * 
	 * @param caller the user details
	 * @return registered user record
	 * @throws NotFoundException when user record in the system is not found
	 */
	public Caller validateCaller(Caller caller) throws NotFoundException {
		Caller dbCaller = callerRepository.findByUsernameAndPassword(caller.getUsername(), caller.getPassword());
		if(dbCaller == null) {
			throw new NotFoundException("Caller with username and password not found!");
		}
		
		return dbCaller;
	}
	
	/**
	 * Validate whether monitored service is registered with the system,
	 * 
	 * @param host the host field
	 * @param port the port field
	 * @return monitored service record in the system
	 * @throws NotFoundException when monitored service record is not found
	 */
	public ClientService validateClientService(String host, Integer port) throws NotFoundException {
		ClientService dbClientService = clientServiceRepository.findByHostAndPort(host, port);
		if (dbClientService == null) {
			throw new NotFoundException("Service monitoring is not set up!");
		}
		
		return dbClientService;
	}
	
	/**
	 * Validate whether outage configuration input is correct
	 * 
	 * @param outage the outage details
	 * @throws BadRequestException when the outage configuration input is wrong
	 */
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
