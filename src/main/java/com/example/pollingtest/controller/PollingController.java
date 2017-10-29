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

@RestController
public class PollingController extends AbstractController {
	
	@Autowired
    private PollingService pollingService;
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/service", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClientService> saveService(@RequestBody ClientService clientService) throws BadRequestException {
		
		ClientService dbClientService = pollingService.saveClientService(clientService);
		
		return new ResponseEntity<ClientService>(dbClientService, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/caller", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Caller> saveCaller(@RequestBody Caller caller) {
		
		Caller dbCaller = pollingService.saveCaller(caller);
		
		return new ResponseEntity<Caller>(dbCaller, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/host/{host}/port/{port}/outage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Outage> setupOutage(@PathVariable("host") String host, @PathVariable("port") Integer port, @RequestBody Outage outage) throws BadRequestException {
		
		Outage dbOutage = pollingService.setupOutage(host, port, outage);
		
		return new ResponseEntity<Outage>(dbOutage, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/host/{host}/port/{port}/service", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteService(@PathVariable("host") String host, @PathVariable("port") Integer port) throws NotFoundException {
		
	    pollingService.deleteClientService(host, port);
	}
	
	// username and password in the body should be encrypted
	@RequestMapping(value = "/caller", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCaller(@RequestBody Caller caller) throws NotFoundException {
		
	    pollingService.deleteCaller(caller);
	}
	
	@RequestMapping(value = "/host/{host}/port/{port}/outage", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteOutage(@PathVariable("host") String host, @PathVariable("port") Integer port) {
		
	    pollingService.deleteOutage(host, port);
	}
	
	@RequestMapping(value = "/host/{host}/port/{port}/callerService", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CallerConfiguration> setupCallerService(@PathVariable("host") String host, @PathVariable("port") Integer port, @RequestBody CallerConfigDTO callerConfigDTO, 
			@RequestParam(value = "append", defaultValue = "false", required = false) String append) throws NotFoundException, BadRequestException {
		
		boolean appendCallerConfig = Boolean.parseBoolean(append);
		CallerConfiguration dbCallerConfiguration = pollingService.setupCallerService(host, port, callerConfigDTO, appendCallerConfig);
		
		return new ResponseEntity<CallerConfiguration>(dbCallerConfiguration, HttpStatus.CREATED);
	}

}