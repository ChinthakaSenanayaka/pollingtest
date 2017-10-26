package com.example.pollingtest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pollingtest.repository.CallerRepository;
import com.example.pollingtest.repository.ClientServiceRepository;

@Service
public class PollingServiceImpl implements PollingService {
	
	@Autowired
	private CallerRepository callerRepository;
	
	@Autowired
    private ClientServiceRepository clientServiceRepository;
	
}
