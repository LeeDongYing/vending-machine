package com.dongying.config;

import static java.util.Collections.singletonList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
@EnableTransactionManagement
@EnableMongoRepositories(
		basePackages = { "com.dongying.mongoDB.dao" }, 
		mongoTemplateRef = "mongoTemplate")
public class MongoDataSourceConfig {

	@Autowired
	@Qualifier("mongodb")
	private MongoProperties mongoProperties;

	@Bean(name = "mongoClient")
	public MongoClient mongoClient() {

		MongoCredential credential = MongoCredential.createCredential(mongoProperties.getUsername(),
				mongoProperties.getAuthenticationDatabase(), mongoProperties.getPassword());

		return MongoClients.create(MongoClientSettings.builder()
				.applyToClusterSettings(builder -> builder
						.hosts(singletonList(new ServerAddress(mongoProperties.getHost(), mongoProperties.getPort()))))
				.credential(credential).build());
	}

	@Bean(name = "mongoDBFactory")
	public MongoDatabaseFactory mongoDatabaseFactory(@Qualifier("mongoClient") MongoClient mongoClient
			) {
		return new SimpleMongoClientDatabaseFactory(mongoClient, mongoProperties.getDatabase());
	}

	@Bean(name = "mongoTemplate")
	public MongoTemplate mongoTemplate(@Qualifier("mongoDBFactory") MongoDatabaseFactory mongoDatabaseFactory) {
		return new MongoTemplate(mongoDatabaseFactory);
	}
}
