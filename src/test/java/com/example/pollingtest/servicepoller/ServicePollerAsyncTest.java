package com.example.pollingtest.servicepoller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.pollingtest.model.CallerConfiguration;
import com.example.pollingtest.model.ClientService;
import com.example.pollingtest.model.Outage;
import com.example.pollingtest.repository.ClientServiceRepository;

public class ServicePollerAsyncTest {
	
	private ClientServiceRepository clientServiceRepositoryMock;
	
    private JavaMailSender emailSenderMock;
    
    private ServicePollerAsync servicePollerAsync;
    
    private ClientService clientService;
	
	private Outage outage;
	
	private CallerConfiguration callerConfiguration;
    
    @Before
	public void setUp() {
    		clientServiceRepositoryMock = Mockito.mock(ClientServiceRepository.class);
    		emailSenderMock = Mockito.mock(JavaMailSender.class);
    		servicePollerAsync = new ServicePollerAsync();
    		ReflectionTestUtils.setField(servicePollerAsync, "clientServiceRepository", clientServiceRepositoryMock);
    		ReflectionTestUtils.setField(servicePollerAsync, "emailSender", emailSenderMock);
    		ReflectionTestUtils.setField(servicePollerAsync, "emailSubject", "Monitored service is down");
    		ReflectionTestUtils.setField(servicePollerAsync, "emailText", "Monitored service %s %d is down");
    		
    		createClientServiceObject();
    }
    
    @Test
	public void testPollSingleService_WithUpStatusTrue() {
    		Date now = new Date();
    		callerConfiguration.setNextPoll(1);
    		callerConfiguration.setGraceTimeExpiration(1);
    		
    		servicePollerAsync.pollSingleService(clientService, now);
    		Mockito.verify(clientServiceRepositoryMock, Mockito.times(1)).save(Mockito.any(ClientService.class));
    		Mockito.verify(emailSenderMock, Mockito.times(1)).send(Mockito.any(SimpleMailMessage.class));
    }
    
    @Test
	public void testPollSingleService_WithUpStatusFalse() {
    		Date now = new Date();
    		clientService.setUpStatus(false);
    		callerConfiguration.setGraceTimeExpiration(1);
    		
    		servicePollerAsync.pollSingleService(clientService, now);
    		Mockito.verify(clientServiceRepositoryMock, Mockito.times(1)).save(Mockito.any(ClientService.class));
    		Mockito.verify(emailSenderMock, Mockito.times(1)).send(Mockito.any(SimpleMailMessage.class));
    }
    
    private void createClientServiceObject() {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("433")); // 433 Z ZULU = UTC time zone
		calendar.set(2000, Calendar.JANUARY, 30);
		Date startTime = calendar.getTime();
		calendar.add(Calendar.MONTH, 2);
		Date endTime = calendar.getTime();
		outage = new Outage(startTime, endTime);
		
		List<String> notifyEmail = new ArrayList<String>();
		notifyEmail.add("testemail@gmail.com");
		callerConfiguration = new CallerConfiguration("1234", 5, 5, notifyEmail, 3, 3);
		List<CallerConfiguration> callerConfigs = new ArrayList<CallerConfiguration>();
		callerConfigs.add(callerConfiguration);
		
		clientService = new ClientService("serviceName", "host", 8888, true, outage, callerConfigs);
	}
	
}
