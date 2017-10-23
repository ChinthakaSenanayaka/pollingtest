package com.example.pollingtest.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class ClientServiceRepositoryImpl implements ClientServiceRepositoryCustom {
	
	@Autowired
    MongoTemplate mongoTemplate;
	
	
	
}
