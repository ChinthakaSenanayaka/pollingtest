package com.example.pollingtest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.pollingtest.model.Caller;

public interface CallerRepository extends MongoRepository<Caller, Long> {
	
	@Query("{username: '?0', password: '?1'}")
	Caller findByUsernameAndPassword(String username, String password);
	
}
