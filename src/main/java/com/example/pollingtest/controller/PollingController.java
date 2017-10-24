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
import com.example.pollingtest.repository.CallerRepository;
import com.example.pollingtest.repository.ClientServiceRepository;

@RestController
public class PollingController extends AbstractController {

	@Autowired
	private CallerRepository callerRepository;
	
	@Autowired
	private ClientServiceRepository clientServiceRepository;

	@RequestMapping(value = "/saveService", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String saveService(@RequestBody ClientService clientService) {
		
		ClientService dbClientService = clientServiceRepository.save(clientService);
		
		return dbClientService.getId();
	}
	
	@RequestMapping(value = "/saveCaller", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String saveCaller(@RequestBody Caller caller) {
		
		Caller dbCaller = callerRepository.save(caller);
		
		return dbCaller.getId();
	}
	
	@RequestMapping(value = "/deleteService", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String deleteService(@RequestBody ClientService clientService) {
		
		clientServiceRepository.delete(clientService);
		
		return "Service is deleted";
	}
	
	@RequestMapping(value = "/deleteCaller", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String deleteCaller(@RequestBody Caller caller) {
		
		callerRepository.delete(caller);
		
		return "Caller is deleted";
	}

}