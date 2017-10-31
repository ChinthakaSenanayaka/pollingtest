package com.example.pollingtest.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Client service monitoring configuration class.
 * 
 * @author Chinthaka Senanayaka
 * @since 31-Oct-2017
 *
 */
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

    /**
     * Default constructor
     */
	public ClientService() {}

	/**
	 * Fields setting constructor
	 * 
	 * @param serviceName service name field
	 * @param host host field
	 * @param port port field
	 * @param upStatus service up status field
	 * @param outage outage configuration field
	 * @param callerConfigs list of user configurations for service monitoring
	 */
	public ClientService(String serviceName, String host, Integer port, Boolean upStatus, Outage outage, List<CallerConfiguration> callerConfigs) {
		this.serviceName = serviceName;
		this.host = host;
		this.port = port;
		this.upStatus = upStatus;
		this.outage = outage;
		this.callerConfigs = callerConfigs;
	}

	/**
	 * Getter for id field
	 * 
	 * @return id field
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter for id field
	 * 
	 * @param id id field
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Getter for service name field
	 * 
	 * @return service name field
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * Setter for service name field
	 * 
	 * @param serviceName service name field
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * Getter for host field
	 * 
	 * @return host field
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Setter for host field
	 * 
	 * @param host host field
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * Getter for port field
	 * 
	 * @return port field
	 */
	public Integer getPort() {
		return port;
	}

	/**
	 * Setter for port field
	 * 
	 * @param port port field
	 */
	public void setPort(Integer port) {
		this.port = port;
	}

	/**
	 * Getter for service up status field
	 * 
	 * @return service up status field
	 */
	public Boolean getUpStatus() {
		return upStatus;
	}

	/**
	 * Setter for service up status field
	 * 
	 * @param upStatus service up status field
	 */
	public void setUpStatus(Boolean upStatus) {
		this.upStatus = upStatus;
	}

	/**
	 * Getter for outage configuration field
	 * 
	 * @return outage configuration field
	 */
	public Outage getOutage() {
		return outage;
	}

	/**
	 * Setter for outage configuration field
	 * 
	 * @param outage outage configuration field
	 */
	public void setOutage(Outage outage) {
		this.outage = outage;
	}

	/**
	 * Getter for list of user configurations for service monitoring
	 * 
	 * @return list of user configurations for service monitoring
	 */
	public List<CallerConfiguration> getCallerConfigs() {
		return callerConfigs;
	}

	/**
	 * Setter for list of user configurations for service monitoring
	 * 
	 * @param callerConfigs list of user configurations for service monitoring
	 */
	public void setCallerConfigs(List<CallerConfiguration> callerConfigs) {
		this.callerConfigs = callerConfigs;
	}
	
}
