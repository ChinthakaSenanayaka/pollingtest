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
import org.springframework.web.bind.annotation.ResponseBody;
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
public class PollingController {
	
	@Autowired
    private PollingService pollingService;
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/saveService", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClientService> saveService(@RequestBody ClientService clientService) {
		
		ClientService dbClientService = pollingService.saveClientService(clientService);
		
		return new ResponseEntity<ClientService>(dbClientService, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/saveCaller", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Caller> saveCaller(@RequestBody Caller caller) {
		
		Caller dbCaller = pollingService.saveCaller(caller);
		
		return new ResponseEntity<Caller>(dbCaller, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/host/{host}/port/{port}/setupOutage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Outage> setupOutage(@PathVariable("host") String host, @PathVariable("port") Integer port, @RequestBody Outage outage) throws BadRequestException {
		
		Outage dbOutage = pollingService.setupOutage(host, port, outage);
		
		return new ResponseEntity<Outage>(dbOutage, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/host/{host}/port/{port}/deleteService", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> deleteService(@PathVariable("host") String host, @PathVariable("port") Integer port) {
		
	    pollingService.deleteClientService(host, port);
		
	    return new ResponseEntity<String>("Service is deleted", HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value = "/deleteCaller", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> deleteCaller(@RequestBody Caller caller) throws NotFoundException {
		
	    pollingService.deleteCaller(caller);
		
	    return new ResponseEntity<String>("Caller is deleted", HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value = "/host/{host}/port/{port}/deleteOutage", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> deleteOutage(@PathVariable("host") String host, @PathVariable("port") Integer port) {
		
	    pollingService.deleteOutage(host, port);
		
	    return new ResponseEntity<String>("Outage is deleted", HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value = "/host/{host}/port/{port}/setupCallerService", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<CallerConfiguration> setupCallerService(@PathVariable("host") String host, @PathVariable("port") Integer port, @RequestBody CallerConfigDTO callerConfigDTO, 
			@RequestParam(value = "append", defaultValue = "false", required = false) String append) throws NotFoundException, BadRequestException {
		
		boolean appendCallerConfig = Boolean.getBoolean(append);
		CallerConfiguration dbCallerConfiguration = pollingService.setupCallerService(host, port, callerConfigDTO, appendCallerConfig);
		
		return new ResponseEntity<CallerConfiguration>(dbCallerConfiguration, HttpStatus.CREATED);
	}

}