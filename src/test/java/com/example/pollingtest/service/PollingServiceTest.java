package com.example.pollingtest.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.pollingtest.model.Caller;
import com.example.pollingtest.repository.CallerRepository;
import com.example.pollingtest.repository.ClientServiceRepository;

public class PollingServiceTest {
	
	private CallerRepository callerRepositoryMock;
	
    private ClientServiceRepository clientServiceRepositoryMock;
	
	private PollingService pollingService;
	
	Caller caller;
	
	@Before
	public void setUp() {
		pollingService = new PollingServiceImpl();
		callerRepositoryMock = Mockito.mock(CallerRepository.class);
		clientServiceRepositoryMock = Mockito.mock(ClientServiceRepository.class);
		ReflectionTestUtils.setField(pollingService, "callerRepository", callerRepositoryMock);
		ReflectionTestUtils.setField(pollingService, "clientServiceRepository", clientServiceRepositoryMock);
		
		caller = new Caller();
		caller.setCallerName("callerName");
		caller.setId("1234");
		caller.setUsername("username");
		caller.setPassword("password");
	}

	@Test
	public void testSaveCaller() {
		Mockito.when(callerRepositoryMock.save(Mockito.any(Caller.class))).thenReturn(caller);
		Caller dbCaller = pollingService.saveCaller(caller);
		assertNotNull(dbCaller);
	}

}
