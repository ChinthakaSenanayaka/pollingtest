package com.example.pollingtest.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

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

	public Caller() {}

	public Caller(String username, String password, String callerName) {
		this.username = username;
		this.password = password;
		this.callerName = callerName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCallerName() {
		return callerName;
	}

	public void setCallerName(String callerName) {
		this.callerName = callerName;
	}
	
}
