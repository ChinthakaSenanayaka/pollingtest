package com.example.pollingtest.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.pollingtest.constants.ClientServiceConstants;
import com.example.pollingtest.dto.CallerConfigDTO;
import com.example.pollingtest.exceptions.BadRequestException;
import com.example.pollingtest.exceptions.NotFoundException;
import com.example.pollingtest.model.Caller;
import com.example.pollingtest.model.CallerConfiguration;
import com.example.pollingtest.model.ClientService;
import com.example.pollingtest.model.Outage;
import com.example.pollingtest.repository.CallerRepository;
import com.example.pollingtest.repository.ClientServiceRepository;
import com.example.pollingtest.util.Validator;

public class PollingServiceTest {
	
	private CallerRepository callerRepositoryMock;
	
    private ClientServiceRepository clientServiceRepositoryMock;
    
    private Validator validatorMock;
	
	private PollingService pollingService;
	
	private ClientService clientService;
	
	private Outage outage;
	
	private CallerConfiguration callerConfiguration;
	
	private Caller caller;
	
	private CallerConfigDTO callerConfigDTO;
	
	@Before
	public void setUp() {
		pollingService = new PollingServiceImpl();
		callerRepositoryMock = Mockito.mock(CallerRepository.class);
		clientServiceRepositoryMock = Mockito.mock(ClientServiceRepository.class);
		validatorMock = Mockito.mock(Validator.class);
		ReflectionTestUtils.setField(pollingService, "callerRepository", callerRepositoryMock);
		ReflectionTestUtils.setField(pollingService, "clientServiceRepository", clientServiceRepositoryMock);
		ReflectionTestUtils.setField(pollingService, "validator", validatorMock);
		
		createClientServiceObject();
		createCallerObject();
		callerConfigDTO = new CallerConfigDTO(caller, callerConfiguration);
		
	}
	
	@Test
	public void testSaveClientService() throws BadRequestException {
		Mockito.when(clientServiceRepositoryMock.save(Mockito.any(ClientService.class))).thenReturn(clientService);
		clientService.setServiceName(null);
		clientService.setCallerConfigs(null);
		ClientService dbClientService = pollingService.saveClientService(clientService);
		assertNotNull(dbClientService);
		assertEquals(ClientServiceConstants.CLIENT_SERVICE_NAME, dbClientService.getServiceName());
	}
	
	@Test(expected = BadRequestException.class)
	public void testSaveClientServiceWithBadRequestException() throws BadRequestException {
		Mockito.when(clientServiceRepositoryMock.save(Mockito.any(ClientService.class))).thenReturn(clientService);
		Date startTime = clientService.getOutage().getStartTime();
		clientService.getOutage().setStartTime(clientService.getOutage().getEndTime());
		clientService.getOutage().setEndTime(startTime);
		pollingService.saveClientService(clientService);
	}
	
	@Test
	public void testSaveCaller() {
		Mockito.when(callerRepositoryMock.save(Mockito.any(Caller.class))).thenReturn(caller);
		Caller dbCaller = pollingService.saveCaller(caller);
		assertNotNull(dbCaller);
	}
	
	@Test
	public void testDeleteClientService() throws NotFoundException {
		pollingService.deleteClientService("host", 1234);
		Mockito.verify(clientServiceRepositoryMock, Mockito.times(1)).deleteClientService(Mockito.anyString(), Mockito.anyInt());
	}
	
	@Test
	public void testDeleteCaller() throws NotFoundException {
		Mockito.when(validatorMock.validateCaller(Mockito.any(Caller.class))).thenReturn(caller);
		pollingService.deleteCaller(caller);
		Mockito.verify(clientServiceRepositoryMock, Mockito.times(1)).removeCallerRefs(Mockito.anyString());
		Mockito.verify(callerRepositoryMock, Mockito.times(1)).delete(Mockito.any(Caller.class));
	}
	
