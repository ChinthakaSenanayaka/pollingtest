package com.example.pollingtest.scheduler;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.SocketFactory;

import org.springframework.stereotype.Component;

@Component
public class ServicePollerImpl implements ServicePoller {
	
	public boolean pollServices(String host, int port) throws IOException {
	    
		Socket socket = null;
		boolean isConnected = false;
		
		try {
			socket = SocketFactory.getDefault().createSocket(host, port);
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
		
		return isConnected;
		
	}
	
}
