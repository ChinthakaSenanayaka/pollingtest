package com.example.pollingtest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.pollingtest.model.ClientService;

/**
 * Service monitoring entry repository interface (DAO)
 * 
 * @author Chinthaka Senanayaka
 * @since 31-Oct-2017
 *
 */
public interface ClientServiceRepository extends MongoRepository<ClientService, Long>, ClientServiceRepositoryCustom {
	
	/**
	 * To find service monitoring entry by host and port fields
	 * 
	 * @param host the host field
	 * @param port the port field
	 * @return service monitoring record
	 */
	@Query("{host: '?0', port: ?1}")
	ClientService findByHostAndPort(String host, Integer port);
	
}
