package com.sample.application.demo;

import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableJpaRepositories("com.sample.application.demo.repository")
@EntityScan("com.sample.application.demo.model")
@SpringBootApplication
@EnableAsync
public class DemoApplication extends AsyncConfigurerSupport {


	@Value("${spring.threads.corePoolSize}")
	int corePoolSize;

	@Value("${spring.threads.maxPoolSize}")
	int maxPoolSize;

	@Value("${spring.threads.queueCapacity}")
	int queueCapacity;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setThreadNamePrefix("Graceful-Startup-");
		executor.initialize();
		return executor;
	}
}
