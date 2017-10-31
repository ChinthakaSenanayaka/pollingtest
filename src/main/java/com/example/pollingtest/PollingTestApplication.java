package com.example.pollingtest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Polling test application starting class.
 * 
 * @author Chinthaka Senanayaka
 * @since 31-Oct-2017
 *
 */
@SpringBootApplication
@EnableAsync
public class PollingTestApplication {
	
	@Value("${com.example.pollingtest.async.corePoolSize}")
	private Integer corePoolSize;
	
	@Value("${com.example.pollingtest.async.maxPoolSize}")
	private Integer maxPoolSize;
	
	@Value("${com.example.pollingtest.async.queueCapacity}")
	private Integer queueCapacity;
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(PollingTestApplication.class, args);
	}
	
	/**
	 * Thread pool for asynchronous service polling.
	 * 
	 * @return TaskExecutor thread pool objects.
	 */
	@Bean(name="processExecutor")
    public TaskExecutor workExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setThreadNamePrefix("Async-");
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        threadPoolTaskExecutor.afterPropertiesSet();
        return threadPoolTaskExecutor;
    }

}