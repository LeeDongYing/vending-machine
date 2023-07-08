package com.dongying.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DB_DataSourceConfig {

//   @Primary
//   @Bean(name = "oracle")
//   @ConfigurationProperties(prefix = "springboot.datasource.oracle")
//   public DataSource oracleDataSource() {
//      return DataSourceBuilder.create().build();
//   }

	@Bean(name = "mysql")
	@ConfigurationProperties(prefix = "springboot.datasource.mysql")
	public DataSource postgreDataSourceBackend() {
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean(name = "mongodb")
	@ConfigurationProperties(prefix = "springboot.datasource.mongodb")
	public MongoProperties mongoProperties() {
		return new MongoProperties();
	}

}
