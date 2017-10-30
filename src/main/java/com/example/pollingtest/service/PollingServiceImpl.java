package com.example.pollingtest.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.pollingtest.constants.ClientServiceConstants;
import com.example.pollingtest.dto.CallerConfigDTO;
import com.example.pollingtest.exceptions.BadRequestException;
import com.example.pollingtest.exceptions.NotFoundException;
import com.example.pollingtest.model.Caller;
import com.example.pollingtest.model.CallerConfiguration;
import com.example.pollingtest.model.ClientService;
import com.example.pollingtest.model.Outage;
import com.example.pollingtest.repository.CallerRepository;
import com.example.pollingtest.repository.ClientServiceRepository;
import com.example.pollingtest.util.Validator;

@Service
public class PollingServiceImpl implements PollingService {
	
	@Autowired
	private CallerRepository callerRepository;
	
	@Autowired
    private ClientServiceRepository clientServiceRepository;
	
	@Autowired
    private Validator validator;
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
    public ClientService saveClientService(final ClientService clientService) throws BadRequestException {
    		
    		if(StringUtils.isEmpty(clientService.getServiceName())) {
    			clientService.setServiceName(ClientServiceConstants.CLIENT_SERVICE_NAME);
    		}
    		clientService.setUpStatus(true);
    		if(clientService.getOutage() != null) {
        		Validator.validateOutage(clientService.getOutage());
    		}
    		
    		if(clientService.getCallerConfigs() == null) {
    			clientService.setCallerConfigs(new ArrayList<CallerConfiguration>());
    		}
    		for(CallerConfiguration callerConfig : clientService.getCallerConfigs()) {
    			callerConfig.setNextPoll(callerConfig.getPollingFrequency());
    			callerConfig.setGraceTimeExpiration(callerConfig.getGraceTime());
    			if(callerConfig.getNotifyEmail() == null) {
    				callerConfig.setNotifyEmail(new ArrayList<String>());
    			}
    		}
    		
        return clientServiceRepository.save(clientService);
    }
    
    public void deleteClientService(final String host, Integer port) throws NotFoundException {
        clientServiceRepository.deleteClientService(host, port);
    }
    
    public Caller saveCaller(Caller caller) {
        return callerRepository.save(caller);
    }
    
    public void deleteCaller(final Caller caller) throws NotFoundException {
    		
    		Caller dbCaller = validator.validateCaller(caller);
    		
    		clientServiceRepository.removeCallerRefs(dbCaller.getId());
        callerRepository.delete(dbCaller);
    }
    
    public Outage maintainOutage(final String host, final Integer port, final Outage outage) throws BadRequestException, NotFoundException {
    		
    		// validation
    		validator.validateClientService(host, port);
    		if(outage != null) {
    			Validator.validateOutage(outage);
    		}
    		
        return clientServiceRepository.maintainOutage(host, port, outage);
    }
    
    public CallerConfiguration setupCallerService(final String host, final Integer port, final CallerConfigDTO callerConfigDTO, boolean append)
    		throws NotFoundException, BadRequestException {
    		
    		Caller caller = callerConfigDTO.getCaller();
    		CallerConfiguration callerConfiguration = callerConfigDTO.getCallerConfiguration();

		// validation
		Caller dbCaller = validator.validateCaller(caller);
		ClientService dbClientService = validator.validateClientService(host, port);
		if (!append) {
			if (callerConfiguration.getNotifyEmail() == null || callerConfiguration.getNotifyEmail().size() == 0) {
				throw new BadRequestException("At least single email address should be added.");
			}
			if (callerConfiguration.getPollingFrequency() == null || callerConfiguration.getPollingFrequency() < 1) {
				throw new BadRequestException("Service polling frequency should be higher than 1 second!");
			}
			if (callerConfiguration.getGraceTime() == null || callerConfiguration.getGraceTime() < 1) {
				throw new BadRequestException("Service fail notifying grace time should be higher than 1 second!");
			}
		} else if(callerConfiguration.getNotifyEmail() == null && (callerConfiguration.getPollingFrequency() == null || callerConfiguration.getPollingFrequency() < 1) && 
				(callerConfiguration.getGraceTime() == null || callerConfiguration.getGraceTime() < 1)) {
			throw new BadRequestException("Service setup failed, notify email, polling frequency and grace time fields are mandatory!");
		}

		callerConfiguration.setCallerId(dbCaller.getId());
		callerConfiguration.setNextPoll(callerConfiguration.getPollingFrequency());
		callerConfiguration.setGraceTimeExpiration(callerConfiguration.getGraceTime());

		return clientServiceRepository.setupCallerService(dbClientService, callerConfiguration, append);
    		
    }
    
    public void removeCallerService(final String host, final Integer port, final Caller caller) throws NotFoundException {
    		
    		// validation
    		Caller dbCaller = validator.validateCaller(caller);
		ClientService dbClientService = validator.validateClientService(host, port);
		
		clientServiceRepository.removeCallerService(dbClientService, dbCaller.getId());
    		
    }
	
}
	