package com.example.pollingtest.controller;

import java.io.IOException;
import java.net.Socket;

import javax.net.SocketFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.pollingtest.model.Caller;
import com.example.pollingtest.repository.CallerRepository;

@RestController
public class PollingController {

	@Value("${welcome.message:test}")
	private String message = "Hello World";
	
	@Autowired
	private CallerRepository callerRepository;

	@RequestMapping("/welcome")
	public String welcome() throws IOException {
		
		Socket socket = null;
		boolean isConnected = false;
		
		try {
			socket = SocketFactory.getDefault().createSocket("localhost", 8888);
			isConnected = socket.isConnected();
			System.out.println("CONNECTED: " + isConnected);
		} catch (IOException e) {
			System.out.println("ERROR: " + e);
		} finally {
			if(socket != null) {
				socket.close();
			}
		}
		
		return "welcome, " + isConnected;
	}
	
	@RequestMapping(value = "/saveCaller", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String saveCaller(@RequestBody Caller caller) throws IOException {
		
		Caller dbCaller = callerRepository.save(caller);
		
		return "welcome " + dbCaller.getId();
	}

}