package com.example.pollingtest.scheduler;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.SocketFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.pollingtest.repository.ClientServiceRepository;

@Component
@EnableScheduling
public class ServicePollingScheduler {
	
	@Autowired
	private ClientServiceRepository clientServiceRepository;
	
	@Scheduled(fixedDelayString = "${service.polling.scheduled.rate}")
	public void pollServices() throws IOException {
	    
		Socket socket = null;
		boolean isConnected = false;
		
		try {
			socket = SocketFactory.getDefault().createSocket("localhost", 8888);
			isConnected = socket.isConnected();
			System.out.println("CONNECTED: " + isConnected);
		} catch (UnknownHostException e) {
			System.out.println("ERROR unknown host: " + e);
		} catch (IOException e) {
			System.out.println("ERROR IO: " + e);
		} finally {
			if(socket != null) {
				socket.close();
			}
		}
		
	}
	
}
