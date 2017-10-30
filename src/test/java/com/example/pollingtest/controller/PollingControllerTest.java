package com.example.pollingtest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.pollingtest.dto.CallerConfigDTO;
import com.example.pollingtest.model.Caller;
import com.example.pollingtest.model.CallerConfiguration;
import com.example.pollingtest.model.ClientService;
import com.example.pollingtest.model.Outage;
import com.example.pollingtest.service.PollingService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PollingControllerTest {
	
	@Mock
	private PollingService pollingService;
	
	@InjectMocks
    private PollingController pollingController;
 
    private MockMvc mockMvc;
    
    private ObjectMapper objectMapper;
    
    private ClientService clientService;
	
	private Outage outage;
	
	private CallerConfiguration callerConfiguration;
	
	private Caller caller;
	
	private CallerConfigDTO callerConfigDTO;
 
    @Before
    public void setup() {
    		MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pollingController).build();
        objectMapper = new ObjectMapper();
        
        createClientServiceObject();
		createCallerObject();
		callerConfigDTO = new CallerConfigDTO(caller, callerConfiguration);
    }
    
    @Test
    public void testSaveService() throws Exception {
    		Mockito.when(pollingService.saveClientService(Mockito.any(ClientService.class))).thenReturn(clientService);
 
        mockMvc.perform(post("/service").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsBytes(clientService)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.host", is("host")));
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
