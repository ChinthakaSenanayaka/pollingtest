package com.example.pollingtest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.pollingtest.model.ClientService;

public interface ClientServiceRepository extends MongoRepository<ClientService, Long>, ClientServiceRepositoryCustom {
	
	@Query("{host: '?0', port: ?1}")
	ClientService findByHostAndPort(String host, Integer port);
	
}
