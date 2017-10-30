package com.example.pollingtest.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.pollingtest.exceptions.NotFoundException;
import com.example.pollingtest.model.Caller;
import com.example.pollingtest.model.CallerConfiguration;
import com.example.pollingtest.model.ClientService;
import com.example.pollingtest.model.Outage;
import com.mongodb.WriteResult;

public class ClientServiceRepositoryTest {
	
	private MongoTemplate mongoTemplateMock;
	
	private ClientServiceRepositoryImpl clientServiceRepository;
	
	private ClientService clientService;
	
	private Outage outage;
	
	private CallerConfiguration callerConfiguration;
	
	private List<ClientService> clientServiceList;
	
	@Before
	public void setUp() {
		mongoTemplateMock = Mockito.mock(MongoTemplate.class);
		clientServiceRepository = new ClientServiceRepositoryImpl();
		ReflectionTestUtils.setField(clientServiceRepository, "mongoTemplate", mongoTemplateMock);
		
		createClientServiceObject();
		clientServiceList = new ArrayList<ClientService>();
		clientServiceList.add(clientService);
	}
	
	@Test
	public void testMaintainOutage() {
		Mockito.when(mongoTemplateMock.findAndModify(Mockito.any(Query.class), Mockito.any(Update.class), Matchers.<Class<ClientService>>any())).thenReturn(clientService);
		Outage dbOutage = clientServiceRepository.maintainOutage("host", 1234, outage);
		assertNotNull(dbOutage);
	}
	
	@Test
	public void testMaintainOutage_Null() {
		clientService.setOutage(null);
		Mockito.when(mongoTemplateMock.findAndModify(Mockito.any(Query.class), Mockito.any(Update.class), Matchers.<Class<ClientService>>any())).thenReturn(clientService);
		Outage dbOutage = clientServiceRepository.maintainOutage("host", 1234, null);
		assertNull(dbOutage);
		Mockito.verify(mongoTemplateMock, Mockito.times(1)).findAndModify(Mockito.any(Query.class), Mockito.any(Update.class), Matchers.<Class<ClientService>>any());
	}
	
	@Test
	public void testSetupCallerService_WhileNoDbCallerConfigs() {
		clientService.setCallerConfigs(new ArrayList<CallerConfiguration>());
		callerConfiguration.setPollingFrequency(120);
		callerConfiguration.setNextPoll(100);
		CallerConfiguration dbCallerConfiguration = clientServiceRepository.setupCallerService(clientService, callerConfiguration, true);
		assertNotNull(dbCallerConfiguration);
		assertEquals(dbCallerConfiguration.getPollingFrequency(), new Integer(120));
		assertEquals(dbCallerConfiguration.getNextPoll(), new Integer(100));
		Mockito.verify(mongoTemplateMock, Mockito.times(1)).save(Mockito.any(ClientService.class));
	}
	
	@Test
	public void testSetupCallerService_WithAppendTrueWhileDbCallerConfigsExist() {
		callerConfiguration.setPollingFrequency(120);
		CallerConfiguration dbCallerConfiguration = clientServiceRepository.setupCallerService(clientService, callerConfiguration, true);
		assertNotNull(dbCallerConfiguration);
		assertEquals(dbCallerConfiguration.getPollingFrequency(), new Integer(120));
		assertEquals(dbCallerConfiguration.getNextPoll(), new Integer(50));
		Mockito.verify(mongoTemplateMock, Mockito.times(1)).save(Mockito.any(ClientService.class));
	}
	
	@Test
	public void testSetupCallerService_WithAppendFalseWhileDbCallerConfigsExist() {
		callerConfiguration.setPollingFrequency(120);
		callerConfiguration.setNextPoll(100);
		CallerConfiguration dbCallerConfiguration = clientServiceRepository.setupCallerService(clientService, callerConfiguration, false);
		assertNotNull(dbCallerConfiguration);
		assertEquals(dbCallerConfiguration.getPollingFrequency(), new Integer(120));
		assertEquals(dbCallerConfiguration.getNextPoll(), new Integer(100));
		Mockito.verify(mongoTemplateMock, Mockito.times(1)).save(Mockito.any(ClientService.class));
	}
	
	@Test
	public void testDeleteClientService() throws NotFoundException {
		WriteResult writeResultMock = Mockito.mock(WriteResult.class);
		Mockito.when(writeResultMock.getN()).thenReturn(1);
		Mockito.when(mongoTemplateMock.remove(Mockito.any(Query.class), Matchers.<Class<ClientService>>any())).thenReturn(writeResultMock);
		clientServiceRepository.deleteClientService("host", 1234);
		Mockito.verify(mongoTemplateMock, Mockito.times(1)).remove(Mockito.any(Query.class), Matchers.<Class<ClientService>>any());
	}
	
	@Test(expected = NotFoundException.class)
	public void testDeleteClientService_WithNoDBRecord() throws NotFoundException {
		WriteResult writeResultMock = Mockito.mock(WriteResult.class);
		Mockito.when(writeResultMock.getN()).thenReturn(0);
		Mockito.when(mongoTemplateMock.remove(Mockito.any(Query.class), Matchers.<Class<ClientService>>any())).thenReturn(writeResultMock);
		clientServiceRepository.deleteClientService("host", 1234);
		Mockito.verify(mongoTemplateMock, Mockito.times(1)).remove(Mockito.any(Query.class), Matchers.<Class<ClientService>>any());
	}
	
	@Test
	public void testRemoveCallerRefs() {
		Mockito.when(mongoTemplateMock.findAll(Matchers.<Class<ClientService>>any())).thenReturn(clientServiceList);
		clientServiceRepository.removeCallerRefs("1234");
		Mockito.verify(mongoTemplateMock, Mockito.times(1)).findAll(Matchers.<Class<ClientService>>any());
		Mockito.verify(mongoTemplateMock, Mockito.times(1)).updateFirst(Mockito.any(Query.class), Mockito.any(Update.class), Matchers.<Class<ClientService>>any());
	}
	
	@Test
	public void testRemoveCallerRefs_WithNoCallers() {
		clientService.setCallerConfigs(new ArrayList<CallerConfiguration>());
		Mockito.when(mongoTemplateMock.findAll(Matchers.<Class<ClientService>>any())).thenReturn(clientServiceList);
		clientServiceRepository.removeCallerRefs("1234");
		Mockito.verify(mongoTemplateMock, Mockito.times(1)).findAll(Matchers.<Class<ClientService>>any());
		Mockito.verify(mongoTemplateMock, Mockito.never()).updateFirst(Mockito.any(Query.class), Mockito.any(Update.class), Matchers.<Class<ClientService>>any());
	}
	
	@Test
	public void testRemoveCallerService() {
		clientServiceRepository.removeCallerService(clientService, "1234");
		Mockito.verify(mongoTemplateMock, Mockito.times(1)).save(Mockito.any(ClientService.class));
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
