package com.example.pollingtest.servicepoller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.pollingtest.model.ClientService;
import com.example.pollingtest.repository.ClientServiceRepository;

@Component
@EnableScheduling
public class ServicePoller {

	@Autowired
	private ClientServiceRepository clientServiceRepository;
	
	@Autowired
	private ServicePollerAsync servicePollerAsync;
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Scheduled(fixedDelayString = "${com.example.pollingtest.poll.rate}")
	public void pollForServiceStatus() {

		List<ClientService> clientServiceList = clientServiceRepository.findAll();
		Date now = new Date();
		for (ClientService clientService : clientServiceList) {
			servicePollerAsync.pollSingleService(clientService, now);
		} // clientService for loop end

	}

}
