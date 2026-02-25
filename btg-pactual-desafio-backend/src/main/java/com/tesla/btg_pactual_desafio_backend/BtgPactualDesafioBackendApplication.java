package com.tesla.btg_pactual_desafio_backend;

import java.time.Instant;

import com.mongodb.MongoWriteException;
import org.bson.Document;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class BtgPactualDesafioBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BtgPactualDesafioBackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner ensureMongoDbCreated(MongoTemplate mongoTemplate) {
		return args -> {
			// Force MongoDB to create the database by doing a short-lived write.
			String collection = "__bootstrap__";
			Document doc = new Document("_id", "startup").append("ts", Instant.now().toString());
			try {
				mongoTemplate.getCollection(collection).insertOne(doc);
			} catch (MongoWriteException ex) {
				if (ex.getError() == null || ex.getError().getCode() != 11000) {
					throw ex;
				}
				// If a previous run left the doc behind, clean it up and continue.
				mongoTemplate.getCollection(collection).deleteOne(new Document("_id", "startup"));
				mongoTemplate.getCollection(collection).insertOne(doc);
			}
			mongoTemplate.getCollection(collection).deleteOne(new Document("_id", "startup"));
		};
	}

}
