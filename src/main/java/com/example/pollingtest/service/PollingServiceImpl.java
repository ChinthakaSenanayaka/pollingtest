package com.example.pollingtest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pollingtest.model.Caller;
import com.example.pollingtest.model.ClientService;
import com.example.pollingtest.model.Outage;
import com.example.pollingtest.repository.CallerRepository;
import com.example.pollingtest.repository.ClientServiceRepository;
import com.example.pollingtest.repository.OutageRepository;

@Service
public class PollingServiceImpl implements PollingService {
	
	@Autowired
	private CallerRepository callerRepository;
	
	@Autowired
    private OutageRepository outageRepository;
	
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
    
    public Outage saveOutage(Outage outage) {
        return outageRepository.save(outage);
    }
    
    public void deleteOutage(Outage outage) {
      outageRepository.delete(outage);
    }
	
}
