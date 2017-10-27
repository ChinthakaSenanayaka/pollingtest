package com.example.pollingtest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pollingtest.dto.CallerConfigDTO;
import com.example.pollingtest.exceptions.BadRequestException;
import com.example.pollingtest.exceptions.NotFoundException;
import com.example.pollingtest.model.Caller;
import com.example.pollingtest.model.CallerConfiguration;
import com.example.pollingtest.model.ClientService;
import com.example.pollingtest.model.Outage;
import com.example.pollingtest.repository.CallerRepository;
import com.example.pollingtest.repository.ClientServiceRepository;

@Service
public class PollingServiceImpl implements PollingService {
	
	@Autowired
	private CallerRepository callerRepository;
	
	@Autowired
    private ClientServiceRepository clientServiceRepository;
	
    public ClientService saveClientService(ClientService clientService) {
        return clientServiceRepository.save(clientService);
    }
    
    public void deleteClientService(ClientService clientService) {
        clientServiceRepository.delete(clientService);
    }
    
    public Caller saveCaller(Caller caller) {
        return callerRepository.save(caller);
    }
    
    public void deleteCaller(Caller caller) {
        callerRepository.delete(caller);
    }
    
    public Outage setupOutage(String host, Integer port, Outage outage) throws BadRequestException {
    		
    		// validation
    		if(outage.getStartTime().after(outage.getEndTime())) {
    			throw new BadRequestException("Outage start time should be earlier than outage end time!");
    		}
    		
        return clientServiceRepository.setupOutage(host, port, outage);
    }
    
    public void deleteOutage(String host, Integer port) {
    		clientServiceRepository.deleteOutage(host, port);
    }
    
    public CallerConfiguration setupCallerService(String host, Integer port, CallerConfigDTO callerConfigDTO, boolean append)
    		throws NotFoundException, BadRequestException {
    		
    		Caller caller = callerConfigDTO.getCaller();
    		CallerConfiguration callerConfiguration = callerConfigDTO.getCallerConfiguration();
    		
    		// validation
    		Caller dbCaller = callerRepository.findByUsernameAndPassword(caller.getUsername(), caller.getPassword());
    		if(dbCaller == null) {
    			throw new NotFoundException("Caller does not exist!");
    		}
    		ClientService dbClientService = clientServiceRepository.findByHostAndPort(host, port);
    		if(dbClientService == null) {
    			throw new NotFoundException("Service monitoring is not set up!");
    		}
    		if(callerConfiguration.getNotifyEmail() == null || callerConfiguration.getNotifyEmail().size() == 0) {
    			throw new BadRequestException("At least single email address should be added.");
    		}
    		if(callerConfiguration.getPollingFrequency() < 1) {
    			throw new BadRequestException("Service polling frequency should be higher than 1 second!");
    		}
    		if(callerConfiguration.getGraceTime() < 1) {
    			throw new BadRequestException("Service fail notifying grace time should be higher than 1 second!");
    		}
    		
    		callerConfiguration.setNextPoll(callerConfiguration.getPollingFrequency());
    		callerConfiguration.setGraceTimeExpiration(callerConfiguration.getGraceTime());
    		
    		return clientServiceRepository.setupCallerService(host, port, callerConfiguration, append);
    		
    }
	
}
	