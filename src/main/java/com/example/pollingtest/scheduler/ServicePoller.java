package com.example.pollingtest.scheduler;

import java.io.IOException;

public interface ServicePoller {
	
	boolean pollServices(String host, int port) throws IOException;
	
}
