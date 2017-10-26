package com.example.pollingtest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.pollingtest.model.Outage;

public interface OutageRepository extends MongoRepository<Outage, Long> {

}
