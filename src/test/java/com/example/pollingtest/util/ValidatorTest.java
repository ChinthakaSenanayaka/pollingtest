package com.example.pollingtest.util;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.pollingtest.exceptions.BadRequestException;
import com.example.pollingtest.exceptions.NotFoundException;
import com.example.pollingtest.model.Caller;
import com.example.pollingtest.model.CallerConfiguration;
import com.example.pollingtest.model.ClientService;
import com.example.pollingtest.model.Outage;
import com.example.pollingtest.repository.CallerRepository;
import com.example.pollingtest.repository.ClientServiceRepository;

public class ValidatorTest {
	
	@Autowired
	private CallerRepository callerRepositoryMock;
	
	@Autowired
    private ClientServiceRepository clientServiceRepositoryMock;
	
	private Validator validator;
	
	private ClientService clientService;
	
	private Caller caller;
	
	private Outage outage;
	
	private CallerConfiguration callerConfiguration;
	
	@Before
	public void setUp() {
		validator = new Validator();
		callerRepositoryMock = Mockito.mock(CallerRepository.class);
		clientServiceRepositoryMock = Mockito.mock(ClientServiceRepository.class);
		ReflectionTestUtils.setField(validator, "callerRepository", callerRepositoryMock);
		ReflectionTestUtils.setField(validator, "clientServiceRepository", clientServiceRepositoryMock);
		
		createClientServiceObject();
		createCallerObject();
	}
	
	@Test
	public void testValidateCaller() throws NotFoundException {
		Mockito.when(callerRepositoryMock.findByUsernameAndPassword(Mockito.anyString(), Mockito.anyString())).thenReturn(caller);
		Caller dbCaller = validator.validateCaller(caller);
		assertNotNull(dbCaller);
	}
	
	@Test(expected = NotFoundException.class)
	public void testValidateCallerWithNotFoundException() throws NotFoundException {
		Mockito.when(callerRepositoryMock.findByUsernameAndPassword(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
		validator.validateCaller(caller);
	}
	
	@Test
	public void testValidateClientService() throws NotFoundException {
		Mockito.when(clientServiceRepositoryMock.findByHostAndPort(Mockito.anyString(), Mockito.anyInt())).thenReturn(clientService);
		ClientService dbClientService = validator.validateClientService("host", 1234);
		assertNotNull(dbClientService);
	}
	
	@Test(expected = NotFoundException.class)
	public void testValidateClientServiceWithNotFoundException() throws NotFoundException {
		Mockito.when(clientServiceRepositoryMock.findByHostAndPort(Mockito.anyString(), Mockito.anyInt())).thenReturn(null);
		validator.validateClientService("host", 1234);
	}
	
	@Test
	public void testValidateOutage() throws BadRequestException {
		Validator.validateOutage(outage);
	}
	
	@Test(expected = BadRequestException.class)
	public void testValidateOutageWithNoFields() throws BadRequestException {
		outage.setStartTime(null);
		Validator.validateOutage(outage);
	}
	
	@Test(expected = BadRequestException.class)
	public void testValidateOutageWithStartAfterEnd() throws BadRequestException {
		Date endTime = outage.getEndTime();
		outage.setEndTime(outage.getStartTime());
		outage.setStartTime(endTime);
		Validator.validateOutage(outage);
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
