package com.example.pollingtest.servicepoller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.pollingtest.model.CallerConfiguration;
import com.example.pollingtest.model.ClientService;
import com.example.pollingtest.model.Outage;
import com.example.pollingtest.repository.ClientServiceRepository;

public class ServicePollerTest {
	
	private ClientServiceRepository clientServiceRepositoryMock;
	
	private ServicePollerAsync servicePollerAsyncMock;
	
	private ServicePoller servicePoller;
	
	List<ClientService> clientServiceList;
	
	private ClientService clientService;
	
	private Outage outage;
	
	private CallerConfiguration callerConfiguration;
	
	@Before
	public void setUp() {
		clientServiceRepositoryMock = Mockito.mock(ClientServiceRepository.class);
		servicePollerAsyncMock = Mockito.mock(ServicePollerAsync.class);
		servicePoller = new ServicePoller();
		ReflectionTestUtils.setField(servicePoller, "clientServiceRepository", clientServiceRepositoryMock);
		ReflectionTestUtils.setField(servicePoller, "servicePollerAsync", servicePollerAsyncMock);
		
		createClientServiceObject();
		clientServiceList = new ArrayList<ClientService>();
		clientServiceList.add(clientService);
	}
	
	@Test
	public void testPollForServiceStatus() {
		Mockito.when(clientServiceRepositoryMock.findAll()).thenReturn(clientServiceList);
		servicePoller.pollForServiceStatus();
		Mockito.verify(servicePollerAsyncMock, Mockito.times(1)).pollSingleService(Mockito.any(ClientService.class), Mockito.any(Date.class));
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
		callerConfiguration = new CallerConfiguration("1234", 50, 50, notifyEmail, 30, 30);
		List<CallerConfiguration> callerConfigs = new ArrayList<CallerConfiguration>();
		callerConfigs.add(callerConfiguration);
		
		clientService = new ClientService("serviceName", "host", 8888, true, outage, callerConfigs);
	}
	
}