	@Test
	public void testDeleteCallerWithNotFoundException() throws NotFoundException {
		Mockito.when(validatorMock.validateCaller(Mockito.any(Caller.class))).thenThrow(NotFoundException.class);
		try {
			pollingService.deleteCaller(caller);
		} catch (NotFoundException e) {}
		Mockito.verify(clientServiceRepositoryMock, Mockito.never()).removeCallerRefs(Mockito.anyString());
		Mockito.verify(callerRepositoryMock, Mockito.never()).delete(Mockito.any(Caller.class));
	}
	
	@Test
	public void testMaintainOutage() throws BadRequestException, NotFoundException {
		Mockito.when(validatorMock.validateClientService(Mockito.anyString(), Mockito.anyInt())).thenReturn(clientService);
		Mockito.when(clientServiceRepositoryMock.maintainOutage(Mockito.anyString(), Mockito.anyInt(), Mockito.any(Outage.class))).thenReturn(outage);
		Outage dbOutage = pollingService.maintainOutage("host", 1234, outage);
		assertNotNull(dbOutage);
	}
	
	@Test(expected = BadRequestException.class)
	public void testMaintainOutageWithBadRequestException() throws BadRequestException, NotFoundException {
		Mockito.when(validatorMock.validateClientService(Mockito.anyString(), Mockito.anyInt())).thenReturn(clientService);
		Date startTime = outage.getStartTime();
		outage.setStartTime(outage.getEndTime());
		outage.setEndTime(startTime);
		pollingService.maintainOutage("host", 1234, outage);
	}
	
	@Test(expected = NotFoundException.class)
	public void testMaintainOutageWithNotFoundException() throws BadRequestException, NotFoundException {
		Mockito.when(validatorMock.validateClientService(Mockito.anyString(), Mockito.anyInt())).thenThrow(NotFoundException.class);
		pollingService.maintainOutage("host", 1234, outage);
	}
	
	@Test
	public void testSetupCallerServiceWithAppendFalse() throws NotFoundException, BadRequestException {
		Mockito.when(clientServiceRepositoryMock.setupCallerService(Mockito.any(ClientService.class), Mockito.any(CallerConfiguration.class), Mockito.anyBoolean())).thenReturn(callerConfiguration);
		Mockito.when(validatorMock.validateCaller(Mockito.any(Caller.class))).thenReturn(caller);
		Mockito.when(validatorMock.validateClientService(Mockito.anyString(), Mockito.anyInt())).thenReturn(clientService);
		CallerConfiguration dbCallerConfiguration = pollingService.setupCallerService("host", 1234, callerConfigDTO, false);
		assertNotNull(dbCallerConfiguration);
	}
	
	@Test
	public void testSetupCallerServiceWithAppendTrue() throws NotFoundException, BadRequestException {
		Mockito.when(clientServiceRepositoryMock.setupCallerService(Mockito.any(ClientService.class), Mockito.any(CallerConfiguration.class), Mockito.anyBoolean())).thenReturn(callerConfiguration);
		Mockito.when(validatorMock.validateCaller(Mockito.any(Caller.class))).thenReturn(caller);
		Mockito.when(validatorMock.validateClientService(Mockito.anyString(), Mockito.anyInt())).thenReturn(clientService);
		CallerConfiguration dbCallerConfiguration = pollingService.setupCallerService("host", 1234, callerConfigDTO, true);
		assertNotNull(dbCallerConfiguration);
	}
	
	@Test
	public void testRemoveCallerService() throws NotFoundException, BadRequestException {
		Mockito.when(validatorMock.validateCaller(Mockito.any(Caller.class))).thenReturn(caller);
		Mockito.when(validatorMock.validateClientService(Mockito.anyString(), Mockito.anyInt())).thenReturn(clientService);
		pollingService.removeCallerService("host", 1234, caller);
		Mockito.verify(clientServiceRepositoryMock, Mockito.times(1)).removeCallerService(Mockito.any(ClientService.class), Mockito.anyString());
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
	
	private void createCallerObject() {
		caller = new Caller("username", "password", "callerName");
		caller.setId("1234");
	}

}
