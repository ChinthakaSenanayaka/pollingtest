package com.example.pollingtest.service;

import com.example.pollingtest.dto.CallerConfigDTO;
import com.example.pollingtest.exceptions.BadRequestException;
import com.example.pollingtest.exceptions.NotFoundException;
import com.example.pollingtest.model.Caller;
import com.example.pollingtest.model.CallerConfiguration;
import com.example.pollingtest.model.ClientService;
import com.example.pollingtest.model.Outage;

/**
 * Service interface for polling the monitored service and inform its listening users.
 * 
 * @author Chinthaka Senanayaka
 * @since 31-Oct-2017
 *
 */
public interface PollingService {
	
	/**
	 * To save monitored service details
	 * 
	 * @param clientService monitored service details and its listening users' and their configs
	 * @return clientService monitored service DB object
	 * @throws BadRequestException when there is an issue with request fields
	 */
    ClientService saveClientService(final ClientService clientService) throws BadRequestException;

    /**
     * To delete monitored service details
     * 
     * @param host the host field
     * @param port the port field
     * @throws NotFoundException when monitored service could not be found
     */
    void deleteClientService(final String host, final Integer port) throws NotFoundException;

    /**
     * To save user details
     * 
     * @param caller the user details
     * @return saved DB user details
     */
    Caller saveCaller(final Caller caller);

    /**
     * To delete user record
     * 
     * @param caller the user details
     * @throws NotFoundException when user record is not found
     */
    void deleteCaller(final Caller caller) throws NotFoundException;

    /**
     * To maintain outage details for the monitored service
     * 
     * @param host the host field
     * @param port the port field
     * @param outage the outage details
     * @return DB outage record
     * @throws BadRequestException when there is an issue with input fields
     * @throws NotFoundException when monitored service is not found
     */
    Outage maintainOutage(final String host, final Integer port, final Outage outage) throws BadRequestException, NotFoundException;
    
    /**
     * To configure user configuration for a monitored service
     * 
     * @param host the host field
     * @param port the port field
     * @param callerConfigDTO the user details and user config details
     * @param append if true then only specified fields will be updated otherwise total user config details will be updated for the monitored service
     * @return user config details for the user and for the monitored service
     * @throws NotFoundException when monitored service is not found
     * @throws BadRequestException when there is an issue with input fields
     */
    CallerConfiguration setupCallerService(final String host, final Integer port, final CallerConfigDTO callerConfigDTO, boolean append)
    		throws NotFoundException, BadRequestException;
    
    /**
     * To delete user configs for a monitored service 
     * 
     * @param host the host field
     * @param port the port field
     * @param caller the user details
     * @throws NotFoundException when monitored service is not found
     */
    void removeCallerService(final String host, final Integer port, final Caller caller) throws NotFoundException;
	
}
