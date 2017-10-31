package com.example.pollingtest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.pollingtest.model.Caller;

/**
 * User detail repository interface (DAO).
 * 
 * @author Chinthaka Senanayaka
 * @since 31-Oct-2017
 *
 */
public interface CallerRepository extends MongoRepository<Caller, Long> {
	
	/**
	 * To find user record by username and password
	 * 
	 * @param username user's username field
	 * @param password user's password field
	 * @return user record
	 */
	@Query("{username: '?0', password: '?1'}")
	Caller findByUsernameAndPassword(String username, String password);
	
}
