package com.yaavarea.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@EnableMongoRepositories(basePackages = "com.yaavarea.server.repo")
@SpringBootApplication
@EnableMongoAuditing
public class YaavAreaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(YaavAreaServerApplication.class, args);
	}

}
