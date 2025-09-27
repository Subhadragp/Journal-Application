package com.subcodes.journalApp;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
public class JournalApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		System.setProperty("APP_PORT", String.valueOf(dotenv.get("PORT")));
		System.setProperty("SERVER_PORT_DEV", String.valueOf(dotenv.get("SERVER_PORT_DEV")));
		System.setProperty("SERVER_PORT_PROD", String.valueOf(dotenv.get("SERVER_PORT_prod")));
		System.setProperty("EMAIL_USERNAME", String.valueOf(dotenv.get("EMAIL_USERNAME")));
		System.setProperty("EMAIL_APP_PASSWORD", String.valueOf(dotenv.get("EMAIL_APP_PASSWORD")));
		System.setProperty("WEATHER_API_KEY", String.valueOf(dotenv.get("WEATHER_API_KEY")));
		System.setProperty("MONGO_DB_URI", String.valueOf(dotenv.get("MONGO_DB_URI")));
		System.setProperty("MONGO_USERNAME", String.valueOf(dotenv.get("MONGO_USERNAME")));
		System.setProperty("MONGO_PASSWORD", String.valueOf(dotenv.get("MONGO_PASSWORD")));

		SpringApplication.run(JournalApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
