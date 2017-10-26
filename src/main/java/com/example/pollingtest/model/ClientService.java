package com.example.pollingtest.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "clientService")
@CompoundIndexes({
    @CompoundIndex(name = "host_1_port_1", unique = true, def = "{'host' : 1, 'port': 1}")
})
public class ClientService {
	
	@Id
    private String id;

    private String serviceName;

    private String host;
    
    private Integer port;
    
    private Boolean upStatus;
    
    private Outage outage;
    
    private List<CallerConfiguration> callerConfigs;

	public ClientService() {}

	public ClientService(String serviceName, String host, Integer port, Boolean upStatus, Outage outage, List<CallerConfiguration> callerConfigs) {
		this.serviceName = serviceName;
		this.host = host;
		this.port = port;
		this.upStatus = upStatus;
		this.outage = outage;
		this.callerConfigs = callerConfigs;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Boolean getUpStatus() {
		return upStatus;
	}

	public void setUpStatus(Boolean upStatus) {
		this.upStatus = upStatus;
	}

	public Outage getOutage() {
		return outage;
	}

	public void setOutage(Outage outage) {
		this.outage = outage;
	}

	public List<CallerConfiguration> getCallerConfigs() {
		return callerConfigs;
	}

	public void setCallerConfigs(List<CallerConfiguration> callerConfigs) {
		this.callerConfigs = callerConfigs;
	}
	
}
