package com.example.pollingtest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.pollingtest.dto.CallerConfigDTO;
import com.example.pollingtest.exceptions.BadRequestException;
import com.example.pollingtest.exceptions.NotFoundException;
import com.example.pollingtest.model.Caller;
import com.example.pollingtest.model.CallerConfiguration;
import com.example.pollingtest.model.ClientService;
import com.example.pollingtest.model.Outage;
import com.example.pollingtest.service.PollingService;


/**
 * Polling controller class to define route details.
 * 
 * @author Chinthaka Senanayaka
 * @since 31-Oct-2017
 *
 */
@RestController
public class PollingController extends AbstractController {
	
	@Autowired
    private PollingService pollingService;
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	/**
	 * To save service to be monitored. Can setup outage and caller configurations as well.
	 * 
	 * @param clientService client service to be monitored and other details.
	 * @return HTTP response with client service created
	 * @throws BadRequestException when there is any issue with the client service request
	 */
	@RequestMapping(value = "/service", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClientService> saveService(@RequestBody ClientService clientService) throws BadRequestException {
		
		ClientService dbClientService = pollingService.saveClientService(clientService);
		
		return new ResponseEntity<ClientService>(dbClientService, HttpStatus.CREATED);
	}
	
	/**
	 * To save user (or caller) to setup client service configurations
	 * 
	 * @param caller user details
	 * @return HTTP response with caller user created
	 */
	@RequestMapping(value = "/caller", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Caller> saveCaller(@RequestBody Caller caller) {
		
		Caller dbCaller = pollingService.saveCaller(caller);
		
		return new ResponseEntity<Caller>(dbCaller, HttpStatus.CREATED);
	}
	
	/**
	 * To setup an outage for an existing servive monitoring.
	 * 
	 * @param host host details
	 * @param port port details
	 * @param outage outage details
	 * @return HTTP response with outage created
	 * @throws BadRequestException when there is any issue with the outage request
	 * @throws NotFoundException when there is any missing detail in the request
	 */
	@RequestMapping(value = "/host/{host}/port/{port}/outage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Outage> setupOutage(@PathVariable("host") String host, @PathVariable("port") Integer port, @RequestBody Outage outage) throws BadRequestException, NotFoundException {
		
		Outage dbOutage = pollingService.maintainOutage(host, port, outage);
		
		return new ResponseEntity<Outage>(dbOutage, HttpStatus.CREATED);
	}
	
	/**
	 * To delete a monitoring service
	 * 
	 * @param host host details
	 * @param port port details
	 * @throws NotFoundException when there is any missing detail in the request
	 */
	@RequestMapping(value = "/host/{host}/port/{port}/service", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteService(@PathVariable("host") String host, @PathVariable("port") Integer port) throws NotFoundException {
		
	    pollingService.deleteClientService(host, port);
	}
	
	/**
	 * To delete user.
	 * username and password in the body should be encrypted
	 * 
	 * @param caller user details
	 * @throws NotFoundException when there is any missing detail in the request
	 */
	@RequestMapping(value = "/caller", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCaller(@RequestBody Caller caller) throws NotFoundException {
		
	    pollingService.deleteCaller(caller);
	}
	
	/**
	 * To delete an outage configured
	 * 
	 * @param host hot details
	 * @param port port details
	 * @throws BadRequestException when there is any issue with the caller request
	 * @throws NotFoundException when there is any missing detail in the request
	 */
	@RequestMapping(value = "/host/{host}/port/{port}/outage", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteOutage(@PathVariable("host") String host, @PathVariable("port") Integer port) throws BadRequestException, NotFoundException {
		
	    pollingService.maintainOutage(host, port, null);
	}
	
	/**
	 * To setup monitoring entry for a user for a service configured
	 * 
	 * @param host host details
	 * @param port port details
	 * @param callerConfigDTO user details and user service monitoring details
	 * @param append specify whether to append (if true) new config to existing config or if false existing config will be fully overridden
	 * @return HTTP response with user monitoring config created/changed
	 * @throws NotFoundException when there is any missing detail in the request
	 * @throws BadRequestException when there is any issue with the caller request
	 */
	@RequestMapping(value = "/host/{host}/port/{port}/callerService", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CallerConfiguration> setupCallerService(@PathVariable("host") String host, @PathVariable("port") Integer port, @RequestBody CallerConfigDTO callerConfigDTO, 
			@RequestParam(value = "append", defaultValue = "false", required = false) String append) throws NotFoundException, BadRequestException {
		
		boolean appendCallerConfig = Boolean.parseBoolean(append);
		CallerConfiguration dbCallerConfiguration = pollingService.setupCallerService(host, port, callerConfigDTO, appendCallerConfig);
		
		return new ResponseEntity<CallerConfiguration>(dbCallerConfiguration, HttpStatus.CREATED);
	}
	
	/**
	 * To delete existing user configuration for service monitoring
	 * 
	 * @param host host details
	 * @param port port details
	 * @param caller user details
	 * @throws NotFoundException when there is any missing detail in the request
	 */
	@RequestMapping(value = "/host/{host}/port/{port}/callerService", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeCallerService(@PathVariable("host") String host, @PathVariable("port") Integer port, @RequestBody Caller caller) throws NotFoundException {
		
		pollingService.removeCallerService(host, port, caller);
	}

}