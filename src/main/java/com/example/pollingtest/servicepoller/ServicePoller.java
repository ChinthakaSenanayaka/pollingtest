package com.example.pollingtest.servicepoller;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import javax.net.SocketFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.pollingtest.model.CallerConfiguration;
import com.example.pollingtest.model.ClientService;
import com.example.pollingtest.repository.ClientServiceRepository;

@Component
@EnableScheduling
public class ServicePoller {
	
    @Autowired
    private ClientServiceRepository clientServiceRepository;
    
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    
    @Scheduled(fixedDelayString = "${service.polling.scheduled.rate}")
    public void pollForServiceStatus() {
        
        List<ClientService> clientServiceList =  clientServiceRepository.findAll();
        for(ClientService clientService : clientServiceList) {
            boolean servicePollStatus = false;
            
            // general service polling function.
            List<CallerConfiguration> callerConfigurationList = clientService.getCallerConfigs();
            for(CallerConfiguration callerConfiguration : callerConfigurationList) {
                if(callerConfiguration.getNextPoll() == 1) { // run by nextPoll since last service status is true
                    
                    try {
                        servicePollStatus = pollServices(clientService.getHost(), clientService.getPort());
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
                
            if (!clientService.getUpStatus()) { // if service is down as of last poll
                for(CallerConfiguration callerConfiguration : callerConfigurationList) {
                    if(callerConfiguration.getGraceTimeExpiration() == 1) { // run by graceTimeExpiration since last service status is false
                        
                        try {
                            servicePollStatus = pollServices(clientService.getHost(), clientService.getPort());
                            if(servicePollStatus) {
                                // get back to starting point of polling
                                callerConfiguration.setNextPoll(callerConfiguration.getPollingFrequency());
                                callerConfiguration.setGraceTimeExpiration(callerConfiguration.getGraceTime());
                            } else {
                                for(String notifyEmail : callerConfiguration.getNotifyEmail()) {
                                    // TODO: email notify
                                    LOGGER.info("Email notification is sent to " + notifyEmail + " on " + 
                                        clientService.getHost() + ":" + clientService.getPort());
                                }
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
            
            clientServiceRepository.save(clientService);
            
        } // clientService for loop end
        
    }
    
	private boolean pollServices(String host, int port) throws IOException {
	    
		Socket socket = null;
		boolean isConnected = false;
		
		try {
			socket = SocketFactory.getDefault().createSocket(host, port);
			isConnected = socket.isConnected();
			LOGGER.info("CONNECTED: " + isConnected);
		} catch (UnknownHostException e) {
		    LOGGER.warn("ERROR unknown host: " + e);
		} catch (IOException e) {
		    LOGGER.error("ERROR IO: " + e);
		} finally {
			if(socket != null) {
				socket.close();
			}
		}
		
		return isConnected;
		
	}
	
}