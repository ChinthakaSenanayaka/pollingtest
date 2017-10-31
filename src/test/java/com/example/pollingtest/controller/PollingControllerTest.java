package com.example.pollingtest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
	private PollingService pollingServiceMock;
	
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
    		Mockito.when(pollingServiceMock.saveClientService(Mockito.any(ClientService.class))).thenReturn(clientService);
 
        mockMvc.perform(post("/service").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsBytes(clientService)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.host", is("host")))
                .andExpect(jsonPath("$.port", is(8888)));
    }
    
    @Test
    public void testSaveCaller() throws Exception {
    		Mockito.when(pollingServiceMock.saveCaller(Mockito.any(Caller.class))).thenReturn(caller);
 
        mockMvc.perform(post("/caller").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsBytes(caller)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.username", is("username")))
                .andExpect(jsonPath("$.password", is("password")));
    }
    
    @Test
    public void testSetupOutage() throws Exception {
    		Mockito.when(pollingServiceMock.maintainOutage(Mockito.anyString(), Mockito.anyInt(), Mockito.any(Outage.class))).thenReturn(outage);
 
        mockMvc.perform(post("/host/{host}/port/{port}/outage", "host", 8888, objectMapper.writeValueAsBytes(outage))
        			.contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsBytes(outage)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.startTime", is(not(empty()))))
                .andExpect(jsonPath("$.endTime", is(not(empty()))));
    }
    
    @Test
    public void testDeleteService() throws Exception {
        mockMvc.perform(delete("/host/{host}/port/{port}/service", "host", 8888)
        			.contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
        Mockito.verify(pollingServiceMock, Mockito.times(1)).deleteClientService(Mockito.anyString(), Mockito.anyInt());
    }
    
    @Test
    public void testDeleteCaller() throws Exception {
        mockMvc.perform(delete("/caller")
        			.contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsBytes(caller)))
                .andExpect(status().isNoContent());
        Mockito.verify(pollingServiceMock, Mockito.times(1)).deleteCaller(Mockito.any(Caller.class));
    }
    
    @Test
    public void testDeleteOutage() throws Exception {
        mockMvc.perform(delete("/host/{host}/port/{port}/outage", "host", 8888)
        			.contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
        Mockito.verify(pollingServiceMock, Mockito.times(1)).maintainOutage(Mockito.anyString(), Mockito.anyInt(), Mockito.isNull(Outage.class));
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
