package com.service.category;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableReactiveMongoRepositories
@Configuration
public class MongoDBConfig extends AbstractReactiveMongoConfiguration {

    @Value("${db.url}")
    private String url;

    @Value("${db.name}")
    private String name;

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(url);
    }

    @Override
    protected String getDatabaseName() {
        return name;
    }
}