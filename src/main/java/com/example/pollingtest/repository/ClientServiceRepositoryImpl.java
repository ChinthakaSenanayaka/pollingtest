package com.example.pollingtest.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.example.pollingtest.constants.ClientServiceConstants;
import com.example.pollingtest.model.CallerConfiguration;
import com.example.pollingtest.model.ClientService;
import com.example.pollingtest.model.Outage;

public class ClientServiceRepositoryImpl implements ClientServiceRepositoryCustom {
	
	@Autowired
    MongoTemplate mongoTemplate;
	
	public Outage setupOutage(String host, Integer port, Outage outage) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where(ClientServiceConstants.HOST).is(host).
				andOperator(Criteria.where(ClientServiceConstants.PORT).is(port)));
		
		Update update = new Update();
		update.set(ClientServiceConstants.OUTAGE, outage);
		
		ClientService clientService = mongoTemplate.findAndModify(query, update, ClientService.class);
		
		return clientService.getOutage();
		
	}
	
	public void deleteOutage(String host, Integer port) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where(ClientServiceConstants.HOST).is(host).
				andOperator(Criteria.where(ClientServiceConstants.PORT).is(port)));
		
		Update update = new Update();
		update.set(ClientServiceConstants.OUTAGE, null);
		
		mongoTemplate.findAndModify(query, update, ClientService.class);
		
	}
	
	public CallerConfiguration setupCallerService(String host, Integer port, CallerConfiguration callerConfiguration, boolean append) {
		
		Query query = new Query();
		Update update = new Update();
		
		if(append) {
			query.addCriteria(Criteria.where(ClientServiceConstants.HOST).is(host).
					andOperator(Criteria.where(ClientServiceConstants.PORT).is(port)).
					andOperator(Criteria.where(ClientServiceConstants.CALLER_CONFIGS_CALLER_ID).is(callerConfiguration.getCallerId())));
			
			if(callerConfiguration.getPollingFrequency() != null) {
				update.set(ClientServiceConstants.CALLER_CONFIGS_$_POLL_FREQUENCY, callerConfiguration.getPollingFrequency());
			}
			if(callerConfiguration.getNotifyEmail() != null) {
				update.set(ClientServiceConstants.CALLER_CONFIGS_$_NOTIFY_EMAIL, callerConfiguration.getNotifyEmail());
			}
			if(callerConfiguration.getGraceTime() != null) {
				update.set(ClientServiceConstants.CALLER_CONFIGS_$_GRACE_TIME, callerConfiguration.getGraceTime());
			}
			
		} else {
			query.addCriteria(Criteria.where(ClientServiceConstants.HOST).is(host).
					andOperator(Criteria.where(ClientServiceConstants.PORT).is(port)));
			
			update.addToSet(ClientServiceConstants.CALLER_CONFIGS_CALLER_ID, callerConfiguration);
		}
		ClientService clientService = mongoTemplate.findAndModify(query, update, ClientService.class);
		
		CallerConfiguration dbCallerConfiguration = null;
		for(CallerConfiguration dbInternalCallerConfiguration : clientService.getCallerConfigs()) {
			if(dbInternalCallerConfiguration.getCallerId().equals(callerConfiguration.getCallerId())) {
				dbCallerConfiguration = dbInternalCallerConfiguration;
				break;
			}
		}
		
		return dbCallerConfiguration;
		
	}
	
}
