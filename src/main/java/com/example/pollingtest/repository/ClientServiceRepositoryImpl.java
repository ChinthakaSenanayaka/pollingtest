package com.example.pollingtest.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.example.pollingtest.constants.ClientServiceConstants;
import com.example.pollingtest.exceptions.NotFoundException;
import com.example.pollingtest.model.CallerConfiguration;
import com.example.pollingtest.model.ClientService;
import com.example.pollingtest.model.Outage;
import com.mongodb.BasicDBObject;
import com.mongodb.WriteResult;

public class ClientServiceRepositoryImpl implements ClientServiceRepositoryCustom {
	
	@Autowired
    private MongoTemplate mongoTemplate;
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	public Outage maintainOutage(final String host, final Integer port, final Outage outage) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where(ClientServiceConstants.HOST).is(host).
				andOperator(Criteria.where(ClientServiceConstants.PORT).is(port)));
		
		Update update = new Update();
		update.set(ClientServiceConstants.OUTAGE, outage);
		
		ClientService clientService = mongoTemplate.findAndModify(query, update, ClientService.class);
		
		return clientService.getOutage();
		
	}
	
	public CallerConfiguration setupCallerService(final ClientService dbClientService, final CallerConfiguration callerConfiguration, boolean append) {
		
		List<CallerConfiguration> dbCallerConfigs = dbClientService.getCallerConfigs();
		boolean foundCallerConfig = false;
		CallerConfiguration returnDbCallerConfig = null;
		for(int callerConifgCounter = 0; callerConifgCounter < dbClientService.getCallerConfigs().size(); callerConifgCounter++) {
			
			CallerConfiguration dbCallerConfig = dbCallerConfigs.get(callerConifgCounter);
			if(dbCallerConfig.getCallerId().equals(callerConfiguration.getCallerId())) {
				foundCallerConfig = true;
				
				if(append) {
					if(callerConfiguration.getPollingFrequency() != null) {
						dbCallerConfig.setPollingFrequency(callerConfiguration.getPollingFrequency());
					}
					if(callerConfiguration.getNotifyEmail() != null) {
						dbCallerConfig.setNotifyEmail(callerConfiguration.getNotifyEmail());
					}
					if(callerConfiguration.getGraceTime() != null) {
						dbCallerConfig.setGraceTime(callerConfiguration.getGraceTime());
					}
					returnDbCallerConfig = dbCallerConfig;
				} else {
					dbCallerConfigs.remove(callerConifgCounter);
					dbCallerConfigs.add(callerConifgCounter, callerConfiguration);
					returnDbCallerConfig = callerConfiguration;
				}
				break;
				
			}
		}
		if(!foundCallerConfig) {
			dbClientService.getCallerConfigs().add(callerConfiguration);
			returnDbCallerConfig = callerConfiguration;
		}
		mongoTemplate.save(dbClientService);
		
		return returnDbCallerConfig;
		
	}
	
	public void deleteClientService(final String host, final Integer port) throws NotFoundException {
		Query query = new Query();
		query.addCriteria(Criteria.where(ClientServiceConstants.HOST).is(host).
				andOperator(Criteria.where(ClientServiceConstants.PORT).is(port)));
		WriteResult dbWriteResult = mongoTemplate.remove(query, ClientService.class);
		if(dbWriteResult.getN() == 0) {
			throw new NotFoundException("Specified client service not found!");
		}
	}
	
	public void removeCallerRefs(final String callerId) {
		
		for(ClientService dbClientService : mongoTemplate.findAll(ClientService.class)) {
			for(CallerConfiguration dbCallerConfig : dbClientService.getCallerConfigs()) {
				if(callerId.equals(dbCallerConfig.getCallerId())) {
					
					Query query = new Query();
					query.addCriteria(Criteria.where(ClientServiceConstants.HOST).is(dbClientService.getHost()).
							andOperator(Criteria.where(ClientServiceConstants.PORT).is(dbClientService.getPort())));
					
					Update update = new Update();
					update.pull(ClientServiceConstants.CALLER_CONFIGS, new BasicDBObject(ClientServiceConstants.CALLER_ID, callerId));
					
					mongoTemplate.updateFirst(query, update, ClientService.class);
					break;
				}
			}
		}
	}

	@Override
	public void removeCallerService(final ClientService dbClientService, final String callerId) {
		
		List<CallerConfiguration> dbCallerConfigs = dbClientService.getCallerConfigs();
		for(int callerConifgCounter = 0; callerConifgCounter < dbClientService.getCallerConfigs().size(); callerConifgCounter++) {
			
			CallerConfiguration dbCallerConfig = dbCallerConfigs.get(callerConifgCounter);
			if(dbCallerConfig.getCallerId().equals(callerId)) {
				dbCallerConfigs.remove(callerConifgCounter);
				break;
			}
		}
		mongoTemplate.save(dbClientService);
		
	}
	
}
