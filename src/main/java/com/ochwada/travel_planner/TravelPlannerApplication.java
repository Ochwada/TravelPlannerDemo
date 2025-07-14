package com.ochwada.travel_planner;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Healthcare Management System application.
 * This Spring Boot application loads environment variables from a `.env` file using the dotenv-java library.
 * Specifically, it looks for the `MONGODB_URI`  and 'OPENWEATHER_API' variables and sets it as a system property for
 * use by Spring Data MongoDB.
 *
 * @author Ochwada
 */
@SpringBootApplication
public class TravelPlannerApplication {


	/**
	 * Main method that launches the Spring Boot application.
	 *
	 * @param args application arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(TravelPlannerApplication.class, args);
	}

	//=================================== .env SETTINGS ======================================================
	static {
		// Load environment variables from .env (ignore if .env is missing, e.g., on Heroku)
		Dotenv dotenv = Dotenv.configure()
				.ignoreIfMissing()
				.load();

		// Retrieve and set MongoDB URI (Set the URI as a system property if found, so Spring Boot can use it)
		String uri = dotenv.get("MONGODB_URI");
		if (uri != null) {
			System.setProperty("MONGODB_URI", uri);
			System.out.println("✅ MONGODB_URI loaded and set.");
		} else {
			System.out.println("⚠️ MONGODB_URI not found in .env file. Skipping System.setProperty.");
		}

		// Retrieve and set OpenWeather API key for Spring Boot
		String weatherApiKey = dotenv.get("OPENWEATHER_API");
		if (weatherApiKey != null) {
			System.setProperty("OPENWEATHER_API", weatherApiKey);
			System.out.println("✅ OPENWEATHER_API loaded and set.");
		} else {
			System.out.println("⚠️ OPENWEATHER_API not found in .env file.");
		}

	}

}
