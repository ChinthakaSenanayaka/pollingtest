package com.example.pollingtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.MongoTimeoutException;

/**
 * MongoDB database configuration class.
 * 
 * @author Chinthaka Senanayaka
 * @since 31-Oct-2017
 *
 */
@Configuration
@ComponentScan
public class MongoConfigurations extends AbstractMongoConfiguration {

  @Value("${com.example.pollingtest.configuration.monitoringDB}")
  private String monitoringDB;

  private static final Logger LOGGER = LoggerFactory.getLogger(MongoConfigurations.class);

  @Override
  protected String getDatabaseName() {
    MongoClientURI uri = new MongoClientURI(monitoringDB);
    return uri.getDatabase();
  }

  @Override
  @Bean
  public Mongo mongo() throws Exception {
    Mongo mongo = null;
    try {
      MongoClientURI uri = new MongoClientURI(monitoringDB);
      mongo = new MongoClient(uri);
    } catch (MongoTimeoutException e) {
      LOGGER.error("Mongo timeout exception caught");
      throw e;
    } catch (MongoException e) {
      LOGGER.error("Mongo exception caught");
      throw e;
    }

    return mongo;
  }

}
