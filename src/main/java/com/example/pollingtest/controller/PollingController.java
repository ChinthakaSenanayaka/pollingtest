package com.example.pollingtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.pollingtest.model.Caller;
import com.example.pollingtest.model.ClientService;
import com.example.pollingtest.service.PollingService;

@RestController
public class PollingController extends AbstractController {
	
	@Autowired
    private PollingService pollingService;

	@RequestMapping(value = "/saveService", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String saveService(@RequestBody ClientService clientService) {
		
		ClientService dbClientService = pollingService.saveClientService(clientService);
		
		return dbClientService.getId();
	}
	
	@RequestMapping(value = "/saveCaller", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String saveCaller(@RequestBody Caller caller) {
		
		Caller dbCaller = pollingService.saveCaller(caller);
		
		return dbCaller.getId();
	}
	
	@RequestMapping(value = "/deleteService", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String deleteService(@RequestBody ClientService clientService) {
		
	    pollingService.deleteClientService(clientService);
		
		return "Service is deleted";
	}
	
	@RequestMapping(value = "/deleteCaller", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String deleteCaller(@RequestBody Caller caller) {
		
	    pollingService.deleteCaller(caller);
		
		return "Caller is deleted";
	}

}