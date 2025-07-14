package com.ochwada.travel_planner.client;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * *******************************************************
 * Package: com.ochwada.travel_planner.client
 * File: WeatherClient.java
 * Author: Ochwada
 * Date: Monday, 14.Jul.2025, 11:38 AM
 * Description: Client to fetch weather data from OpenWeather API
 * Objective:
 * *******************************************************
 */

/**
 * {@code WeatherClient} is a Spring-managed component that provides access to weather data
 * from the OpenWeather API.
 *
 * <p>Annotated with {@link org.springframework.stereotype.Component}, this class is automatically
 * detected and instantiated by Spring's component scanning mechanism. It becomes eligible for
 * dependency injection wherever needed in the application.
 *
 * <p>The API key and base URL for the OpenWeather service are injected from the application's
 * configuration (e.g., {@code application.properties} or environment variables) using the
 * {@link org.springframework.beans.factory.annotation.Value} annotation.
 */
@Component // Tells Spring Boot to manage this class as a bean for dependency injection
public class WeatherClient {

    /**
     * API key for authenticating requests to the OpenWeather API.
     * Injected from the property {@code openweather.api.key}.
     */
    @Value("${openweather.api.key}")
    private String apiKey;

    /**
     * Base URL for the OpenWeather API endpoint.
     * Injected from the property {@code openweather.api.url}.
     */
    @Value("${openweather.api.url}")
    private String apiUrl;

    /**
     * {@code RestTemplate} is a synchronous HTTP client used to perform RESTful API calls.
     *
     * <p>This instance is used by {@code WeatherClient} to send requests to the OpenWeather API
     * and retrieve weather data as JSON responses.
     */
    private final RestTemplate restTemplate;

    /**
     * Constructs a {@code WeatherClient} with a {@link RestTemplate} instance.
     * The {@code RestTemplate} is injected by Spring via constructor injection.
     *
     * @param restTemplate the HTTP client used for sending requests to the OpenWeather API
     */
    @Autowired
    public WeatherClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Fetches current weather information for a given city using the OpenWeather API.
     *
     * <p>Makes a synchronous HTTP GET request to the API using {@link RestTemplate},
     * then parses the JSON response using {@link com.fasterxml.jackson.databind.ObjectMapper}
     * to extract the weather description and temperature.
     *
     * @param cityName the name of the city to query (e.g., "Berlin")
     * @return a {@link WeatherData} object containing the weather description and temperature in Celsius
     * @throws RuntimeException if the JSON response cannot be parsed
     */
    public WeatherData getWeatherForCity(String cityName) {
        // Construct the full URL with query parameters
        String url = String.format("%s?q=%s&appid=%s&units=metric", apiUrl, cityName, apiKey);

        // Send a GET request to the OpenWeather API and retrieve the response as a raw JSON string
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Parse the JSON and extract weather data
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode json = mapper.readTree(response.getBody());

            // Extract weather description and temperature from the JSON structure
            String description = json.path("weather").get(0).path("description").asText();
            double temperature = json.path("main").path("temp").asDouble();
            return new WeatherData(description, temperature);

        } catch (JsonProcessingException e) {
            // Wrap JSON parsing exceptions as unchecked exceptions
            throw new RuntimeException(e);
        }
    }

    // =======================================WeatherData POJO ==================================================

    /**
     * Simple POJO to hold weather data results returned from the API.
     * Includes description (e.g. "clear sky") and temperature in Celsius.
     */
    public static class WeatherData {
        private String description;
        private double temperature;

        /**
         * Constructs a {@code WeatherData} object with the given weather description and temperature.
         *
         * @param description the textual weather description
         * @param temperature the temperature in Celsius
         */
        public WeatherData(String description, double temperature) {
            this.description = description;
            this.temperature = temperature;
        }

        public String getDescription() {
            return description;
        }

        public double getTemperature() {
            return temperature;
        }

    }
}
