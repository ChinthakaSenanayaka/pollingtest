package com.example.pollingtest.repository;

import com.example.pollingtest.exceptions.NotFoundException;
import com.example.pollingtest.model.CallerConfiguration;
import com.example.pollingtest.model.ClientService;
import com.example.pollingtest.model.Outage;

/**
 * Service monitoring entry repository interface (DAO) for custom CURD operations
 * 
 * @author Chinthaka Senanayaka
 * @since 31-Oct-2017
 *
 */
public interface ClientServiceRepositoryCustom {
	
	/**
	 * To maintain outage details on service monitoring record
	 * 
	 * @param host the host field
	 * @param port the port field
	 * @param outage the outage details
	 * @return create DB outage record
	 */
	Outage maintainOutage(final String host, final Integer port, final Outage outage);
	
	/**
	 * To setup service monitoring registration for a user and its configurations
	 * 
	 * @param clientService service monitoring record
	 * @param callerConfiguration user details and service monitoring configs for the user
	 * @param append if true then append the config changed fields or else update the full service monitoring configs record for the user
	 * @return service monitoring configs for the user record
	 */
	CallerConfiguration setupCallerService(final ClientService clientService, final CallerConfiguration callerConfiguration, boolean append);
	
	/**
	 * To delete service monitoring record
	 * 
	 * @param host the host field
	 * @param port the port field
	 * @throws NotFoundException when service monitoring record was not found
	 */
	void deleteClientService(final String host, final Integer port) throws NotFoundException;
	
	/**
	 * To delete user details and its service monitoring config references
	 * 
	 * @param callerId the user id
	 */
	void removeCallerRefs(final String callerId);
	
	/**
	 * To delete service monitoring configuration for a user
	 * 
	 * @param clientService the service monitoring record details
	 * @param callerId the user id
	 */
	void removeCallerService(final ClientService clientService, final String callerId);
	
}
