package com.example.pollingtest.repository;

import org.junit.Before;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.util.ReflectionTestUtils;

public class ClientServiceRepositoryTest {
	
	private MongoTemplate mongoTemplateMock;
	
	private ClientServiceRepositoryImpl clientServiceRepository;
	
	@Before
	public void setUp() {
		mongoTemplateMock = Mockito.mock(MongoTemplate.class);
		clientServiceRepository = new ClientServiceRepositoryImpl();
		ReflectionTestUtils.setField(clientServiceRepository, "mongoTemplate", mongoTemplateMock);
	}
	
}
