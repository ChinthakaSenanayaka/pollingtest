package com.example.pollingtest.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.pollingtest.model.CallerConfiguration;
import com.example.pollingtest.model.ClientService;
import com.example.pollingtest.repository.CallerRepository;
import com.example.pollingtest.repository.ClientServiceRepository;
import com.example.pollingtest.scheduler.ServicePoller;

@Service
@EnableScheduling
public class PollingServiceImpl implements PollingService {
	
	@Autowired
	private CallerRepository callerRepository;
	
	@Autowired
	private ClientServiceRepository clientServiceRepository;
	
	@Autowired
	private ServicePoller servicePoller;
	
	@Scheduled(fixedDelayString = "${service.polling.scheduled.rate}")
	public void pollForServiceStatus() {
		
		List<ClientService> clientServiceList =  clientServiceRepository.findAll();
		for(ClientService clientService : clientServiceList) {
			boolean servicePollStatus = false;
			
			if(clientService.getUpStatus()) { // if service is up as of last poll
				List<CallerConfiguration> callerConfigurationList = clientService.getCallerConfigs();
				for(CallerConfiguration callerConfiguration : callerConfigurationList) {
					if(callerConfiguration.getNextPoll() == 1) { // run by nextPoll since last service status is true
						
						try {
							servicePollStatus = servicePoller.pollServices(clientService.getHost(), clientService.getPort());
							if(servicePollStatus) {
								callerConfiguration.setNextPoll(callerConfiguration.getPollingFrequency());
							}
						} catch (IOException e) {
							servicePollStatus = false;
						}
						clientService.setUpStatus(servicePollStatus);
						
					} else {
						callerConfiguration.setNextPoll((callerConfiguration.getNextPoll() - 1));
					}
				} // callerConfiguration for loop end
				
			} else { // if service is down as of last poll
				List<CallerConfiguration> callerConfigurationList = clientService.getCallerConfigs();
				for(CallerConfiguration callerConfiguration : callerConfigurationList) {
					if(callerConfiguration.getGraceTimeExpiration() == 1) { // run by graceTimeExpiration since last service status is false
						
						try {
							servicePollStatus = servicePoller.pollServices(clientService.getHost(), clientService.getPort());
							if(servicePollStatus) {
								callerConfiguration.setNextPoll(callerConfiguration.getPollingFrequency());
							} else {
								// TODO: email notify
							}
						} catch (IOException e) {
							servicePollStatus = false;
						}
						clientService.setUpStatus(servicePollStatus);
						
					} else {
						callerConfiguration.setGraceTimeExpiration((callerConfiguration.getGraceTimeExpiration() - 1));
					}
				} // callerConfiguration for loop end
				
			}
			
		} // clientService for loop end
	}
	
}
