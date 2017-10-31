package com.example.pollingtest.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * User details model class.
 * 
 * @author Chinthaka Senanayaka
 * @since 31-Oct-2017
 *
 */
@Document(collection = "caller")
@CompoundIndexes({
    @CompoundIndex(name = "username_1_password_1", unique = true, def = "{'username' : 1, 'password': 1}")
})
public class Caller {
	
	@Id
    private String id;
	
	private String username;
	
	private String password;
	
	private String callerName;

	/**
	 * Default constructor
	 */
	public Caller() {}

	public Caller(String username, String password, String callerName) {
		this.username = username;
		this.password = password;
		this.callerName = callerName;
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
	 * Getter for username
	 * 
	 * @return username field
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Setter for username
	 * 
	 * @param username username field
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Getter for password
	 * 
	 * @return password field
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter for password
	 * 
	 * @param password password field
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Getter for caller name
	 * 
	 * @return caller name field
	 */
	public String getCallerName() {
		return callerName;
	}

	/**
	 * Setter for caller name
	 * 
	 * @param callerName caller name field
	 */
	public void setCallerName(String callerName) {
		this.callerName = callerName;
	}
	
}
