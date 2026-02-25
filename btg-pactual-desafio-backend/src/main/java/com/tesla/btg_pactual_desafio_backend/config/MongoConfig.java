package com.tesla.btg_pactual_desafio_backend.config;

import com.mongodb.ConnectionString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MongoConfig {

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory(
            @Value("${spring.data.mongodb.uri:${spring.mongodb.uri:}}") String uri) {
        if (uri == null || uri.isBlank()) {
            throw new IllegalStateException("MongoDB URI is not configured. Set spring.data.mongodb.uri or spring.mongodb.uri.");
        }
        return new SimpleMongoClientDatabaseFactory(new ConnectionString(uri));
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTemplate(mongoDatabaseFactory);
    }
}
